package Model;

import Controller.Controller;
import Model.DAO.InvoiceManagerDB_DAO;
import spark.Request;

import java.io.*;
import java.net.URLDecoder;
import java.nio.file.FileSystems;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.sql.*;
import java.text.DecimalFormat;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.nio.file.Files.exists;
import static java.nio.file.Files.isDirectory;

/**
 * Created by Karol Kistela on 30-Apr-16.
 */
public class Helpers {

    public static void main(String[] args) throws InterruptedException, SQLException, ClassNotFoundException, IOException {
        int i = 49544;
        runShellCommand("PATH");
    }

    public static void runShellCommand(String cmd) throws IOException, InterruptedException {
        Runtime runTime = Runtime.getRuntime();
        System.out.println("========================== shellCmd: " + cmd);
        String[] processCommand = {"cmd", "/c", cmd};
        Process shellProcess = runTime.exec(processCommand);

        BufferedReader stdInput = new BufferedReader(new
                InputStreamReader(shellProcess.getInputStream()));

        BufferedReader stdError = new BufferedReader(new
                InputStreamReader(shellProcess.getErrorStream()));

        // read the output from the command;
        String s = null;
        while ((s = stdInput.readLine()) != null) {
            System.out.println(s);
        }

        // read any errors from the attempted command
        System.out.println("Standard error of the command (if any):\n");
        while ((s = stdError.readLine()) != null) {
            System.out.println(s);
        }
    }

    public static void runShellCommand2(int i) throws IOException, InterruptedException, SQLException, ClassNotFoundException {
        Runtime runTime = Runtime.getRuntime();
        Invoice inv = new InvoiceManagerDB_DAO().sqlSELECTid(i);

        String cmd = "\"outlook /c ipm.note /m \"" + inv.getAuthContact() + "&subject=ID%3A%20" + inv.getID() + "\" /a " + Controller.ImCFG.getImExternalFolderPath() + inv.getInvScanPath() + "\"";

        System.out.println("========================== shellCmd: " + cmd);
        String[] processCommand = {"cmd", "/c", cmd};
        Process shellProcess = runTime.exec(processCommand);

        BufferedReader stdInput = new BufferedReader(new
                InputStreamReader(shellProcess.getInputStream()));

        BufferedReader stdError = new BufferedReader(new
                InputStreamReader(shellProcess.getErrorStream()));

        // read the output from the command;
        String s = null;
        while ((s = stdInput.readLine()) != null) {
            System.out.println(s);
        }

        // read any errors from the attempted command
        System.out.println("Standard error of the command (if any):\n");
        while ((s = stdError.readLine()) != null) {
            System.out.println(s);
        }
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


    public static String signConvertor(String s) {
        switch (s) {
            case "=":  return "eq";
            case "!=": return "neq";
            case ">":  return "gt";
            case ">=": return "gte";
            case "<":  return "lt";
            case "<=": return "lte";
            case "LIKE": return "LIKE";
            case "NOT LIKE": return  "NOT LIKE";
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
            case "NOT%20LIKE": sign = " NOT LIKE "; break;
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
            case "NOT%20LIKE": sign = " NOT LIKE "; break;
            default: sign = "="; break;
        }
        String value = null;
        try {
            value = URLDecoder.decode(request.params("value"),"UTF-8");
            if (value.equals("null")) {
                value = "";
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            value = "";
        }

        String whereClause = "WHERE " + columnName + sign + "'" + value + "'" + " ";
        String orderByClause = "ORDER BY " + columnName2 + " " + direction;

        System.out.println("sqlQueryConstructor2: " + "SELECT * FROM Invoices " + whereClause + orderByClause);
        return ("SELECT * FROM Invoices " + whereClause + orderByClause);
    }

    public static String sqlQueryConstructor2(Request request, String columnName3, String value3 ) {
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
            case "NOT%20LIKE": sign = " NOT LIKE "; break;
            default: sign = "="; break;
        }
        String value = null;
        try {
            value = URLDecoder.decode(request.params("value"),"UTF-8");
            if (value.equals("null")) {
                value = "";
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            value = "";
        }

        String colName3 = columnName3;
        String val3 = null;
        try {
            val3 = URLDecoder.decode(request.params("value3"),"UTF-8");
            if (value.equals("null")) {
                value = "";
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            value = "";
        }


        String whereClause = "WHERE " + columnName + sign + "'" + value + "'" + " ";
        String whereClause2 = " AND " + colName3 + "=" + "'" + val3 + "'"+ " ";
        String orderByClause = "ORDER BY " + columnName2 + " " + direction;

        System.out.println("sqlQueryConstructor2: " + "SELECT * FROM Invoices " + whereClause + whereClause2 + orderByClause);
        return ("SELECT * FROM Invoices " + whereClause + whereClause2 + orderByClause);
    }

    public static String sqlQueryConstructor3(Request request, Integer filterNR, List<String[]> filters) {
        String query;
        if (filters.get(filterNR)[3].equals("1")) {
            query = filters.get(filterNR)[2];
        } else {
            query = filters.get(filterNR)[2] + " ORDER BY " + request.params(":columnName2") + " " + request.params("direction");
        }

        System.out.println("sqlQueryConstructor3: " + query);
        return query;
    }

    public static String advanceSearchSQLqueryConstructor(Request request) {
        String qCol1 = request.queryParams("search_query_columns");
        String qVal1;
        if (request.queryParams("search_query_value").equals("null")) {
            qVal1 = "''";
        } else {
            qVal1 = "'"+request.queryParams("search_query_value")+"'";
        }
        String qSig1 = " " + request.queryParams("search_query_sign") + " ";
        String whereC1 = "WHERE " + qCol1 + qSig1 + qVal1 + " ";

        String qCol2 = request.queryParams("search_query_columns2");
        String qVal2;
        if (request.queryParams("search_query_value2").equals("null")) {
            qVal2 = "''";
        } else {
            qVal2 = "'"+request.queryParams("search_query_value2")+"'";
        }
        String qSig2 = " " + request.queryParams("search_query_sign2") + " ";
        String and1 = "AND " + qCol2 + qSig2 + qVal2 + " ";
        if (qCol2.equals("")) and1 = "";

        String qCol3 = request.queryParams("search_query_columns3");
        String qVal3;
        if (request.queryParams("search_query_value3").equals("null")) {
            qVal3 = "''";
        } else {
            qVal3 = "'"+request.queryParams("search_query_value3")+"'";
        }
        String qSig3 = " " + request.queryParams("search_query_sign3") + " ";
        String and2 = "AND " + qCol3 + qSig3 + qVal3 + " ";
        if (qCol3.equals("")) and2 = "";

        String sqlQ = "SELECT * FROM Invoices " + whereC1 + and1 + and2;

        return sqlQ;
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
        DecimalFormat myFormatter = new DecimalFormat("##0.00");
        String s = myFormatter.format(d);
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
