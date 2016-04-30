import Model.InvoiceManagerDB_DAO;

import java.io.File;
import java.io.IOException;

/**
 * Created by Karol Kistela on 30-Apr-16.
 */
public class runtimeTest {
    public static void main(String[] args) throws ClassNotFoundException, IOException, InterruptedException {
        Runtime runTime = Runtime.getRuntime();
        String filePath = new InvoiceManagerDB_DAO().filePath("51600","InvScanPath");
        System.out.println(filePath);

        File f = new File(filePath);
        if(f.exists() && !f.isDirectory()) {
            System.out.println("Jest plik");
        } else {
            System.out.println("nie ma plika");
        }

        Process shellProcess = runTime.exec("rundll32 SHELL32.DLL,ShellExec_RunDLL " + filePath);

        shellProcess.waitFor();
    }
}
