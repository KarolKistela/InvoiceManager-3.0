<#-- VARIABLES for replecaMap:
Values of colummns:
${ID}
${BC}
${EntryDate}
${ContactGenpact}
${Supplier}
${InvoiceNR}
${InvScanPath}
${PO}
${NetPrice}
${Currency}
${InvDate}
${EmailSubject}
${AuthContact}
${AuthDate}
${AuthReplyDate}
${AuthEmail}
${EndDate}
${GR}
${GenpactLastReply}
${UserComments}
${Status}
${User}
${RowColor}
${ProcessStatus}
${ProcessStage}

${ID-activeClass}
${BC-activeClass}
${EntryDate-activeClass}
${ContactGenpact-activeClass}
${Supplier-activeClass}
${InvoiceNR-activeClass}
${InvScanPath-activeClass}
${PO-activeClass}
${NetPrice-activeClass}
${Currency-activeClass}
${InvDate-activeClass}
${EmailSubject-activeClass}
${AuthContact-activeClass}
${AuthDate-activeClass}
${AuthReplyDate-activeClass}
${AuthEmail-activeClass}
${EndDate-activeClass}
${GR-activeClass}
${GenpactLastReply-activeClass}
${UserComments-activeClass}
${Status-activeClass}
${User-activeClass}
${RowColor-activeClass}
${ProcessStatus-activeClass}

-->
<main style="padding: 0 1%; margin-top: 0px">
    <article class="card">
        <div class="row" style="padding-top: 7.5% 4%">
            <form class="col s12" method="post">
<!-- ========== 1st Row - 4 columns ================================================================================ -->
                <div class="row" style="margin-top: 15px">
<!-- ============== 1.1 BarCode ==================================================================================== -->
                    <div class="input-field col s3">
                        <input value="${BC}" id="BC" name="BC" type="text">
                        <label class="${BC_activeClass}" for="BC">BarCode</label>
                    </div>
<!-- ============== 1.2 EntryDate ================================================================================== -->
                    <div class="input-field col s3">
                        <input value="${EntryDate}" id="EntryDate" name="EntryDate" type="date" class="datepicker">
                        <label class="${EntryDate_activeClass}" for="EntryDate">Entry Date</label>
                    </div>
<!-- ============== 1.3 ContactGenpact ============================================================================= -->
                    <div class="input-field col s3">
                        <input value="${ContactGenpact}" id="ContactGenpact" name="ContactGenpact" type="text">
                        <label class="${ContactGenpact_activeClass}" for="ContactGenpact">Contact Genpact</label>
                    </div>
<!-- ============== 1.4 Supplier =================================================================================== -->
                    <div class="input-field col s3">
                        <input value="${Supplier}" id="Supplier" name="Supplier" type="text">
                        <label class="${Supplier_activeClass}" for="Supplier">Supplier</label>
                    </div>
                </div>
<!-- ========== 2nd Row - 5 columns ================================================================================ -->
                <div class="row" style="margin-top: 15px">
<!-- ============== 2.1 InvoiceNR ================================================================================== -->
                    <div class="input-field col s3">
                        <input value="${InvoiceNR}" id="InvoiceNR" name="InvoiceNR" type="text">
                        <label class="${InvoiceNR_activeClass}" for="InvoiceNR">Invoice number</label>
                    </div>
<!-- ============== 2.2 InvScanPath ================================================================================ -->
                    <div class="input-field col s3">
                        <input value="${InvScanPath}" id="InvScanPath" name="InvScanPath" type="text">
                        <label class="${InvScanPath_activeClass}" for="InvScanPath">Path to invoice Scan file</label>
                    </div>
<!-- ============== 2.3 PO ========================================================================================= -->
                    <div class="input-field col s3">
                        <input value="${PO}" id="PO" name="PO" type="text">
                        <label class="${PO_activeClass}" for="PO">Purchase order</label>
                    </div>
<!-- ============== 2.4 NetPrice =================================================================================== -->
                    <div class="input-field col s2">
                        <input value="${NetPrice}" id="NetPrice" name="NetPrice" type="number" step="any">
                        <label class="${NetPrice_activeClass}" for="NetPrice">Net Price</label>
                    </div>
                    <#--<div class="input-field col s1">-->
                        <#--<input value=" {NetPriceDecimal}" id="NetPriceDecimal" name="NetPriceDecimal" type="number">-->
                        <#--<label class=" {NetPriceDecimal_activeClass}" for="NetPriceDecimal">.00</label>-->
                    <#--</div>-->
<!-- ============== 2.5 Currency =================================================================================== -->
                    <div class="input-field col s1">
                        <input value="${Currency}" list="Currency" name="Currency" >
                        <label class="active" for="Currency">Currency</label>
                        <datalist id="Currency">
                        ${CurrencyList}
                        </datalist>
                    </div>
                </div>
<!-- ========== 3rd Row - 4 columns ================================================================================ -->
                <div class="row" style="margin-top: 15px">
<!-- ============== 3.1 InvDate ==================================================================================== -->
                    <div class="input-field col s3">
                        <input value="${InvDate}" id="InvDate" name="InvDate" type="date" class="datepicker">
                        <label class="${InvDate_activeClass}" for="InvDate">Invoice Date</label>
                    </div>
<!-- ============== 3.2 EmailSubject =============================================================================== -->
                    <div class="input-field col s3">
                        <input value="${EmailSubject}" id="EmailSubject" name="EmailSubject" type="text">
                        <label class="${EmailSubject_activeClass}" for="EmailSubject">Email Subject</label>
                    </div>
