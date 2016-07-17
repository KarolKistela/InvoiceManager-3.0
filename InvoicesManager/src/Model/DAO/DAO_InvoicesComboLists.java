package Model.DAO;

import java.io.FileNotFoundException;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

import static Controller.Controller.logger;

/**
 * Created by Karol Kistela on 11-Jul-16.
 */
public class DAO_InvoicesComboLists extends InvoicesDB {
    private final String suppliersSQL = "SELECT distinct(Supplier) FROM Invoices;";
    private final String authContactSQL = "SELECT distinct(AuthContact) FROM Invoices;";
    private final String currenciesSQL = "SELECT distinct(Currency) FROM Invoices;";
    private final String contactGenpactSQL = "SELECT distinct(ContactGenpact) FROM Invoices;";
    public List<String> suppliers = new LinkedList<>();
    public List<String> authContact = new LinkedList<>();
    public List<String> currencies = new LinkedList<>();
    public List<String> contactGenpact = new LinkedList<>();



    public DAO_InvoicesComboLists() throws ClassNotFoundException, FileNotFoundException, SQLException {
        super();
        try {
            // create a database connection
            logger.add("Model.DAO.DAO_InvoicesComboLists connected to: " + this.DB_Path);

            connection = DriverManager.getConnection(connectionString);
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            // Suppliers List
            logger.add("Get suppliers List: " + suppliersSQL);
            ResultSet rs = statement.executeQuery(suppliersSQL);
            while (rs.next()){
                if (!rs.getString(1).equals("")) suppliers.add(rs.getString(1));
            }
            rs.close();
            statement.close();

            // authContact List
            logger.add("Get authContact List: " + authContactSQL);
            rs = statement.executeQuery(authContactSQL);
            while (rs.next()){
                if (!rs.getString(1).equals("")) authContact.add(rs.getString(1));
            }
            rs.close();
            statement.close();

            // currencies List
            logger.add("Get currencies List: " + currenciesSQL);
            rs = statement.executeQuery(currenciesSQL);
            while (rs.next()){
                if (!rs.getString(1).equals("")) currencies.add(rs.getString(1));
            }
            rs.close();
            statement.close();

            // contactGenpact List
            logger.add("Get contactGenpact List: " + contactGenpactSQL);
            rs = statement.executeQuery(contactGenpactSQL);
            while (rs.next()){
                if (!rs.getString(1).equals("")) contactGenpact.add(rs.getString(1));
            }
            rs.close();
            statement.close();
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
