<#-- VARIABLES for replecaMap:
        ${filterList} -- Im CFG
            |-- Header_filterList.ftl
        ${menu1} -- IM-menu-active
        ${menu2}
        ${menu3}
        ${menu4}
        ${menu5}
        ${viewTitle}
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
            <li><a href="/NewFilter">New filter</a></li>
${filterList}        </ul>
        <!-- ========================================== MainManu -->
        <ul>
            <li><a href="/DB/1"><i class="fa fa-home${menu1}"></i></a></li>
            <li><a href="/Settings"><i class="fa fa-cog${menu2}"></i></a></li>
            <li onclick="w3_open()"><a href="#"><i class="fa fa-filter${menu3}"></i></a></li>
            <li><a href="/Sqlquery"><i class="fa fa-database${menu4}"></i></a></li>
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
            <li class="waves-effect"><a href="/DB/${previous}"><i class="fa fa-angle-double-left"></i></a></li>
            <li class="waves-effect"><a>${pageNr}</a></li>
            <li class="waves-effect"><a href="/DB/${next}"><i class="fa fa-angle-double-right"></i></a></li>
        </ul>
    </nav>
    <!-- ======================================= Search Info -->
    <nav class="col s3">
        <!-- Modal Trigger   -->
        <ul style="float: right">
            <li><a onclick="openModal('searchHelp');"><i class="fa fa-info"></i></a></li>
        </ul>
        <!-- Modal Structure -->
        <div id="searchHelp" class="modal">
            <div class="modal-content">
                <h4>Search Help</h4>
                <p>under construction</p>
            </div>
            <div class="modal-fixed-footer white">
                <a onclick="closeModal('searchHelp');" class="modal-close">
                    <h5>Close</h5>
                </a>
            </div>
        </div>
    </nav>
    <!-- ==================================== Search input  -->
    <form class="col s2 no-padding">
        <label for="search_query"></label>
        <input placeholder="search" id="search_query" type="search">
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