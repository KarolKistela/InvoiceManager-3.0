package View;

import View.Parts.DBTable;
import View.Parts.Header;
import View.Parts.Style;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import spark.Request;

import java.io.IOException;
import java.sql.SQLException;

import static Model.Helpers.sqlQueryConstructor;

/**
 * Created by Karol Kistela on 30-Apr-16.
 */
public class SelectWhereView extends FreeMarkerTemplate implements Renderer{
    private final String viewTitle = "Filter view";
    private final String ftlFile = "DBview.ftl";
    private boolean tabHeader = true;
    private final boolean pagination = true;
    private final int menuButtonActive = 3;
    private String rout;
    private int pageNr;
    private String sqlQuery;
;

    public SelectWhereView(Request request) {
        super();
        this.rout = request.pathInfo().substring(0,request.pathInfo().lastIndexOf("/")+1);
        this.pageNr = Integer.parseInt(request.params("pageNr").replace(",",""));
        this.sqlQuery = sqlQueryConstructor(request);
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
