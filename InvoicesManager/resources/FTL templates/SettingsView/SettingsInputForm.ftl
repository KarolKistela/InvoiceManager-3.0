<#-- VARIABLES for replecaMap:
    ${value_imExternalFolderPath}
    ${activeClass_imExternalFolderPath}
    ${folderExists}
    ${value_imDBPath}
    ${activeClass_imDBPath}
    ${dbExists}
    ${value_rowsPerPage}
    ${rowsSetup}
    ${placeHolder_orderBy}
    ${order_chcked}
    ${duplicates_chcked}
    ${value_userID}
    ${activeClass_userID}
    ${netIDok}
    ${value_userEmail}
    ${isEmail}
    $${activeClass_userEmail}
    ${value_userColor}

    ${value_DNSserver}
    ${activeClass_DNSserver}

    outlookExePathIconColor
    value_outlookExePath
    activeClass_outlookExePath
-->
<main style="margin-top: 70px; padding: 0 5%">
    <article class="card">
            <form class="col s12" style="padding: 3% 0%" method="post">
<!-- ============================ Folder Path ====================================================================== -->
                <div class="row" style="margin-bottom: 10px">
                    <div class="col s1 IM-SettingsLabels" style="padding-top: 25px${folderExists}">
                        <i class="fa fa-folder fa-2x" aria-hidden="true"></i>
                    </div>
                    <div class="input-field col s4">
                        <input  value="${value_imExternalFolderPath}" id="imExternalFolderPath" name="imExternalFolderPath" type="text">
                        <label class="${activeClass_imExternalFolderPath}" for="imExternalFolderPath">Path to Folder with invoices files</label>
                    </div>
<!-- ============================ Databse Path ===================================================================== -->
                    <div class="col s1 IM-SettingsLabels" style="padding-top: 25px${dbExists}">
                        <i class="fa fa-database fa-2x" aria-hidden="true"></i>
                    </div>
                    <div class="input-field col s5">
                        <input value="${value_imDBPath}" id="imDBPath" type="text" name="imDBPath">
                        <label class="${activeClass_imDBPath}" for="imDBPath">Path to SQLITE DB InvoicesManager.db</label>
                    </div>
                </div>
