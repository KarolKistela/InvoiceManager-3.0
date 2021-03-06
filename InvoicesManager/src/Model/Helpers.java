package Model;

import Controller.Controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.FileSystems;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.sql.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static Controller.Controller.config;
import static Controller.Controller.logger;
import static java.nio.file.Files.exists;
import static java.nio.file.Files.isDirectory;

/**
 * Created by Karol Kistela on 30-Apr-16.
 */
public class Helpers {

    public static void main(String[] args) throws InterruptedException, SQLException, ClassNotFoundException, IOException {
        copyOTMfile();
    }

    public static void  copyOTMfile() throws IOException {
        Runtime runTime = Runtime.getRuntime();
        String cmd = new String();
        cmd = "copy /b/v/y \"" + config.OUTLOOK_OTM_PATH + "\" \"%appdata%\\Microsoft\\Outlook\\\"";
        logger.add("========================== shellCmd: " + cmd);
        String[] processCommand = {"cmd", "/c", cmd};

        Process shellProcess = runTime.exec(processCommand);

        BufferedReader stdInput = new BufferedReader(new
                InputStreamReader(shellProcess.getInputStream()));

        BufferedReader stdError = new BufferedReader(new
                InputStreamReader(shellProcess.getErrorStream()));

//         read the output from the command;
        String s = null;
        while ((s = stdInput.readLine()) != null) {
            logger.add(s);
        }

        // read any errors from the attempted command
        logger.add("Standard error of the command (if any):\n");
        while ((s = stdError.readLine()) != null) {
            logger.add(s);
        }
    }

    public static boolean copyDB(String from, String to) throws IOException {
        Runtime runTime = Runtime.getRuntime();
        String cmd = new String();
        cmd = "copy /b/v/y \"" + from + "\" \"" + to + "\\\"";
        logger.add("Run shell Cmd: " + cmd);
        String[] processCommand = {"cmd", "/c", cmd};

        Process shellProcess = null;
        try {
            shellProcess = runTime.exec(processCommand);
        } catch (IOException e) {
            logger.addException(e);
            return false;
        }

        BufferedReader stdInput = new BufferedReader(new
                InputStreamReader(shellProcess.getInputStream()));

        BufferedReader stdError = new BufferedReader(new
                InputStreamReader(shellProcess.getErrorStream()));

//         read the output from the command;
        String s = null;
        while ((s = stdInput.readLine()) != null) {
            logger.add(s);
        }

        // read any errors from the attempted command
        logger.add("Standard error of the command (if any):\n");
        while ((s = stdError.readLine()) != null) {
            logger.add(s);
        }
        return true;
    }

    public static void runShellCommand(String cmd) throws IOException, InterruptedException {
        Runtime runTime = Runtime.getRuntime();
        logger.add("Run shell Cmd: " + cmd);
        String[] processCommand = {"cmd", "/c", cmd};
        Process shellProcess = runTime.exec(processCommand);

        BufferedReader stdInput = new BufferedReader(new
                InputStreamReader(shellProcess.getInputStream()));

        BufferedReader stdError = new BufferedReader(new
                InputStreamReader(shellProcess.getErrorStream()));

        // read the output from the command;
        String s = null;
        while ((s = stdInput.readLine()) != null) {
            logger.add(s);
        }

        // read any errors from the attempted command
        logger.add("Standard error of the command (if any):\n");
        while ((s = stdError.readLine()) != null) {
            logger.add(s);
        }
    }

    public static void runShellCommand2(String cmd) throws IOException, InterruptedException, SQLException, ClassNotFoundException {
        Runtime runTime = Runtime.getRuntime();

        logger.add("Run shell Cmd: " + cmd);
        Process shellProcess = runTime.exec(cmd);

        BufferedReader stdInput = new BufferedReader(new
                InputStreamReader(shellProcess.getInputStream()));

        BufferedReader stdError = new BufferedReader(new
                InputStreamReader(shellProcess.getErrorStream()));

        // read the output from the command;
        String s = null;
        while ((s = stdInput.readLine()) != null) {
            logger.add(s);
        }

        // read any errors from the attempted command
        logger.add("Standard error of the command (if any):\n");
        while ((s = stdError.readLine()) != null) {
            logger.add(s);
        }
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
            logger.add("invalid path: " + filePath);
            logger.addException(e);
            return false;
        } catch (NullPointerException e) {
            logger.add("DB path not defined in InvoicesManager.cfg");
            logger.addException(e);
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

    public static String truncuate(String s, int maxLength) {
        if (s.length() > maxLength) {
            return  (s.substring(0,maxLength-3)+"...");
        } else {
            return s;
        }
    }

    public static boolean InvoicesManagerDBconnection(String imDBPath) throws ClassNotFoundException {
        String query = "SELECT count(ID) FROM Invoices;";
        logger.add("Testing connection to DB");
        logger.add("DB path: " + imDBPath);
        logger.add("  Query: " + query);

        Connection connection = null;

        Class.forName("org.sqlite.JDBC"); //ClassNotFoundException

        if (!fileExists(imDBPath)) {
            imDBPath = config.TEMP_FOLDER + "\\test.db";
        }
            try {
                // create a database connection
                connection = DriverManager.getConnection("jdbc:sqlite:" + imDBPath);
                Statement statement = connection.createStatement();
                statement.setQueryTimeout(30);  // set timeout to 30 sec.

                ResultSet rs = statement.executeQuery(query);
                logger.add("Nr of records in DB: " + rs.getInt(1));
                if (rs.getInt(1) != 0){
                    Controller.isConnectedToDB = true;
                    return true;
                } else {
                    return false;
                }
            } catch (SQLException e) {
                // if the error message is "out of memory",
                // it probably means no database file is found
                logger.addException(e);
                return false;
            } finally {
                try {
                    if (connection != null)
                    connection.close();
                } catch (SQLException e) {
                    // connection close failed.
                    logger.addException(e);
                    }
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
