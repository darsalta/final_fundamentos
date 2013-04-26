/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hiperheuristica;

/**
 *
 * @author Marcel
 */
public class Figure implements Comparable<Figure> {

    protected Point[] vertices;

    public Figure(Point[] vertices) {
        assert (vertices != null);
        this.vertices = vertices;
    }

    /**
     * Gets the X coordinate with highest value in this piece's vertices.
     *
     * @return the right bounds.
     */
    public int getRightBound() {
        int maxX = Integer.MIN_VALUE;
        for (Point point : this.vertices) {
            if (point.getX() > maxX) {
                maxX = point.getX();
            }
        }

        return maxX;
    }

    /**
     * Gets the X coordinate with smallest value in this piece's vertices.
     *
     * @return the left bounds
     */
    public int getLeftBound() {
        int minX = Integer.MAX_VALUE;
        for (Point point : this.vertices) {
            if (point.getX() < minX) {
                minX = point.getX();
            }
        }

        return minX;
    }

    /**
     * Gets the Y coordinate with highest value in this piece's vertices.
     *
     * @return top bounds
     */
    public int getTopBound() {
        int maxY = Integer.MIN_VALUE;
        for (Point point : this.vertices) {
            if (point.getY() > maxY) {
                maxY = point.getY();
            }
        }

        return maxY;
    }

    /**
     * Gets the Y coordinate with smallest value in this piece's vertices.
     *
     * @return the bottom bounds.
     */
    public int getBottBound() {
        int minY = Integer.MAX_VALUE;
        for (Point point : this.vertices) {
            if (point.getY() < minY) {
                minY = point.getY();
            }
        }

        return minY;
    }

    
    public int getWidth() {
        return this.getRightBound() - this.getLeftBound();
    }

    public int getHeight() {
        return this.getTopBound() - this.getBottBound();
    }

    /**
     * Se 'rectangularizan' los vértices de la pieza, i.e. Siempre se aproxima
     * al rectángulo más chico que abarque todos los vértices.
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
     * @return 1: this Figure is bigger, 0: both figure are of the same size,
     * -1: this Figure is smaller.
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
     * TODO: Test this method, it is high risk method.
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
     * TODO: Needs tests, this is a high risk method.
     *
     * @param figure to check if it intersects with.
     * @return true if it intersects, false otherwise.
     */
    public boolean intersectsWith(Figure figure) {
        return this.intersectsOnXAxis(figure) && this.intersectsOnYAxis(figure);
    }

    /**
     * TODO: Test this method, it is high risk method. Determines if a figure
     * intersects on the X axis with another.
     *
     * @param figure to check for intersection
     * @return true if they intersect on the X axis, false otherwise.
     */
    public boolean intersectsOnXAxis(Figure figure) {
        Figure left = figure.getLeftBound() < this.getLeftBound() ? figure : this;
        Figure right = left == this ? figure : this;

        return left.getRightBound() <= right.getLeftBound();
    }

    /**
     * TODO: Test this method, it is high risk method. Determines if a figure
     * intersects with another on the Y axis.
     *
     * @param figure to check for intersection
     * @return true if they intersect on the Y axis, false otherwise.
     */
    public boolean intersectsOnYAxis(Figure figure) {
        Figure lower = figure.getBottBound() < this.getBottBound() ? figure : this;
        Figure upper = lower == this ? figure : this;

        return lower.getTopBound() <= upper.getBottBound();
    }
}
