<#-- VARIABLES for replecaMap:
        ${filterList} -- Im CFG
            |-- Header_filterList.ftl
        ${NetID}
        ${supplierList}
        ${commentOnMenu1}
        ${commentOnMenu2}
        ${commentOnMenu3}
        ${commentOnMenu4}
        ${commentOnMenu5}
        ${commentOffMenu1}
        ${commentOffMenu2}
        ${commentOffMenu3}
        ${commentOffMenu4}
        ${commentOffMenu5}
        ${menu1} -- IM-menu-active
        ${menu2}
        ${menu3}
        ${menu4}
        ${menu5}
        ${records}
        ${viewTitle}
        ${paginationOff1} - turns off while pagination
        ${paginationOff2} - turns off while pagination
        ${pagePreviousOff1} - turns off pagination buttons - previous
        ${pagePreviousOff2} - turns off pagination buttons - previous
        ${pageNextOff1} - turns off pagination buttons - next
        ${pageNextOff2} - turns off pagination buttons - next
        ${previous}
        ${pageNr}
        ${next}
        ${suppliersList}
        ${commentOnAdvSearch}
        ${commentOffAdvSearch}
        ${commentOnSearch}
        ${commentOffSearch}
        &{tableHeader} -- InvoiceMetaData
            | -- Header_tableHeader.ftl
-->
<header class="row">
    <nav class="col s3">
<!-- ============================ SideNav ========================================================================== -->
        <ul class="IM-sidenav">
            <li><a href="javascript:void(0)" onclick="w3_close()">Close &times;</a></li>
            <li><a href="/IFV/User/eq/${NetID}/GR/eq/null/ID/DESC/1">NetID + GR blank</a></li>
            <li><a href="/IFV/User/eq/${NetID}/Status/neq/null/ID/DESC/1">NetID + Status NOT blank</a></li>
            <li><a href="/IFV/User/eq/${NetID}/AuthEmail/eq/null/GR/eq/null/ID/DESC/1">NetID + AuthEmail blank + GR blank</a></li>
            <li><a href="/IFV/User/eq/${NetID}/GR/eq/null/AuthContact/eq/null/ID/DESC/1">NetID + AuthEmail blank + GR blank + AuthContact blank</a></li>
            <li><a href="/IFV/User/eq/${NetID}/PO/neq/null/AuthEmail/eq/null/ID/DESC/1">NetID + PO nr only + AuthEmail blank</a></li>
            <li><a onclick="openModal('advSearchByColor'); w3_close()">By color</a></li>
            <li><a onclick="openModal('advSearch7'); w3_close()">Supplier + PO</a></li>
            <li><a onclick="openModal('advSearch8'); w3_close()">Supplier + PO + AuthContact</a></li>
            <li><a onclick="openModal('advSearch9'); w3_close()">Supplier + Status not blank</a></li>
        </ul>
        <!-- ============================ MainManu ========================================================================= -->
        <ul>
            ${commentOnMenu1}<li><a href="/IFV/ID/gte/0/ID/DESC/1"><i class="fa fa-home${menu1}"></i></a></li>${commentOffMenu1}
            ${commentOnMenu2}<li><a href="/Settings"><i class="fa fa-cog${menu2}"></i></a></li>${commentOffMenu2}
            ${commentOnMenu3}<li onclick="w3_open()"><a href="#"><i class="fa fa-filter${menu3}"></i></a></li>${commentOffMenu3}
            ${commentOnMenu4}<li><a href="/Reports"><i class="fa fa-bar-chart${menu4}"></i></a></li>${commentOffMenu4}
            ${commentOnMenu5}<li><a onclick="openModal('Save');"><i class="fa fa-floppy-o${menu5}"></i></a></li>${commentOffMenu5}
            <li><a onclick="openModal('Exit')"><i class="fa fa-power-off"></i></a></li>
        </ul>
        <div id="Save" class="modal" style="border: 1px solid #bbb">
            <div class="modal-content" style="height: auto; color: white;">
                <h5 style="text-align: center">Do you want to export ${records} records to CSV file?</h5>
                <div class="row center">
                    <a href="/Save" class="waves-effect waves-light btn" style="width: 100px; margin: 20px 20px; color: black;">Export</a>
                    <a onclick="closeModal('Save');" class="waves-effect waves-light btn" style="width: 100px; margin: 20px 20px; color: black;">Cancel</a>
                </div>
            </div>
        </div>
        <div id="Exit" class="modal" style="border: 1px solid #bbb">
            <div class="modal-content" style="height: auto; color: white;">
                <h5 style="text-align: center">Do you want to Exit Invoices Manager 3.0?</h5>
                <div class="row center">
                    <a href="/exit" class="waves-effect waves-light btn" style="width: 100px; margin: 20px 20px; color: black;">Yes</a>
                    <a onclick="closeModal('Exit');" class="waves-effect waves-light btn" style="width: 100px; margin: 20px 20px; color: black;">Cancel</a>
                </div>
            </div>
        </div>
    </nav>
