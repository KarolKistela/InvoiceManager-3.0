package View;

import View.Parts.DBTable;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import View.Parts.Header;
import View.Parts.Style;

import java.io.IOException;
import java.io.StringWriter;
import java.sql.SQLException;

/**
 * Created by Karol Kistela on 27-Apr-16.
 *
 * This class render HTML page for viewing results of SELECT query (1 or more rows). It covers both DB main view and Filter views.
 * Per page will be displayed limited nr of rows (according to settings). However this is carry out by htmlFactory
 */
public class DBview extends FreeMarkerTemplate implements Renderer {
    private int menuButtonActive;
    private int pageNr;
    private String viewTitle;
    private boolean tabHeader;
    private String sqlQuery;

    public DBview(int menuButtonActive, String viewTitle,int pageNr, boolean tabHeader, String sqlQuery) {
        super();
        this.menuButtonActive = menuButtonActive;
        this.pageNr = pageNr;
        this.viewTitle = viewTitle;
        this.tabHeader = tabHeader;
        this.sqlQuery = sqlQuery;
    }

    @Override
    public String render() throws IOException, TemplateException, ClassNotFoundException, SQLException {
        Template template = getTemplate("DBview.ftl");

        replaceMap.put("Style", new Style().render());
        replaceMap.put("Header", new Header(this.menuButtonActive,
                                            this.pageNr,
                                            this.viewTitle,
                                            this.tabHeader).render());
        replaceMap.put("DBTable", new DBTable(this.sqlQuery,
                                              this.pageNr).render());
        template.process(replaceMap, retVal);

        return retVal.toString();
    }
}
