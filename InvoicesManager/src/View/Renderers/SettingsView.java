package View.Renderers;

import Model.User;
import View.FreeMarkerTemplate;
import View.Renderer;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import spark.Request;

import java.io.IOException;
import java.sql.SQLException;

import static Controller.Controller.FINANCE_VIEW;
import static Model.Helpers.fileExists;
import static Model.Helpers.isEmail;

/**
 * Created by Karol Kistela on 01-May-16.
 */
public class SettingsView extends FreeMarkerTemplate implements Renderer {
    private final String ftlFile = "FTL templates/SettingsView/Settings.ftl";
    private final String settingsInputFormFTL = "FTL templates/SettingsView/SettingsInputForm.ftl";
    private final String headerFTL = "FTL templates/SettingsView/Header.ftl";

    private final String viewTitle = "Settings";
    private final int menuButtonActive = 2;
    private String rout;

    public SettingsView(Request request) throws ClassNotFoundException {
        super();
        this.rout = request.pathInfo().substring(0,request.pathInfo().lastIndexOf("/")+1);
        System.out.println("********View.Renderers.SettingsView ROUT: " + this.rout + " ********");
    }

    @Override
    public String render() throws IOException, TemplateException, ClassNotFoundException, SQLException {
        Template template = getTemplate(this.ftlFile);

        if (FINANCE_VIEW) {
            replaceMap.put("FINANCE_VIEW"," Reader");
        } else {
            replaceMap.put("FINANCE_VIEW","");
        }

        replaceMap.put("Style", new Style().render());
        replaceMap.put("Header", new Header().render());
        replaceMap.put("SettingsInputForm", new SettingsInputForm().render());
        replaceMap.put("Footer", new Footer().render());

        return process(template);
    }

    private class Header extends FreeMarkerTemplate implements Renderer {

        public Header() throws ClassNotFoundException, SQLException {
            super();
        }

        @Override
        public String render() throws IOException, TemplateException, ClassNotFoundException, SQLException {
            Template template = getTemplate(headerFTL);

            replaceMap.put("commentOnMenu1","");
            replaceMap.put("commentOnMenu2","");
            replaceMap.put("commentOnMenu3","<!--");
            replaceMap.put("commentOnMenu4","");
            replaceMap.put("commentOnMenu5","<!--");
            replaceMap.put("commentOffMenu1","");
            replaceMap.put("commentOffMenu2","");
            replaceMap.put("commentOffMenu3","-->");
            replaceMap.put("commentOffMenu4","");
            replaceMap.put("commentOffMenu5","-->");
            replaceMap.put("commentOnAdvSearch", "<!--");
            replaceMap.put("commentOffAdvSearch", "-->");
            replaceMap.put("commentOnSearch", "<!--");
            replaceMap.put("commentOffSearch", "-->");

            replaceMap.put("paginationOff1", "<!--");
            replaceMap.put("paginationOff2", "--><p></p>");
            replaceMap.put("pagePreviousOff1", "");
            replaceMap.put("pagePreviousOff2", "");
            replaceMap.put("pageNextOff1", "");
            replaceMap.put("pageNextOff2", "");


            replaceMap.put("filterList", "");
            replaceMap.put("NetID",ImCFG.getUserNetID());
            replaceMap.put("supplierList", "");
            replaceMap.put("menu1", (menuButtonActive == 1) ? " IM-menu-active" : "");
            replaceMap.put("menu2", (menuButtonActive == 2) ? " IM-menu-active" : "");
            replaceMap.put("menu3", (menuButtonActive == 3) ? " IM-menu-active" : "");
            replaceMap.put("menu4", (menuButtonActive == 4) ? " IM-menu-active" : "");
            replaceMap.put("menu5", (menuButtonActive == 5) ? " IM-menu-active" : "");
            replaceMap.put("viewTitle", viewTitle);
            replaceMap.put("records", "");
            replaceMap.put("rout", "");
            replaceMap.put("previous", "");
            replaceMap.put("pageNr", "");
            replaceMap.put("next", "");
            replaceMap.put("tableHeader", "");

            return process(template);
        }
    }