<!-- ============================ View Title ======================================================================= -->
    <div class="col s2 center">
        <a href="#"><strong class="waves-effect">${viewTitle}</strong></a>
    </div>
<!-- ============================ Pagination ======================================================================= -->
    <nav class="col s2">
        <ul id="topPagination">
            ${paginationOff1}<li class="waves-effect">${pagePreviousOff1}<a href="${rout}${previous}">${pagePreviousOff2}<i class="fa fa-angle-double-left"></i>${pagePreviousOff1}</a>${pagePreviousOff2}</li>
            <li class="waves-effect"><a>${pageNr}</a></li>
            <li class="waves-effect"><a href="${rout}${next}">${pageNextOff1}<i class="fa fa-angle-double-right"></i>${pageNextOff2}</a></li>${paginationOff2}
        </ul>
    </nav>
<!-- ============================ Advanced Search ================================================================== -->
    <nav class="col s3">
        <!-- Modal Trigger   -->
        <ul style="float: right">
            ${commentOnAdvSearch}<li><a onclick="openModal('advSearch');"><i class="fa fa-search-plus"></i></a></li>${commentOffAdvSearch}
        </ul>
<!-- ============================ Modal Structure for advSearch ==================================================== -->
        <div id="advSearch" class="modal" style="border: 1px solid #bbb">
            <div class="modal-content white" style="height: auto">
                <h5 style="text-align: center">Advance Search</h5>
                <form method="post">
                <div class="row center">
                    <div class="col s4">
                        <input placeholder="search column" id="search_query_columns" list="columns" name="search_query_columns" type="text">
                    </div>
                    <div class="col s1 center">
                        <input id="search_query_sign" list="signs" name="search_query_sign" type="text" value="=">
                    </div>
                    <div class="col s4">
                        <input placeholder="search value" id="search_query_value" name="search_query_value" type="text">
                    </div>
                    <div class="col s3">
                        <input type="submit" class="btn" style="padding: 0 30px">
                    </div>
                </div>
                <div class="row center">
                    <div class="col s4">
                        <input placeholder="search column2" id="search_query_columns2" list="columns" name="search_query_columns2" type="text">
                    </div>
                    <div class="col s1 center">
                        <input id="search_query_sign2" list="signs" name="search_query_sign2" type="text" value="=">
                    </div>
                    <div class="col s4">
                        <input placeholder="search value2" id="search_query_value2" name="search_query_value2" type="text">
                    </div>
                    <div class="col s3">

                    </div>
                </div>
                <div class="row center">
                    <div class="col s4">
                        <input placeholder="search column3" id="search_query_columns3" list="columns" name="search_query_columns3" type="text">
                    </div>
                    <div class="col s1 center">
                        <input id="search_query_sign3" list="signs" name="search_query_sign3" type="text" value="=">
                    </div>
                    <div class="col s4">
                        <input placeholder="search value3" id="search_query_value3" name="search_query_value3" type="text">
                    </div>
                    <div class="col s3">

                    </div>
                </div>
                    <datalist id="columns">
                        <option value="BC">
                        <option value="EntryDate">
                        <option value="ContactGenpact">
                        <option value="Supplier">
                        <option value="InvoiceNR">
                        <option value="InvScanPath">
                        <option value="PO">
                        <option value="NetPrice">
                        <option value="Currency">
                        <option value="InvDate">
                        <option value="EmailSubject">
                        <option value="AuthContact">
                        <option value="AuthDate">
                        <option value="AuthReplyDate">
                        <option value="AuthEmail">
                        <option value="EndDate">
                        <option value="GR">
                        <option value="GenpactLastReply">
                        <option value="UserComments">
                        <option value="Status">
                        <option value="User">
                        <option value="RowColor">
                        <option value="ProcessStatus">
                        <option value="FinanceComments">
                    </datalist>
                    <datalist id="signs">
                        <option value="=">
                        <option value="!=">
                        <option value=">">
                        <option value=">=">
                        <option value="<">
                        <option value="<=">
                        <option value="LIKE">
                        <option value="NOT LIKE">
                    </datalist>
                    <datalist id="colors">
                        <option style="color: cyan" value="cyan">
                        <option style="color: pink" value="pink">
                        <option style="color: purple" value="purple">
                        <option style="color: indigo" value="indigo">
                        <option style="color: teal" value="teal">
                        <option style="color: red" value="red">
                        <option style="color: green" value="green">
                        <option style="color: lime" value="lime">
                        <option style="color: #ffc107" value="amber">
                        <option style="color: orange" value="orange">
                        <option style="color: #ff5722" value="deep-orange">
                        <option style="color: #607d8b" value="blue-grey">
                        <option style="color: grey" value="grey">
                        <option style="color: yellow" value="yellow">
                    </datalist>
                    <datalist id="suppliers">
