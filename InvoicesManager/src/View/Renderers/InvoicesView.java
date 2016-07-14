package View.Renderers;

import Controller.Controller;
import Model.DAO.DAO_InvoicesFullView;
import Model.Invoice;
import Model.Rout;
import Model.SQL;
import Model.User;
import View.FreeMarkerTemplate;
import View.Renderer;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import spark.Request;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.sql.SQLException;

import static Controller.Controller.FINANCE_VIEW;
import static Model.Helpers.fileExists;
import static Model.Helpers.truncuate;

/**
 * Created by Karol Kistela on 20-Jun-16.
 */
public class InvoicesView extends FreeMarkerTemplate implements Renderer {
    private final String ftlFile = "FTL templates/InvoicesView/InvoicesView.ftl";
    private final String headerFTL = "FTL templates/InvoicesView/Header.ftl";
    private final String headerTableHeaderFTL = "FTL templates/InvoicesView/Header_tableHeader.ftl";
    private final String headerTableHeaderWithoutSortFTL = "FTL templates/InvoicesView/Header_tableHeaderWithoutSort.ftl";
    private final String dbTableFTL = "FTL templates/InvoicesView/DBTable.ftl";
    private final String dbTableRowFTL = "FTL templates/InvoicesView/DBTable_row.ftl";
    private final String IDInputFormFTL = "FTL templates/InvoicesView/IDinputForm.ftl";

    private Rout rout;
    private SQL sql;
    private DAO_InvoicesFullView queryData;
    private String viewTitle;
    private int menuButtonActive;


    public InvoicesView(Request request) throws ClassNotFoundException, UnsupportedEncodingException, FileNotFoundException, SQLException {
        super();
        this.rout = new Rout(request);
        this.sql = new SQL(request);
        this.queryData = new DAO_InvoicesFullView(sql.query, sql.queryCountID);
        this.menuButtonActive = this.getMenuButtonActive(request);
        this.viewTitle = this.getViewTitle(request);
    }

    public InvoicesView(Request request, int id) throws ClassNotFoundException, UnsupportedEncodingException, FileNotFoundException, SQLException {
        super();
        this.rout = new Rout(request);
        this.sql = new SQL(request);
        this.menuButtonActive = this.getMenuButtonActive(request);
        this.viewTitle = "ID " + String.format("%d",id);
        queryData = new DAO_InvoicesFullView(sql.query, sql.queryCountID);
    }

    @Override
    public String render() throws IOException, TemplateException, ClassNotFoundException, SQLException {
        Template template = getTemplate(this.ftlFile);

        if (FINANCE_VIEW) {
            replaceMap.put("FINANCE_VIEW"," Reader");
        } else {
            replaceMap.put("FINANCE_VIEW","");
        }

        replaceMap.put("Footer", new Footer().render());
        replaceMap.put("supplierList", Controller.comboList.getSuppliersOptionValue());
        replaceMap.put("CurrencyList", Controller.comboList.getCurrencyOptionValue());
        replaceMap.put("authContactList", Controller.comboList.getAuthContactOptionValue());
        replaceMap.put("contactGenpactList", Controller.comboList.getContactGenpactOptionValue());

        switch (queryData.nrOfRecords) {
            case 0:
                replaceMap.put("Style", new Style().render());
                replaceMap.put("Header", new IFVHeader().render());
                //TODO: 0 records return info, replace it with something more eyecandy
                replaceMap.put("DBTable", "<main class=\"row\"><h3>Query: " + sql.query + "</h3>" + "<h4>Returns: " + queryData.nrOfRecords + " records</h4></main>");
                replaceMap.put("IDinputForm", "");
                break;
            case 1:
                replaceMap.put("Style", new Style().render());
                replaceMap.put("Header", new IFVHeader().render());
                replaceMap.put("DBTable", new IFVDBtable().render());
                replaceMap.put("IDinputForm", new IDinputForm(queryData.invoices.get(0)).render());
                break;
            default:
                replaceMap.put("Style", new Style().render());
                replaceMap.put("Header", new IFVHeader().render());
                replaceMap.put("DBTable", new IFVDBtable().render());
                replaceMap.put("IDinputForm", "");
                break;
        }
        return process(template);
    }

