package View.Renderers;

import View.FreeMarkerTemplate;
import View.Renderer;
import freemarker.template.TemplateException;

import java.io.IOException;
import java.sql.SQLException;

import static Controller.Controller.FINANCE_VIEW;

/**
 * Created by Karol Kistela on 27-Jun-16.
 */
public class Footer extends FreeMarkerTemplate implements Renderer{
    public Footer() throws ClassNotFoundException {
        super();
    }

    @Override
    public String render() throws IOException {
        if (FINANCE_VIEW) {
            return getTemplate("FTL templates/Footer/Footer.ftl").toString().replace("FINANCE_VIEW", "viewer");
        } else {
            return getTemplate("FTL templates/Footer/Footer.ftl").toString().replace("FINANCE_VIEW", "");
        }

    }
}
