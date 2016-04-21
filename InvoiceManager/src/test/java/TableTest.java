import Model.Invoice;
import Model.InvoicesList;

import java.sql.*;

/**
 * Created by mzjdx6 on 21-Apr-16.
 */
public class TableTest {
    public static void main(String[] args) throws ClassNotFoundException {
        if (args.length == 0) {
            new TableTest("C:\\InvoiceManagerExt\\Invoices_manager_DB\\InvoiceManager.db");
        }
        else {
            new TableTest(args[0]);
        }
    }

    public TableTest(String DBpath) throws ClassNotFoundException {
        Class.forName("org.sqlite.JDBC");
        String query = "SELECT * FROM Invoices ORDER BY ID DESC LIMIT 2000, 2";
        InvoicesList tab;

        Connection connection = null;
        try
        {
            // create a database connection
            connection = DriverManager.getConnection("jdbc:sqlite:" + DBpath);
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);  // set timeout to 30 sec.
            tab = new InvoicesList();

            ResultSet rs = statement.executeQuery(query);
            while (rs.next()) {
                tab.addInvoice(new Invoice(rs));
            }
            tab.toString();
        }
        catch(SQLException e)
        {
            // if the error message is "out of memory",
            // it probably means no database file is found
            System.err.println(e.getMessage());

        }
        finally
        {
            try
            {
                if(connection != null)
                    connection.close();
            }
            catch(SQLException e)
            {
                // connection close failed.
                System.err.println(e);
            }
        }
    }
}
