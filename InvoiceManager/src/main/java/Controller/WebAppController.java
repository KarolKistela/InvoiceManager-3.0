package Controller;

/**
 * Created by mzjdx6 on 24-Mar-16.
 */

import Model.*;
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
    private final HTMLtemplateEngine HTML;
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
        HTML = new HTMLtemplateEngine();
        ImCFG = new InvoiceManagerCFG();
        FreeMarkerCFG = createFreemarkerConfiguration();
        DAO = new InvoiceManagerDB_DAO(ImCFG.getImDBPath());

        setPort(8082);
        externalStaticFileLocation(ImCFG.getImExternalFolderPath());
        staticFileLocation("/"); // translate to /resources
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
        get(new FreemarkerBasedRoute("/DB/:pageNr", "DBmainView.ftl") {
            @Override
            protected void doHandle(Request request, Response response, Writer writer) throws IOException, TemplateException {
                SimpleHash root = new SimpleHash();

                root.put("style", HTML.getStyle());
                root.put("header2rowed", HTML.getHeader2rowed(
                                            "DB main view",                                     // view name to display in top bar
                                            1,                                                  // which icon from top bar to activate
                                            Integer.parseInt(request.params("pageNR")),         // what pageNr are we at, required for pagination
                                            150));                                              // total nr of pages, depends on query and ImCFG.nrOfColumnsToDisplay

                template.process(root, writer);
            }
        });

        get(new FreemarkerBasedRoute("/", "DBmainView.ftl") {
                @Override
                protected void doHandle(Request request, Response response, Writer writer) throws IOException, TemplateException {
                    response.redirect("/DB/1");
                }
        });
    }

    // TODO: rout   /ID/:id
    // TODO: rout   /ID/:id/scan
    // TODO: rout
    // TODO: rout
    // TODO: rout
    // TODO: rout
    // TODO: rout
    // TODO: rout

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