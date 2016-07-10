package View;

import freemarker.template.TemplateException;

import java.io.IOException;
import java.sql.SQLException;

/**
 * Created by Karol Kistela on 27-Apr-16.
 */
public interface Renderer {
    String render() throws IOException, TemplateException, ClassNotFoundException, SQLException;
}
