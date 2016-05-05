package View.Renderers;

import Model.Invoice;
import Model.InvoiceManagerDB_DAO;
import View.FreeMarkerTemplate;
import View.Renderer;
import View.Renderers.PartCodeRenderers.DBTable;
import View.Renderers.PartCodeRenderers.Header;
import View.Renderers.PartCodeRenderers.IDinputForm;
import View.Renderers.PartCodeRenderers.Style;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import spark.Request;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;


/**
 * Created by Karol Kistela on 03-May-16.
 */
public class IDView extends FreeMarkerTemplate implements Renderer {
    private final String viewTitle = "View ID";
    private final String ftlFile = "IDView.ftl";
    private boolean tabHeader = true;
    private final boolean pagination = true;
    private final int menuButtonActive = 0;
    private String rout;
    private Integer pageNr;
    private Invoice invoice;
    private HashMap<String, String> usersColors;
    private HashMap<String, Integer> invDuplicatesMap;

    public static void main(String[] args) throws ClassNotFoundException {
        IDView invV = new IDView(51500);

        try {
            System.out.println(invV.render());
            System.out.println(invV.invDuplicatesMap.get("jest"));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TemplateException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public IDView(int pageNr) throws ClassNotFoundException {
        super();
        this.rout = "/ID/";
        this.pageNr = pageNr;
        try {
            System.out.println("********View.Renderers.IDView.IDView(Request request)********");
            InvoiceManagerDB_DAO db = new InvoiceManagerDB_DAO();
            this.invoice = db.sqlSELECTid(pageNr);                  // get Invoice
            this.usersColors = db.usersColorMap();                  // get colors
            if (ImCFG.isCheckForInvDuplicates()) {                  // if true check for duplicates in invoice nrs
                this.invDuplicatesMap = db.findDuplicatedInvNr();
            } else {
                this.invDuplicatesMap = new HashMap<>();
            }


        } catch (SQLException e) {
            e.printStackTrace();
            this.invoice = null;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            this.invoice = null;
        }
    }

    public IDView(Request request) throws ClassNotFoundException {
        super();
        this.rout = request.pathInfo().substring(0,request.pathInfo().lastIndexOf("/")+1);
        this.pageNr = Integer.parseInt(request.params("id").replace(",",""));
        try {
            System.out.println("********View.Renderers.IDView.IDView(Request request ROUT: " + this.rout + this.pageNr + " ********");
            InvoiceManagerDB_DAO db = new InvoiceManagerDB_DAO();
            this.invoice = db.sqlSELECTid(pageNr);                  // get Invoice
            this.usersColors = db.usersColorMap();                  // get colors
            if (ImCFG.isCheckForInvDuplicates()) {                  // if true check for duplicates in invoice nrs
                this.invDuplicatesMap = db.findDuplicatedInvNr();
            } else {
                this.invDuplicatesMap = new HashMap<>();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            this.invoice = null;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            this.invoice = null;
        }
    }

    @Override
    public String render() throws IOException, TemplateException, ClassNotFoundException, SQLException {
        Template template = getTemplate(this.ftlFile);

        replaceMap.put("Style", new Style().render());
        replaceMap.put("Header", new Header(this.menuButtonActive,
                                            this.rout,
                                            this.pageNr,
                                            this.viewTitle,
                                            this.tabHeader,
                                            this.pagination).render());
        if (this.invoice == null || this.invoice.getID() ==0) {
            replaceMap.put("DBTable", "<h1 style=\"margin-top: 75px; padding-left: 50px\">error</h1>");
            replaceMap.put("InputForm", "<h5 style=\"padding-left: 50px\">Record ID = "+this.pageNr+" doesn't exists in database</h5>");
        } else {
            replaceMap.put("DBTable", new DBTable(this.invoice,
                                                  this.usersColors,
                                                  this.invDuplicatesMap).render());
            replaceMap.put("InputForm", new IDinputForm(invoice).render());//"hello world!");
        }

        template.process(replaceMap,retVal);

        return retVal.toString();
    }
}


