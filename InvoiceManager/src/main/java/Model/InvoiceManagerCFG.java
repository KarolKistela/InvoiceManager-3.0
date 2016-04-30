package Model;

import java.sql.*;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * Created by Karol Kistela on 07-Apr-16.
 * Configuration Class for Invoice Manager. Data are serialized in ../../resources/InvoiceManagerCFG/InvoiceManager.cfg database.
 */
public class InvoiceManagerCFG {
    private String imExternalFolderPath;
    private String imDBPath;
    /** how many records will be displayed per page. It applies for getDBview main view and All Filters views. */
    private int rowsPerPage;
    /** Key - name of the column, Value - width of columnn in %. IMPORTANT: sum of widths cannot exceed 90%. Columns with value of 0% will not be displayed */
    private LinkedHashMap<String, Double> columnsAndWidth = new LinkedHashMap<String, Double>();
    /** Calculated, based on columnsAndWidth. */
    private int nrOfColumnsToDisplay;
    private String backgroundColor;
    private double tableWidth;
    private int totalNrOfPages;
    private List<String[]> filters;
    private String orderByClause;
    private boolean checkForInvDuplicates;

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
            connection = DriverManager.getConnection("jdbc:sqlite:" + "src/main/resources/InvoiceManagerCFG/InvoiceManager.cfg");
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);  // set timeout to 30 sec.
            ResultSet resultSet;

            resultSet = statement.executeQuery("SELECT * FROM CFG ORDER BY ID DESC LIMIT 1;");
            this.setImExternalFolderPath(resultSet.getString("imExternalFolderPath"));
            this.setImDBPath(resultSet.getString("imDBPath"));
            this.setRowsPerPage(resultSet.getInt("rowsPerPage"));
            this.setBackgroundColor(resultSet.getString("backgroundColor"));
            this.setTableWidth(resultSet.getDouble("tableWidth"));
            this.setOrderByClause(resultSet.getString("OrderByClause"));
            this.setCheckForInvDuplicates(resultSet.getBoolean("Duplicates"));

            // load (K,V) into columnsAndWidth
            resultSet = statement.executeQuery("SELECT * FROM columnsAndWidth;");
            while(resultSet.next())
            {
                this.putInColumnsAndWidth(resultSet.getString(1), resultSet.getDouble(2));
            }

            resultSet = statement.executeQuery("SELECT count() FROM columnsAndWidth WHERE columnWidth > 0;");
            this.setNrOfColumnsToDisplay(resultSet.getInt(1));

            this.setTotalNrOfPages(0);

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

    public int getRowsPerPage() {
        return rowsPerPage;
    }

    public void setRowsPerPage(int rowsPerPage) {
        this.rowsPerPage = rowsPerPage;
    }

    public LinkedHashMap<String, Double> getColumnsAndWidthMap() {
        return columnsAndWidth;
    }

    public void setColumnsAndWidth(LinkedHashMap<String, Double> columnsAndWidth) {
        this.columnsAndWidth = columnsAndWidth;
    }

    public void putInColumnsAndWidth(String K, Double V) {
        this.columnsAndWidth.put(K,V);
    }

    public Set getSetOfColumns() { return this.columnsAndWidth.keySet();}

    public int getNrOfColumnsToDisplay() {
        return nrOfColumnsToDisplay;
    }

    public void setNrOfColumnsToDisplay(int nrOfColumnsToDisplay) {
        this.nrOfColumnsToDisplay = nrOfColumnsToDisplay;
    }

    public Double getColumnWidth(Object columnID){
        return this.columnsAndWidth.get(columnID);
    }

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
}

