import View.HtmlFactory;
import View.Renderer;

/**
 * Created by Karol Kistela on 27-Apr-16.
 */
public class FactoryTest {
    public static void main(String[] args) {
        HtmlFactory htmlFactory = new HtmlFactory();

        Renderer dbView = htmlFactory.getDataBaseView(2);
        try {
            System.out.println(dbView.render());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



}
