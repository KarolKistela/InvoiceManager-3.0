package View.Renderers;

import Controller.Controller;
import Model.CSVFile;
import Model.DAO.InvoiceManagerCFG;
import View.FreeMarkerTemplate;
import View.Renderer;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.sql.SQLException;

import static Controller.Controller.CFG_PATH;


/**
 * Created by Karol Kistela on 01-Jun-16.
 */
public class SaveView extends FreeMarkerTemplate implements Renderer {
    private final String ftlFile = "FTL templates/SaveView/SaveView.ftl";
    private final String headerFTL = "FTL templates/SaveView/Header.ftl";
    private final String headerTableHeaderWithoutSortFTL = "FTL templates/SaveView/Header_tableHeaderWithoutSort.ftl";
    private final String saveViewFTL = "FTL templates/SaveView/SaveViewBody.ftl";

    private final String viewTitle = "Save";
    private boolean fileGenerated;
    private String errorMsg = new String();
    private String csvFilePath;

    public SaveView() throws ClassNotFoundException, UnsupportedEncodingException {
        super();
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
        replaceMap.put("Header", new Header(5,
                                            "/save",
                                            1,
                                            this.viewTitle).render());
        if (fileGenerated) {
            replaceMap.put("SettingsInputForm", new SaveViewBody().render(csvFilePath));
        } else {
            replaceMap.put("SettingsInputForm", new SaveViewBody().renderErrorMsg(errorMsg));
        }
        replaceMap.put("Footer", new Footer().render());

        return process(template);
    }

    private class Header extends FreeMarkerTemplate implements Renderer {

        private int menuButtonActive;
        private String rout;
        private int pageNr;
        private int nrOfRecords;
        private int totalPages;
        private String viewTitle;
        private boolean tabHeader;
        private InvoiceManagerCFG ImCFG;

        public Header(int menuButtonActive, String rout, int pageNr, String viewTitle) throws ClassNotFoundException, SQLException {
            super();
            this.ImCFG = new InvoiceManagerCFG(CFG_PATH);
            this.menuButtonActive = menuButtonActive;
            this.rout = rout;
            this.pageNr = pageNr;
            this.nrOfRecords = 0;
            this.totalPages = 0;
            this.viewTitle = viewTitle;
        }

        @Override
        public String render() throws IOException, TemplateException, ClassNotFoundException, SQLException {
            Template template = getTemplate(headerFTL);
            String filterList = "";
            String supplierList = "";
            String tableHeader = this.getTableHeaderWithoutSort();

            replaceMap.put("commentOnMenu1","");
            replaceMap.put("commentOnMenu2","");
            replaceMap.put("commentOnMenu3","<!--");
            replaceMap.put("commentOnMenu4","");
            replaceMap.put("commentOnMenu5","");
            replaceMap.put("commentOffMenu1","");
            replaceMap.put("commentOffMenu2","");
            replaceMap.put("commentOffMenu3","-->");
            replaceMap.put("commentOffMenu4","");
            replaceMap.put("commentOffMenu5","");
            replaceMap.put("commentOnAdvSearch", "<!--");
            replaceMap.put("commentOffAdvSearch", "-->");
            replaceMap.put("commentOnSearch", "<!--");
            replaceMap.put("commentOffSearch", "-->");
            // comment part of HTML code for pagination
            replaceMap.put("paginationOff1", "<!--");
            replaceMap.put("paginationOff2", "--><p></p>");
            replaceMap.put("pagePreviousOff1", "");
            replaceMap.put("pagePreviousOff2", "");
            replaceMap.put("pageNextOff1", "");
            replaceMap.put("pageNextOff2", "");


            replaceMap.put("filterList", filterList);
            replaceMap.put("NetID",ImCFG.getUserNetID());
            replaceMap.put("supplierList", supplierList);
            replaceMap.put("menu1", (menuButtonActive == 1) ? " IM-menu-active" : "");
            replaceMap.put("menu2", (menuButtonActive == 2) ? " IM-menu-active" : "");
            replaceMap.put("menu3", (menuButtonActive == 3) ? " IM-menu-active" : "");
            replaceMap.put("menu4", (menuButtonActive == 4) ? " IM-menu-active" : "");
            replaceMap.put("menu5", (menuButtonActive == 5) ? " IM-menu-active" : "");
            replaceMap.put("viewTitle", this.viewTitle);
            replaceMap.put("records", this.nrOfRecords);
            replaceMap.put("rout", this.rout);
            replaceMap.put("previous", (this.pageNr < 1) ? "1" : Integer.toString(this.pageNr - 1).replace(",", ""));
            if (this.totalPages != 0) {
                replaceMap.put("pageNr", Integer.toString(this.pageNr).replace(",", "") + "/" + Integer.toString(this.totalPages).replace(",", ""));
            } else {    // case when total nr of pages is not required
                replaceMap.put("pageNr", Integer.toString(this.pageNr).replace(",", ""));
            }
            replaceMap.put("next", Integer.toString(this.pageNr + 1).replace(",", ""));
            replaceMap.put("tableHeader", (tabHeader) ? tableHeader : "");

            return process(template);
        }

        private String getTableHeaderWithoutSort() throws IOException, ClassNotFoundException, SQLException, TemplateException {
            String tableHeader = "";
            Template template = getTemplate(headerTableHeaderWithoutSortFTL);

            for (String[] s : ImCFG.getInvoicesMetaData()) {
                replaceMap.put("className", s[1]);
                replaceMap.put("viewName", s[4]);

                template.process(replaceMap, retVal);
            }

            tableHeader = retVal.toString();

            clearData();

            return tableHeader;
        }
    }

    private class SaveViewBody extends FreeMarkerTemplate implements Renderer {

        public SaveViewBody() throws ClassNotFoundException {
            super();
        }

        @Override
        public String render() throws IOException, TemplateException, ClassNotFoundException, SQLException {
            Template template = getTemplate(saveViewFTL);

            replaceMap.put("sqlQuery", "");
            replaceMap.put("commentON", "");
            replaceMap.put("commentOFF", "");
            replaceMap.put("csvFilePath", "");
            replaceMap.put("csvFilePathURL", "");

            return process(template);
        }

        public String render(String filePath) throws IOException, TemplateException, ClassNotFoundException, SQLException {
            Template template = getTemplate(saveViewFTL);

            replaceMap.put("sqlQuery", "File was saved to:");
            replaceMap.put("commentON", "");
            replaceMap.put("commentOFF", "");
            replaceMap.put("csvFilePath", filePath);
            replaceMap.put("csvFilePathURL", URLEncoder.encode(filePath.substring(0,filePath.lastIndexOf("\\")),"UTF-8"));

            return process(template);
        }

        public String renderErrorMsg(String errorMsg) throws IOException, TemplateException {
            Template template = getTemplate(saveViewFTL);

            replaceMap.put("sqlQuery", "ups... Something went wrong: " + errorMsg);
            replaceMap.put("commentON", "<!--");
            replaceMap.put("commentOFF", "-->");
            replaceMap.put("csvFilePath", "");
            replaceMap.put("csvFilePathURL", "");

            return process(template);
        }
    }
}
