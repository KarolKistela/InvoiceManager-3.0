//package View.Renderers;
//
//import Model.InvoiceManagerDB_DAO;
//import View.FreeMarkerTemplate;
//import View.Renderer;
//import View.Renderers.PartCodeRenderers.DBTable;
//import freemarker.template.Template;
//import freemarker.template.TemplateException;
//import View.Renderers.PartCodeRenderers.Header;
//import View.Renderers.PartCodeRenderers.Style;
//import spark.Request;
//
//import java.io.FileNotFoundException;
//import java.io.IOException;
//import java.sql.SQLException;
//import java.util.HashMap;
//import java.util.List;
//
///**
// * Created by Karol Kistela on 27-Apr-16.
// *
// * This class render HTML page for viewing results of SELECT query (1 or more rows). It covers both DB main view and Filter views.
// * Per page will be displayed limited nr of rows (according to settings). However this is carry out by htmlFactory
// */
//public class DBview extends FreeMarkerTemplate implements Renderer {
//    private final String viewTitle = "DB main view";
//    private final String ftlFile = "DBview.ftl";
//    private final boolean tabHeader = true;
//    private final boolean tabHeaderWithSort = true;
//    private final boolean pagination = true;
//    private final int menuButtonActive = 1;
//    private String sqlQuery = "SELECT * FROM Invoices ";
//    private String rout;
//    private int pageNr;
//    private int records;
//    private List<String[]> invoices;
//    private HashMap<String, String> usersColors;
//    private HashMap<String, Integer> invDuplicatesMap;
//
//    public DBview(Request request) throws ClassNotFoundException {
//        super();
//        this.rout = request.pathInfo().substring(0,request.pathInfo().lastIndexOf("/")+1);
//        this.pageNr = Integer.parseInt(request.params("pageNr").replace(",",""));
//
//        try {
//            System.out.println("********View.Renderers.DBView ROUT: " + this.rout + this.pageNr + " ********");
//            InvoiceManagerDB_DAO db = new InvoiceManagerDB_DAO();
//            this.invoices = db.sqlSELECT(sqlQuery, pageNr, true, true);    // get invoices of query
//            this.usersColors = db.usersColorMap();                          // get colors
//            this.records = db.sqlCOUNT(sqlQuery.replace("*", "count(ID)"));  // get how many records will return query
//            if (ImCFG.isCheckForInvDuplicates()) {                          // if true check for duplicates in invoice nrs
//                this.invDuplicatesMap = db.findDuplicatedInvNr();
//            } else {
//                this.invDuplicatesMap = new HashMap();
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//            this.invoices = null;
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//            this.invoices = null;
//        }
//    }
//
//    @Override
//    public String render() throws IOException, TemplateException, ClassNotFoundException, SQLException {
//        Template template = getTemplate(this.ftlFile);
//
//        replaceMap.put("Style", new Style().render());
//        replaceMap.put("Header", new Header(this.menuButtonActive,
//                                            this.rout,
//                                            this.pageNr,
//                                            this.records,
//                                            this.viewTitle,
//                                            this.tabHeader,
//                                            this.tabHeaderWithSort,
//                                            this.pagination).render());
//        replaceMap.put("DBTable", new DBTable(this.invoices,
//                                              this.usersColors,
//                                              this.invDuplicatesMap).render());
//        replaceMap.put("Footer", getTemplate("/Parts/Footer.ftl").toString());
//
//        return process(template);
//    }
//}
