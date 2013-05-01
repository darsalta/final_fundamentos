/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hiper;

import org.junit.Test;
import static org.junit.Assert.*;
import hiper.PieceContainer;
import hiper.Piece;
import hiper.Point;
import hiper.Heuristica;
import java.util.logging.Level;
import java.util.logging.Logger;
import static org.hamcrest.CoreMatchers.*;

/**
 *
 * @author Marcel
 */
public class HeuristicaTest {

  public HeuristicaTest() {
  }
  private static Object[][] testMovePieceToLowerLeftData = new Object[][]{
    new Object[]{
      // test name 
      "empty, distToLeftBound() = Zero",
      // container width, container height
      10, 10,
      // piece to place
      new Piece(Point.At(11, 0), 5, 5),
      // expected piece lower left corner
      Point.At(0, 0)
    },
    new Object[]{
      // test name
      "empty, distToBottBound() = Zero",
      // containerWidth, containerHeight
      10, 10,
      // piece to place
      new Piece(Point.At(11, 0), 5, 5),
      // expected piece lower left corner
      Point.At(0, 0)
    },
    new Object[]{
      // test name
      "empty container",
      // containerWidth, containerHeight
      10, 10,
      // piece to place
      new Piece(Point.At(11, 11), 5, 5),
      // expected piece lower left corner
      Point.At(0, 0)
    },
    new Object[]{
      // test name
      "Piece in lower left. DisttoBott = 11, DisttoLeft = 11",
      // containerWidth, containerHeight
      10, 10,
      // piece to place
      new Piece(Point.At(11, 11), 5, 5),
      // expected piece lower left corner
      Point.At(5, 0),
      // previous pieces in container
      new Piece[]{
        new Piece(Point.At(0, 0), 5, 5)
      }
    },
    new Object[]{
      // test name
      "Piece in lower left. DisttoBott = 0, DisttoLeft = 11",
      // containerWidth, containerHeight
      10, 10,
      // piece to place
      new Piece(Point.At(11, 0), 5, 5),
      // expected piece lower left corner
      Point.At(5, 0),
      // previous pieces in container
      new Piece[]{
        new Piece(Point.At(0, 0), 5, 5)
      }
    },
    new Object[]{
      // test name
      "Piece in lower left. DisttoBott = 11, DisttoLeft = 0",
      // containerWidth, containerHeight
      10, 10,
      // piece to place
      new Piece(Point.At(0, 11), 5, 5),
      // expected piece lower left corner
      Point.At(0, 5),
      // previous pieces in container
      new Piece[]{
        new Piece(Point.At(0, 0), 5, 5)
      }
    },
    new Object[]{
      // test name
      "Container completely full. Piece does not fit within bounds.",
      // containerWidth, containerHeight
      5, 5,
      // piece to place
      new Piece(Point.At(0, 11), 5, 5),
      // expected piece lower left corner
      Point.At(0, 11),
      // previous pieces
      new Piece[]{
        new Piece(Point.At(0, 0), 5, 5),},
      // expected result
      false
    },
    new Object[]{
      // test name
      "Container nearly full. Piece does not fit within bounds.",
      // containerWidth, containerHeight
      6, 6,
      // piece to place
      new Piece(Point.At(7, 7), 5, 5),
      // expected piece lower left corner
      Point.At(7, 7),
      // previous pieces
      new Piece[]{
        new Piece(Point.At(0, 0), 5, 5)
      },
      // expected result
      false
    },
    new Object[]{
      // test name
      "Only width fits. Piece does not fit within bounds.",
      // containerWidth, containerHeight
      10, 4,
      // piece to place
      new Piece(Point.At(10, 4), 5, 5),
      // expected piece lower left corner
      Point.At(10, 4),
      // previous pieces
      new Piece[]{
        new Piece(Point.At(0, 0), 4, 4)
      },
      // expected result
      false
    },
    new Object[]{
      // test name
      "Only height fits. Piece does not fit within bounds.",
      // containerWidth, containerHeight
      4, 10,
      // piece to place
      new Piece(Point.At(4, 10), 5, 5),
      // expected piece lower left corner
      Point.At(4, 10),
      // previous pieces
      new Piece[]{
        new Piece(Point.At(0, 0), 4, 4)
      },
      // expected result
      false
    }
  };
  private static Object[][] testTryPlaceInBottomLeftData = new Object[][]{
    new Object[]{
      // test name
      "Piece in lower left. Start at: 0,0.",
      // containerWidth, containerHeight
      10, 10,
      // piece to place
      new Piece(Point.At(0, 0), 5, 5),
      // expected piece lower left corner
      Point.At(5, 0),
      // previous pieces in container
      new Piece[]{
        new Piece(Point.At(0, 0), 5, 5)
      }
    },
    new Object[]{
      // test name
      "Piece in lower left. Start at: 11,0.",
      // containerWidth, containerHeight
      10, 10,
      // piece to place
      new Piece(Point.At(11, 0), 5, 5),
      // expected piece lower left corner
      Point.At(5, 0),
      // previous pieces in container
      new Piece[]{
        new Piece(Point.At(0, 0), 5, 5)
      }
    },
    new Object[]{
      // test name
      "Piece in lower left. Start at: 0,11",
      // containerWidth, containerHeight
      10, 10,
      // piece to place
      new Piece(Point.At(0, 11), 5, 5),
      // expected piece lower left corner
      Point.At(5, 0),
      // previous pieces in container
      new Piece[]{
        new Piece(Point.At(0, 0), 5, 5)
      }
    }
  };

