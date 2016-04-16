package Model;

import java.sql.*;
import java.util.LinkedHashMap;
import java.util.Set;

/**
 * Created by Karol Kistela on 07-Apr-16.
 * Configuration Class for Invoice Manager. Data are serialized in ../../resources/InvoiceManagerCFG/InvoiceManager.cfg database.
 */
public class InvoiceManagerCFG {
    private String imExternalFolderPath;
    private String imDBPath;
    /** how many records will be displayed per page. It applies for DB main view and All Filters views. */
    private int rowsPerPage;
    /** Key - name of the column, Value - width of columnn in %. IMPORTANT: sum of widths cannot exceed 90%. Columns with value of 0% will not be displayed */
    private LinkedHashMap<String, Double> columnsAndWidth = new LinkedHashMap<String, Double>();
    /** Calculated, based on columnsAndWidth. */
    private int nrOfColumnsToDisplay;
    private String backgroundColor;
    private double tableWidth;
    private int totalNrOfPages;

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

            // load (K,V) into columnsAndWidth
            resultSet = statement.executeQuery("SELECT * FROM columnsAndWidth;");
            while(resultSet.next())
            {
                this.putInColumnsAndWidth(resultSet.getString(1), resultSet.getDouble(2));
            }

            resultSet = statement.executeQuery("SELECT count() FROM columnsAndWidth WHERE columnWidth > 0;");
            this.setNrOfColumnsToDisplay(resultSet.getInt(1));

            this.setTotalNrOfPages(0);
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
}

