package View.Renderers.PartCodeRenderers;

import Controller.Controller;
import Model.InvoiceManagerCFG;
import Model.InvoiceManagerDB_DAO;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import View.FreeMarkerTemplate;
import View.Renderer;

import java.io.IOException;
import java.io.StringWriter;
import java.sql.SQLException;
import java.util.List;

import static Controller.Controller.suppliers;

/**
 * Created by Karol Kistela on 28-Apr-16.
 */
public class Header extends FreeMarkerTemplate implements Renderer {
    private final String headerFTL = "Parts/DBview/Header.ftl";
    private final String headerFilterListFTL = "Parts/DBview/Header_FilterList.ftl";
    private final String headerTableHeaderFTL = "Parts/DBview/Header_tableHeader.ftl";
    private final String headerTableHeaderWithoutSortFTL = "Parts/DBview/Header_tableHeaderWithoutSort.ftl";
    private int menuButtonActive;
    private String rout;
    private int pageNr;
    private int nrOfRecords;
    private int totalPages;
    private String viewTitle;
    private boolean tabHeader;
    private boolean tabHeaderWithSort;
    private boolean pagination;
    private InvoiceManagerCFG ImCFG;
//    private List<String[]> invoicesMetaData;

//    public static void main(String[] args) throws ClassNotFoundException, TemplateException, SQLException, IOException {
//        Header h = new Header(1,"/DB/",1, 1000, "DB Main View", true, true);
//
//        System.out.println(h.render());
//    }

    public Header(int menuButtonActive, String rout, int pageNr, int records, String viewTitle, boolean tabHeader, boolean tabHeaderWithSort, boolean pagination) throws ClassNotFoundException, SQLException {
        super();
        this.ImCFG = new InvoiceManagerCFG();
        this.menuButtonActive = menuButtonActive;
        this.rout = rout;
        this.pageNr = pageNr;
        this.nrOfRecords = records;
        this.totalPages = (Integer) records / ImCFG.getRowsPerPage() + 1;
        this.viewTitle = viewTitle + " " + records + " records";
        this.tabHeader = tabHeader;
        this.tabHeaderWithSort = tabHeaderWithSort;
        this.pagination = pagination;

//        this.invoicesMetaData = new InvoiceManagerDB_DAO().sqlSELECT("SELECT * FROM InvoicesMetaData WHERE DisplayOrder>0 ORDER BY DisplayOrder ASC ",1,false,false);
    }

    public Header(int menuButtonActive, String rout, int pageNr, String viewTitle, boolean tabHeader, boolean tabHeaderWithSort, boolean pagination) throws ClassNotFoundException, SQLException {
        super();
        this.ImCFG = new InvoiceManagerCFG();
        this.menuButtonActive = menuButtonActive;
        this.rout = rout;
        this.pageNr = pageNr;
        this.nrOfRecords = 0;
        this.totalPages = 0;
        this.viewTitle = viewTitle;
        this.tabHeader = tabHeader;
        this.tabHeaderWithSort = tabHeaderWithSort;
        this.pagination = pagination;

//        this.invoicesMetaData = new InvoiceManagerDB_DAO().sqlSELECT("SELECT * FROM InvoicesMetaData WHERE DisplayOrder>0 ORDER BY DisplayOrder ASC ",1,false,false);
    }

