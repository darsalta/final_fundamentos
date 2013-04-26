/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hiperheuristica;

import static hiperheuristica.Direction.DOWN;
import static hiperheuristica.Direction.LEFT;
import static hiperheuristica.Direction.RIGHT;
import static hiperheuristica.Direction.UP;

/**
 * TODO: Pending implementation.
 *
 * @author Marcel
 */
class Pieza extends Figure {

    public Pieza(Point[] vertices) {
        super(vertices);
    }

    /**
     * Moves this piece in a direction for a given distance
     *
     * @param distance to move the Pieza
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
     * Gets a copy of this Pieza, but keep in mind that:
     * this.getCopy().equals(this) == false
     * @return A deep copy of this Pieza
     */
    public Pieza getCopy() {
        return new Pieza(this.vertices.clone());
    }
}