    // 1 highlight menu button 1 in header, 3 is for any filter view
    private Integer getMenuButtonActive(Request request) throws UnsupportedEncodingException {
        Integer retVal;
        switch (this.queryData.nrOfRecords) {
            case 0: retVal = 0; break;
            case 1: retVal = 0; break;
            default:
                String c1 = URLDecoder.decode(request.params("column1"), "UTF-8");
                String s1 = URLDecoder.decode(request.params("sign1"), "UTF-8");
                String v1 = URLDecoder.decode(request.params("value1"), "UTF-8");

                if (c1.equals("ID") && s1.equals("gte") && v1.equals("0")) {
                    retVal = 1;
                } else {
                    retVal = 3;
                }
        }
        return retVal;
    }

    private String getViewTitle(Request request){
        String retVal;
        switch (this.queryData.nrOfRecords){
            case 0: retVal = "No records to return"; break;
            case 1: retVal = "ID " + queryData.invoices.get(0).getID(); break;
            default:
                if (this.menuButtonActive == 1) {
                    retVal = "Main view " + String.format("%d",queryData.nrOfRecords) + " records";
                } else {
                    retVal = "Filter view " + String.format("%d",queryData.nrOfRecords) + " records";
                }
        }
        return retVal;
    }

    private class IFVHeader extends FreeMarkerTemplate implements Renderer  {
        private final Boolean tabHeaderWithSort = true;
        private final Boolean pagination = true;

        public IFVHeader() throws ClassNotFoundException, SQLException {
            super();
        }

        @Override
        public String render() throws IOException, TemplateException, ClassNotFoundException, SQLException {
            Template templateHeader = getTemplate(headerFTL);
            String tableHeader;
            if (this.tabHeaderWithSort) {
                tableHeader = this.getTableHeader();
            } else {
                tableHeader = this.getTableHeaderWithoutSort();
            }

            int rowPerPage = ImCFG.getRowsPerPage();
            int lastPage = queryData.nrOfRecords/rowPerPage + 1;

            switch (menuButtonActive){
                case 2: {
                    replaceMap.put("commentOnMenu1","");
                    replaceMap.put("commentOnMenu2","");
                    replaceMap.put("commentOnMenu3","<!--");
                    replaceMap.put("commentOnMenu4","");
                    replaceMap.put("commentOnMenu5","<!--");
                    replaceMap.put("commentOffMenu1","");
                    replaceMap.put("commentOffMenu2","");
                    replaceMap.put("commentOffMenu3","-->");
                    replaceMap.put("commentOffMenu4","");
                    replaceMap.put("commentOffMenu5","-->");
                    replaceMap.put("commentOnAdvSearch", "<!--");
                    replaceMap.put("commentOffAdvSearch", "-->");
                    replaceMap.put("commentOnSearch", "<!--");
                    replaceMap.put("commentOffSearch", "-->");
                    break;
                }
                case 5: {
                    replaceMap.put("commentOnMenu1","");
                    replaceMap.put("commentOnMenu2","");
                    replaceMap.put("commentOnMenu3","<!--");
                    replaceMap.put("commentOnMenu4","");
                    replaceMap.put("commentOnMenu5","");
                    replaceMap.put("commentOffMenu1","");
                    replaceMap.put("commentOffMenu2","");
                    replaceMap.put("commentOffMenu3","-->");
                    replaceMap.put("commentOffMenu4","");
                    replaceMap.put("commentOffMenu5","");
                    replaceMap.put("commentOnAdvSearch", "<!--");
                    replaceMap.put("commentOffAdvSearch", "-->");
                    replaceMap.put("commentOnSearch", "<!--");
                    replaceMap.put("commentOffSearch", "-->");
                    break;
                }
                default: {
                    replaceMap.put("commentOnMenu1","");
                    replaceMap.put("commentOnMenu2","");
                    replaceMap.put("commentOnMenu3","");
                    replaceMap.put("commentOnMenu4","");
                    replaceMap.put("commentOnMenu5","");
                    replaceMap.put("commentOffMenu1","");
                    replaceMap.put("commentOffMenu2","");
                    replaceMap.put("commentOffMenu3","");
                    replaceMap.put("commentOffMenu4","");
                    replaceMap.put("commentOffMenu5","");
                    replaceMap.put("commentOnAdvSearch", "");
                    replaceMap.put("commentOffAdvSearch", "");
                    replaceMap.put("commentOnSearch", "");
                    replaceMap.put("commentOffSearch", "");
                    break;
                }
            }

            if (pagination) {   // Dont comment part of HTML code for pagination
                replaceMap.put("paginationOff1", "");
                replaceMap.put("paginationOff2", "");
                if (rout.pageNr == 1) { // hide previous pagination button
                    replaceMap.put("pagePreviousOff1", "<!--");
                    replaceMap.put("pagePreviousOff2", "-->");
                } else {
                    replaceMap.put("pagePreviousOff1", "");
                    replaceMap.put("pagePreviousOff2", "");
                }
                if (rout.pageNr == queryData.nrOfRecords/ImCFG.getRowsPerPage()+1) { // hide next pagination button
                    replaceMap.put("pageNextOff1", "<!--");
                    replaceMap.put("pageNextOff2", "-->");
                } else {
                    replaceMap.put("pageNextOff1", "");
                    replaceMap.put("pageNextOff2", "");
                }
                if ((rout.pageNr > 1) && (rout.pageNr < lastPage)) { // dont hide any pagination button
                    replaceMap.put("pagePreviousOff1", "");
                    replaceMap.put("pagePreviousOff2", "");
                    replaceMap.put("pageNextOff1", "");
                    replaceMap.put("pageNextOff2", "");
                }
            } else {            // comment part of HTML code for pagination
                replaceMap.put("paginationOff1", "<!--");
                replaceMap.put("paginationOff2", "--><p></p>");
                replaceMap.put("pagePreviousOff1", "");
                replaceMap.put("pagePreviousOff2", "");
                replaceMap.put("pageNextOff1", "");
                replaceMap.put("pageNextOff2", "");
            }

//            replaceMap.put("filterList", filterList);
            replaceMap.put("NetID", ImCFG.getUserNetID());
            replaceMap.put("supplierList", Controller.comboList.getSuppliersOptionValue()); //supplierList);
            replaceMap.put("menu1", (menuButtonActive == 1) ? " IM-menu-active" : "");
            replaceMap.put("menu2", (menuButtonActive == 2) ? " IM-menu-active" : "");
            replaceMap.put("menu3", (menuButtonActive == 3) ? " IM-menu-active" : "");
            replaceMap.put("menu4", (menuButtonActive == 4) ? " IM-menu-active" : "");
            replaceMap.put("menu5", (menuButtonActive == 5) ? " IM-menu-active" : "");
            replaceMap.put("viewTitle", viewTitle);
            replaceMap.put("records", queryData.nrOfRecords);
            replaceMap.put("rout", rout.select + rout.orderByClause);
            replaceMap.put("previous", (rout.pageNr < 1) ? "1" : Integer.toString(rout.pageNr - 1).replace(",", ""));
            if (queryData.nrOfRecords != 0) {
                replaceMap.put("pageNr", Integer.toString(rout.pageNr).replace(",", "") + "/" + Integer.toString(queryData.nrOfRecords/ImCFG.getRowsPerPage() + 1).replace(",", ""));
            } else {    // case when total nr of pages is not required
                replaceMap.put("pageNr", Integer.toString(rout.pageNr).replace(",", ""));
            }
            replaceMap.put("next", Integer.toString(rout.pageNr + 1).replace(",", ""));
            replaceMap.put("tableHeader", (tabHeaderWithSort) ? tableHeader : "");

            return process(templateHeader);
        }

