/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hiperheuristica;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author marcel
 */
public class FigureTest {
  
  public FigureTest() {
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

  /**
   * Test of getRightBound method, of class Figure.
   */
  @Test
  public void testGetRightBound() {
    System.out.println("getRightBound");    
    // Arrange
    Point[] vertices = new Point[] { 
      Point.At(0, 0),      
      Point.At(0, 1),
      Point.At(1, 0),
      Point.At(1, 1),
    };
    Figure instance = new FigureStub(null);
    int expResult = 0;
    // Act
    int result = instance.getRightBound();
    // Assert        
    assertEquals(expResult, result);
    // TODO review the generated test code and remove the default call to fail.
    fail("The test case is a prototype.");
  }

  /**
   * Test of getLeftBound method, of class Figure.
   */
  @Test
  public void testGetLeftBound() {
    System.out.println("getLeftBound");
    Figure instance = null;
    int expResult = 0;
    int result = instance.getLeftBound();
    assertEquals(expResult, result);
    // TODO review the generated test code and remove the default call to fail.
    fail("The test case is a prototype.");
  }

  /**
   * Test of getTopBound method, of class Figure.
   */
  @Test
  public void testGetTopBound() {
    System.out.println("getTopBound");
    Figure instance = null;
    int expResult = 0;
    int result = instance.getTopBound();
    assertEquals(expResult, result);
    // TODO review the generated test code and remove the default call to fail.
    fail("The test case is a prototype.");
  }

  /**
   * Test of getBottBound method, of class Figure.
   */
  @Test
  public void testGetBottBound() {
    System.out.println("getBottBound");
    Figure instance = null;
    int expResult = 0;
    int result = instance.getBottBound();
    assertEquals(expResult, result);
    // TODO review the generated test code and remove the default call to fail.
    fail("The test case is a prototype.");
  }

  /**
   * Test of getWidth method, of class Figure.
   */
  @Test
  public void testGetWidth() {
    System.out.println("getWidth");
    Figure instance = null;
    int expResult = 0;
    int result = instance.getWidth();
    assertEquals(expResult, result);
    // TODO review the generated test code and remove the default call to fail.
    fail("The test case is a prototype.");
  }

  /**
   * Test of getHeight method, of class Figure.
   */
  @Test
  public void testGetHeight() {
    System.out.println("getHeight");
    Figure instance = null;
    int expResult = 0;
    int result = instance.getHeight();
    assertEquals(expResult, result);
    // TODO review the generated test code and remove the default call to fail.
    fail("The test case is a prototype.");
  }

  /**
   * Test of getArea method, of class Figure.
   */
  @Test
  public void testGetArea() {
    System.out.println("getArea");
    Figure instance = null;
    int expResult = 0;
    int result = instance.getArea();
    assertEquals(expResult, result);
    // TODO review the generated test code and remove the default call to fail.
    fail("The test case is a prototype.");
  }

  /**
   * Test of compareTo method, of class Figure.
   */
  @Test
  public void testCompareTo() {
    System.out.println("compareTo");
    Figure other = null;
    Figure instance = null;
    int expResult = 0;
    int result = instance.compareTo(other);
    assertEquals(expResult, result);
    // TODO review the generated test code and remove the default call to fail.
    fail("The test case is a prototype.");
  }

  /**
   * Test of isWithinBounds method, of class Figure.
   */
  @Test
  public void testIsWithinBounds() {
    System.out.println("isWithinBounds");
    Figure figure = null;
    Figure instance = null;
    boolean expResult = false;
    boolean result = instance.isWithinBounds(figure);
    assertEquals(expResult, result);
    // TODO review the generated test code and remove the default call to fail.
    fail("The test case is a prototype.");
  }

  /**
   * Test of intersectsWith method, of class Figure.
   */
  @Test
  public void testIntersectsWith() {
    System.out.println("intersectsWith");
    Figure figure = null;
    Figure instance = null;
    boolean expResult = false;
    boolean result = instance.intersectsWith(figure);
    assertEquals(expResult, result);
    // TODO review the generated test code and remove the default call to fail.
    fail("The test case is a prototype.");
  }

  /**
   * Test of intersectsOnXAxis method, of class Figure.
   */
  @Test
  public void testIntersectsOnXAxis() {
    System.out.println("intersectsOnXAxis");
    Figure figure = null;
    Figure instance = null;
    boolean expResult = false;
    boolean result = instance.intersectsOnXAxis(figure);
    assertEquals(expResult, result);
    // TODO review the generated test code and remove the default call to fail.
    fail("The test case is a prototype.");
  }

  /**
   * Test of intersectsOnYAxis method, of class Figure.
   */
  @Test
  public void testIntersectsOnYAxis() {
    System.out.println("intersectsOnYAxis");
    Figure figure = null;
    Figure instance = null;
    boolean expResult = false;
    boolean result = instance.intersectsOnYAxis(figure);
    assertEquals(expResult, result);
    // TODO review the generated test code and remove the default call to fail.
    fail("The test case is a prototype.");
  }
  
  public class FigureStub extends Figure {
    public FigureStub(Point[] vertices) {
      super(vertices);
    }
  }
}