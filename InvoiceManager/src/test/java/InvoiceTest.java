import Model.Invoice;

import java.sql.*;

/**
 * Created by mzjdx6 on 21-Apr-16.
 */
public class InvoiceTest {
    public static void main(String[] args) throws ClassNotFoundException {
        if (args.length == 0) {
            new InvoiceTest("C:\\InvoiceManagerExt\\Invoices_manager_DB\\InvoiceManager.db");
        }
        else {
            new InvoiceTest(args[0]);
        }

    }

    public InvoiceTest(String DBpath) throws ClassNotFoundException {
        Class.forName("org.sqlite.JDBC");
        String query = "SELECT * FROM Invoices ORDER BY ID DESC LIMIT 2000, 2";

        Connection connection = null;
        try
        {
            // create a database connection
            connection = DriverManager.getConnection("jdbc:sqlite:" + DBpath);
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);  // set timeout to 30 sec.

            ResultSet rs = statement.executeQuery(query);
            while (rs.next()) {
                Invoice inv = new Invoice(rs);

                System.out.println("ID for query is: " + inv.getID());
                System.out.println("BC for query is: " + inv.getBC());
                System.out.println("EntryDate for query is: " + inv.getEntryDate());
                System.out.println("ContactGenpact for query is: " + inv.getContactGenpact());
                System.out.println("Supplier for query is: " + inv.getSupplier());
                System.out.println("InvoiceNR for query is: " + inv.getInvoiceNR());
                System.out.println("InvScanPath for query is: " + inv.getInvScanPath());
                System.out.println("PO for query is: " + inv.getPO());
                System.out.println("NetPrice for query is: " + inv.getNetPrice());
                System.out.println("Currency for query is: " + inv.getCurrency());
                System.out.println("InvDate for query is: " + inv.getInvDate());
                System.out.println("EmailSubject for query is: " + inv.getEmailSubject());
                System.out.println("AuthContact for query is: " + inv.getAuthContact());
                System.out.println("AuthDate for query is: " + inv.getAuthDate());
                System.out.println("AuthReplyDate for query is: " + inv.getAuthReplyDate());
                System.out.println("AuthEmail for query is: " + inv.getAuthEmail());
                System.out.println("EndDate for query is: " + inv.getEndDate());
                System.out.println("GR for query is: " + inv.getGR());
                System.out.println("GenpactLastReply for query is: " + inv.getGenpactLastReply());
                System.out.println("UserComments for query is: " + inv.getUserComments());
                System.out.println("Status for query is: " + inv.getStatus());
                System.out.println("User for query is: " + inv.getUser());
                System.out.println("RowColor for query is: " + inv.getRowColor());
                System.out.println("ProcessStatus for query is: " + inv.getProcessStatus());
            }
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