        private String getTableHeader() throws IOException, ClassNotFoundException, SQLException, TemplateException {
            String tableHeader = "";
            Template templateTableHeader = getTemplate(headerTableHeaderFTL);
            String link = rout.select;
            String orderByColumn = rout.orderBy;
            String orderDirection = rout.sortDirection;

            for (String[] s : ImCFG.getInvoicesMetaData()) {
                replaceMap.put("className", s[1]);
                replaceMap.put("viewName", s[4]);
                replaceMap.put("fontColor", "inherit");
                replaceMap.put("iconColor", "black");
                // column Folder has no sort option - it would make no sens to sort by this column
                if (s[1].equals("AuthEmail")) {
                    replaceMap.put("commentOn", "<!--");
                    replaceMap.put("commentOff", "-->");
                    replaceMap.put("sortIcon", "");
                    replaceMap.put("sortLink", "");
                    // other columns, default is desc sorting
                } else if (s[1].equals(orderByColumn)) {
                    replaceMap.put("commentOn", "");
                    replaceMap.put("commentOff", "");
                    replaceMap.put("fontColor", "dodgerblue");
                    replaceMap.put("iconColor", "dodgerblue");
                    if (orderDirection.equals("ASC")) {
                        replaceMap.put("sortLink", link + s[1] + "/DESC/1");
                        replaceMap.put("sortIcon", "fa-sort-asc");
                    } else {
                        replaceMap.put("sortLink", link + s[1] + "/ASC/1");
                        replaceMap.put("sortIcon", "fa-sort-desc");
                    }
                } else {
                    replaceMap.put("commentOn", "");
                    replaceMap.put("commentOff", "");
                    replaceMap.put("sortLink", link + s[1] + "/DESC/1");
                    replaceMap.put("sortIcon", "fa-sort");
                }

                templateTableHeader.process(replaceMap, retVal);

                tableHeader = retVal.toString();
            }

            clearData();

            return tableHeader;
        }

