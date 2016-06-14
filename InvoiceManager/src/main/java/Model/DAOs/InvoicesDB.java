package Model.DAOs;

import Controller.Controller;

import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import static Model.Helpers.fileExists;

/**
 * Created by Karol Kistela on 14-Jun-16.
 */
abstract class InvoicesDB {
    public String DB_Path;

    public InvoicesDB() throws ClassNotFoundException, FileNotFoundException {
        Class.forName("org.sqlite.JDBC");

        if (fileExists(Controller.ImCFG.getImDBPath())) {
            this.DB_Path = Controller.ImCFG.getImDBPath();
        } else {
            this.DB_Path = "src/main/resources/InvoiceManagerCFG/saveToDelete.file";
            throw new FileNotFoundException();
        }
    }

    public String[] toStringArray(ResultSet rs) throws SQLException {
        String[] retVal;
        retVal = new String[rs.getMetaData().getColumnCount()];

        for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
            retVal[i - 1] = rs.getString(i);
        }
        return retVal;
    }
}
