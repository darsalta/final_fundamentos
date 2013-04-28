package hiperheuristica;

import static hiperheuristica.Direction.*;

/**
 * Represents a piece that can be stored inside a Container.
 *
 * @author Marcel
 */
public class Piece extends Figure {

  public Piece(Point[] vertices) {
    super(vertices);
  }

  /**
   * Moves this piece in a direction for a given distance
   *
   * @param distance to move the Piece
   * @param dir in which to move.
   */
  public void moveDistance(int distance, Direction dir) {
    Point[] vertices = this.getVertices();
    for (int i = 0; i < this.getVertices().length; i++) {
      Point point = vertices[i];
      switch (dir) {
        case UP:
          this.setVertex(i, point.getX(), point.getY() + distance);
          break;

        case DOWN:
          this.setVertex(i, point.getX(), point.getY() - distance);
          break;

        case LEFT:
          this.setVertex(i, point.getX() - distance, point.getY());
          break;

        case RIGHT:
          this.setVertex(i, point.getX() + distance, point.getY());
          break;
      }
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