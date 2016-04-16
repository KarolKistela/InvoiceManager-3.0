<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Invoice Manager 3.0</title>
    <link type="text/css" rel="stylesheet" href="/Materialize/css/materialize.css" media="screen,projection"/>
    <link type="text/css" rel="stylesheet" href="/w3css/w3.css" media="screen,projection"/>
    <link rel="stylesheet" href="/Materialize/font-awesome-4.5.0/css/font-awesome.min.css">
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <script type="text/javascript" src="/jQuery/jquery-2.2.3.min.js"></script>
    <script type="text/javascript" src="/Materialize/js/materialize.min.js"></script>

<!-- FreeMarker generated part of code: template @ /HTML Parts/style.ftl, Model.HTMLtemplateEngine.getStyle -->
${style}
<!-- /FreeMarker generated part of code -->

</head>
<body>

<!-- FreeMarker generated part of code: template @ /HTML Parts/header2rowed.ftl, getHeader2rowedrowed -->
<header>
${header2rowed}
</header>
<!-- /FreeMarker generated part of code -->

<!--Footer
 TODO: footer-->
<script> <#-- TODO: clean this s..t up, but later - low priority -->
    function w3_open() {
        document.getElementsByClassName("w3-sidenav")[0].style.display = "block";
        document.getElementsByClassName("w3-overlay")[0].style.display = "block";
    }
    function w3_close() {
        document.getElementsByClassName("w3-sidenav")[0].style.display = "none";
        document.getElementsByClassName("w3-overlay")[0].style.display = "none";
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
    $(document).ready(function(){
        $('.tabs-wrapper .row').pushpin({ top: $('.tabs-wrapper').offset().top });
    });
    $(document).ready(function(){
        $('.tooltipped').tooltip({delay: 50});
    });
    $(document).ready(function(){
        // the "href" attribute of .modal-trigger must specify the modal ID that wants to be triggered
        $('.modal-trigger').leanModal();
    });
</script>

</body>
</html>