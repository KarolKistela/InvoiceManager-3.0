<#-- VARIABLES for replecaMap:
        ${rowComment}
        ${rowColor}
        ${userColor}
        ${ID}
        ${fileExists}
        ${entryDate}
        ${supplier}
        ${invNrLink} - if inv nr repeats in DB then do link to this repetitions
        ${invoiceNR}
        ${invNrLink_a}
        ${PO}
        ${netPrice}
        ${authorization}
        ${email}
        ${emailLink}
        ${GR}
        ${processStage}
        ${BC
        ${contactGenpact}
        ${invDate}
        ${emailSubject}
        ${authDate}
        ${authReplyDate}
        ${endDate}
        ${genpactLastReply}
        ${userComments}
        ${status}
        ${processStatus}
-->
${rowComment}
    <article class="hoverable">
        <table class="${rowColor}" style="border-left: 4px solid ${userColor};border-right: 4px solid ${userColor}">
            <tbody>
    <#-- TODO: more generic solution -> create rows based on data from InvoicesMetaData. New columns with additonal instructions need to be added -->
            <tr>
                <td class="IM-ID"><a href="/ID/${ID}" target="_blank">${ID}</a></td>
                <td class="IM-InvScanPath ">
                    <a href="/ID/${ID}/scan" onClick="scan${ID}=window.open('/ID/${ID}/scan','scan${ID}','toolbar=no,location=no,directories=no,status=no,menubar=no,scrollbars=no,resizable=yes,width=640,height=480'); setTimeout(function () { scan${ID}.close();}, 500); return false;">
                        <i class="fa fa-${fileExists}" aria-hidden="true"></i>
                    </a>
                </td>
                <td class="IM-EntryDate ">${entryDate}</td>
                <td class="IM-Supplier">
                    <a href="/Filter/Select/Supplier/eq/${supplierLink}/1">
                        ${supplier}
                    </a>
                </td>
                <td class="IM-InvoiceNR">${invNrLink}${invoiceNR}${invNrLink_a}</td>
                <td class="IM-PO">${PO}</td>
                <td class="IM-NetPrice">${netPrice}</td>
                <td class="IM-AuthContact" >
                    <a href="/Filter/Select//AuthContact/eq/${authorizationLink}/1">
                        ${authorization}
                    </a>
                </td>
                <td class="IM-AuthEmail"><a ${emailLink}">${email}</a></td>
                <td class="IM-GR">${GR}</td>
                <td class="IM-details"><i class="fa fa-chevron-down" onclick="myFunction('${ID}')"></i></td>
            </tr>
            </tbody>
        </table>
        <!-- ========================================== Process stage Indicator -->
    <#-- TODO: export color of progres bar to settings -->
        <div class="progress grey">
            <div class="determinate grey darken-4" style="width: ${processStage}%"></div>
        </div>
        <!-- ================================================ Details for inv ID -->
        <section id="${ID}" class="w3-accordion-content" style="border-left: 4px solid ${userColor};border-right: 4px solid ${userColor}">
            <table class="highlight IM-detailsTabel" style="border-top: none">
                <tbody>
                <tr>
                    <td>BC:</td>
                    <td>${BC}</td>
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
                    <td>ProcessStatus:</td>
                    <td>${processStatus}</td>
                </tr>
                </tbody>
            </table>

        </section>
    </article>
