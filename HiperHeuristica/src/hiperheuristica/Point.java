package hiperheuristica;

/**
 *
 * @author Marcel
 */
public class Point {

  private final int x;
  private final int y;

  /**
   * Creates a new point with coordinates at (x,y)
   * @param x coordinate in the horizontal plane
   * @param y coordinate in the vertical plane.
   * @return 
   */
  public static Point At(int x, int y) {
    return new Point(x, y);
  }
  
  public Point(int x, int y) {
    this.x = x;
    this.y = y;
  }

  public int getX() {
    return this.x;
  }

  public int getY() {
    return this.y;
  }
}
