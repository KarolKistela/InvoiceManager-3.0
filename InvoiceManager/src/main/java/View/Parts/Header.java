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
    private int pageNr;
    private String viewTitle;
    private boolean tabHeader;

    public static void main(String[] args) throws ClassNotFoundException, TemplateException, SQLException, IOException {
        Header h = new Header(1,1,"DB Main View", true);

        System.out.println(h.render());
    }

    public Header(int menuButtonActive, int pageNr, String viewTitle, boolean tabHeader) {
        super();
        this.menuButtonActive = menuButtonActive;
        this.pageNr = pageNr;
        this.viewTitle = viewTitle;
        this.tabHeader = tabHeader;
    }

    @Override
    public String render() throws IOException, TemplateException, ClassNotFoundException, SQLException {
        Template template = getTemplate("Parts/DBview/Header.ftl");
        String filterList = this.getFilterList();
        String tableHeader = this.getTableHeader();

        replaceMap.put("filterList", filterList);
        replaceMap.put("menu1", (menuButtonActive == 1) ? " IM-menu-active":"");
        replaceMap.put("menu2", (menuButtonActive == 2) ? " IM-menu-active":"");
        replaceMap.put("menu3", (menuButtonActive == 3) ? " IM-menu-active":"");
        replaceMap.put("menu4", (menuButtonActive == 4) ? " IM-menu-active":"");
        replaceMap.put("menu5", (menuButtonActive == 5) ? " IM-menu-active":"");
        replaceMap.put("viewTitle", this.viewTitle);
        replaceMap.put("previous", this.pageNr - 1);
        replaceMap.put("pageNr", this.pageNr);
        replaceMap.put("next", this.pageNr + 1);
        replaceMap.put("tableHeader", (tabHeader) ? tableHeader:"");

        return process(template);
    }

    private String getFilterList() throws IOException, ClassNotFoundException, TemplateException {
        Template template = getTemplate("Parts/DBview/Header_FilterList.ftl");
        InvoiceManagerCFG ImCFG = new InvoiceManagerCFG();
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
        List<String[]> invoicesMetaData = new InvoiceManagerDB_DAO().sqlSELECT("SELECT * FROM InvoicesMetaData WHERE DisplayOrder>0 ORDER BY DisplayOrder ASC ",1,false);

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
