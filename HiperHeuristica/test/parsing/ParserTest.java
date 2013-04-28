/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package parsing;

import hiperheuristica.Container;
import hiperheuristica.Piece;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Priscila Angulo
 */
public class ParserTest {
    
    public ParserTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }
    
    @Test
    public void testProcessFile() {
      System.out.println("parser.processFile");    
      // Arrange
      //System.out.println((new java.io.File( "." )).getCanonicalPath());
      String file = ".\\input_data\\PF01.txt";
      ProblemInstance problemInstance = null;
      Parser parser = new Parser();

      // Act
      try {
        problemInstance = parser.processFile(file);
      } catch (IOException ex) {
        Logger.getLogger(ParserTest.class.getName()).log(Level.SEVERE, null, ex);
      } 

      // Assert        
      assertNotNull(problemInstance);
      assertNotNull(problemInstance.container);
      assertEquals(100, problemInstance.container.getHeight());
      assertNotNull(problemInstance.pieceList.get(0));
      assertEquals(100, (problemInstance.pieceList.get(0)).getHeight());
      assertEquals(54, (problemInstance.pieceList.get(0)).getWidth());
      
    }
    
    @Test
    public void testGetContainerDim() {
      System.out.println("parser.getContainerDim");    
      // Arrange
      String line = "100 100";
      Container container = null;
      Parser parser = new Parser();
      
      // Act
      container = parser.getContainerDim(line);

      // Assert        
      assertNotNull(container);
      assertEquals(100, container.getHeight());
    }
    
    @Test
    public void testGetPiece(){
      System.out.println("parser.getPiece");    
      // Arrange
      String line = " 4 0 0 54 0 54 100 0 100";
      Piece piece = null;
      Parser parser = new Parser();
      
      // Act
      piece = parser.getPiece(line);

      // Assert        
      assertNotNull(piece);
      assertEquals(100, piece.getHeight());
      assertEquals(54, piece.getWidth());
    }
            
}