package View.Renderers.PartCodeRenderers;

import Model.InvoiceManagerCFG;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import View.FreeMarkerTemplate;
import View.Renderer;

import java.io.IOException;
import java.sql.SQLException;

/**
 * Created by Karol Kistela on 28-Apr-16.
 */
public class Header extends FreeMarkerTemplate implements Renderer {
    private final String headerFTL = "Parts/DBview/Header.ftl";
    private final String headerFilterListFTL = "Parts/DBview/Header_FilterList.ftl";
    private final String headerTableHeaderFTL = "Parts/DBview/Header_tableHeader.ftl";
    private int menuButtonActive;
    private String rout;
    private int pageNr;
    private String pageNrString;
    private int totalPages;
    private String viewTitle;
    private boolean tabHeader;
    private boolean pagination;
    private InvoiceManagerCFG ImCFG;
//    private List<String[]> invoicesMetaData;

    public static void main(String[] args) throws ClassNotFoundException, TemplateException, SQLException, IOException {
        Header h = new Header(1,"/DB/",1, 1000, "DB Main View", true, true);

        System.out.println(h.render());
    }

    public Header(int menuButtonActive, String rout, int pageNr, int records, String viewTitle, boolean tabHeader, boolean pagination) throws ClassNotFoundException, SQLException {
        super();
        this.ImCFG = new InvoiceManagerCFG();
        this.menuButtonActive = menuButtonActive;
        this.rout = rout;
        this.pageNr = pageNr;
        this.pageNrString = Integer.toString(pageNr).replace(",","");
        this.totalPages = (Integer) records/ImCFG.getRowsPerPage() + 1;
        this.viewTitle = viewTitle;
        this.tabHeader = tabHeader;
        this.pagination = pagination;

//        this.invoicesMetaData = new InvoiceManagerDB_DAO().sqlSELECT("SELECT * FROM InvoicesMetaData WHERE DisplayOrder>0 ORDER BY DisplayOrder ASC ",1,false,false);
    }

    public Header(int menuButtonActive, String rout, int pageNr, String viewTitle, boolean tabHeader, boolean pagination) throws ClassNotFoundException, SQLException {
        super();
        this.ImCFG = new InvoiceManagerCFG();
        this.menuButtonActive = menuButtonActive;
        this.rout = rout;
        this.pageNr = pageNr;
        this.pageNrString = Integer.toString(pageNr).replace(",","");
        this.totalPages = 1;
        this.viewTitle = viewTitle;
        this.tabHeader = tabHeader;
        this.pagination = pagination;

//        this.invoicesMetaData = new InvoiceManagerDB_DAO().sqlSELECT("SELECT * FROM InvoicesMetaData WHERE DisplayOrder>0 ORDER BY DisplayOrder ASC ",1,false,false);
    }

    @Override
    public String render() throws IOException, TemplateException, ClassNotFoundException, SQLException {
        Template template = getTemplate(this.headerFTL);
        String filterList = this.getFilterList();
        String tableHeader = this.getTableHeader();

        if (pagination) {   // Dont comment part of HTML code for pagination
            replaceMap.put("paginationOff1","");
            replaceMap.put("paginationOff2","");
            if (this.pageNr == 1) { // hide previous pagination button
                replaceMap.put("pagePreviousOff1", "<!--");
                replaceMap.put("pagePreviousOff2", "-->");
            } else {                // dont hide previous pagination button
                replaceMap.put("pagePreviousOff1", "");
                replaceMap.put("pagePreviousOff2", "");
            }
        } else {            // comment part of HTML code for pagination
            replaceMap.put("paginationOff1","<!--");
            replaceMap.put("paginationOff2","--><p></p>");
            replaceMap.put("pagePreviousOff1", "");
            replaceMap.put("pagePreviousOff2", "");
        }

        replaceMap.put("filterList", filterList);
        replaceMap.put("menu1", (menuButtonActive == 1) ? " IM-menu-active":"");
        replaceMap.put("menu2", (menuButtonActive == 2) ? " IM-menu-active":"");
        replaceMap.put("menu3", (menuButtonActive == 3) ? " IM-menu-active":"");
        replaceMap.put("menu4", (menuButtonActive == 4) ? " IM-menu-active":"");
        replaceMap.put("menu5", (menuButtonActive == 5) ? " IM-menu-active":"");
        replaceMap.put("viewTitle", this.viewTitle);
        replaceMap.put("rout", this.rout);
        replaceMap.put("previous", (this.pageNr < 1) ? "1": Integer.toString(this.pageNr - 1).replace(",",""));
        replaceMap.put("pageNr", Integer.toString(this.pageNr).replace(",","") + "/" + Integer.toString(this.totalPages).replace(",",""));
        replaceMap.put("next", Integer.toString(this.pageNr + 1).replace(",",""));
        replaceMap.put("tableHeader", (tabHeader) ? tableHeader:"");

        return process(template);
    }

    private String getFilterList() throws IOException, ClassNotFoundException, TemplateException {
        Template template = getTemplate(this.headerFilterListFTL);
        String filterList = "";

        for (String[] s:ImCFG.getFilters()
             ) {
            replaceMap.put("ID", s[0]);
            replaceMap.put("filterName", s[1]);

            template.process(replaceMap,retVal);

            filterList = retVal.toString();
        }
        clearData();
        return filterList;
    }

    private String getTableHeader() throws IOException, ClassNotFoundException, SQLException, TemplateException {
        String tableHeader = "";
        Template template = getTemplate(this.headerTableHeaderFTL);

        for (String[] s:ImCFG.getInvoicesMetaData()) {
            replaceMap.put("className", s[1]);
            replaceMap.put("viewName", s[4]);

            template.process(replaceMap,retVal);

            tableHeader = retVal.toString();
        }

        clearData();

        return tableHeader;
    }
}
