package Model;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by Karol Kistela on 28-Apr-16.
 */
public interface IMsqlite {
    List<String[]> sqlSELECT(String query, int pageNr, boolean withLimitClause) throws ClassNotFoundException, SQLException;
}
