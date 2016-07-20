package Model;

import java.sql.ResultSet;
import java.sql.SQLException;

//import Model.DAO.Invoices;

/**
 * Created by mzjdx6 on 21-Apr-16.
 */
public class Invoice {
    private int ID;
    private String BC;
    private String EntryDate;
    private String ContactGenpact;
    private String Supplier;
    private String InvoiceNR;
    private String InvScanPath;
    private String PO;
    private Double NetPrice;
    private String Currency;
    private String InvDate;
    private String EmailSubject;
    private String AuthContact;
    private String AuthDate;
    private String AuthReplyDate;
    private String AuthEmail;
    private String EndDate;
    private String GR;
    private String GenpactLastReply;
    private String UserComments;
    private String Status;
    private String User;
    private String RowColor;
    private String ProcessStatus;
    private String FinanceComments;
    private Integer InvNrDuplicates;
    private String UserColor;

    public Invoice(ResultSet rs) throws SQLException {
        this.ID = rs.getInt("ID");
        this.BC = (rs.getString("BC"));
        this.EntryDate = (rs.getString("EntryDate"));
        this.ContactGenpact = (rs.getString("ContactGenpact"));
        this.Supplier = (rs.getString("Supplier"));
        this.InvoiceNR = (rs.getString("InvoiceNR"));
        this.InvScanPath = (rs.getString("InvScanPath"));
        this.PO = (rs.getString("PO"));
        this.NetPrice = (rs.getDouble("NetPrice"));
        this.Currency = (rs.getString("Currency"));
        this.InvDate = (rs.getString("InvDate"));
        this.EmailSubject = (rs.getString("EmailSubject"));
        this.AuthContact = (rs.getString("AuthContact"));
        this.AuthDate = (rs.getString("AuthDate"));
        this.AuthReplyDate = (rs.getString("AuthReplyDate"));
        this.AuthEmail = (rs.getString("AuthEmail"));
        this.EndDate = (rs.getString("EndDate"));
        this.GR = (rs.getString("GR"));
        this.GenpactLastReply = (rs.getString("GenpactLastReply"));
        this.UserComments = (rs.getString("UserComments"));
        this.Status = (rs.getString("Status"));
        this.User = (rs.getString("User"));
        this.RowColor = (rs.getString("RowColor"));
        this.ProcessStatus = (rs.getString("ProcessStatus"));
        this.FinanceComments = rs.getString("FinanceComments");
        this.InvNrDuplicates = rs.getInt("InvNrDuplicates");
        this.UserColor = rs.getString("UserColor");
    }

    public Invoice() {

    }

//    public Boolean save(Request request) throws FileNotFoundException, ClassNotFoundException, SQLException {
//        if (config.FINANCE_VIEW) {
//            db.upsertInvoiceFinance(request);
//        } else {
//            db.upsertInvoice(request);
//        }
//        // relode the combolist on save action - it works to slow
//        Controller.ImCFG.loadData();
//        //Controller.comboList.reload();
//        return true;
//    }
//
//    public Invoice(Integer pageNr) throws FileNotFoundException, ClassNotFoundException, SQLException {
//        String[] invRow = new Invoices().sqlSELECTid2(pageNr);
//
//        this.ID = Integer.parseInt(invRow[0]);
//        this.BC = invRow[1];
//        this.EntryDate = invRow[2];
//        this.ContactGenpact = invRow[3];
//        this.Supplier = invRow[4];
//        this.InvoiceNR = invRow[5];
//        this.InvScanPath =  invRow[6];
//        this.PO = invRow[7];
//        this.NetPrice = Double.parseDouble(invRow[8]);
//        this.Currency = invRow[9];
//        this.InvDate = invRow[10];
//        this.EmailSubject = invRow[11];
//        this.AuthContact = invRow[12];
//        this.AuthDate = invRow[13];
//        this.AuthReplyDate = invRow[14];
//        this.AuthEmail = invRow[15];
//        this.EndDate = invRow[16];
//        this.GR = invRow[17];
//        this.GenpactLastReply = invRow[18];
//        this.UserComments = invRow[19];
//        this.Status = invRow[20];
//        this.User = invRow[21];
//        this.RowColor = invRow[22];
//        this.ProcessStatus = invRow[23];
////        this.ProcessStage = Integer.parseInt(invRow[24]);
//        this.FinanceComments = invRow[24];
//        this.InvNrDuplicates = Integer.parseInt(invRow[25]);
//        this.UserColor = invRow[26];
//    }

