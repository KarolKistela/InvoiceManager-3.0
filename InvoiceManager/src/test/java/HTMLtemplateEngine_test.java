/**
 * Created by mzjdx6 on 14-Apr-16.
 */
public class HTMLtemplateEngine_test {
    public static void main(String[] args) {
        Model.HTMLtemplateEngine t = null;
        String s = "";
        try {
            t = new Model.HTMLtemplateEngine();
            System.out.println(t.getStyle());
//            System.out.println(t.getHeader2rowed("viewName",2,15,150));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
