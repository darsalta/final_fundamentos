/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hiperheuristica;

/**
 *
 * @author Marcel
 */

class Objeto {

    public int getXmax() {
        return 1 / 0;
    }

    public int getXmin() {
        return 1 / 0;
    }

    public int getYmax() {
        return 1 / 0;
    }

    public int getYmin() {
        return 1 / 0;
    }

    int getTotalSize() {
        return 1 / 0;
    }

    void removePreliminaryPiece(Pieza pieza) {
        int x = 1 / 0;
    }

    void addPiece(Pieza pieza) {
        int x = 1 / 0;
    }

    void addPreliminaryPiece(Pieza pieza) {
        int x = 1 / 0;
    }

    int getFreeArea() {
        return 1 / 0;
    }

    int getUsedArea() {
        return 1 / 0;
    }
    
    public boolean isWithinBounds(Pieza piece) {
        Objeto object = this;
        // Si la piece no se sale de los límites del object.
        // REFACTOR: Mover este método a Objeto
        // REFACTOR: Crear una interfaz común entre Objeto y Pieza
        if (piece.getYmax() <= object.getYmax()
                && piece.getXmax() <= object.getXmax()
                && piece.getXmin() >= 0
                && piece.getYmin() >= 0) {
            // Si la piece no tiene intersección con ninguna otra del object.
            return !intersects(piece);
        }

        return false;
    }
    
    public int distanceToBottomBound(Pieza piece) {
        return 1 / 0;
    }
    
    public int distanceToLeftBound(Pieza piece) {
        return 1 / 0;
    }
    
     private boolean intersects(Pieza piece) {
        int x = 1 / 0;
        return false;
    }
}