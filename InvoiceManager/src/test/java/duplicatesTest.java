import Model.InvoiceManagerDB_DAO;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.*;

/**
 * Created by Karol Kistela on 01-May-16.
 */
public class duplicatesTest {
    public static void main(String[] args) throws ClassNotFoundException, FileNotFoundException {
        InvoiceManagerDB_DAO db = new InvoiceManagerDB_DAO();
        HashMap invDuplicMap = db.findDuplicatedInvNr();

        System.out.println("Founded: " + invDuplicMap.size() + " duplicates");
        Set set = invDuplicMap.entrySet();
        // Get an iterator
        Iterator i = set.iterator();
        // Display elements
        while(i.hasNext()) {
            Map.Entry me = (Map.Entry)i.next();
            System.out.print(me.getKey() + ": ");
            System.out.println(me.getValue());
        }

        System.out.println("Nr of duplicates for ID 51888 " + invDuplicMap.get("51888"));
    }
}
