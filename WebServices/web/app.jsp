<%-- 
    Document   : app
    Created on : Apr 28, 2013, 12:16:59 PM
    Author     : Priscila Angulo
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<!--[if lt IE 7]>      <html class="no-js lt-ie9 lt-ie8 lt-ie7"> <![endif]-->
<!--[if IE 7]>         <html class="no-js lt-ie9 lt-ie8"> <![endif]-->
<!--[if IE 8]>         <html class="no-js lt-ie9"> <![endif]-->
<!--[if gt IE 8]><!--> <html class="no-js"> <!--<![endif]-->
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <title></title>
    <meta name="description" content="">
    <meta name="viewport" content="width=device-width">

    <link rel="stylesheet" href="css/normalize.min.css">
    <link rel="stylesheet" href="css/main.css">
    <script src="js/json2.js"></script>
    <script src="js/jquery-2.0.0.js"></script>
    <script src="js/hiperheuristicas.js"></script>
    <script src="js/webservices.js"></script>        
    <script src="js/vendor/modernizr-2.6.2-respond-1.1.0.min.js"></script>
    <script src="http://d3lp1msu2r81bx.cloudfront.net/kjs/js/lib/kinetic-v4.4.3.min.js"></script>
    <script src="js/main.js"></script>
  </head>
  <body>
    <!--[if lt IE 7]>
        <p class="chromeframe">You are using an <strong>outdated</strong> browser. Please <a href="http://browsehappy.com/">upgrade your browser</a> or <a href="http://www.google.com/chromeframe/?redirect=true">activate Google Chrome Frame</a> to improve your experience.</p>
    <![endif]-->

    <div class="header-container">
      <header class="wrapper clearfix">
        <h1 class="title">Distribucion de Access Points</h1>
      </header>
    </div>

    <div class="main-container">
      <div class="main wrapper clearfix">

        <article>
          <header>
            <h1>Mapa</h1>
            <p></p>
          </header>

          <div id="container" style="background: url(campus.gif) no-repeat; background-size: 100%; height:400px; width:700px;"></div>          
          <script defer="defer">
            var stage = new Kinetic.Stage({
              container: 'container',
              width: 720,
              height: 369
            });
            var layer = new Kinetic.Layer();
            // add the layer to the stage
            stage.add(layer);
          </script>
        </article>

        <aside>
          <h3> Configuracion: </h3>
          Radio
          de alcance en metros: <input type = "number" min = "20" id = "rango">
          Cantidad
          Access Points: <input type = "number" min = "1" id = "cantidad">
          <input type = "button"
                 onclick = "addList(document.getElementById('rango').value, document.getElementById('cantidad').value);"
                 value = "Agregar" />
          <p> </p>
          Alcance x Cantidad APs:
          <textarea id = "lista" cols = "23" rows = "6" > </textarea>
          <input type = "button" onclick = "enviarData(document.getElementById('lista').value);" value = "Calcular" />
        </aside>

      </div> <!-- #main -->


    </div> <!-- #main-container -->

    <div class = "footer-container" >
      <footer class = "wrapper" >
        <h3 > HyperTech </h3>
      </footer>
    </div>
  </body>
</html>

