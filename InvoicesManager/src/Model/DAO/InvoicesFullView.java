package Model.DAO;

import Controller.Controller;
import Model.Invoice;
import Model.User;
import spark.Request;

import java.io.FileNotFoundException;
import java.sql.*;
import java.util.LinkedList;
import java.util.List;

import static Controller.Controller.logger;

/**
 * Created by Karol Kistela on 21-Jun-16.
 */
public class InvoicesFullView extends DataBaseConnection {
    public List<Invoice> invoices = new LinkedList<>();
    public Integer nrOfRecords;

    public InvoicesFullView() throws FileNotFoundException, ClassNotFoundException {
        super();
        logger.add("Model.DAO.InvoicesFullView.constructor()");
    }

    public InvoicesFullView(String sqlQuery, String sqlQueryCount) throws FileNotFoundException, ClassNotFoundException, SQLException {
        super();
        Controller.sqlQuery = sqlQuery.substring(0, sqlQuery.indexOf("LIMIT")); // this is used when IM wants to export data to CSV from last executed query.
        try {
            logger.add("Model.DAO.InvoicesFullView.constructor(sqlQuery, sqlQueryCount) connected to: " + this.DB_Path);
            logger.add("Model.DAO.InvoicesFullView sqlQuery: " + sqlQuery);
            logger.add("Model.DAO.InvoicesFullView sqlQueryCount: " + sqlQueryCount);

            connection = DriverManager.getConnection(connectionString);
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            //get query result
            ResultSet rs = statement.executeQuery(sqlQuery);
            while (rs.next()) {
                invoices.add(new Invoice(rs));
            }
            rs.close();
            statement.close();

            rs = statement.executeQuery(sqlQueryCount);
            this.nrOfRecords = rs.getInt(1);
            rs.close();
            statement.close();
        } catch (SQLException e) {
            logger.addException(e);
            throw e;
        } finally {
            try {
                if (connection != null)
                    connection.close();
            } catch (SQLException e) {
                logger.addException(e);
                throw e;
            }
        }
    }

    public boolean update(Request request) {
        try {
            connection = DriverManager.getConnection(connectionString);
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            PreparedStatement prepStmt = connection.prepareStatement(
                    "UPDATE Invoices SET BC=?, EntryDate=?, ContactGenpact=?, Supplier=?, InvoiceNR=?, InvScanPath=?, PO=?, NetPrice=?, Currency=?, InvDate=?, EmailSubject=?, AuthContact=?, AuthDate=?, AuthReplyDate=?, AuthEmail=?, EndDate=?, GR=?, GenpactLastReply=?, UserComments=?, Status=?, User=?, RowColor=?, ProcessStatus=?, FinanceComments=? WHERE ID = ?;");
            prepStmt.setString(1, request.queryParams("BC"));
            prepStmt.setString(2, request.queryParams("EntryDate"));
            prepStmt.setString(3, request.queryParams("ContactGenpact"));
            prepStmt.setString(4, request.queryParams("Supplier"));
            prepStmt.setString(5, request.queryParams("InvoiceNR"));
            prepStmt.setString(6, request.queryParams("InvScanPath"));
            prepStmt.setString(7, request.queryParams("PO"));
            prepStmt.setString(8, request.queryParams("NetPrice"));
            prepStmt.setString(9, request.queryParams("Currency"));
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
            prepStmt.setString(21, request.queryParams("User").toUpperCase());
            prepStmt.setString(22, request.queryParams("RowColor"));
            prepStmt.setString(23, request.queryParams("ProcessStatus"));
            prepStmt.setString(24, request.queryParams("FinanceComments"));
            prepStmt.setString(25, request.params("value1"));

            logger.add("Model.DAO.Invoices.insertUser: ");
            logger.add("query: " + prepStmt.toString());

            prepStmt.execute();
            prepStmt.close();
        } catch (SQLException e) {
            logger.addException(e);
            return false;
        } finally {
            try {
                if (connection != null)
                    connection.close();
            } catch (SQLException e) {
                logger.addException(e);
            }
        }
        return true;
    }

