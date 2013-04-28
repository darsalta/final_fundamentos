/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hiperheuristica;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author marcel
 */
public class FigureTest {

  private static final Point[] SMALL_SQUARE_AT_1_1 = new Point[]{
    Point.At(1, 1), Point.At(1, 2), Point.At(2, 1), Point.At(2, 2)
  };
  private static final Point[] SMALL_TRIANGLE_AT_0_0 = new Point[]{
    Point.At(0, 0), Point.At(1, 1), Point.At(2, 2)
  };
  private static final Point[] HUNDRED_UNIT_SQUARE = new Point[]{
    Point.At(0, 0), Point.At(100, 0), Point.At(0, 100), Point.At(100, 100)
  };
  private static final Point[] SMALL_SQUARE_AT_0_0 = new Point[]{
    Point.At(0, 0), Point.At(0, 1), Point.At(1, 0), Point.At(1, 1)
  };
  private static final Point[] BIG_SQUARE_AT_0_0 = new Point[]{
    Point.At(0, 0), Point.At(4, 4), Point.At(0, 4), Point.At(4, 0)
  };
  private static final Point[] MEDIUM_SQUARE_AT_1_1 = new Point[]{
    Point.At(1, 1), Point.At(3, 3), Point.At(1, 3), Point.At(3, 1)
  };
  private static final Point[] BIG_TRIANGLE_AT_0_0 = new Point[]{
    Point.At(0, 0), Point.At(2, 4), Point.At(0, 4)
  };
  private static final Point[] SMALL_SQUARE_AT_1_0 = new Point[]{
    Point.At(1, 0), Point.At(2, 0), Point.At(2, 2), Point.At(1, 2)
  };
  private static final Point[] BIG_SQUARE_AT_3_3 = new Point[]{
    Point.At(3, 3), Point.At(7, 7), Point.At(3, 7), Point.At(7, 3)
  };

