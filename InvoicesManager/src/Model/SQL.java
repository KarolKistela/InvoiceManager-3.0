package Model;

import spark.Request;

import java.net.URLDecoder;

import static Controller.Controller.config;

/**
 * Created by Karol Kistela on 26-Jun-16.
 */
public class SQL {
    // select; SELECT * FROM InvoicesFullView WHERE ID != '50000' AND Supplier like '%orele%' AND NetPrice > '100'
    // queryCountID; SELECT count(ID) FROM InvoicesFullView WHERE ID != '50000' AND Supplier like '%orele%' AND NetPrice > '100'
    // orderBy; ORDER BY BC ASC
    // limit; LIMIT 0, 250;
    // query; full SQL query with where clauses, order by and limit
    public String select;
    public String queryCountID;
    public String orderBy;
    public String limit;
    public String query;

    public SQL(Request request) {
        this.select = getQuery(request);
        this.queryCountID = select.replace("*","count(ID)");
        this.limit = getLimitClause(request);
        this.orderBy = getOrderBy(request);
        this.query = this.select + this.orderBy + this.limit;
    }

    public String getQuery(Request request) {
        String retVal = "SELECT * FROM InvoicesFullView WHERE ";

        try {
            String c1 = URLDecoder.decode(request.params("column1"), "UTF-8");
            String s1 = URLDecoder.decode(signDecode(request.params("sign1")), "UTF-8");
            String v1 = URLDecoder.decode(request.params("value1"), "UTF-8");

            v1 = v1.equals("null") ? "" : v1;

            retVal += c1 + " " + s1 + " " + "'" + v1 + "' ";
        } catch (Exception e) {
            return "SELECT * FROM InvoicesFullView ";
        }
        try {
            String c2 = URLDecoder.decode(request.params("column2"), "UTF-8");
            String s2 = URLDecoder.decode(signDecode(request.params("sign2")), "UTF-8");
            String v2 = URLDecoder.decode(request.params("value2"), "UTF-8");

            v2 = v2.equals("null") ? "" : v2;

            retVal += "AND " + c2 + " " + s2 + " " + "'" + v2 + "' ";
        } catch (Exception e) {
            return retVal;
        }
        try {
            String c3 = URLDecoder.decode(request.params("column3"), "UTF-8");
            String s3 = URLDecoder.decode(signDecode(request.params("sign3")), "UTF-8");
            String v3 = URLDecoder.decode(request.params("value3"), "UTF-8");

            v3 = v3.equals("null") ? "" : v3;

            retVal += "AND " + c3 + " " + s3 + " " + "'" + v3 + "' ";
        } catch (Exception e) {
            return retVal;
        }
        return retVal;
    }

    public String getOrderBy(Request request) {
        return "ORDER BY " + request.params("orderBy") + " " + request.params("sort") + " ";
    }
    public String getLimitClause(Request request) {
        Integer pageNr = Integer.parseInt(request.params("pageNr"));
        if (pageNr < 1) pageNr = 1; // 1< would cause a SQL error

        return "LIMIT " + ((pageNr - 1) * config.RECORDS_PER_PAGE) + ", " + config.RECORDS_PER_PAGE + ";";
    }
    private String signDecode(String URLsign) {
        String sign;
        switch (URLsign){
            case "=":  sign = "=";     break;
            case "!=": sign = "!=";    break;
            case ">":  sign = ">";     break;
            case ">=": sign = ">=";    break;
            case "<":  sign = "<";     break;
            case "<=": sign = "<=";    break;
            case "eq":  sign = "=";     break;
            case "neq": sign = "!=";    break;
            case "gt":  sign = ">";     break;
            case "gte": sign = ">=";    break;
            case "lt":  sign = "<";     break;
            case "lte": sign = "<=";    break;
            case "LIKE": sign = " LIKE "; break;
            case "NOT%20LIKE": sign = " NOT LIKE "; break;
            case "like": sign = " LIKE "; break;
            case "not%20like": sign = " NOT LIKE "; break;
            default: sign = "="; break;
        }
        return sign;
    }

}