${supplierList}
                    </datalist>
                </form>
            </div>
            <div class="modal-fixed-footer blue">
                <a onclick="closeModal('advSearch');" class="modal-close">
                    <h5 style="padding: 8px;border: 1px solid #2196F3;margin: 10px 0 0 0;">Close</h5>
                </a>
            </div>
        </div>
<!-- ============================ Modal Structure for advSearchByColor ============================================= -->
        <div id="advSearchByColor" class="modal" style="border: 1px solid #bbb">
            <div class="modal-content white" style="height: auto">
                <h5 style="text-align: center">Advance Search By color</h5>
                <form method="post">
                    <div class="row center">
                        <div class="col s4">
                            <input placeholder="search column" id="search_query_columns" name="search_query_columns" type="text" value="RowColor">
                        </div>
                        <div class="col s1 center">
                            <input id="search_query_sign" list="signs" name="search_query_sign" type="text" value="=">
                        </div>
                        <div class="col s4">
                            <input placeholder="color?" id="search_query_value" list="colors" name="search_query_value" type="text">
                        </div>
                        <div class="col s3">
                            <input type="submit" class="btn" style="padding: 0 30px">
                        </div>
                    </div>
                    <div class="row center">
                        <div class="col s4">
                            <input placeholder="" id="search_query_columns2" list="columns" name="search_query_columns2" type="text">
                        </div>
                        <div class="col s1 center">
                            <input id="search_query_sign2" list="signs" name="search_query_sign2" type="text" value="">
                        </div>
                        <div class="col s4">
                            <input placeholder="" id="search_query_value2" name="search_query_value2" type="text">
                        </div>
                        <div class="col s3">

                        </div>
                    </div>
                    <div class="row center">
                        <div class="col s4">
                            <input placeholder="" id="search_query_columns3" list="columns" name="search_query_columns3" type="text">
                        </div>
                        <div class="col s1 center">
                            <input id="search_query_sign3" list="signs" name="search_query_sign3" type="text" value="">
                        </div>
                        <div class="col s4">
                            <input placeholder="" id="search_query_value3" name="search_query_value3" type="text">
                        </div>
                        <div class="col s3">

                        </div>
                    </div>
                </form>
            </div>
            <div class="modal-fixed-footer blue">
                <a onclick="closeModal('advSearchByColor');" class="modal-close">
                    <h5 style="padding: 8px;border: 1px solid #2196F3;margin: 10px 0 0 0;">Close</h5>
                </a>
            </div>
        </div>
