package hiperheuristica;

import java.util.ArrayList;
import java.util.List;

/**
 * Clase que utiliza hiperheurísticas para acomodar piezas dentro de
 * contenedores.
 * 
 * BAD: This class does not have any state, it is not object-oriented.
 * TODO: Needs more tests.
 * @author Dra. Eunice Lopez, modifications by Priscila Angulo and Marcel Valdez
 */
public class HiperHeuristica {

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
    /// De mayor a menor
    inputPieces.sort(Order.DESCENDING);
    List<PieceContainer> containers = new ArrayList<PieceContainer>();
    while (inputPieces.size() > 0) {
      PieceContainer currentPieceContainer = openNewPieceContainer(
              containers,
              widthPieceContainer,
              heightPieceContainer);
      /// Fills the container with the minimum pieces to fill the initialCapacity
      placeWithInitialCapacity(inputPieces, currentPieceContainer, initialCapacity);
      /// Fills the remaining space with best fit of pieces possible.
      fillPieceContainerRemainder(inputPieces, currentPieceContainer, increment);
    }

    return containers;
  }

  /**
   * HeurísticaBL, trata de colocar piece en container abajo a la izquierda,
   * empezando a intentar desde arriba a la derecha.
   *
   * @param container
   * @param piece
   * @return true if the piece fits, false otherwise.
   */
  private static boolean tryPlaceInBottomLeft(
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
   * TODO: Needs testing, high risk method. Mueve la piece hasta una posicion
   * estable lo más abajo y a la izquierda posible. Devuelve TRUE si hubo
   * movimiento y FALSE si no hubo. REFACTOR: Mover este método a PieceContainer
   * o Piece.
   *
   * @param container whose bounds are used.
   * @param piece to move.
   * @return true if the piece was moved, false otherwise.
   */
  private static boolean movePieceToLowerLeft(
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
      /**
       * Distancia hacia la izquierda que puede moverse la piece hasta topar.
       */
      distToLeft = container.distanceToLeftBound(piece);
      if (distToLeft > 0) {
        piece.moveDistance(distToLeft, Direction.LEFT);
        totalHorDistance += distToLeft;
      }
      // Por qué while? No debería moverse el máximo con una sola
      // iteración? No, porque va intentar mover el objeto hacia abajo e
      // izquierda hasta que ya no pueda moverse más.
    } while (distToLeft > 0 || distToBott > 0);

    // Si la pieza no cupo dentro del PieceContainer, debemos regresarla a su lugar.
    if (!container.isWithinBounds(piece)) {
      piece.moveDistance(totalHorDistance, Direction.RIGHT);
      piece.moveDistance(totalVertDistance, Direction.UP);
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

    if (tryFitOnePiece(descOrderPieces, container, maxWaste)) {
      return true;
    }

    if (descOrderPieces.size() > 1
            && tryFitTwoPieces(descOrderPieces, container, maxWaste)) {
      return true;
    }

    if (descOrderPieces.size() > 2
            && tryFitThreePieces(descOrderPieces, container, maxWaste)) {
      return true;
    }

    return false;
  }

  /**
   * TODO: Needs testing, this method is high risk. Indica si puede o no poner
   * una piece en el container, dejando un máximo de desperdicio maxWaste. SE
   * ASUME QUE LA LISTA ESTÁ DADA ORDENADA DE MAYOR A MENOR
   */
  private static boolean tryFitOnePiece(
          PieceList descOrderPieces,
          PieceContainer container,
          int maxWaste) throws Exception {
    assert (descOrderPieces.get(0).equals(descOrderPieces.getBiggest()));

    for (int i = 0; i < descOrderPieces.size(); i++) {
      Piece piece = descOrderPieces.get(i);
      if (container.getFreeArea() - piece.getArea() > maxWaste) {
        /**
         * Si con una piece deja más desperdicio que maxWaste, con las demás
         * también lo hará (dado q están ordenadas).
         */
        break;
      }

      if (tryPlaceInBottomLeft(container, piece)) {
        container.putPiece(piece);
        descOrderPieces.remove(piece);
        // Indica que ya acomodó piece.
        return true;
      }
    }

    return false;
  }

  /**
   * TODO: Needs testing, this method is high risk. Indica si puede o no poner
   * dos pieces en el container, dejando un máximo de desperdicio maxWaste. SE
   * ASUME QUE LA LISTA ESTÁ DADA ORDENADA DE MAYOR A MENOR
   */
  private static boolean tryFitTwoPieces(
          PieceList descOrderPieces,
          PieceContainer container,
          int maxWaste) throws Exception {
    /// Assert descOrderPieces are actually in descending order.
    assert (descOrderPieces.get(0).equals(descOrderPieces.getBiggest()));

    /// Guardará el área de las 2 pieces más grandes.
    int largestSz, secondLargeSz;
    /// Guardará el área de la piece más pequeña.
    int smallestSz;

    smallestSz = descOrderPieces.get(descOrderPieces.size() - 1)
            .getArea();

    largestSz = descOrderPieces.get(0).getArea();
    secondLargeSz = descOrderPieces.get(1).getArea();
    /**
     * Verificando si cabrían 2 pieces con ese desperdicio máximo permitido. SE
     * SUPONE QUE ESTÁN ORDENADAS DE MAYOR A MENOR (se revisan las 2 pieces más
     * grandes).
     */
    if ((container.getFreeArea() - largestSz - secondLargeSz) > maxWaste) {
      return false;
    }

    PieceContainer tempPieceContainer = container.getCopy();
    for (int i = 0; i < descOrderPieces.size(); i++) {
      Piece candidateI = descOrderPieces.get(i);

      if (tempPieceContainer.getFreeArea()
              - candidateI.getArea()
              - largestSz > maxWaste && i > 0) {
        /**
         * Con pieceI y la más grande dejan más waste, ya no tiene caso probar +
         * pieces1.
         */
        break;
      }

      if (candidateI.getArea() + smallestSz > tempPieceContainer.getFreeArea()) {
        /**
         * A la siguiente pieceI. pieceI + la mas chica se pasarían del área
         * disponible.
         */
        continue;
      }

      if (tryPlaceInBottomLeft(tempPieceContainer, candidateI)) {
        /**
         * Se añade piece como 'borrador'. Se utiliza para poder estimar si
         * cabrán dos piezas en el contenedor, sin solapar entre sí, ni con las
         * demás ya dentro.
         *
         * La Dra. Eunice quería dejar claro que no agregaba definitivamente la
         * pieza al contenedor, y por ende no cambia el área libre, sino que se
         * trata de un acomodo hipotético dentro del contendor (un intento de
         * permutación).
         */
        tempPieceContainer.putPiece(candidateI);
        /// No altera el FreeArea de container.

        /**
         * Si puede acomodar pieceI, prueba con cuál candidateJ entra
         * simultáneamente.
         */
        for (int j = 0; j < descOrderPieces.size(); j++) {
          if (i == j) {
            continue;
          }

          Piece candidateJ = descOrderPieces.get(j);

          if (tempPieceContainer.getFreeArea()
                  - candidateJ.getArea() > maxWaste) {
            /**
             * If current candidateJ already wastes more spaces than maxWaste,
             * then the next pieces will also waste even more space, so break
             * the loop.
             */
            break;
          }

          if (tempPieceContainer.getFreeArea() < candidateJ.getArea()) {
            /// Try with next piece, this one is too big.
            continue;
          }

          if (tryPlaceInBottomLeft(container, candidateJ)) {
            /// Se añade definitivamente.
            container.putPiece(candidateI);
            container.putPiece(candidateJ);
            descOrderPieces.remove(candidateI);
            descOrderPieces.remove(candidateJ);
            // Indica que ya acomodó 2 pieces.
            return true;
          }
        } /// Termina de revisar posibles pieces 2.

        /// Ninguna candidateJ entró con la posible pieceI.  
        tempPieceContainer.removePiece(candidateI);
        /// Se borra el preliminar de pieceI.
      }
    }  /// Termina de revisar posibles pieces 1.

    return false;
  }

  /**
   * TODO: Needs testing, this method is high risk. Indica si puede o no poner
   * tres pieces en el container, dejando un máximo de desperdicio maxWaste. SE
   * ASUME QUE LA LISTA ESTÁ DADA ORDENADA DE MAYOR A MENOR
   */
  private static boolean tryFitThreePieces(
          PieceList descOrderPieces,
          PieceContainer container,
          int maxWaste) throws Exception {
    /// Assert descOrderPieces are actually in descending order.
    assert (descOrderPieces.get(0).equals(descOrderPieces.getBiggest()));

    /// Guardará el área de las 3 pieces más grandes.
    int largestSz, secondLargeSz, thirdLargeSz;
    /// Guardará el área de las 2 pieces más pequeñas.
    int smallestSz, secondSmallSz;

    smallestSz = descOrderPieces.get(descOrderPieces.size() - 1)
            .getArea();
    secondSmallSz = descOrderPieces.get(descOrderPieces.size() - 2)
            .getArea();

    largestSz = descOrderPieces.get(0).getArea();
    secondLargeSz = descOrderPieces.get(1).getArea();
    thirdLargeSz = descOrderPieces.get(2).getArea();

    /**
     * Verificando si cabrían 3 pieces con ese desperdicio máximo permitido.
     *
     * SE SUPONE QUE ESTÁN ORDENADAS DE MAYOR A MENOR (se revisan las pieces más
     * grandes).
     */
    if (container.getFreeArea()
            - largestSz
            - secondLargeSz
            - thirdLargeSz > maxWaste) {
      return false;
    }

    /**
     * PieceContainer used for calculating how the pieces will be placed in the
     * actual container.
     *
     * Se utilizar una copia de container, y se opera *directamente* sobre ese,
     * y si se encuentra que sí caben, entonces se modifica el contenedor
     * original (container).
     */
    PieceContainer tempPieceContainer = container.getCopy();
    for (int i = 0; i < descOrderPieces.size(); i++) {
      Piece candidateI = descOrderPieces.get(i);
      if (tempPieceContainer.getFreeArea()
              - candidateI.getArea()
              - largestSz
              - secondLargeSz > maxWaste && i > 1) {
        /**
         * Esa candidato I no es 'compatible' con ningun otro par de piezas sin
         * pasarse del desperdicio máximo permitido.
         */
        break;
      }

      if (tempPieceContainer.getFreeArea()
              < candidateI.getArea() + smallestSz + secondSmallSz) {
        /**
         * A la siguiente candidato I. candidato I + las 2 más chicas se
         * pasarían del área libre disponible.
         */
        continue;
      }

      if (tryPlaceInBottomLeft(tempPieceContainer, candidateI)) {
        tempPieceContainer.putPiece(candidateI);

        /**
         * Si puede acomodar candidato I, prueba con cuál candidato J entra
         * simultáneamente.
         */
        for (int j = 0; j < descOrderPieces.size(); j++) {
          if (j == i) {
            continue;
          }

          Piece candidateJ = descOrderPieces.get(j);

          if (tempPieceContainer.getFreeArea()
                  - candidateJ.getArea()
                  - largestSz > maxWaste) {
            /**
             * Los candidatos I y J no son 'compatibles' con ningun otra pieza
             * sin pasarse del desperdicio máximo permitido.
             */
            break;
          }

          if (tempPieceContainer.getFreeArea()
                  < candidateJ.getArea() + smallestSz) {
            /**
             * A la siguiente candidato J: candidato J + MásChica se pasarían
             * del área libre.
             */
            continue;
          }

          if (tryPlaceInBottomLeft(tempPieceContainer, candidateJ)) {
            tempPieceContainer.putPiece(candidateJ);
            for (int k = 0; k < descOrderPieces.size(); k++) {
              if (k == i || k == j) {
                continue;
              }

              Piece candidateK = descOrderPieces.get(k);

              if (tempPieceContainer.getFreeArea()
                      - candidateK.getArea() > maxWaste) {
                /**
                 * Si con candidatoK elegida se deja más waste, con las
                 * siguiente candidatesK (más chicas) también lo haría. Deja de
                 * revisar candidatos K y se pasa al siguiente candidato J.
                 */
                break;
              }

              /// Misma advertencia que en función 'dospieces'
              if (tempPieceContainer.getFreeArea()
                      < candidateK.getArea()) {
                /// A la siguiente piece 3.					
                continue;
              }

              if (tryPlaceInBottomLeft(tempPieceContainer, candidateK)) {
                /// Se añaden definitivamente.
                container.putPiece(candidateI);
                container.putPiece(candidateJ);
                container.putPiece(candidateK);
                descOrderPieces.remove(candidateI);
                descOrderPieces.remove(candidateJ);
                descOrderPieces.remove(candidateK);
                /// Indica que ya acomodó 3 pieces.
                return true;
              }
            } /// Termina de revisar posibles pieces 3.

            /// Ningun candidato K entró con los candidatos I y J
            /// Se quita le candidato J
            tempPieceContainer.removePiece(candidateJ);
          }

        } /// Termina de revisar posibles pieces 2.

        /// Ningun par de candidatos J y K entró con la posible pieceI.  
        /// Se quita el candidato I
        tempPieceContainer.removePiece(candidateI);
      }
    }  /// termina de revisar posibles pieces 1.

    return false;
  }

  /**
   * Opens a new container meant to be used to add more pieces to it. TODO:
   * Pending implementation.
   *
   * @param containers
   * @param xCoord
   * @param yCoord
   * @return
   */
  PieceContainer openNewPieceContainer(
          List<PieceContainer> containers,
          int widthPieceContainer,
          int heightPieceContainer) {
    PieceContainer container = new PieceContainer(widthPieceContainer, heightPieceContainer);
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
    for (int i = 0; i < inputPieces.size(); i++) {
      // initialCapacity = 1/4 o 1/3
      if (container.getUsedArea() > container.getArea() * initialCapacity) {
        break;
      }

      Piece piece = inputPieces.get(i);
      if (piece.getArea() <= container.getFreeArea()) {
        /* *
         * true o false, dependiendo si se puede acomodar 
         * la piece.                        
         */
        if (tryPlaceInBottomLeft(container, piece)) {
          container.putPiece(piece);
          inputPieces.remove(piece);
          /**
           * HACK: synchronize i counter with inputPieces size so it doesn't
           * skip pieces
           */
          i--;
        }
      }
    }
  }

  /**
   * Fills the remaining space with best fit of pieces possible.
   *
   * @param descOrderPieces
   * @param container
   * @param increment
   */
  private void fillPieceContainerRemainder(
          PieceList descOrderPieces,
          PieceContainer container,
          int increment) throws Exception {
    if (descOrderPieces.areAllBiggerThan(container.getFreeArea())) {
      /**
       * Si por area libre, ya no cabe ninguna piece, se pasa al otro objeto, en
       * caso de haber.
       */
      return;
    }

    /// Desperdicio
    int maxAllowedWaste = 0;
    /**
     * Pasa a otro objeto si no encuentra 1, 2 o 3 piezas que quepan en el
     * objeto actual.
     */
    while (maxAllowedWaste <= container.getFreeArea()) {
      if (tryFitPieces(descOrderPieces, container, maxAllowedWaste)) {
        break;
      }

      maxAllowedWaste += increment;
    }
  }
}
