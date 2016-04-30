package View;

import spark.Request;

import java.sql.SQLException;

/**
 * Created by Karol Kistela on 27-Apr-16.
 */
public class HtmlFactory {

    public Renderer getDataBaseView(Request request, String rout){
        return new DBview(request, rout);
    }

    public Renderer getSelectWhereView(Request request, String rout){
        return new SelectWhereView(request, rout);
    }

    public Renderer getInvNrView(Request request, String rout) throws SQLException, ClassNotFoundException {
        return new InvNrView(request, rout);
    }
}
