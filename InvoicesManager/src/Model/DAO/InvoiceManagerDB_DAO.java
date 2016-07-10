package Model.DAO;

/**
 * Created by mzjdx6 on 20-Mar-16.
 * TODO: clean up this s..t ...actually it's not so bad
 */

import Model.Invoice;
import Model.User;
import spark.Request;

import java.io.FileNotFoundException;
import java.sql.*;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import static Controller.Controller.CFG_PATH;
import static Model.Helpers.fileExists;

public class InvoiceManagerDB_DAO implements IMsqlite {
    private final String DB_Path;
    private InvoiceManagerCFG ImCFG;
    private Connection connection;
    private Integer nrOfrecods;

    public static void main(String[] args) throws ClassNotFoundException, FileNotFoundException {
        InvoiceManagerDB_DAO db = new InvoiceManagerDB_DAO();

        String preperedQuery = "INSERT INTO Users (NetID, Email, UserColor) VALUES (1,2,3);";
        preperedQuery = preperedQuery.replace("1","mzjdx6").replace("2","karol.kistela@gmial.com").replace("3","red");
        System.out.println("query: " + preperedQuery);

    }

    public InvoiceManagerDB_DAO() throws ClassNotFoundException, FileNotFoundException {
        ImCFG = new InvoiceManagerCFG(CFG_PATH);
        if (fileExists(ImCFG.getImDBPath())) {
            this.DB_Path = ImCFG.getImDBPath();
        } else {
            this.DB_Path = "src/main/resources/InvoiceManagerCFG/saveToDelete.file";
            throw new FileNotFoundException();
        }
    }

    public InvoiceManagerDB_DAO(String DB_Path) throws ClassNotFoundException, FileNotFoundException {
        ImCFG = new InvoiceManagerCFG(CFG_PATH);
        Class.forName("org.sqlite.JDBC");
        this.connection = null;
        if (fileExists(DB_Path)) {
            this.DB_Path = DB_Path;
        } else {
            this.DB_Path = "src/main/resources/InvoiceManagerCFG/saveToDelete.file";
            throw new FileNotFoundException();
        }

    }

