import InvoiceManagerCFG.*;

import javax.swing.text.StringContent;
import javax.swing.text.html.HTMLDocument;
import java.util.HashMap;
import java.util.*;

/**
 * Created by mzjdx6 on 07-Apr-16.
 */
public class InvoiceManagerCFG_test {
    public static void main(String[] args) {
        InvoiceManagerCFG ImCFG = null;
        try {
            ImCFG = new InvoiceManagerCFG();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        HashMap hm = ImCFG.getColumnsAndWidth();

        // Get a set of the entries
        Set set = hm.entrySet();
        // Get an iterator
        Iterator i = set.iterator();
        // Display elements
        System.out.println("columnsAndWidth (K:V)");
        while(i.hasNext()) {
            Map.Entry me = (Map.Entry)i.next();
            System.out.print(me.getKey() + ": ");
            System.out.println(me.getValue());
        }
        System.out.println("nr of columns to display: " + ImCFG.getNrOfColumnsToDisplay());
        System.out.println("External Folder Path: " + ImCFG.getImExternalFolderPath());
        System.out.println("DataBase Path: " + ImCFG.getImDBPath());
        System.out.println("Rows to display: " + ImCFG.getRowsPerPage());

        int BarcodeWidth = (String) ImCFG.getWidth("Barcode");

        System.out.println("width of: " + ImCFG.getWidth("Barcode"));


    }
    }
