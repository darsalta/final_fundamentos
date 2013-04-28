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
public class ContainerTest {

  public ContainerTest() {
  }

  /**
   * Test of distanceToBottBound method, of class Container.
   */
  @Test
  public void testDistanceToBottBound_empty_container() {
    System.out.println("Container.distanceToBottBound : empty container");
    // Arrange
    Piece piece = makePiece(0, 1, 10, 12);
    int containerWidth = 100, containerHeight = 10;
    int expResult = 10;
    // Act
    checkDistanceToBottomBound(containerWidth, containerHeight, new Piece[]{}, piece, expResult);
  }

  /**
   * Test of distanceToBottBound method, of class Container.
   */
  @Test
  public void testDistanceToBottBound_blocking_bottom() {
    System.out.println("Container.distanceToBottBound : blocking bottom");
    // Arrange
    Piece piece = makePiece(0, 1, 10, 12);
    Piece[] previousPieces = new Piece[]{makePiece(0, 10, 0, 5)};
    int containerWidth = 10, containerHeight = 10;
    int expResult = 5;
    // Act
    checkDistanceToBottomBound(
            containerWidth,
            containerHeight,
            previousPieces,
            piece,
            expResult);
  }

  /**
   * Test of distanceToBottBound method, of class Container.
   */
  @Test
  public void testDistanceToBottBound_piece_not_blocking_bottom() {
    System.out.println("Container.distanceToBottBound : not blocking bottom");
    // Arrange
    Piece piece = makePiece(0, 1, 10, 12);
    Piece[] previousPieces = new Piece[]{makePiece(2, 10, 0, 5)};
    int containerWidth = 10, containerHeight = 10;
    int expResult = 10;
    // Act & Assert
    checkDistanceToBottomBound(
            containerWidth,
            containerHeight,
            previousPieces,
            piece,
            expResult);
  }

  /**
   * Test of distanceToBottBound method, of class Container.
   */
  @Test
  public void testDistanceToBottBound_pieces_blocking_bottom() {
    System.out.println("Container.distanceToBottBound : blocking bottom");
    // Arrange
    Piece piece = makePiece(0, 1, 10, 12);
    Piece[] previousPieces = new Piece[]{
      makePiece(0, 10, 0, 2),
      makePiece(0, 10, 3, 5)};
    int containerWidth = 10, containerHeight = 10;
    int expResult = 5;
    // Act & Assert
    checkDistanceToBottomBound(
            containerWidth,
            containerHeight,
            previousPieces,
            piece,
            expResult);
  }

  /**
   * Test of distanceToLeftBound method, of class Container.
   */
  @Test
  public void testDistanceToLeftBound_empty_container() {
    System.out.println("Container.distanceToLeftBound : empty container");
    // Arrange
    Piece piece = makePiece(10, 20, 0, 10);
    int containerWidth = 10, containerHeight = 10;
    int expected = 10;
    // Act & Assert
    checkDistanceToLeftBound(
            containerWidth,
            containerHeight,
            new Piece[]{},
            piece,
            expected);
  }

  /**
   * Test of distanceToLeftBound method, of class Container.
   */
  @Test
  public void testDistanceToLeftBound_partial_block() {
    System.out.println("Container.distanceToLeftBound : partial block");
    // Arrange
    Piece piece = makePiece(10, 20, 0, 10);
    int containerWidth = 10, containerHeight = 10;
    final Piece[] containerPieces = new Piece[]{makePiece(0, 5, 0, 5)};
    int expResult = 5;
    // Act & Assert
    checkDistanceToLeftBound(
            containerWidth,
            containerHeight,
            containerPieces,
            piece,
            expResult);
  }

  /**
   * Test of distanceToLeftBound method, of class Container.
   */
  @Test
  public void testDistanceToLeftBound_over_block() {
    System.out.println("Container.distanceToLeftBound : over block");
    // Arrange
    Piece piece = makePiece(10, 20, 3, 6);
    int containerWidth = 10, containerHeight = 10;
    final Piece[] containerPieces = new Piece[]{makePiece(0, 5, 0, 10)};
    int expResult = 5;
    // Act & Assert
    checkDistanceToLeftBound(
            containerWidth,
            containerHeight,
            containerPieces,
            piece,
            expResult);
  }

  /**
   * Test of distanceToLeftBound method, of class Container.
   */
  @Test
  public void testDistanceToLeftBound_not_blocking() {
    System.out.println("Container.testDistanceToLeftBound : not blocking");
    // Arrange
    Piece piece = makePiece(10, 20, 3, 6);
    int containerWidth = 10, containerHeight = 10;
    final Piece[] containerPieces = new Piece[]{
      makePiece(0, 5, 0, 2),
      makePiece(6, 10, 7, 10)
    };
    int expResult = 10;
    // Act & Assert
    checkDistanceToLeftBound(
            containerWidth,
            containerHeight,
            containerPieces,
            piece,
            expResult);
  }

  /**
   * Test of intersectsWith method, of class Container.
   */
  @Test
  public void testIntersectsWith_empty_container() {
    System.out.println("Container.intersectsWith : empty container");        
    // Arrange
    Piece piece = makePiece(0, 10, 0, 10);
    boolean shouldIntersect = false;
    int width = 10, height = 10;    
    // Act & Assert
    checkIntersectsWith(width, height, new Piece[]{}, piece, shouldIntersect);
  }

  /**
   * Test of getCopy method, of class Container.
   */
  @Test
  public void testGetCopy() {
    System.out.println("Container.getCopy");
    // TODO: Test not implemented.
    fail("Test not implemented yet.");
    Container instance = null;
    Container expResult = null;
    Container result = instance.getCopy();
    assertEquals(expResult, result);
    // TODO review the generated test code and remove the default call to fail.
    fail("The test case is a prototype.");
  }

  Piece makePiece(int left, int right, int bot, int top) {
    return new Piece(new Point[]{
      Point.At(left, bot), Point.At(left, top),
      Point.At(right, bot), Point.At(right, top)
    });
  }

  private void checkDistanceToBottomBound(
          int width,
          int height,
          Piece[] previousPieces,
          Piece piece,
          int expResult) {
    Container target = initContainer(width, height, previousPieces);
    // Act
    int result = target.distanceToBottBound(piece);
    // Assert   
    assertEquals(expResult, result);
  }

  private void checkDistanceToLeftBound(
          int width,
          int height,
          Piece[] previousPieces,
          Piece piece,
          int expResult) {
    Container target = initContainer(width, height, previousPieces);
    // Act
    int result = target.distanceToLeftBound(piece);
    // Assert   
    assertEquals(expResult, result);
  }

  private void checkIntersectsWith(
          int width,
          int height,
          Piece[] previousPieces,
          Piece piece,
          boolean shouldIntersect) {
    Container target = initContainer(width, height, previousPieces);
    // Act
    boolean intersects = target.intersectsWith(piece);
    // Assert   
    assertEquals(shouldIntersect, intersects);
  }

  private Container initContainer(int width, int height, Piece[] previousPieces) {
    Container target = new Container(width, height);
    for (Piece blocking : previousPieces) {
      target.putPiece(blocking);
    }
    return target;
  }
}