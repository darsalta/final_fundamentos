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
}