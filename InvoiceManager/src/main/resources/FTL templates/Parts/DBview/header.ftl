<#-- VARIABLES for replecaMap:
        ${filterList} -- Im CFG
            |-- Header_filterList.ftl
        ${menu1} -- IM-menu-active
        ${menu2}
        ${menu3}
        ${menu4}
        ${menu5}
        ${viewTitle}
        ${paginationOff1} - turns off while pagination
        ${paginationOff2} - turns off while pagination
        ${pagePreviousOff1} - turns off pagination buttons - previous
        ${pagePreviousOff2} - turns off pagination buttons - previous
        ${previous}
        ${pageNr}
        ${next}
        &{tableHeader} -- InvoiceMetaData
            | -- Header_tableHeader.ftl
-->

<header class="row">
    <nav class="col s3">
        <!-- =========================================== SideNav -->
        <ul class="IM-sidenav">
            <li><a href="javascript:void(0)" onclick="w3_close()">Close &times;</a></li>
         <#--<li><a href="/NewFilter">New filter</a></li>-->
            <li><a href="#">Super dluga nazwa ciekawe czy sie zmiesci</a></li>
${filterList}        </ul>
        <!-- ========================================== MainManu -->
        <ul>
            <li><a href="/DB/1"><i class="fa fa-home${menu1}"></i></a></li>
            <li><a href="/Settings"><i class="fa fa-cog${menu2}"></i></a></li>
            <li onclick="w3_open()"><a href="#"><i class="fa fa-filter${menu3}"></i></a></li>
        <#--<li><a href="/Sqlquery"><i class="fa fa-database${menu4}"></i></a></li>-->
            <li><a href="/Reports"><i class="fa fa-bar-chart${menu5}"></i></a></li>
        </ul>
    </nav>
    <!-- ======================================== View Title style="padding-top: 5px" -->
    <div class="col s2 center">
        <a href="#"><strong class="waves-effect">${viewTitle}</strong></a>
    </div>
    <!-- ======================================== Pagination -->
    <nav class="col s2">
        <ul id="topPagination">
            ${paginationOff1}<li class="waves-effect">${pagePreviousOff1}<a href="${rout}${previous}">${pagePreviousOff2}<i class="fa fa-angle-double-left"></i>${pagePreviousOff1}</a>${pagePreviousOff2}</li>
            <li class="waves-effect"><a>${pageNr}</a></li>
            <li class="waves-effect"><a href="${rout}${next}"><i class="fa fa-angle-double-right"></i></a></li>${paginationOff2}
        </ul>
    </nav>
    <!-- ======================================= Search Info -->
    <nav class="col s3">
        <!-- Modal Trigger   -->
        <ul style="float: right">
            <li><a onclick="openModal('searchHelp');"><i class="fa fa-search-plus"></i></a></li>
        </ul>
        <!-- Modal Structure -->
        <div id="searchHelp" class="modal" style="border-top: 1px solid #bbb">
            <div class="modal-content white" style="height: auto">
                <h4>Advance Search</h4>
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
                    <datalist id="columns">
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
                        <option value="ProcessStage">
                    </datalist>
                    <datalist id="signs">
                        <option value="=">
                        <option value=">">
                        <option value=">=">
                        <option value="<">
                        <option value="<=">
                    </datalist>
                </form>
            </div>
            <div class="modal-fixed-footer blue">
                <a onclick="closeModal('searchHelp');" class="modal-close">
                    <h5 style="padding: 8px;border: 1px solid #2196F3;margin: 10px 0 0 0;">Close</h5>
                </a>
            </div>
        </div>
    </nav>
    <!-- ==================================== Search input  -->
    <form class="col s2 no-padding" method="post">
        <label for="search_query"></label>
        <input placeholder="search" id="search_query" name="search_query" type="search">
    </form>
    <!-- ===================================== 2nd row for Tab Header ================================================== -->
    <aside class="row">
        <table>
            <tbody>
            <tr>
${tableHeader}                <td class="IM-details"></td>
            </tr>
            </tbody>
        </table>
    </aside>
</header>