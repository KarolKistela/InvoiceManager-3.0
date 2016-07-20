package Model;

import Model.DAO.InvoicesFullView;

import java.io.FileNotFoundException;
import java.sql.ResultSet;
import java.sql.SQLException;

import static Controller.Controller.logger;

/**
 * Created by Karol Kistela on 02-May-16.
 */
public class User {
    private String userID;
    private String userMail;
    private String userColor;

    public User(String netID, String Email, String Color) throws ClassNotFoundException {
            this.userID = netID.toUpperCase();
            this.userColor = Color;
            this.userMail = Email;
    }

    public User(ResultSet rs) throws SQLException {
        this.userID = rs.getString("NetID");
        this.userMail = rs.getString("Email");
        this.userColor = rs.getString("UserColor");
    }

    @Override
    public String toString() {
        return "User{" +
                "userID='" + userID + '\'' +
                ", userMail='" + userMail + '\'' +
                ", userColor='" + userColor + '\'' +
                '}';
    }

    public void upsertUserToIMDB() throws FileNotFoundException, ClassNotFoundException {
        InvoicesFullView db = new InvoicesFullView();
        try {
            if (db.isUserInDB(this.userID)) {
                db.upsertUser(this);
            } else {
                db.insertUser(this);
            }
        } catch (Exception e) {
            logger.addException(e);
        }
    }

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
