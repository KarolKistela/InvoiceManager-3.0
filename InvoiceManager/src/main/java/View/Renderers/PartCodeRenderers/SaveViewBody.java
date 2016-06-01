package View.Renderers.PartCodeRenderers;

import Controller.Controller;
import Model.CSVFile;
import View.FreeMarkerTemplate;
import View.Renderer;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.IOException;
import java.net.URLEncoder;
import java.sql.SQLException;

/**
 * Created by Karol Kistela on 01-Jun-16.
 */
public class SaveViewBody extends FreeMarkerTemplate implements Renderer {
    private final String saveViewFTL = "Parts/SaveView/SaveViewBody.ftl";

    public SaveViewBody() throws ClassNotFoundException {
        super();
    }

    @Override
    public String render() throws IOException, TemplateException, ClassNotFoundException, SQLException {
        Template template = getTemplate(this.saveViewFTL);

        replaceMap.put("sqlQuery", "");
        replaceMap.put("commentON", "");
        replaceMap.put("commentOFF", "");
        replaceMap.put("csvFilePath", "");
        replaceMap.put("csvFilePathURL", "");

        return process(template);
    }

    public String render(String filePath) throws IOException, TemplateException, ClassNotFoundException, SQLException {
        Template template = getTemplate(this.saveViewFTL);

        replaceMap.put("sqlQuery", "File was saved to:");
        replaceMap.put("commentON", "");
        replaceMap.put("commentOFF", "");
        replaceMap.put("csvFilePath", filePath);
        replaceMap.put("csvFilePathURL", URLEncoder.encode(filePath.substring(0,filePath.lastIndexOf("\\")),"UTF-8"));

        return process(template);
    }

    public String renderErrorMsg(String errorMsg) throws IOException, TemplateException {
        Template template = getTemplate(this.saveViewFTL);

        replaceMap.put("sqlQuery", "ups... Something went wrong: " + errorMsg);
        replaceMap.put("commentON", "<!--");
        replaceMap.put("commentOFF", "-->");
        replaceMap.put("csvFilePath", "");
        replaceMap.put("csvFilePathURL", "");

        return process(template);
    }
}
