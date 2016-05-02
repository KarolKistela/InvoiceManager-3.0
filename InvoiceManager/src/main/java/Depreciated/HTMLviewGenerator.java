//package Depreciated;
//
//import Model.InvoiceManagerCFG;
//import Model.InvoiceManagerDB_DAO;
//import freemarker.template.Configuration;
//import freemarker.template.SimpleHash;
//import freemarker.template.Template;
//import freemarker.template.TemplateException;
//
//import java.io.IOException;
//import java.io.StringWriter;
//
///**
// * Created by Karol Kistela on 14-Apr-16.
// * TODO: move out tempates of src code, so templates can be changed without recompiling the code - low priority
// */
//public class HTMLviewGenerator {
//    private final Configuration cfg = new Configuration();
//    private final InvoiceManagerCFG ImCFG = new InvoiceManagerCFG();
//    private final InvoiceManagerDB_DAO InvoiceManagerDB_DAO;
//
////    public static void main(String[] args) throws ClassNotFoundException, IOException, TemplateException {
////        HTMLviewGenerator generator = new HTMLviewGenerator();
////
////        String table = generator.InvoiceManagerDB_DAO.getTable("SELECT * FROM Invoices ORDER BY ID DESC ", 1, 100, 87.5);
////
////        System.out.println(table);
////    }
//
//    public HTMLviewGenerator() throws ClassNotFoundException {
//        cfg.setClassForTemplateLoading(HTMLviewGenerator.class, "/FTL templates/");
//        InvoiceManagerDB_DAO = new InvoiceManagerDB_DAO(this.ImCFG.getImDBPath());
//        // TODO: catch InvoiceManagerDB_DAO constructor and in case off connection problem load default values (write method in InvoiceManagerDB_DAO class)
//    }
//
////    public String getDBview(Request request, Response response) throws IOException, TemplateException, ClassNotFoundException, SQLException {
////        int pageNr = Integer.parseInt(request.params("pageNR"));
////        ImCFG.setTotalNrOfPages((InvoiceManagerDB_DAO.sqlCOUNT("SELECT count(*) FROM Invoices")/ImCFG.getRowsPerPage() + 1));
////        SimpleHash replaceMap = new SimpleHash();
////        StringWriter retVal = new StringWriter();
////        Template template = cfg.getTemplate("DB.ftl");
////
////        replaceMap.put("style", this.getStyle());
////        replaceMap.put("header2rowed", this.getHeader2rowed(
////                "DB main view",                                     // view name to display in top bar
////                1,                                                  // which icon from top bar to activate
////                pageNr,                                             // what pageNr are we at, required for pagination
////                ImCFG.getTotalNrOfPages()));                        // total nr of pages, depends on query and ImCFG.nrOfColumnsToDisplay
////        replaceMap.put("table", InvoiceManagerDB_DAO.getTable(
////                "SELECT * FROM Invoices ORDER BY ID DESC ",         // SQL query, it will add LIMIT skip,nrOfrows
////                pageNr,                                             // what pageNr are we at, required for LIMIT - skip = (pageNr - 1)*rowsPerPage
////                ImCFG.getRowsPerPage(),
////                ImCFG.getTableWidth()));
////
////        template.process(replaceMap, retVal);
////
////        return retVal.toString();
////    }
//
////
//
//    private StringWriter getStyle() throws IOException, TemplateException {
//        SimpleHash replaceMap = new SimpleHash();
//        StringWriter HEAD = new StringWriter();
//        Template template = cfg.getTemplate("/PartsRenderers/style.ftl");
//
//        replaceMap.put("backGroundColor", this.ImCFG.getBackgroundColor());
//        replaceMap.put("IMcolumnClasses", this.getColumnStyleClasses());
//
//        template.process(replaceMap,HEAD);
//
//        return HEAD;
//    }
//
//    private StringWriter getHeader1rowed(String viewName, int activateMenuItemNR, boolean pagination, int pageNR) throws IOException, TemplateException {
//        SimpleHash replaceMap = new SimpleHash();
//        StringWriter header2rowed = new StringWriter();
//        Template template = cfg.getTemplate("/PartsRenderers/header1rowed.ftl");
//
//        if (pagination) {
//            replaceMap.put("previous", (pageNR == 1) ? "#" : pageNR - 1);
//            replaceMap.put("pagination", pageNR);
//            replaceMap.put("next", pageNR + 1);
//        } else {
//            replaceMap.put("previous", "#");
//            replaceMap.put("pagination", "");
//            replaceMap.put("next", "#");
//        }
//        replaceMap.put("IMmenuActive1",(activateMenuItemNR == 1)? "IM-menu-active":"");
//        replaceMap.put("IMmenuActive2",(activateMenuItemNR == 2)? "IM-menu-active":"");
//        replaceMap.put("IMmenuActive3",(activateMenuItemNR == 3)? "IM-menu-active":"");
//        replaceMap.put("viewTitle", viewName);
//
//        template.process(replaceMap, header2rowed);
//
//        return header2rowed;
//    }
//
//    private StringWriter getHeader2rowed(String viewName, int activateMenuItemNR, int pageNr, int totalPages) throws IOException, TemplateException {
//        SimpleHash replaceMap = new SimpleHash();
//        StringWriter header2rowed = new StringWriter();
//        Template template = cfg.getTemplate("/PartsRenderers/header2rowed.ftl");
//
//        replaceMap.put("previous",(pageNr == 1) ? "#" : pageNr - 1);
//        replaceMap.put("pagination", (pageNr + " / " + totalPages));
//        replaceMap.put("next", pageNr + 1);
//        replaceMap.put("IMmenuActive1",(activateMenuItemNR == 1)? "IM-menu-active":"");
//        replaceMap.put("IMmenuActive2",(activateMenuItemNR == 2)? "IM-menu-active":"");
//        replaceMap.put("IMmenuActive3",(activateMenuItemNR == 3)? "IM-menu-active":"");
//        replaceMap.put("viewTitle", viewName);
//        replaceMap.put("tabHeaderRows", this.getTabHeaderRowHTML());
//        replaceMap.put("tableWidth", ImCFG.getTableWidth());
//        replaceMap.put("headerWidth", 100 - ImCFG.getTableWidth());
//
//        template.process(replaceMap, header2rowed);
//
//        return header2rowed;
//    }
//
//    private String getTabHeaderRowHTML(){
//        String retVal = "";
//        //  TODO: use template for this not String
//        String tabRowTemplate =
//        "                <td class=\"IM-columnClass\">columnName</td>\n";
//
//        for (Object K:this.ImCFG.getSetOfColumns()) {
//            retVal = retVal + tabRowTemplate;
//            retVal = retVal.replace("columnClass", (CharSequence) K)
//                        .replace("columnName", (CharSequence) K);
//            }
//        retVal = retVal.replace("\">Details</","\"></"); // remove details column name, its not needed in this place
//        return retVal;
//    }
//
//    private String getColumnStyleClasses() {
//        String retVal = "";
//        //  TODO: use template for this not String
//        String columnClassTemplate =
//        "        .IM-columnName {\n" +
//        "            width: columnWidth%\n" +
//        "        }\n";
//
//        for (Object K:this.ImCFG.getSetOfColumns()) {
//            retVal = retVal + columnClassTemplate;
//            retVal = retVal.replace("columnWidth", String.valueOf(this.ImCFG.getColumnWidth(K)))
//                            .replace("columnName", (CharSequence) K);
//        }
//
//        return retVal;
//    }
//}
//
////    private String getTableRow(ResultSet rs) throws IOException, TemplateException, SQLException {
////        SimpleHash replaceMap = new SimpleHash();
////        StringWriter tableRow = new StringWriter();
////        Template template = cfg.getTemplate("/PartsRenderers/tableRow.ftl");
////
////        // TODO: mayby some list of ftl variables and: foreach (v: list) { replaceMap(V,rs.getString(V) } ? ...
////        // var need to be the same as column name and since it ends up in HTML it doesnt care if it is String
////
//////        userColor - color of left border, depend on which user added record to getDBview
////        replaceMap.put("userColor","red");
//////        rowColor - row color default ""
////        replaceMap.put("rowColor","");
//////        ID - from InvoiceManagerDB.sql
////        replaceMap.put("ID",rs.getString(1));
//////        barCode - BC for ID, from InvoiceManagerDB.sql
////        replaceMap.put("barCode","4815694597");
//////        scan - "barcode" for inv from scanning center, "print" for inv from floor scaner
////        replaceMap.put("scan","barcode");
//////        EntryDate - from InvoiceManagerDB.sql converter 23 apr, 2016 <-> 20160423
////        replaceMap.put("EntryDate","23 apr, 2016");
//////        tableWidth - from ImCFG
////        replaceMap.put("tableWidth",ImCFG.getTableWidth());
//////        headerWidth - 100 - tableWidth
////        replaceMap.put("headerWidth",100-ImCFG.getTableWidth());
//////        Supplier
////        replaceMap.put("Supplier","ACME");
//////        InvoiceNR
////        replaceMap.put("InvoiceNR","12/A45/2016");
//////        PO
////        replaceMap.put("PO","PO55124457");
//////        NetPrice
////        replaceMap.put("NetPrice","1200.00 EUR");
//////        Authorization
////        replaceMap.put("Authorization","Karol.Kistela@delphi.com");
//////         GR
////        replaceMap.put("GR","45843186, 156863154, 86631216");
////
////        template.process(replaceMap, tableRow);
////
////        return tableRow.toString();
////    }