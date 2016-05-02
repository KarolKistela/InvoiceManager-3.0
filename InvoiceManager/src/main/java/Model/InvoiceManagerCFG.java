package Model;

import spark.Request;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

import static Model.Helpers.*;

/**
 * Created by Karol Kistela on 07-Apr-16.
 * Configuration Class for Invoice Manager. Data are serialized in ../../resources/InvoiceManagerCFG/InvoiceManager.cfg database.
 */
public class InvoiceManagerCFG {
    private final String InvoicesManagerCFGPath = "src/main/resources/InvoiceManagerCFG/InvoiceManager.cfg";
    private String imExternalFolderPath;
    private String imDBPath;
    /** how many records will be displayed per page. It applies for getDBview main view and All Filters views. */
    private Integer rowsPerPage;
    /** Key - name of the column, Value - width of columnn in %. IMPORTANT: sum of widths cannot exceed 90%. Columns with value of 0% will not be displayed */
//    private LinkedHashMap<String, Double> columnsAndWidth = new LinkedHashMap<String, Double>();
    /** Calculated, based on columnsAndWidth. */
//    private int nrOfColumnsToDisplay;
    private String backgroundColor;
    private double tableWidth;
    private int totalNrOfPages;
    private List<String[]> filters;
    private String orderByClause;
    private boolean checkForInvDuplicates;
    private String userNetID;
    private String userEmail;
    private String userColor;
    private List<String[]> invoicesMetaData;

    public static void main(String[] args) throws ClassNotFoundException {
        InvoiceManagerCFG imcfg = new InvoiceManagerCFG();

        System.out.println(imcfg.toString());

        for (String[] s: imcfg.getInvoicesMetaData()
             ) {
            System.out.println(s[2]);
        }
    }

    public InvoiceManagerCFG() throws ClassNotFoundException {
        this.loadData();
    }

    /* Connetct to db cfg file at src/main/resources/InvoiceManagerCFG/InvoiceManager.cfg and load data from this file. Basically serialization for this class */
    public void loadData() throws ClassNotFoundException {
        // load the sqlite-JDBC driver using the current class loader
        Class.forName("org.sqlite.JDBC");

        Connection connection = null;
        try
        {
            // create a database connection
            connection = DriverManager.getConnection("jdbc:sqlite:" + this.InvoicesManagerCFGPath);
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);  // set timeout to 30 sec.
            ResultSet resultSet;

            resultSet = statement.executeQuery("SELECT * FROM CFG ORDER BY ID DESC LIMIT 1;");
            this.setImExternalFolderPath(resultSet.getString("imExternalFolderPath"));
            this.setImDBPath(resultSet.getString("imDBPath"));
            this.setRowsPerPage(resultSet.getInt("rowsPerPage"));
            this.setBackgroundColor(resultSet.getString("backgroundColor"));
            this.setTableWidth(85.5);  // not sure if needed
            this.setOrderByClause(resultSet.getString("OrderByClause"));
            this.setCheckForInvDuplicates(resultSet.getBoolean("InvDuplicates"));
            this.setUserNetID(resultSet.getString("userNetID"));
            this.setUserEmail(resultSet.getString("userEmail"));
            this.setUserColor(resultSet.getString("userColor"));

            // load (K,V) into columnsAndWidth
//            resultSet = statement.executeQuery("SELECT * FROM columnsAndWidth;");
//            while(resultSet.next())
//            {
//                this.putInColumnsAndWidth(resultSet.getString(1), resultSet.getDouble(2));
//            }

//            resultSet = statement.executeQuery("SELECT count() FROM columnsAndWidth WHERE columnWidth > 0;");
//            this.setNrOfColumnsToDisplay(resultSet.getInt(1));

//            this.setTotalNrOfPages(0);
            resultSet = statement.executeQuery("SELECT * FROM Filters");
            List<String[]> l = new LinkedList<>();
            while (resultSet.next()){
                String[] filter;
                filter = new String[resultSet.getMetaData().getColumnCount()];

                for (int i=1; i <= resultSet.getMetaData().getColumnCount(); i++){
                    filter[i-1]=resultSet.getString(i);
                }
                l.add(filter);
            }
            this.setFilters(l);

            resultSet = statement.executeQuery("SELECT * FROM InvoicesMetaData WHERE DisplayOrder>0 ORDER BY DisplayOrder ASC");
            List<String[]> metaDataList = new LinkedList<>();
            while (resultSet.next()){
                String[] metaData;
                metaData = new String[resultSet.getMetaData().getColumnCount()];

                for (int i=1; i <= resultSet.getMetaData().getColumnCount(); i++){
                    metaData[i-1]=resultSet.getString(i);
                }
                metaDataList.add(metaData);
            }
            this.setInvoicesMetaData(metaDataList);
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
    }

    public String getUserNetID() {
        return userNetID;
    }

    public void setUserNetID(String user) {
        this.userNetID = user;
    }

    public String getImExternalFolderPath() {
        return imExternalFolderPath;
    }

    public void setImExternalFolderPath(String imExternalFolderPath) {
        this.imExternalFolderPath = imExternalFolderPath;
    }

    public String getImDBPath() {
        return imDBPath;
    }

    public void setImDBPath(String imDBPath) {
        this.imDBPath = imDBPath;
    }

    public Integer getRowsPerPage() {
        return rowsPerPage;
    }

