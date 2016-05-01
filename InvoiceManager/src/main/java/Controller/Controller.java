package Controller;

/**
 * Created by mzjdx6 on 24-Mar-16.
 * TODO: rewrite it to use with spark 2.3 - low priority
 */

import Model.*;
import Depreciated.HTMLviewGenerator;
import View.Renderer;
import freemarker.template.TemplateException;
import spark.Request;
import spark.Response;
import spark.Route;
import View.HtmlFactory;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.SQLException;

import static Model.Helpers.getRout;
import static Model.Helpers.runShellCommand;
import static spark.Spark.*;

public class Controller {
    private final HTMLviewGenerator Renderer;
    private final InvoiceManagerCFG ImCFG;
    private final HtmlFactory htmlFactory;
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
        htmlFactory = new HtmlFactory();

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

        public void searchRespons(Request request, Response response){
            String s = request.queryParams("search_query");
            response.redirect("/ID/"+s);
        };
    }

    private void initializeRoutes() throws IOException{
        get(new FreemarkerBasedRoute("/DB/:pageNr") {
            @Override
            protected void doHandle(Request request, Response response, StringWriter webPage) throws IOException, TemplateException, ClassNotFoundException, SQLException {
                Renderer DBview = htmlFactory.getDataBaseView(request);
                webPage.write(DBview.render());
            }
        });
        post(new FreemarkerBasedRoute("/DB/:pageNr") {
            @Override
            protected void doHandle(Request request, Response response, StringWriter webPage) throws IOException, TemplateException, ClassNotFoundException, SQLException {
                this.searchRespons(request, response);
            }
        });


        get(new FreemarkerBasedRoute("/Filter/Select/:columnName/:sign/:value/:pageNr") {
            @Override
            protected void doHandle(Request request, Response response, StringWriter webPage) throws IOException, TemplateException, ClassNotFoundException, SQLException {
                String rout = getRout(request);
                System.err.println(request.pathInfo().substring(0,request.pathInfo().lastIndexOf("/")+1));

                Renderer selectWhereView = htmlFactory.getSelectWhereView(request, rout);
                webPage.write(selectWhereView.render());
            }
        });
        post(new FreemarkerBasedRoute("/Filter/Select/:columnName/:sign/:value/:pageNr") {
            @Override
            protected void doHandle(Request request, Response response, StringWriter webPage) throws IOException, TemplateException, ClassNotFoundException, SQLException {
                this.searchRespons(request, response);
            }
        });

        get(new FreemarkerBasedRoute("/Settings") {
            @Override
            protected void doHandle(Request request, Response response, StringWriter webPage) throws IOException, TemplateException, ClassNotFoundException, SQLException {
                Renderer settingView = HtmlFactory.getSettingsView(request);
                        webPage.write(settingView.render());
            }
        });
        post(new FreemarkerBasedRoute("/Settings") {
            @Override
            protected void doHandle(Request request, Response response, StringWriter webPage) throws IOException, TemplateException, ClassNotFoundException, SQLException {
                System.err.println(request.queryParams("imDBPath"));
            }
        });


        get(new FreemarkerBasedRoute("/ID/:idNr/invNr/:pageNr") {
            @Override
            protected void doHandle(Request request, Response response, StringWriter webPage) throws IOException, TemplateException, ClassNotFoundException, SQLException {
                String rout = "/ID/"+request.params("idNr")+"/invNr/";

                Renderer invNr = htmlFactory.getInvNrView(request, rout);
                webPage.write(invNr.render());
            }
        });
        post(new FreemarkerBasedRoute("/ID/:idNr/invNr/:pageNr") {
            @Override
            protected void doHandle(Request request, Response response, StringWriter webPage) throws IOException, TemplateException, ClassNotFoundException, SQLException {
                this.searchRespons(request, response);
            }
        });

        get(new FreemarkerBasedRoute("/error/:errorMSG") {
            @Override
            protected void doHandle(Request request, Response response, StringWriter writer) throws IOException, TemplateException {
                writer.write(errorMSG.toString());
            }
        });
        /* this rout will open tif file for ID
         */
        get(new FreemarkerBasedRoute("/ID/:idNr/scan") {
            @Override
            protected void doHandle(Request request, Response response, StringWriter writer) throws IOException, TemplateException, ClassNotFoundException {
                String filePath = new InvoiceManagerDB_DAO().filePath(request.params("idNr"),"InvScanPath");
                try {
                    runShellCommand(filePath);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                writer.write("Open invoice scan file");
            }
        });
        get(new FreemarkerBasedRoute("/ID/:idNr/authEmail") {
            @Override
            protected void doHandle(Request request, Response response, StringWriter writer) throws IOException, TemplateException, ClassNotFoundException {
                String filePath = new InvoiceManagerDB_DAO().filePath(request.params("idNr"),"AuthEmail");
                try {
                    runShellCommand(filePath);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                writer.write("Open Authorization msg");
            }
        });

    }
    // TODO: rout   /ID/:id
    // TODO: rout   /ID/:id/scan
    // TODO: rout   /ID/:id/AuthEmail
    // TODO: rout   /Filter/Supplier/:supplier
    // TODO: rout   /Filter/AuthContact/:authContact  hmm... or more generic: /Filter/:filterKey/:filterValue
    // TODO: rout   /ID/:${ID}/invNr
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