    private class SettingsInputForm extends FreeMarkerTemplate implements Renderer {
        private final User user;

        public SettingsInputForm() throws ClassNotFoundException, SQLException {
            super();
            this.user = new User(ImCFG.getUserNetID(),ImCFG.getUserEmail(),ImCFG.getUserColor());
        }

        @Override
        public String render() throws IOException, TemplateException, ClassNotFoundException, SQLException {
            Template template = getTemplate(settingsInputFormFTL);
            String orderBy = ImCFG.getOrderByClause().replace("ORDER BY ","").replace(" DESC","").replace(" ASC","");
            String order = ImCFG.getOrderByClause().substring(ImCFG.getOrderByClause().lastIndexOf(" ") + 1, ImCFG.getOrderByClause().length());

            if (ImCFG.getImExternalFolderPath().equals("")) {
                replaceMap.put("value_imExternalFolderPath", "");
                replaceMap.put("activeClass_imExternalFolderPath", "");
            } else {
                replaceMap.put("value_imExternalFolderPath",ImCFG.getImExternalFolderPath());
                replaceMap.put("activeClass_imExternalFolderPath", "active");
            }
            if (fileExists(ImCFG.getImDBPath())) {
                replaceMap.put("dbExists",";color: dodgerblue");
            } else {
                replaceMap.put("dbExists","\"");
            }
            if (ImCFG.getImDBPath().equals("")) {
                replaceMap.put("value_imDBPath", "");
                replaceMap.put("activeClass_imDBPath", "");
            } else {
                replaceMap.put("value_imDBPath",ImCFG.getImDBPath());
                replaceMap.put("activeClass_imDBPath", "active");
            }
            if (fileExists(ImCFG.getImExternalFolderPath()+"IM_checker.txt")) {
                replaceMap.put("folderExists",";color: dodgerblue");
            } else {
                replaceMap.put("folderExists","\"");
            }
            if (ImCFG.getRowsPerPage() < 5) {
                replaceMap.put("value_rowsPerPage", 5);
                replaceMap.put("rowsSetup", "\"");
            } else if (ImCFG.getRowsPerPage() > 250){
                replaceMap.put("value_rowsPerPage",250);
                replaceMap.put("rowsSetup", ";color: dodgerblue");
            } else {
                replaceMap.put("value_rowsPerPage", ImCFG.getRowsPerPage());
                replaceMap.put("rowsSetup", ";color: dodgerblue");
            }
            if (orderBy.equals("")) {
                replaceMap.put("placeHolder_orderBy", "ID");
            } else {
                replaceMap.put("placeHolder_orderBy", orderBy);
            }
            if (order.equals("")) {
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
            if (ImCFG.getUserNetID().equals("")){
                replaceMap.put("value_userID", "");
                replaceMap.put("activeClass_userID", "");
            } else {
                replaceMap.put("value_userID", user.getUserID());
                replaceMap.put("activeClass_userID", "active");
            }
            if (user.getUserID().length() == 6) {
                replaceMap.put("netIDok", ";color: dodgerblue");
            } else {
                replaceMap.put("netIDok", "\"");
            }

            if (user.getUserMail().equals("")) {
                replaceMap.put("value_userEmail","");
                replaceMap.put("activeClass_userEmail","");
                replaceMap.put("isEmail","\"");
            } else {
                replaceMap.put("value_userEmail",user.getUserMail());
                replaceMap.put("activeClass_userEmail","active");
                if (isEmail(user.getUserMail())) {
                    replaceMap.put("isEmail", ";color: dodgerblue");
                } else {
                    replaceMap.put("isEmail", "\"");
                }
            }
            if (user.getUserColor().equals("")) {
                replaceMap.put("value_userColor","#888888");
            } else {
                replaceMap.put("value_userColor",user.getUserColor());
            }

            try {
                if (ImCFG.getDNSserver().equals("")) {
                    replaceMap.put("value_DNSserver", "");
                    replaceMap.put("activeClass_DNSserver", "");
                    replaceMap.put("DNSserverIconColor", "");
                } else {
                    replaceMap.put("value_DNSserver", ImCFG.getDNSserver());
                    replaceMap.put("activeClass_DNSserver", "active");
                    replaceMap.put("DNSserverIconColor", ";color: dodgerblue");
                }
            } catch (Exception e) {
                replaceMap.put("value_DNSserver", "");
                replaceMap.put("activeClass_DNSserver", "");
                replaceMap.put("DNSserverIconColor", "");
            }

            try {
                if (ImCFG.getDNSjdbcClass().equals("")) {
                    replaceMap.put("value_DNSjdbcClass", "");
                    replaceMap.put("activeClass_DNSjdbcClass", "");
                } else {
                    replaceMap.put("value_DNSjdbcClass", ImCFG.getDNSjdbcClass());
                    replaceMap.put("activeClass_DNSjdbcClass", "active");
                }
            } catch (Exception e) {
                replaceMap.put("value_DNSjdbcClass", "");
                replaceMap.put("activeClass_DNSjdbcClass", "");
            }

            try {
                if (ImCFG.getDNSuser().equals("")) {
                    replaceMap.put("value_DNSuser", "");
                    replaceMap.put("activeClass_DNSuser", "");
                    replaceMap.put("DNSuserIcon", "");
                } else {
                    replaceMap.put("value_DNSuser", ImCFG.getDNSuser());
                    replaceMap.put("activeClass_DNSuser", "active");
                    replaceMap.put("DNSuserIcon", ";color: dodgerblue");
                }
            } catch (Exception e) {
                replaceMap.put("value_DNSuser", "");
                replaceMap.put("activeClass_DNSuser", "");
                replaceMap.put("DNSuserIcon", "");
            }

            try {
                if (ImCFG.getDNSpass().equals("")) {
                    replaceMap.put("value_DNSpass", "");
                    replaceMap.put("activeClass_DNSpass", "");
                    replaceMap.put("DNSpassIcon", "");
                } else {
                    replaceMap.put("value_DNSpass", ImCFG.getDNSpass());
                    replaceMap.put("activeClass_DNSpass", "active");
                    replaceMap.put("DNSpassIcon", ";color: dodgerblue");
                }
            } catch (Exception e) {
                replaceMap.put("value_DNSpass", "");
                replaceMap.put("activeClass_DNSpass", "");
                replaceMap.put("DNSpassIcon", "");
            }
//            outlookExePathIconColor value_outlookExePath activeClass_outlookExePath
            try {
                if (ImCFG.getOutlookExePath().equals("")) {
                    replaceMap.put("value_outlookExePath", "");
                    replaceMap.put("activeClass_outlookExePath", "");
                    replaceMap.put("outlookExePathIconColor", "");
                } else {
                    replaceMap.put("value_outlookExePath", ImCFG.getOutlookExePath());
                    replaceMap.put("activeClass_outlookExePath", "active");
                    replaceMap.put("outlookExePathIconColor", ";color: dodgerblue");
                }
            } catch (Exception e) {
                replaceMap.put("value_outlookExePath", "");
                replaceMap.put("activeClass_outlookExePath", "");
                replaceMap.put("outlookExePathIconColor", "");
            }

            if (FINANCE_VIEW) { // it will coment out part of input form with user data, Finance dont use it
                replaceMap.put("financeView1","<!--");
                replaceMap.put("financeView2","-->");
            } else {
                replaceMap.put("financeView1","");
                replaceMap.put("financeView2","");
            }

            return process(template);
        }
    }
}
