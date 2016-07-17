package Controller;

/**
 * Created by Karol Kistela on 16-Jul-16.
 *
 * Configuration class of Invoices Manager 3.0. Currently implemented default constructor, in future will be possibility to load data from JSON file.
 * This class stores global constants used by other classes. Class is static class in @Controller.
 */
public class Config {
    /**
     * Wwhich port will be used for communicating
     */
    public Integer  PORT;
    /**
     * Path to SQLITE database. This db is used as a serialization for InvoicesManagerCFG class.
     */
    public String   CFG_PATH;
    /**
     * Path to folder where Invoices database dump to .csv is stored
     */
    public String   CSV_EXPORT_FOLDER_PATH;
    /**
     * Outlook .OTM file with macros for connecting wtih Invoices Manager. During the start of the Invoices Manager this file is copied to %appdata%/Microsoft/Outlook/
     */
    public String   OUTLOOK_OTM_PATH;
    /**
     * Path to folder from where Spark will get all files for eg.: css, JS, JPGE ...
     */
    public String   EXTERNAL_FOLDER_PATH;
    /**
     * Temp folder. Files here can be deleted
     */
    public String   TEMP_FOLDER;
    /**
     * if set up to true: then you can only view DB, and change only Finnance Comments column
     * if set up to false: read/write access
     */
    public boolean  FINANCE_VIEW;
    /**
     * if value true: then during start of the program .OTM file will be copied to %appdata%/Microsoft/Outlook/
     * if value false: .OTM file will not be copied
     */
    public boolean  COPY_OTM;
    /**
     * How many records will be displayed on one page
     */
    public Integer RECORDS_PER_PAGE;

    /**
     * Background color of HTML
     */
    public String BACKGROUND_COLOR;
    /**
     * Default Order by clause used in all queries against InvoicesManager.db
     */
    public String ORDER_BY_CLAUSE;
    /**
     * Deprecited. It was used as a option to check or not to check for invoice duplicates. After tests it was set up permanently to true
     */
    public Boolean CHECK_FOR_INV_DUPLICATES;

    public Config() {
        PORT = 8082;
        CFG_PATH = "C:\\InvoicesManager\\SQLITE\\InvoiceManagerCFG\\InvoicesManager.cfg";
        CSV_EXPORT_FOLDER_PATH = "C:\\InvoicesManager\\SQLITE\\DBcsvExport";
        OUTLOOK_OTM_PATH = "C:\\InvoicesManager\\IM\\resources\\OutlookVBA\\VbaProject.OTM";
        EXTERNAL_FOLDER_PATH = "C:\\InvoicesManager\\IM\\resources";
        TEMP_FOLDER = "C:\\InvoicesManager\\Temp";
        FINANCE_VIEW = false;
        COPY_OTM = false;
        RECORDS_PER_PAGE = 250;
        BACKGROUND_COLOR = "#eee";
        ORDER_BY_CLAUSE = "ORDER BY ID DESC";
        CHECK_FOR_INV_DUPLICATES = true;
    }

    public Config(String JSONfilePath) {
        //TODO: implament referencing config from JSON file;
    }
}
