package hiperheuristica;

import java.util.Iterator;

/**
 * Contenedor para las piezas a acomodar en una heuristica.
 *
 * @author Marcel
 */
public class PieceContainer extends Figure implements Iterable<Piece> {

  private final PieceList pieces;

  public PieceContainer(int width, int height) {
    super(new Point[]{
      new Point(0, 0),
      new Point(width, 0),
      new Point(0, height),
      new Point(width, height)
    });

    this.pieces = new PieceList();
  }

  /**
   * Puts a piece into this PieceContainer
   *
   * @param pieza to add.
   */
  public void putPiece(Piece pieza) {
    this.pieces.add(pieza);
  }

  /**
   * Removes a piece from this PieceContainer.
   *
   * @param piece
   */
  public void removePiece(Piece piece) {
    this.pieces.remove(piece);
  }

  /**
   * Gets the unused area in this PieceContainer.
   *
   * @return the free area
   */
  public int getFreeArea() {
    return this.getArea() - this.getUsedArea();
  }

  /**
   * Gets the area used up by all pieces in this PieceContainer.
   *
   * @return area used up by pieces in this PieceContainer.
   */
  public int getUsedArea() {
    /**
     * NOTA: Puede que sea util utilizar un cache para acelerar este calculo.
     */
    return this.pieces.piecesArea();
  }

  /**
   * TODO: Needs testings, it is a high risk method. Dado un objeto (con sus
   * piezas ya colocadas), indica cuál es la distancia vertical que una pieza
   * candidata puede desplazarse verticalmente hacia abajo hasta topar con otra
   * pieza o con la base del objeto.
   *
   * @param piece to determine its bottom bound within this PieceContainer
   * @return distance to the bottom bound within this PieceContainer for a piece.
   */
  public int distanceToBottBound(Piece piece) {
    assert (piece != null);
    assert (piece.getBottBound() >= this.getBottBound());

    // get biggest maxY
    int bottomBounds = 0;
    for (Piece _piece : this.pieces) {
      if (_piece.intersectsOnXAxis(piece)
              && _piece.getTopBound() > bottomBounds) {
        bottomBounds = _piece.getTopBound();
      }
    }

    return piece.getBottBound() - bottomBounds;
  }

  /**
   * Dado un objeto (con sus piezas ya colocadas), indica cuál es la distincia 
   * horizontal que una pieza candidata puede desplazarse verticalmente hacia la
   * izquierda hasta topar con otra pieza o con la base del objeto.
   *
   * @param piece to determine its left bound within this PieceContainer
   * @return the distance to the left bound within this PieceContainer for a piece.
   */
  public int distanceToLeftBound(Piece piece) {
    assert (piece != null);
    assert (piece.getLeftBound() > this.getLeftBound());

    // get biggest maxY
    int leftBounds = 0;
    for (Piece other : this.pieces) {
      if (other.intersectsOnYAxis(piece)
              && other.getRightBound() > leftBounds) {
        leftBounds = other.getRightBound();
      }
    }

    return piece.getLeftBound() - leftBounds;
  }

  /**
   * TODO: Test this method, it is high risk method. Determines if a figure is
   * within the bounds of this PieceContainer and does not overlap with any Figures
   * already in this PieceContainer
   *
   * @param figure to check
   * @returns true if the figure is within the bounds of this instance and does
   * not overlap with other figures in this instance.
   */
  @Override
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
   * @return true if it intersects with this PieceContainer's bounds or a piece
   * within this PieceContainer, false otherwise.
   */
  @Override
  public boolean intersectsWith(Figure figure) {
    assert (figure != null);
    if (figure.intersectsWith(this) && !super.isWithinBounds(figure)) {
      return true;
    }

    for (Piece piece : this.pieces) {
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
  public PieceContainer getCopy() {
    PieceContainer copy = new PieceContainer(this.getWidth(), this.getHeight());
    for (Piece piece : this.pieces) {
      copy.putPiece(piece.getCopy());
    }

    return copy;
  }

  @Override
  public Iterator<Piece> iterator() {
    return this.pieces.iterator();
  }
}