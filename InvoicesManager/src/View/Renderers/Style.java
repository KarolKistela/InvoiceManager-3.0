package View.Renderers;

import View.FreeMarkerTemplate;
import View.Renderer;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.IOException;
import java.sql.SQLException;

import static Controller.Controller.ImCFG;
import static Controller.Controller.config;

/**
 * Created by Karol Kistela on 28-Apr-16.
 */
public class Style extends FreeMarkerTemplate implements Renderer {
    private final String styleFTL = "FTL templates/Style/Style.ftl";
    private final String stylColumnClassesFTL = "FTL templates/Style/Style_columnClasses.ftl";

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        Style s = new Style();

        try {
            System.out.println(s.render());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Style() throws ClassNotFoundException, SQLException {
        super();
    }

    @Override
    public String render() throws IOException, TemplateException, ClassNotFoundException, SQLException {
        Template template = getTemplate(this.styleFTL);
        String columnStyle = this.getColumnClasses();

        replaceMap.put("backGroundColor", config.BACKGROUND_COLOR);
        replaceMap.put("columnStyle", columnStyle);

        return process(template);
    }

    private String getColumnClasses() throws IOException, ClassNotFoundException, SQLException, TemplateException {
        String columnClasses = "";
        Template template = getTemplate(this.stylColumnClassesFTL);

        for (String[] s:ImCFG.getInvoicesMetaData()) {
            replaceMap.put("className", s[1]);
            replaceMap.put("styling", s[3]);

            template.process(replaceMap,retVal);

            columnClasses = retVal.toString();
        }
        clearData();
        return columnClasses;
    }
}
