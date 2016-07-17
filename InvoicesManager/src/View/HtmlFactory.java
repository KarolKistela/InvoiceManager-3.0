package View;

import View.Renderers.ErrorView;
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

    public static Renderer getErrorView() throws ClassNotFoundException, UnsupportedEncodingException {
        return new ErrorView();
    }
}