/// Requires: jquery-2.0.0.js
/// Requires: json2.js
/// Requires: heuristicas.js

(function() {
  var callService = function(uri, data, type, successFunction) {
    $.ajax({
      'url': uri,
      'data': data,
      'type': type,
      'contentType': "application/json",
      'success': function(response) {
        successFunction(response);
      }
    });
  };

  var setProblemConfig = function(pieces, width, height, callback) {
    var piecesJson = pieces.toJson();
    var dimensions = '[' + width + ',' + height + ']';
    if (callback === null) {
      callback = function() {
      };
    }

    ws.Call('/mapping/resources/mapping?vertices=' + piecesJson + '&dimensions=' + dimensions,
            '{}',
            'PUT',
            function(arg) {
              callback(arg);
            });
  };

  var getPieces = function(callback) {
    ws.Call('/mapping/resources/mapping',
            '{}',
            'GET',
            function(arg) {
              var pieces = hiper.PieceList.fromJsonPieces(arg);
              callback(pieces);
            });
  };

  ws = {};
  ws.Call = callService;
  ws.CallSetProblemConfig = setProblemConfig;
  ws.CallGetBestFit = getPieces;
})();

(function() {
  var assert = function(value, msg) {
    if (value === false) {
      alert("failure: " + msg);
    }
  };

  var testCallPutConfiguration = function() {
    $("body").append('<br/> testCallPutConfiguration: A success message must be written below.');
    var pieceJson = '[ { vertices: [ {"x":1,"y":1},{"x":1,"y":3},{"x":3,"y":3},{"x":3,"y":1} ] } ]';
    var dimensions = '[10,10]';
    ws.Call('/mapping/resources/mapping?vertices=' + pieceJson + '&dimensions=' + dimensions,
            '{}',
            'PUT',
            function(arg) {
              callGetVertices();
            });
  };

  var callGetVertices = function() {
    ws.Call('/mapping/resources/mapping',
            '{}',
            'GET',
            function(arg) {
              $("body").append('<br/> testCallPutConfiguration: Success: ' + JSON.stringify(arg));
            });
  };

  var testWebServiceAbstractions = function() {
    // Arrange
    var pieceList = new hiper.PieceList();
    pieceList.addPiece(hiper.Point.At(0, 0), hiper.Point.At(0, 2),
            hiper.Point.At(2, 2), hiper.Point.At(2, 0));
    var width = 10;
    var height = 10;

    // Act
    $('body').append('<br/> testWebServiceAbstractions: A success message should be written below.');
    ws.CallSetProblemConfig(pieceList, width, height, function(arg) {
      ws.CallGetBestFit(function(_arg) {
        // Assert        
        assert(_arg !== null && _arg.pieces !== null,
                "testWebServiceAbstractions: It does not have pieces. <"
                + JSON.stringify(_arg) + ">");
        assert(_arg.pieces[0].vertices !== null,
                "testWebServiceAbstractions: pieces[0].vertices does not have vertices.");
        $('body').append('<br/> testWebServiceAbstractions: Success: ' + JSON.stringify(_arg));
      });
    });
  };

  ws.Test = function() {
    testCallPutConfiguration();
    testWebServiceAbstractions();
  };
})();