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
    Piece piece = makePiece(1, 10, 1, 10);
    boolean shouldIntersect = false;
    int width = 11, height = 11;
    // Act & Assert
    checkIntersectsWith(width, height, new Piece[]{}, piece, shouldIntersect);
  }

  /**
   * Test of intersectsWith method, of class Container.
   */
  @Test
  public void testIntersectsWith_empty_container_exactly() {
    System.out.println("Container.intersectsWith : empty container, exact fit");
    // Arrange
    Piece piece = makePiece(0, 10, 0, 10);
    boolean shouldIntersect = false;
    int width = 10, height = 10;
    // Act & Assert
    checkIntersectsWith(width, height, new Piece[]{}, piece, shouldIntersect);
  }

  /**
   * Test of intersectsWith method, of class Container.
   */
  @Test
  public void testIntersectsWith_nonblocking_side_pieces() {
    System.out.println("Container.intersectsWith : with nonblocking side pieces");
    // Arrange
    Piece piece = makePiece(3, 5, 0, 10);
    boolean shouldIntersect = false;
    int width = 10, height = 10;
    Piece[] prevPieces = new Piece[]{
      makePiece(5, 7, 0, 10),
      makePiece(0, 3, 0, 10)
    };

    // Act & Assert
    checkIntersectsWith(width, height, prevPieces, piece, shouldIntersect);
  }

  /**
   * Test of intersectsWith method, of class Container.
   */
  @Test
  public void testIntersectsWith_blocking_left_piece() {
    System.out.println("Container.intersectsWith : with blocking left ieces");
    // Arrange
    Piece piece = makePiece(3, 5, 0, 10);
    boolean shouldIntersect = true;
    int width = 10, height = 10;
    Piece[] prevPieces = new Piece[]{
      makePiece(0, 4, 0, 10)
    };

    // Act & Assert
    checkIntersectsWith(width, height, prevPieces, piece, shouldIntersect);
  }

  /**
   * Test of intersectsWith method, of class Container.
   */
  @Test
  public void testIntersectsWith_blocking_right_piece() {
    System.out.println("Container.intersectsWith : with blocking right pieces");
    // Arrange
    Piece piece = makePiece(3, 5, 0, 10);
    boolean shouldIntersect = true;
    int width = 10, height = 10;
    Piece[] prevPieces = new Piece[]{
      makePiece(4, 7, 0, 10)
    };

    // Act & Assert
    checkIntersectsWith(width, height, prevPieces, piece, shouldIntersect);
  }

  /**
   * Test of intersectsWith method, of class Container.
   */
  @Test
  public void testIntersectsWith_blocking_top_piece() {
    System.out.println("Container.intersectsWith : with blocking top piece");
    // Arrange
    Piece piece = makePiece(3, 5, 3, 5);
    boolean shouldIntersect = true;
    int width = 10, height = 10;
    Piece[] prevPieces = new Piece[]{
      makePiece(3, 5, 4, 10)
    };

    // Act & Assert
    checkIntersectsWith(width, height, prevPieces, piece, shouldIntersect);
  }
  
    /**
   * Test of intersectsWith method, of class Container.
   */
  @Test
  public void testIntersectsWith_blocking_bot_piece() {
    System.out.println("Container.intersectsWith : with blocking bot piece");
    // Arrange
    Piece piece = makePiece(3, 5, 3, 5);
    boolean shouldIntersect = true;
    int width = 10, height = 10;
    Piece[] prevPieces = new Piece[]{
      makePiece(3, 5, 0, 4)
    };

    // Act & Assert
    checkIntersectsWith(width, height, prevPieces, piece, shouldIntersect);
  }

  /**
   * Test of intersectsWith method, of class Container.
   */
  @Test
  public void testIntersectsWith_nonblocking_top_bottom_pieces() {
    System.out.println("Container.intersectsWith : with nonblocking top & bottom pieces");
    // Arrange
    Piece piece = makePiece(0, 10, 3, 5);
    boolean shouldIntersect = false;
    int width = 10, height = 11;
    Piece[] prevPieces = new Piece[]{
      makePiece(0, 10, 0, 3),
      makePiece(0, 10, 5, 7)
    };

    // Act & Assert
    checkIntersectsWith(width, height, prevPieces, piece, shouldIntersect);
  }

  /**
   * Test of getCopy method, of class Container.
   */
  @Test
  public void testGetCopy_is_shallow_copy() {
    System.out.println("Container.getCopy : is shallow copy");
    // Arrange
    Container target = new Container(10, 10);
    target.putPiece(makePiece(0, 1, 0, 1));
    target.putPiece(makePiece(2, 3, 2, 3));
    // Act
    Container copy = target.getCopy();
    // Assert    
    assertNotSame(target, copy);
    assertEquals(target.getWidth(), copy.getWidth());
    assertEquals(target.getHeight(), copy.getHeight());
    assertEquals(target.getFreeArea(), copy.getFreeArea());
  }
  
    /**
   * Test of getCopy method, of class Container.
   */
  @Test
  public void testGetCopy_does_not_share_state() {
    System.out.println("Container.getCopy");
    // Arrange
    Container target = new Container(10, 10);
    Piece piece = makePiece(0, 1, 0, 1);
    target.putPiece(piece);
    target.putPiece(makePiece(2, 3, 2, 3));
    Container copy = target.getCopy();
    
    // Assure
    assertThat(target.getFreeArea(), is(copy.getFreeArea()));
    
    // Act
    target.removePiece(piece);
    
    // Assert
    assertThat(target.getFreeArea(), is(not(copy.getFreeArea())));
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