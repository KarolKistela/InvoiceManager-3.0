package Model;

/**
 * Created by mzjdx6 on 20-Mar-16.
 */

import java.sql.*;

public class InvoiceManagerDB_DAO {
    private String DB_Path;

    public InvoiceManagerDB_DAO(String DB_Path) {
        this.DB_Path = DB_Path;
    }

    public ResultSet sqlQuery(String SQLquery) throws ClassNotFoundException {
        // load the sqlite-JDBC driver using the current class loader
        Class.forName("org.sqlite.JDBC");

        Connection connection = null;
        try
        {
            // create a database connection
            connection = DriverManager.getConnection("jdbc:sqlite:" + DB_Path);
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);  // set timeout to 30 sec.

            ResultSet rs = statement.executeQuery(SQLquery);
            return rs;
        }
        catch(SQLException e)
        {
            // if the error message is "out of memory",
            // it probably means no database file is found
            System.err.println(e.getMessage());
            return null;
        }
        finally
        {
            try
            {
                if(connection != null)
                    connection.close();
            }
            catch(SQLException e)
            {
                // connection close failed.
                System.err.println(e);
            }
        }
    }

}

//while(rs.next())
//        {
//        // read the result set
//        TableRow = TableRow.replace("ID", rs.getString("ID"));
//        TableRow = TableRow.replace("BarCode", rs.getString("BarCode"));
//        TableRow = TableRow.replace("Supplier", rs.getString("Suplier"));
//        TableRow = TableRow.replace("Invoice_Number", rs.getString("Invoice_Number"));
//        TableRow = TableRow.replace("Purchase_Order", rs.getString("Purchase_Order"));
//        TableRow = TableRow.replace("Net_Price", rs.getString("Net_Price"));
//        TableRow = TableRow.replace("Currency", rs.getString("Currency"));
//        TableRow = TableRow.replace("Goods Receipt nr", rs.getString("GR_Nr"));
//        TableRow = TableRow.replace("*nr", Integer.toString(i));
//        }