<!-- ============================ outlook.exe path ================================================================= -->
                ${financeView1}<div class="row" style="margin-bottom: 10px">
                    <div class="col s1 IM-SettingsLabels" style="padding-top: 25px${outlookExePathIconColor}">
                        <i class="fa fa-envelope fa-2x" aria-hidden="true"></i>
                    </div>
                    <div class="input-field col s10">
                        <input  value="${value_outlookExePath}" id="outlookExePath" name="outlookExePath" type="text">
                        <label class="${activeClass_outlookExePath}" for="outlookExePath">Path to outlook.exe (for eg.: C:\Program Files (x86)\Microsoft Office\Office14\outlook.exe</label>
                    </div>
                </div>${financeView2}
<!-- ============================ DNS server ======================================================================= -->
                <div class="row" style="margin-bottom: 10px">
                    <div class="col s1 IM-SettingsLabels" style="padding-top: 25px${DNSserverIconColor}">
                            <i class="fa fa-server fa-2x" aria-hidden="true"></i>
                    </div>
                    <div class="input-field col s7">
                        <input  value="${value_DNSserver}" id="imDNSserver" name="imDNSserver" type="text">
                        <label class="${activeClass_DNSserver}" for="imDNSserver">Database DNS Server address (JDBC connection string - full)</label>
                    </div>
                    <div class="input-field col s3">
                        <input  value="${value_DNSjdbcClass}" id="imDNSjdbcClass" name="imDNSjdbcClass" type="text">
                        <label class="${activeClass_DNSjdbcClass}" for="imDNSjdbcClass">JDBC load class string</label>
                    </div>
                </div>
<!-- ============================ DNS credentials ====================================================================== -->
                <div class="row" style="margin-bottom: 10px">
                    <div class="col s1 IM-SettingsLabels" style="padding-top: 25px${DNSuserIcon}">
                        <i class="fa fa-key fa-2x" aria-hidden="true"></i>
                    </div>
                    <div class="input-field col s4">
                        <input  value="${value_DNSuser}" id="imDNSuser" name="imDNSuser" type="text">
                        <label class="${activeClass_DNSuser}" for="imDNSuser">DNS DB username</label>
                    </div>
                    <div class="col s1 IM-SettingsLabels" style="padding-top: 25px${DNSpassIcon}">
                        <i class="fa fa-key fa-2x" aria-hidden="true"></i>
                    </div>
                    <div class="input-field col s5">
                        <input  value="${value_DNSpass}" id="imDNSpass" name="imDNSpass" type="text">
                        <label class="${activeClass_DNSpass}" for="imDNSpass">DNS DB password</label>
                    </div>
                </div>
<!-- =============================== Rows Per Page ================================================================= -->
                    <#--<div class="col s1 IM-SettingsLabels" style="padding-top: 25px${rowsSetup}>-->
                        <#--<i class="fa fa-list fa-2x" aria-hidden="true"></i>-->
                    <#--</div>-->
                    <#--<div class="input-field col s10">-->
                        <#--<p class="range-field">-->
                            <#--<input type="range" id="rowsPerPage" name="rowsPerPage" min="5" max="250" value="${value_rowsPerPage}"/>-->
                        <#--</p>-->
                        <#--<label for="rowsPerPage">How many rows display per page?</label>-->
                    <#--</div>-->
                <#--</div>-->
                <#---->
<!-- ================================ Order by ===================================================================== -->
                    <#--<div class="col s1 IM-SettingsLabels" style="padding-top: 23px">-->
                        <#--<label style="font-size: 1.25em; color: dodgerblue">Order by</label>-->
                    <#--</div>-->
                    <#--<div class="col s2 input-field">-->
                        <#--<input placeholder="${placeHolder_orderBy}" list="columnName" name="orderBy"/>-->
                        <#--<datalist id="columnName">-->
                            <#--<option value="ID">-->
                            <#--<option value="EntryDate">-->
                            <#--<option value="Supplier">-->
                            <#--<option value="InvoiceNR">-->
                            <#--<option value="InvDate">-->
                            <#--<option value="AuthDate">-->
                        <#--</datalist>-->
                    <#--</div>-->
                    <#--<div class="col s1 IM-SettingsLabels" style="padding-top: 25px; color: dodgerblue"">-->
                        <#--<i class="fa fa-sort fa-2x" aria-hidden="true"></i>-->
                    <#--</div>-->
                    <#--<div class="switch col s2" style="padding: 23px 0 0 23px; ">-->
                        <#--<label>-->
                            <#--Ascending-->
                            <#--<input type="checkbox" name="order" ${order_chcked} style="padding-top: 5px">-->
                            <#--<span class="lever"></span>-->
                            <#--Descending-->
                        <#--</label>-->
                    <#--</div>-->
<#--<!-- ================================== Duplicates ================================================================= &ndash;&gt;-->
                    <#--<div class="col s3 IM-SettingsLabels" style="padding-top: 23px">-->
                        <#--<label style="font-size: 1.25em; color: dodgerblue"">Find Duplicates by Invoice Nr?</label>-->
                    <#--</div>-->
                    <#--<div class="switch col s3" style="padding: 23px 0 0 23px">-->
                        <#--<label>-->
                            <#--no-->
                            <#--<input type="checkbox" name="duplicates" ${duplicates_chcked}>-->
                            <#--<span class="lever"></span>-->
                            <#--yes-->
                        <#--</label>-->
                    <#--</div>-->
                <#--</div>-->
                <#--<div class="row" style="margin-top: 20px">-->
<!-- ============================ User settings ==================================================================== -->
                ${financeView1}<div class="row" style="margin-bottom: 10px">
                    <div class="col s1 IM-SettingsLabels" style="padding-top: 25px${netIDok}">
                        <i class="fa fa-user fa-2x" aria-hidden="true"></i>
                    </div>
                    <div class="input-field col s2">
                        <input value="${value_userID}" id="userID" type="text" name="userID" length="6">
                        <label class="${activeClass_userID}" for="userID">NetID</label>
                    </div>
                    <div class="col s1 IM-SettingsLabels" style="padding-top: 25px${isEmail}">
                        <i class="fa fa-envelope-o fa-2x" aria-hidden="true"></i>
                    </div>
                    <div class="col s3">
                        <div class="input-field col s12">
                            <input value="${value_userEmail}" id="email" type="email" name="userEmail" class="validate">
                            <label class="${activeClass_userEmail}" for="email" data-error="wrong" data-success="right">Email</label>
                        </div>
                    </div>
                    <div class="col s2 IM-SettingsLabels" style="padding-top: 23px">
                        <label style="font-size: 1.25em;color: dodgerblue"">Choose your color</label>
                    </div>
                    <div class="col s2">
                        <div class="input-field col s12" style="padding-top: 9px">
                            <input id="userColor" type="color" name="userColor" value="${value_userColor}">
                        </div>
                    </div>
                </div>${financeView2}
=<!-- =========================== Submit btn ======================================================================= -->
                <div class="row" style="margin: 50px 0">
                    <div class="col s12 center">
                        <input class="btn Im-save" type="submit" value="Save" id="save changes">
                    </div>
                </div>
            </form>
    </article>
</main>