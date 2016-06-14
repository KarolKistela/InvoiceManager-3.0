package Model.DAOs;

import Controller.Controller;
import Model.InvoiceManagerDB_DAO;
import spark.Request;

import java.io.FileNotFoundException;
import java.sql.*;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import static Controller.Controller.ImCFG;
import static Controller.Controller.sqlQuery;
import static Model.Helpers.fileExists;
import static Model.Helpers.sqlQueryConstructor2;

/**
 * Created by Karol Kistela on 14-Jun-16.
 */
public class DAO_queryView extends InvoicesDB{
    private Connection connection;
    private String sqlQuery;
    private int records;
    private List<String[]> resultSet = new LinkedList<>();
    private HashMap<String, String> usersColors = new HashMap<>();
    private HashMap<String, Integer> invDuplicatesMap = new HashMap<>();

    public DAO_queryView(Request request, String sqlQuery) throws ClassNotFoundException, FileNotFoundException {
        super();
        try {
            // create a database connection
            this.connection = DriverManager.getConnection("jdbc:sqlite:" + this.DB_Path);
            System.out.println("Model.DAOs.DAO_queryView: connected to " + this.DB_Path);
            this.sqlQuery = sqlQuery;
            System.out.println("Model.DAOs.DAO_queryView: sqlQuery " + this.sqlQuery);

            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);  // set timeout to 30 sec.
            //get nr of records returned by query;
            System.out.println("Model.DAOs.DAO_queryView: execute " + this.sqlQuery.replace("*", "count(ID)"));
            ResultSet rsRecords = statement.executeQuery(this.sqlQuery.replace("*","count(ID)"));
            this.records = rsRecords.getInt(1);
            rsRecords.close();
            statement.close();

            Statement statement2 = connection.createStatement();
            statement.setQueryTimeout(30);  // set timeout to 30 sec.
            //get result set of query;
            String limitClause = "";
            /* Make sure that LIMIT is correct sql statment: */
            Integer pageNr = Integer.parseInt(request.params("pageNr"));
            if (pageNr < 0) pageNr = 0;
            if (pageNr > records / Controller.ImCFG.getRowsPerPage()) {
                    pageNr = records / Controller.ImCFG.getRowsPerPage() + 1;
            }
            limitClause = " LIMIT " + ((pageNr - 1) * Controller.ImCFG.getRowsPerPage()) + ", " + Controller.ImCFG.getRowsPerPage() + ";";

            System.out.println("Model.DAOs.DAO_queryView: execute " + this.sqlQuery + limitClause);
            ResultSet rs = statement2.executeQuery(this.sqlQuery + limitClause);

            while (rs.next()) {
                this.resultSet.add(this.toStringArray(rs));
            }
            rs.close();
            statement2.close();


            Statement statement3 = connection.createStatement();
            statement.setQueryTimeout(30);  // set timeout to 30 sec.
            //get usersColor Map
            String query = "SELECT * FROM Users";
            System.out.println("Model.DAOs.DAO_queryView: execute " + query);

            ResultSet rsUserColors = statement3.executeQuery(query);

            while (rsUserColors.next()) {
                this.usersColors.put(rsUserColors.getString("NetID"), rsUserColors.getString("UserColor"));
            }
            rsUserColors.close();
            statement3.close();


            Statement statement4 = connection.createStatement();
            statement.setQueryTimeout(30);  // set timeout to 30 sec.
            if (Controller.ImCFG.isCheckForInvDuplicates()) {                              // if true check for duplicates in invoice nrs
                query = "SELECT InvoiceNR, repeatNr FROM (SELECT count(ID) as repeatNr, InvoiceNR FROM Invoices GROUP BY InvoiceNR) as tymTab WHERE repeatNr>1 ORDER BY InvoiceNR DESC";
                System.out.println("Model.DAOs.DAO_queryView: execute " + query);
                ResultSet rsInvDuplicates = statement4.executeQuery(query);

                while (rs.next()) {
                    this.invDuplicatesMap.put(rsInvDuplicates.getString("InvoiceNR"), rsInvDuplicates.getInt("repeatNR"));
                }
                rsInvDuplicates.close();
                statement4.close();
            } else {
                this.invDuplicatesMap = new HashMap();
            }
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
    }

    public int getRecords() {
        return records;
    }

    public List<String[]> getResultSet() {
        return resultSet;
    }

    public HashMap<String, String> getUsersColors() {
        return usersColors;
    }

    public HashMap<String, Integer> getInvDuplicatesMap() {
        return invDuplicatesMap;
    }
}
