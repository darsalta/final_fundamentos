package hiper;

/**
 * Represents a piece that can be stored inside a Container.
 *
 * @author Marcel
 */
public class Piece extends Figure {

  public Piece(Point... vertices) {
    super(vertices);
  }

  /**
   * Creates a new piece at the lowerLeft Point, with a given width and height.
   *
   * @param lowerLeft the position of the lower left corner of the piece.
   * @param width this instance width
   * @param height this instance height.
   */
  Piece(Point lowerLeft, int width, int height) {
    super(new Point[]{
      lowerLeft,
      Point.At(lowerLeft.getX(), lowerLeft.getY() + height),
      Point.At(lowerLeft.getX() + width, lowerLeft.getY()),
      Point.At(lowerLeft.getX() + width, lowerLeft.getY() + height)
    });
  }

  /**
   * Moves this piece in a direction for a given distance
   *
   * @param distance to move the Piece
   * @param dir in which to move.
   */
  public void moveDistance(int distance, Direction dir) throws Exception {
    Point[] vertices = this.getVertices();
    for (int i = 0; i < this.getVertices().length; i++) {
      Point point = vertices[i];
      this.setVertex(i, point.move(dir, distance));
    }
  }

  /**
   * Gets a copy of this Piece, but keep in mind that:
   * this.getCopy().equals(this) == false
   *
   * @return A deep copy of this Piece
   */
  public Piece getCopy() {
    return new Piece(this.getVertices());
  }
}