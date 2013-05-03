/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hiper;

/**
 *
 * @author Marcel
 */
import org.junit.Test;
import static org.junit.Assert.*;
import hiper.Piece;
import hiper.ProblemInstanceSpec;

public class ProblemInstanceSpecTest {

  private static final int IGNORE = 0;
  private static final int SAME = 0;

  public ProblemInstanceSpecTest() {
  }
  
  private static Object[][] getRecommendedInitialCapacityData = new Object[][]{
    new Object[]{      
      "0.25 percent has more wide pieces, should result in 0.25",
      // # Pieces
      IGNORE, (double)IGNORE,
      //  # of wide pieces bigger than 0.25, # of wide pieces bigger than 0.33
      1, 0,
      // expected
      0.25
    },
    new Object[]{
      "0.25 percent has less wide pieces, should result in 0.33",
      // # Pieces
      IGNORE, (double) IGNORE,
      //  # of wide pieces bigger than 0.25, # of wide pieces bigger than 0.33
      0, 1,
      // expected
      0.33
    },
    new Object[]{
      "same wide pieces (0), pieces < 35 percent = 0.2, should be 0.25",
      // # Pieces
      34, 0.2,
      //  # of wide pieces bigger than 0.25, # of wide pieces bigger than 0.33
      0, 0,
      // expected
      0.25
    },
    new Object[]{
      "same wide pieces (1), pieces = 35 percent = 0.2, should be 0.33",
      // # Pieces
      35, 0.2,
      //  # of wide pieces bigger than 0.25, # of wide pieces bigger than 0.33
      1, 1,
      // expected
      0.33
    },
    new Object[]{
      "same wide pieces (2), pieces > 35 percent = 0.2, should be 0.33",
      // # Pieces
      36, 0.2,
      //  # of wide pieces bigger than 0.25, # of wide pieces bigger than 0.33
      SAME, SAME,
      // expected
      0.33
    },
    new Object[]{
      "same wide pieces (2), pieces < 35 percent < 0.2, should be 0.33",
      // # Pieces
      34, 0.1999,
      //  # of wide pieces bigger than 0.25, # of wide pieces bigger than 0.33
      SAME, SAME,
      // expected
      0.33
    },
    new Object[]{
      "same wide pieces (2), pieces = 35 percent < 0.2, should be 0.33",
      // # Pieces
      35, 0.1999,
      //  # of wide pieces bigger than 0.25, # of wide pieces bigger than 0.33
      SAME, SAME,
      // expected
      0.33
    },
    new Object[]{
      "same wide pieces (2), pieces > 35 percent < 0.2, should be 0.33",
      // # Pieces
      36, 0.1999,
      //  # of wide pieces bigger than 0.25, # of wide pieces bigger than 0.33
      SAME, SAME,
      // expected
      0.33
    }
  };

  @Test
  public void testAvgPercentOfContainerArea() {
    System.out.println("ProblemInstanceSpec.getAvgPercentOfContainerArea");

    // Arrange
    Piece[] pieces = new Piece[]{ // Avg: 32.5%
      PieceStub.WithArea(1),// 25%
      PieceStub.WithArea(2),// 50%
      PieceStub.WithArea(1),// 25%
      PieceStub.WithArea(2),// 50%
    };

    double expected = 0.375;

    ProblemInstanceSpec target = new ProblemInstanceSpec(2, 2, pieces);
    // Act
    double actual = target.getAvgPercentOfContainerArea();

    // Assert
    assertEquals(expected, actual, 0);
  }

  @Test
  public void testGetPerfectNumberOfContainers() {
    System.out.println("ProblemInstanceSpec.getPerfectNumberOfContainers");

    // Arrange

    Piece[] pieces = new Piece[]{ // Total: 2.25 containers
      PieceStub.WithArea(1),//   25%
      PieceStub.WithArea(2),// + 50%
      PieceStub.WithArea(1),// + 25%
      PieceStub.WithArea(3),// + 75%
    };

    double expected = 1.75;
    ProblemInstanceSpec target = new ProblemInstanceSpec(2, 2, pieces);

    // Act
    double actual = target.getPerfectNumberOfContainers();

    // Assert
    assertEquals(expected, actual, 0);
  }

  @Test
  public void testCountBaseWidePiecesBiggerThan() {
    System.out.println("ProblemInstanceSpec.countBaseWidePiecesBiggerThan");
    // Arrange
    Piece[] pieces = new Piece[]{
      PieceStub.Dimensions(2, 2), // Smallest width area < 11 
      PieceStub.Dimensions(4, 3), // Wide, area > 11 +1
      PieceStub.Dimensions(5, 3), // Fully wide, area > 11 +1
      PieceStub.Dimensions(5, 2), // Fully wide, area < 11
      PieceStub.Dimensions(4, 2), // Wide, area < 11
      PieceStub.Dimensions(3, 4), // Not wide, area > 11
    };

    ProblemInstanceSpec target = new ProblemInstanceSpec(5, 5, pieces);

    int expected = 2;

    // Act
    int actual = target.countBaseWidePiecesBiggerThan(11);

    // Assert
    assertEquals(expected, actual);
  }

  @Test
  public void testGetRecommendedInitialCapacity() {

    // Arrange
    for (Object[] testcase : getRecommendedInitialCapacityData) {
      System.out.println("ProblemInstanceSpec.getRecommendedInitialCapacity: "
              + testcase[0]);
      ProblemInstanceSpecStub target = new ProblemInstanceSpecStub();
      target.setNumberOfPieces((int) testcase[1]);
      target.setAvgPercentOfContainerArea((double) testcase[2]);
      target.setCountBaseWidePiecesBigger25((int) testcase[3]);
      target.setCountBaseWidePiecesBigger33((int) testcase[4]);
      double expected = (double) testcase[5];

      // Act
      double actual = target.getRecommendedInitialCapacity();

      // Assert
      assertEquals(expected, actual, 0);
    }
  }

  class ProblemInstanceSpecStub extends ProblemInstanceSpec {

    private int numberOfPieces;
    private int countBaseWidePiecesBigger25;
    private double avgPercentOfContainerArea;
    private int countBaseWidePiecesBigger33;

    public ProblemInstanceSpecStub() {
      super(10, 10, new Piece[]{});
    }

    @Override
    public int countBaseWidePiecesBiggerThan(int area) {
      if (area == 25) {
        return this.countBaseWidePiecesBigger25;
      } else {
        return this.countBaseWidePiecesBigger33;
      }
    }
    
    @Override
    public double getAvgPercentOfContainerArea() {
      return this.avgPercentOfContainerArea;
    }

    @Override
    public int getNumberOfPieces() {
      return this.numberOfPieces;
    }

    /**
     * @param numberOfPieces the numberOfPieces to set
     */
    public void setNumberOfPieces(int numberOfPieces) {
      this.numberOfPieces = numberOfPieces;
    }

    /**
     * @param count the countBaseWidePiecesBigger to set
     */
    public void setCountBaseWidePiecesBigger25(int count) {
      this.countBaseWidePiecesBigger25 = count;
    }

    /**
     * @param count the countBaseWidePiecesBigger to set
     */
    public void setCountBaseWidePiecesBigger33(int count) {
      this.countBaseWidePiecesBigger33 = count;
    }

    /**
     * @param avgPercentOfContainerArea the avgPercentOfContainerArea to set
     */ 
    public void setAvgPercentOfContainerArea(double avgPercentOfContainerArea) {
      this.avgPercentOfContainerArea = avgPercentOfContainerArea;
    }    
  }
}
