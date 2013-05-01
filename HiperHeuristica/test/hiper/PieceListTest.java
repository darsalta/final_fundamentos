/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hiper;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import hiper.Order;
import hiper.Piece;
import hiper.PieceList;
import hiper.Point;
import hiper.Order;

/**
 *
 * @author marcel
 */
public class PieceListTest {

  public PieceListTest() {
  }

  /**
   * Test of sort method, of class PieceList.
   */
  @Test
  public void testDescendingSort() {
    // Arrange
    System.out.println("PieceList.PieceList.sort(Order.DESCENDING)");
    Order order = Order.DESCENDING;
    PieceList target = new PieceList();
    Piece middle = new PieceStub(1);
    Piece smallest = new PieceStub(0);
    Piece biggest = new PieceStub(2);

    target.add(middle);
    target.add(smallest);
    target.add(biggest);


    // Act
    target.sort(order);
    // Assert
    assertThat(target.get(0), is(biggest));
    assertThat(target.get(1), is(middle));
    assertThat(target.get(2), is(smallest));
  }

  /**
   * Test of getBiggest method, of class PieceList.
   */
  @Test
  public void testGetBiggest() {
    System.out.println("PieceList.getBiggest");
    // Arrange    
    PieceList target = new PieceList();
    Piece biggest = new PieceStub(2);
    target.add(new PieceStub(1));
    target.add(new PieceStub(0));
    target.add(biggest);

    // Act
    Piece result = target.getBiggest();

    // Assert
    assertEquals(biggest, result);
  }

  /**
   * Test of getBiggest method, of class PieceList.
   */
  @Test
  public void testGetBiggest_updates_after_removing_biggest() {
    System.out.println("PieceList.getBiggest : updates after removing biggest.");
    // Arrange    
    PieceList target = new PieceList();
    Piece biggest = new PieceStub(2);
    final PieceStub secondBiggest = new PieceStub(1);
    target.add(secondBiggest);
    target.add(new PieceStub(0));
    target.add(biggest);

    // Assure
    assertSame(biggest, target.getBiggest());

    // Act
    target.remove(biggest);
    Piece newBiggest = target.getBiggest();

    // Assert
    assertEquals(secondBiggest, newBiggest);
  }

  @Test
  public void testRemove_does_not_break_on_removing_last_element() {
    System.out.println("PieceList.testRemove: does not break on removing last element");
    // Arrange    
    PieceList target = new PieceList();
    Piece onlyElement = new PieceStub(2);
    target.add(onlyElement);

    // Assure
    assertSame(onlyElement, target.get(0));

    // Act
    target.remove(onlyElement);

    // Assert
    assertEquals(0, target.size());
  }

  /**
   * Test of areAllBiggerThan method, of class PieceList.
   */
  @Test
  public void testAreAllBiggerThan_some_lower_quantity() {
    System.out.println("PieceList.areAllBiggerThan");
    PieceList target = makeSeriesPieceList(3, 3);

    // Act
    boolean result = target.areAllBiggerThan(2);

    // Assert
    assertTrue(result);
  }

  /**
   * Test of areAllBiggerThan method, of class PieceList.
   */
  @Test
  public void testAreAllBiggerThan_some_middle_quantity() {
    System.out.println("PieceList.testAreAllBiggerThan_some_middle_quantity");
    PieceList target = makeSeriesPieceList(3, 3);

    // Act
    boolean result = target.areAllBiggerThan(5);

    // Assert
    assertFalse(result);
  }

  /**
   * Test of areAllBiggerThan method, of class PieceList.
   */
  @Test
  public void testAreAllBiggerThan_some_higher_quantity() {
    System.out.println("PieceList.testAreAllBiggerThan_some_higher_quantity");
    PieceList target = makeSeriesPieceList(3, 3);

    // Act
    boolean result = target.areAllBiggerThan(8);

    // Assert
    assertFalse(result);
  }

  /**
   * Test of piecesArea method, of class PieceList.
   */
  @Test
  public void testPiecesArea() {
    System.out.println("PieceList.piecesArea");
    // Arrange
    PieceList target = new PieceList();
    target.add(new PieceStub(1));
    target.add(new PieceStub(1));
    int expected = 2;

    // Act
    int area = target.piecesArea();

    // Assert
    assertEquals(expected, area);
  }

  private PieceList makeSeriesPieceList(int start, int count) {
    // Arrange
    PieceList target = new PieceList();
    for (int i = 0; i < count; i++) {
      target.add(new PieceStub(start + i));
    }

    return target;
  }

  class PieceStub extends Piece {

    private int area;

    public PieceStub(int area) {
      super(new Point[]{Point.At(1, 1), Point.At(2, 2), Point.At(3, 3)});

      this.area = area;
    }

    @Override
    public int getArea() {
      return this.area;
    }
  }
}