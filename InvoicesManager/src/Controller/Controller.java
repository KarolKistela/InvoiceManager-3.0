package Controller;

/**
 * Created by Karol Kistela on 10-07-2016.
 *
 * Main class of Invoices Manager.
 */

import Model.ComboList;
import Model.DAO.InvoiceManagerCFG;
import Model.DAO.InvoiceManagerDB_DAO;
import Model.Invoice;
import View.HtmlFactory;
import View.Renderer;
import freemarker.template.TemplateException;
import spark.Request;
import spark.Response;
import spark.Route;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.sql.SQLException;

import static Model.Helpers.*;
import static spark.Spark.*;

public class Controller {
    public final static Logger logger = new Logger();
    public static Config config = new Config();

    public static InvoiceManagerCFG ImCFG;
    public static ComboList comboList;
    public static boolean isConnectedToDB;
    public static String sqlQuery = new String(); // story last SQL query executed by Invoices Manager go to DAO_InvoicesFullView constructor

    private final HtmlFactory htmlFactory;

    public static void main(String[] args) {
        if (args.length == 0) {
            new Controller(); // use default path to InvoicesManager.cfg
        }
        else {
            new Controller(); // TODO: reference Config values to IM from JSON file
        }
    }

    public Controller() {
        htmlFactory = new HtmlFactory();
        setPort(config.PORT);
        externalStaticFileLocation(config.EXTERNAL_FOLDER_PATH);

        try {
            ImCFG = new InvoiceManagerCFG(config.CFG_PATH);
            initializeRoutes();
            runShellCommand("chrome " + "http://" + InetAddress.getLocalHost().getHostName() + ":" + config.PORT + "/");
            comboList = new ComboList();
        } catch (SQLException | InterruptedException | IOException | ClassNotFoundException sql) {
            logger.addException(sql);
        }

        try {
            if (fileExists(ImCFG.getImDBPath())) {
                isConnectedToDB = InvoicesManagerDBconnection(ImCFG.getImDBPath());
            }
        } catch (ClassNotFoundException e) {
            logger.addException(e);
        }

        try {
            if (config.COPY_OTM) copyOTMfile();
        } catch (IOException e) {
            logger.addException(e);
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
                logger.addException(e);
                    response.redirect("/Settings");
            }
            return writer;
        }

        protected abstract void doHandle(final Request request, final Response response, final StringWriter writer)
                throws IOException, TemplateException, ClassNotFoundException, SQLException;

        public void searchRespons(Request request, Response response) throws UnsupportedEncodingException, FileNotFoundException, ClassNotFoundException, SQLException {
            // TODO: change this to switch case
            if (request.queryParams("search_query") == null) { // quick search is empty do Advance search
                if (request.queryParams("search_query_columns") == null) { // advance search is empty
                    // save Invoice from INVinputForm
                    logger.add("ID " + request.params("value1") + " update");
                    for (String s: request.queryParams()
                            ) {
                        logger.add(s + " = " + request.queryParams(s));
                    }
                    if (new Invoice().save(request)) {
                        response.redirect("/IFV/ID/eq/" + request.params("value1") + "/ID/DESC/1");
                    } else {
                        response.redirect("/Settings");
                    }
                } else {
                    response.redirect(this.getIFVadvanceSearchURL(request));
                }
            } else if (request.queryParams("search_query").length() == 10) { // quick search is for BarCode
                response.redirect("/IFV/BC/eq/" + request.queryParams("search_query") + "/ID/DESC/1");
            } else { // quick search is for ID
                response.redirect("/IFV/ID/eq/" + request.queryParams("search_query") + "/ID/DESC/1");
            }
        };

        private String getIFVadvanceSearchURL(Request request) throws UnsupportedEncodingException {
            String retVal = "/IFV/";

            if (!request.queryParams("search_query_columns").equals("")) {
                retVal += URLEncoder.encode(request.queryParams("search_query_columns"), "UTF-8") + "/";
                retVal += URLEncoder.encode(signConvertor(request.queryParams("search_query_sign")), "UTF-8") + "/";
                if (request.queryParams("search_query_value").equals("")) {
                    retVal += "null/";
                } else {
                    retVal += URLEncoder.encode(request.queryParams("search_query_value"), "UTF-8") + "/";
                }
            }

            if (!request.queryParams("search_query_columns2").equals("")) {
                retVal += URLEncoder.encode(request.queryParams("search_query_columns2"), "UTF-8") + "/";
                retVal += URLEncoder.encode(signConvertor(request.queryParams("search_query_sign2")), "UTF-8") + "/";
                if (request.queryParams("search_query_value2").equals("")) {
                    retVal += "null/";
                } else {
                    retVal += URLEncoder.encode(request.queryParams("search_query_value2"), "UTF-8") + "/";
                }
            }

            if (!request.queryParams("search_query_columns3").equals("")) {
                retVal += URLEncoder.encode(request.queryParams("search_query_columns3"), "UTF-8") + "/";
                retVal += URLEncoder.encode(signConvertor(request.queryParams("search_query_sign3")), "UTF-8") + "/";
                if (request.queryParams("search_query_value3").equals("")) {
                    retVal += "null/";
                } else {
                    retVal += URLEncoder.encode(request.queryParams("search_query_value3"), "UTF-8") + "/";
                }
            }

            return retVal + "ID/DESC/1";
        }

