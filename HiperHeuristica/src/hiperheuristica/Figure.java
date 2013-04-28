package hiperheuristica;

/**
 *
 * @author Marcel
 */
public abstract class Figure implements Comparable<Figure> {

  private Point[] vertices;
  private int rightBound = Integer.MIN_VALUE;
  private int leftBound = Integer.MAX_VALUE;
  private int bottBound = Integer.MAX_VALUE;
  private int topBound = Integer.MIN_VALUE;

  public Figure(Point[] vertices) {
    assert (vertices != null);
    assert (vertices.length >= 3);
    this.vertices = new Point[vertices.length];
    for (int i = 0; i < vertices.length; i++) {
      this.setVertex(i, vertices[i].getX(), vertices[i].getY());
    }
  }

  /**
   * Gets the X coordinate with highest value in this piece's vertices.
   *
   * @return the right bounds.
   */
  public int getRightBound() {
    return this.rightBound;
  }

  /**
   * Gets the X coordinate with smallest value in this piece's vertices.
   *
   * @return the left bounds
   */
  public int getLeftBound() {
    return this.leftBound;
  }

  /**
   * Gets the Y coordinate with highest value in this piece's vertices.
   *
   * @return top bounds
   */
  public int getTopBound() {
    return this.topBound;
  }

  /**
   * Gets the Y coordinate with smallest value in this piece's vertices.
   *
   * @return the bottom bounds.
   */
  public int getBottBound() {
    return this.bottBound;
  }

  public int getWidth() {
    return this.getRightBound() - this.getLeftBound();
  }

  public int getHeight() {
    return this.getTopBound() - this.getBottBound();
  }

  /**
   * Se 'rectangularizan' los vértices de la pieza, i.e. Siempre se aproxima al
   * rectángulo más chico que abarque todos los vértices.
   *
   * @return area rectangularizada de la pieza.
   */
  public int getArea() {
    return this.getWidth() * this.getHeight();
  }

  /**
   * Compares two figures based on their area.
   *
   * @param other Figure to compare with
   * @return 1: this Figure is bigger, 0: both figure are of the same size, -1:
   * this Figure is smaller.
   */
  @Override
  public int compareTo(Figure other) {
    assert (other != null);

    if (this.getArea() < other.getArea()) {
      return -1;
    }

    if (this.getArea() > other.getArea()) {
      return 1;
    }

    return 0;
  }

  /**
   * Determines if a figure is within the bounds of another.
   *
   * @param figure to check that is within the bounds of this Figure
   * @return true if it is within the bounds of this Figure, false otherwise.
   */
  public boolean isWithinBounds(Figure figure) {
    // Si la piece no se sale de los límites del object.
    // REFACTOR: Mover este método a Objeto
    // REFACTOR: Crear una interfaz común entre Objeto y Pieza
    return figure.getTopBound() <= this.getTopBound()
            && figure.getRightBound() <= this.getRightBound()
            && figure.getLeftBound() >= this.getLeftBound()
            && figure.getBottBound() >= this.getBottBound();
  }

  /**
   * Checks if this Figure intersects with another.
   *
   * @param figure to check if it intersects with.
   * @return true if it intersects, false otherwise.
   */
  public boolean intersectsWith(Figure figure) {
    return this.intersectsOnXAxis(figure) && this.intersectsOnYAxis(figure);
  }

  /**
   * Determines if a figure intersects on the X axis with another.
   *
   * @param figure to check for intersection
   * @return true if they intersect on the X axis, false otherwise.
   */
  public boolean intersectsOnXAxis(Figure figure) {
    Figure left = figure.getLeftBound() < this.getLeftBound() ? figure : this;
    Figure right = left == this ? figure : this;

    return left.getRightBound() > right.getLeftBound();
  }

  /**
   * Determines if a figure intersects with another on the Y axis.
   *
   * @param figure to check for intersection
   * @return true if they intersect on the Y axis, false otherwise.
   */
  public boolean intersectsOnYAxis(Figure figure) {
    Figure lower = figure.getBottBound() < this.getBottBound() ? figure : this;
    Figure upper = lower == this ? figure : this;

    return lower.getTopBound() > upper.getBottBound();
  }

  /**
   * Gets the vertices that make up this figure.
   *
   * @return The vertices of this Figure.
   */
  public Point[] getVertices() {
    return this.vertices.clone();
  }

  protected final void setVertex(int index, int x, int y) {
    Point original = this.vertices[index];
    this.vertices[index] = Point.At(x, y);
    if (original != null && original.getX() == this.leftBound) {
      this.leftBound = Point.getSmallestX(vertices).getX();
    } else if (x < this.leftBound) {
      this.leftBound = x;
    }

    if (original != null && original.getX() == this.rightBound) {
      this.leftBound = Point.getBiggestX(vertices).getX();
    } else if (x > this.rightBound) {
      this.rightBound = x;
    }

    if (original != null && original.getY() == this.bottBound) {
      this.bottBound = Point.getSmallestY(vertices).getY();
    } else if (y < this.bottBound) {
      this.bottBound = y;
    }

    if (original != null && original.getY() == this.topBound) {
      this.topBound = Point.getBiggestY(vertices).getY();
    } else if (y > this.topBound) {
      this.topBound = y;
    }
  }
}
