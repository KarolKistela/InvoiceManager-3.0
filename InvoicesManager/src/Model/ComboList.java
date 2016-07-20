package Model;

import Controller.Controller;
import Model.DAO.InvoicesComboLists;

import java.io.FileNotFoundException;
import java.sql.SQLException;

/**
 * Created by Karol Kistela on 11-Jul-16.
 */
public class ComboList {
    private InvoicesComboLists DAO;

    public ComboList() throws FileNotFoundException, SQLException, ClassNotFoundException {
        try {
            this.DAO = new InvoicesComboLists();
            Controller.ImCFG.updateComboLists(DAO);
            Controller.ImCFG.synchronizeComboLists(DAO);
        } catch (Exception e) {
            Controller.logger.addException(e);
        }
    }

    public void reload() throws FileNotFoundException, SQLException, ClassNotFoundException {
        try {
            this.DAO = new InvoicesComboLists();
            Controller.ImCFG.updateComboLists(DAO);
            Controller.ImCFG.synchronizeComboLists(DAO);
        } catch (Exception e) {
            Controller.logger.addException(e);
        }
    }

    public String getSuppliersOptionValue() {
        String retVal = "";
        for (String s: DAO.suppliers
                ) {
            retVal += "<option value=\"" + s + "\">";
        }
        return retVal;
    }

    public String getCurrencyOptionValue() {
        String retVal = "";
        for (String s: DAO.currencies
                ) {
            retVal += "<option value=\"" + s + "\">";
        }
        return retVal;
    }

    public String getAuthContactOptionValue() {
        String retVal = "";
        for (String s: DAO.authContact
                ) {
            retVal += "<option value=\"" + s + "\">";
        }
        return retVal;
    }

    public String getContactGenpactOptionValue() {
        String retVal = "";
        for (String s: DAO.contactGenpact
                ) {
            retVal += "<option value=\"" + s + "\">";
        }
        return retVal;
    }

    public String getUserOptionValue() {
        String retVal = "";
        for (String s: DAO.users
                ) {
            retVal += "<option value=\"" + s + "\">";
        }
        return retVal;
    }
}
