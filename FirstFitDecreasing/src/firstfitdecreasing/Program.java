/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package firstfitdecreasing;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author Marcel
 */
public class Program {

    private static final int binCapacity1 = 524;
    private static final int[] pieces1 = new int[]{
        37, 10, 106, 85, 252, 252,
        252, 10, 37, 10, 46, 127,
        127, 252, 252, 10, 12, 12,
        442, 9, 252, 106, 10, 106,
        252, 127, 106, 84, 127, 9,
        12, 10, 127
    };
    private static final int binCapacity2 = 1000;
    private static final int[] pieces2 = new int[]{
        351, 357, 269, 252, 261, 350,
        303, 473, 347, 262, 287, 252,
        474, 258, 366, 410, 271, 275,
        370, 298, 273, 366, 419, 444,
        372, 299, 445, 439, 259, 272,
        315, 251, 430, 320, 450, 273,
        472, 395, 275, 288, 292, 269,
        298, 495, 274, 252, 355, 307,
        350, 366, 283, 466, 414, 361,
        363, 255, 272, 254, 263, 268
    };
    private static final int binCapacity3 = 10000;
    private static final int[] pieces3 = new int[]{
        4812, 4122, 3326, 2067, 1492, 468,
        4812, 4122, 3326, 2067, 1492, 246,
        4812, 3959, 3168, 1912, 1308, 246,
        4783, 3787, 3168, 1897, 1308, 117,
        4778, 3534, 3168, 1762, 1274, 117,
        4769, 3534, 3168, 1762, 1274, 63,
        4769, 3534, 2649, 1762, 724, 63,
        4738, 3412, 2317, 1594, 511, 55,
        4199, 3412, 2317, 1574, 511, 26,
        4199, 3412, 2156
    };
    private static final int[] pieces1Mod = new int[]{
        37, 10, 106, 85, 252, 252,
        252, 10, 37, 10, /*46,*/ 127,
        127, 252, 252, 10, 12, 12,
        442, 9, 252, 106, 10, 106,
        252, 127, 106, 84, 127, 9,
        12, 10, 127
    };

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        printProblemSolution("Cuadro #1", pieces1, binCapacity1);
        printProblemSolution("Cuadro #2", pieces2, binCapacity2);
        printProblemSolution("Cuadro #3", pieces3, binCapacity3);
        printProblemSolution("Cuadro #1 sin 46", pieces1Mod, binCapacity1);
    }

    /**
     * Solves the bin packing problem using First Fit Decreasing strategy.
     *
     * @param pieces to pack
     * @param binCapacity of each bin
     * @return the bins with the pieces in them.
     */
    static List<Bin> binPackFFD(int[] pieces, int binCapacity) {
        assert (pieces != null);
        assert (pieces.length > 0);
        assert (binCapacity > 0);

        
        // Iterate in opposite order (descending values)
        countingSort(pieces);
        List<Bin> bins = new ArrayList<>();
        bins.add(new Bin(binCapacity));
        for (int i = pieces.length - 1; i >= 0; i--) {
            if (pieces[i] > binCapacity) {
                throw new IllegalArgumentException("No piece can be bigger than the bin capacity!");
            }

            for (int j = 0; j < bins.size(); j++) {
                Bin bin = bins.get(j);
                if (bin.getFreeCapacity() >= pieces[i]) {
                    bin.addPiece(pieces[i]);
                    break;
                } else if (j == bins.size() - 1) {
                    bins.add(new Bin(binCapacity));
                }
            }
        }

        return bins;
    }

    private static void printProblemSolution(
            String problemName,
            int[] pieces,
            int binCapacity) {

        System.out.println(problemName);
        BinPackingResult result = new BinPackingResult(
                binPackFFD(pieces, binCapacity));
        System.out.println(result);
        System.out.println("****************************************");
    }
    
    /**
     * Does a counting sort on the numbers in the array [a]     
     * @param a the input array
     */
    private static void countingSort(int[] a)
    {
        assert(a != null && a.length > 0);
        
        int max = a[0];
        int min = a[0];
        for(int i = 1; i < a.length; i++) {
            if(a[i] > max) {
                max = a[i];
            } else if (a[i] < min) {
                min = a[i];
            }                        
        }
        
        // this will hold all possible values, from low to high
        int[] counts = new int[max - min + 1];
        for (int x : a) {
            counts[x - min]++;
        } // - low so the lowest possible value is always 0

        int current = 0;
        for (int i = 0; i < counts.length; i++)
        {
            // fills counts[i] elements of value i + low in current
            Arrays.fill(a, current, current + counts[i], i + min);
            // leap forward by counts[i] steps
            current += counts[i];
        }
    }
}
