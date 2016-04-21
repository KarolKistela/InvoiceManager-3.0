<#-- vars for freeMarker
backGroundColor - #rrggbb
IMcolumnClasses - each column has own css class IM-columnName, with width: columnWidth% parameter
-->
    <style>
        body {
            font-family: "Roboto", sans-serif;
            font-size: 15px;
        }
        .IM-menu {
            margin: 0 0 0 15px;
            color: #aaa;
            font-size: 1.5em;
        }
        .IM-menu:hover{
            color: #000;
        }
        .IM-menu-active{
            color: #000;
        }
        .IM-topBar{
            position:fixed;
            width:100%;
            z-index:1;
            top:0;
            border-bottom: 2px solid #555;
        }
        .IM-sidenav{
            display:none;
            margin-top: 70px;
            line-height: 32px;
            height: 280px
        }
        .IM-search {
            margin-top: 0;
        }
        .IM-search input{
            height: 2rem;
            margin: 0 0 0 0;
        }
        ul li .fa:hover {
            color: #000;
        }
        ul li .fa {
            color: #aaa ;
        }
        .disabled ul li:hover{
            color: #000;
        }
        .IM-container{
            padding:0.01em 16px;
            content:"";
            display:table;
            clear:both;
            padding-top: 80px;
            width: 100%;
            background-color: ${backGroundColor};
        }
        .IM-tooltip {
            padding-left: 0px;
            margin: 0 0px;
        }
        .IM-2x {
            font-size: 1.5em;
            padding: 0 5% 0 5%;
        }
${IMcolumnClasses}    </style>
