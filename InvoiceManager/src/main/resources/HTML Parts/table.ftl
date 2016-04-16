<#--
Variables for freeMarker:
userColor - color of left border, depend on which user added record to DB
ID - from InvoiceManagerDB.sql
barCode - BC for ID, from InvoiceManagerDB.sql
scan - "barcode" for inv from scanning center, "print" for inv from floor scaner
EntryDate - from InvoiceManagerDB.sql TODO: converter 23 apr, 2016 <-> 20160423
tableWidth - from ImCFG
headerWidth - 100 - tableWidth
-->

<div class="IM-container">
    <div class="w3-accordion white w3-card-8">
        <!-- User Color @ border-left: ? -->
        <div class="row valign-wrapper"  style="margin-bottom: 3px; border-bottom: 1px solid #ddd; border-left: 5px solid ${userColor}">
            <div class="col valign-wrapper center" style="padding: 0px; width: ${headerWidth}%">
                <div class="col s4 valign center" style="padding: 0px"><a href="/ID/${ID}"><button class="btn-flat tooltipped IM-tooltip" data-position="top" data-delay="50" data-tooltip="Edit">${ID}</button></a></div>
                <!-- Trigger/Open new window with INV.tiff  REPLACE ":NR" with ID-->
                <!-- fa fa-barcode - for INV from scanning center, fa fa-print - for INV from scaner -->
                <div class="col s2 valign center" style="padding: 0px"><a href="/ID/${ID}/scan" onClick="${ID}=window.open('/FV/${ID}/scan','Invoice for ID ${ID}','toolbar=no,location=no,directories=no,status=no,menubar=no,scrollbars=no,resizable=yes,width=1024,height=640'); setTimeout(function () { ${ID}.close();}, 500); return false;">
                    <i class="fa fa-${scan} tooltipped IM-tooltip IM-2x" data-position="top" data-delay="50" data-tooltip="${barCode}"></i>
                </a></div>
                <!-- Trigger/Open new window with INV.msg  REPLACE ":NR" with ID -->
                <div class="col s6 valign center" style="padding: 0px"><a href="/ID/${ID}/email" onClick="${ID}=window.open('/FV/Scam/:nr','EntryEmailViewNR','toolbar=no,location=no,directories=no,status=no,menubar=no,scrollbars=no,resizable=yes,width=1024,height=640'); setTimeout(function () { ${ID}.close();}, 500); return false;">
                    <span class="tooltipped IM-tooltip" data-position="top" data-delay="50" data-tooltip="Entry E-Mail">${EntryDate}</span>
                </a></div>
            </div>

            <div class="col" style="padding: 0px; width: ${tableWidth}%">
                <!-- User color for rows  -->
                <#-- TODO: start from here -->
                <table style="border: 2px solid red">
                    <tbody>
                    <tr style="line-height: 1.2em">
                        <td class="IM-Supplier">Acme Inc.</td>
                        <td class="IM-InvoiceNR">12/A45/2016</td>
                        <td class="IM-PO">PO55124457</td>
                        <td class="IM-NetPrice">1200.00 USD</td>
                        <td class="IM-Authorization">Karol.Kistela@delphi.com</td>
                        <td class="IM-Email"><a href="/AuthMail"><i class="fa fa-envelope-o IM-2x" aria-hidden="true"></i></a></td> <!-- Email Icon for Authorization Email -->
                        <td class="IM-GR">45843186, 156863154, 86631216</td>
                        <td class="IM-details" style="text-align: center" >
                            <div class="btn-fla" style="display: flex" onclick="myFunction('Demo1')">
                                <i class="fa fa-info-circle IM-2x fa-align-center" aria-hidden="true"></i>
                            </div>
                        </td>
                    </tr>
                    </tbody>
                </table>
            </div>
        </div>
        <div id="Demo1" class="w3-accordion-content w3-container">
            <p>Lorem ipsum...</p>
        </div>
    </div>
</div>