<!-- ============== 3.3 AuthContact ================================================================================ -->
                    <div class="input-field col s3">
                        <input value="${AuthContact}" id="AuthContact" name="AuthContact" type="text">
                        <label class="${AuthContact_activeClass}" for="AuthContact">Authorization Contact</label>
                    </div>
<!-- ============== 3.4 AuthDate =================================================================================== -->
                    <div class="input-field col s3">
                        <input value="${AuthDate}" id="AuthDate" name="AuthDate" type="date" class="datepicker">
                        <label class="${AuthDate_activeClass}" for="AuthDate">Authorization Date</label>
                    </div>
                </div>
<!-- ========== 4th Row - 4 columns ================================================================================ -->
                <div class="row" style="margin-top: 15px">
<!-- ============== 4.1 AuthReplyDate ============================================================================== -->
                    <div class="input-field col s3">
                        <input value="${AuthReplyDate}" id="AuthReplyDate" name="AuthReplyDate" type="date" class="datepicker">
                        <label class="${AuthReplyDate_activeClass}" for="AuthReplyDate">Authorization Reply Date</label>
                    </div>
<!-- ============== 4.2 AuthEmail ================================================================================== -->
                    <div class="input-field col s3">
                        <input value="${AuthEmail}" id="AuthEmail" name="AuthEmail" type="text">
                        <label class="${AuthEmail_activeClass}" for="AuthEmail">Path to Authorization Email</label>
                    </div>
<!-- ============== 4.3 EndDate ==================================================================================== -->
                    <div class="input-field col s3">
                        <input value="${EndDate}" id="EndDate" name="EndDate" type="date" class="datepicker">
                        <label class="${EndDate_activeClass}" for="EndDate">End Date</label>
                    </div>
<!-- ============== 4.4 GR ========================================================================================= -->
                    <div class="input-field col s3">
                        <input value="${GR}" id="GR" name="GR" type="text">
                        <label class="${GR_activeClass}" for="GR">Good Recipt</label>
                    </div>
                </div>
<!-- ========== 5th Row - 3 columns ================================================================================ -->
                <div class="row" style="margin-top: 15px">
<!-- ============== 5.1 GenpactLastReply =========================================================================== -->
                    <div class="input-field col s3">
                        <input value="${GenpactLastReply}" id="GenpactLastReply" name="GenpactLastReply" type="date" class="datepicker">
                        <label class="${GenpactLastReply_activeClass}" for="GenpactLastReply">Genpact Last Reply</label>
                    </div>
<!-- ============== 5.2 UserComments =============================================================================== -->
                    <div class="input-field col s6">
                        <input value="${UserComments}" id="UserComments" name="UserComments" type="text">
                        <label class="${UserComments_activeClass}" for="UserComments">User Comments</label>
                    </div>
<!-- ============== 5.3 Status ===================================================================================== -->
                    <div class="input-field col s3">
                        <input value="${Status}" list="Status" name="Status" >
                        <label class="active" for="Status">Status</label>
                        <datalist id="Status">
                        ${StatusList}
                        </datalist>
                    </div>
                </div>
<!-- ========== 6th Row - 3 columns ================================================================================ -->
                <div class="row" style="margin-top: 15px">
<!-- ============== 6.1 User ======================================================================================= -->
                    <div class="input-field col s3">
                        <#--<input value="${User}" id="User" name="User" type="text">-->
                        <#--<label class="${User_activeClass}" for="User">User</label>-->
                        <input value="${User}" list="User" name="User" >
                        <label class="active" for="User">User</label>
                        <datalist id="User">
                        ${UserList}
                        </datalist>
                    </div>
<!-- ============== 6.2 RowColor =================================================================================== -->
                    <div class="input-field col s3">
                        <#--<input value="${RowColor}" id="RowColor" name="RowColor" type="text">-->
                        <#--<label class="${RowColor_activeClass}" for="RowColor">Mark Invoice with color</label>-->
                        <input value="${RowColor}" list="RowColor" name="RowColor" >
                        <label class="active" for="RowColor">Mark ID with color</label>
                        <datalist id="RowColor">
                        ${RowColorList}
                        </datalist>
                    </div>
<!-- ============== 6.3 ProcessStatus ============================================================================== -->
                    <div class="input-field col s3">
                        <#--<input value="${ProcessStatus}" id="ProcessStatus" name="ProcessStatus" type="number">-->
                        <#--<label class="${ProcessStatus_activeClass}" for="ProcessStatus">Process Status</label>-->
                        <input value="${ProcessStatus}" list="ProcessStatus" name="ProcessStatus" >
                        <label class="active" for="ProcessStatus">Process Status</label>
                        <datalist id="ProcessStatus">
                        ${ProcessStatusList}
                        </datalist>
                    </div>
<!-- ============== ProcessStage =================================================================================== -->
                    <div class="input-field col s3">
                        <p class="range-field">
                            <input value="${ProcessStage}" id="ProcessStage" name="ProcessStage" type="range" min="0" max="100">
                        </p>
                        <label class="" for="ProcessStage">Process Stage - turned off</label>
                    </div>
                </div>
<!-- =============== Submit btn ==================================================================================== -->
                <div class="row" style="margin: 50px 0">
                    <div class="col s12 center">
                        <input class="btn Im-save" type="submit" value="Save" id="save changes">
                    </div>
                </div>
        </form>
        </div>
    </article>
</main>