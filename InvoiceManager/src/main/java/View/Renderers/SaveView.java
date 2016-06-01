package View.Renderers;

import Controller.Controller;
import Model.CSVFile;
import View.FreeMarkerTemplate;
import View.Renderer;
import View.Renderers.PartCodeRenderers.Header;
import View.Renderers.PartCodeRenderers.SaveViewBody;
import View.Renderers.PartCodeRenderers.Style;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import spark.Request;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;

/**
 * Created by Karol Kistela on 01-Jun-16.
 */
public class SaveView extends FreeMarkerTemplate implements Renderer {
    private final String viewTitle = "Save";
    private final String ftlFile = "Save.ftl";
    private final boolean tabHeader = false;
    private final boolean tabHeaderWithSort = false;
    private final boolean pagination = false;
    private final int menuButtonActive = 5;
    private final int pageNr = 1;
    private String rout;
    private boolean fileGenerated;
    private String errorMsg = new String();
    private String csvFilePath;

    public SaveView(Request request) throws ClassNotFoundException {
        super();
        this.rout = request.pathInfo().substring(0,request.pathInfo().lastIndexOf("/")+1);
        System.out.println("********View.Renderers.SaveView ROUT: " + this.rout + " ********");
        this.fileGenerated = false;
        try {
            CSVFile csvFile = new CSVFile(Controller.sqlQuery);
            this.csvFilePath = csvFile.getPath();
            this.fileGenerated = true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            this.fileGenerated = false;
            this.errorMsg = e.toString();
        } catch (SQLException e) {
            e.printStackTrace();
            this.fileGenerated = false;
            this.errorMsg = e.toString() + "<p>connection to DB lost!</p>";
        } catch (IOException e) {
            e.printStackTrace();
            this.fileGenerated = false;
            this.errorMsg = e.toString() + "<p>another program might be using this file!</p>";
        }
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
                                            this.tabHeaderWithSort,
                                            this.pagination).render());
        if (fileGenerated) {
            replaceMap.put("SettingsInputForm", new SaveViewBody().render(csvFilePath));
        } else {
            replaceMap.put("SettingsInputForm", new SaveViewBody().renderErrorMsg(errorMsg));
        }
        replaceMap.put("Footer", getTemplate("/Parts/Footer.ftl").toString());

        return process(template);
    }
}
