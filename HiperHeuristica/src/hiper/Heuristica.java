package hiper;

import java.util.ArrayList;
import java.util.List;

/**
 * Clase que utiliza hiperheurísticas para acomodar piezas dentro de
 * contenedores.
 *
 * BAD: This class does not have any state, it is not object-oriented. TODO:
 * Needs more tests.
 *
 * @author Dra. Eunice Lopez, modifications by Priscila Angulo and Marcel Valdez
 */
public class Heuristica {

  /**
   * Implementa DJD. REFACTOR: Needs simplification and method extraction.
   */
  public List<PieceContainer> DJD(
          PieceList inputPieces,
          int widthPieceContainer,
          int heightPieceContainer,
          double initialCapacity) throws Exception {

    /// El desperdicio se incrementa en 1/20 del container.
    int increment = (widthPieceContainer * heightPieceContainer) / 20;
    if (increment == 0) {
      increment = 1;
    }
    /// De mayor a menor
    inputPieces.sort(Order.DESCENDING);
    List<PieceContainer> containers = new ArrayList<PieceContainer>();
    while (inputPieces.size() > 0) {
      PieceContainer currentContainer = openNewPieceContainer(
              containers,
              widthPieceContainer,
              heightPieceContainer);
      /// Fills the container with the minimum pieces to fill the initialCapacity
      placeWithInitialCapacity(inputPieces, currentContainer, initialCapacity);
      /// Fills the remaining space with best fit of pieces possible.
      fillPieceContainerRemainder(inputPieces, currentContainer, increment);
    }

    return containers;
  }

  /**
   * Trata de colocar piece en container abajo a la izquierda, empezando a
   * intentar desde arriba a la derecha.
   *
   * @param container
   * @param piece
   * @return true if the piece fits, false otherwise.
   */
  static boolean tryPlaceInBottomLeft(
          PieceContainer container,
          Piece piece) throws Exception {
    /**
     * Coloca la piece en la parte superior derecha del container, justo afuera
     * del container.
     */
    piece.moveDistance(
            container.getRightBound() - piece.getRightBound(),
            Direction.RIGHT);
    piece.moveDistance(
            container.getTopBound() - piece.getBottBound(),
            Direction.UP);

    return movePieceToLowerLeft(container, piece);
  }

  /**
   * Mueve la piece hasta una posicion estable lo más abajo y a la izquierda
   * posible. Devuelve TRUE si hubo movimiento y FALSE si no hubo. Este metodo
   * permanece en esta clase, porque es parte de la inteligencia del dominio
   * para esta heuristica.
   *
   * @param container whose bounds are used.
   * @param piece to move.
   * @return true if the piece was moved, false otherwise.
   */
  static boolean movePieceToLowerLeft(
          PieceContainer container,
          Piece piece) throws Exception {
    int totalVertDistance = 0, totalHorDistance = 0;
    int distToBott, distToLeft;
    do {
      /// Distancia hacia abajo que puede moverse la piece hasta topar.
      distToBott = container.distanceToBottBound(piece);
      if (distToBott > 0) {
        piece.moveDistance(distToBott, Direction.DOWN);
        totalVertDistance += distToBott;
      }

      /// Distancia hacia la izquierda que puede moverse la piece hasta topar.
      distToLeft = container.distanceToLeftBound(piece);
      if (distToLeft > 0) {
        piece.moveDistance(distToLeft, Direction.LEFT);
        totalHorDistance += distToLeft;
      }
      // Va intentar mover el objeto hacia abajo e izquierda hasta que ya no 
      // pueda moverse más.
    } while (distToLeft > 0 || distToBott > 0);

    // Si la pieza no cupo dentro del PieceContainer
    if (!container.isWithinBounds(piece)) {
      // Debemos regresar la pieza a su lugar.
      piece.moveDistance(totalHorDistance, Direction.RIGHT);
      piece.moveDistance(totalVertDistance, Direction.UP);
      // actualizar la distancia que se movio
      totalHorDistance = 0;
      totalVertDistance = 0;
    }

    /// Si no reacomoda la piece (si no cambió de posición)
    return totalHorDistance > 0 || totalVertDistance > 0;
  }

  /**
   * Tries to fit one, two or three pieces with a given maximum waste.
   *
   * @param descOrderPieces from which to select.
   * @param container in which to fit.
   * @param maxWaste to lose with piece selection.
   * @return true if it found and put 1, 2 or 3 pieces that it could fit into
   * container with maximum waste maxWaste, false otherwise.
   */
  private static boolean tryFitPieces(
          PieceList descOrderPieces,
          PieceContainer container,
          int maxWaste) throws Exception {

    if (tryFitPiecesRec(descOrderPieces, container, 1, maxWaste)) {
      return true;
    }

    if (descOrderPieces.size() > 1
            && tryFitPiecesRec(descOrderPieces, container, 2, maxWaste)) {
      return true;
    }

    if (descOrderPieces.size() > 2
            && tryFitPiecesRec(descOrderPieces, container, 3, maxWaste)) {
      return true;
    }

    return false;
  }

