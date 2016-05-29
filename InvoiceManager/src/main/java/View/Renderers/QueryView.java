package View.Renderers;

import Model.InvoiceManagerDB_DAO;
import View.FreeMarkerTemplate;
import View.Renderer;
import View.Renderers.PartCodeRenderers.DBTable;
import View.Renderers.PartCodeRenderers.Header;
import View.Renderers.PartCodeRenderers.Style;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import spark.Request;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import static Controller.Controller.PORT;
import static Model.Helpers.sqlQueryConstructor;
import static Model.Helpers.sqlQueryConstructor2;
import static Model.Helpers.sqlQueryConstructor3;

/**
 * Created by Karol Kistela on 26-May-16.
 */
public class QueryView extends FreeMarkerTemplate implements Renderer {
    private String viewTitle;
    private final String ftlFile = "DBview.ftl";
    private boolean tabHeader = true;
    private boolean tabHeaderWithSort = true;
    private final boolean pagination = true;
    private int menuButtonActive;
    private String rout;
    private int pageNr;
    private int records;
    private String sqlQuery;
    private List<String[]> resultSet;
    private HashMap<String, String> usersColors;
    private HashMap<String, Integer> invDuplicatesMap;

    public QueryView(Request request) throws ClassNotFoundException {
        super();
        this.rout = request.url().substring(request.url().indexOf(PORT.toString().replace(",",""))+4,request.url().lastIndexOf("/")+1);
        this.pageNr = Integer.parseInt(request.params("pageNr").replace(",",""));
        if (request.params("columnName").equals("ID") & request.params("sign").equals("gte") & request.params("value").equals("0")) {
            this.menuButtonActive = 1;
            this.viewTitle = "Main view";
        } else {
            this.menuButtonActive = 3;
            this.viewTitle = "Filter view";
        }

        try {
            System.out.println("********View.Renderers.SelectWhere ROUT: " + this.rout + this.pageNr + " ********");
            InvoiceManagerDB_DAO db = new InvoiceManagerDB_DAO(ImCFG.getImDBPath());
            this.sqlQuery = sqlQueryConstructor2(request);
            this.records = db.sqlCOUNT(sqlQuery.replace("*", "count(ID)"));
            this.resultSet = db.sqlSELECT2(sqlQuery, pageNr, false, true, this.records);       // get resultSet of query
            this.usersColors = db.usersColorMap();                              // get colors
            if (ImCFG.isCheckForInvDuplicates()) {                              // if true check for duplicates in invoice nrs
                this.invDuplicatesMap = db.findDuplicatedInvNr();
            } else {
                this.invDuplicatesMap = new HashMap();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            this.resultSet = null;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            this.resultSet = null;
        }
    }

    public QueryView(Request request, Integer filterNR) throws ClassNotFoundException {
        super();
        List<String[]> filters = ImCFG.getFilters();
        this.rout = request.url().substring(request.url().indexOf(PORT.toString().replace(",",""))+4,request.url().lastIndexOf("/")+1);
        this.pageNr = Integer.parseInt(request.params("pageNr").replace(",",""));
        this.menuButtonActive = 3;
        this.viewTitle = filters.get(filterNR)[1];
        if (filters.get(filterNR)[3].equals("1")) {
            this.tabHeaderWithSort = false;
        } else {
            this.tabHeaderWithSort = true;
        }

        try {
            System.out.println("********View.Renderers.SelectWhere ROUT: " + this.rout + this.pageNr + " ********");
            InvoiceManagerDB_DAO db = new InvoiceManagerDB_DAO(ImCFG.getImDBPath());
            this.sqlQuery = sqlQueryConstructor3(request, filterNR, filters);
            this.records = db.sqlCOUNT(sqlQuery.replace("*", "count(ID)"));
            this.resultSet = db.sqlSELECT2(sqlQuery, pageNr, false, true, this.records);       // get resultSet of query
            this.usersColors = db.usersColorMap();                              // get colors
            if (ImCFG.isCheckForInvDuplicates()) {                              // if true check for duplicates in invoice nrs
                this.invDuplicatesMap = db.findDuplicatedInvNr();
            } else {
                this.invDuplicatesMap = new HashMap();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            this.resultSet = null;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            this.resultSet = null;
        }
    }
    @Override
    public String render() throws IOException, TemplateException, ClassNotFoundException, SQLException {
        Template template = getTemplate(this.ftlFile);

        replaceMap.put("Style", new Style().render());
        replaceMap.put("Header", new Header(this.menuButtonActive,
                                            this.rout,
                                            this.pageNr,
                                            this.records,
                                            this.viewTitle,
                                            this.tabHeader,
                                            this.tabHeaderWithSort,
                                            this.pagination).render());
        replaceMap.put("DBTable", new DBTable(this.resultSet,
                                              this.usersColors,
                                              this.invDuplicatesMap).render());
        replaceMap.put("Footer", getTemplate("/Parts/Footer.ftl").toString());

        return process(template);
    }
}
