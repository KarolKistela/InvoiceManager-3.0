package View;

import View.PartsRenderers.DBTable;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import View.PartsRenderers.Header;
import View.PartsRenderers.Style;
import spark.Request;

import java.io.IOException;
import java.sql.SQLException;

/**
 * Created by Karol Kistela on 27-Apr-16.
 *
 * This class render HTML page for viewing results of SELECT query (1 or more rows). It covers both DB main view and Filter views.
 * Per page will be displayed limited nr of rows (according to settings). However this is carry out by htmlFactory
 */
public class DBview extends FreeMarkerTemplate implements Renderer {
    private final String viewTitle = "DB main view";
    private final String ftlFile = "DBview.ftl";
    private final boolean tabHeader = true;
    private final boolean pagination = true;
    private final int menuButtonActive = 1;
    private String rout;
    private int pageNr;
    private String sqlQuery;

    public DBview(Request request) throws ClassNotFoundException {
        super();
        this.rout = request.pathInfo().substring(0,request.pathInfo().lastIndexOf("/")+1);
        this.pageNr = Integer.parseInt(request.params("pageNr").replace(",",""));
        this.sqlQuery = "SELECT * FROM Invoices ";
    }

    @Override
    public String render() throws IOException, TemplateException, ClassNotFoundException, SQLException {
        Template template = getTemplate(this.ftlFile);

        replaceMap.put("Style", new Style().render());
        replaceMap.put("Header", new Header(this.menuButtonActive,
                                            this.rout,
                                            this.pageNr,
                                            this.viewTitle,
                                            this.tabHeader,
                                            this.pagination).render());
        replaceMap.put("DBTable", new DBTable(this.sqlQuery,
                                              this.pageNr).render());
        template.process(replaceMap, retVal);

        return retVal.toString();
    }
}
