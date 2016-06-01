package View;

import View.Renderers.*;
import spark.Request;

import java.io.FileNotFoundException;
import java.sql.SQLException;

/**
 * Created by Karol Kistela on 27-Apr-16.
 */
public class HtmlFactory {

    public static Renderer getSettingsView(Request request) throws ClassNotFoundException {
        return new SettingsView(request);
    }

    public static Renderer getIDView(Request request) throws ClassNotFoundException {
        return new IDView(request);
    }

    public static Renderer getQueryView(Request request) throws ClassNotFoundException {
        return new QueryView(request);
    }

    public static Renderer getQueryView(Request request, Integer filterNR) throws ClassNotFoundException {
        return new QueryView(request, filterNR);
    }

    public static Renderer getQueryView(Request request, boolean advSearch) throws ClassNotFoundException {
        return new QueryView(request, advSearch);
    }

    public static Renderer getSaveView(Request request) throws ClassNotFoundException {
        return new SaveView(request);
    }
}

//    public Renderer getDataBaseView(Request request) throws ClassNotFoundException {
//        return new DBview(request);
//    }

//    public Renderer getSelectWhereView(Request request, String rout) throws ClassNotFoundException {
//        return new SelectWhereView(request);
//    }

//    public Renderer getInvNrView(Request request, String rout) throws SQLException, ClassNotFoundException, FileNotFoundException {
//        return new InvNrView(request);
//    }
