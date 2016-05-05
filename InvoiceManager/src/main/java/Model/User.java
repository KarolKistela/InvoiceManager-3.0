package Model;

import java.io.FileNotFoundException;
import java.sql.SQLException;

/**
 * Created by Karol Kistela on 02-May-16.
 */
public class User {
    private String userID;
    private String userMail;
    private String userColor;

    public static void main(String[] args) throws ClassNotFoundException, FileNotFoundException {
        InvoiceManagerCFG cfg = new InvoiceManagerCFG();
        User u = new User(cfg.getUserNetID(), cfg.getUserColor(), cfg.getUserEmail());

        if (u.isUserInDB()) {
            System.out.println(u.toString());
        } else {
            System.out.println("Probably there is no such a user!");
        }
    }

    public User(String netID, String Email, String Color) throws ClassNotFoundException {
            this.userID = netID;
            this.userColor = Color;
            this.userMail = Email;
        }

    @Override
    public String toString() {
        return "User{" +
                "userID='" + userID + '\'' +
                ", userMail='" + userMail + '\'' +
                ", userColor='" + userColor + '\'' +
                '}';
    }

    public void upsertUserToIMDB() throws ClassNotFoundException {
        InvoiceManagerDB_DAO db = null;
        try {
            db = new InvoiceManagerDB_DAO();
            if (isUserInDB()) {
                db.upsertUser(this);
            } else {
                db.insertUser(this);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public Boolean isUserInDB() throws ClassNotFoundException {
        InvoiceManagerDB_DAO db = null;

        try {
            db = new InvoiceManagerDB_DAO();
            int i = db.sqlCOUNT("SELECT count(NetID) FROM Users WHERE NetID='"+this.getUserID()+"'");
            if (i == 0) {
                return false;
            } else {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    };

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getUserMail() {
        return userMail;
    }

    public void setUserMail(String userMail) {
        this.userMail = userMail;
    }

    public String getUserColor() {
        return userColor;
    }

    public void setUserColor(String userColor) {
        this.userColor = userColor;
    }
}
