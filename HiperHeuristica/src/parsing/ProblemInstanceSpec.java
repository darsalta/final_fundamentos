/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package parsing;

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
   * @param containerWidth the width for each container for piece placing.
   * @param containerHeight the height for each container for piece placing.
   * @param inputPieces the pieces defined to place for the problem.
   */
  public ProblemInstanceSpec(
          int containerWidth,
          int containerHeight,
          Piece[] inputPieces) {
    this.inputPieces = inputPieces;
    this.containerWidth = containerWidth;
    this.containerHeight = containerHeight;
  }

  /**
   * Gets a copy of the pieces of the problem specification.
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
}