<!-- ============================ Modal Structure for advSearchBySupplierPO ======================================== -->
        <div id="advSearch7" class="modal" style="border: 1px solid #bbb">
            <div class="modal-content white" style="height: auto">
                <h5 style="text-align: center">Advance Search Supplier & PO</h5>
                <form method="post">
                    <div class="row center">
                        <div class="col s4">
                            <input placeholder="search column" id="search_query_columns" name="search_query_columns" type="text" value="Supplier">
                        </div>
                        <div class="col s1 center">
                            <input id="search_query_sign" list="signs" name="search_query_sign" type="text" value="LIKE">
                        </div>
                        <div class="col s4">
                            <input placeholder="supplier?" list="suppliers" id="search_query_value" name="search_query_value" type="text">
                        </div>
                        <div class="col s3">
                            <input type="submit" class="btn" style="padding: 0 30px">
                        </div>
                    </div>
                    <div class="row center">
                        <div class="col s4">
                            <input placeholder="search column2" id="search_query_columns2" list="columns" name="search_query_columns2" type="text" value="PO">
                        </div>
                        <div class="col s1 center">
                            <input id="search_query_sign2" list="signs" name="search_query_sign2" type="text" value="=">
                        </div>
                        <div class="col s4">
                            <input placeholder="PO number?" id="search_query_value2" name="search_query_value2" type="text">
                        </div>
                        <div class="col s3">

                        </div>
                    </div>
                    <div class="row center">
                        <div class="col s4">
                            <input placeholder="" id="search_query_columns3" list="columns" name="search_query_columns3" type="text">
                        </div>
                        <div class="col s1 center">
                            <input id="search_query_sign3" list="signs" name="search_query_sign3" type="text" value="">
                        </div>
                        <div class="col s4">
                            <input placeholder="" id="search_query_value3" name="search_query_value3" type="text">
                        </div>
                        <div class="col s3">

                        </div>
                    </div>
                </form>
            </div>
            <div class="modal-fixed-footer blue">
                <a onclick="closeModal('advSearch7');" class="modal-close">
                    <h5 style="padding: 8px;border: 1px solid #2196F3;margin: 10px 0 0 0;">Close</h5>
                </a>
            </div>
        </div>
<!-- ============================ Modal Structure for advSearchBySupplierPOAuthEmail =============================== -->
        <div id="advSearch8" class="modal" style="border: 1px solid #bbb">
            <div class="modal-content white" style="height: auto">
                <h5 style="text-align: center">Advance Search Supplier + PO + AuthEmail not Blank</h5>
                <form method="post">
                    <div class="row center">
                        <div class="col s4">
                            <input placeholder="search column" id="search_query_columns" name="search_query_columns" type="text" value="Supplier">
                        </div>
                        <div class="col s1 center">
                            <input id="search_query_sign" list="signs" name="search_query_sign" type="text" value="LIKE">
                        </div>
                        <div class="col s4">
                            <input placeholder="supplier?" list="suppliers" id="search_query_value" name="search_query_value" type="text">
                        </div>
                        <div class="col s3">
                            <input type="submit" class="btn" style="padding: 0 30px">
                        </div>
                    </div>
                    <div class="row center">
                        <div class="col s4">
                            <input placeholder="search column2" id="search_query_columns2" list="columns" name="search_query_columns2" type="text" value="PO">
                        </div>
                        <div class="col s1 center">
                            <input id="search_query_sign2" list="signs" name="search_query_sign2" type="text" value="=">
                        </div>
                        <div class="col s4">
                            <input placeholder="PO number?" id="search_query_value2" name="search_query_value2" type="text">
                        </div>
                        <div class="col s3">

                        </div>
                    </div>
                    <div class="row center">
                        <div class="col s4">
                            <input placeholder="search column3" id="search_query_columns3" list="columns" name="search_query_columns3" type="text" value="AuthEmail">
                        </div>
                        <div class="col s1 center">
                            <input id="search_query_sign3" list="signs" name="search_query_sign3" type="text" value="!=">
                        </div>
                        <div class="col s4">
                            <input placeholder="search value3" id="search_query_value3" name="search_query_value3" type="text" value="null">
                        </div>
                        <div class="col s3">

                        </div>
                    </div>
                </form>
            </div>
            <div class="modal-fixed-footer blue">
                <a onclick="closeModal('advSearch8');" class="modal-close">
                    <h5 style="padding: 8px;border: 1px solid #2196F3;margin: 10px 0 0 0;">Close</h5>
                </a>
            </div>
        </div>
