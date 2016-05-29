package View.Renderers.PartCodeRenderers;

import Model.Invoice;
import Model.InvoiceManagerCFG;
import Model.InvoiceManagerDB_DAO;
import View.FreeMarkerTemplate;
import View.Renderer;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import static Model.Helpers.doubleFormat;
import static Model.Helpers.fileExists;
import static Model.Helpers.truncuate;

/**
 * Created by Karol Kistela on 29-Apr-16.
 */
public class DBTable extends FreeMarkerTemplate implements Renderer {
    private final String dbTableFTL = "Parts/DBview/DBTable.ftl";
    private final String dbTableRowFTL = "Parts/DBview/DBTable_row.ftl";
    private InvoiceManagerCFG ImCFG;
    private InvoiceManagerDB_DAO db;
    private String pathToExternalFolder;
    private HashMap<String, String> usersColors;
    private HashMap<String, Integer> invDuplicatesMap;
    private List<String[]> rs;

    private Invoice invoice;

    public static void main(String[] args) throws ClassNotFoundException, TemplateException, SQLException, IOException {
        DBTable t = new DBTable("SELECT * FROM Invoices WHERE ID=51704",1);
        System.out.println(t.render());
    }

    public DBTable(Invoice invoice, HashMap<String,String> usersColors, HashMap<String, Integer> invDuplicatesMap) throws ClassNotFoundException {
        super();
        this.ImCFG = new InvoiceManagerCFG();
        this.pathToExternalFolder = ImCFG.getImExternalFolderPath();
        this.rs = invoice.getResultSet();
        this.usersColors = usersColors;
        this.invDuplicatesMap = invDuplicatesMap;
    }
    public DBTable(List<String[]> rs, HashMap<String,String> usersColors, HashMap<String, Integer> invDuplicatesMap) throws ClassNotFoundException {
        super();
        this.ImCFG = new InvoiceManagerCFG();
        this.pathToExternalFolder = ImCFG.getImExternalFolderPath();
        this.rs = rs;
        this.usersColors = usersColors;
        this.invDuplicatesMap = invDuplicatesMap;
    }

    public DBTable(String sqlQuery, int pageNr) throws ClassNotFoundException, SQLException, FileNotFoundException {
        super();
        this.ImCFG = new InvoiceManagerCFG();
        this.db = new InvoiceManagerDB_DAO(ImCFG.getImDBPath());
        this.pathToExternalFolder = ImCFG.getImExternalFolderPath();
        this.usersColors = this.db.usersColorMap();
        this.invDuplicatesMap = this.db.findDuplicatedInvNr();
        this.rs = this.db.sqlSELECT(sqlQuery, pageNr, true, true);
    }

    @Override
    public String render() throws IOException, TemplateException, ClassNotFoundException, SQLException {
        Template template = getTemplate(this.dbTableFTL);
        String table = getTable();

        replaceMap.put("Table", table);

        return process(template);
    }

