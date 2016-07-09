import Model.Invoice;
import Model.DAO.InvoiceManagerDB_DAO;

import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by mzjdx6 on 21-Apr-16.
 */
public class InvoiceManagerDB_DAO_test {
    public static void main(String[] args) throws FileNotFoundException {
        if (args.length == 0) {
            new InvoiceManagerDB_DAO_test("C:\\InvoiceManagerExt\\Invoices_manager_DB\\InvoiceManager.db");
        }
        else {
            new InvoiceManagerDB_DAO_test(args[0]);
        }
    }

    public InvoiceManagerDB_DAO_test(String s) throws FileNotFoundException {
        InvoiceManagerDB_DAO db = null;
        try {
            db = new InvoiceManagerDB_DAO();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        List<Invoice> invL =  new LinkedList<Invoice>();
//        try {
//            System.out.println("Nr of records in DB: " + db.sqlCOUNT("SELECT count(*) FROM Invoices;"));
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        try {
//            invL = db.sqlSELECT("SELECT * FROM Invoices ORDER BY ID DESC LIMIT 0, 1");
//            invL.toString();
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
        try {
            List<String[]> list;
            list = db.sqlSELECT("SELECT * FROM InvoicesMetaData WHERE DisplayOrder>0 ORDER BY DisplayOrder ASC ",1,false,false);

            for (String[] sarray: list) {
                System.out.println(sarray[2]);
            }


        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
