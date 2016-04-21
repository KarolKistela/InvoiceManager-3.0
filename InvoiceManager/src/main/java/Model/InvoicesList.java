package Model;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by mzjdx6 on 21-Apr-16.
 */
public class InvoicesList {
    private List<Invoice> tab;

    public InvoicesList() {
        tab = new LinkedList<Invoice>();
    }

    public InvoicesList(List<Invoice> tab) {
        this.tab = tab;
    }

    public List<Invoice> getTab() {
        return tab;
    }

    public void setTab(List<Invoice> tab) {
        this.tab = tab;
    }

    public void addInvoice(Invoice inv) {
        this.tab.add(inv);
    }

    @Override
    public String toString() {
        for (Invoice inv: this.tab) {
            System.out.println("======INVOICE=====");
            System.out.println("ID for query is: " + inv.getID());
            System.out.println("BC for query is: " + inv.getBC());
            System.out.println("EntryDate for query is: " + inv.getEntryDate());
            System.out.println("ContactGenpact for query is: " + inv.getContactGenpact());
            System.out.println("Supplier for query is: " + inv.getSupplier());
            System.out.println("InvoiceNR for query is: " + inv.getInvoiceNR());
            System.out.println("InvScanPath for query is: " + inv.getInvScanPath());
            System.out.println("PO for query is: " + inv.getPO());
            System.out.println("NetPrice for query is: " + inv.getNetPrice());
            System.out.println("Currency for query is: " + inv.getCurrency());
            System.out.println("InvDate for query is: " + inv.getInvDate());
            System.out.println("EmailSubject for query is: " + inv.getEmailSubject());
            System.out.println("AuthContact for query is: " + inv.getAuthContact());
            System.out.println("AuthDate for query is: " + inv.getAuthDate());
            System.out.println("AuthReplyDate for query is: " + inv.getAuthReplyDate());
            System.out.println("AuthEmail for query is: " + inv.getAuthEmail());
            System.out.println("EndDate for query is: " + inv.getEndDate());
            System.out.println("GR for query is: " + inv.getGR());
            System.out.println("GenpactLastReply for query is: " + inv.getGenpactLastReply());
            System.out.println("UserComments for query is: " + inv.getUserComments());
            System.out.println("Status for query is: " + inv.getStatus());
            System.out.println("User for query is: " + inv.getUser());
            System.out.println("RowColor for query is: " + inv.getRowColor());
            System.out.println("ProcessStatus for query is: " + inv.getProcessStatus());
        }
        return null;
    }
}
