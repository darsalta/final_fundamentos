package hiper;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * It is in charge of maintaining a List of Pieces, and gathering data about
 * them.
 *
 * @author Marcel
 */
public class PieceList implements Iterable<Piece> {

  private final List<Piece> pieces;
  private Piece biggest = null;
  private int piecesArea = 0;

  /**
   * Initializes this instance.
   */
  public PieceList() {
    this.pieces = new ArrayList<Piece>();
  }

  /**
   * Gets the Piece at the given index
   *
   * @param index of the Piece
   * @return the Piece at the given index
   */
  public Piece get(int index) {
    return this.pieces.get(index);
  }

  /**
   * Removes a given piece
   *
   * @param piece to remove
   * @return true if the piece was found and removed, false otherwise.
   */
  public boolean remove(Piece piece) {
    boolean removed = this.pieces.remove(piece);
    this.piecesArea -= piece.getArea();

    if (this.pieces.size() > 0) {
      if (piece == this.biggest) {
        this.biggest = java.util.Collections.<Piece>max(this.pieces);
      }
    } else {
      this.biggest = null;
    }

    return removed;
  }

  /**
   * Adds a Piece to this PieceList
   *
   * @param piece to add
   */
  public void add(Piece piece) {
    if (this.biggest == null || piece.getArea() > this.biggest.getArea()) {
      this.biggest = piece;
    }

    this.piecesArea += piece.getArea();
    this.pieces.add(piece);
  }

  /**
   * Inserts a piece at a specific index.
   *
   * @param index in which to place the piece
   * @param piece to place at a specific index
   */
  public void insertAt(int index, Piece piece) {
    if (this.biggest == null || piece.getArea() > this.biggest.getArea()) {
      this.biggest = piece;
    }

    this.piecesArea += piece.getArea();
    this.pieces.add(index, piece);
  }

  /**
   * Gets the number of items in this PieceList
   *
   * @return the number of items in this PieceList
   */
  public int size() {
    return this.pieces.size();
  }

  /**
   * Sorts this PieceList according to a specified order
   *
   * @param sort according to an order
   */
  public void sort(Order order) {
    java.util.Collections.<Piece>sort(this.pieces);
    if (order.equals(Order.DESCENDING)) {
      java.util.Collections.<Figure>reverse(this.pieces);
    }
  }

  /**
   * Gets the biggest Piece in this PieceList.
   *
   * @return the biggest Piece. Gives null if the PieceList is empty.
   */
  public Piece getBiggest() {
    return this.biggest;
  }

  /**
   * Determines if all pieces in this PieceList are bigger than the
   * sizeThreshold
   *
   * @param sizeThreshold to compare all pieces against
   * @return true if all pieces are bigger than sizeThreshold, false otherwise.
   */
  public boolean areAllBiggerThan(int sizeThreshold) {
    for (Piece piece : this.pieces) {
      if (piece.getArea() <= sizeThreshold) {
        return false;
      }
    }

    return true;
  }

  /**
   * Gets the area occupied by all the pieces in this list.
   *
   * @return the area occupied by all the pieces.
   */
  public int piecesArea() {
    return this.piecesArea;
  }

  /**
   * Gets the sum of the area of the first pieces, if the number of pieces
   * pieceCount is bigger than the number of pieces in this list, only the sum
   * of the number of pieces available is obtained.
   *
   * @param pieceCount the number of first pieces to sum their area
   * @return the sum of the area of the first pieces.
   */
  public int getAreaOfFirst(int pieceCount) {
    int result = 0;
    int finish = validateIndex(pieceCount);

    for (int i = 0; i < finish; i++) {
      result += pieces.get(i).getArea();
    }

    return result;
  }

    /**
   * Gets the sum of the area of the last pieces, if the number of pieces
   * pieceCount is bigger than the number of pieces in this list, only the sum
   * of the number of pieces available is obtained.
   *
   * @param pieceCount the number of last pieces to sum their area
   * @return the sum of the area of the last pieces.
   */
  public int getAreaOfLast(int pieceCount) {
    int result = 0;
    int finish = validateIndex(pieceCount);
    
    for (int i = 1; i <= finish; i++) {
      result += pieces.get(pieces.size() - i).getArea();
    }

    return result;
  }

  @Override
  public Iterator<Piece> iterator() {
    return this.pieces.<Piece>iterator();
  }

  /**
   * Clears the pieces in this instance.
   */
  public void clear() {
    this.pieces.clear();
    this.piecesArea = 0;
    this.biggest = null;
  }

  /**
   * Validates the piece index, to a value &lt;= this.size()
   * @param index to validate
   * @return a value such that result <= this.size
   */
  private int validateIndex(int index) {
    int finish = index;
    if (index > this.size()) {
      finish = this.size();
    }
    
    return finish;
  }
}
