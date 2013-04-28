/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hiperheuristica;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

/**
 *
 * @author marcel
 */
public class PieceTest {

  public PieceTest() {
  }
  private static Object[][] moveDistanceData = new Object[][]{
    // Move direction, distance, leftBoundMovement, lowerBoundMovemten
    new Object[]{Direction.LEFT, 1, -1, 0, "Left movement."},
    new Object[]{Direction.RIGHT, 1, 1, 0, "Right movement."},
    new Object[]{Direction.DOWN, 1, 0, -1, "Down movement."},
    new Object[]{Direction.UP, 1, 0, 1, "Up movement."}
  };

  /**
   * Test of moveDistance method, of class Piece.
   */
  @Test
  public void testMoveDistance() {
    for (Object[] testcase : moveDistanceData) {
      System.out.println("Piece.moveDistance: " + testcase[4]);
      // Arrange
      int distance = (int) testcase[1];
      Direction dir = (Direction) testcase[0];
      Piece target = new Piece(
              new Point[]{Point.At(1, 0), Point.At(2, 1), Point.At(3, 2)
      });
      int expectedLeftBound = target.getLeftBound() + (int) testcase[2];
      int expectedBottBound = target.getBottBound() + (int) testcase[3];

      // Act
      target.moveDistance(distance, dir);

      // Assert
      assertEquals(expectedLeftBound, target.getLeftBound());
      assertEquals(expectedBottBound, target.getBottBound());
    }
  }

  /**
   * Test of getCopy method, of class Piece.
   */
  @Test
  public void testGetCopy() {
    System.out.println("Piece.getCopy");
    // Arrange
    Piece target = new Piece(new Point[]{
      Point.At(0, 0), Point.At(1, 0), Point.At(1, 1)});
    // Act
    Piece copy = target.getCopy();
    // Assert
    assertEquals(target.getBottBound(), copy.getBottBound());
    assertEquals(target.getHeight(), copy.getHeight());
    assertEquals(target.getLeftBound(), copy.getLeftBound());
    assertEquals(target.getWidth(), copy.getWidth());
  }

  @Test
  public void testGetCopy_does_not_share_state() {
    System.out.println("Piece.GetCopy: does not share state");
    // Arrange
    Piece target = new Piece(new Point[]{
      Point.At(0, 0), Point.At(1, 0), Point.At(1, 1)});
    // Act
    Piece copy = target.getCopy();
    copy.moveDistance(1, Direction.UP);
    copy.moveDistance(1, Direction.RIGHT);
    // Assert        
    assertThat(target.getBottBound(), is(not(copy.getBottBound())));
    assertThat(target.getLeftBound(), is(not(copy.getLeftBound())));
  }
}