    private String getTable() throws IOException, ClassNotFoundException, SQLException, TemplateException {
        String table = "";
        Template template = getTemplate(this.dbTableRowFTL);

        System.err.println("check for duplicates = " + ImCFG.isCheckForInvDuplicates() );
        for (String[] record:rs) {
            String scanPath = pathToExternalFolder + record[6];

            // remove null from row: is it necessary?
//            for (String s:record
//                 ) {
//                if (s == null) {s = "";}
//            }

            if (ImCFG.isCheckForInvDuplicates()) {
                if (invDuplicatesMap.containsKey(record[5])) {
                    replaceMap.put("invNrLink", "<a href=\"/View/InvoiceNR/eq/" + URLEncoder.encode(record[5],"UTF-8") + "/OrderBy/ID/DESC/1\" target=\"_blank\" style=\"color: red\">");
                    replaceMap.put("invNrLink_a", "</a>");
                } else {
                    replaceMap.put("invNrLink", "");
                    replaceMap.put("invNrLink_a", "");
                }
            } else  {
                replaceMap.put("invNrLink", "");
                replaceMap.put("invNrLink_a", "");
            }
            replaceMap.put("rowComment", "<!-- ============================ ID = "+record[0]+" ======================================================================= -->");
            replaceMap.put("rowColor", (record[22].length() == 0) ? "white":record[22]);

            if (this.usersColors.get(record[21]) == null) {             // will it work?
                replaceMap.put("userColor", this.usersColors.get("DB")); // TODO: in sql sdript for creating user db make sure that default record has ID='DB'
            } else {
                replaceMap.put("userColor", this.usersColors.get(record[21]));
            }
            replaceMap.put("ID", record[0]);
            //replaceMap.put("fileExists", (fileExists(scanPath)) ? "file-text":"file-text-o");  // for Inv icon
            replaceMap.put("fileExists", (fileExists(scanPath)) ? "":"black");
            replaceMap.put("BCrow", truncuate(record[1],10));
            replaceMap.put("entryDate", record[2]);
            replaceMap.put("supplier", truncuate(record[4],18));
            replaceMap.put("supplierLink", URLEncoder.encode(record[4],"UTF-8"));
            replaceMap.put("invoiceNR", truncuate(record[5],18));
            replaceMap.put("PO", truncuate(record[7],18));
            try {
                replaceMap.put("netPrice", (doubleFormat(Double.parseDouble(record[8])) + " " + record[9]));
            } catch (Exception e) {
                replaceMap.put("netPrice", "");
            }
            replaceMap.put("authorization", truncuate(record[12],18));
//            replaceMap.put("authorizationLink", "/ID/"+record[0]+"/authEmail\" onClick=\"authEmail"+record[0]+"=window.open('/ID/"+record[0]+"/authEmail','authEmail"+record[0]+"','toolbar=no,location=no,directories=no,status=no,menubar=no,scrollbars=no,resizable=yes,width=640,height=480'); setTimeout(function () { authEmail"+record[0]+".close();}, 500); return false;");
            // if there is authorization email then we have full envelop icon and it will open it, else we will open outlook with mailto auth contact
//            replaceMap.put("emailLink", (fileExists(emailAuthPath)) ? ("href=\"/ID/"+record[0]+"/authEmail\" onClick=\"authEmail"+record[0]+"=window.open('/ID/"+record[0]+"/authEmail','authEmail"+record[0]+"','toolbar=no,location=no,directories=no,status=no,menubar=no,scrollbars=no,resizable=yes,width=640,height=480'); setTimeout(function () { authEmail"+record[0]+".close();}, 500); return false;"):("href=\"mailto: " + record[12]));
//            replaceMap.put("email", (fileExists(emailAuthPath)) ? "<i class=\"fa fa-envelope\" aria-hidden=\"true\"></i>":"<i class=\"fa fa-envelope-o\" aria-hidden=\"true\"></i>");
            // new option, opens folder with inv scan and msg (if already sent)
//            replaceMap.put("emailLink", "/ID/"+record[0]+"/Folder\" onClick=\"Folder"+record[0]+"=window.open('/ID/"+record[0]+"/Folder','Folder"+record[0]+"','toolbar=no,location=no,directories=no,status=no,menubar=no,scrollbars=no,resizable=yes,width=640,height=480'); setTimeout(function () { Folder"+record[0]+".close();}, 500); return false;");
            replaceMap.put("email", "<i class=\"fa fa-folder-open-o\" aria-hidden=\"true\"></i>");

            replaceMap.put("GR", truncuate(record[17],25));
            replaceMap.put("processStage", record[24]);
            // Details Table:
            replaceMap.put("BC", record[1]);
            replaceMap.put("SupplierDetails", record[4]);
            replaceMap.put("InvNrDetails",record[5]);
            replaceMap.put("PODetails",record[7]);
            replaceMap.put("GRDetails",record[17]);
            replaceMap.put("contactGenpact", record[3]);
            replaceMap.put("invDate", record[10]);
            replaceMap.put("emailSubject", record[11]);
            replaceMap.put("authDate", record[13]);
            replaceMap.put("authReplyDate", record[14]);
            replaceMap.put("endDate", record[16]);
            replaceMap.put("genpactLastReply", record[18]);
            replaceMap.put("userComments", record[19]);
            replaceMap.put("status", record[20]);
            replaceMap.put("processStatus", (record[23] == null) ? "":record[23]);

            template.process(replaceMap,retVal);

            table = retVal.toString();
        }
        clearData();
        return table;
    }
}
