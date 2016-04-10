package InvoiceManagerCFG;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

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
    private Map<String,Double> columnsAndWidth = new HashMap<String,Double>();
    /** Calculated, based on columnsAndWidth. */
    private int nrOfColumnsToDisplay;

    public InvoiceManagerCFG() throws ClassNotFoundException {
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

            // load (K,V) into columnsAndWidth
            resultSet = statement.executeQuery("SELECT * FROM columnsAndWidth;");
            while(resultSet.next())
            {
                this.putInColumnsAndWidth(resultSet.getString(1), resultSet.getDouble(2));
            }

            resultSet = statement.executeQuery("SELECT count() FROM columnsAndWidth WHERE columnWidth > 0;");
            this.setNrOfColumnsToDisplay(resultSet.getInt(1));


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

    public void putInColumnsAndWidth(String K, Double V) {
        this.columnsAndWidth.put(K,V);
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

    public Map<String, Double> getColumnsAndWidthMap() {
        return columnsAndWidth;
    }

    public void setColumnsAndWidth(Map<String, Double> columnsAndWidth) {
        this.columnsAndWidth = columnsAndWidth;
    }

    public int getNrOfColumnsToDisplay() {
        return nrOfColumnsToDisplay;
    }

    public void setNrOfColumnsToDisplay(int nrOfColumnsToDisplay) {
        this.nrOfColumnsToDisplay = nrOfColumnsToDisplay;
    }

    public Double getColumnWidth(String columnID){
        return this.columnsAndWidth.get(columnID);
    }
}

