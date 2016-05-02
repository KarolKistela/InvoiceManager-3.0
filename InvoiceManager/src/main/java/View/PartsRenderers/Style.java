package View.PartsRenderers;

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
    private final String styleFTL = "Parts/DBview/Style.ftl";
    private final String stylColumnClassesFTL = "Parts/DBview/Style_columnClasses.ftl";

//    private List<String[]> invoicesMetaData;

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
//        this.invoicesMetaData = ImCFG.getInvoicesMetaData();
//                new InvoiceManagerDB_DAO().sqlSELECT("SELECT * FROM InvoicesMetaData WHERE DisplayOrder>0 ORDER BY DisplayOrder ASC ",1,false,false);
    }

    @Override
    public String render() throws IOException, TemplateException, ClassNotFoundException, SQLException {
        Template template = getTemplate(this.styleFTL);
        String columnStyle = this.getColumnClasses();

        replaceMap.put("backGroundColor", ImCFG.getBackgroundColor());
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
