package Model;

import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Karol Kistela on 31-May-16.
 */
public class Suppliers {
    private List<String[]> supplierList = new LinkedList<>();

    public static void main(String[] args) throws FileNotFoundException, SQLException, ClassNotFoundException {
        Suppliers suppliers = new Suppliers();

        for (String[] s:suppliers.getSupplierList()
             ) {
            System.out.println(s[0]);
        }
    }

    public Suppliers() {
        try {
            this.supplierList = new InvoiceManagerDB_DAO().sqlSELECT("SELECT Supplier FROM Invoices GROUP BY Supplier",1,false,false);
        } catch (Exception e) {
            e.printStackTrace();
            String[] noDBconnection = new String[1];
            noDBconnection[0] = "No DB connection";
            this.supplierList.add(noDBconnection);
        }
        this.supplierList.get(0)[0] = "null";   // first empty record will be convertet to null value - it is needed when you want to search empty values in DB
    }

    public List<String[]> getSupplierList() {
        return supplierList;
    }

    public void setSupplierList(List<String[]> supplierList) {
        this.supplierList = supplierList;
    }
}