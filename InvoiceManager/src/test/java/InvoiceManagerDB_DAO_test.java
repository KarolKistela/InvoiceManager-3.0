import Model.Invoice;
import Model.InvoiceManagerDB_DAO;
import Model.InvoicesList;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by mzjdx6 on 21-Apr-16.
 */
public class InvoiceManagerDB_DAO_test {
    public static void main(String[] args) {
        if (args.length == 0) {
            new InvoiceManagerDB_DAO_test("C:\\InvoiceManagerExt\\Invoices_manager_DB\\InvoiceManager.db");
        }
        else {
            new InvoiceManagerDB_DAO_test(args[0]);
        }
    }

    public InvoiceManagerDB_DAO_test(String s) {
        InvoiceManagerDB_DAO db = new InvoiceManagerDB_DAO(s);
        InvoicesList invL =  new InvoicesList();
        try {
            System.out.println("Nr of records in DB: " + db.sqlCount("SELECT count(*) FROM Invoices;"));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            invL = db.sqlQuery("SELECT * FROM Invoices ORDER BY ID DESC LIMIT 0, 1");
            invL.toString();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            invL = new InvoicesList(db.InvoicesTab("SELECT * FROM Invoices ORDER BY ID DESC LIMIT 1, 1"));
            invL.toString();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
