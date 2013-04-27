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
   * TODO: High risk method. Needs testing.
   * Moves this piece in a direction for a given distance
   *
   * @param distance to move the Piece
   * @param dir in which to move.
   */
  public void moveDistance(int distance, Direction dir) {
    for (int i = 0; i < this.vertices.length; i++) {
      Point cPoint = this.vertices[i];
      switch (dir) {
        case UP:
          this.vertices[i] = new Point(cPoint.getX(), cPoint.getY() + distance);
          break;

        case DOWN:
          this.vertices[i] = new Point(cPoint.getX(), cPoint.getY() - distance);
          break;

        case LEFT:
          this.vertices[i] = new Point(cPoint.getX() - distance, cPoint.getY());
          break;

        case RIGHT:
          this.vertices[i] = new Point(cPoint.getX() + distance, cPoint.getY());
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
    return new Piece(this.vertices.clone());
  }
}