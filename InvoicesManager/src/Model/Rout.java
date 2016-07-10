package Model;

import spark.Request;

import java.io.UnsupportedEncodingException;

/**
 * Created by Karol Kistela on 26-Jun-16.
 */
public class Rout {
    public String fullURLrout;     // full rout path: /IFV/ID/>/0/Supplier/like/Morele.net/EntryDate/ASC/1
    public String select;          // /IFV/ID/>/0/Supplier/like/Morele.net/
    public String orderByClause;   // EntryDate/ASC/
    public Integer pageNr;         // 1
    public String orderBy;         // EntryDate
    public String sortDirection;   // ASC

    public Rout(Request request) throws UnsupportedEncodingException {
        this.fullURLrout = this.getPath(request);
        this.pageNr = (Integer.parseInt(request.params("pageNr")) < 1) ? 1:Integer.parseInt(request.params("pageNr"));
        this.select = fullURLrout;
        int i = 0;
        while (i<3){
            select = select.substring(0,select.length()-1);
            if (select.substring(select.length()-1,select.length()).equals("/")) i++;
        }
        this.orderByClause = request.params("orderBy") + "/" + request.params("sort") + "/" ;
        this.orderBy = request.params("orderBy");
        this.sortDirection = request.params("sort");
    }
    public String getPath(Request request){
        String retVal = "/IFV/";

        try {
            String c1 = request.params("column1");
            String s1 = request.params("sign1");
            String v1 = request.params("value1");

            retVal += c1 + "/" + s1 + "/" + v1 + "/";
        } catch (Exception e) {
            return "ID/gte/0/" + request.params("orderBy") + "/" + request.params("sort") + "/" + request.params("pageNr");
        }
        try {
            String c2 = request.params("column2");
            String s2 = request.params("sign2");
            String v2 = request.params("value2");

            if (!c2.equals("null")) {
                retVal += c2 + "/" + s2 + "/" + v2 + "/";
            }
        } catch (Exception e) {
            return retVal + request.params("orderBy") + "/" + request.params("sort") + "/" + request.params("pageNr");
        }
        try {
            String c3 = request.params("column3");
            String s3 = request.params("sign3");
            String v3 = request.params("value3");

            if (!c3.equals("null")) {
                retVal += c3 + "/" + s3 + "/" + v3 + "/";
            }
        } catch (Exception e) {
            return retVal + request.params("orderBy") + "/" + request.params("sort") + "/" + request.params("pageNr");
        }

        retVal += request.params("orderBy") + "/" + request.params("sort") + "/" + request.params("pageNr");

        return retVal;
    }

}
