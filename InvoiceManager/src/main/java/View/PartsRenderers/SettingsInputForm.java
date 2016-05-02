package View.PartsRenderers;

import Model.InvoiceManagerCFG;
import Model.User;
import View.FreeMarkerTemplate;
import View.Renderer;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.IOException;
import java.sql.SQLException;

/**
 * Created by Karol Kistela on 02-May-16.
 */
public class SettingsInputForm extends FreeMarkerTemplate implements Renderer {
    private final String settingsInputFormFTL = "Parts/Settings/SettingsInputForm.ftl";
//    private final InvoiceManagerCFG ImCFG;
    private final User user;


    public static void main(String[] args) throws ClassNotFoundException, TemplateException, IOException, SQLException {
        SettingsInputForm s = new SettingsInputForm();

        System.out.println(s.render());
    }

    public SettingsInputForm() throws ClassNotFoundException, SQLException {
        super();
        this.user = new User(ImCFG.getUserNetID(),ImCFG.getUserEmail(),ImCFG.getUserColor());
    }

    @Override
    public String render() throws IOException, TemplateException, ClassNotFoundException, SQLException {
        Template template = getTemplate(this.settingsInputFormFTL);
        String orderBy = ImCFG.getOrderByClause().replace("ORDER BY ","").replace(" DESC","").replace(" ASC","");
        String order = ImCFG.getOrderByClause().substring(ImCFG.getOrderByClause().lastIndexOf(" ") + 1, ImCFG.getOrderByClause().length());

        // TODO: remove not null checks: in database make sure no null values are stored for eg define not nuul or default values for all columns

        if (ImCFG.getImExternalFolderPath().equals(null) || ImCFG.getImExternalFolderPath().equals("")) {
            replaceMap.put("value_imExternalFolderPath", "");
            replaceMap.put("activeClass_imExternalFolderPath", "");
        } else {
            replaceMap.put("value_imExternalFolderPath",ImCFG.getImExternalFolderPath());
            replaceMap.put("activeClass_imExternalFolderPath", "active");
        }
        if (ImCFG.getImDBPath().equals(null) || ImCFG.getImDBPath().equals("")) {
            replaceMap.put("value_imDBPath", "");
            replaceMap.put("activeClass_imDBPath", "");
        } else {
            replaceMap.put("value_imDBPath",ImCFG.getImDBPath());
            replaceMap.put("activeClass_imDBPath", "active");
        }
        if (ImCFG.getRowsPerPage() == null || ImCFG.getRowsPerPage() < 5) {
            replaceMap.put("value_rowsPerPage", 5);
        } else if (ImCFG.getRowsPerPage() > 250){
            replaceMap.put("value_rowsPerPage",250);
        } else {
            replaceMap.put("value_rowsPerPage", ImCFG.getRowsPerPage());
        }
        if (orderBy.equals(null) || orderBy.equals("")) {
            replaceMap.put("placeHolder_orderBy", "ID");
        } else {
            replaceMap.put("placeHolder_orderBy", orderBy);
        }
        if (order.equals(null) || order.equals("")) {
            replaceMap.put("order_chcked", "checked");
        } else if (order.equalsIgnoreCase("asc")){
            replaceMap.put("order_chcked", "");
        } else {
            replaceMap.put("order_chcked", "checked");
        }
        if (ImCFG.isCheckForInvDuplicates()) {
            replaceMap.put("duplicates_chcked", "checked");
        } else {
            replaceMap.put("duplicates_chcked", "");
        }
        if (ImCFG.getUserNetID().equals(null) || ImCFG.getUserNetID().equals("")){
            replaceMap.put("value_userID", "");
            replaceMap.put("activeClass_userID", "");
        } else {
            replaceMap.put("value_userID", user.getUserID());
            replaceMap.put("activeClass_userID", "active");
        }
        if (user.getUserMail().equals("")) {
            replaceMap.put("value_userEmail","");
            replaceMap.put("activeClass_userID","");
        } else {
            replaceMap.put("value_userEmail",user.getUserMail());
            replaceMap.put("activeClass_userID","active");
        }
        if (user.getUserColor().equals("")) {
            replaceMap.put("value_userColor","#888888");
        } else {
            replaceMap.put("value_userColor",user.getUserColor());
        }


        return process(template);
    }
}