  /**
   * TODO: Needs testing, this method is high risk. Indica si puede o no poner
   * una piece en el container, dejando un máximo de desperdicio maxWaste. SE
   * ASUME QUE LA LISTA ESTÁ DADA ORDENADA DE MAYOR A MENOR
   */
  private static boolean tryFitPiecesRec(
          PieceList descOrderPieces,
          PieceContainer container,
          int piecesToFit,
          int maxWaste) throws Exception {
    /// Assert descending order for the first piece (at least)
    assert (descOrderPieces.get(0).equals(descOrderPieces.getBiggest()));

    if (piecesToFit == 0) {
      // Done placing pieces
      return true;
    }

    int bigPiecesArea = descOrderPieces.getAreaOfFirst(piecesToFit - 1);
    int smallPiecesArea = descOrderPieces.getAreaOfLast(piecesToFit - 1);

    for (int i = 0; i < descOrderPieces.size(); i++) {
      Piece candidate = descOrderPieces.get(i);

      /// If the area(candidate) + area(biggest pieces) leaves too much waste

      if (container.getFreeArea()
              - candidate.getArea()
              - bigPiecesArea > maxWaste) {
        /// Stop because the rest of the pieces will also leave too much waste.
        break;
      }

      /// If area(current candidate) + area(smallest pieces) does not fit
      if (container.getFreeArea() < candidate.getArea() + smallPiecesArea) {
        /// Try with a smaller piece
        continue;
      }

      /// If can place the current piece without colisioning
      if (tryPlaceInBottomLeft(container, candidate)) {
        /// Place the piece
        container.putPiece(candidate);
        descOrderPieces.remove(candidate);
        /// If successful at fitting the remaining pieces
        if (tryFitPiecesRec(
                descOrderPieces,
                container,
                piecesToFit - 1, maxWaste)) {
          /// Could place candidate piece!
          return true;
        } else {
          /// Unsuccessful at fitting the candidate with the remaining pieces.
          /// Remove the candidate from the container.
          container.removePiece(candidate);
          /// Re-insert in the piece list in the same order it previously was.
          descOrderPieces.insertAt(i, candidate);
        }
      }
    }
    /// Could not fit any pieces
    return false;
  }

  /**
   * Opens a new container meant to be used to add more pieces to it.
   *
   * @param containers
   * @param containerWidth
   * @param containerHeight
   * @return the newly added PieceContainer
   */
  PieceContainer openNewPieceContainer(
          List<PieceContainer> containers,
          int containerWidth,
          int containerHeight) {
    PieceContainer container = new PieceContainer(
            containerWidth, containerHeight);
    containers.add(container);

    return container;
  }

  /**
   * Fills the container with the minimum pieces to fill the initialCapacity
   *
   * @param inputPieces
   * @param container
   * @param initialCapacity
   */
  private void placeWithInitialCapacity(
          PieceList inputPieces,
          PieceContainer container,
          double initialCapacity) throws Exception {
    /// Recorre de mayor a menor, dado que pieces está en orden DESC
    for (Piece piece : inputPieces) {
      // initialCapacity = 1/4 o 1/3
      if (container.getUsedArea() > container.getArea() * initialCapacity) {
        break;
      }

      if (piece.getArea() <= container.getFreeArea()) {
        /* *
         * true o false, dependiendo si se puede acomodar la pieza.                        
         */
        if (tryPlaceInBottomLeft(container, piece)) {
          container.putPiece(piece);
        }
      }
    }

    /// Remove all pieces put in the container.
    for (Piece piece : container) {
      inputPieces.remove(piece);
    }
  }

  /**
   * Fills the remaining space with best fit of pieces possible.
   *
   * @param descOrderPieces
   * @param container
   * @param wasteIncrement
   */
  private void fillPieceContainerRemainder(
          PieceList descOrderPieces,
          PieceContainer container,
          int wasteIncrement) throws Exception {
    if (descOrderPieces.areAllBiggerThan(container.getFreeArea())) {
      /**
       * Si por area libre, ya no cabe ninguna piece, se pasa al otro
       * contenedor, en caso de haber.
       */
      return;
    }

    /// Desperdicio máximo permitido
    int maxAllowedWaste = 0;
    /// Keep increasing allowed waste until we find pieces that waste the 
    /// allowed amount or the waste has reached its max (free area).
    while (!tryFitPieces(descOrderPieces, container, maxAllowedWaste)
            && maxAllowedWaste <= container.getFreeArea()) {
      maxAllowedWaste += wasteIncrement;
    }
  }
}