        private String getTableHeaderWithoutSort() throws IOException, ClassNotFoundException, SQLException, TemplateException {
            String tableHeader = "";
            Template templateTableHeaderWithoutSort = getTemplate(headerTableHeaderWithoutSortFTL);

            for (String[] s : ImCFG.getInvoicesMetaData()) {
                replaceMap.put("className", s[1]);
                replaceMap.put("viewName", s[4]);

                templateTableHeaderWithoutSort.process(replaceMap, retVal);
            }

            tableHeader = retVal.toString();

            clearData();

            return tableHeader;
        }
    }

    private class IFVDBtable extends FreeMarkerTemplate implements Renderer {

        public IFVDBtable() throws ClassNotFoundException {
            super();
        }

        @Override
        public String render() throws IOException, TemplateException, ClassNotFoundException, SQLException {
            Template template = getTemplate(dbTableFTL);
            String table = getTable();

            replaceMap.put("Table", table);

            return process(template);
        }

        private String getTable() throws IOException, ClassNotFoundException, SQLException, TemplateException {
            String table = "";
            Template template = getTemplate(dbTableRowFTL);

            for (Invoice invoice:queryData.invoices) {
                String scanPath = ImCFG.getImExternalFolderPath() + invoice.getInvScanPath();
                String emailAuthPath = ImCFG.getImExternalFolderPath() + invoice.getInvScanPath();

                // if invoice number is repeating - highligth inv nr link in red!!! link is linking to duplicates
                if (invoice.getInvNrDuplicates() > 1) {
                    replaceMap.put("invNrLink", "<a href=\"/IFV/InvoiceNR/=/" + URLEncoder.encode(invoice.getInvoiceNR(),"UTF-8") + "/ID/DESC/1\" target=\"_blank\" style=\"color: red\">");
                    replaceMap.put("invNrLink_a", "</a>");
                } else {
                    replaceMap.put("invNrLink", "");
                    replaceMap.put("invNrLink_a", "");
                }

                replaceMap.put("rowComment", "<!-- ============================ ID = "+String.format("%d",invoice.getID())+" ======================================================================= -->");
                replaceMap.put("rowColor", (invoice.getRowColor().length() == 0) ? "white":invoice.getRowColor());

                replaceMap.put("userColor", invoice.getUserColor());

                replaceMap.put("ID", String.format("%d",invoice.getID()));
                replaceMap.put("fileExists", (fileExists(scanPath)) ? "":"black");
                replaceMap.put("BCrow", truncuate(invoice.getBC(),10));
                replaceMap.put("entryDate", invoice.getEntryDate());
                replaceMap.put("supplier", truncuate(invoice.getSupplier(),18));
                replaceMap.put("supplierLink", URLEncoder.encode(invoice.getSupplier(),"UTF-8"));
                replaceMap.put("invoiceNR", truncuate(invoice.getInvoiceNR(),18));
                replaceMap.put("POlink",URLEncoder.encode(invoice.getPO(),"UTF-8"));
                replaceMap.put("PO", truncuate(invoice.getPO(),18));
                try {
                    replaceMap.put("netPrice", String.format("%.2f",invoice.getNetPrice()));
                } catch (Exception e) {
                    replaceMap.put("netPrice", "");
                }
                replaceMap.put("currency",invoice.getCurrency());
                replaceMap.put("authorization", truncuate(invoice.getAuthContact(),18));
                replaceMap.put("authEmailExists", !invoice.getAuthEmail().equals("") ? "":"-o");
                // new option, opens folder with inv scan and msg (if already sent)
                replaceMap.put("email", "<i class=\"fa fa-folder-open-o\" aria-hidden=\"true\"></i>"); // depreciated to deletion

                replaceMap.put("GR", truncuate(invoice.getGR(),25));
                replaceMap.put("processStage", ""); //depreciated to deletion
                // Details Table:
                replaceMap.put("BC", invoice.getBC());
                replaceMap.put("SupplierDetails", invoice.getSupplier());
                replaceMap.put("InvNrDetails",invoice.getInvoiceNR());
                replaceMap.put("PODetails",invoice.getPO());
                replaceMap.put("GRDetails",invoice.getGR());
                replaceMap.put("contactGenpact", invoice.getContactGenpact());
                replaceMap.put("invDate", invoice.getInvDate());
                replaceMap.put("emailSubject", invoice.getEmailSubject());
                replaceMap.put("authDate", invoice.getAuthDate());
                replaceMap.put("authReplyDate", invoice.getAuthReplyDate());
                replaceMap.put("endDate", invoice.getEndDate());
                replaceMap.put("genpactLastReply", invoice.getGenpactLastReply());
                replaceMap.put("userComments", invoice.getUserComments());
                replaceMap.put("status", invoice.getStatus());
                replaceMap.put("FinanceComments", (invoice.getFinanceComments() == null) ? "":invoice.getFinanceComments());

                template.process(replaceMap,retVal);

                table = retVal.toString();
            }
            clearData();
            return table;
        }
    }

