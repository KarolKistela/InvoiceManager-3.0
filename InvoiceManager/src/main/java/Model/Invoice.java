package Model;

import java.sql.*;

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
    private int Status;
    private String User;
    private String RowColor;
    private String ProcessStatus;
    private int ProcessStage;

    public Invoice(ResultSet rs) throws SQLException {
        setID(rs.getInt("ID"));
        setBC(rs.getString("BC"));
        setEntryDate(rs.getString("EntryDate"));
        setContactGenpact(rs.getString("ContactGenpact"));
        setSupplier(rs.getString("Supplier"));
        setInvoiceNR(rs.getString("InvoiceNR"));
        setInvScanPath(rs.getString("InvScanPath"));
        setPO(rs.getString("PO"));
        setNetPrice(rs.getDouble("NetPrice"));
        setCurrency(rs.getString("Currency"));
        setInvDate(rs.getString("InvDate"));
        setEmailSubject(rs.getString("EmailSubject"));
        setAuthContact(rs.getString("AuthContact"));
        setAuthDate(rs.getString("AuthDate"));
        setAuthReplyDate(rs.getString("AuthReplyDate"));
        setAuthEmail(rs.getString("AuthEmail"));
        setEndDate(rs.getString("EndDate"));
        setGR(rs.getString("GR"));
        setGR(rs.getString("GR"));
        setGenpactLastReply(rs.getString("GenpactLastReply"));
        setUserComments(rs.getString("UserComments"));
        setStatus(rs.getInt("Status"));
        setUser(rs.getString("User"));
        setRowColor(rs.getString("RowColor"));
        setProcessStatus(rs.getString("ProcessStatus"));
        setProcessStage(rs.getInt("ProcessStage"));
    }

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
                ", ProcessStage=" + ProcessStage +
                '}';
    }

    public int getProcessStage() {
        return ProcessStage;
    }

    public void setProcessStage(int processStage) {
        ProcessStage = processStage;
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

    public int getStatus() {
        return Status;
    }

    public void setStatus(int status) {
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
}
