import java.util.LinkedList;
import java.util.List;

/**
 * Created by Karol Kistela on 05-May-16.
 */
public class nullTestFFS {
    public static void main(String[] args) {
        List<String> o = new LinkedList<>();
        o = null;



        if (o == null) {
            System.out.println("object is null");
        } else {
            System.out.println(o.toString());
        }
    }
}