    private class IDinputForm extends FreeMarkerTemplate implements Renderer {
        private Invoice invoice;

        public IDinputForm(Invoice invoice) throws ClassNotFoundException, SQLException {
            super();
            this.invoice = invoice;
        }

        @Override
        public String render() throws IOException, TemplateException, ClassNotFoundException, SQLException {
            Template template = getTemplate(IDInputFormFTL);

            this.invoice.toString();

            replaceMap.put("BC", this.invoice.getBC());
            if (this.invoice.getBC().equals("")) { replaceMap.put("BC_activeClass","");} else {replaceMap.put("BC_activeClass","active");}
            replaceMap.put("EntryDate", this.invoice.getEntryDate());
            if (this.invoice.getEntryDate().equals("")) { replaceMap.put("EntryDate_activeClass","");} else {replaceMap.put("EntryDate_activeClass","active");}
            replaceMap.put("ContactGenpact", this.invoice.getContactGenpact());
            if (this.invoice.getContactGenpact().equals("")) { replaceMap.put("ContactGenpact_activeClass","");} else {replaceMap.put("ContactGenpact_activeClass","active");}
            replaceMap.put("Supplier", this.invoice.getSupplier());
            if (this.invoice.getSupplier().equals("")) { replaceMap.put("Supplier_activeClass","");} else {replaceMap.put("Supplier_activeClass","active");}
            replaceMap.put("InvoiceNR", this.invoice.getInvoiceNR());
            if (this.invoice.getInvoiceNR().equals("")) { replaceMap.put("InvoiceNR_activeClass","");} else {replaceMap.put("InvoiceNR_activeClass","active");}
            replaceMap.put("InvScanPath", this.invoice.getInvScanPath());
            if (this.invoice.getInvScanPath().equals("")) { replaceMap.put("InvScanPath_activeClass","");} else {replaceMap.put("InvScanPath_activeClass","active");}
            replaceMap.put("PO", this.invoice.getPO());
            if (this.invoice.getPO().equals("")) { replaceMap.put("PO_activeClass","");} else {replaceMap.put("PO_activeClass","active");}
            replaceMap.put("NetPrice", this.invoice.getNetPrice().toString().replace(",",""));
            if (this.invoice.getNetPrice() == 0.00) { replaceMap.put("NetPrice_activeClass","");} else {replaceMap.put("NetPrice_activeClass","active");}
//        replaceMap.put("NetPriceDecimal",this.invoice.getNetPriceDecimal());
//        if (this.invoice.getBC().equals("")) { replaceMap.put("NetPriceDecimal_activeClass","");} else {replaceMap.put("NetPriceDecimal_activeClass","active");}

            replaceMap.put("Currency", this.invoice.getCurrency());
            if (this.invoice.getCurrency().equals("")) { replaceMap.put("Currency_activeClass","");} else {replaceMap.put("Currency_activeClass","active");}
            String currencyOption = "";
            for (String s: ImCFG.getCurrencies()
                    ) {
                currencyOption = currencyOption + "<option value=\"" + s + "\">\n";
            }
            replaceMap.put("CurrencyList", currencyOption);

            replaceMap.put("InvDate", this.invoice.getInvDate());
            if (this.invoice.getInvDate().equals("")) { replaceMap.put("InvDate_activeClass","");} else {replaceMap.put("InvDate_activeClass","active");}
            replaceMap.put("EmailSubject", this.invoice.getEmailSubject());
            if (this.invoice.getEmailSubject().equals("")) { replaceMap.put("EmailSubject_activeClass","");} else {replaceMap.put("EmailSubject_activeClass","active");}
            replaceMap.put("AuthContact", this.invoice.getAuthContact());
            if (this.invoice.getAuthContact().equals("")) { replaceMap.put("AuthContact_activeClass","");} else {replaceMap.put("AuthContact_activeClass","active");}
            replaceMap.put("AuthDate", this.invoice.getAuthDate());
            if (this.invoice.getAuthDate().equals("")) { replaceMap.put("AuthDate_activeClass","");} else {replaceMap.put("AuthDate_activeClass","active");}
            replaceMap.put("AuthReplyDate", this.invoice.getAuthReplyDate());
            if (this.invoice.getAuthReplyDate().equals("")) { replaceMap.put("AuthReplyDate_activeClass","");} else {replaceMap.put("AuthReplyDate_activeClass","active");}
            replaceMap.put("AuthEmail", this.invoice.getAuthEmail());
            if (this.invoice.getAuthEmail().equals("")) { replaceMap.put("AuthEmail_activeClass","");} else {replaceMap.put("AuthEmail_activeClass","active");}
            replaceMap.put("EndDate", this.invoice.getEndDate());
            if (this.invoice.getEndDate().equals("")) { replaceMap.put("EndDate_activeClass","");} else {replaceMap.put("EndDate_activeClass","active");}
            replaceMap.put("GR", this.invoice.getGR());
            if (this.invoice.getGR().equals("")) { replaceMap.put("GR_activeClass","");} else {replaceMap.put("GR_activeClass","active");}
            replaceMap.put("GenpactLastReply", this.invoice.getGenpactLastReply());
            if (this.invoice.getGenpactLastReply().equals("")) { replaceMap.put("GenpactLastReply_activeClass","");} else {replaceMap.put("GenpactLastReply_activeClass","active");}
            replaceMap.put("UserComments", this.invoice.getUserComments());
            if (this.invoice.getUserComments().equals("")) { replaceMap.put("UserComments_activeClass","");} else {replaceMap.put("UserComments_activeClass","active");}

            replaceMap.put("Status", this.invoice.getStatus());
            System.err.println(this.invoice.getStatus());
//        if (this.invoice.getStatus() == 0) { replaceMap.put("Status_activeClass","active");} else {replaceMap.put("Status_activeClass","active");}
            String statusOption = "";
            for (String s: ImCFG.getStatusMetaData()
                    ) {
                statusOption = statusOption + "<option value=\"" + s + "\">\n";
            }
            replaceMap.put("StatusList", statusOption);

            replaceMap.put("User", this.invoice.getUser());
            if (this.invoice.getUser().equals("")) { replaceMap.put("User_activeClass","");} else {replaceMap.put("User_activeClass","active");}
            String userOption = "";
            for (User user: queryData.users
                    ) {
                userOption = userOption + "<option value=\"" + user.getUserID() + "\">\n";
            }
            replaceMap.put("UserList", userOption);

            replaceMap.put("RowColor", this.invoice.getRowColor());
            if (this.invoice.getRowColor().equals("")) { replaceMap.put("RowColor_activeClass","");} else {replaceMap.put("RowColor_activeClass","active");}
            String rowColorOption = "";
            for (String s: ImCFG.getRowColor()
                    ) {
//            s = s.substring(0,s.indexOf(" ")); // remove lighten-4 from row color picker - it is not needed to be seen by user
                rowColorOption = rowColorOption + "<option value=\"" + s + "\">\n";
            }
            replaceMap.put("RowColorList", rowColorOption);

            replaceMap.put("ProcessStatus", this.invoice.getProcessStatus());
            if (this.invoice.getProcessStatus().equals("")) { replaceMap.put("ProcessStatus_activeClass","");} else {replaceMap.put("ProcessStatus_activeClass","active");}
            String processStatusOption = "";
            for (String s: ImCFG.getProcessStatus()
                    ) {
                processStatusOption = processStatusOption + "<option value=\"" + s + "\">\n";
            }
            replaceMap.put("ProcessStatusList", processStatusOption);

            replaceMap.put("ProcessStage", "100");
            replaceMap.put("ProcessStage_activeClass","");

            replaceMap.put("FinanceComments", this.invoice.getFinanceComments());
            if (this.invoice.getFinanceComments().equals("")) { replaceMap.put("FinanceComments_activeClass","");} else {replaceMap.put("FinanceComments_activeClass","active");}

            return process(template);
        }
    }

}