  public FigureTest() {
  }
  static Object[][] rightBoundData = new Object[][]{
    new Object[]{
      SMALL_SQUARE_AT_0_0, 1, "Zero root single unit square"
    },
    new Object[]{SMALL_TRIANGLE_AT_0_0, 2, "Two unit triangle"},
    new Object[]{HUNDRED_UNIT_SQUARE, 100, "100 unit square"}
  };
  static Object[][] leftBoundData = new Object[][]{
    new Object[]{SMALL_SQUARE_AT_1_1, 1, "Single unit square"},
    new Object[]{SMALL_TRIANGLE_AT_0_0, 0, "Two unit triangle"},
    new Object[]{HUNDRED_UNIT_SQUARE, 0, "100 unit square"}
  };
  static Object[][] topBoundData = new Object[][]{
    new Object[]{SMALL_SQUARE_AT_1_1, 2, "Single unit square"},
    new Object[]{SMALL_TRIANGLE_AT_0_0, 2, "Two unit triangle"},
    new Object[]{HUNDRED_UNIT_SQUARE, 100, "100 unit square"}
  };
  static Object[][] bottBoundData = new Object[][]{
    new Object[]{SMALL_SQUARE_AT_1_1, 1, "Single unit square"},
    new Object[]{SMALL_TRIANGLE_AT_0_0, 0, "Two unit triangle"},
    new Object[]{HUNDRED_UNIT_SQUARE, 0, "100 unit square"}
  };
  static Object[][] widthData = new Object[][]{
    new Object[]{SMALL_SQUARE_AT_1_1, 1, "Single unit square"},
    new Object[]{SMALL_TRIANGLE_AT_0_0, 2, "Two unit triangle"},
    new Object[]{HUNDRED_UNIT_SQUARE, 100, "100 unit square"}
  };
  static Object[][] heightData = new Object[][]{
    new Object[]{SMALL_SQUARE_AT_1_1, 1, "Single unit square"},
    new Object[]{SMALL_TRIANGLE_AT_0_0, 2, "Two unit triangle"},
    new Object[]{HUNDRED_UNIT_SQUARE, 100, "100 unit square"}
  };
  static Object[][] areaData = new Object[][]{
    new Object[]{SMALL_SQUARE_AT_1_1, 1 * 1, "Single unit square"},
    new Object[]{SMALL_TRIANGLE_AT_0_0, 2 * 2, "Two unit triangle"},
    new Object[]{HUNDRED_UNIT_SQUARE, 100 * 100, "100 unit square"}
  };
  static Object[][] compareToData = new Object[][]{
    new Object[]{SMALL_SQUARE_AT_1_1, 0, "equal single unit squares",
      SMALL_SQUARE_AT_1_1},
    new Object[]{SMALL_SQUARE_AT_1_1, -1,
      "single unit square VS two unit triangle", SMALL_TRIANGLE_AT_0_0},
    new Object[]{SMALL_TRIANGLE_AT_0_0, 1,
      "two unit triangle VS single unit square", SMALL_SQUARE_AT_1_1}
  };
  static Object[][] isWithinBoundsData = new Object[][]{
    new Object[]{
      BIG_SQUARE_AT_0_0, true, "Square a lot bigger than other.", MEDIUM_SQUARE_AT_1_1},
    new Object[]{
      BIG_SQUARE_AT_0_0, true, "Square exactly like another.", BIG_SQUARE_AT_0_0},
    new Object[]{BIG_TRIANGLE_AT_0_0, false,
      "Triangle bigger than square cannot fit inside.", MEDIUM_SQUARE_AT_1_1
    },
    new Object[]{MEDIUM_SQUARE_AT_1_1, false,
      "Square smaller than other triangle that cannot fit inside.", BIG_TRIANGLE_AT_0_0
    },
    new Object[]{BIG_SQUARE_AT_0_0, true,
      "Square fits exactly a triangle inside it.", BIG_TRIANGLE_AT_0_0},
    new Object[]{BIG_TRIANGLE_AT_0_0, true,
      "Triangle fits exactly a square inside it.", SMALL_SQUARE_AT_1_0
    }
  };
  static Object[][] intersectsOnXAxisData = new Object[][]{
    new Object[]{
      new Point[]{Point.At(3, 3), Point.At(3, 5), Point.At(5, 3), Point.At(5, 5)},
      true,
      "Two figures with same width and overlaping height should intersect.",
      new Point[]{Point.At(3, 0), Point.At(3, 4), Point.At(5, 0), Point.At(5, 4)}
    },
    new Object[]{
      BIG_SQUARE_AT_0_0, true,
      "Big square on left intersects on X axis with other square by one unit.",
      BIG_SQUARE_AT_3_3
    },
    new Object[]{
      BIG_SQUARE_AT_3_3, true,
      "Big square on right intersects on X axis with other square by one unit.",
      BIG_SQUARE_AT_0_0
    },
    new Object[]{
      MEDIUM_SQUARE_AT_1_1, false,
      "Medium square @(1,1) does not intersect on X axis with big square @(3,3).",
      BIG_SQUARE_AT_3_3
    },
    new Object[]{
      BIG_SQUARE_AT_3_3, false,
      "Big square @(3,3) does not intersect on X axis with medium square @(1,1).",
      MEDIUM_SQUARE_AT_1_1
    },
    new Object[]{
      SMALL_SQUARE_AT_0_0, false,
      "Small square @(0,0) doesn't intersect on X axis with big square @(3,3)",
      BIG_SQUARE_AT_3_3
    },
    new Object[]{
      BIG_SQUARE_AT_3_3, false,
      "Big square @(3,3) doesn't intersect on X axis with small square @(0,0)",
      SMALL_SQUARE_AT_0_0
    }
  };
  static Object[][] intersectsOnYAxisData = new Object[][]{
    new Object[]{
      BIG_SQUARE_AT_0_0, true,
      "Big square below intersects on Y axis with other square by an unit.",
      BIG_SQUARE_AT_3_3
    },
    new Object[]{
      BIG_SQUARE_AT_3_3, true,
      "Big square atop intersects on Y axis with other square by an unit.",
      BIG_SQUARE_AT_0_0
    },
    new Object[]{
      SMALL_TRIANGLE_AT_0_0, false,
      "Small triangle @(0,0) doesn't intersect on Y axis with big square @(3,3)",
      BIG_SQUARE_AT_3_3
    },
    new Object[]{
      BIG_SQUARE_AT_3_3, false,
      "Big square @(3,3) doesn't intersect on Y axis with small triangle @(0,0)",
      SMALL_TRIANGLE_AT_0_0
    }
  };

