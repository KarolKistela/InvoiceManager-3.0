package Model;

/**
 * Created by mzjdx6 on 20-Mar-16.
 */

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class InvoiceManagerDB_DAO implements IMsqlite{
    private final String DB_Path;
    private InvoiceManagerCFG ImCFG;

    public InvoiceManagerDB_DAO() throws ClassNotFoundException {
        ImCFG = new InvoiceManagerCFG();
        this.DB_Path = ImCFG.getImDBPath();
    }

    public InvoiceManagerDB_DAO(String DB_Path) {
        this.DB_Path = DB_Path;
    }

    public List<String[]> sqlSELECT(String query, int pageNr, boolean withOrderByClause ,boolean withLimitClause) throws ClassNotFoundException, SQLException {
        Class.forName("org.sqlite.JDBC");
        List<String[]> retVal = new LinkedList<>();
        String orderByClause;
            if (withOrderByClause) {
                orderByClause = ImCFG.getOrderByClause();
            } else {
                orderByClause = "";
            }

        String limitClause;
            /* Make sure that LIMIT is correct sql statment: */
            if (withLimitClause) {
                if (pageNr < 0) pageNr = 0;
                if (pageNr > (this.sqlCOUNT(query.replace("*", "COUNT(*)")) / ImCFG.getRowsPerPage()))
                    pageNr = this.sqlCOUNT(query.replace("*", "COUNT(*)")) / ImCFG.getRowsPerPage() + 1;
                limitClause = " LIMIT " + ((pageNr - 1) * ImCFG.getRowsPerPage()) + ", " + ImCFG.getRowsPerPage() + ";";
            } else {
                limitClause = ";";
            }

        System.err.println("List<String[]> sqlSELECT(String query, int pageNr, boolean withOrderByClause ,boolean withLimitClause):");
        System.err.println("DB path: " + this.DB_Path);
        System.err.println("  Query: " + query + orderByClause + limitClause);

        Connection connection = null;
        try {
            // create a database connection
            connection = DriverManager.getConnection("jdbc:sqlite:" + this.DB_Path);
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);  // set timeout to 30 sec.

            ResultSet rs = statement.executeQuery(query + orderByClause + limitClause);

            while (rs.next()) {
                String[] row;
                row = new String[rs.getMetaData().getColumnCount()];

                for (int i=1; i <= rs.getMetaData().getColumnCount(); i++){
                    row[i-1]=rs.getString(i);
                }

                retVal.add(row);
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

    public List<Invoice> sqlSELECT(String query) throws ClassNotFoundException, SQLException {
        Class.forName("org.sqlite.JDBC");
        List<Invoice> retVal = new LinkedList<>();

        Connection connection = null;
        try {
            // create a database connection
            connection = DriverManager.getConnection("jdbc:sqlite:" + this.DB_Path);
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);  // set timeout to 30 sec.

            ResultSet rs = statement.executeQuery(query);
            while (rs.next()) {
                retVal.add(new Invoice(rs));
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

    public int sqlCOUNT(String query) throws ClassNotFoundException, SQLException {
        Class.forName("org.sqlite.JDBC");
        System.err.println("sqlCOUNT(String query)");
        System.err.println("DB path: " + this.DB_Path);
        System.err.println("  Query: " + query);

        Connection connection = null;
        try {
            // create a database connection
            connection = DriverManager.getConnection("jdbc:sqlite:" + DB_Path);
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);  // set timeout to 30 sec.


            ResultSet rs = statement.executeQuery(query);

            return rs.getInt(1);
        } catch (SQLException e) {
            // if the error message is "out of memory",
            // it probably means no database file is found
            System.err.println(e.getMessage());
            return 0;
        } finally {
            try {
                if (connection != null)
                    connection.close();
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e);
            }
        }
    }

    public String filePath(String ID, String columnName) throws ClassNotFoundException {
        Class.forName("org.sqlite.JDBC");

        Connection connection = null;
        try {
            // create a database connection
            connection = DriverManager.getConnection("jdbc:sqlite:" + DB_Path);
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);  // set timeout to 30 sec.
            String query = "SELECT " + columnName + " FROM Invoices WHERE ID = " + ID + ";";

            ResultSet rs = statement.executeQuery(query);
            System.err.println("DB path: " + this.DB_Path);
            System.err.println("  Query: " + query);
            System.err.println(" Return: " + ImCFG.getImExternalFolderPath() + rs.getString(1));

            return (ImCFG.getImExternalFolderPath() + rs.getString(1));
            } catch (SQLException e) {
            // if the error message is "out of memory",
            // it probably means no database file is found
                System.err.println(e.getMessage());
                return null;
            } finally {
                try {
                    if (connection != null)
                        connection.close();
                } catch (SQLException e) {
                // connection close failed.
                System.err.println(e);
            }
        }
    }
}
    // Depreciated

//    public InvoicesList sqlQuery(String query) throws ClassNotFoundException, SQLException {
//        Class.forName("org.sqlite.JDBC");
//        InvoicesList retVal = new InvoicesList();
//
//        Connection connection = null;
//        try
//        {
//            // create a database connection
//            connection = DriverManager.getConnection("jdbc:sqlite:" + this.DB_Path);
//            Statement statement = connection.createStatement();
//            statement.setQueryTimeout(30);  // set timeout to 30 sec.
//
//            ResultSet rs = statement.executeQuery(query);
//            while (rs.next()){
//                retVal.addInvoice(new Invoice(rs));
//            }
//            return retVal;
//        }
//        catch(SQLException e)
//        {
//            // if the error message is "out of memory",
//            // it probably means no database file is found
//            System.err.println(e.getMessage());
//        }
//        finally
//        {
//            try
//            {
//                if(connection != null)
//                    connection.close();
//            }
//            catch(SQLException e)
//            {
//                // connection close failed.
//                System.err.println(e);
//            }
//        }
//        return null;
//    }
//    public String getTable(String SQLquery, int pageNr, int rowsPerPage, double tableWidth) throws ClassNotFoundException, IOException, TemplateException {
//        // load the sqlite-JDBC driver using the current class loader
//        Class.forName("org.sqlite.JDBC");
//        String limit = "LIMIT " + (pageNr - 1)*rowsPerPage + ", " + rowsPerPage + ";";
//        SimpleHash replaceMap = new SimpleHash();
//        StringWriter tableRow = new StringWriter();
//        String retVal = new String();
//        Template template = cfg.getTemplate("/Parts/tableRow.ftl");
//        System.out.println("DB: " + this.DB_Path);
//        System.out.println("Query: " + SQLquery + limit);
//
//        Connection connection = null;
//        try
//        {
//            System.out.println("tutaj");
//            // create a database connection
//            connection = DriverManager.getConnection("jdbc:sqlite:" + DB_Path);
//            Statement statement = connection.createStatement();
//            statement.setQueryTimeout(30);  // set timeout to 30 sec.
//            // "LIMIT 0,1" change to limit
//            ResultSet rs = statement.executeQuery(SQLquery + limit);
//            while (rs.next()){
//                System.out.println("inside while");
////          TODO: add user color to ImCFG
////          userColor - color of left border, depend on which user added record to getDBview
//                replaceMap.put("userColor","red");
////          rowColor - row color default ""
//                replaceMap.put("rowColor",rs.getString(23));
////          ID - from InvoiceManagerDB.sql
//                replaceMap.put("ID",rs.getString(1));
////          barCode - BC for ID, from InvoiceManagerDB.sql
//                replaceMap.put("barCode",rs.getString(2));
////          scan - TODO: "barcode" for inv from scanning center, "print" for inv from floor scaner
//                replaceMap.put("scan","barcode");
////          EntryDate - from InvoiceManagerDB.sql converter 23 apr, 2016 <-> 20160423
//                replaceMap.put("EntryDate",rs.getString(3));
////          tableWidth - from ImCFG
//                replaceMap.put("tableWidth",tableWidth);
////          headerWidth - 100 - tableWidth
//                replaceMap.put("headerWidth",100-tableWidth);
////          Supplier
//                replaceMap.put("Supplier",rs.getString(5));
////          InvoiceNR
//                replaceMap.put("InvoiceNR",rs.getString(6));
////          PO
//                replaceMap.put("PO",rs.getString(8));
////          NetPrice
//                replaceMap.put("NetPrice", rs.getString(9) + " " + rs.getString(10));
////          Authorization
//                replaceMap.put("Authorization",rs.getString(13));
////          GR
//                replaceMap.put("GR",rs.getString(18));
//
//                template.process(replaceMap, tableRow);
//
//                retVal = (tableRow.toString());
//            }
//
//            return retVal;
//        }
//        catch(SQLException e)
//        {
//            // if the error message is "out of memory",
//            // it probably means no database file is found
//            System.err.println(e.getMessage());
//            return "Upss DB is sick!!!";
//        }
//        finally
//        {
//            try
//            {
//                if(connection != null)
//                    connection.close();
//            }
//            catch(SQLException e)
//            {
//                // connection close failed.
//                System.err.println(e);
//            }
//        }
//    }


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