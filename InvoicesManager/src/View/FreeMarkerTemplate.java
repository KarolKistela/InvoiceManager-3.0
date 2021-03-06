package View;

import freemarker.template.Configuration;
import freemarker.template.SimpleHash;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.IOException;
import java.io.StringWriter;

/**
 * Created by Karol Kistela on 27-Apr-16.
 */
abstract public class FreeMarkerTemplate {
    private final Configuration cfg = new Configuration();
    public SimpleHash replaceMap;
    public StringWriter retVal;

    public FreeMarkerTemplate() throws ClassNotFoundException {
        cfg.setClassForTemplateLoading(FreeMarkerTemplate.class, "/");
        this.replaceMap = new SimpleHash();
        this.retVal = new StringWriter();
    }

    public Template getTemplate(String t) throws IOException {
        return this.cfg.getTemplate(t);
    }

    public String process(Template template) throws IOException, TemplateException {
        template.process(replaceMap,retVal);
        String s = retVal.toString();
        this.clearData();

        return s;
    }

    public void clearData() {
        this.replaceMap = new SimpleHash();
        this.retVal = new StringWriter();
    }
}
