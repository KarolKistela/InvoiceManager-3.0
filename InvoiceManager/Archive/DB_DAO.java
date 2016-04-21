package Model;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by mzjdx6 on 21-Apr-16.
 */
public class DB_DAO {
    private String DB_Path;

    public DB_DAO(String DB_Path) {
        this.DB_Path = DB_Path;
    }

    public List<ResultSet> sqlSelect(String query) throws ClassNotFoundException {
        Class.forName("org.sqlite.JDBC");

        Connection connection = null;
        try {
            // create a database connection
            connection = DriverManager.getConnection("jdbc:sqlite:" + this.getDB_Path());
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);  // set timeout to 30 sec.
            List<ResultSet> retVal = new LinkedList<ResultSet>();

            ResultSet rs = statement.executeQuery(query);
            while (rs.next()) {
                retVal.add(rs);
            }
            return retVal;
        } catch (SQLException e) {
            // if the error message is "out of memory",
            // it probably means no database file is found
            System.err.println(e.getMessage());

        } finally {
            try {
                if (connection != null)
                    connection.close();
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e);
            }
        }
        return null;
    }

    public String getDB_Path() {
        return DB_Path;
    }

    public void setDB_Path(String DB_Path) {
        this.DB_Path = DB_Path;
    }
}
