<#-- VARIABLES for replecaMap:
        ${Style}    Styl.class
            |-- Style.ftl
                | -- Style_tableHeader.ftl - probably not nessesary in this case :)
        ${Header}   Heeader.class
            | -- Header.ftl
                | -- Header_FilterList.ftl
-->
<!-- template for DBview view with many records per page -->
<!DOCTYPE html>
<html lang="en" xmlns="http://www.w3.org/1999/html">
<head>
    <meta charset="UTF-8">
    <title>Invoices Manager Beta</title>
    <link type="text/css" rel="stylesheet" href="/css/Materialize/css/materialize.css" media="screen,projection"/>
    <link rel="stylesheet" href="/css/Materialize/font-awesome-4.5.0/css/font-awesome.css">
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <script type="text/javascript" src="/css/jQuery/jquery-2.2.3.min.js"></script>
    <script type="text/javascript" src="/css/Materialize/js/materialize.js"></script>
${Style}
</head>
<body>
<!-- =========================================== TopBar ============================================================ -->
${Header}
<!-- ============================ InvoicesList content for 2 rowed header ========================================== -->
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
                        <input  id="imExternalFolderPath" name="imExternalFolderPath" type="text">
                        <label for="imExternalFolderPath">Path to Folder with invoices files</label>
                    </div>
<!-- =============== Databse Path ================================================================================== -->
                    <div class="col s2 IM-SettingsLabels" style="padding-top: 25px">
                        <i class="fa fa-database fa-2x" aria-hidden="true"></i>
                    </div>
                    <div class="input-field col s4">
                        <input  id="imDBPath" type="text" name="imDBPath">
                        <label for="imDBPath">Path to InvoicesManager.db</label>
                    </div>
                </div>
                <div class="row">
<!-- =============== Rows Per Page ================================================================================= -->
                    <div class="col s1 IM-SettingsLabels" style="padding-top: 25px">
                        <i class="fa fa-list fa-2x" aria-hidden="true"></i>
                    </div>
                    <div class="input-field col s10">
                        <p class="range-field">
                            <input type="range" id="rowsPerPage" name="rowsPerPage" min="5" max="250" value="150"/>
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
                            <input list="browsers" name="orderBy" value="Firefox"/>
                                <datalist id="browsers">
                                    <option value="Internet Explorer">
                                    <option value="Firefox">
                                    <option value="Chrome">
                                    <option value="Opera">
                                    <option value="Safari">
                                </datalist>
                    </div>
                    <div class="col s1 IM-SettingsLabels" style="padding-top: 25px">
                        <i class="fa fa-sort fa-2x" aria-hidden="true"></i>
                    </div>
                    <div class="switch col s2" style="padding: 23px 0 0 23px; ">
                        <label>
                            Ascending
                            <input type="checkbox" name="orderByDirection" checked style="padding-top: 5px">
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
                            <input type="checkbox" name="duplicates" checked>
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
                        <input id="netId" type="text" name="netId" length="6">
                        <label for="netId">netid</label>
                    </div>
                    <div class="col s1 IM-SettingsLabels" style="padding-top: 25px">
                        <i class="fa fa-envelope-o fa-2x" aria-hidden="true"></i>
                    </div>
                    <div class="col s3">
                        <div class="input-field col s12">
                            <input id="email" type="email" name="userEmail" class="validate">
                            <label for="email" data-error="wrong" data-success="right">Email</label>
                        </div>
                    </div>
                    <div class="col s2 IM-SettingsLabels" style="padding-top: 23px">
                        <label style="font-size: 1.25em">Choose your color</label>
                    </div>
                    <div class="col s2">
                        <div class="input-field col s12" style="padding-top: 9px">
                            <input id="userColor" type="color" name="userColor">
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
<!-- ================================================== Footer ===================================================== -->
<footer class="page-footer blue">
    <div class="container">
        <div class="row">
            <div class="col l6 s12">
                <h5 class="white-text">Footer Content</h5>
                <p class="grey-text text-lighten-4">You can use rows and columns here to organize your footer content.</p>
            </div>
            <div class="col l4 offset-l2 s12">
                <h5 class="white-text">Links</h5>
                <ul>
                    <li><a class="grey-text text-lighten-3" href="#!">Link 1</a></li>
                    <li><a class="grey-text text-lighten-3" href="#!">Link 2</a></li>
                    <li><a class="grey-text text-lighten-3" href="#!">Link 3</a></li>
                    <li><a class="grey-text text-lighten-3" href="#!">Link 4</a></li>
                </ul>
            </div>
        </div>
    </div>
    <div class="footer-copyright">
        <div class="container">
            © 2014 Copyright Text
            <a class="grey-text text-lighten-4 right" href="#!">More Links</a>
        </div>
    </div>
</footer>
<!--TODO: footer-->

<script>
    function w3_open() {
        document.getElementsByClassName("IM-sidenav")[0].style.display = "block";
        document.getElementsByClassName("IM-overlay")[0].style.display = "block";
    }
    function w3_close() {
        document.getElementsByClassName("IM-sidenav")[0].style.display = "none";
        document.getElementsByClassName("IM-overlay")[0].style.display = "none";
    }

    function myAccFunc() {
        var x = document.getElementById("demoAcc");
        if (x.className.indexOf("w3-show") == -1) {
            x.className += " w3-show";
            x.previousElementSibling.className += " w3-green";
        } else {
            x.className = x.className.replace(" w3-show", "");
            x.previousElementSibling.className =
                    x.previousElementSibling.className.replace(" w3-green", "");
        }
    }

    function myDropFunc() {
        var x = document.getElementById("demoDrop");
        if (x.className.indexOf("w3-show") == -1) {
            x.className += " w3-show";
            x.previousElementSibling.className += " w3-green";
        } else {
            x.className = x.className.replace(" w3-show", "");
            x.previousElementSibling.className =
                    x.previousElementSibling.className.replace(" w3-green", "");
        }
    }
    $(document).ready(function(){
        $('.collapsible').collapsible({
            accordion : false // A setting that changes the collapsible behavior to expandable instead of the default accordion style
        });
    });

    function myFunction(id) {
        var x = document.getElementById(id);
        if (x.className.indexOf("w3-show") == -1) {
            x.className += " w3-show";
        } else {
            x.className = x.className.replace(" w3-show", "");
        }
    }

    function popUpModal(id) {
        var modalId = document.getElementById(id);
        modalId.style.display='block';
        setTimeout(function () { document.getElementById(id).style.display='none';}, 1500);
        return false;
    }

    function openModal(id) {
        var modalId = document.getElementById(id)
        modalId.style.display='block';
    }

    function closeModal(id) {
        var modalId = document.getElementById(id)
        modalId.style.display='none';

    }

    $(document).ready(function(){
        $('.tabs-wrapper .row').pushpin({ top: $('.tabs-wrapper').offset().top });
    });

    $(document).ready(function(){
        $('.tooltipped').tooltip({delay: 50});
    });
    $(document).ready(function() {
        Materialize.updateTextFields();
    });
    $(document).ready(function() {
        $('input#netId, textarea#textarea1').characterCounter();
    });
</script>

</body>
</html>