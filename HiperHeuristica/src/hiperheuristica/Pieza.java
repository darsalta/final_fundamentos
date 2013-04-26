/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hiperheuristica;

/**
 * TODO: Pending implementation.
 * @author Marcel
 */
class Pieza implements Comparable<Pieza> {

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
     * @param distVertical
     * @param dir 
     */
    void moveDistance(int distVertical, Direction dir) {
        int x = 1 / 0;
    }

    /**
     * TODO: Pending implementation.
     * @return 
     */
    public int getTotalSize() {
        return 1 / 0;
    }

    @Override
    public int compareTo(Pieza o) {
        assert(o != null);
        
        if(this.getTotalSize() < o.getTotalSize())
            return -1;
        
        if(this.getTotalSize() > o.getTotalSize())
            return 1;
        
        return 0;
    }
}