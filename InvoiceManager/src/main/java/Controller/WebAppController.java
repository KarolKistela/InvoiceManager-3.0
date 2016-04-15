package Controller;

/**
 * Created by mzjdx6 on 24-Mar-16.
 */

import Model.*;
import InvoiceManagerCFG.*;
import freemarker.template.Configuration;
import freemarker.template.SimpleHash;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import spark.Request;
import spark.Response;
import spark.Route;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;

import static spark.Spark.*;

public class WebAppController {
    private final HTMLblockEngine HTML;
    private final InvoiceManagerCFG ImCFG;
    private final Configuration FreeMarkerCFG;
    private final InvoiceManagerDB_DAO DAO;

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        if (args.length == 0) {
            new WebAppController();
        }
        else {
            new WebAppController();
        }
    }

    public WebAppController() throws IOException, ClassNotFoundException {
        HTML = new HTMLblockEngine();
        ImCFG = new InvoiceManagerCFG();
        FreeMarkerCFG = createFreemarkerConfiguration();
        DAO = new InvoiceManagerDB_DAO(ImCFG.getImDBPath());

        setPort(8082);
        externalStaticFileLocation(ImCFG.getImExternalFolderPath());
        staticFileLocation("/public"); // TODO: check if this is correct path
        initializeRoutes();
    }

    abstract class FreemarkerBasedRoute extends Route {
        final Template template;
        /**
         * TODO: code description
         */
        protected FreemarkerBasedRoute(final String path, final String templateName) throws IOException {
            super(path);
            template = FreeMarkerCFG.getTemplate(templateName);
        }

        @Override
        public Object handle(Request request, Response response) {
            StringWriter writer = new StringWriter();
            try {
                doHandle(request, response, writer);
            } catch (Exception e) {
                e.printStackTrace();
                response.redirect("/internal_error");
            }
            return writer;
        }

        protected abstract void doHandle(final Request request, final Response response, final Writer writer)
                throws IOException, TemplateException;

    }

    private void initializeRoutes() throws IOException{
        get(new FreemarkerBasedRoute("/test/:nr", "test.html") {
            @Override
            protected void doHandle(Request request, Response response, Writer writer) throws IOException, TemplateException {
                SimpleHash root = new SimpleHash();

                root.put("body", HTML.test("Karol", "Kistela", request));

                template.process(root, writer);
            }
        });
    }

    private Configuration createFreemarkerConfiguration() {
        Configuration retVal = new Configuration();
        retVal.setClassForTemplateLoading(WebAppController.class, "/");
        return retVal;
    }
}

//                int pageNr = Integer.parseInt(request.params(":Nr"));
//
//                if (pageNr == 5) {
//                    Runtime runTime = Runtime.getRuntime();
//                    Process process = runTime.exec("notepad");
//                    response.redirect("/internal_error");
//                } else {