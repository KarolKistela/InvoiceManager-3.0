package Model;

import Controller.Controller;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import static Model.Helpers.fileExists;

/**
 * Created by Karol Kistela on 01-Jun-16.
 */
public class CSVFile {
    private final String CSVPATH = "src/main/resources/DBcsv/IM.csv";
    private final Path csvFilePath = Paths.get(CSVPATH);
    private BufferedWriter writer;
    private List<String[]> rs = new LinkedList<>();

    public static void main(String[] args) throws IOException, SQLException, ClassNotFoundException {
        CSVFile s = new CSVFile(Controller.sqlQuery);
    }

    public CSVFile(String sqlQuery) throws IOException, ClassNotFoundException, SQLException {
        this.rs = new InvoiceManagerDB_DAO().sqlSELECT(sqlQuery,1,false,false);

        if (fileExists(csvFilePath.toString())) this.deleteOldFile();

        this.generateCSV();
    }

    private void generateCSV() throws IOException {
        writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(csvFilePath.toString()), "Cp1252"));

        writer.append("sep=" + "" + '\t');
        writer.append('\n');
        writer.append("ID" + '\t');
        writer.append("BC" + '\t');
        writer.append("EntryDate" + '\t');
        writer.append("ContactGenpact" + '\t');
        writer.append("Supplier" + '\t');
        writer.append("InvoiceNR" + '\t');
        writer.append("InvScanPath" + '\t');
        writer.append("PO" + '\t');
        writer.append("NetPrice" + '\t');
        writer.append("Currency" + '\t');
        writer.append("InvDate" + '\t');
        writer.append("EmailSubject" + '\t');
        writer.append("AuthContact" + '\t');
        writer.append("AuthDate" + '\t');
        writer.append("AuthReplyDate" + '\t');
        writer.append("AuthEmail" + '\t');
        writer.append("EndDate" + '\t');
        writer.append("GR" + '\t');
        writer.append("GenpactLastReply" + '\t');
        writer.append("UserComments" + '\t');
        writer.append("Status" + '\t');
        writer.append("User" + '\t');
        writer.append("RowColor" + '\t');
        writer.append("ProcessStatus" + '\t');
        writer.append("ProcessStage" + '\n');

        for (String[] r: this.rs) {
            int i;
            for (i = 0; i<r.length; i++) {
                if (i!=r.length-1) {
                    writer.append(r[i].replace(",",""));
                    writer.append('\t');
                } else {
                    writer.append(r[i].replace(",",""));
                    writer.append('\n');
                }
            }
        }

        writer.flush();
        writer.close();
    }

    public void deleteOldFile() throws IOException {
        try {
            Files.delete(csvFilePath);
        } catch (IOException e) {
            e.printStackTrace();
            throw new IOException(e.getCause());
        }
    }

    public String getPath() {
        return Paths.get(CSVPATH).toAbsolutePath().toString();
    }
}
