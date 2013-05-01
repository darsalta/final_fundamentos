package hiper;

/**
 *
 * @author Marcel
 */
public abstract class Figure implements Comparable<Figure> {

  private final Point[] vertices;
  private int rightBound = Integer.MIN_VALUE;
  private int leftBound = Integer.MAX_VALUE;
  private int bottBound = Integer.MAX_VALUE;
  private int topBound = Integer.MIN_VALUE;

  public Figure(Point[] vertices) {
    assert (vertices != null);
    assert (vertices.length >= 3);
    this.vertices = vertices;    
  }

  /**
   * Gets the X coordinate with highest value in this piece's vertices.
   *
   * @return the right bounds.
   */
  public int getRightBound() {
    if (this.rightBound == Integer.MIN_VALUE) {
      this.refreshRightBound();
    }

    return this.rightBound;
  }

  /**
   * Gets the X coordinate with smallest value in this piece's vertices.
   *
   * @return the left bounds
   */
  public int getLeftBound() {
    if (this.leftBound == Integer.MAX_VALUE) {
      this.refreshLeftBound();
    }

    return this.leftBound;
  }

  /**
   * Gets the Y coordinate with highest value in this piece's vertices.
   *
   * @return top bounds
   */
  public int getTopBound() {
    if (this.topBound == Integer.MIN_VALUE) {
      this.refreshTopBound();
    }

    return this.topBound;
  }

  /**
   * Gets the Y coordinate with smallest value in this piece's vertices.
   *
   * @return the bottom bounds.
   */
  public int getBottBound() {
    if (this.bottBound == Integer.MAX_VALUE) {
      this.refreshBottBound();
    }

    return this.bottBound;
  }

  /**
   * Gets the width of this figure.
   *
   * @return the width of the figure.
   */
  public int getWidth() {
    return this.getRightBound() - this.getLeftBound();
  }

  /**
   * Gets the height of this figure.
   *
   * @return the height of this figure.
   */
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

  @Override
  public String toString() {
    return "{ origin: (" + this.getBottBound()
            + "," + this.getLeftBound()
            + "), width: " + this.getWidth()
            + ", height: " + this.getHeight()
            + ", area: " + this.getArea() + " }";
  }

  /**
   * Sets a specific vertex coordinates
   *
   * @param index the index of the vertex.
   * @param newVertex the new point for the vertex
   */
  protected final void setVertex(int index, Point newVertex) {
    this.vertices[index] = newVertex;
    if (newVertex.getX() < this.leftBound) {
      this.leftBound = newVertex.getX();
    } else {
      this.refreshLeftBound();
    }

    if (newVertex.getY() < this.bottBound) {
      this.bottBound = newVertex.getY();
    } else {
      this.refreshBottBound();
    }


    if (newVertex.getX() > this.rightBound) {
      this.rightBound = newVertex.getX();
    } else {
      this.refreshRightBound();
    }

    if (newVertex.getY() > this.topBound) {
      this.topBound = newVertex.getY();
    } else {
      this.refreshTopBound();
    }
  }

  /**
   * Refreshes the left bound value
   */
  private void refreshLeftBound() {
    this.leftBound = Point.getMinX(this.vertices).getX();
  }

  /**
   * Refreshes the bottom bound value
   */
  private void refreshBottBound() {
    this.bottBound = Point.getMinX(this.vertices).getY();
  }

  /**
   * Refreshes the right bound value.
   */
  private void refreshRightBound() {
    this.rightBound = Point.getMaxX(this.vertices).getX();
  }

  /**
   * Refreshes the top bound value.
   */
  private void refreshTopBound() {
    this.topBound = Point.getMaxY(this.vertices).getY();
  }
}