    @Override
    public String toString() {
        return "Invoice{" +
                "ID=" + ID +
                ", BC='" + BC + '\'' +
                ", EntryDate='" + EntryDate + '\'' +
                ", ContactGenpact='" + ContactGenpact + '\'' +
                ", Supplier='" + Supplier + '\'' +
                ", InvoiceNR='" + InvoiceNR + '\'' +
                ", InvScanPath='" + InvScanPath + '\'' +
                ", PO='" + PO + '\'' +
                ", NetPrice=" + NetPrice +
                ", Currency='" + Currency + '\'' +
                ", InvDate='" + InvDate + '\'' +
                ", EmailSubject='" + EmailSubject + '\'' +
                ", AuthContact='" + AuthContact + '\'' +
                ", AuthDate='" + AuthDate + '\'' +
                ", AuthReplyDate='" + AuthReplyDate + '\'' +
                ", AuthEmail='" + AuthEmail + '\'' +
                ", EndDate='" + EndDate + '\'' +
                ", GR='" + GR + '\'' +
                ", GenpactLastReply='" + GenpactLastReply + '\'' +
                ", UserComments='" + UserComments + '\'' +
                ", Status=" + Status +
                ", User='" + User + '\'' +
                ", RowColor='" + RowColor + '\'' +
                ", ProcessStatus='" + ProcessStatus + '\'' +
                ", FinanceComents=" + FinanceComments +
                ", InvNrDuplicates=" + InvNrDuplicates +
                ", UserColor=" + UserColor +
                '}';
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getBC() {
        return BC;
    }

    public void setBC(String BC) {
        this.BC = BC;
    }

    public String getEntryDate() {
        return EntryDate;
    }

    public void setEntryDate(String entryDate) {
        EntryDate = entryDate;
    }

    public String getContactGenpact() {
        return ContactGenpact;
    }

    public void setContactGenpact(String contactGenpact) {
        ContactGenpact = contactGenpact;
    }

    public String getSupplier() {
        return Supplier;
    }

    public void setSupplier(String supplier) {
        Supplier = supplier;
    }

    public String getInvoiceNR() {
        return InvoiceNR;
    }

    public void setInvoiceNR(String invoiceNR) {
        InvoiceNR = invoiceNR;
    }

    public String getInvScanPath() {
        return InvScanPath;
    }

    public void setInvScanPath(String invScanPath) {
        InvScanPath = invScanPath;
    }

    public String getPO() {
        return PO;
    }

    public void setPO(String PO) {
        this.PO = PO;
    }

    public Double getNetPrice() {
        return NetPrice;
    }

    public void setNetPrice(Double netPrice) {
        NetPrice = netPrice;
    }

    public String getCurrency() {
        return Currency;
    }

    public void setCurrency(String currency) {
        Currency = currency;
    }

    public String getInvDate() {
        return InvDate;
    }

    public void setInvDate(String invDate) {
        InvDate = invDate;
    }

    public String getEmailSubject() {
        return EmailSubject;
    }

    public void setEmailSubject(String emailSubject) {
        EmailSubject = emailSubject;
    }

    public String getAuthContact() {
        return AuthContact;
    }

    public void setAuthContact(String authContact) {
        AuthContact = authContact;
    }

    public String getAuthDate() {
        return AuthDate;
    }

    public void setAuthDate(String authDate) {
        AuthDate = authDate;
    }

    public String getAuthReplyDate() {
        return AuthReplyDate;
    }

    public void setAuthReplyDate(String authReplyDate) {
        AuthReplyDate = authReplyDate;
    }

    public String getAuthEmail() {
        return AuthEmail;
    }

    public void setAuthEmail(String authEmail) {
        AuthEmail = authEmail;
    }

    public String getEndDate() {
        return EndDate;
    }

    public void setEndDate(String endDate) {
        EndDate = endDate;
    }

    public String getGR() {
        return GR;
    }

    public void setGR(String GR) {
        this.GR = GR;
    }

    public String getGenpactLastReply() {
        return GenpactLastReply;
    }

    public void setGenpactLastReply(String genpactLastReply) {
        GenpactLastReply = genpactLastReply;
    }

    public String getUserComments() {
        return UserComments;
    }

    public void setUserComments(String userComments) {
        UserComments = userComments;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getUser() {
        return User;
    }

    public void setUser(String user) {
        User = user;
    }

    public String getRowColor() {
        return RowColor;
    }

    public void setRowColor(String rowColor) {
        RowColor = rowColor;
    }

    public String getProcessStatus() {
        return ProcessStatus;
    }

    public void setProcessStatus(String processStatus) {
        ProcessStatus = processStatus;
    }

    public String getFinanceComments() {
        return FinanceComments;
    }

    public void setFinanceComments(String financeComments) {
        FinanceComments = financeComments;
    }

    public Integer getInvNrDuplicates() {
        return InvNrDuplicates;
    }

    public void setInvNrDuplicates(Integer invNrDuplicates) {
        InvNrDuplicates = invNrDuplicates;
    }

    public String getUserColor() {
        if (UserColor == null) {
            return "";
        } else {
            return UserColor;
        }
    }

    public void setUserColor(String userColor) {
        UserColor = userColor;
    }
}
