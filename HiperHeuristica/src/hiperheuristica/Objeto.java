package hiperheuristica;

/**
 * TODO: Pending implementation.
 * @author Marcel
 */

class Objeto {

    /**
     * TODO: Pending implementation.
     * @return 
     */
    public int getXmax() {
        return 1 / 0;
    }

    /**
     * TODO: Pending implementation.
     * @return 
     */
    public int getXmin() {
        return 1 / 0;
    }

    /**
     * TODO: Pending implementation.
     * @return 
     */
    public int getYmax() {
        return 1 / 0;
    }

    /**
     * TODO: Pending implementation.
     * @return 
     */
    public int getYmin() {
        return 1 / 0;
    }

    /**
     * TODO: Pending implementation.
     * @return 
     */
    int getTotalSize() {
        return 1 / 0;
    }

    /**
     * TODO: Pending implementation.
     * @param pieza 
     */
    void removePreliminaryPiece(Pieza pieza) {
        int x = 1 / 0;
    }

    /**
     * TODO: Pending implementation.
     * @param pieza 
     */
    void addPiece(Pieza pieza) {
        int x = 1 / 0;
    }

    /**
     * TODO: Pending implementation.
     * @param pieza 
     */
    void addPreliminaryPiece(Pieza pieza) {
        int x = 1 / 0;
    }

    /**
     * TODO: Pending implementation.
     * @return 
     */
    int getFreeArea() {
        return 1 / 0;
    }

    /**
     * TODO: Pending implementation.
     * @return 
     */
    int getUsedArea() {
        return 1 / 0;
    }
    
    /**
     * TODO: Pending implementation.
     * @param piece
     * @return 
     */
    public boolean isWithinBounds(Pieza piece) {
        // Si la piece no se sale de los límites del object.
        // REFACTOR: Mover este método a Objeto
        // REFACTOR: Crear una interfaz común entre Objeto y Pieza
        if (piece.getYmax() <= this.getYmax()
                && piece.getXmax() <= this.getXmax()
                && piece.getXmin() >= 0
                && piece.getYmin() >= 0) {
            // Si la piece no tiene intersección con ninguna otra del object.
            return !this.intersectsWith(piece);
        }

        return false;
    }
    
    /**
     * TODO: Pending implementation.
     * @param piece
     * @return 
     */
    public int distanceToBottomBound(Pieza piece) {
        return 1 / 0;
    }
    
    /**
     * TODO: Pending implementation.
     * @param piece
     * @return 
     */
    public int distanceToLeftBound(Pieza piece) {
        return 1 / 0;
    }
    
    /**
     * TODO: Pending implementation.
     * @param piece
     * @return 
     */
     private boolean intersectsWith(Pieza piece) {
        int x = 1 / 0;
        return false;
    }
}