    public boolean updateFinance(Request request) {
        try {
            connection = DriverManager.getConnection(connectionString);
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            PreparedStatement prepStmt = connection.prepareStatement(
                    "UPDATE Invoices SET FinanceComments=? WHERE ID = ?;");
            prepStmt.setString(1, request.queryParams("FinanceComments"));
            prepStmt.setString(2, request.params("value1"));

            logger.add("Model.DAO.InvoicesFullView.updateFinance: UPDATE Invoices SET FinanceComments=? WHERE ID = ?;");

            prepStmt.execute();
            prepStmt.close();

            // For finance: they are accesing copy of DB, however we want to save update to both DBs so we need this additonal update
            updateFinance2(request);
        } catch (SQLException e) {
            logger.addException(e);
            return false;
        } finally {
            try {
                if (connection != null)
                    connection.close();
            } catch (SQLException e) {
                logger.addException(e);
            }
        }
        return true;
    }

    private void updateFinance2(Request request) {
        String dbPath = Controller.ImCFG.getImDBPath();

        try {
            connection = DriverManager.getConnection(Driver + dbPath);
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            PreparedStatement prepStmt = connection.prepareStatement(
                    "UPDATE Invoices SET FinanceComments=? WHERE ID = ?;");
            prepStmt.setString(1, request.queryParams("FinanceComments"));
            prepStmt.setString(2, request.params("value1"));

            logger.add("Connected to: " + Driver + dbPath);
            logger.add("Model.DAO.InvoicesFullView.updateFinance2: UPDATE Invoices SET FinanceComments=? WHERE ID = ?;");

            prepStmt.execute();
            prepStmt.close();

        } catch (SQLException e) {

        }
    }

    public void insertUser(User u) {
        try {
            connection = DriverManager.getConnection(connectionString);
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            PreparedStatement prepStmt = connection.prepareStatement(
                    "INSERT INTO Users (NetID, Email, UserColor) VALUES (?,?,?);");
            prepStmt.setString(1, u.getUserID().toUpperCase());
            prepStmt.setString(2, u.getUserMail());
            prepStmt.setString(3, u.getUserColor());

            logger.add("Model.DAO.InvoicesFullView.insertUser: " + "INSERT INTO Users (NetID, Email, UserColor) VALUES (?,?,?);");
            logger.add("1: " + u.getUserID().toUpperCase());
            logger.add("2: " + u.getUserMail());
            logger.add("1: " + u.getUserColor());

            prepStmt.execute();
            prepStmt.close();
        } catch (SQLException e) {
            logger.addException(e);
        } finally {
            try {
                if (connection != null)
                    connection.close();
            } catch (SQLException e) {
                logger.addException(e);
            }
        }
    }

    public void upsertUser(User u) {
        try {
            connection = DriverManager.getConnection(connectionString);
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            PreparedStatement prepStmt = connection.prepareStatement(
                    "UPDATE Users SET Email=?, UserColor=? WHERE NetID = ?;");
            prepStmt.setString(1, u.getUserMail());
            prepStmt.setString(2, u.getUserColor());
            prepStmt.setString(3, u.getUserID().toUpperCase());

            logger.add("Model.DAO.Invoices.insertUser: UPDATE Users SET Email=?, UserColor=? WHERE NetID = ?;");
            logger.add("1,2,3: " + u.getUserMail() + ", " + u.getUserColor() + ", " + u.getUserID().toUpperCase());

            prepStmt.execute();
            prepStmt.close();
        } catch (SQLException e) {
            logger.addException(e);
        } finally {
            try {
                if (connection != null)
                    connection.close();
            } catch (SQLException e) {
                logger.addException(e);
            }
        }
    }

    public Boolean isUserInDB(String netID) {
        try {
            connection = DriverManager.getConnection(connectionString);
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            ResultSet rs = statement.executeQuery("SELECT count(NetID) FROM Users WHERE NetID='" + netID + "';");
            int i = rs.getInt(1);
            rs.close();
            statement.close();

            if (i == 0) {
                return false;
            } else {
                return true;
            }
        } catch (SQLException e) {
            logger.addException(e);
            return false;
        }finally {
            try {
                if (connection != null)
                    connection.close();
            } catch (SQLException e) {
                logger.addException(e);
            }
        }
    };

    public List<String[]> sqlSelect(String sqlQuery) {
        List<String[]> retVal = new LinkedList<>();

        try {
            connection = DriverManager.getConnection(connectionString);
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            ResultSet rs = statement.executeQuery(sqlQuery);
            logger.add("Model.DAO.InvoicesFullView.sqlSelect: " + sqlQuery);
            while (rs.next()){
                retVal.add(this.toStringArray(rs));
            }
            rs.close();
            statement.close();
        } catch (SQLException e) {
            logger.addException(e);
        }finally {
            try {
                if (connection != null)
                    connection.close();
            } catch (SQLException e) {
                logger.addException(e);
            }
        }
        return retVal;
    }
}