  @Test
  public void testTryPlaceInBottomLeft() throws Exception {
    for (Object[] testdata : testTryPlaceInBottomLeftData) {
      System.out.println("testTryPlaceInBottomLeft: " + getTestName(testdata));
      // Arrange
      PieceContainer container = new PieceContainer(
              getContainerWidth(testdata),
              getContainerHeight(testdata));
      for (Piece previousPiece : getPreviousPieces(testdata)) {
        container.putPiece(previousPiece);
      }

      Piece piece = getPieceToPlace(testdata);
      Point expectedCorner = getExpectedPieceCorner(testdata);

      try {
        // Act
        boolean result = Heuristica.tryPlaceInBottomLeft(container, piece);

        // Assert
        assertTrue(result);
        assertThat(stringify(piece), expectedCorner.getX(), is(piece.getLeftBound()));
        assertThat(stringify(piece), expectedCorner.getY(), is(piece.getBottBound()));
      } catch (Exception ex) {
        Logger.getGlobal().log(Level.INFO, ex.getMessage());
        throw ex;
      }
    }
  }

  @Test
  public void testMovePieceToLowerLeft() throws Exception {
    for (Object[] testdata : testMovePieceToLowerLeftData) {
      System.out.println("testMovePieceToLowerLeft: " + getTestName(testdata));
      // Arrange
      PieceContainer container = new PieceContainer(
              getContainerWidth(testdata),
              getContainerHeight(testdata));
      for (Piece previousPiece : getPreviousPieces(testdata)) {
        container.putPiece(previousPiece);
      }

      Piece piece = getPieceToPlace(testdata);
      Point expectedCorner = getExpectedPieceCorner(testdata);
      boolean expectedResult = getExpectedResult(testdata);

      try {
        // Act
        boolean result = Heuristica.movePieceToLowerLeft(container, piece);

        // Assert
        assertEquals(expectedResult, result);
        assertThat(stringify(piece), expectedCorner.getX(), is(piece.getLeftBound()));
        assertThat(stringify(piece), expectedCorner.getY(), is(piece.getBottBound()));
      } catch (Exception ex) {
        Logger.getGlobal().log(Level.INFO, ex.getMessage());
        throw ex;
      }
    }
  }

  private static String getTestName(Object[] testcase) {
    return testcase[0].toString();
  }

  private static Integer getContainerWidth(Object[] testcase) {
    return (Integer) testcase[1];
  }

  private static Integer getContainerHeight(Object[] testcase) {
    return (Integer) testcase[2];
  }

  private static Piece getPieceToPlace(Object[] testcase) {
    return (Piece) testcase[3];
  }

  private static Point getExpectedPieceCorner(Object[] testcase) {
    return (Point) testcase[4];
  }

  private static Piece[] getPreviousPieces(Object[] testcase) {
    if (testcase.length > 5) {
      return (Piece[]) testcase[5];
    } else {
      return new Piece[]{};
    }
  }

  private static boolean getExpectedResult(Object[] testcase) {
    if (testcase.length > 6) {
      return (Boolean) testcase[6];
    } else {
      return true;
    }
  }

  private static String stringify(Piece piece) {
    String msg = "{ ";
    msg += "left: " + piece.getLeftBound() + ", "
            + "bott: " + piece.getBottBound() + ", "
            + "top: " + piece.getTopBound() + ", "
            + "right: " + piece.getRightBound() + " }";

    return msg;
  }
}