<#-- VARIABLES for replecaMap:
        ${rowComment}
        ${rowColor}
        ${userColor}
        ${ID}
        ${fileExists}
        ${BCrow}
        ${entryDate}
        ${supplier}
        ${invNrLink} - if inv nr repeats in DB then do link to this repetitions
        ${invoiceNR}
        ${invDate}
        ${invNrLink_a}
        &{POlink}
        ${PO}
        ${netPrice}
        ${currency}
        ${authorization}
        ${email}
        ${GR}
        ${processStage}
        ${BC}
        ${SupplierDetails}
        ${InvNrDetails}
        ${PODetails}
        ${GRDetails}
        ${contactGenpact}
        ${invDate}
        ${emailSubject}
        ${authDate}
        ${authReplyDate}
        ${endDate}
        ${genpactLastReply}
        ${userComments}
        ${status}
        ${FinanceComments}
-->
${rowComment}
    <article class="hoverable">
        <table class="${rowColor} lighten-3" style="border-left: 6px solid ${userColor}"> <#--;border-right: 4px solid ${userColor}">-->
            <tbody>
            <tr>
                <td class="IM-ID"><a href="/IFV/ID/eq/${ID}/ID/DESC/1">${ID}</a></td>
                <td class="IM-BC">
                    <a href="/ID/${ID}/${scanPath}/scan" onClick="scan${ID}=window.open('/ID/${ID}/${scanPath}/scan','scan${ID}','width=640,height=480');
                                                    setTimeout(function () { scan${ID}.close();}, 500);
                                                    return false;">
                        <span style="color: ${fileExists}">${BCrow}</span>
                    </a>
                </td>
                <td class="IM-EntryDate ">${entryDate}</td>
                <td class="IM-Supplier">
                    <a href="/IFV/Supplier/eq/${supplierLink}/ID/DESC/1">
                        ${supplier}
                    </a>
                </td>
                <td class="IM-InvoiceNR">${invNrLink}${invoiceNR}${invNrLink_a}</td>
                <td class="IM-InvDate">${invDate}</td>
                <td class="IM-PO"><a href="/IFV/Supplier/eq/${supplierLink}/PO/eq/${POlink}/ID/DESC/1">${PO}</td>
                <td class="IM-NetPrice" style="text-align: right; padding-right: 20px">${netPrice}</td>
                <td class="IM-Currency">${currency}</td>
                <td class="IM-AuthContact" >
                    <a href="/ID/${ID}/${authContact}/${scanPath}/authEmail" onClick="authEmail${ID}=window.open('/ID/${ID}/${authContact}/${scanPath}/authEmail','authEmail${ID}','width=640,height=480');
                            setTimeout(function () { authEmail${ID}.close();}, 500);
                            return false;">
                        ${authorization}
                    </a>
                </td>
                <td class="IM-AuthEmail">
                    <a href="/ID/${ID}/${scanPath}/Folder" onClick="Folder${ID}=window.open('/ID/${ID}/${scanPath}/Folder','Folder${ID}','width=640,height=480');
                            setTimeout(function () { Folder${ID}.close();}, 500);
                            return false;">
                        <i class="fa fa-folder-open${authEmailExists}" aria-hidden="true"></i>
                    </a>
                </td>
                <td class="IM-GR">${GR}</td>
                <td class="IM-details"><i class="fa fa-angle-double-down" onclick="myFunction('${ID}')"></i></td>
            </tr>
            </tbody>
        </table>
<!-- ============================ Process stage Indicator ========================================================== -->
<!-- off -->
        <#--<div class="progress grey">-->
            <#--<div class="determinate" style="width: ${processStage}%; background-color: ${userColor}"></div>-->
        <#--</div>-->
<!-- ============================ Details for inv ID =============================================================== -->
        <section id="${ID}" class="w3-accordion-content"> <#--style="border-left: 6px solid ${userColor}"><#--;border-right: 4px solid ${userColor}">-->
            <table class="highlight IM-detailsTabel" style="border-top: none">
                <tbody>
                <tr>
                    <td>BC:</td>
                    <td>${BC}</td>
                </tr>
                <tr>
                    <td>Supplier:</td>
                    <td>${SupplierDetails}</td>
                </tr>
                <tr>
                    <td>InvNr:</td>
                    <td>${InvNrDetails}</td>
                </tr>
                <tr>
                    <td>PO:</td>
                    <td>${PODetails}</td>
                </tr>
                <tr>
                    <td>GR:</td>
                    <td>${GRDetails}</td>
                </tr>
                <tr>
                    <td>ContactGenpact:</td>
                    <td>${contactGenpact}</td>
                </tr>
                <tr>
                    <td>InvDate:</td>
                    <td>${invDate}</td>
                </tr>
                <tr>
                    <td>EmailSubject:</td>
                    <td>${emailSubject}</td>
                </tr>
                <tr>
                    <td>AuthDate:</td>
                    <td>${authDate}</td>
                </tr>
                <tr>
                    <td>AuthReplyDate:</td>
                    <td>${authReplyDate}</td>
                </tr>
                <tr>
                    <td>EndDate:</td>
                    <td>${endDate}</td>
                </tr>
                <tr>
                    <td>GenpactLastReply:</td>
                    <td>${genpactLastReply}</td>
                </tr>
                <tr>
                    <td>UserComments:</td>
                    <td>${userComments}</td>
                </tr>
                <tr>
                    <td>Status:</td>
                    <td>${status}</td>
                </tr>
                <tr>
                    <td>FinanceComments:</td>
                    <td>${FinanceComments}</td>
                </tr>
                </tbody>
            </table>
        </section>
    </article>
