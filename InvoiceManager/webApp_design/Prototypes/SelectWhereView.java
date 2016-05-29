//package View.Renderers;
//
//import Model.InvoiceManagerDB_DAO;
//import View.FreeMarkerTemplate;
//import View.Renderer;
//import View.Renderers.PartCodeRenderers.DBTable;
//import View.Renderers.PartCodeRenderers.Header;
//import View.Renderers.PartCodeRenderers.Style;
//import freemarker.template.Template;
//import freemarker.template.TemplateException;
//import spark.Request;
//
//import java.io.FileNotFoundException;
//import java.io.IOException;
//import java.sql.SQLException;
//import java.util.HashMap;
//import java.util.List;
//
//import static Model.Helpers.sqlQueryConstructor;
//
///**
// * Created by Karol Kistela on 30-Apr-16.
// */
//public class SelectWhereView extends FreeMarkerTemplate implements Renderer {
//    private final String viewTitle = "Filter view";
//    private final String ftlFile = "DBview.ftl";
//    private boolean tabHeader = true;
//    private final boolean tabHeaderWithSort = true;
//    private final boolean pagination = true;
//    private final int menuButtonActive = 3;
//    private String rout;
//    private int pageNr;
//    private int records;
//    private String sqlQuery;
//    private List<String[]> resultSet;
//    private HashMap<String, String> usersColors;
//    private HashMap<String, Integer> invDuplicatesMap;
//
//    public SelectWhereView(Request request) throws ClassNotFoundException {
//        super();
//        this.rout = request.pathInfo().substring(0,request.pathInfo().lastIndexOf("/")+1);
//        this.pageNr = Integer.parseInt(request.params("pageNr").replace(",",""));
//
//        try {
//            System.out.println("********View.Renderers.SelectWhere ROUT: " + this.rout + this.pageNr + " ********");
//            InvoiceManagerDB_DAO db = new InvoiceManagerDB_DAO();
//            this.sqlQuery = sqlQueryConstructor(request);
//            this.resultSet = db.sqlSELECT(sqlQuery, pageNr, true, true);    // get resultSet of query
//            this.usersColors = db.usersColorMap();                          // get colors
//            this.records = db.sqlCOUNT(sqlQuery.replace("*", "count(ID)"));
//            if (ImCFG.isCheckForInvDuplicates()) {                          // if true check for duplicates in invoice nrs
//                this.invDuplicatesMap = db.findDuplicatedInvNr();
//            } else {
//                this.invDuplicatesMap = new HashMap();
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//            this.resultSet = null;
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//            this.resultSet = null;
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
//        replaceMap.put("DBTable", new DBTable(this.resultSet,
//                                              this.usersColors,
//                                              this.invDuplicatesMap).render());
//        replaceMap.put("Footer", getTemplate("/Parts/Footer.ftl").toString());
//
//        return process(template);
//    }
//}
