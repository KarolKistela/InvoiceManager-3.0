package View;

import Model.InvoiceManagerCFG;
import View.Parts.DBTable;
import View.Parts.Header;
import View.Parts.Style;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import spark.Request;

import java.io.IOException;
import java.sql.SQLException;

/**
 * Created by Karol Kistela on 01-May-16.
 */
public class SettingsView extends FreeMarkerTemplate implements Renderer {
    private final String viewTitle = "Settings";
    private final String ftlFile = "Settings.ftl";
    private final boolean tabHeader = false;
    private final boolean pagination = false;
    private final int menuButtonActive = 2;
    private final int pageNr = 1;
    private InvoiceManagerCFG ImCFG;
    private String rout;

    public SettingsView(Request request) throws ClassNotFoundException {
        super();
        this.rout = request.pathInfo().substring(0,request.pathInfo().lastIndexOf("/")+1);
        this.ImCFG = new InvoiceManagerCFG();
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

        template.process(replaceMap, retVal);

        return retVal.toString();
    }
}
