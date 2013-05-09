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
        int[] pieces = new int[]{
            // num1 = 12, num2 = 10, num3 = 9, num4 = 37, num5 = 38, nr = numero de repeticiones de num1
            // Regla: num5 = num4+1
            // Regla: num4 < num5
            // Regla: num1 * [cuenta de num2] = num4 + 1
            // Regla: num3 + num1 > num2 * 2
            // Regla: en los renglones donde haya num2, debe haber 2x num2            
            // Regla: Número de num1's en renglon 0 = número de renglones con num2
            // Regla: (num2*2*nr - num1*nr) + (num3*2 - num2) + (num4 - num2*nr) + (num4 - 2*num2 - num3) = num3 - 1
            665, 561, 12, 12, 12,
            500, 500, 280, 10, 10,
            500, 500, 280, 10, 10,
            500, 500, 280, 10, 10,
            500, 280, 280, 222, 9, 9,
            243, 211, 200, 200, 212, 197, 37, //38,
            189, 162, 150, 150, 150, 150, 154, 158, 37};
        int capacity = 1300;
        // Act
        //List<Bin> bins = Program.binPackFFD(pieces, capacity);
        BinPackingResult result = new BinPackingResult(Program.binPackFFD(pieces, capacity));

        // Assert (visually)
        System.out.println(result);
    }
    
    @Test
    public void testFindSample2() {
        // Arrange
        int[] pieces = new int[]{
            // num1 = 1, num2 = 2, num3 = 5, num4 = 6, num5 = 7
            // Regla: num4 < num5
            // Regla: num3 + num1 > num2 * 2
            // Regla: en los renglones donde haya num2, debe haber 2x num2
            190, 19, 8, 8, 8,
            79, 79, 69, 3, 3,
            79, 79, 69, 3, 3,
            79, 79, 69, 3, 3,
            78, 76, 77, 1, 1,
            75, 73, 71, 14,
            69, 44, 36, 35, 35, 14
            };
        int capacity = 233;
        // Act
        //List<Bin> bins = Program.binPackFFD(pieces, capacity);
        BinPackingResult result = new BinPackingResult(Program.binPackFFD(pieces, capacity));

        // Assert (visually)
        System.out.println(result);
    }
    
}