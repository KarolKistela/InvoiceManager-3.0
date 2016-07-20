package Model.DAO;

import Model.User;
import spark.Request;

import java.io.FileNotFoundException;
import java.sql.*;
import java.util.LinkedList;
import java.util.List;

import static Controller.Controller.comboList;
import static Controller.Controller.config;
import static Controller.Controller.logger;
import static Model.Helpers.InvoicesManagerDBconnection;

/**
 * Created by Karol Kistela on 07-Apr-16.
 * Configuration Class for Invoice Manager. Data are serialized in ../../resources/InvoiceManagerCFG/InvoiceManager.cfg database.
 */
public class InvoiceManagerCFG {
    /**
     * Path to folder with .tif and .msg files
     */
    private String imExternalFolderPath;
    /**
     * Path to InvoicesManager.db
     */
    private String imDBPath;
    /**
     * Path to outlook.exe. When sending authorization, Im using shell command to setup email.
     */
    private String outlookExePath;
    private int totalNrOfPages;
    private List<String[]> filters;
    private String userNetID;
    private String userEmail;
    private String userColor;
    private List<String[]> invoicesMetaData;
    private List<String> statusMetaData = new LinkedList<>();

    private List<String> currencies = new LinkedList<>();
    private List<String> suppliers = new LinkedList<>();
    private List<String> authContact = new LinkedList<>();
    private List<String> contactGenpact = new LinkedList<>();

    private List<String> rowColor = new LinkedList<>();
    private List<String> processStatus = new LinkedList<>();

    private String DNSserver;
    private String DNSuser;
    private String DNSpass;
    private String DNSjdbcClass;

    public InvoiceManagerCFG(String cfgPath) throws ClassNotFoundException, SQLException {
        this.loadData();
    }

    public void clearData(){
        this.imExternalFolderPath = new String();
        this.imDBPath = new String();
        this.outlookExePath = new String();
        this.totalNrOfPages = new Integer(0);
        this.filters = new LinkedList<>();
        this.userNetID = new String();
        this.userEmail = new String();
        this.userColor = new String();
        this.invoicesMetaData = new LinkedList<>();
        this.statusMetaData = new LinkedList<>();
        this.currencies = new LinkedList<>();
        this.rowColor = new LinkedList<>();
        this.processStatus = new LinkedList<>();
    }

