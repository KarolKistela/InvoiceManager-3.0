package View.Parts;

import Model.InvoiceManagerCFG;
import Model.InvoiceManagerDB_DAO;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import View.FreeMarkerTemplate;
import View.Renderer;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by Karol Kistela on 28-Apr-16.
 */
public class Style extends FreeMarkerTemplate implements Renderer {

    public static void main(String[] args) {
        Style s = new Style();

        try {
            System.out.println(s.render());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public String render() throws IOException, TemplateException, ClassNotFoundException, SQLException {
        Template template = getTemplate("Parts/DBview/Style.ftl");
        String columnStyl = this.getColumnClasses();

        replaceMap.put("backGroundColor", new InvoiceManagerCFG().getBackgroundColor());
        replaceMap.put("columnStyle", columnStyl);

        return process(template);
    }

    private String getColumnClasses() throws IOException, ClassNotFoundException, SQLException, TemplateException {
        String columnClasses = "";
        Template template = getTemplate("Parts/DBview/Style_columnClasses.ftl");
        List<String[]> invoicesMetaData = new InvoiceManagerDB_DAO().sqlSELECT("SELECT * FROM InvoicesMetaData WHERE DisplayOrder>0 ORDER BY DisplayOrder ASC ",1,false);

        for (String[] s:invoicesMetaData) {
            replaceMap.put("className", s[1]);
            replaceMap.put("styling", s[3]);

            template.process(replaceMap,retVal);

            columnClasses = retVal.toString();
        }
        clearData();
        return columnClasses;
    }
}
