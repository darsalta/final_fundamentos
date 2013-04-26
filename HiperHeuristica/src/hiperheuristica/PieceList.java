/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hiperheuristica;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Marcel
 */
class PieceList {

    private List<Pieza> pieces;

    public PieceList() {
        this.pieces = new ArrayList<>();
    }

    /**
     * Gets the Pieza at the given index
     *
     * @param index of the Pieza
     * @return the Pieza at the given index
     */
    public Pieza get(int index) {
        return this.pieces.get(index);
    }

    /**
     * Removes a given piece
     *
     * @param piece to remove
     * @return true if the piece was found and removed, false otherwise.
     */
    public boolean remove(Pieza piece) {
        return this.pieces.remove(piece);
    }
    
    /**
     * Gets the number of items in this PieceList
     * @return  the number of items in this PieceList
     */
    public int size() {
        return this.pieces.size();
    }
            

    /**
     * Sorts this PieceList according to a specified order
     *
     * @param sort according to an order
     */
    public void sort(Order order) {
        java.util.Collections.<Pieza>sort(this.pieces);
        if (order.equals(Order.DESCENDING)) {
            java.util.Collections.<Pieza>reverse(this.pieces);
        }
    }
    
    /**
     * Gets the biggest Pieza in this PieceList
     * @return the biggest Pieza
     */
    public Pieza getBiggestPiece() {
        return java.util.Collections.<Pieza>max(this.pieces);
    }
    
    /**
     * Determines if all pieces in this PieceList are bigger than the
     * sizeThreshold
     * @param sizeThreshold to compare all pieces against
     * @return true if all pieces are bigger than sizeThreshold, false otherwise.
     */
    public boolean areAllBiggerThan(int sizeThreshold) {
        for(Pieza piece : this.pieces) {
            if(piece.getTotalSize() <= sizeThreshold) {
                return false;
            }
        }
        
        return true;
    }   
}
