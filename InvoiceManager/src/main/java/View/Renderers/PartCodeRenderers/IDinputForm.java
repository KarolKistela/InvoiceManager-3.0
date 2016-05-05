package View.Renderers.PartCodeRenderers;

import Model.Invoice;
import Model.InvoiceManagerDB_DAO;
import View.FreeMarkerTemplate;
import View.Renderer;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.IOException;
import java.sql.SQLException;

/**
 * Created by Karol Kistela on 03-May-16.
 */
public class IDinputForm extends FreeMarkerTemplate implements Renderer {
    private final String settingsInputFormFTL = "Parts/IDview/IDinputForm.ftl";
    private final Invoice invoice;
    private Integer ID;

    public static void main(String[] args) throws ClassNotFoundException, TemplateException, IOException, SQLException {
        IDinputForm inv = new IDinputForm(new InvoiceManagerDB_DAO().sqlSELECTid(51893));

        System.out.println(inv.render());
    }

    public IDinputForm(Invoice invoice) throws ClassNotFoundException, SQLException {
        super();
        this.ID = invoice.getID();
        this.invoice = invoice;
    }

    @Override
    public String render() throws IOException, TemplateException, ClassNotFoundException, SQLException {
        Template template = getTemplate(this.settingsInputFormFTL);

        this.invoice.toString();
        // TODO: kick out netPrice decimal!!! depriciated
        // TODO: color picker list for RowColor
        // TODO: Status, ProcessStatuss list pisker
        // TODO: Currency list piscker
        // TODO: take out polish letters from DB
        // TODO: paths to file cannot contains signs: /\:*?<>"| make sure this will not get into DB!!!
        // TODO: option in settings for lists for Supplier, AuthContact, Users
        // TODO: /Settings search not working!!!
        replaceMap.put("BC", this.invoice.getBC());
        if (this.invoice.getBC().equals("")) { replaceMap.put("BC_activeClass","");} else {replaceMap.put("BC_activeClass","active");}
        replaceMap.put("EntryDate", this.invoice.getEntryDate());
        if (this.invoice.getEntryDate().equals("")) { replaceMap.put("EntryDate_activeClass","");} else {replaceMap.put("EntryDate_activeClass","active");}
        replaceMap.put("ContactGenpact", this.invoice.getContactGenpact());
        if (this.invoice.getContactGenpact().equals("")) { replaceMap.put("ContactGenpact_activeClass","");} else {replaceMap.put("ContactGenpact_activeClass","active");}
        replaceMap.put("Supplier", this.invoice.getSupplier());
        if (this.invoice.getSupplier().equals("")) { replaceMap.put("Supplier_activeClass","");} else {replaceMap.put("Supplier_activeClass","active");}
        replaceMap.put("InvoiceNR", this.invoice.getInvoiceNR());
        if (this.invoice.getInvoiceNR().equals("")) { replaceMap.put("InvoiceNR_activeClass","");} else {replaceMap.put("InvoiceNR_activeClass","active");}
        replaceMap.put("InvScanPath", this.invoice.getInvScanPath());
        if (this.invoice.getInvScanPath().equals("")) { replaceMap.put("InvScanPath_activeClass","");} else {replaceMap.put("InvScanPath_activeClass","active");}
        replaceMap.put("PO", this.invoice.getPO());
        if (this.invoice.getPO().equals("")) { replaceMap.put("PO_activeClass","");} else {replaceMap.put("PO_activeClass","active");}
        replaceMap.put("NetPrice", this.invoice.getNetPrice().toString().replace(",",""));
        if (this.invoice.getNetPrice() == 0.00) { replaceMap.put("NetPrice_activeClass","");} else {replaceMap.put("NetPrice_activeClass","active");}
        replaceMap.put("NetPriceDecimal",this.invoice.getNetPriceDecimal());
        if (this.invoice.getBC().equals("")) { replaceMap.put("NetPriceDecimal_activeClass","");} else {replaceMap.put("NetPriceDecimal_activeClass","active");}
        replaceMap.put("Currency", this.invoice.getCurrency());
        if (this.invoice.getCurrency().equals("")) { replaceMap.put("Currency_activeClass","");} else {replaceMap.put("Currency_activeClass","active");}
        replaceMap.put("InvDate", this.invoice.getInvDate());
        if (this.invoice.getInvDate().equals("")) { replaceMap.put("InvDate_activeClass","");} else {replaceMap.put("InvDate_activeClass","active");}
        replaceMap.put("EmailSubject", this.invoice.getEmailSubject());
        if (this.invoice.getEmailSubject().equals("")) { replaceMap.put("EmailSubject_activeClass","");} else {replaceMap.put("EmailSubject_activeClass","active");}
        replaceMap.put("AuthContact", this.invoice.getAuthContact());
        if (this.invoice.getAuthContact().equals("")) { replaceMap.put("AuthContact_activeClass","");} else {replaceMap.put("AuthContact_activeClass","active");}
        replaceMap.put("AuthDate", this.invoice.getAuthDate());
        if (this.invoice.getAuthDate().equals("")) { replaceMap.put("AuthDate_activeClass","");} else {replaceMap.put("AuthDate_activeClass","active");}
        replaceMap.put("AuthReplyDate", this.invoice.getAuthReplyDate());
        if (this.invoice.getAuthReplyDate().equals("")) { replaceMap.put("AuthReplyDate_activeClass","");} else {replaceMap.put("AuthReplyDate_activeClass","active");}
        replaceMap.put("AuthEmail", this.invoice.getAuthEmail());
        if (this.invoice.getAuthEmail().equals("")) { replaceMap.put("AuthEmail_activeClass","");} else {replaceMap.put("AuthEmail_activeClass","active");}
        replaceMap.put("EndDate", this.invoice.getEndDate());
        if (this.invoice.getEndDate().equals("")) { replaceMap.put("EndDate_activeClass","");} else {replaceMap.put("EndDate_activeClass","active");}
        replaceMap.put("GR", this.invoice.getGR());
        if (this.invoice.getGR().equals("")) { replaceMap.put("GR_activeClass","");} else {replaceMap.put("GR_activeClass","active");}
        replaceMap.put("GenpactLastReply", this.invoice.getGenpactLastReply());
        if (this.invoice.getGenpactLastReply().equals("")) { replaceMap.put("GenpactLastReply_activeClass","");} else {replaceMap.put("GenpactLastReply_activeClass","active");}
        replaceMap.put("UserComments", this.invoice.getUserComments());
        if (this.invoice.getUserComments().equals("")) { replaceMap.put("UserComments_activeClass","");} else {replaceMap.put("UserComments_activeClass","active");}
        replaceMap.put("Status", this.invoice.getStatus());
        if (this.invoice.getStatus() == 0) { replaceMap.put("Status_activeClass","");} else {replaceMap.put("Status_activeClass","active");}
        replaceMap.put("User", this.invoice.getUser());
        if (this.invoice.getUser().equals("")) { replaceMap.put("User_activeClass","");} else {replaceMap.put("User_activeClass","active");}
        replaceMap.put("RowColor", this.invoice.getRowColor());
        if (this.invoice.getRowColor().equals("")) { replaceMap.put("RowColor_activeClass","");} else {replaceMap.put("RowColor_activeClass","active");}
        replaceMap.put("ProcessStatus", this.invoice.getProcessStatus());
        if (this.invoice.getProcessStatus().equals("")) { replaceMap.put("ProcessStatus_activeClass","");} else {replaceMap.put("ProcessStatus_activeClass","active");}
        replaceMap.put("ProcessStage", this.invoice.getProcessStage());
        if (this.invoice.getProcessStage() == 0) { replaceMap.put("ProcessStage_activeClass","");} else {replaceMap.put("ProcessStage_activeClass","active");}

        return process(template);
    }
}