    /* Connetct to db cfg file and load data from this file. Basically serialization for this class */
    public void loadData() throws ClassNotFoundException, SQLException {
        this.clearData();
        // load the sqlite-JDBC driver using the current class loader
        Class.forName("org.sqlite.JDBC");
        Connection connection = null;

        try
        {
            connection = DriverManager.getConnection("jdbc:sqlite:" + config.CFG_PATH);
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);  // set timeout to 30 sec.
            ResultSet resultSet;

            resultSet = statement.executeQuery("SELECT * FROM UserSettings ORDER BY ID DESC LIMIT 1;");
            this.setImExternalFolderPath(resultSet.getString("imExternalFolderPath"));
            this.setImDBPath(resultSet.getString("imDBPath"));
            this.setUserNetID(resultSet.getString("userNetID"));
            this.setUserEmail(resultSet.getString("userEmail"));
            this.setUserColor(resultSet.getString("userColor"));
            this.setDNSserver(resultSet.getString("DNSserver"));
            this.setDNSuser(resultSet.getString("DNSuser"));
            this.setDNSpass(resultSet.getString("DNSpass"));
            this.setDNSjdbcClass(resultSet.getString("DNSjdbcClass"));
            this.setOutlookExePath(resultSet.getString("outlookExePath"));

//            resultSet = statement.executeQuery("SELECT * FROM Filters");
//            List<String[]> l = new LinkedList();
//            while (resultSet.next()){
//                String[] filter;
//                filter = new String[resultSet.getMetaData().getColumnCount()];
//
//                for (int i=1; i <= resultSet.getMetaData().getColumnCount(); i++){
//                    filter[i-1]=resultSet.getString(i);
//                }
//                l.add(filter);
//            }
//            this.setFilters(l);

            resultSet = statement.executeQuery("SELECT * FROM InvoicesMetaData WHERE DisplayOrder>0 ORDER BY DisplayOrder ASC");
            List<String[]> metaDataList = new LinkedList();
            while (resultSet.next()){
                String[] metaData;
                metaData = new String[resultSet.getMetaData().getColumnCount()];

                for (int i=1; i <= resultSet.getMetaData().getColumnCount(); i++){
                    metaData[i-1]=resultSet.getString(i);
                }
                metaDataList.add(metaData);
            }
            this.setInvoicesMetaData(metaDataList);

            resultSet = statement.executeQuery("SELECT * FROM StatusMetaData");
            while (resultSet.next()) {
                this.statusMetaData.add(resultSet.getString(1));
            }

            resultSet = statement.executeQuery("SELECT * FROM Currencies");
            while (resultSet.next()) {
                this.currencies.add(resultSet.getString(1));
            }

            resultSet = statement.executeQuery("SELECT * FROM AuthContact");
            while (resultSet.next()) {
                this.authContact.add(resultSet.getString(1));
            }

            resultSet = statement.executeQuery("SELECT * FROM Suppliers");
            while (resultSet.next()) {
                this.suppliers.add(resultSet.getString(1));
            }

            resultSet = statement.executeQuery("SELECT * FROM ContactGenpact");
            while (resultSet.next()) {
                this.contactGenpact.add(resultSet.getString(1));
            }

            resultSet = statement.executeQuery("SELECT * FROM RowColors");
            while (resultSet.next()) {
                this.rowColor.add(resultSet.getString(1));
            }

            resultSet = statement.executeQuery("SELECT * FROM ProcessStatus");
            while (resultSet.next()) {
                this.processStatus.add(resultSet.getString(1));
            }
        } catch (SQLException e) {
            // if the error message is "out of memory",
            // it probably means no database file is found
            logger.addException(e);
        } finally {
            try
            {
                if(connection != null)
                    connection.close();
            }
            catch(SQLException e)
            {
                // connection close failed.
                logger.addException(e);
            }
        }
    }

    public boolean save(Request request) throws ClassNotFoundException, FileNotFoundException, SQLException {
        String imExternalFolderPath = request.queryParams("imExternalFolderPath");
        if (imExternalFolderPath.lastIndexOf("\\") != imExternalFolderPath.length()-1) {
            imExternalFolderPath += "\\";
        }

        String imDBPath = request.queryParams("imDBPath");
        String userNetID = "";
        String userEmail = "";
        String userColor = "";
        if (!config.FINANCE_VIEW) {
            userNetID = request.queryParams("userID").toUpperCase();
            userEmail = request.queryParams("userEmail").toLowerCase();
            userColor = request.queryParams("userColor");
        } else {
            userNetID = "FINAN";
            userEmail = "finance@delphi.com";
            userColor = "#FFFFFF";
        }
        String DNSserver = request.queryParams("imDNSserver");
        String DNSuser = request.queryParams("imDNSuser");
        String DNSpass = request.queryParams("imDNSpass");
        String DNSjdbcClass = request.queryParams("imDNSjdbcClass");
        String outlookExePath = request.queryParams("outlookExePath");

        Connection connection = null;
        try {
            // create a database connection
            connection = DriverManager.getConnection("jdbc:sqlite:" + config.CFG_PATH);
            logger.add("Connecting to: " + config.CFG_PATH );
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);  // set timeout to 30 sec.

            if (!config.FINANCE_VIEW) {
                PreparedStatement prepStmt = connection.prepareStatement(
                        "UPDATE UserSettings SET imExternalFolderPath=?,imDBPath=?,userNetID=?,userEmail=?,userColor=?, DNSserver=?, DNSuser=?, DNSpass=?, DNSjdbcClass=?, outlookExePath=? WHERE ID=1");
                prepStmt.setString(1, imExternalFolderPath);
                prepStmt.setString(2, imDBPath);
                prepStmt.setString(3, userNetID);
                prepStmt.setString(4, userEmail);
                prepStmt.setString(5, userColor);
                prepStmt.setString(6, DNSserver);
                prepStmt.setString(7, DNSuser);
                prepStmt.setString(8, DNSpass);
                prepStmt.setString(9, DNSjdbcClass);
                prepStmt.setString(10, outlookExePath);
                logger.add("Execute sql query: " + "UPDATE UserSettings SET imExternalFolderPath=?,imDBPath=?,userNetID=?,userEmail=?,userColor=?, DNSserver=?, DNSuser=?, DNSpass=?, DNSjdbcClass=?, outlookExePath=? WHERE ID=1");

                prepStmt.execute();
                this.loadData();
                comboList.reload();
            } else { //for finance view allow to update only folder,DB paths
                PreparedStatement prepStmt = connection.prepareStatement(
                        "UPDATE UserSettings SET imExternalFolderPath=?,imDBPath=?,DNSserver=?, DNSuser=?, DNSpass=?, DNSjdbcClass=? WHERE ID=1");
                prepStmt.setString(1, imExternalFolderPath);
                prepStmt.setString(2, imDBPath);
                prepStmt.setString(3, DNSserver);
                prepStmt.setString(4, DNSuser);
                prepStmt.setString(5, DNSpass);
                prepStmt.setString(6, DNSjdbcClass);
                logger.add("Execute sql query: " + "UPDATE UserSettings SET imExternalFolderPath=?,imDBPath=?,DNSserver=?, DNSuser=?, DNSpass=?, DNSjdbcClass=? WHERE ID=1");

                prepStmt.execute();
                this.loadData();
                comboList.reload();
            }
            if (InvoicesManagerDBconnection(imDBPath)) {
                User user = new User(userNetID, userEmail, userColor);
                user.upsertUserToIMDB();
            }
        } catch (SQLException e) {
            logger.addException(e);
            return false;
        } finally {
            try {
                if (connection != null)
                    connection.close();
            } catch (SQLException e) {
                // connection close failed.
                logger.addException(e);
            }
        }
        return true;
    }

    public String getUserNetID() {
        if (userNetID == null) {
            return new String("");
        } else {
            return userNetID;
        }
    }

    public void setUserNetID(String user) {
        this.userNetID = user;
    }

    public String getImExternalFolderPath() {
        if (imExternalFolderPath == null) {
            return new String("");
        } else {
            return imExternalFolderPath;
        }
    }

    public void setImExternalFolderPath(String imExternalFolderPath) {
        this.imExternalFolderPath = imExternalFolderPath;
    }

    public String getImDBPath() {
        if (imDBPath == null) {
            return new String("");
        } else {
            return imDBPath;
        }
    }

    public void setImDBPath(String imDBPath) {
        this.imDBPath = imDBPath;
    }

    public int getTotalNrOfPages() {
        return totalNrOfPages;
    }

    public void setTotalNrOfPages(int totalNrOfPages) {
        this.totalNrOfPages = totalNrOfPages;
    }

    public List<String[]> getFilters() {
        return filters;
    }

    public void setFilters(List<String[]> list) {
        this.filters = list;
    }

    public String getUserEmail() {
        if (userEmail == null) {
            return new String("");
        } else {
            return userEmail;
        }
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserColor() {
        if (userColor == null) {
            return new String("");
        } else {
            return userColor;
        }
    }

    public void setUserColor(String userColor) {
        this.userColor = userColor;
    }

    public List<String[]> getInvoicesMetaData() {
        return invoicesMetaData;
    }

    public void setInvoicesMetaData(List<String[]> invoicesMetaData) {
        this.invoicesMetaData = invoicesMetaData;
    }

    public List<String> getStatusMetaData() {
        return statusMetaData;
    }

    public List<String> getCurrencies() {
        return currencies;
    }

    public List<String> getRowColor() {
        return rowColor;
    }

    public List<String> getProcessStatus() {
        return processStatus;
    }

    public String getDNSserver() {
        if (DNSserver == null) {
            return new String("");
        } else {
            return DNSserver;
        }
    }

    public void setDNSserver(String DNSserver) {
        this.DNSserver = DNSserver;
    }

    public String getDNSuser() {
        if (DNSuser == null) {
            return new String("");
        } else {
            return DNSuser;
        }
    }

    public void setDNSuser(String DNSuser) {
        this.DNSuser = DNSuser;
    }

    public String getDNSpass() {
        if (DNSpass == null) {
            return new String("");
        } else {
            return DNSpass;
        }
    }

    public void setDNSpass(String DNSpass) {
        this.DNSpass = DNSpass;
    }

    public String getDNSjdbcClass() {

        if (DNSjdbcClass == null) {
            return new String("");
        } else {
            return DNSjdbcClass;
        }
    }

    public void setDNSjdbcClass(String DNSjdbcClass) {
        this.DNSjdbcClass = DNSjdbcClass;
    }

    public List<String> getSuppliers() {
        return suppliers;
    }

    public List<String> getAuthContact() {
        return authContact;
    }

    public List<String> getContactGenpact() {
        return contactGenpact;
    }

    public void setOutlookExePath(String outlookExePath) {
        this.outlookExePath = outlookExePath;
    }

    public String getOutlookExePath() {
        if (outlookExePath == null) {
            return new String("");
        } else {
            return outlookExePath;
        }
    }

    @Override
    public String toString() {
        return "InvoiceManagerCFG{" +
                "InvoicesManagerCFGPath='" + config.CFG_PATH + '\'' + '\n' +
                ", imExternalFolderPath='" + imExternalFolderPath + '\'' + '\n' +
                ", imDBPath='" + imDBPath + '\'' + '\n' +
                ", rowsPerPage=" + config.RECORDS_PER_PAGE + '\n' +
                ", totalNrOfPages=" + totalNrOfPages + '\n' +
                ", userNetID='" + userNetID + '\'' + '\n' +
                ", userEmail='" + userEmail + '\'' + '\n' +
                ", userColor='" + userColor + '\'' +
                '}';
    }

    public void updateComboLists(InvoicesComboLists dao) throws ClassNotFoundException {
        Class.forName("org.sqlite.JDBC");
        Connection connection = null;

        try {
            // create a database connection
            connection = DriverManager.getConnection("jdbc:sqlite:" + config.CFG_PATH);
            connection.setAutoCommit(true);
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);  // set timeout to 30 sec.
            int i = 1;

            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO Suppliers VALUES (?)");
            for (String s: dao.suppliers) {
                if (!this.suppliers.contains(s)) {
                    preparedStatement.setString(1, s);
                    preparedStatement.addBatch();
                    i++;
                }
                if (i % 1001 == 0) {
                    preparedStatement.executeBatch();
                    logger.add("Update Suppliers in ImCFG: " + (i-1) + " records");
                }
            }
            preparedStatement.executeBatch();
            logger.add("Update Suppliers in ImCFG: " + (i-1) + " records");
            preparedStatement.close();

            preparedStatement = connection.prepareStatement("INSERT INTO AuthContact VALUES (?)");
            i = 1;
            for (String s: dao.authContact) {
                if (!this.authContact.contains(s)) {
                    preparedStatement.setString(1, s);
                    preparedStatement.addBatch();
                    i++;
                }
                if (i % 1001 == 0) {
                    preparedStatement.executeBatch();
                    logger.add("Update AuthContact in ImCFG: " + (i-1) + " records");
                }
            }
            preparedStatement.executeBatch();
            logger.add("Update AuthContact in ImCFG: " + (i-1) + " records");
            preparedStatement.close();

            preparedStatement = connection.prepareStatement("INSERT INTO Currencies VALUES (?)");
            i = 1;
            for (String s: dao.currencies) {
                if (!this.currencies.contains(s)) {
                    preparedStatement.setString(1, s);
                    preparedStatement.addBatch();
                    i++;
                }
                if (i % 1001 == 0) {
                    preparedStatement.executeBatch();
                    logger.add("Update Currencies in ImCFG: " + (i-1) + " records");
                }
            }
            preparedStatement.executeBatch();
            logger.add("Update Currencies in ImCFG: " + (i-1) + " records");
            preparedStatement.close();

            preparedStatement = connection.prepareStatement("INSERT INTO ContactGenpact VALUES (?)");
            i = 1;
            for (String s: dao.contactGenpact) {
                if (!this.contactGenpact.contains(s)) {
                    preparedStatement.setString(1, s);
                    preparedStatement.addBatch();
                    i++;
                }
                if (i % 1001 == 0) {
                    preparedStatement.executeBatch();
                    logger.add("Update ContactGenpact in ImCFG: " + (i-1) + " records");
                }
            }
            preparedStatement.executeBatch();
            logger.add("Update ContactGenpact in ImCFG: " + (i-1) + " records");
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
        try {
            if (connection != null)
                connection.close();
        } catch (SQLException e) {
            // connection close failed.
            System.err.println(e);
        }
    }
    }

    public void synchronizeComboLists(InvoicesComboLists dao) throws ClassNotFoundException {
        Class.forName("org.sqlite.JDBC");
        Connection connection = null;

        try {
            // create a database connection
            connection = DriverManager.getConnection("jdbc:sqlite:" + config.CFG_PATH);
            connection.setAutoCommit(true);
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);  // set timeout to 30 sec.
            int i = 1;

            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM Suppliers WHERE supplier=(?)");
            for (String s: this.suppliers) {
                if (!dao.suppliers.contains(s)) {
                    preparedStatement.setString(1, s);
                    preparedStatement.addBatch();
                    i++;
                }
                if (i % 1001 == 0) {
                    preparedStatement.executeBatch();
                    logger.add("Delete Suppliers in ImCFG: " + (i-1) + " records");
                }
            }
            preparedStatement.executeBatch();
            logger.add("Delete Suppliers in ImCFG: " + (i-1) + " records");
            preparedStatement.close();

            preparedStatement = connection.prepareStatement("DELETE FROM AuthContact WHERE authContact=(?)");
            i = 1;
            for (String s: this.authContact) {
                if (!dao.authContact.contains(s)) {
                    preparedStatement.setString(1, s);
                    preparedStatement.addBatch();
                    i++;
                }
                if (i % 1001 == 0) {
                    preparedStatement.executeBatch();
                    logger.add("Delete AuthContact in ImCFG: " + (i-1) + " records");
                }
            }
            preparedStatement.executeBatch();
            logger.add("Delete AuthContact in ImCFG: " + (i-1) + " records");
            preparedStatement.close();

            preparedStatement = connection.prepareStatement("DELETE FROM Currencies WHERE currency=(?)");
            i = 1;
            for (String s: this.currencies) {
                if (!dao.currencies.contains(s)) {
                    preparedStatement.setString(1, s);
                    preparedStatement.addBatch();
                    i++;
                }
                if (i % 1001 == 0) {
                    preparedStatement.executeBatch();
                    logger.add("Delete Currencies in ImCFG: " + (i-1) + " records");
                }
            }
            preparedStatement.executeBatch();
            logger.add("Delete Currencies in ImCFG: " + (i-1) + " records");
            preparedStatement.close();

            preparedStatement = connection.prepareStatement("DELETE FROM ContactGenpact WHERE contactGenpact=(?)");
            i = 1;
            for (String s: this.contactGenpact) {
                if (!dao.contactGenpact.contains(s)) {
                    preparedStatement.setString(1, s);
                    preparedStatement.addBatch();
                    i++;
                }
                if (i % 1001 == 0) {
                    preparedStatement.executeBatch();
                    logger.add("Delete ContactGenpact in ImCFG: " + (i-1) + " records");
                }
            }
            preparedStatement.executeBatch();
            logger.add("Delete ContactGenpact in ImCFG: " + (i-1) + " records");
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (connection != null)
                    connection.close();
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e);
            }
        }
    }
}

