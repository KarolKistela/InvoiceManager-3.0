<#-- ======================================= template for SaveView ================================================= -->
<#-- VARIABLES for replecaMap:
        ${Style}    Styl.class
            |-- Style.ftl
                | -- Style_tableHeader.ftl - probably not nessesary in this case :)
        ${Header}   Heeader.class
            | -- Header.ftl
                | -- Header_FilterList.ftl
        ${SettingsInputForm}
            | -- SettingsInputForm.ftl
-->
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
<!-- ==========================================|__TopBar__|========================================================= -->
${Header}
<!-- ==========================================|_Settings_|========================================================= -->
${SettingsInputForm}
<!-- ==========================================|__Footer__|========================================================= -->
${Footer}

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