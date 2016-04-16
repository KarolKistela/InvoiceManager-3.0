<#-- vars for freeMarker
previous
pagination
next
IMmenuActive1 = IM-menu-active for active element
IMmenuActive2
IMmenuActive3
viewTitle

1 row header for settings, edit ...

-->

<!--TopBar  -->
<div class="row white valign-wrapper IM-topBar">
    <!--Left  1/2  -->
    <div class="col s2">
        <nav class="w3-sidenav grey darken-1 w3-animate-left IM-sidenav">
            <a href="javascript:void(0)" onclick="w3_close()"
               class="w3-closenav w3-large">Close &times;</a>
            <a href="/NewFilter">User Defined</a>
            <a href="/FilterView/1">Filter 1</a>
            <a href="/FilterView/2">Filter 2</a>
            <a href="/FilterView/3">Filter 3</a>
            <a href="/FilterView/4">Filter 4</a>
            <a href="/FilterView/5">Filter 5</a>
        </nav>
        <a href="/DB/1"><i class="fa fa-home IM-menu ${IMmenuActive1}"></i></a>
        <a href="/Settings"><i class="fa fa-cog IM-menu ${IMmenuActive2}"></i></a>
        <i class="w3-opennav fa fa-filter IM-menu ${IMmenuActive3}" onclick="w3_open()"></i>
    </div>
    <!--Left  2/2  -->
    <div class="col s2 center">
        <h4 class="waves-effect">${viewTitle}</h4>
    </div>
    <!--Center    -->
    <div class="col s4 center">
        <ul class="pagination" style="margin: 0 0 1px 0">
            <a href="${previous}"><li class="waves-effect"><i class="fa fa-angle-double-left"></i></li></a>
            <li class="waves-effect" style="vertical-align: middle; padding: 3px 20px 0 20px">${pagination}</li>
            <a href="${next}"><li class="waves-effect"><i class="fa fa-angle-double-right"></i></li></a>
        </ul>
    </div>
    <!--Right  1/2  -->
    <div class="col s2">
        <i class="fa fa-search right waves-effect IM-menu" style="font-size: 23px"></i>
    </div>
    <!--Right  1/2  -->
    <div class="col s2">
        <div class="input-field IM-search">
            <input placeholder="search ID" id="search_query" type="text" class="validate right">
            <label for="search_query"></label>
        </div>
    </div>
</div>