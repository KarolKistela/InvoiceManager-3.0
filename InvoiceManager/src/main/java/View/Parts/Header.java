package View.Parts;

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

/**
 * Created by Karol Kistela on 28-Apr-16.
 */
public class Header extends FreeMarkerTemplate implements Renderer {
    private int menuButtonActive;
    private String rout;
    private int pageNr;
    private String viewTitle;
    private boolean tabHeader;
    private boolean pagination;
    private InvoiceManagerCFG ImCFG;
    private List<String[]> invoicesMetaData;

    public static void main(String[] args) throws ClassNotFoundException, TemplateException, SQLException, IOException {
        Header h = new Header(1,"/DB/",1,"DB Main View", true, true);

        System.out.println(h.render());
    }

    public Header(int menuButtonActive, String rout, int pageNr, String viewTitle, boolean tabHeader, boolean pagination) throws ClassNotFoundException, SQLException {
        super();
        this.menuButtonActive = menuButtonActive;
        this.rout = rout;
        this.pageNr = pageNr;
        this.viewTitle = viewTitle;
        this.tabHeader = tabHeader;
        this.pagination = pagination;
        this.ImCFG = new InvoiceManagerCFG();
        this.invoicesMetaData = new InvoiceManagerDB_DAO().sqlSELECT("SELECT * FROM InvoicesMetaData WHERE DisplayOrder>0 ORDER BY DisplayOrder ASC ",1,false,false);
    }

    @Override
    public String render() throws IOException, TemplateException, ClassNotFoundException, SQLException {
        Template template = getTemplate("Parts/DBview/Header.ftl");
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
        replaceMap.put("previous", (this.pageNr < 1) ? "1": this.pageNr - 1);
        replaceMap.put("pageNr", this.pageNr);
        replaceMap.put("next", this.pageNr + 1);
        replaceMap.put("tableHeader", (tabHeader) ? tableHeader:"");

        return process(template);
    }

    private String getFilterList() throws IOException, ClassNotFoundException, TemplateException {
        Template template = getTemplate("Parts/DBview/Header_FilterList.ftl");
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
        Template template = getTemplate("Parts/DBview/Header_tableHeader.ftl");

        for (String[] s:invoicesMetaData) {
            replaceMap.put("className", s[1]);
            replaceMap.put("viewName", s[4]);

            template.process(replaceMap,retVal);

            tableHeader = retVal.toString();
        }

        clearData();

        return tableHeader;
    }
}
