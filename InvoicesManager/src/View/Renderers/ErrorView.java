package View.Renderers;

import Model.DAO.InvoiceManagerCFG;
import View.FreeMarkerTemplate;
import View.Renderer;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.List;

import static Controller.Controller.config;
import static Controller.Controller.logger;
import static Model.Reversed.reversed;

/**
 * Created by Karol Kistela on 16-Jul-16.
 */
public class ErrorView extends FreeMarkerTemplate implements Renderer {
    private final String ftlFile = "FTL templates/ErrorView/ErrorView.ftl";
    private final String headerFTL = "FTL templates/ErrorView/Header.ftl";
    private final String headerTableHeaderWithoutSortFTL = "FTL templates/ErrorView/Header_tableHeaderWithoutSort.ftl";
    private final String errorViewFTL = "FTL templates/ErrorView/ErrorViewBody.ftl";

    private final String viewTitle = "error";
    private boolean fileGenerated;
//    private String errorMsg = new String();
//    private String csvFilePath;

    public ErrorView() throws ClassNotFoundException, UnsupportedEncodingException {
        super();
        this.fileGenerated = false;
        // TODO: creating log file and sending to me
    }

    @Override
    public String render() throws IOException, TemplateException, ClassNotFoundException, SQLException {
        Template template = getTemplate(this.ftlFile);

        if (config.FINANCE_VIEW) {
            replaceMap.put("FINANCE_VIEW", " Reader");
        } else {
            replaceMap.put("FINANCE_VIEW", "");
        }

        replaceMap.put("Style", new Style().render());
        replaceMap.put("Header", new ErrorView.Header(5,
                "/save",
                1,
                this.viewTitle).render());
        replaceMap.put("error", new ErrorMessage().render());
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
            this.ImCFG = new InvoiceManagerCFG(config.CFG_PATH);
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

            replaceMap.put("commentOnMenu1", "");
            replaceMap.put("commentOnMenu2", "");
            replaceMap.put("commentOnMenu3", "<!--");
            replaceMap.put("commentOnMenu4", "");
            replaceMap.put("commentOnMenu5", "");
            replaceMap.put("commentOffMenu1", "");
            replaceMap.put("commentOffMenu2", "");
            replaceMap.put("commentOffMenu3", "-->");
            replaceMap.put("commentOffMenu4", "");
            replaceMap.put("commentOffMenu5", "");
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
            replaceMap.put("NetID", ImCFG.getUserNetID());
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

    private class ErrorMessage extends FreeMarkerTemplate implements Renderer {
        Template template = getTemplate(errorViewFTL);

        public ErrorMessage() throws ClassNotFoundException, IOException {
            super();
        }

        @Override
        public String render() throws IOException, TemplateException, ClassNotFoundException, SQLException {
            String retVal = "";

            List<String> ls = logger.getExceptionList();

            for (String s : reversed(ls)) {
                if (s.contains("!")) {
                    if (s.substring(10,15).contains("at")) {
                        String t = s.substring(10,s.length());
                        retVal += "<p style=\"color:red; padding-left: 100px\">" + t.replace("!", "") + "</p>";
                    } else {
                        retVal += "<p style=\"color:red\">" + s.replace("!", "") + "</p>";
                    }
                } else {
                    retVal += "<p>" + s + "</p>";
                }
            }

            replaceMap.put("errorLog", retVal);

            return process(template);
        }
    }
}
