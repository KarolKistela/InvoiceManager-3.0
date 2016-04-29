package View;

/**
 * Created by Karol Kistela on 27-Apr-16.
 */
public class HtmlFactory {

    public Renderer getDataBaseView(int pageNR){
        return new DBview(1, "DB view", pageNR, true, "SELECT * FROM Invoices ORDER BY ID DESC");
    }
}
