/// Requires: json2.js
(function() {
    function PieceList() {
        this.pieces = [];
        this.addPiece = function(botLeft, topLeft, botRight, topRight) {
            this.pieces.push(Piece(botLeft, topLeft, botRight, topRight));
        };

        this.toJson = function() {
            var json = "[ ";
            for (var i = 0; i < this.pieces.length; i++) {
                var piece = this.pieces[i];
                json += piece.toJson() + ", ";
            }

            json += " ]";

            return json;
        };
    }

    PieceList.fromJson = function(json) {
        var jsonPieces = JSON.parse(json);
        return PieceList.fromJsonPieces(jsonPieces);
    };

    PieceList.fromJsonPieces = function(jsonPieces) {
        var pieceList = new PieceList();
        for (var i = 0; i < jsonPieces.length; i++) {
            pieceList.pieces.push(
                    Piece.fromJsonVertices(jsonPieces[i].vertices));
        }

        return pieceList;
    };

    function Piece(botLeft, topLeft, botRight, topRight) {
        this.vertices = [botLeft, topLeft, botRight, topRight];

        this.toJson = function() {
            var json = '{ "vertices": [ ';
            for (var i = 0; i < this.vertices.length; i++) {
                var vertice = this.vertices[i];
                json += vertice.toJson();
                if (i < this.vertices.length - 1) {
                    json += ", ";
                }
            }
            json += " ] }";

            return json;
        };
    }

    Piece.fromJson = function(json) {
        var jsonPiece = JSON.parse(json);
        return Piece.fromJsonVertices(jsonPiece.vertices);
    };

    Piece.fromJsonVertices = function(jsonVertices) {
        var vertices = [];
        for (var i = 0; i < jsonVertices.length; i++) {
            var vertice = jsonVertices[i];
            vertices.push(Point.fromJsonCoords(vertice));
        }

        return new Piece(vertices[0], vertices[1], vertices[2], vertices[3]);
    };

    function Point(x, y) {
        this.x = x;
        this.y = y;

        this.toJson = function() {
            return '{ "x": ' + this.x + ', "y": ' + this.y + ' }';
        };
    }

    Point.fromJson = function(json) {
        var jsonCoords = JSON.parse(json);
        return Point.fromJsonCoords(jsonCoords);
    };

    Point.fromJsonCoords = function(jsonCoords) {
        return new Point(jsonCoords.x, jsonCoords.y);
    };
    
    Point.At = function(x, y) {
        return new Point(x, y);
    };

    hiper = {};
    hiper.Point = Point;
    hiper.PieceList = PieceList;
    hiper.Piece = Piece;
})();


(function() {
    var assert = function(value, msg) {
        if (value === false) {
            alert("failure: " + msg);
        }
    };

    var testPointFromJson = function() {
        // Arrange
        var json = '{ "x": 1, "y": 2 }';
        // Act
        var point = hiper.Point.fromJson(json);
        // Assert
        assert(point.x === 1 && point.y === 2, "testPointFromJson");
    };

    var testPointToJson = function() {
        // Arrange
        var point = new hiper.Point(1, 2);
        var expected = '{ "x": 1, "y": 2 }';
        // Act
        var actual = point.toJson();
        // Assert
        assert(expected === actual, 'testPointToJson');
    };

    var testPieceFromJson = function() {
        // Arrange
        var json = '{ "vertices" : [' +
                '{ "x": 1, "y": 1 }, ' +
                '{ "x": 2, "y": 2 }, ' +
                '{ "x": 3, "y": 3 }, ' +
                '{ "x": 4, "y": 4 }' +
                ']' +
                '}';

        // Act
        var piece = hiper.Piece.fromJson(json);

        // Assert
        assert(piece.vertices[0].x === 1 && piece.vertices[0].y === 1 &&
                piece.vertices[1].x === 2 && piece.vertices[1].y === 2 &&
                piece.vertices[2].x === 3 && piece.vertices[2].y === 3 &&
                piece.vertices[3].x === 4 && piece.vertices[3].y === 4,
                'testPieceFromJson');
    };

    var testPieceToJson = function() {
        // Arrange
        var piece = new hiper.Piece(new hiper.Point(1, 1),
                new hiper.Point(2, 2),
                new hiper.Point(3, 3),
                new hiper.Point(4, 4));
        var expected = '{ "vertices": [ ' +
                '{ "x": 1, "y": 1 }, ' +
                '{ "x": 2, "y": 2 }, ' +
                '{ "x": 3, "y": 3 }, ' +
                '{ "x": 4, "y": 4 } ' +
                '] ' +
                '}';
        // Act
        var actual = piece.toJson();
        // Assert
        assert(expected === actual,
                'testPieceToJson: \n<' + expected + '> !=\n <' + actual + '>');
    };

    var testPieceListFromJson = function() {
        // Arrange
        var json = '[ { "vertices": [ ' +
                '{ "x": 1, "y": 1 }, ' +
                '{ "x": 2, "y": 2 }, ' +
                '{ "x": 3, "y": 3 }, ' +
                '{ "x": 4, "y": 4 } ' +
                ']' +
                '}, ' +
                '{ "vertices": [ ' +
                '{ "x": 5, "y": 5 }, ' +
                '{ "x": 6, "y": 6 }, ' +
                '{ "x": 7, "y": 7 }, ' +
                '{ "x": 8, "y": 8 } ' +
                '] ' +
                '} ' +
                ']';
        // Act
        var list = hiper.PieceList.fromJson(json);

        // Assert
        assert(list.pieces[0].vertices[0].x === 1 && list.pieces[0].vertices[0].y === 1 &&
                list.pieces[0].vertices[1].x === 2 && list.pieces[0].vertices[1].y === 2 &&
                list.pieces[0].vertices[2].x === 3 && list.pieces[0].vertices[2].y === 3 &&
                list.pieces[0].vertices[3].x === 4 && list.pieces[0].vertices[3].y === 4 &&
                list.pieces[1].vertices[0].x === 5 && list.pieces[1].vertices[0].y === 5 &&
                list.pieces[1].vertices[1].x === 6 && list.pieces[1].vertices[1].y === 6 &&
                list.pieces[1].vertices[2].x === 7 && list.pieces[1].vertices[2].y === 7 &&
                list.pieces[1].vertices[3].x === 8 && list.pieces[1].vertices[3].y === 8);
    };

    var test = function() {
        testPointFromJson();
        testPointToJson();
        testPieceFromJson();
        testPieceToJson();
        testPieceListFromJson();
    };

    hiper.Test = test;
})();