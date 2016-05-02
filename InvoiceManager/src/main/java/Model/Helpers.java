package Model;

import spark.Request;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.sql.*;
import java.util.List;

/**
 * Created by Karol Kistela on 30-Apr-16.
 */
public class Helpers {
    public static void runShellCommand(String cmd) throws IOException, InterruptedException {
        Runtime runTime = Runtime.getRuntime();
        System.err.println(" shellCmd: " + cmd);
        String[] processCommand = {"cmd", "/c", cmd};
        Process shellProcess = runTime.exec(processCommand);
    }

    public static boolean fileExists(String filePath) {
        File f = new File(filePath);
        if(f.exists() && !f.isDirectory()) {
//            System.out.println(filePath + " EXISTS!");
            return true;
        } else {
//            System.out.println(filePath + " DOES NOT EXIST!");
            return false;
        }
    }

    public static String sqlQueryConstructor(Request request) {
        String columnName = request.params("columnName");
        String sign;
        switch (request.params("sign")){
            case "eq":  sign = "=";     break;
            case "neq": sign = "<>";    break;
            case "gt":  sign = ">";     break;
            case "gte": sign = ">=";    break;
            case "lt":  sign = "<";     break;
            case "lte": sign = "<=";    break;
            default: sign = "="; break;
        }
        String value = request.params("value").replace("%20"," ");

        String whereClause = "WHERE " + columnName + sign + "'" + value + "'" + " ";
        System.out.println("sqlQueryConstructor: " + "SELECT * FROM Invoices " + whereClause);

        return ("SELECT * FROM Invoices " + whereClause);
    }

    public static String sqlQueryConstructorInvNr(Request request) throws ClassNotFoundException, SQLException {
        List<String[]> rs = new InvoiceManagerDB_DAO().sqlSELECT("SELECT InvoiceNr FROM Invoices WHERE ID="+request.params("idNr"),1,false,false);
        String[] row = rs.get(0);

        String whereClause = "WHERE InvoiceNr=" + "'" + row[0] + "'" + " ";
        System.out.println("sqlQueryConstructor: " + "SELECT * FROM Invoices " + whereClause);

        return ("SELECT * FROM Invoices " + whereClause);
    }

    public static String getRout(Request request) {
        String columnName = request.params("columnName");
        String sign = request.params("sign");
        String value = request.params("value");

        System.out.println("getRout: " + "/Filter/Select/"+columnName+"/"+sign+"/"+value+"/");

        return "/Filter/Select/"+columnName+"/"+sign+"/"+value+"/";
    }

    public static String truncuate(String s, int maxLength) {
        if (s.length() > maxLength) {
            return  (s.substring(0,maxLength-3)+"...");
        } else {
            return s;
        }
    }

    public static String doubleFormat(String s) {
        if (s.indexOf(".") == -1) {
            return s+".00";
        } else if (s.indexOf(".") == 2) {
            return s+"0";
        } else {
            return s;
        }
    }

    public static boolean InvoicesManagerDBconnection(String imDBPath) throws ClassNotFoundException {
        String query = "SELECT count(ID) FROM Invoices;";
        System.err.println("Testing connection to DB");
        System.err.println("DB path: " + imDBPath);
        System.err.println("  Query: " + query);

        Connection connection = null;

        Class.forName("org.sqlite.JDBC"); //ClassNotFoundException

        try {
            // create a database connection
            connection = DriverManager.getConnection("jdbc:sqlite:" + imDBPath);
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);  // set timeout to 30 sec.

            ResultSet rs = statement.executeQuery(query);
            System.out.println("Nr of records in DB: " + rs.getInt(1));
            return true;
        } catch (SQLException e) {
            // if the error message is "out of memory",
            // it probably means no database file is found
            System.err.println(e.getMessage());
            return false;
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

    public static boolean isNull(Request req, String s){
        String retVal;
        try{
            retVal = req.queryParams(s);
            if (retVal.equals("")) {}
            return false;
        } catch (Exception e) {
            return true;
        }
    }
}
