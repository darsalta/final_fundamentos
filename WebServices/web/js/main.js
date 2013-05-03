// Requires: jQuery-2.0.0.js
// Requires: webservices.js
// Requires: hiperheuristicas.js
// Requires: kinetic-v4.4.3.min.js
function drawRect(x1, y1, x2, y2, _layer) {
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

  // Redraw the layer
  _layer.draw();
}
;

function drawPlacing(pieces, _layer) {
  _layer.removeChildren();
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
}


function addList(rango, cantidad) {
  $('#lista').append(rango + 'x' + cantidad + "\n");
  cleanInputs();
}

function cleanInputs() {
  $('#rango').prop('value', '');
  $('#cantidad').prop('value', '');
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

    var tokens = datos[i].split('x');
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
              drawPlacing(arg.pieces, layer);
            });
          });
}