    public void setRowsPerPage(int rowsPerPage) {
        this.rowsPerPage = rowsPerPage;
    }

//    public LinkedHashMap<String, Double> getColumnsAndWidthMap() {
//        return columnsAndWidth;
//    }

//    public void setColumnsAndWidth(LinkedHashMap<String, Double> columnsAndWidth) {
//        this.columnsAndWidth = columnsAndWidth;
//    }

//    public void putInColumnsAndWidth(String K, Double V) {
//        this.columnsAndWidth.put(K,V);
//    }
//
//    public Set getSetOfColumns() { return this.columnsAndWidth.keySet();}

//    public int getNrOfColumnsToDisplay() {
//        return nrOfColumnsToDisplay;
//    }
//
//    public void setNrOfColumnsToDisplay(int nrOfColumnsToDisplay) {
//        this.nrOfColumnsToDisplay = nrOfColumnsToDisplay;
//    }

//    public Double getColumnWidth(Object columnID){
//        return this.columnsAndWidth.get(columnID);
//    }

    public void setBackgroundColor(String backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public String getBackgroundColor() {
        return this.backgroundColor;
    }

    public int getTotalNrOfPages() {
        return totalNrOfPages;
    }

    public void setTotalNrOfPages(int totalNrOfPages) {
        this.totalNrOfPages = totalNrOfPages;
    }

    public double getTableWidth() {
        return tableWidth;
    }

    public void setTableWidth(double tableWidth) {
        this.tableWidth = tableWidth;
    }

    public List<String[]> getFilters() {
        return filters;
    }

    public void setFilters(List<String[]> list) {
        this.filters = list;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public boolean isCheckForInvDuplicates() {
        return checkForInvDuplicates;
    }

    public void setCheckForInvDuplicates(boolean checkForInvDuplicates) {
        this.checkForInvDuplicates = checkForInvDuplicates;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserColor() {
        return userColor;
    }

    public void setUserColor(String userColor) {
        this.userColor = userColor;
    }

    public List<String[]> getInvoicesMetaData() {
        return invoicesMetaData;
    }

    public void setInvoicesMetaData(List<String[]> invoicesMetaData) {
        this.invoicesMetaData = invoicesMetaData;
    }

    public boolean save(Request request) throws ClassNotFoundException {
        String imExternalFolderPath = request.queryParams("imExternalFolderPath");
            if (imExternalFolderPath.lastIndexOf("\\") != imExternalFolderPath.length()-1) {
                imExternalFolderPath += "\\";
                System.out.println("Add \\ at the end of the path = " + imExternalFolderPath);
            }

        String imDBPath = request.queryParams("imDBPath");
        Integer rowPerPage = Integer.parseInt(request.queryParams("rowsPerPage"));
        String order = (isNull(request,"order"))? "ASC":"DESC";
        String orderBy = this.getOrderByClause().replace("ORDER BY ","").replace(" DESC","").replace(" ASC","");
            if (request.queryParams("orderBy").equals("")) { // user has not changed anything in this field
                System.out.println("OrderBy has not changed!");
            } else {
                orderBy = request.queryParams("orderBy");
            }
        String orderByClause = "ORDER BY "+orderBy+" "+order;
        Integer InvDuplicates = (isNull(request,"duplicates")) ? 0:1;
        String userNetID = request.queryParams("userID").toUpperCase();
        String userEmail = request.queryParams("userEmail").toLowerCase();
        String userColor = request.queryParams("userColor");

        System.out.println(" Update CFG tab with: ====================================================================");
        System.out.println("imExternalFolderPath: " + imExternalFolderPath);
        System.out.println("            imDBPath: " + imDBPath);
        System.out.println("          rowPerPage: " + rowPerPage);
        System.out.println("       orderByClause: " + orderByClause);
        System.out.println("       InvDuplicates: " + InvDuplicates);
        System.out.println("           userNetID: " + userNetID);
        System.out.println("           userEmail: " + userEmail);
        System.out.println("           userColor: " + userColor);

        Connection connection = null;
        try {
            // create a database connection
            connection = DriverManager.getConnection("jdbc:sqlite:" + this.InvoicesManagerCFGPath);
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);  // set timeout to 30 sec.

            PreparedStatement prepStmt = connection.prepareStatement(
            "UPDATE CFG SET imExternalFolderPath=?,imDBPath=?,rowsPerPage=?,OrderByClause=?,InvDuplicates=?,userNetID=?,userEmail=?,userColor=? WHERE ID=1");
            prepStmt.setString(1, imExternalFolderPath);
            prepStmt.setString(2, imDBPath);
            prepStmt.setInt(   3, rowPerPage);
            prepStmt.setString(4, orderByClause);
            prepStmt.setInt(   5, InvDuplicates);
            prepStmt.setString(6, userNetID);
            prepStmt.setString(7, userEmail);
            prepStmt.setString(8, userColor);

            prepStmt.execute();

            User user = new User(userNetID, userEmail, userColor);
            user.upsertUserToIMDB();

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
        return true;
    }

    @Override
    public String toString() {
        return "InvoiceManagerCFG{" +
                "InvoicesManagerCFGPath='" + InvoicesManagerCFGPath + '\'' + '\n' +
                ", imExternalFolderPath='" + imExternalFolderPath + '\'' + '\n' +
                ", imDBPath='" + imDBPath + '\'' + '\n' +
                ", rowsPerPage=" + rowsPerPage + '\n' +
                ", backgroundColor='" + backgroundColor + '\'' + '\n' +
                ", tableWidth=" + tableWidth + '\n' +
                ", totalNrOfPages=" + totalNrOfPages + '\n' +
                ", orderByClause='" + orderByClause + '\'' + '\n' +
                ", checkForInvDuplicates=" + checkForInvDuplicates + '\n' +
                ", userNetID='" + userNetID + '\'' + '\n' +
                ", userEmail='" + userEmail + '\'' + '\n' +
                ", userColor='" + userColor + '\'' +
                '}';
    }
}