  /**
   * Test of getRightBound method, of class Figure.
   */
  @Test
  public void testGetRightBound() {
    // Arrange    
    for (Object[] data : rightBoundData) {
      System.out.println("Figure.getRightBound " + getTestCaseName(data));

      int expected = getExpectedValue(data);
      Figure target = new FigureStub(getVertices(data));

      // Act
      int result = target.getRightBound();

      // Assert
      assertEquals(expected, result);
    }
  }

  /**
   * Test of getLeftBound method, of class Figure.
   */
  @Test
  public void testGetLeftBound() {
    // Arrange    
    for (Object[] data : leftBoundData) {
      System.out.println("Figure.getLeftBound " + getTestCaseName(data));
      int expected = getExpectedValue(data);
      Figure target = new FigureStub(getVertices(data));

      // Act
      int result = target.getLeftBound();

      // Assert
      assertEquals(expected, result);
    }
  }

  /**
   * Test of getTopBound method, of class Figure.
   */
  @Test
  public void testGetTopBound() {
    // Arrange    
    for (Object[] data : topBoundData) {
      System.out.println("Figure.getTopBound " + getTestCaseName(data));
      int expected = getExpectedValue(data);
      Figure target = new FigureStub(getVertices(data));

      // Act
      int result = target.getTopBound();

      // Assert
      assertEquals(expected, result);
    }
  }

  /**
   * Test of getBottBound method, of class Figure.
   */
  @Test
  public void testGetBottBound() {
    // Arrange    
    for (Object[] data : bottBoundData) {
      System.out.println("Figure.getBottBound " + getTestCaseName(data));
      int expected = getExpectedValue(data);
      Figure target = new FigureStub(getVertices(data));

      // Act
      int result = target.getBottBound();

      // Assert
      assertEquals(expected, result);
    }
  }

  /**
   * Test of getWidth method, of class Figure.
   */
  @Test
  public void testGetWidth() {
    // Arrange    
    for (Object[] data : widthData) {
      System.out.println("Figure.getWidth " + getTestCaseName(data));

      int expected = getExpectedValue(data);
      Figure target = new FigureStub(getVertices(data));

      // Act
      int result = target.getWidth();

      // Assert
      assertEquals(expected, result);
    }
  }

  /**
   * Test of getHeight method, of class Figure.
   */
  @Test
  public void testGetHeight() {
    // Arrange    
    for (Object[] data : heightData) {
      System.out.println("Figure.getHeight " + getTestCaseName(data));
      int expected = getExpectedValue(data);
      Figure target = new FigureStub(getVertices(data));

      // Act
      int result = target.getHeight();

      // Assert
      assertEquals(expected, result);
    }
  }

  /**
   * Test of getArea method, of class Figure.
   */
  @Test
  public void testGetArea() {
    // Arrange    
    for (Object[] data : areaData) {
      System.out.println("Figure.getArea " + getTestCaseName(data));
      int expected = getExpectedValue(data);
      Figure target = new FigureStub(getVertices(data));

      // Act
      int result = target.getArea();

      // Assert
      assertEquals(expected, result);
    }
  }

  /**
   * Test of compareTo method, of class Figure.
   */
  @Test
  public void testCompareTo() {
    // Arrange    
    for (Object[] data : compareToData) {
      System.out.println("Figure.compareTo " + getTestCaseName(data));
      int expected = getExpectedValue(data);
      Figure targetFigure = new FigureStub(getVertices(data));
      Figure otherFigure = new FigureStub(getOtherVertices(data));

      // Act
      int result = targetFigure.compareTo(otherFigure);

      // Assert
      assertEquals(expected, result);
    }
  }

