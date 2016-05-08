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

import org.joda.time.LocalDate;

public class Controller {
    private final InvoiceManagerCFG ImCFG;
    private final HtmlFactory htmlFactory;
    public static boolean isConnectedToDB;
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
        if (fileExists(ImCFG.getImDBPath())) {
            isConnectedToDB = InvoicesManagerDBconnection(ImCFG.getImDBPath());
        }
        System.err.println(new LocalDate().toString() + " testing connection to DB: " + isConnectedToDB);


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
            if (request.queryParams("search_query") == null) {
                if (request.queryParams("search_query_columns") == null) {
                    System.out.println(request.pathInfo());
                } else {
                    response.redirect("/Filter/Select/" + request.queryParams("search_query_columns")
                                                        + "/" + signConvertor(request.queryParams("search_query_sign"))
                                                        + "/" + request.queryParams("search_query_value") +"/1");
                }
            } else {
                response.redirect("/ID/" + request.queryParams("search_query"));
            }
        };

        public boolean redirectToSettings() throws ClassNotFoundException {
            if (isConnectedToDB) {
                return false;
            } else {
                if (fileExists(ImCFG.getImDBPath())) {
                    if (InvoicesManagerDBconnection(ImCFG.getImDBPath())) {
                        System.out.println("Connected to DB you can work");
                        return false;
                    } else {
                        return true;
                    }
                } else {
                    return true;
                }
            }
        }
    }

    private void initializeRoutes() throws IOException{
        get(new FreemarkerBasedRoute("/") {
            @Override
            protected void doHandle(Request request, Response response, StringWriter webPage) throws IOException, TemplateException, ClassNotFoundException, SQLException {
                if (redirectToSettings()) {
                    response.redirect("/Settings");
                } else {
                    response.redirect("/DB/1");
                }
            }
        });

        get(new FreemarkerBasedRoute("/ID/:id") {
            @Override
            protected void doHandle(Request request, Response response, StringWriter webPage) throws IOException, TemplateException, ClassNotFoundException, SQLException {
                if (redirectToSettings()) {
                    response.redirect("/Settings");
                } else {
                    Renderer InvoiceView = htmlFactory.getIDView(request);
                    webPage.write(InvoiceView.render());
                }

            }
        });

        post(new FreemarkerBasedRoute("/ID/:id") {
            @Override
            protected void doHandle(Request request, Response response, StringWriter webPage) throws IOException, TemplateException, ClassNotFoundException, SQLException {
                if (request.queryParams("search_query") == null) {
                    if (request.queryParams("search_query_columns") == null) {
                        System.out.println("ID " + request.params("id") + " update");
                        for (String s: request.queryParams()
                                ) {
                            System.out.println(s + " = " + request.queryParams(s));
                        }
                        if (new Invoice().save(request)) {
                            response.redirect("/ID/" + request.params("id"));
                        } else {
                            response.redirect("/Error/InvoiceUpadate");
                        }
                        response.redirect("/ID/" + request.params("id"));
                    } else {
                        System.out.println("Advanced search: ");
                        for (String s: request.queryParams()
                                ) {
                            System.out.println(s + " = " + request.queryParams(s));
                        }
                        this.searchRespons(request, response);
                    }
                } else {
                    System.out.println("Search for ID: ");
                    for (String s: request.queryParams()
                         ) {
                        System.out.println(s + " = " + request.queryParams(s));
                    }
                    this.searchRespons(request, response);
                }
            }
        });


        get(new FreemarkerBasedRoute("/DB/:pageNr") {
            @Override
            protected void doHandle(Request request, Response response, StringWriter webPage) throws IOException, TemplateException, ClassNotFoundException, SQLException {
                if (redirectToSettings()) {
                    response.redirect("/Settings");
                } else {
                    Renderer DBview = htmlFactory.getDataBaseView(request);
                    webPage.write(DBview.render());
                }
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
                if (redirectToSettings()) {
                    response.redirect("/Settings");
                } else {
                    String rout = getRout(request);
                    System.err.println(request.pathInfo().substring(0, request.pathInfo().lastIndexOf("/") + 1));

                    Renderer selectWhereView = htmlFactory.getSelectWhereView(request, rout);
                    webPage.write(selectWhereView.render());
                }
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
                    response.redirect("/Settings");
                } else {
                    response.redirect("/Error/UpdateCFG");
                }
            }
        });

        get(new FreemarkerBasedRoute("/Reports") {
            @Override
            protected void doHandle(Request request, Response response, StringWriter writer) throws IOException, TemplateException, ClassNotFoundException, SQLException {
                writer.write("Under construction :)");
            }
        });

        get(new FreemarkerBasedRoute("/ID/:idNr/invNr/:pageNr") {
            @Override
            protected void doHandle(Request request, Response response, StringWriter webPage) throws IOException, TemplateException, ClassNotFoundException, SQLException {
                if (redirectToSettings()) {
                    response.redirect("/Settings");
                } else {
                    String rout = "/ID/" + request.params("idNr") + "/invNr/";

                    Renderer invNr = htmlFactory.getInvNrView(request, rout);
                    webPage.write(invNr.render());
                }
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

