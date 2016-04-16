import Model.InvoiceManagerCFG;

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

        System.out.println("nr of columns to display: " + ImCFG.getNrOfColumnsToDisplay());
        System.out.println("External Folder Path: " + ImCFG.getImExternalFolderPath());
        System.out.println("DataBase Path: " + ImCFG.getImDBPath());
        System.out.println("Rows to display: " + ImCFG.getRowsPerPage());
        System.out.println("BackGround color: " + ImCFG.getBackgroundColor());

        for (Object K:ImCFG.getSetOfColumns()) {
            System.out.println("Width of column " + K +": " + ImCFG.getColumnWidth(K));
        }
    }
}
