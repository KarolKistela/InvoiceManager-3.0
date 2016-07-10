<#-- =========================== template for DBview view with many records per page =============================== -->
<#-- VARIABLES for replecaMap:
        ${FINANCE_VIEW}
        ${Style}    Styl.class
            |-- Style.ftl
                | -- Style_tableHeader.ftl
        ${Header}   Heeader.class
            | -- Header.ftl
                | -- Header_FilterList.ftl
                | -- Header_tableHeader.ftl
        ${DBTable}  DBTable.class
            | -- DBTable.ftl
                | -- DBTable_row.ftl
-->
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Invoices Manager 3.0${FINANCE_VIEW}</title>
    <link type="text/css" rel="stylesheet" href="/css/Materialize/css/materialize.css" media="screen,projection"/>
    <link rel="stylesheet" href="/css/Materialize/font-awesome-4.5.0/css/font-awesome.css">
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <script type="text/javascript" src="/css/jQuery/jquery-2.2.3.min.js"></script>
    <script type="text/javascript" src="/css/Materialize/js/materialize.js"></script>
${Style}
</head>
<body>
<!-- ============================ TopBar       ===================================================================== -->
${Header}
<!-- ============================ InvoicesList ===================================================================== -->
<main class="row">
${DBTable}
<!-- ============================ Input Form ===================================================================== -->
</main>
${IDinputForm}
<!-- ============================ Footer       ===================================================================== -->
${Footer}

<#--<script>-->
    <#--function w3_open() {-->
        <#--document.getElementsByClassName("IM-sidenav")[0].style.display = "block";-->
        <#--document.getElementsByClassName("IM-overlay")[0].style.display = "block";-->
    <#--}-->
    <#--function w3_close() {-->
        <#--document.getElementsByClassName("IM-sidenav")[0].style.display = "none";-->
        <#--document.getElementsByClassName("IM-overlay")[0].style.display = "none";-->
    <#--}-->
    <#--function myAccFunc() {-->
        <#--var x = document.getElementById("demoAcc");-->
        <#--if (x.className.indexOf("w3-show") == -1) {-->
            <#--x.className += " w3-show";-->
            <#--x.previousElementSibling.className += " w3-green";-->
        <#--} else {-->
            <#--x.className = x.className.replace(" w3-show", "");-->
            <#--x.previousElementSibling.className =-->
                    <#--x.previousElementSibling.className.replace(" w3-green", "");-->
        <#--}-->
    <#--}-->
    <#--function myDropFunc() {-->
        <#--var x = document.getElementById("demoDrop");-->
        <#--if (x.className.indexOf("w3-show") == -1) {-->
            <#--x.className += " w3-show";-->
            <#--x.previousElementSibling.className += " w3-green";-->
        <#--} else {-->
            <#--x.className = x.className.replace(" w3-show", "");-->
            <#--x.previousElementSibling.className =-->
                    <#--x.previousElementSibling.className.replace(" w3-green", "");-->
        <#--}-->
    <#--}-->
    <#--$(document).ready(function(){-->
        <#--$('.collapsible').collapsible({-->
            <#--accordion : false // A setting that changes the collapsible behavior to expandable instead of the default accordion style-->
        <#--});-->
    <#--});-->
    <#--function myFunction(id) {-->
        <#--var x = document.getElementById(id);-->
        <#--if (x.className.indexOf("w3-show") == -1) {-->
            <#--x.className += " w3-show";-->
        <#--} else {-->
            <#--x.className = x.className.replace(" w3-show", "");-->
        <#--}-->
    <#--}-->
    <#--function popUpModal(id) {-->
        <#--var modalId = document.getElementById(id);-->
        <#--modalId.style.display='block';-->
        <#--setTimeout(function () { document.getElementById(id).style.display='none';}, 1500);-->
        <#--return false;-->
    <#--}-->
    <#--function openModal(id) {-->
        <#--var modalId = document.getElementById(id)-->
        <#--modalId.style.display='block';-->
    <#--}-->
    <#--function closeModal(id) {-->
        <#--var modalId = document.getElementById(id)-->
        <#--modalId.style.display='none';-->
    <#--}-->
    <#--$(document).ready(function(){-->
        <#--$('.tabs-wrapper .row').pushpin({ top: $('.tabs-wrapper').offset().top });-->
    <#--});-->
    <#--$(document).ready(function(){-->
        <#--$('.tooltipped').tooltip({delay: 50});-->
    <#--});-->
    <#--$(document).ready(function() {-->
        <#--Materialize.updateTextFields();-->
    <#--});-->
<#--</script>-->

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
    $('.datepicker').pickadate({
        format: 'yyyy-mm-dd',
        selectMonths: true, // Creates a dropdown to control month
        selectYears: 15 // Creates a dropdown of 15 years to control year
    });
</script>
</body>
</html>