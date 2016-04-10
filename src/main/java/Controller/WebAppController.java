package Controller;//package Controller;
//
//
//import java.io.IOException;
//import java.io.StringWriter;
//import java.io.Writer;
//
///**
// * Created by mzjdx6 on 07-Apr-16.
// */
//public class WebAppController {
//    private final Configuration cfg;
//    private final baza_2010_DAO baza_2010_DAO;
//    private final userSettings userSettings;
//
//    public static void main(String[] args) throws IOException {
//        if (args.length == 0) {
//            new WebAppController("C:\\Users\\mzjdx6\\Desktop\\InvoiceManager\\InvM_DB\\InvoiceManager.db");
//        }
//        else {
//            new WebAppController(args[0]);
//        }
//    }
//
//    public WebAppController(String DBpath) throws IOException {
//        baza_2010_DAO = new baza_2010_DAO(DBpath);
//        userSettings = new userSettings(300);
//        cfg = createFreemarkerConfiguration();
//        setPort(8082);
//        externalStaticFileLocation("C:/");
//        staticFileLocation("/public");
//        initializeRoutes();
//    }
//
//    abstract class FreemarkerBasedRoute extends Route {
//        final Template template;
//
//        /**
//         * Zrodlo: https://university.mongodb.com/m101j/
//         * Constructor
//         *
//         * @param path The route path which is used for matching. (e.g. /hello, users/:name)
//         */
//        protected FreemarkerBasedRoute(final String path, final String templateName) throws IOException {
//            super(path);
//            template = cfg.getTemplate(templateName);
//        }
//
//        @Override
//        public Object handle(Request request, Response response) {
//            StringWriter writer = new StringWriter();
//            try {
//                doHandle(request, response, writer);
//            } catch (Exception e) {
//                e.printStackTrace();
//                response.redirect("/internal_error");
//            }
//            return writer;
//        }
//
//        protected abstract void doHandle(final Request request, final Response response, final Writer writer)
//                throws IOException, TemplateException;
//
//    }
//
//    private void initializeRoutes() throws IOException{
//        get(new FreemarkerBasedRoute("DBview/:pageNr", "IM_DBmainView.html") {
//            @Override
//            protected void doHandle(Request request, Response response, Writer writer) throws IOException, TemplateException {
//                SimpleHash root = new SimpleHash();
//                int pageNr = Integer.parseInt(request.params(":pageNr"));
//                System.err.println("pageNr = " + pageNr);
//                System.err.println("skip = " + Integer.toString((pageNr-1)*150));
//
//                if (pageNr == 1) {
//                    root.put("disable", "w3-disabled");
//                    root.put("previous", "1");
//                    root.put("next", pageNr + 1);
//                }
//                else {
//                    root.put("disable", "");
//                    root.put("previous", pageNr-1);
//                    root.put("next", pageNr+1);
//                }
//
//                root.put("currentPage", pageNr);
//                try {
//                    root.put("row", baza_2010_DAO.DBviewHTMLcode("select * from baza_2010 ORDER BY ID DESC limit "+Integer.toString((pageNr-1)*150)+",150"));
//                } catch (ClassNotFoundException e) {
//                    e.printStackTrace();
//                }
//
//                template.process(root, writer);
//            }
//        });
//
//
//        get(new FreemarkerBasedRoute("/DBview/w3.css","W3.css") {
//            @Override
//            protected void doHandle(Request request, Response response, Writer writer) throws IOException, TemplateException {
//
//            }
//        });
//
//    }
//
//    private Configuration createFreemarkerConfiguration() {
//        Configuration retVal = new Configuration();
//        retVal.setClassForTemplateLoading(WebAppController.class, "/");
//        return retVal;
//    }
//
//    // helper function to get session cookie as string
//    private String getSessionCookie(final Request request) {
//        if (request.raw().getCookies() == null) {
//            return null;
//        }
//        for (Cookie cookie : request.raw().getCookies()) {
//            if (cookie.getName().equals("session")) {
//                return cookie.getValue();
//            }
//        }
//        return null;
//    }
//
//    // helper function to get session cookie as string
//    private Cookie getSessionCookieActual(final Request request) {
//        if (request.raw().getCookies() == null) {
//            return null;
//        }
//        for (Cookie cookie : request.raw().getCookies()) {
//            if (cookie.getName().equals("session")) {
//                return cookie;
//            }
//        }
//        return null;
//    }
//}
