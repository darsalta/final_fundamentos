package hiperheuristica;

import java.util.Iterator;

/**
 * TODO: Pending implementation.
 *
 * @author Marcel
 */
public class Container extends Figure implements Iterable<Piece> {

  PieceList pieces;

  public Container(int width, int height) {
    super(new Point[]{
      new Point(0, 0),
      new Point(width, 0),
      new Point(0, height),
      new Point(width, height)
    });

    this.pieces = new PieceList();
  }

  /**
   * Puts a piece into this Container
   *
   * @param pieza to add.
   */
  public void putPiece(Piece pieza) {
    this.pieces.add(pieza);
  }

  /**
   * Removes a piece from this Container.
   *
   * @param piece
   */
  public void removePiece(Piece piece) {
    this.pieces.remove(piece);
  }

  /**
   * Gets the unused area in this Container.
   *
   * @return the free area
   */
  public int getFreeArea() {
    return this.getArea() - this.getUsedArea();
  }

  /**
   * Gets the area used up by all pieces in this Container.
   *
   * @return area used up by pieces in this Container.
   */
  public int getUsedArea() {
    return this.pieces.piecesArea();
  }

  /**
   * TODO: Needs testings, it is a high risk method. Dado un objeto (con sus
   * piezas ya colocadas), indica cuál es la distancia vertical que una pieza
   * candidata puede desplazarse verticalmente hacia abajo hasta topar con otra
   * pieza o con la base del objeto.
   *
   * @param piece to determine its bottom bound within this Container
   * @return distance to the bottom bound within this Container for a piece.
   */
  public int distanceToBottBound(Piece piece) {
    assert (piece != null);
    assert (piece.getBottBound() > this.getBottBound());

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
   * TODO: Needs testings, it is a high risk method. Dado un objeto (con sus
   * piezas ya colocadas), indica cuál es la distincia horizontal que una pieza
   * candidata puede desplazarse verticalmente hacia la izquierda hasta topar
   * con otra pieza o con la base del objeto.
   *
   * @param piece to determine its left bound within this Container
   * @return the distance to the left bound within this Container for a piece.
   */
  public int distanceToLeftBound(Piece piece) {
    assert (piece != null);
    assert (piece.getLeftBound() > this.getLeftBound());

    // get biggest maxY
    int leftBounds = 0;
    for (Piece _piece : this.pieces) {
      if (_piece.intersectsOnYAxis(piece)
              && _piece.getRightBound() > leftBounds) {
        leftBounds = _piece.getRightBound();
      }
    }

    return piece.getLeftBound() - leftBounds;
  }

  /**
   * TODO: Test this method, it is high risk method. Determines if a figure is
   * within the bounds of this Container and does not overlap with any Figures
   * already in this Container
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
   * @return true if it intersects with this Container's bounds or a piece
   * within this Container, false otherwise.
   */
  @Override
  public boolean intersectsWith(Figure figure) {
    assert (figure != null);
    if (figure.intersectsWith(this)) {
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
  public Container getCopy() {
    Container copy = new Container(this.getWidth(), this.getHeight());
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