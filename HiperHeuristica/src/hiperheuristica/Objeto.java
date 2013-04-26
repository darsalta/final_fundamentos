package hiperheuristica;

/**
 * TODO: Pending implementation.
 *
 * @author Marcel
 */
class Objeto extends Figure {

    PieceList pieces;

    public Objeto(int width, int height) {
        super(new Point[]{
            new Point(0, 0),
            new Point(width, 0),
            new Point(0, height),
            new Point(width, height)
        });

        this.pieces = new PieceList();
    }

    /**
     * Adds a piece to this Objeto
     *
     * @param pieza to add.
     */
    public void addPiece(Pieza pieza) {
        this.pieces.add(pieza);
    }

    /**
     * TODO: Pending implementation. TODO: What is removeCandidate?
     *
     * @param pieza
     */
    public void removeCandidate(Pieza pieza) {
        int x = 1 / 0;
    }

    /**
     * TODO: Pending implementation. TODO: What is addCandidate?
     *
     * @param pieza
     */
    public void addCandidate(Pieza pieza) {
        int x = 1 / 0;
    }

    /**
     * Gets the unused area in this Objeto.
     *
     * @return the free area
     */
    public int getFreeArea() {
        return this.getArea() - this.getUsedArea();
    }

    /**
     * Gets the area used up by all pieces in this Objeto.
     *
     * @return area used up by pieces in this Objeto.
     */
    public int getUsedArea() {
        return this.pieces.piecesArea();
    }

    /**
     * Dado un objeto (con sus piezas ya colocadas), indica cuál es la distancia
     * vertical que una pieza candidata puede desplazarse verticalmente hacia
     * abajo hasta topar con otra pieza o con la base del objeto.
     *
     * @param piece to determine its bottom bound within this Objeto
     * @return distance to the bottom bound within this Objeto for a piece.
     */
    public int distanceToBottBound(Pieza piece) {
        assert (piece != null);
        assert (piece.getBottBound() > this.getBottBound());

        // get biggest maxY
        int bottomBounds = 0;
        for (Pieza _piece : this.pieces) {
            if (_piece.intersectsOnXAxis(piece)
                    && _piece.getTopBound() > bottomBounds) {
                bottomBounds = _piece.getTopBound();
            }
        }

        return piece.getBottBound() - bottomBounds;
    }

    /**
     * Dado un objeto (con sus piezas ya colocadas), indica cuál es la distincia
     * horizontal que una pieza candidata puede desplazarse verticalmente hacia
     * la izquierda hasta topar con otra pieza o con la base del objeto.
     *
     * @param piece to determine its left bound within this Objeto
     * @return the distance to the left bound within this Objeto for a piece.
     */
    public int distanceToLeftBound(Pieza piece) {
        assert (piece != null);
        assert (piece.getLeftBound() > this.getLeftBound());

        // get biggest maxY
        int leftBounds = 0;
        for (Pieza _piece : this.pieces) {
            if (_piece.intersectsOnYAxis(piece)
                    && _piece.getRightBound() > leftBounds) {
                leftBounds = _piece.getRightBound();
            }
        }

        return piece.getLeftBound() - leftBounds;
    }

    @Override
    /**
     * TODO: Test this method, it is high risk method. Determines if a figure is
     * within the bounds of this Objeto and does not overlap with any Figures
     * already in this Objeto
     *
     * @param figure to check
     * @returns true if the figure is within the bounds of this instance and
     * does not overlap with other figures in this instance.
     */
    public boolean isWithinBounds(Figure figure) {
        assert (figure != null);

        return super.isWithinBounds(figure) && !this.intersectsWith(figure);
    }

    /**
     * TODO: Test this method, it is a high risk method. Dado un objeto (con sus
     * piezas ya colocadas), indica si una pieza candidata intersecta con los
     * límites del objeto o con alguna pieza ya colocada.
     *
     * @param figure to check for intersection.
     * @return true if it intersects with this Objeto's bounds or a piece within
     * this Objeto, false otherwise.
     */
    @Override
    public boolean intersectsWith(Figure figure) {
        assert (figure != null);
        if (figure.intersectsWith(this)) {
            return true;
        }

        for (Pieza piece : this.pieces) {
            if (figure.intersectsWith(piece)) {
                return true;
            }
        }

        return false;
    }

    /**
     * TODO: Needs testing, high risk method. Gets a copy of this instance. But
     * keep in mind that: this.getCopy().equals(this) == false
     *
     * @return A copy of this instance.
     */
    public Objeto getCopy() {
        Objeto copy = new Objeto(this.getWidth(), this.getHeight());
        for (Pieza piece : this.pieces) {
            copy.addPiece(piece.getCopy());
        }

        return copy;
    }
}