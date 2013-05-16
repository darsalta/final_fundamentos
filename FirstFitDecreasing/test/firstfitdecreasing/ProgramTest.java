/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package firstfitdecreasing;

import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Marcel
 */
public class ProgramTest {

    public ProgramTest() {
    }

    /**
     * Test of binPackFFD method, of class Program.
     */
    @Test
    public void testBinPackFFD() {
        System.out.println("binPackFFD");
        // Arrange
        int[] pieces = new int[]{1, 5, 2};
        int binCapacity = 5;

        // Act
        List<Bin> result = Program.binPackFFD(pieces, binCapacity);
        // Assert
        assertEquals(2, result.size());
        // First bin must have the 5 only
        assertEquals(1, result.get(0).getPieces().length);
        // Second bin must have the 2 and 1
        assertEquals(2, result.get(1).getPieces().length);
        // First Bin must have the biggest piece which fits perfectly.
        assertEquals(0, result.get(0).getFreeCapacity());
        // Second bin must have 2 cap. free because of the smaller pieces
        assertEquals(2, result.get(1).getFreeCapacity());
        // Pieces must be set in decreasing order
        assertEquals(5, (int) result.get(0).getPieces()[0]);
        assertEquals(2, (int) result.get(1).getPieces()[0]);
        assertEquals(1, (int) result.get(1).getPieces()[1]);
    }

    @Test
    public void testFindSample() {
        // Arrange
        // y = 12, x = 10, z = 9, w = 37, v = 38, n = numero de repeticiones de y
            // Regla: v = w+1
            // Regla: w = v-1
            // Regla: y * n = w - 1
            // Regla: z + y = 2x + 1
            // Regla: z = x - 1
            // Regla: y = x + 2
            // Regla: En los renglones donde haxa x, debe haber 2 x's
            // Regla: Debe haber n renglones donde haya x
        int[] more_containers = new int[]{            
            665, 561, 12, 12, 12,
            500, 500, 280, 10, 10,
            500, 500, 280, 10, 10,
            500, 500, 280, 10, 10,
            500, 280, 280, 222, 9, 9,
            243, 211, 200, 200, 212, 197, 37, //38,
            189, 162, 150, 150, 150, 150, 154, 158, 37};
        
        int[] less_containers = new int[]{            
            665, 561, 12, 12, 12,
            500, 500, 280, 10, 10,
            500, 500, 280, 10, 10,
            500, 500, 280, 10, 10,
            500, 280, 280, 222, 9, 9,
            243, 211, 200, 200, 212, 197, 37, 38,
            189, 162, 150, 150, 150, 150, 154, 158, 37};
        int capacitx = 1300;
        // Act
        BinPackingResult bigger_result = new BinPackingResult(
                Program.binPackFFD(more_containers, capacitx));
        BinPackingResult smaller_result = new BinPackingResult(
                Program.binPackFFD(less_containers, capacitx));

        // Assert (visually)
        assertTrue(
                bigger_result.getNumberOfBins() > smaller_result.getNumberOfBins());
        //System.out.println(result);
    }
}