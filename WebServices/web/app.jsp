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

        <script src="js/vendor/modernizr-2.6.2-respond-1.1.0.min.js"></script>
        
    </head>
    <body>
        <!--[if lt IE 7]>
            <p class="chromeframe">You are using an <strong>outdated</strong> browser. Please <a href="http://browsehappy.com/">upgrade your browser</a> or <a href="http://www.google.com/chromeframe/?redirect=true">activate Google Chrome Frame</a> to improve your experience.</p>
        <![endif]-->

        <div class="header-container">
            <header class="wrapper clearfix">
                <h1 class="title">Distribucion de Access Points</h1>
                <!--nav>
                    <ul>
                        <li><a href="#">nav ul li a</a></li>
                        <li><a href="#">nav ul li a</a></li>
                        <li><a href="#">nav ul li a</a></li>
                    </ul>
                </nav-->
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
                    <script src="http://d3lp1msu2r81bx.cloudfront.net/kjs/js/lib/kinetic-v4.4.3.min.js"></script>
                    <script defer="defer">
                    var stage = new Kinetic.Stage({
                      container: 'container',
                      width: 700,
                      height: 400
                    });

                    var layer = new Kinetic.Layer();

                    var rect = new Kinetic.Rect({
                      x: 239,
                      y: 75,
                      width: 100,
                      height: 50,
                      fill: 'green',
                      stroke: 'black',
                      strokeWidth: 4,
                      draggable: true
                    });

                    rect.on('mouseover', function(){
                      document.body.style.cursor = 'pointer'  
                    });

                    rect.on('mouseout', function(){
                      document.body.style.cursor = 'default'  
                    });

                    // add the shape to the layer
                    layer.add(rect);

                    // add the layer to the stage
                    stage.add(layer);

                    function addList(rango, cantidad){
                        document.getElementById('lista').value += rango;
                        document.getElementById('lista').value += ",";
                        document.getElementById('lista').value += cantidad;
                        document.getElementById('lista').value += "\n";
                        cleanInputs();
                    }
                    function cleanInputs(){
                        document.getElementById('rango').value = "";
                        document.getElementById('cantidad').value = "";
                    }

                    function enviarData(datos){
                        var ctx = canvas.getContext('2d');
                        ctx.beginPath();
                        ctx.rect(0,0,200,200)
                        ctx.fillStyle = 'rgb(10,30,160)';
                        ctx.fill();

                    }
                        
                   </script>

                </article>

                <aside>
                    <h3>Configuracion:</h3>
                    Rango en metros: <input type="number" min="20" id="rango">
                    Cantidad Access Points: <input type="number" min="1" id="cantidad">
                    <input type="button" 
                           onclick="addList(document.getElementById('rango').value, document.getElementById('cantidad').value);" value="Agregar"/>
                    <p> </p>
                    Rango,Cantidad APs:
                    <textarea id="lista" readonly="readonly" cols ="23" rows="6"></textarea>
                    <input type="button" onclick="enviarData(document.getElementById('lista').value);" value="Calcular"/> 
                </aside>

            </div> <!-- #main -->
            
            
            </div> <!-- #main-container -->

        <div class="footer-container">
            <footer class="wrapper">
                <h3>footer</h3>
            </footer>
        </div>

        <script src="//ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.js"></script>
        <script>window.jQuery || document.write('<script src="js/vendor/jquery-1.9.1.js"><\/script>')</script>

        <script src="js/main.js"></script>

        <script>
            var _gaq=[['_setAccount','UA-XXXXX-X'],['_trackPageview']];
            (function(d,t){var g=d.createElement(t),s=d.getElementsByTagName(t)[0];
            g.src=('https:'==location.protocol?'//ssl':'//www')+'.google-analytics.com/ga.js';
            s.parentNode.insertBefore(g,s)}(document,'script'));
        </script>
    </body>
</html>

