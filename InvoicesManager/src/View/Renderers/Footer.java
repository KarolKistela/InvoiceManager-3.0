package View.Renderers;

import View.FreeMarkerTemplate;
import View.Renderer;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.IOException;

import static Controller.Controller.FINANCE_VIEW;

/**
 * Created by Karol Kistela on 27-Jun-16.
 */
public class Footer extends FreeMarkerTemplate implements Renderer{
    public Footer() throws ClassNotFoundException {
        super();
    }

    @Override
    public String render() throws IOException, TemplateException {
        Template template = getTemplate("FTL templates/Footer/Footer.ftl");

        if (FINANCE_VIEW) {
            replaceMap.put("FINANCE_VIEW","Reader");
        } else {
            replaceMap.put("FINANCE_VIEW","");
        }

        return process(template);
    }
}