    @Override
    public String render() throws IOException, TemplateException, ClassNotFoundException, SQLException {
        Template template = getTemplate(this.headerFTL);
        String filterList = this.getFilterList();
        String supplierList = this.getSupplierList();
        String tableHeader;
        if (this.tabHeaderWithSort) {
            tableHeader = this.getTableHeader();
        } else {
            tableHeader = this.getTableHeaderWithoutSort();
        }

        switch (this.menuButtonActive){
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
            case 0: {
                replaceMap.put("commentOnMenu1","");
                replaceMap.put("commentOnMenu2","");
                replaceMap.put("commentOnMenu3","");
                replaceMap.put("commentOnMenu4","");
                replaceMap.put("commentOnMenu5","<!--");
                replaceMap.put("commentOffMenu1","");
                replaceMap.put("commentOffMenu2","");
                replaceMap.put("commentOffMenu3","");
                replaceMap.put("commentOffMenu4","");
                replaceMap.put("commentOffMenu5","-->");
                replaceMap.put("commentOnAdvSearch", "");
                replaceMap.put("commentOffAdvSearch", "");
                replaceMap.put("commentOnSearch", "");
                replaceMap.put("commentOffSearch", "");
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
            if (this.pageNr == 1) { // hide previous pagination button
                replaceMap.put("pagePreviousOff1", "<!--");
                replaceMap.put("pagePreviousOff2", "-->");
                replaceMap.put("pageNextOff1", "");
                replaceMap.put("pageNextOff2", "");
            } else if ((this.pageNr == this.totalPages)) { // hide next pagination button
                replaceMap.put("pagePreviousOff1", "");
                replaceMap.put("pagePreviousOff2", "");
                replaceMap.put("pageNextOff1", "<!--");
                replaceMap.put("pageNextOff2", "-->");
            } else { // dont hide any pagination button
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

        replaceMap.put("filterList", filterList);
        replaceMap.put("supplierList", supplierList);
        replaceMap.put("menu1", (menuButtonActive == 1) ? " IM-menu-active" : "");
        replaceMap.put("menu2", (menuButtonActive == 2) ? " IM-menu-active" : "");
        replaceMap.put("menu3", (menuButtonActive == 3) ? " IM-menu-active" : "");
        replaceMap.put("menu4", (menuButtonActive == 4) ? " IM-menu-active" : "");
        replaceMap.put("menu5", (menuButtonActive == 5) ? " IM-menu-active" : "");
        replaceMap.put("viewTitle", this.viewTitle);
        replaceMap.put("records", this.nrOfRecords);
        replaceMap.put("rout", this.rout);
        replaceMap.put("previous", (this.pageNr < 1) ? "1" : Integer.toString(this.pageNr - 1).replace(",", ""));
        if (this.totalPages != 0) {
            replaceMap.put("pageNr", Integer.toString(this.pageNr).replace(",", "") + "/" + Integer.toString(this.totalPages).replace(",", ""));
        } else {    // case when total nr of pages is not required
            replaceMap.put("pageNr", Integer.toString(this.pageNr).replace(",", ""));
        }
        replaceMap.put("next", Integer.toString(this.pageNr + 1).replace(",", ""));
        replaceMap.put("tableHeader", (tabHeader) ? tableHeader : "");

        return process(template);
    }

    private String getSupplierList() {
        String retVal = "";
        for (String[] s: suppliers.getSupplierList()
             ) {
            retVal += "                      <option value=\"" + s[0] + "\">\n";
        }
        return retVal;
    }

    private String getFilterList() throws IOException, ClassNotFoundException, TemplateException {
        Template template = getTemplate(this.headerFilterListFTL);
        String filterList = "";
        String orderBy = ImCFG.getOrderByClause().replace("ORDER BY ","").replace(" ","/");

        for (String[] s : ImCFG.getFilters()
                ) {
            replaceMap.put("ID", s[0]);
            replaceMap.put("filterName", s[1]);
            replaceMap.put("orderBy", orderBy);

            template.process(replaceMap, retVal);

            filterList = retVal.toString();
        }
        clearData();
        return filterList;
    }

    private String getTableHeader() throws IOException, ClassNotFoundException, SQLException, TemplateException {
        String tableHeader = "";
        Template template = getTemplate(this.headerTableHeaderFTL);
        String link = this.rout.substring(0, this.rout.indexOf("OrderBy") + 8);
        String orderByColumn = this.rout.replace(link, "").substring(0, this.rout.replace(link, "").indexOf("/"));
        String orderDirection = this.rout.replace(link, "").replace(orderByColumn, "").replace("/", "");

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

            template.process(replaceMap, retVal);

            tableHeader = retVal.toString();
        }

        clearData();

        return tableHeader;
    }

    private String getTableHeaderWithoutSort() throws IOException, ClassNotFoundException, SQLException, TemplateException {
        String tableHeader = "";
        Template template = getTemplate(this.headerTableHeaderWithoutSortFTL);

        for (String[] s : ImCFG.getInvoicesMetaData()) {
            replaceMap.put("className", s[1]);
            replaceMap.put("viewName", s[4]);

            template.process(replaceMap, retVal);
        }

        tableHeader = retVal.toString();

        clearData();

        return tableHeader;
    }
}
