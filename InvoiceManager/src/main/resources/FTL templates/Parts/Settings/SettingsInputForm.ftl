<#-- VARIABLES for replecaMap:
    ${value_imExternalFolderPath}
    ${activeClass_imExternalFolderPath}
    ${value_imDBPath}
    ${activeClass_imDBPath}
    ${value_rowsPerPage}
    ${placeHolder_orderBy}
    ${order_chcked}
    ${duplicates_chcked}
    ${value_userID}
    ${activeClass_userID}
    ${value_userEmail}
    ${activeClass_userID}
    ${value_userColor}
-->
<main style="margin-top: 50px; padding: 0 5%">
    <article class="card">
        <div class="row" style="padding-top: 7.5% 4%">
            <form class="col s12" method="post">
                <div class="row" style="margin-top: 15px">
                    <!-- =============== Folder Path =================================================================================== -->
                    <div class="col s1 IM-SettingsLabels" style="padding-top: 25px">
                        <i class="fa fa-folder fa-2x" aria-hidden="true"></i>
                    </div>
                    <div class="input-field col s4">
                        <input  value="${value_imExternalFolderPath}" id="imExternalFolderPath" name="imExternalFolderPath" type="text">
                        <label class="${activeClass_imExternalFolderPath}" for="imExternalFolderPath">Path to Folder with invoices files</label>
                    </div>
                    <!-- =============== Databse Path ================================================================================== -->
                    <div class="col s2 IM-SettingsLabels" style="padding-top: 25px">
                        <i class="fa fa-database fa-2x" aria-hidden="true"></i>
                    </div>
                    <div class="input-field col s4">
                        <input value="${value_imDBPath}" id="imDBPath" type="text" name="imDBPath">
                        <label class="${activeClass_imDBPath}" for="imDBPath">Path to InvoicesManager.db</label>
                    </div>
                </div>
                <div class="row">
                    <!-- =============== Rows Per Page ================================================================================= -->
                    <div class="col s1 IM-SettingsLabels" style="padding-top: 25px">
                        <i class="fa fa-list fa-2x" aria-hidden="true"></i>
                    </div>
                    <div class="input-field col s10">
                        <p class="range-field">
                            <input type="range" id="rowsPerPage" name="rowsPerPage" min="5" max="250" value="${value_rowsPerPage}"/>
                        </p>
                        <label for="rowsPerPage">How many rows display per page?</label>
                    </div>
                </div>
                <div class="row">
                    <!-- =============== Order by ====================================================================================== -->
                    <div class="col s1 IM-SettingsLabels" style="padding-top: 23px">
                        <label style="font-size: 1.25em">Order by</label>
                    </div>
                    <div class="col s2 input-field">
                        <input placeholder="${placeHolder_orderBy}" list="columnName" name="orderBy"/>
                        <datalist id="columnName">
                            <option value="ID">
                            <option value="EntryDate">
                            <option value="Supplier">
                            <option value="InvoiceNR">
                            <option value="InvDate">
                            <option value="AuthDate">
                        </datalist>
                    </div>
                    <div class="col s1 IM-SettingsLabels" style="padding-top: 25px">
                        <i class="fa fa-sort fa-2x" aria-hidden="true"></i>
                    </div>
                    <div class="switch col s2" style="padding: 23px 0 0 23px; ">
                        <label>
                            Ascending
                            <input type="checkbox" name="order" ${order_chcked} style="padding-top: 5px">
                            <span class="lever"></span>
                            Descending
                        </label>
                    </div>
                    <!-- =============== Duplicates ==================================================================================== -->
                    <div class="col s3 IM-SettingsLabels" style="padding-top: 23px">
                        <label style="font-size: 1.25em">Find Duplicates by Invoic Nr?</label>
                    </div>
                    <div class="switch col s3" style="padding: 23px 0 0 23px">
                        <label>
                            no
                            <input type="checkbox" name="duplicates" ${duplicates_chcked}>
                            <span class="lever"></span>
                            yes
                        </label>
                    </div>
                </div>
                <div class="row" style="margin-top: 20px">
                    <!-- =============== User settings ================================================================================= -->
                    <div class="col s1 IM-SettingsLabels" style="padding-top: 25px">
                        <i class="fa fa-user fa-2x" aria-hidden="true"></i>
                    </div>
                    <div class="input-field col s2">
                        <input value="${value_userID}" id="userID" type="text" name="userID" length="6">
                        <label class="${activeClass_userID}" for="userID">netid</label>
                    </div>
                    <div class="col s1 IM-SettingsLabels" style="padding-top: 25px">
                        <i class="fa fa-envelope-o fa-2x" aria-hidden="true"></i>
                    </div>
                    <div class="col s3">
                        <div class="input-field col s12">
                            <input value="${value_userEmail}" id="email" type="email" name="userEmail" class="validate">
                            <label class="${activeClass_userID}" for="email" data-error="wrong" data-success="right">Email</label>
                        </div>
                    </div>
                    <div class="col s2 IM-SettingsLabels" style="padding-top: 23px">
                        <label style="font-size: 1.25em">Choose your color</label>
                    </div>
                    <div class="col s2">
                        <div class="input-field col s12" style="padding-top: 9px">
                            <input id="userColor" type="color" name="userColor" value="${value_userColor}">
                        </div>
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