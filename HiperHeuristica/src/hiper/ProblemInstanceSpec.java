/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hiper;

import hiper.PieceList;
import hiper.Piece;

/**
 *
 * @author Priscila Angulo
 */
public class ProblemInstanceSpec {

  private final Piece[] inputPieces;
  private final int containerWidth;
  private final int containerHeight;

  /**
   * Initializes this instance.
   *
   * @param containerWidth the width for each container for piece placing.
   * @param containerHeight the height for each container for piece placing.
   * @param inputPieces the pieces defined to place for the problem.
   */
  public ProblemInstanceSpec(
          int containerWidth,
          int containerHeight,
          Piece[] inputPieces) {
    assert (inputPieces != null);
    assert (containerHeight > 0);
    assert (containerWidth > 0);

    this.inputPieces = inputPieces;
    this.containerWidth = containerWidth;
    this.containerHeight = containerHeight;
  }

  /**
   * Gets a copy of the pieces of the problem specification.
   *
   * @return the pieceList
   */
  public PieceList getInputPieces() {
    PieceList pieceList = new PieceList();

    for (Piece piece : this.inputPieces) {
      pieceList.add(piece.getCopy());
    }

    return pieceList;
  }

  /**
   * Gets a specific piece from the input pieces.
   *
   * @param index
   * @return
   */
  public Piece getInputPiece(int index) {
    return this.inputPieces[index].getCopy();
  }

  /**
   * Gets the container width for the problem definition.
   *
   * @return the containerWidth
   */
  public int getContainerWidth() {
    return this.containerWidth;
  }

  /**
   * Gets the container height for the problem definition.
   *
   * @return the containerHeight
   */
  public int getContainerHeight() {
    return this.containerHeight;
  }

  /**
   * Gets the average percentage of area of the pieces, in comparison to the
   * container area.
   *
   * @return average percentage of area of the pieces, in comparison to the
   * container area
   */
  public double getAvgPercentOfContainerArea() {
    double sum = 0;
    for (Piece piece : this.inputPieces) {
      sum += piece.getArea();
    }

    double containerArea = this.getContainerHeight() * this.getContainerWidth();

    return (sum / containerArea) / getNumberOfPieces();
  }

  /**
   * Gets a number of pieces with a width greater than the smallest piece's
   * width, that are bigger then a given area.
   *
   * @return number of pieces bigger then a given area.
   */
  public int countBaseWidePiecesBiggerThan(int area) {
    int counter = 0;
    int smallestWidth = this.getSmallestWidth();
    for (Piece piece : this.inputPieces) {
      if (piece.getArea() > area
              && this.containerWidth - piece.getWidth() < smallestWidth) {
        counter++;
      }
    }

    return counter;
  }

  /**
   * Gets the smallest width of the pieces in this problem specification.
   *
   * @return width of the piece with the smallest width
   */
  private int getSmallestWidth() {
    int smallest = Integer.MAX_VALUE;

    for (Piece piece : this.inputPieces) {
      if (piece.getWidth() < smallest) {
        smallest = piece.getWidth();
      }
    }

    return smallest;
  }

  /**
   * Gets the theoretical number of containers if all pieces could magically fit
   * perfectly without wasting any space in any container.
   *
   * @return number of containers if all pieces fit perfectly
   */
  public double getPerfectNumberOfContainers() {
    return this.getAvgPercentOfContainerArea() * this.getNumberOfPieces();
  }

  /**
   * Gets the recommended initial capacity based on a hyper-heuristic that
   * chooses between 0.25 and 0.33 as the initial capacity for the Djang and
   * Finch bin packing heuristic.
   *
   * @return
   */
  public double getRecommendedInitialCapacity() {
    int containerArea = this.getContainerHeight() * this.getContainerWidth();
    int count25 = this.countBaseWidePiecesBiggerThan((int) (containerArea * 0.25));
    int count33 = this.countBaseWidePiecesBiggerThan((int) (containerArea * 0.33));

    if (count25 > count33) {
      return 0.25;
    } else if (count33 > count25) {
      return 0.33;

    }

    // Tie breaker
    if (this.getNumberOfPieces() < 35
     && this.getAvgPercentOfContainerArea() >= 0.2) {
      return 0.25;
    } else {
      return 0.33;
    }
  }

  /**
   * Gets the number of pieces in this problem instance specification.
   *
   * @return the number of pieces
   */
  public int getNumberOfPieces() {
    return this.inputPieces.length;
  }
}