        public boolean redirectToSettings() throws ClassNotFoundException {
            if (isConnectedToDB) {
                return false;
            } else {
                if (fileExists(ImCFG.getImDBPath())) {
                    if (InvoicesManagerDBconnection(ImCFG.getImDBPath())) {
                        logger.add("Connected to DB you can work");
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
                    response.redirect("/IFV/ID/gte/0/ID/DESC/1");
                }
            }
        });
// ================================= Invoices view =====================================================================
        get(new FreemarkerBasedRoute("/IFV/:column1/:sign1/:value1/:column2/:sign2/:value2/:column3/:sign3/:value3/:orderBy/:sort/:pageNr") {
            @Override
            protected void doHandle(Request request, Response response, StringWriter webPage) throws IOException, TemplateException, ClassNotFoundException, SQLException {
                if (redirectToSettings()) {
                    response.redirect("/Settings");
                } else {
                    Renderer InvoicesView = htmlFactory.getInvoicesView(request);
                    webPage.write(InvoicesView.render());
                }
            }
        });
        post(new FreemarkerBasedRoute("/IFV/:column1/:sign1/:value1/:column2/:sign2/:value2/:column3/:sign3/:value3/:orderBy/:sort/:pageNr") {
            @Override
            protected void doHandle(Request request, Response response, StringWriter webPage) throws IOException, TemplateException, ClassNotFoundException, SQLException {
                this.searchRespons(request, response);
            }
        });
        get(new FreemarkerBasedRoute("/IFV/:column1/:sign1/:value1/:column2/:sign2/:value2/:orderBy/:sort/:pageNr") {
            @Override
            protected void doHandle(Request request, Response response, StringWriter webPage) throws IOException, TemplateException, ClassNotFoundException, SQLException {
                if (redirectToSettings()) {
                    response.redirect("/Settings");
                } else {
                    Renderer InvoicesView = htmlFactory.getInvoicesView(request);
                    webPage.write(InvoicesView.render());
                }
            }
        });
        post(new FreemarkerBasedRoute("/IFV/:column1/:sign1/:value1/:column2/:sign2/:value2/:orderBy/:sort/:pageNr") {
            @Override
            protected void doHandle(Request request, Response response, StringWriter webPage) throws IOException, TemplateException, ClassNotFoundException, SQLException {
                this.searchRespons(request, response);
            }
        });
        get(new FreemarkerBasedRoute("/IFV/:column1/:sign1/:value1/:orderBy/:sort/:pageNr") {
            @Override
            protected void doHandle(Request request, Response response, StringWriter webPage) throws IOException, TemplateException, ClassNotFoundException, SQLException {
                if (redirectToSettings()) {
                    response.redirect("/Settings");
                } else {
                    Renderer InvoicesView = htmlFactory.getInvoicesView(request);
                    webPage.write(InvoicesView.render());
                }
            }
        });
        post(new FreemarkerBasedRoute("/IFV/:column1/:sign1/:value1/:orderBy/:sort/:pageNr") {
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
                    response.redirect("/error");
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
// ================================= Exit rout - exit IM ====================================================================================
        get(new FreemarkerBasedRoute("/exit") {
            @Override
            protected void doHandle(Request request, Response response, StringWriter webPage) throws IOException, TemplateException, ClassNotFoundException, SQLException {
                System.exit(0);
            }
        });
// ================================= Error view - not implemented yet =======================================================================
// TODO: error msg handling
        get(new FreemarkerBasedRoute("/error") {
            @Override
            protected void doHandle(Request request, Response response, StringWriter writer) throws IOException, TemplateException, SQLException, ClassNotFoundException {
                Renderer errorView = HtmlFactory.getErrorView();
                writer.write(errorView.render());
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

