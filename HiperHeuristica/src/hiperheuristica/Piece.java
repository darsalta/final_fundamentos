package hiperheuristica;

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