    public List<String[]> sqlSELECT(String query, int pageNr, boolean withOrderByClause, boolean withLimitClause) throws ClassNotFoundException, SQLException {
        List<String[]> retVal = new LinkedList();

        String orderByClause;
        if (withOrderByClause) {
            orderByClause = ImCFG.getOrderByClause();
        } else {
            orderByClause = "";
        }

        String limitClause;
            /* Make sure that LIMIT is correct sql statment: */
        if (withLimitClause) {
            if (pageNr < 0) pageNr = 0;
            if (pageNr > (this.sqlCOUNT(query.replace("*", "COUNT(*)")) / ImCFG.getRowsPerPage()))
                pageNr = this.sqlCOUNT(query.replace("*", "COUNT(*)")) / ImCFG.getRowsPerPage() + 1;
            limitClause = " LIMIT " + ((pageNr - 1) * ImCFG.getRowsPerPage()) + ", " + ImCFG.getRowsPerPage() + ";";
        } else {
            limitClause = ";";
        }

        System.out.println("Model.DAO.InvoiceManagerDB_DAO.sqlSELECT("+query+", "+pageNr+", "+withOrderByClause+", "+withLimitClause+")");

        try {
            // create a database connection
            connection = DriverManager.getConnection("jdbc:sqlite:" + this.DB_Path);
            System.out.println("DB path: " + this.DB_Path);
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);  // set timeout to 30 sec.
            System.out.println("  Query: " + query + orderByClause + limitClause);

            ResultSet rs = statement.executeQuery(query + orderByClause + limitClause);

            while (rs.next()) {
                retVal.add(this.toStringArray(rs));
            }
            return retVal;
        } catch (SQLException e) {
            // if the error message is "out of memory",
            // it probably means no database file is found
            System.err.println(e.getMessage());
        } finally {
            try {
                if (connection != null)
                    connection.close();
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e);
            }
        }
        return null;
    }

    public List<String[]> sqlSELECT2(String query, int pageNr, boolean withOrderByClause, boolean withLimitClause, int records) throws ClassNotFoundException, SQLException {
        List<String[]> retVal = new LinkedList();

        String orderByClause;
        if (withOrderByClause) {
            orderByClause = ImCFG.getOrderByClause();
        } else {
            orderByClause = "";
        }

        String limitClause;
            /* Make sure that LIMIT is correct sql statment: */
        if (withLimitClause) {
            if (pageNr < 0) pageNr = 0;
            if (pageNr > records / ImCFG.getRowsPerPage()) {
                pageNr = records / ImCFG.getRowsPerPage() + 1;
            }
            limitClause = " LIMIT " + ((pageNr - 1) * ImCFG.getRowsPerPage()) + ", " + ImCFG.getRowsPerPage() + ";";
        } else {
            limitClause = ";";
        }

        System.out.println("Model.DAO.InvoiceManagerDB_DAO.sqlSELECT("+query+", "+pageNr+", "+withOrderByClause+", "+withLimitClause+")");

        try {
            // create a database connection
            connection = DriverManager.getConnection("jdbc:sqlite:" + this.DB_Path);
            System.out.println("DB path: " + this.DB_Path);
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);  // set timeout to 30 sec.
            System.out.println("  Query: " + query + orderByClause + limitClause);

            ResultSet rs = statement.executeQuery(query + orderByClause + limitClause);

            while (rs.next()) {
                retVal.add(this.toStringArray(rs));
            }
            return retVal;
        } catch (SQLException e) {
            // if the error message is "out of memory",
            // it probably means no database file is found
            System.err.println(e.getMessage());
        } finally {
            try {
                if (connection != null)
                    connection.close();
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e);
            }
        }
        return null;
    }

    private String[] toStringArray(ResultSet rs) throws SQLException {
        String[] retVal;
        retVal = new String[rs.getMetaData().getColumnCount()];

        for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
            retVal[i - 1] = rs.getString(i);
        }
        return retVal;
    }

    public Invoice sqlSELECTid(Integer id) throws ClassNotFoundException, SQLException {
        Invoice retVal = new Invoice();
        String query = "SELECT * FROM InvoicesFullView WHERE ID="+id;

        System.out.println("Model.DAO.InvoiceManagerDB_DAO.sqlSELECTid("+id+")");
        try {
            // create a database connection
            connection = DriverManager.getConnection("jdbc:sqlite:" + this.DB_Path);
            System.out.println("DB path: " + this.DB_Path);
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);  // set timeout to 30 sec.
            System.out.println("  Query: " + query);

            ResultSet rs = statement.executeQuery(query);
            while (rs.next()) {
                retVal = new Invoice(rs);
            }
            System.out.println("Returns: " + retVal.toString());
            return retVal;
        } catch (SQLException e) {
            // if the error message is "out of memory",
            // it probably means no database file is found
            System.err.println(e.getMessage());
        } finally {
            try {
                if (connection != null)
                    connection.close();
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e);
            }
        }
        return null;
    }

    public String[] sqlSELECTid2(Integer id) throws ClassNotFoundException, SQLException {
        String[] retVal = new String[28];
        String query = "SELECT * FROM InvoicesFullView WHERE ID="+id;
        System.out.println("Model.DAO.InvoiceManagerDB_DAO.sqlSELECTid("+id+")");

        try {
            // create a database connection
            connection = DriverManager.getConnection("jdbc:sqlite:" + this.DB_Path);
            System.out.println("DB path: " + this.DB_Path);
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);  // set timeout to 30 sec.
            System.out.println("  Query: " + query);

            ResultSet rs = statement.executeQuery(query);
            for (int i = 0; i < rs.getMetaData().getColumnCount(); i++) {
                retVal[i] = rs.getString(i+1);
            }
            return retVal;
        } catch (SQLException e) {
            // if the error message is "out of memory",
            // it probably means no database file is found
            System.err.println(e.getMessage());
        } finally {
            try {
                if (connection != null)
                    connection.close();
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e);
            }
        }
        return null;
    }

    public int sqlCOUNT(String query) throws ClassNotFoundException, SQLException {
        System.out.println("Model.DAO.InvoiceManagerDB_DAO.sqlCOUNT("+query+")");
        try {
            // create a database connection
            connection = DriverManager.getConnection("jdbc:sqlite:" + DB_Path);
            System.out.println("DB path: " + this.DB_Path);
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);  // set timeout to 30 sec.
            System.out.println("  Query: " + query);

            ResultSet rs = statement.executeQuery(query);
            System.out.println("Returns: " + rs.getInt(1));
            return rs.getInt(1);
        } catch (SQLException e) {
            // if the error message is "out of memory",
            // it probably means no database file is found
            System.err.println(e.getMessage());
            return 0;
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

    public String filePath(String ID, String columnName) throws ClassNotFoundException {
        System.out.println("Model.DAO.InvoiceManagerDB_DAO.filePath("+ID+", "+columnName+")");
        try {
            // create a database connection
            connection = DriverManager.getConnection("jdbc:sqlite:" + DB_Path);
            System.out.println("DB path: " + this.DB_Path);
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);  // set timeout to 30 sec.
            String query = "SELECT " + columnName + " FROM Invoices WHERE ID = " + ID + ";";
            System.out.println("  Query: " + query);

            ResultSet rs = statement.executeQuery(query);
            System.out.println("Returns: " + ImCFG.getImExternalFolderPath() + rs.getString(1));
            // returns path in quotation b/c without them files with spaces in path will not open
            return ("\"" + ImCFG.getImExternalFolderPath() + rs.getString(1) + "\"");
        } catch (SQLException e) {
            // if the error message is "out of memory",
            // it probably means no database file is found
            System.err.println(e.getMessage());
            return null;
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

    public HashMap<String, Integer> findDuplicatedInvNr() throws ClassNotFoundException {
        HashMap invDuplicatesMap = new HashMap();
        System.out.println("Model.DAO.InvoiceManagerDB_DAO.findDuplicatedInvNr()");
        try {
            // create a database connection
            connection = DriverManager.getConnection("jdbc:sqlite:" + DB_Path);
            System.out.println("DB path: " + this.DB_Path);
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);  // set timeout to 30 sec.
            String query = "SELECT InvoiceNR, repeatNr FROM (SELECT count(ID) as repeatNr, InvoiceNR FROM Invoices GROUP BY InvoiceNR) as tymTab WHERE repeatNr>1 ORDER BY InvoiceNR DESC";
            System.out.println("  Query: " + query);
            ResultSet rs = statement.executeQuery(query);

            while (rs.next()) {
                invDuplicatesMap.put(rs.getString("InvoiceNR"), rs.getInt("repeatNR"));
            }
            return invDuplicatesMap;
        } catch (SQLException e) {
            // if the error message is "out of memory",
            // it probably means no database file is found
            System.err.println(e.getMessage());
            return null;
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

    public HashMap<String, String> usersColorMap() throws ClassNotFoundException {
        HashMap usersColorMap = new HashMap();
        System.out.println("Model.DAO.InvoiceManagerDB_DAO.usersColorMap()");
        try {
            // create a database connection
            connection = DriverManager.getConnection("jdbc:sqlite:" + DB_Path);
            System.out.println("DB path: " + this.DB_Path);
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);  // set timeout to 30 sec.
            String query = "SELECT * FROM Users";
            System.out.println("  Query: " + query);

            ResultSet rs = statement.executeQuery(query);

            while (rs.next()) {
                usersColorMap.put(rs.getString("NetID"), rs.getString("UserColor"));
            }
            return usersColorMap;
        } catch (SQLException e) {
            // if the error message is "out of memory",
            // it probably means no database file is found
            System.err.println(e.getMessage());
            return null;
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

    public void insertUser(User u) throws ClassNotFoundException {

        try {
            // create a database connection
            connection = DriverManager.getConnection("jdbc:sqlite:" + DB_Path);
            System.out.println("DB path: " + this.DB_Path);
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);  // set timeout to 30 sec.

            PreparedStatement prepStmt = connection.prepareStatement(
                    "INSERT INTO Users (NetID, Email, UserColor) VALUES (?,?,?);");
            prepStmt.setString(1, u.getUserID().toUpperCase());
            prepStmt.setString(2, u.getUserMail());
            prepStmt.setString(3, u.getUserColor());

            String preperedQuery = "INSERT INTO Users (NetID, Email, UserColor) VALUES (1,2,3);";
            preperedQuery = preperedQuery.replace("1",u.getUserID()).replace("2",u.getUserMail()).replace("3",u.getUserColor());
            System.out.println("Model.DAO.InvoiceManagerDB_DAO.insertUser: " + u.getUserID());
            System.out.println("query: " + preperedQuery);

            prepStmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void upsertUser(User u) throws ClassNotFoundException {

        try {
            // create a database connection
            connection = DriverManager.getConnection("jdbc:sqlite:" + DB_Path);
            System.out.println("DB path: " + this.DB_Path);
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);  // set timeout to 30 sec.

            PreparedStatement prepStmt = connection.prepareStatement(
                    "UPDATE Users SET Email=?, UserColor=? WHERE NetID = ?;");
            prepStmt.setString(1, u.getUserMail());
            prepStmt.setString(2, u.getUserColor());
            prepStmt.setString(3, u.getUserID().toUpperCase());

            String preperedQuery = "UPDATE Users SET Email=2, UserColor=3 WHERE NetID = 1;";
            preperedQuery = preperedQuery.replace("1",u.getUserID().toUpperCase()).replace("2",u.getUserMail()).replace("3",u.getUserColor());
            System.out.println("Model.DAO.InvoiceManagerDB_DAO.insertUser: ");
            System.out.println("query: " + preperedQuery);

            prepStmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void upsertInvoice(Request request) {
        try {
            // create a database connection
            connection = DriverManager.getConnection("jdbc:sqlite:" + DB_Path);
            System.out.println("DB path: " + this.DB_Path);
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);  // set timeout to 30 sec.

            PreparedStatement prepStmt = connection.prepareStatement(
                    "UPDATE Invoices SET BC=?, EntryDate=?, ContactGenpact=?, Supplier=?, InvoiceNR=?, InvScanPath=?, PO=?, NetPrice=?, Currency=?, InvDate=?, EmailSubject=?, AuthContact=?, AuthDate=?, AuthReplyDate=?, AuthEmail=?, EndDate=?, GR=?, GenpactLastReply=?, UserComments=?, Status=?, User=?, RowColor=?, ProcessStatus=?, FinanceComments=? WHERE ID = ?;");
            prepStmt.setString(1,  request.queryParams("BC"));
            prepStmt.setString(2,  request.queryParams("EntryDate"));
            prepStmt.setString(3,  request.queryParams("ContactGenpact"));
            prepStmt.setString(4,  request.queryParams("Supplier"));
            prepStmt.setString(5,  request.queryParams("InvoiceNR"));
            prepStmt.setString(6,  request.queryParams("InvScanPath"));
            prepStmt.setString(7,  request.queryParams("PO"));
            prepStmt.setString(8,  request.queryParams("NetPrice"));
            prepStmt.setString(9,  request.queryParams("Currency"));
            prepStmt.setString(10, request.queryParams("InvDate"));
            prepStmt.setString(11, request.queryParams("EmailSubject"));
            prepStmt.setString(12, request.queryParams("AuthContact"));
            prepStmt.setString(13, request.queryParams("AuthDate"));
            prepStmt.setString(14, request.queryParams("AuthReplyDate"));
            prepStmt.setString(15, request.queryParams("AuthEmail"));
            prepStmt.setString(16, request.queryParams("EndDate"));
            prepStmt.setString(17, request.queryParams("GR"));
            prepStmt.setString(18, request.queryParams("GenpactLastReply"));
            prepStmt.setString(19, request.queryParams("UserComments"));
            prepStmt.setString(20, request.queryParams("Status"));
            prepStmt.setString(21, request.queryParams("User"));
            prepStmt.setString(22, request.queryParams("RowColor"));
            prepStmt.setString(23, request.queryParams("ProcessStatus"));
//            prepStmt.setString(24, "100"); // Depreciated
            prepStmt.setString(24, request.queryParams("FinanceComments"));
            prepStmt.setString(25, request.params("value1"));

            System.out.println("Model.DAO.InvoiceManagerDB_DAO.insertUser: ");
            System.out.println("query: " + prepStmt.toString());

            prepStmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void upsertInvoiceFinance(Request request) {
        try {
            // create a database connection
            connection = DriverManager.getConnection("jdbc:sqlite:" + DB_Path);
            System.out.println("DB path: " + this.DB_Path);
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);  // set timeout to 30 sec.

            PreparedStatement prepStmt = connection.prepareStatement(
                    "UPDATE Invoices SET FinanceComments=? WHERE ID = ?;");
            prepStmt.setString(1, request.queryParams("FinanceComments"));
            prepStmt.setString(2, request.params("value1"));

            System.out.println("Model.DAO.InvoiceManagerDB_DAO.insertUser: ");
            System.out.println("query: " + prepStmt.toString());

            prepStmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}