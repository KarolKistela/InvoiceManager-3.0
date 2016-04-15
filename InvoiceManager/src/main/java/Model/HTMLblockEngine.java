package Model;

import freemarker.template.Configuration;
import freemarker.template.SimpleHash;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import spark.Request;

import java.io.IOException;
import java.io.StringWriter;

/**
 * Created by Karol Kistela on 14-Apr-16.
 */
public class HTMLblockEngine {
    private final Configuration cfg = new Configuration();
    private Template template;
//    private StringWriter sw = new StringWriter();

    public HTMLblockEngine() {
        cfg.setClassForTemplateLoading(HTMLblockEngine.class,"/HTML templates/");
    }

    public StringWriter test(String name, String surname, Request request) throws IOException, TemplateException {
        SimpleHash root = new SimpleHash();
        StringWriter sw = new StringWriter();
        template = cfg.getTemplate("t.ftl");
        root.put("name", name);
        root.put("surname", surname);
        root.put("nr",request.params(":nr"));

        template.process(root, sw);

        return sw;
    }

}