  /**
   * Test of isWithinBounds method, of class Figure.
   */
  @Test
  public void testIsWithinBounds() {
    // Arrange    
    for (Object[] data : isWithinBoundsData) {
      System.out.println("Figure.isWithinBounds " + getTestCaseName(data));
      boolean expected = getExpectedValue(data);
      Figure targetFigure = new FigureStub(getVertices(data));
      Figure otherFigure = new FigureStub(getOtherVertices(data));

      // Act
      boolean result = targetFigure.isWithinBounds(otherFigure);

      // Assert
      assertEquals(expected, result);
    }
  }

  /**
   * Test of intersectsWith method, of class Figure.
   */
  @Test
  public void testIntersectsWith_fails_when_x_axis_not_intersects() {
    // Arrange    
    FigureIntersectsStub target = new FigureIntersectsStub();
    target.setIntersectsOnXAxis(false);
    boolean expectedResult = false;

    // Act
    boolean result = target.intersectsWith(null);

    // Assert
    assertEquals(expectedResult, result);
  }

  /**
   * Test of intersectsWith method, of class Figure.
   */
  @Test
  public void testIntersectsWith_fails_when_y_axis_not_intersects() {
    // Arrange    
    FigureIntersectsStub target = new FigureIntersectsStub();
    target.setIntersectsOnYAxis(false);
    boolean expectedResult = false;

    // Act
    boolean result = target.intersectsWith(null);

    // Assert
    assertEquals(expectedResult, result);
  }

  /**
   * Test of intersectsOnXAxis method, of class Figure.
   */
  @Test
  public void testIntersectsOnXAxis() {
// Arrange    
    for (Object[] data : intersectsOnXAxisData) {
      System.out.println("Figure.intersecsOnXAxis " + getTestCaseName(data));
      boolean expected = getExpectedValue(data);
      Figure targetFigure = new FigureStub(getVertices(data));
      Figure otherFigure = new FigureStub(getOtherVertices(data));

      // Act
      boolean result = targetFigure.intersectsOnXAxis(otherFigure);

      // Assert
      assertEquals(expected, result);
    }
  }

  /**
   * Test of intersectsOnYAxis method, of class Figure.
   */
  @Test
  public void testIntersectsOnYAxis() {
    for (Object[] data : intersectsOnYAxisData) {
      System.out.println("Figure.intersecsOnYAxis " + getTestCaseName(data));
      boolean expected = getExpectedValue(data);
      Figure targetFigure = new FigureStub(getVertices(data));
      Figure otherFigure = new FigureStub(getOtherVertices(data));

      // Act
      boolean result = targetFigure.intersectsOnYAxis(otherFigure);

      // Assert
      assertEquals(expected, result);
    }
  }

  private Point[] getVertices(Object[] data) {
    return (Point[]) data[0];
  }

  private Point[] getOtherVertices(Object[] data) {
    return (Point[]) data[3];
  }

  private <T> T getExpectedValue(Object[] data) {
    return (T) data[1];
  }

  private String getTestCaseName(Object[] data) {
    return data[2].toString();
  }

  class FigureStub extends Figure {

    public FigureStub(Point[] vertices) {
      super(vertices);
    }
  }

  class FigureIntersectsStub extends Figure {

    private boolean intersectsOnYAxis;
    private boolean intersectsOnXAxis;

    public FigureIntersectsStub() {
      super(new Point[]{Point.At(0, 0), Point.At(1, 1), Point.At(2, 2)});
    }

    public void setIntersectsOnYAxis(boolean intersects) {
      this.intersectsOnYAxis = intersects;
    }

    public void setIntersectsOnXAxis(boolean intersects) {
      this.intersectsOnXAxis = intersects;
    }

    @Override
    public boolean intersectsOnYAxis(Figure figure) {
      return this.intersectsOnYAxis;
    }

    @Override
    public boolean intersectsOnXAxis(Figure figure) {
      return this.intersectsOnXAxis;
    }
  }
}