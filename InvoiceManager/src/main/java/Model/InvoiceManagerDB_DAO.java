package Model;

/**
 * Created by mzjdx6 on 20-Mar-16.
 */

import View.HTMLviewGenerator;
import freemarker.template.Configuration;
import freemarker.template.SimpleHash;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.IOException;
import java.io.StringWriter;
import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class InvoiceManagerDB_DAO {
    private String DB_Path;
    private final Configuration cfg = new Configuration();

    public InvoiceManagerDB_DAO(String DB_Path) {
        this.DB_Path = DB_Path;
        cfg.setClassForTemplateLoading(HTMLviewGenerator.class, "/FTL templates/");
    }

    public InvoicesList sqlQuery(String query) throws ClassNotFoundException, SQLException {
        Class.forName("org.sqlite.JDBC");
        InvoicesList retVal = new InvoicesList();

        Connection connection = null;
        try
        {
            // create a database connection
            connection = DriverManager.getConnection("jdbc:sqlite:" + this.DB_Path);
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);  // set timeout to 30 sec.

            ResultSet rs = statement.executeQuery(query);
            while (rs.next()){
                retVal.addInvoice(new Invoice(rs));
            }
            return retVal;
        }
        catch(SQLException e)
        {
            // if the error message is "out of memory",
            // it probably means no database file is found
            System.err.println(e.getMessage());
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
        return null;
    }

    public List<Invoice> InvoicesTab(String selectQuery) throws ClassNotFoundException, SQLException {
        Class.forName("org.sqlite.JDBC");
        List<Invoice> retVal = new LinkedList<>();

        Connection connection = null;
        try
        {
            // create a database connection
            connection = DriverManager.getConnection("jdbc:sqlite:" + this.DB_Path);
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);  // set timeout to 30 sec.

            ResultSet rs = statement.executeQuery(selectQuery);
            while (rs.next()){
                retVal.add(new Invoice(rs));
            }
            return retVal;
        }
        catch(SQLException e)
        {
            // if the error message is "out of memory",
            // it probably means no database file is found
            System.err.println(e.getMessage());
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
        return null;
    }

    // Depreciated
    public String getTable(String SQLquery, int pageNr, int rowsPerPage, double tableWidth) throws ClassNotFoundException, IOException, TemplateException {
        // load the sqlite-JDBC driver using the current class loader
        Class.forName("org.sqlite.JDBC");
        String limit = "LIMIT " + (pageNr - 1)*rowsPerPage + ", " + rowsPerPage + ";";
        SimpleHash replaceMap = new SimpleHash();
        StringWriter tableRow = new StringWriter();
        String retVal = new String();
        Template template = cfg.getTemplate("/Parts/tableRow.ftl");
        System.out.println("DB: " + this.DB_Path);
        System.out.println("Query: " + SQLquery + limit);

        Connection connection = null;
        try
        {
            System.out.println("tutaj");
            // create a database connection
            connection = DriverManager.getConnection("jdbc:sqlite:" + DB_Path);
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);  // set timeout to 30 sec.
            // "LIMIT 0,1" change to limit
            ResultSet rs = statement.executeQuery(SQLquery + limit);
            while (rs.next()){
                System.out.println("inside while");
//          TODO: add user color to ImCFG
//          userColor - color of left border, depend on which user added record to getDBview
                replaceMap.put("userColor","red");
//          rowColor - row color default ""
                replaceMap.put("rowColor",rs.getString(23));
//          ID - from InvoiceManagerDB.sql
                replaceMap.put("ID",rs.getString(1));
//          barCode - BC for ID, from InvoiceManagerDB.sql
                replaceMap.put("barCode",rs.getString(2));
//          scan - TODO: "barcode" for inv from scanning center, "print" for inv from floor scaner
                replaceMap.put("scan","barcode");
//          EntryDate - from InvoiceManagerDB.sql converter 23 apr, 2016 <-> 20160423
                replaceMap.put("EntryDate",rs.getString(3));
//          tableWidth - from ImCFG
                replaceMap.put("tableWidth",tableWidth);
//          headerWidth - 100 - tableWidth
                replaceMap.put("headerWidth",100-tableWidth);
//          Supplier
                replaceMap.put("Supplier",rs.getString(5));
//          InvoiceNR
                replaceMap.put("InvoiceNR",rs.getString(6));
//          PO
                replaceMap.put("PO",rs.getString(8));
//          NetPrice
                replaceMap.put("NetPrice", rs.getString(9) + " " + rs.getString(10));
//          Authorization
                replaceMap.put("Authorization",rs.getString(13));
//          GR
                replaceMap.put("GR",rs.getString(18));

                template.process(replaceMap, tableRow);

                retVal = (tableRow.toString());
            }

            return retVal;
        }
        catch(SQLException e)
        {
            // if the error message is "out of memory",
            // it probably means no database file is found
            System.err.println(e.getMessage());
            return "Upss DB is sick!!!";
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

    public int sqlCount(String query) throws ClassNotFoundException, SQLException {
        Class.forName("org.sqlite.JDBC");
        System.out.println("DB: " + this.DB_Path);
        System.out.println("Query: " + query);

        Connection connection = null;
        try
        {
            // create a database connection
            connection = DriverManager.getConnection("jdbc:sqlite:" + DB_Path);
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);  // set timeout to 30 sec.

            ResultSet rs = statement.executeQuery(query);

            return rs.getInt(1);
        }
        catch(SQLException e)
        {
            // if the error message is "out of memory",
            // it probably means no database file is found
            System.err.println(e.getMessage());
            return 0;
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