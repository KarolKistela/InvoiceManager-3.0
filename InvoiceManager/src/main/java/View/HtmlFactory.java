package View;

import View.Renderers.*;
import spark.Request;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;

/**
 * Created by Karol Kistela on 27-Apr-16.
 */
public class HtmlFactory {

    public static Renderer getSettingsView(Request request) throws ClassNotFoundException {
        return new SettingsView(request);
    }

    public static Renderer getInvoicesView(Request request) throws ClassNotFoundException, UnsupportedEncodingException, FileNotFoundException, SQLException {
        return new InvoicesView(request);
    }

    public static Renderer getSaveView(Request request) throws ClassNotFoundException, UnsupportedEncodingException {
        return new SaveView();
    }


}

//    public static Renderer getIDView(Request request) throws ClassNotFoundException {
//        return new IDView(request);
//    }
//
//    public static Renderer getQueryView(Request request) throws ClassNotFoundException {
//        return new QueryView(request);
//    }
//
//    public static Renderer getQueryView(Request request, String columnName3, String value3) throws ClassNotFoundException {
//        return new QueryView(request, columnName3, value3);
//    }
//
//    public static Renderer getQueryView(Request request, Integer filterNR) throws ClassNotFoundException {
//        return new QueryView(request, filterNR);
//    }
//
//    public static Renderer getQueryView(Request request, boolean advSearch) throws ClassNotFoundException {
//        return new QueryView(request, advSearch);
//    }

//    public Renderer getDataBaseView(Request request) throws ClassNotFoundException {
//        return new DBview(request);
//    }

//    public Renderer getSelectWhereView(Request request, String rout) throws ClassNotFoundException {
//        return new SelectWhereView(request);
//    }

//    public Renderer getInvNrView(Request request, String rout) throws SQLException, ClassNotFoundException, FileNotFoundException {
//        return new InvNrView(request);
//    }

//    public static Renderer getInvoiceView(Request request) throws ClassNotFoundException, FileNotFoundException, SQLException, UnsupportedEncodingException {
//        return new InvoicesView(request, Integer.parseInt(request.params("ID")));
//    }
