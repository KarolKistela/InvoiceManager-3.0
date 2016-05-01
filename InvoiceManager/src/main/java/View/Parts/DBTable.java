package View.Parts;

import Model.InvoiceManagerCFG;
import Model.InvoiceManagerDB_DAO;
import View.FreeMarkerTemplate;
import View.Renderer;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.SplittableRandom;

import static Model.Helpers.doubleFormat;
import static Model.Helpers.fileExists;
import static Model.Helpers.truncuate;

/**
 * Created by Karol Kistela on 29-Apr-16.
 */
public class DBTable extends FreeMarkerTemplate implements Renderer {
    private InvoiceManagerCFG ImCFG;
    private InvoiceManagerDB_DAO db;
    private String pathToExternalFolder;
    private HashMap<String, String> usersColors;
    private HashMap<String, Integer> invDuplicatesMap;
    private List<String[]> rs;

    public static void main(String[] args) throws ClassNotFoundException, TemplateException, SQLException, IOException {
        DBTable t = new DBTable("SELECT * FROM Invoices ",1);
        System.out.println(t.render());
    }

    public DBTable(String sqlQuery, int pageNr) throws ClassNotFoundException, SQLException {
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
        Template template = getTemplate("Parts/DBview/DBTable.ftl");
        String table = getTable();

        replaceMap.put("Table", table);

        return process(template);
    }

    private String getTable() throws IOException, ClassNotFoundException, SQLException, TemplateException {
        String table = "";
        Template template = getTemplate("Parts/DBview/DBTable_row.ftl");

        System.err.println("check for duplicates = " + ImCFG.isCheckForInvDuplicates() );
        for (String[] record:rs) {
            String scanPath = pathToExternalFolder + record[6];
            String emailAuthPath = pathToExternalFolder + record[15];

            // remove null from row:
            for (String s:record
                 ) {
                if (s == null) {s = "";}
            }

            if (ImCFG.isCheckForInvDuplicates()) {
                if (invDuplicatesMap.containsKey(record[5])) {
                    replaceMap.put("invNrLink", "<a href=\"/ID/" + record[0] + "/invNr/1\" target=\"_blank\" style=\"color: red\">");
                    replaceMap.put("invNrLink_a", "</a>");
                } else {
                    replaceMap.put("invNrLink", "");
                    replaceMap.put("invNrLink_a", "");
                }
            } else  {
                replaceMap.put("invNrLink", "");
                replaceMap.put("invNrLink_a", "");
            }
            replaceMap.put("rowComment", "<! -- ===================================== Row for ID = " + record[0] + "  ===================================== -->");
            replaceMap.put("rowColor", (record[22].length() == 0) ? "white":record[22]);
            replaceMap.put("userColor", this.usersColors.get(record[21]));
            replaceMap.put("ID", record[0]);
            replaceMap.put("fileExists", (fileExists(scanPath)) ? "file-text":"file-text-o");
            replaceMap.put("entryDate", record[2]);
            replaceMap.put("supplier", truncuate(record[4],20));
            replaceMap.put("supplierLink", record[4]);
            replaceMap.put("invoiceNR", truncuate(record[5],20));
            replaceMap.put("PO", record[7]);
            // Net Price: 950 => 950.00 EUR, 1083.4 => 1083.40 EUR, 650.99 => 650.99 EUR
            replaceMap.put("netPrice", doubleFormat(record[8]) + " " + record[9]);
            replaceMap.put("authorization", truncuate(record[12],20));
            replaceMap.put("authorizationLink", record[12]);
            // if there is authorization email then we have full envelop icon and it will open it, else we will open outlook with mailto auth contact
            replaceMap.put("emailLink", (fileExists(emailAuthPath)) ? ("href=\"/ID/"+record[0]+"/authEmail\" onClick=\"authEmail"+record[0]+"=window.open('/ID/"+record[0]+"/authEmail','authEmail"+record[0]+"','toolbar=no,location=no,directories=no,status=no,menubar=no,scrollbars=no,resizable=yes,width=640,height=480'); setTimeout(function () { authEmail"+record[0]+".close();}, 500); return false;"):("href=\"mailto: " + record[12]));
            replaceMap.put("email", (fileExists(emailAuthPath)) ? "<i class=\"fa fa-envelope\" aria-hidden=\"true\"></i>":"<i class=\"fa fa-envelope-o\" aria-hidden=\"true\"></i>");
            replaceMap.put("GR", truncuate(record[17],30));
            replaceMap.put("processStage", record[24]);
            replaceMap.put("BC", record[1]);
            // Details Table:
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
