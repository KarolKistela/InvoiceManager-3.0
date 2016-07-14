package Model.DAO;

import Controller.Controller;
import Model.Invoice;
import Model.User;

import java.io.FileNotFoundException;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Karol Kistela on 21-Jun-16.
 */
public class DAO_InvoicesFullView extends InvoicesDB{
    public List<Invoice> invoices = new LinkedList<>();
    public List<User> users = new LinkedList<>();
    public Integer nrOfRecords;

    public DAO_InvoicesFullView(String sqlQuery, String sqlQueryCount) throws FileNotFoundException, ClassNotFoundException, SQLException {
        super();
        Controller.sqlQuery = sqlQuery.substring(0,sqlQuery.indexOf("LIMIT")); // this is used when IM wants to export data to CSV from last executed query.
        try {
            // create a database connection
            System.out.println("Model.DAO.DAO_InvoicesFullView connected to: " + this.DB_Path);
            System.out.println("Model.DAO.DAO_InvoicesFullView sqlQuery: " + sqlQuery);
            System.out.println("Model.DAO.DAO_InvoicesFullView sqlQuery: " + sqlQueryCount);

            connection = DriverManager.getConnection(connectionString);
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            //get nr of records returned by query
            ResultSet rs = statement.executeQuery(sqlQueryCount);
            this.nrOfRecords = rs.getInt(1);
            rs.close();
            statement.close();

            //get query result
            rs = statement.executeQuery(sqlQuery);
            while (rs.next()){
                invoices.add(new Invoice(rs));
            }

            rs.close();
            statement.close();

            //get users
            rs = statement.executeQuery("SELECT * FROM Users");
            while (rs.next()){
                users.add(new User(rs));
            }
        } catch (SQLException e) {
            // if the error message is "out of memory",
            // it probably means no database file is found
            throw e;
        } finally {
            try {
                if (connection != null)
                    connection.close();
            } catch (SQLException e) {
                // connection close failed.
                throw e;
            }
        }
    }
}
