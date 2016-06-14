package Controller;

/**
 * Created by mzjdx6 on 24-Mar-16.
 * TODO: rewrite it to use with spark 2.3 - low priority
 * TODO: ImCFG should be Initiated in controller only, other objects should only do reload method on that object
 * TODO:
 */

import Model.*;
import View.Renderer;
import freemarker.template.TemplateException;
import spark.Request;
import spark.Response;
import spark.Route;
import View.HtmlFactory;

import java.net.InetAddress;
import java.net.URLEncoder;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.URLDecoder;
import java.sql.SQLException;

import static Model.Helpers.*;
import static spark.Spark.*;

import org.joda.time.LocalDate;

public class Controller {
    public static final Integer PORT = 8082;
    public static boolean isConnectedToDB;
    public static InvoiceManagerCFG ImCFG;
    // TODO: if there will be need to add more list (for eg: Authorization contanct list) then move them to class FilterLists - one class to rule them all
    public static Suppliers suppliers;
    private final HtmlFactory htmlFactory;
    // TODO: do log class for storying errors, recent routs, sql queryies
    public PrintWriter errorMSG; // not sure if it will stay or not
    public static String advanceSearchSQLquery = new String();
    public static String sqlQuery = new String();

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
        suppliers = new Suppliers();
        suppliers.toString();

        if (fileExists(ImCFG.getImDBPath())) {
            isConnectedToDB = InvoicesManagerDBconnection(ImCFG.getImDBPath());
        }

        System.err.println(new LocalDate().toString() + " testing connection to DB: " + isConnectedToDB);

        setPort(PORT);
        externalStaticFileLocation(ImCFG.getImExternalFolderPath());
        staticFileLocation("/"); // => /resources
        initializeRoutes();
        try { // Open chrome with Invoices Manager address
            runShellCommand("chrome " + "http://" + InetAddress.getLocalHost().getHostName() + ":" + PORT + "/");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
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
            // TODO: change this to switch case
            if (request.queryParams("search_query") == null) {
                if (request.queryParams("search_query_columns") == null) {
                    System.out.println(request.pathInfo());
                } else if (!request.queryParams("search_query_columns2").equals("")) {
                    advanceSearchSQLquery = advanceSearchSQLqueryConstructor(request);
                    response.redirect("/advSearchQuery/OrderBy");
                } else {
                    String searchValue = URLEncoder.encode(request.queryParams("search_query_value"));
                    if (searchValue.equals("")) searchValue = "null";
                    response.redirect("/View/" + request.queryParams("search_query_columns")
                                               + "/" + signConvertor(request.queryParams("search_query_sign"))
                                               + "/" + searchValue
                                               +"/OrderBy");
                }
            } else if (request.queryParams("search_query").length() == 10) {
                response.redirect("/View/BC/eq/" + request.queryParams("search_query") + "/OrderBy");
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
                    // change orderByClaouse from Imcfg, eg. 'ORDER BY ID DESC' => 'ID/DESC'
                    String orderByClause = ImCFG.getOrderByClause().replace("ORDER BY ","").replace(" ","/");
                    response.redirect("/View/ID/gte/0/OrderBy/" + orderByClause + "/1");
                }
            }
        });
// ================================= Single Invoice view ====================================================================================
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
// ================================= Main view for displaying queries returning 1 or more records from DB ===================================
        get(new FreemarkerBasedRoute("/View/:columnName/:sign/:value/OrderBy/:columnName2/:direction/:pageNr") {
            @Override
            protected void doHandle(Request request, Response response, StringWriter webPage) throws IOException, TemplateException, ClassNotFoundException, SQLException {
                if (redirectToSettings()) {
                    response.redirect("/Settings");
                } else {
                    Renderer queryView = htmlFactory.getQueryView(request);
                    webPage.write(queryView.render());
                }
            }
        });
        get(new FreemarkerBasedRoute("/View/:columnName/:sign/:value/OrderBy") {
            @Override
            protected void doHandle(Request request, Response response, StringWriter webPage) throws IOException, TemplateException, ClassNotFoundException, SQLException {
                response.redirect("/View/" + request.params("columnName") + "/"
                        + request.params("sign") + "/" + request.params("value")
                        + "/OrderBy/" + ImCFG.getOrderByClauseURL());
            }
        });
        post(new FreemarkerBasedRoute("/View/:columnName/:sign/:value/OrderBy/:columnName2/:direction/:pageNr") {
            @Override
            protected void doHandle(Request request, Response response, StringWriter webPage) throws IOException, TemplateException, ClassNotFoundException, SQLException {
                this.searchRespons(request, response);
            }
        });
