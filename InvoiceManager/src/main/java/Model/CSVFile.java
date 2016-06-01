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

//        writer.append("ID,");
//        writer.append("BC,");
//        writer.append("EntryDate,");
//        writer.append("ContactGenpact,");
//        writer.append("Supplier,");
//        writer.append("InvoiceNR,");
//        writer.append("InvScanPath,");
//        writer.append("PO,");
//        writer.append("NetPrice,");
//        writer.append("Currency,");
//        writer.append("InvDate,");
//        writer.append("EmailSubject,");
//        writer.append("AuthContact,");
//        writer.append("AuthDate,");
//        writer.append("AuthReplyDate,");
//        writer.append("AuthEmail,");
//        writer.append("EndDate,");
//        writer.append("GR,");
//        writer.append("GenpactLastReply,");
//        writer.append("UserComments,");
//        writer.append("Status,");
//        writer.append("User,");
//        writer.append("RowColor,");
//        writer.append("ProcessStatus,");
//        writer.append("ProcessStage,");
//        writer.append("\n");


        for (String[] r: this.rs) {
            int i;
            for (i = 0; i<r.length; i++) {
                if (i!=r.length-1) {
                    writer.append(r[i].replace(",",""));
                    writer.append(',');
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
