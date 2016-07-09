import Model.DAO.InvoiceManagerCFG;

import static Controller.Controller.CFG_PATH;

/**
 * Created by mzjdx6 on 07-Apr-16.
 */
public class InvoiceManagerCFG_test {
    public static void main(String[] args) {
        InvoiceManagerCFG ImCFG = null;
        try {
            ImCFG = new InvoiceManagerCFG(CFG_PATH);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

//        System.out.println("nr of columns to display: " + ImCFG.getNrOfColumnsToDisplay());
//        System.out.println("External Folder Path: " + ImCFG.getImExternalFolderPath());
//        System.out.println("DBview Path: " + ImCFG.getImDBPath());
//        System.out.println("Rows to display: " + ImCFG.getRowsPerPage());
//        System.out.println("BackGround color: " + ImCFG.getBackgroundColor());
//
//        for (Object K:ImCFG.getSetOfColumns()) {
//            System.out.println("Width of column " + K +": " + ImCFG.getColumnWidth(K));
//        }
        for (String[] s: ImCFG.getFilters()
             ) {
            System.out.println("ID: " + s[0]);
            System.out.println("FilterName: " + s[1]);
            System.out.println("sqlQuery" + s[2]);
        }
    }
}
