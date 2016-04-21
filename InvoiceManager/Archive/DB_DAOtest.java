/**
 * Created by mzjdx6 on 21-Apr-16.
 */
public class DB_DAOtest {
    public static void main(String[] args) {
        if (args.length == 0) {
            try {
                new DB_DAOtest("C:\\InvoiceManagerExt\\Invoices_manager_DB\\InvoiceManager.db");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        else {
            try {
                new DB_DAOtest(args[0]);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    public DB_DAOtest(String dbPath) throws ClassNotFoundException {
        DB_DAO db = new DB_DAO(dbPath);
        List<ResultSet> queryResultSet = db.sqlSelect("SELECT * FROM Invoices ORDER BY ID DESC LIMIT 2000, 2");

        for (ResultSet rs: queryResultSet) {
            try {
                rs.getInt(1);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }
}