// ================================= Main view for displaying queries with AND clause =======================================================
        get(new FreemarkerBasedRoute("/View/:columnName/:sign/:value/OrderBy/:columnName2/:direction/:columnName3/:value3/:pageNr") {
            @Override
            protected void doHandle(Request request, Response response, StringWriter webPage) throws IOException, TemplateException, ClassNotFoundException, SQLException {
                if (redirectToSettings()) {
                    response.redirect("/Settings");
                } else {
                    Renderer queryView = htmlFactory.getQueryView(request, request.params("columnName3"), request.params("value3"));
                    webPage.write(queryView.render());
                }
            }
        });
        post(new FreemarkerBasedRoute("\"/View/:columnName/:sign/:value/OrderBy/:columnName2/:direction/:columnName3/:value3/:pageNr\"") {
            @Override
            protected void doHandle(Request request, Response response, StringWriter webPage) throws IOException, TemplateException, ClassNotFoundException, SQLException {
                this.searchRespons(request, response);
            }
        });
// ================================= Main view for displaying filter view returning 1 or more records from DB ===================================
        get(new FreemarkerBasedRoute("/FilterView/:filterNR/OrderBy/:columnName2/:direction/:pageNr") {
            @Override
            protected void doHandle(Request request, Response response, StringWriter webPage) throws IOException, TemplateException, ClassNotFoundException, SQLException {
                if (redirectToSettings()) {
                    response.redirect("/Settings");
                } else {
                    Renderer queryView = htmlFactory.getQueryView(request, Integer.parseInt(request.params("filterNR"))-1);
                    webPage.write(queryView.render());
                }
            }
        });
        get(new FreemarkerBasedRoute("/FilterView/:filterNR/OrderBy") {
            @Override
            protected void doHandle(Request request, Response response, StringWriter webPage) throws IOException, TemplateException, ClassNotFoundException, SQLException {
                response.redirect("/FilterView/" + request.params("filterNr") + "/"
                        + "OrderBy/" + ImCFG.getOrderByClause().replace("ORDER BY ","").replace(" ","/") + "/1");
            }
        });
        post(new FreemarkerBasedRoute("/FilterView/:filterNR/OrderBy/:columnName2/:direction/:pageNr") {
            @Override
            protected void doHandle(Request request, Response response, StringWriter webPage) throws IOException, TemplateException, ClassNotFoundException, SQLException {
                this.searchRespons(request, response);
            }
        });
// ================================= Main view for displaying adv search query 1 or more records from DB ===================================
        get(new FreemarkerBasedRoute("/advSearchQuery/OrderBy/:columnName2/:direction/:pageNr") {
            @Override
            protected void doHandle(Request request, Response response, StringWriter webPage) throws IOException, TemplateException, ClassNotFoundException, SQLException {
                if (redirectToSettings()) {
                    response.redirect("/Settings");
                } else {
                    Renderer queryView = htmlFactory.getQueryView(request, true);
                    webPage.write(queryView.render());
                }
            }
        });
        get(new FreemarkerBasedRoute("/advSearchQuery/OrderBy") {
            @Override
            protected void doHandle(Request request, Response response, StringWriter webPage) throws IOException, TemplateException, ClassNotFoundException, SQLException {
                response.redirect("/advSearchQuery/OrderBy/" + ImCFG.getOrderByClauseURL());
            }
        });
        post(new FreemarkerBasedRoute("/advSearchQuery/OrderBy/:columnName2/:direction/:pageNr") {
            @Override
            protected void doHandle(Request request, Response response, StringWriter webPage) throws IOException, TemplateException, ClassNotFoundException, SQLException {
                this.searchRespons(request, response);
            }
        });

// ================================= Settings view ==========================================================================================
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
// ================================= Save query result to file ==============================================================================
        get(new FreemarkerBasedRoute("/Save") {
            @Override
            protected void doHandle(Request request, Response response, StringWriter webPage) throws IOException, TemplateException, ClassNotFoundException, SQLException {
//                writer.write(Controller.sqlQuery);
                Renderer saveView = HtmlFactory.getSaveView(request);
                webPage.write(saveView.render());
            }
        });
