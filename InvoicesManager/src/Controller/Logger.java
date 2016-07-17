package Controller;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Karol Kistela on 16-Jul-16.
 */
public class Logger {
    private List<String> log;
    private DateFormat dateFormat;

    public Logger(){
        log = new LinkedList<>();
        dateFormat = new SimpleDateFormat("HH:mm:ss");
    }

    public void add(String s){
        this.log.add(dateFormat.format(Calendar.getInstance().getTime())+ ": " + s);
    }
    public List<String> getExceptionList() {
        return this.log;
    }

    /**
     * "!" - when log will be rendered to HTML by ErrorView class this sign will determine which lines print in red
     * @param e
     */
    public void addException(Exception e) {
        StringWriter errors = new StringWriter();
        String newline = System.getProperty("line.separator");
        e.printStackTrace(new PrintWriter(errors));

        List<String> retVal = new LinkedList<>();

        for (String s: errors.toString().split(newline)) {
            retVal.add(s);
        }
        Collections.reverse(retVal);

        for (String s: retVal) {
            this.add("!" + s);
        }
    }
}