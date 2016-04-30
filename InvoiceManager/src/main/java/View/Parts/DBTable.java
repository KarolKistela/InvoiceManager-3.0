package View.Parts;

import Model.InvoiceManagerCFG;
import Model.InvoiceManagerDB_DAO;
import View.FreeMarkerTemplate;
import View.Renderer;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.IOException;
import java.io.StringWriter;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import static Model.Helpers.fileExists;

/**
 * Created by Karol Kistela on 29-Apr-16.
 */
public class DBTable extends FreeMarkerTemplate implements Renderer {
    private String sqlQuery;
    private int pageNr;
    private HashMap<String, String> usersColors = new HashMap<>();
    private InvoiceManagerCFG ImCFG = new InvoiceManagerCFG();

    public static void main(String[] args) throws ClassNotFoundException, TemplateException, SQLException, IOException {
        DBTable t = new DBTable("SELECT * FROM Invoices ",1);

        System.out.println(t.render());

//        for (String s: t.usersColors.keySet()
//             ) {
//            System.out.print(s + ": ");
//            System.out.println(t.usersColors.get(s));
//        }
    }

    public DBTable(String sqlQuery, int pageNr) throws ClassNotFoundException, SQLException {
        super();
        this.sqlQuery = sqlQuery;
        this.pageNr = pageNr;

        //load users colors into hashMap - it is easier to get them from hashMap then retrive them from DB for each row
        List<String[]> users = new InvoiceManagerDB_DAO().sqlSELECT("SELECT * FROM Users",1,false,false);
        for (String[] record:users) {
            this.usersColors.put(record[0],record[2]);
        }
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
        String pathToExternalFolder = ImCFG.getImExternalFolderPath();
        Template template = getTemplate("Parts/DBview/DBTable_row.ftl");
        List<String[]> rs = new InvoiceManagerDB_DAO().sqlSELECT(sqlQuery,pageNr,true,true);

        for (String[] record:rs) {
            String scanPath = pathToExternalFolder + record[6];
            String emailAuthPath = pathToExternalFolder + record[15];

            if (ImCFG.isCheckForInvDuplicates()) {
                System.err.println("check for duplicates = " + ImCFG.isCheckForInvDuplicates() );
                int invNrCount = new InvoiceManagerDB_DAO().sqlCOUNT("SELECT count(ID) FROM Invoices WHERE InvoiceNR=" + "'" + record[5] + "'");

                if (invNrCount >= 2) {
                    System.out.println("Powtorzony nr FV! dla id = " + record[0]);
                    replaceMap.put("invNrLink", "<a href=\"/ID/" + record[0] + "/invNr/1\" target=\"_blank\" style=\"color: red\">");
                    replaceMap.put("invNrLink_a", "</a>");
                } else {
                    replaceMap.put("invNrLink", "");
                    replaceMap.put("invNrLink_a", "");
                }
            } else  {
                System.err.println("check for duplicates = " + ImCFG.isCheckForInvDuplicates() );
                replaceMap.put("invNrLink", "");
                replaceMap.put("invNrLink_a", "");
            }

            replaceMap.put("rowComment", "<! -- ===================================== Row for ID = " + record[0] + "  ===================================== -->");
            replaceMap.put("rowColor", (record[22].length() == 0) ? "white":record[22]);
            replaceMap.put("userColor", this.usersColors.get(record[21]));
            replaceMap.put("ID", record[0]);
            replaceMap.put("fileExists", (fileExists(scanPath)) ? "file-text":"file-text-o");
            replaceMap.put("entryDate", record[2]);
            replaceMap.put("supplier", this.truncuate(record[4],20));
            replaceMap.put("supplierLink", record[4]);
            replaceMap.put("invoiceNR", this.truncuate(record[5],20));
            replaceMap.put("PO", record[7]);
            // Net Price: 950 => 950.00 EUR, 1083.4 => 1083.40 EUR, 650.99 => 650.99 EUR
            replaceMap.put("netPrice", this.doubleFormat(record[8]) + " " + record[9]);
            replaceMap.put("authorization", this.truncuate(record[12],20));
            replaceMap.put("authorizationLink", record[12]);
            // if there is authorization email then we have full envelop icon and it will open it, else we will open outlook with mailto auth contact
//            replaceMap.put("emailLink", record[15].contains(".msg") ? ("href=\"/ID/"+record[0]+"/authEmail\" onClick=\"authEmail"+record[0]+"=window.open('/ID/"+record[0]+"/authEmail','authEmail"+record[0]+"','toolbar=no,location=no,directories=no,status=no,menubar=no,scrollbars=no,resizable=yes,width=640,height=480'); setTimeout(function () { authEmail"+record[0]+".close();}, 500); return false;"):("href=\"mailto: " + record[12]));
//            replaceMap.put("email", record[15].contains(".msg") ? "<i class=\"fa fa-envelope\" aria-hidden=\"true\"></i>":"<i class=\"fa fa-envelope-o\" aria-hidden=\"true\"></i>");

            replaceMap.put("emailLink", (fileExists(emailAuthPath)) ? ("href=\"/ID/"+record[0]+"/authEmail\" onClick=\"authEmail"+record[0]+"=window.open('/ID/"+record[0]+"/authEmail','authEmail"+record[0]+"','toolbar=no,location=no,directories=no,status=no,menubar=no,scrollbars=no,resizable=yes,width=640,height=480'); setTimeout(function () { authEmail"+record[0]+".close();}, 500); return false;"):("href=\"mailto: " + record[12]));
            replaceMap.put("email", (fileExists(emailAuthPath)) ? "<i class=\"fa fa-envelope\" aria-hidden=\"true\"></i>":"<i class=\"fa fa-envelope-o\" aria-hidden=\"true\"></i>");


            replaceMap.put("GR", this.truncuate(record[17],30));
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
            // TODO: remove null from Invoices Tab
            replaceMap.put("processStatus", (record[23] == null) ? "":record[23]);

            template.process(replaceMap,retVal);

            table = retVal.toString();
        }
        clearData();
        return table;
    }

    private String truncuate(String s, int maxLength) {
        if (s.length() > maxLength) {
            return  (s.substring(0,maxLength-3)+"...");
        } else {
            return s;
        }
    }

    private String doubleFormat(String s) {
        if (s.indexOf(".") == -1) {
            return s+".00";
        } else if (s.indexOf(".") == 2) {
            return s+"0";
        } else {
            return s;
        }
    }
}