// ================================= Error view - not implemented yet =======================================================================
// TODO: error msg handling
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
        /* DEPRECIATED this rout will open email msg file for ID
         */
        get(new FreemarkerBasedRoute("/ID/:idNr/authEmail") {
            @Override
            protected void doHandle(Request request, Response response, StringWriter writer) throws IOException, TemplateException, ClassNotFoundException, SQLException {
                try {
                    writer.write("Create Authorization msg");
                    runShellCommand2(Integer.parseInt(request.params("idNr").replace(",","")));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        });
        /* this rout will open foldet with invoice scan file for ID
         */
        get(new FreemarkerBasedRoute("/ID/:idNr/Folder") {
            @Override
            protected void doHandle(Request request, Response response, StringWriter writer) throws IOException, TemplateException, ClassNotFoundException {
                String filePath = new InvoiceManagerDB_DAO().filePath(request.params("idNr"),"InvScanPath");
                try {
                    runShellCommand("explorer " + filePath.substring(0,filePath.lastIndexOf("\\")+1) + "\"");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                writer.write("Open Folder for ID " + request.params("idNr"));
            }
        });

        get(new FreemarkerBasedRoute("/OpenFolder/:folderPath") {
            @Override
            protected void doHandle(Request request, Response response, StringWriter writer) throws IOException, TemplateException, ClassNotFoundException {
                String filePath = new InvoiceManagerDB_DAO().filePath(request.params("idNr"),"InvScanPath");
                try {
                    runShellCommand("explorer " + URLDecoder.decode(request.params("folderPath"),"UTF-8"));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                writer.write("Open Folder for ID " + request.params("idNr"));
            }
        });
// ================================= Reports view ===========================================================================================
// TODO: do report view
       get(new FreemarkerBasedRoute("/Reports") {
            @Override
            protected void doHandle(Request request, Response response, StringWriter writer) throws IOException, TemplateException, ClassNotFoundException, SQLException {
                writer.write("Under construction :)");
            }
        });
    }
}

// ================================= Duplicate Invoice view =================================================================================
//        get(new FreemarkerBasedRoute("/ID/:idNr/invNr/:pageNr") {
//            @Override
//            protected void doHandle(Request request, Response response, StringWriter webPage) throws IOException, TemplateException, ClassNotFoundException, SQLException {
//                if (redirectToSettings()) {
//                    response.redirect("/Settings");
//                } else {
//                    String rout = "/ID/" + request.params("idNr") + "/invNr/";
//
//                    Renderer invNr = htmlFactory.getInvNrView(request, rout);
//                    webPage.write(invNr.render());
//                }
//            }
//        });
//        post(new FreemarkerBasedRoute("/ID/:idNr/invNr/:pageNr") {
//            @Override
//            protected void doHandle(Request request, Response response, StringWriter webPage) throws IOException, TemplateException, ClassNotFoundException, SQLException {
//                this.searchRespons(request, response);
//            }
//        });
// ================================= Depreciated routs ======================================================================================
//
//        get(new FreemarkerBasedRoute("/DB/:pageNr") {
//            @Override
//            protected void doHandle(Request request, Response response, StringWriter webPage) throws IOException, TemplateException, ClassNotFoundException, SQLException {
//                if (redirectToSettings()) {
//                    response.redirect("/Settings");
//                } else {
//                    // change orderByClaouse from Imcfg, eg. 'ORDER BY ID DESC' => 'ID/DESC'
//                    String orderByClause = ImCFG.getOrderByClause().replace("ORDER BY ","").replace(" ","/");
//                    response.redirect("/View/ID/gte/0/OrderBy/" + orderByClause + "/" + request.params("pageNr"));
//                }
//            }
//        });
//        post(new FreemarkerBasedRoute("/DB/:pageNr") {
//            @Override
//            protected void doHandle(Request request, Response response, StringWriter webPage) throws IOException, TemplateException, ClassNotFoundException, SQLException {
//                this.searchRespons(request, response);
//            }
//        });
//        get(new FreemarkerBasedRoute("/Filter/Select/:columnName/:sign/:value/:pageNr") {
//            @Override
//            protected void doHandle(Request request, Response response, StringWriter webPage) throws IOException, TemplateException, ClassNotFoundException, SQLException {
//                if (redirectToSettings()) {
//                    response.redirect("/Settings");
//                } else {
////                    String rout = getRout(request);
////                    System.err.println(request.pathInfo().substring(0, request.pathInfo().lastIndexOf("/") + 1));
////
////                    Renderer selectWhereView = htmlFactory.getSelectWhereView(request, rout);
////                    webPage.write(selectWhereView.render());
//                    // change orderByClaouse from Imcfg, eg. 'ORDER BY ID DESC' => 'ID/DESC'
//                    String orderByClause = ImCFG.getOrderByClause().replace("ORDER BY ","").replace(" ","/");
//                    response.redirect("/View/"  + request.params("columnName") + "/"
//                                                + request.params("sign") + "/"
//                                                + request.params("value") + "/OrderBy/" + orderByClause + "/" + request.params("pageNr"));
//                }
//            }
//        });
//        post(new FreemarkerBasedRoute("/Filter/Select/:columnName/:sign/:value/:pageNr") {
//            @Override
//            protected void doHandle(Request request, Response response, StringWriter webPage) throws IOException, TemplateException, ClassNotFoundException, SQLException {
//                this.searchRespons(request, response);
//            }
//        });