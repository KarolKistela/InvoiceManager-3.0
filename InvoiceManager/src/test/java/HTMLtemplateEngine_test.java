import Model.TemplateEngine;

/**
 * Created by mzjdx6 on 14-Apr-16.
 */
public class HTMLtemplateEngine_test {
    public static void main(String[] args) {
        TemplateEngine t = null;
        String s = "";
        try {
            t = new TemplateEngine();
            System.out.println(t.getStyle());
//            System.out.println(t.getHeader2rowed("viewName",2,15,150));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
