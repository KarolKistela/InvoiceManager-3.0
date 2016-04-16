package Model;

import freemarker.template.Configuration;
import freemarker.template.SimpleHash;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.IOException;
import java.io.StringWriter;

/**
 * Created by Karol Kistela on 14-Apr-16.
 * TODO: after moving out template processing from WebAppController: change retval to String
 * TODO: move out tempates of src code, so templates can be changed without recompiling the code - low priority
 */
public class TemplateEngine {
    private final Configuration cfg = new Configuration();
    private final InvoiceManagerCFG ImCFG = new InvoiceManagerCFG();
    private final InvoiceManagerDB_DAO InvoiceManagerDB_DAO;

    public TemplateEngine() throws ClassNotFoundException {
        cfg.setClassForTemplateLoading(TemplateEngine.class, "/HTML Parts/");
        InvoiceManagerDB_DAO = new InvoiceManagerDB_DAO(this.ImCFG.getImDBPath());
    }

    public StringWriter getStyle() throws IOException, TemplateException {
        SimpleHash replaceMap = new SimpleHash();
        StringWriter HEAD = new StringWriter();
        Template template = cfg.getTemplate("style.ftl");

        replaceMap.put("backGroundColor", this.ImCFG.getBackgroundColor());
        replaceMap.put("IMcolumnClasses", this.getColumnStyleClasses());

        template.process(replaceMap,HEAD);

        return HEAD;
    }

    public StringWriter getHeader1rowed(String viewName, int activateMenuItemNR, boolean pagination, int pageNR) throws IOException, TemplateException {
        SimpleHash replaceMap = new SimpleHash();
        StringWriter header2rowed = new StringWriter();
        Template template = cfg.getTemplate("header1rowed.ftl");

        if (pagination) {
            replaceMap.put("previous", (pageNR == 1) ? "#" : pageNR - 1);
            replaceMap.put("pagination", pageNR);
            replaceMap.put("next", pageNR + 1);
        } else {
            replaceMap.put("previous", "#");
            replaceMap.put("pagination", "");
            replaceMap.put("next", "#");
        }
        replaceMap.put("IMmenuActive1",(activateMenuItemNR == 1)? "IM-menu-active":"");
        replaceMap.put("IMmenuActive2",(activateMenuItemNR == 2)? "IM-menu-active":"");
        replaceMap.put("IMmenuActive3",(activateMenuItemNR == 3)? "IM-menu-active":"");
        replaceMap.put("viewTitle", viewName);

        template.process(replaceMap, header2rowed);

        return header2rowed;
    }

    public StringWriter getHeader2rowed(String viewName, int activateMenuItemNR, int pageNr, int totalPages) throws IOException, TemplateException {
        SimpleHash replaceMap = new SimpleHash();
        StringWriter header2rowed = new StringWriter();
        Template template = cfg.getTemplate("header2rowed.ftl");

        replaceMap.put("previous",(pageNr == 1) ? "#" : pageNr - 1);
        replaceMap.put("pagination", (pageNr + " / " + totalPages));
        replaceMap.put("next", pageNr + 1);
        replaceMap.put("IMmenuActive1",(activateMenuItemNR == 1)? "IM-menu-active":"");
        replaceMap.put("IMmenuActive2",(activateMenuItemNR == 2)? "IM-menu-active":"");
        replaceMap.put("IMmenuActive3",(activateMenuItemNR == 3)? "IM-menu-active":"");
        replaceMap.put("viewTitle", viewName);
        replaceMap.put("tabHeaderRows", this.getTabHeaderRowHTML());
        replaceMap.put("tableWidth", ImCFG.getTableWidth());
        replaceMap.put("headerWidth", 100 - ImCFG.getTableWidth());

        template.process(replaceMap, header2rowed);

        return header2rowed;
    }

    private String getTabHeaderRowHTML(){
        String retVal = "";
        //  TODO: use template for this not String
        String tabRowTemplate =
        "                <td class=\"IM-columnClass\">columnName</td>\n";

        for (Object K:this.ImCFG.getSetOfColumns()) {
            retVal = retVal + tabRowTemplate;
            retVal = retVal.replace("columnClass", (CharSequence) K)
                        .replace("columnName", (CharSequence) K);
            }
        retVal = retVal.replace("\">Details</","\"></"); // remove details column name, its not needed in this place
        return retVal;
    }

    private String getColumnStyleClasses() {
        String retVal = "";
        //  TODO: use template for this not String
        String columnClassTemplate =
        "        .IM-columnName {\n" +
        "            width: columnWidth%\n" +
        "        }\n";

        for (Object K:this.ImCFG.getSetOfColumns()) {
            retVal = retVal + columnClassTemplate;
            retVal = retVal.replace("columnWidth", String.valueOf(this.ImCFG.getColumnWidth(K)))
                            .replace("columnName", (CharSequence) K);
        }

        return retVal;
    }
}
