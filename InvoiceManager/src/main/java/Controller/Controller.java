package Controller;

/**
 * Created by mzjdx6 on 24-Mar-16.
 * TODO: rewrite it to use with spark 2.3 - low priority
 */

import Model.*;
import View.HTMLviewGenerator;
import freemarker.template.TemplateException;
import spark.Request;
import spark.Response;
import spark.Route;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.SQLException;

import static spark.Spark.*;

public class Controller {
    private final HTMLviewGenerator Renderer;
    private final InvoiceManagerCFG ImCFG;
    public PrintWriter errorMSG; // not sure if it will stay or not

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        //TODO: (args.length == 0) ? connect to getDBview from ImCFG : connect to test getDBview for show case
        if (args.length == 0) {
            new Controller();
        }
        else {
            new Controller();
        }
    }

    public Controller() throws IOException, ClassNotFoundException {
        Renderer = new HTMLviewGenerator();
        ImCFG = new InvoiceManagerCFG();

        setPort(8082);
        externalStaticFileLocation(ImCFG.getImExternalFolderPath());
        staticFileLocation("/"); // => /resources
        initializeRoutes();
    }

    abstract class FreemarkerBasedRoute extends Route {

        protected FreemarkerBasedRoute(final String path) throws IOException {
            super(path);
        }

        @Override
        public Object handle(Request request, Response response) {
            StringWriter writer = new StringWriter();
            try {
                doHandle(request, response, writer);
            } catch (Exception e) {
                e.printStackTrace(errorMSG); ;
                response.redirect("/error/");
            }
            return writer;
        }

        protected abstract void doHandle(final Request request, final Response response, final StringWriter writer)
                throws IOException, TemplateException, ClassNotFoundException, SQLException;
    }

    private void initializeRoutes() throws IOException{
        get(new FreemarkerBasedRoute("/DB/:pageNr") {
            @Override
            protected void doHandle(Request request, Response response, StringWriter html) throws IOException, TemplateException, ClassNotFoundException, SQLException {
                html.write(Renderer.getDBview(request,response));
            }
        });
        get(new FreemarkerBasedRoute("/error/:errorMSG") {
            @Override
            protected void doHandle(Request request, Response response, StringWriter writer) throws IOException, TemplateException {
                writer.write(errorMSG.toString());
            }
        });

        /* Test
         */
        get(new FreemarkerBasedRoute("/ID/:idNr/scan") {
            @Override
            protected void doHandle(Request request, Response response, StringWriter writer) throws IOException, TemplateException {
                int pageNr = Integer.parseInt(request.params("idNr"));

                Runtime runTime = Runtime.getRuntime();
                Process process = runTime.exec("notepad");

                writer.write("Open NotePad");
            }
        });
    }

    // TODO: rout   /ID/:id
    // TODO: rout   /ID/:id/scan
    // TODO: rout   /ID/:id/email
    // TODO: rout
    // TODO: rout
    // TODO: rout
    // TODO: rout
    // TODO: rout
}

//                int pageNr = Integer.parseInt(request.params(":Nr"));
//
//                if (pageNr == 5) {
//                    Runtime runTime = Runtime.getRuntime();
//                    Process process = runTime.exec("notepad");
//                    response.redirect("/internal_error");
//                } else {