<!-- ================================ Modal Structure for Advance Search Supplier + Status not blank =============== -->
        <div id="advSearch9" class="modal" style="border: 1px solid #bbb">
            <div class="modal-content white" style="height: auto">
                <h5 style="text-align: center">Advance Search Supplier + Status not blank</h5>
                <form method="post">
                    <div class="row center">
                        <div class="col s4">
                            <input placeholder="search column" id="search_query_columns" name="search_query_columns" type="text" value="Supplier">
                        </div>
                        <div class="col s1 center">
                            <input id="search_query_sign" list="signs" name="search_query_sign" type="text" value="LIKE">
                        </div>
                        <div class="col s4">
                            <input placeholder="supplier?" list="suppliers" id="search_query_value" name="search_query_value" type="text">
                        </div>
                        <div class="col s3">
                            <input type="submit" class="btn" style="padding: 0 30px">
                        </div>
                    </div>
                    <div class="row center">
                        <div class="col s4">
                            <input placeholder="search column2" id="search_query_columns2" list="columns" name="search_query_columns2" type="text" value="Status">
                        </div>
                        <div class="col s1 center">
                            <input id="search_query_sign2" list="signs" name="search_query_sign2" type="text" value="!=">
                        </div>
                        <div class="col s4">
                            <input placeholder="search value2" id="search_query_value2" name="search_query_value2" type="text" value="null">
                        </div>
                        <div class="col s3">

                        </div>
                    </div>
                    <div class="row center">
                        <div class="col s4">
                            <input placeholder="" id="search_query_columns3" list="columns" name="search_query_columns3" type="text">
                        </div>
                        <div class="col s1 center">
                            <input id="search_query_sign3" list="signs" name="search_query_sign3" type="text" value="">
                        </div>
                        <div class="col s4">
                            <input placeholder="" id="search_query_value3" name="search_query_value3" type="text">
                        </div>
                        <div class="col s3">

                        </div>
                    </div>
                </form>
            </div>
            <div class="modal-fixed-footer blue">
                <a onclick="closeModal('advSearch9');" class="modal-close">
                    <h5 style="padding: 8px;border: 1px solid #2196F3;margin: 10px 0 0 0;">Close</h5>
                </a>
            </div>
        </div>
    </nav>
<!-- ============================ Search input ===================================================================== -->
    ${commentOnSearch}<form class="col s2 no-padding" method="post">
        <label for="search_query"></label>
        <input placeholder="search" id="search_query" name="search_query" type="search">
    </form>${commentOffSearch}
<!-- ============================ 2nd row for Tab Header =========================================================== -->
    <aside class="row">
        <table style="border-left: 6px solid transparent; font-size: 0,85em">
            <tbody>
            <tr>
${tableHeader}                <td class="IM-details"></td>
            </tr>
            </tbody>
        </table>
    </aside>
</header>