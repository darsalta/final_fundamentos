/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package firstfitdecreasing;

import java.util.List;

/**
 *
 * @author Marcel
 */
class BinPackingResult {

    private final List<Bin> bins;

    public BinPackingResult(List<Bin> bins) {
        this.bins = bins;
    }

    public int getNumberOfBins() {
        return this.bins.size();
    }

    @Override
    public String toString() {
        String msg = "{ \n";
        msg += "  numberOfBins: " + this.getNumberOfBins() + ", \n";
        msg += "  bins: [";        
        for (Bin bin : this.bins) {
            String binMsg = "";
            for(String binLine : bin.toString().split("\n")) {
                binMsg += "\n    " + binLine;
            }
            
            msg += binMsg + ",";
        }

        msg += "\n  ]\n";
        return msg + "}";
    }
}
