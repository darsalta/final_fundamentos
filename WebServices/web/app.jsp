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
                            width: 720,
                            height: 369
                        });

                        var layer = new Kinetic.Layer();
                        var drawRect = function(x1, y1, x2, y2, _layer) {
                            //alert('drawRect:' + x1 + ', ' + y1 + ', ' + x2 + ', ' + y2);
                            var rect = new Kinetic.Rect({
                                x: Math.min(x1, x2),
                                y: Math.min(y1, y2),
                                width: Math.abs(x1 - x2),
                                height: Math.abs(y1 - y2),
                                fill: 'green',
                                opacity: 0.5,
                                stroke: 'blue',
                                strokeWidth: 2,
                                draggable: true
                            });

                            rect.on('mouseover', function() {
                                document.body.style.cursor = 'pointer';
                            });

                            rect.on('mouseout', function() {
                                document.body.style.cursor = 'default';
                            });

                            // add the shape to the layer                            
                            _layer.add(rect);                            

                            // add the layer to the stage
                            stage.add(layer);
                        };

                        var drawPlacing = function(pieces, _layer) {
                            alert('drawing ' + pieces.length + ' pieces');

                            for (var i = 0; i < pieces.length; i++) {
                                var vertices = pieces[i].vertices;
                                var x1 = vertices[0].x;
                                var x2 = vertices[1].x;
                                if (x1 === x2) {
                                    x2 = vertices[2].x;
                                }

                                var y1 = vertices[0].y;
                                var y2 = vertices[1].y;
                                if (y1 === y2) {
                                    y2 = vertices[2].y;
                                }

                                drawRect(x1, y1, x2, y2, _layer);
                            }
                        };

                        function addList(rango, cantidad) {
                            document.getElementById('lista').value += rango;
                            document.getElementById('lista').value += ",";
                            document.getElementById('lista').value += cantidad;
                            document.getElementById('lista').value += "\n";
                            cleanInputs();
                        }

                        function cleanInputs() {
                            document.getElementById('rango').value = "";
                            document.getElementById('cantidad').value = "";
                            layer.removeChildren();
                        }

                        function enviarData(datos) {
                            cleanInputs();
                            datos = datos.split('\n');
                            var layerWidth = 700;
                            var layerHeight = 400;
                            var containerHeight = layerHeight;// layerWidth;// / 2;
                            var containerWidth = layerWidth; //layerHeight;// / 4;
                            var pieceList = new hiper.PieceList();
                            for (var i = 0; i < datos.length; i++) {
                                if (datos[i] === "") {
                                    continue;
                                }

                                var tokens = datos[i].split(',');
                                var radius = parseInt(tokens[0]);
                                var cantidad = parseInt(tokens[1]);
                                var width = radius * 2;
                                var height = radius * 2;
                                for (var j = 0; j < cantidad; j++) {
                                    pieceList.addPiece(
                                            hiper.Point.At(0, 0),
                                            hiper.Point.At(0, width),
                                            hiper.Point.At(width, height),
                                            hiper.Point.At(0, height));
                                }
                            }

                            ws.CallSetProblemConfig(
                                    pieceList,
                                    containerWidth,
                                    containerHeight,
                                    function(arg) {
                                        ws.CallGetBestFit(function(arg) {
                                            //alert(JSON.stringify(arg));
                                            drawPlacing(arg.pieces, layer);
                                        });
                                    });
                        }

                    </script>

                </article>

                <aside>
                    <h3>Configuracion:</h3>
                    Radio de alcance en metros: <input type="number" min="20" id="rango">
                    Cantidad Access Points: <input type="number" min="1" id="cantidad">
                    <input type="button" 
                           onclick="addList(document.getElementById('rango').value, document.getElementById('cantidad').value);" value="Agregar"/>
                    <p> </p>
                    Rango,Cantidad APs:
                    <textarea id="lista" cols ="23" rows="6"></textarea>
                    <input type="button" onclick="enviarData(document.getElementById('lista').value);" value="Calcular"/> 
                </aside>

            </div> <!-- #main -->


        </div> <!-- #main-container -->

        <div class="footer-container">
            <footer class="wrapper">
                <h3>HyperTech</h3>
            </footer>
        </div>

        <script src="//ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.js"></script>
        <script>window.jQuery || document.write('<script src="js/vendor/jquery-1.9.1.js"><\/script>')</script>

        <script src="js/main.js"></script>

        <script>
                        var _gaq = [['_setAccount', 'UA-XXXXX-X'], ['_trackPageview']];
                        (function(d, t) {
                            var g = d.createElement(t), s = d.getElementsByTagName(t)[0];
                            g.src = ('https:' == location.protocol ? '//ssl' : '//www') + '.google-analytics.com/ga.js';
                            s.parentNode.insertBefore(g, s)
                        }(document, 'script'));
        </script>
    </body>
</html>

