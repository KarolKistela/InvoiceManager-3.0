package Model;

import Controller.Controller;
import spark.Request;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.file.*;
import java.io.*;
import java.sql.*;
import java.text.DecimalFormat;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.lang.Object.*;

import static java.nio.file.Files.exists;
import static java.nio.file.Files.isDirectory;

/**
 * Created by Karol Kistela on 30-Apr-16.
 */
public class Helpers {

    public static void main(String[] args) {
        String s = "967";
        System.out.println(s);
        System.out.println(doubleFormat(Double.parseDouble(s)));
    }

    public static void runShellCommand(String cmd) throws IOException, InterruptedException {
        Runtime runTime = Runtime.getRuntime();
        System.err.println(" shellCmd: " + cmd);
        String[] processCommand = {"cmd", "/c", cmd};
        Process shellProcess = runTime.exec(processCommand);
    }

    public static void runShellCommand2(String cmd) throws IOException, InterruptedException {
        Runtime runTime = Runtime.getRuntime();
        System.err.println(" shellCmd: " + cmd);
        String[] processCommand = {cmd};
        Process shellProcess = runTime.exec(processCommand);
    }
    public static int decimal(Double d) {
        return (int) ((d % Math.floor(d))*100);
    }

    public static boolean fileExists(String filePath) {
        try {
            Path path = FileSystems.getDefault().getPath(filePath);
            if (isDirectory(path)) {
                return false;
            } else {
                return exists(path);
            }
        } catch (InvalidPathException e) {
            System.err.println("invalid path: " + filePath);
            return false;
        }


    }

//        File file = new File(filePath);
//        long len = file.length();
//        boolean isD =file.isDirectory();

//        if (file.isDirectory()) {
//            return false;
//        } else if (file.length() == 0){
//
//            file.delete();
//            return false;
//        } else {
//            return true;
//        }
//    }

    public static String signConvertor(String s) {
        switch (s) {
            case "=":  return "eq";
            case "!=": return "neq";
            case ">":  return "gt";
            case ">=": return "gte";
            case "<":  return "lt";
            case "<=": return "lte";
            case "LIKE": return "LIKE";
            default: return "eq";
        }
    }

    public static String sqlQueryConstructor(Request request) {
        String columnName = request.params("columnName");
        String sign;
        switch (request.params("sign")){
            case "eq":  sign = "=";     break;
            case "neq": sign = "!=";    break;
            case "gt":  sign = ">";     break;
            case "gte": sign = ">=";    break;
            case "lt":  sign = "<";     break;
            case "lte": sign = "<=";    break;
            case "LIKE": sign = " LIKE "; break;
            default: sign = "="; break;
        }
        String value = null;
        try {
            value = URLDecoder.decode(request.params("value"),"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        String whereClause = "WHERE " + columnName + sign + "'" + value + "'" + " ";
        System.out.println("sqlQueryConstructor: " + "SELECT * FROM Invoices " + whereClause);

        return ("SELECT * FROM Invoices " + whereClause);
    }

    public static String sqlQueryConstructor2(Request request) {
        String columnName = request.params("columnName");
        String columnName2 = request.params("columnName2");
        String direction = request.params("direction");
        String sign;
        switch (request.params("sign")){
            case "eq":  sign = "=";     break;
            case "neq": sign = "!=";    break;
            case "gt":  sign = ">";     break;
            case "gte": sign = ">=";    break;
            case "lt":  sign = "<";     break;
            case "lte": sign = "<=";    break;
            case "LIKE": sign = " LIKE "; break;
            default: sign = "="; break;
        }
        String value = null;
        try {
            value = URLDecoder.decode(request.params("value"),"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        String whereClause = "WHERE " + columnName + sign + "'" + value + "'" + " ";
        System.out.println("sqlQueryConstructor: " + "SELECT * FROM Invoices " + whereClause);

        String orderByClause = "ORDER BY " + columnName2 + " " + direction;

        return ("SELECT * FROM Invoices " + whereClause + orderByClause);
    }

    public static String sqlQueryConstructorInvNr(Request request) throws ClassNotFoundException, SQLException, FileNotFoundException {
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


    public static String doubleFormat(double d) {
        DecimalFormat myFormatter = new DecimalFormat("###,##0.00");
        String s = myFormatter.format(d).replace(","," ");
        return s;
    }

    public static boolean InvoicesManagerDBconnection(String imDBPath) throws ClassNotFoundException {
        String query = "SELECT count(ID) FROM Invoices;";
        System.err.println("Testing connection to DB");
        System.err.println("DB path: " + imDBPath);
        System.err.println("  Query: " + query);

        Connection connection = null;

        Class.forName("org.sqlite.JDBC"); //ClassNotFoundException

        if (!fileExists(imDBPath)) {
            imDBPath = "src/main/resources/InvoiceManagerCFG/saveToDelete.file";
        }
            try {
                // create a database connection
                connection = DriverManager.getConnection("jdbc:sqlite:" + imDBPath);
                Statement statement = connection.createStatement();
                statement.setQueryTimeout(30);  // set timeout to 30 sec.

                ResultSet rs = statement.executeQuery(query);
                System.out.println("Nr of records in DB: " + rs.getInt(1));
                if (rs.getInt(1) != 0){
                    Controller.isConnectedToDB = true;
                    return true;
                } else {
                    return false;
                }
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

    public static boolean isEmail(String s){
        try {
            if (s.equals("")){}
        } catch (Exception e) {
            s = "null";
        }

        Pattern pattern = Pattern.compile("[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}");
        Matcher mat = pattern.matcher(s);

        if(mat.matches()){
            return true;
        }else{
            return false;
        }
    }
}
