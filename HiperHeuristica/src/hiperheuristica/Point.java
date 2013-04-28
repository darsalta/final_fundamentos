package hiperheuristica;

/**
 *
 * @author Marcel
 */
public class Point {

  private int x;
  private int y;

  /**
   * Creates a new point with coordinates at (x,y)
   *
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

  /**
   * Gets the Point with the biggest X value.
   *
   * @param points to search
   * @return the Point with the biggest X value
   */
  public static Point getMaxX(Point[] points) {
    assert (points != null);
    assert (points.length > 0);

    Point result = null;
    int bigX = Integer.MIN_VALUE;
    for (Point point : points) {
      if (point.getX() > bigX) {
        bigX = point.getX();
        result = point;
      }
    }

    return result;
  }

  /**
   * Gets the Point with the smallest X value.
   *
   * @param points to search
   * @return the Point with the smallest X value
   */
  public static Point getMinX(Point[] points) {
    assert (points != null);
    assert (points.length > 0);

    Point result = null;
    int smallX = Integer.MAX_VALUE;
    for (Point point : points) {
      if (point.getX() < smallX) {
        smallX = point.getX();
        result = point;
      }
    }

    return result;
  }

  /**
   * Gets the Point with the smallest Y value.
   *
   * @param points to search
   * @return the Point with the smallest Y value
   */
  public static Point getMinY(Point[] points) {
    assert (points != null);
    assert (points.length > 0);

    Point result = null;
    int smallY = Integer.MAX_VALUE;
    for (Point point : points) {
      if (point.getY() < smallY) {
        smallY = point.getX();
        result = point;
      }
    }

    return result;
  }

  /**
   * Gets the Point with the biggest Y value.
   *
   * @param points to search
   * @return the Point with the biggest Y value
   */
  public static Point getMaxY(Point[] points) {
    assert (points != null);
    assert (points.length > 0);

    Point result = null;
    int bigY = Integer.MIN_VALUE;
    for (Point point : points) {
      if (point.getY() > bigY) {
        bigY = point.getY();
        result = point;
      }
    }

    return result;
  }
}
