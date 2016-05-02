package Controller;

/**
 * Created by mzjdx6 on 24-Mar-16.
 * TODO: rewrite it to use with spark 2.3 - low priority
 */

import Model.*;
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

import static Model.Helpers.*;
import static spark.Spark.*;

public class Controller {
    private final InvoiceManagerCFG ImCFG;
    private final HtmlFactory htmlFactory;
    public boolean isConnectedToDB;
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
        ImCFG = new InvoiceManagerCFG();
        htmlFactory = new HtmlFactory();
        isConnectedToDB = InvoicesManagerDBconnection(ImCFG.getImDBPath());
        System.err.println("Connectioned to DB: " + isConnectedToDB);


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
        public void redirectToSettings(Response res){
            if (!isConnectedToDB) {
                res.redirect("/Settings");
            }
        }
    }

    private void initializeRoutes() throws IOException{
        get(new FreemarkerBasedRoute("/") {
            @Override
            protected void doHandle(Request request, Response response, StringWriter webPage) throws IOException, TemplateException, ClassNotFoundException, SQLException {
                redirectToSettings(response);
                response.redirect("/DB/1");
            }
        });
        get(new FreemarkerBasedRoute("/DB/:pageNr") {
            @Override
            protected void doHandle(Request request, Response response, StringWriter webPage) throws IOException, TemplateException, ClassNotFoundException, SQLException {
                redirectToSettings(response);
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
                redirectToSettings(response);
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
                if (ImCFG.save(request)) {
                    webPage.write(request.queryParams("imExternalFolderPath"));
                    webPage.write(" & " + '\n');
                    webPage.write(request.queryParams("imDBPath"));
                    webPage.write(" & " + '\n');
                    webPage.write(request.queryParams("rowsPerPage"));
                    webPage.write(" & " + '\n');
                    webPage.write(request.queryParams("orderBy")); // if no changes where made it will return "" !!!
                    webPage.write(" & " + '\n');
                    webPage.write(request.queryParams("order")); // returns ON when descending, null when ascending
                    webPage.write(" & " + '\n');
                    webPage.write(request.queryParams("duplicates")); // returns ON when duplicates = yes, null when duplicates = no
                    webPage.write(" & " + '\n');
                    webPage.write(request.queryParams("userID")); // returns ON when duplicates = yes, null when duplicates = no
                    webPage.write(" & " + '\n');
                    webPage.write(request.queryParams("userEmail"));
                    webPage.write(" & " + '\n');
                    webPage.write(request.queryParams("userColor"));
                    response.redirect("/Settings");
                } else {
                    response.redirect("/Error/UpdateCFG");
                }
            }
        });


        get(new FreemarkerBasedRoute("/ID/:idNr/invNr/:pageNr") {
            @Override
            protected void doHandle(Request request, Response response, StringWriter webPage) throws IOException, TemplateException, ClassNotFoundException, SQLException {
                redirectToSettings(response);
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
    // TODO: take care off nullPointerException for Imcfg and Users database querys!
}

//                int pageNr = Integer.parseInt(request.params(":Nr"));
//
//                if (pageNr == 5) {
//                    Runtime runTime = Runtime.getRuntime();
//                    Process process = runTime.exec("notepad");
//                    response.redirect("/internal_error");
//                } else {

