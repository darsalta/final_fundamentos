package hiperheuristica;

import java.util.List;

public class HiperHeuristica {

    /**
     * HeurísticaBL, trata de colocar piece en container abajo a la izquierda,
     * empezando a intentar desde arriba a la derecha.
     *
     * @param container
     * @param piece
     * @return true if the piece fits, false otherwise.
     */
    private static boolean tryAllocateInBottomLeft(Objeto container, Pieza piece) {
        /**
         * Coloca la piece en la parte superior derecha del container, justo
         * afuera del container.
         */
        piece.moveDistance(
                container.getRightBound() - piece.getRightBound(),
                Direction.RIGHT);
        piece.moveDistance(
                container.getTopBound() - piece.getBottBound(),
                Direction.UP);

        return movePieceToObjectLowerLeft(container, piece);
    }

    /**
     * TODO: Needs testing, high risk method. Mueve la piece hasta una posicion
     * estable lo más abajo y a la izquierda posible. Devuelve TRUE si hubo
     * movimiento y FALSE si no hubo. REFACTOR: Mover este método a Objeto o
     * Pieza.
     *
     * @param container whose bounds are used.
     * @param piece to move.
     * @return true if the piece was moved, false otherwise.
     */
    private static boolean movePieceToObjectLowerLeft(Objeto container, Pieza piece) {
        int totalVertDistanceMoved = 0;
        int totalHorDistanceMoved = 0;
        int distToBott;
        int distToLeft;
        do {
            /// Distancia hacia abajo que puede moverse la piece hasta topar.            
            distToBott = container.distanceToBottBound(piece);
            if (distToBott > 0) {
                piece.moveDistance(distToBott, Direction.DOWN);
                totalVertDistanceMoved += distToBott;
            }
            /**
             * Distancia hacia la izquierda que puede moverse la piece hasta
             * topar.
             */
            distToLeft = container.distanceToLeftBound(piece);
            if (distToLeft > 0) {
                piece.moveDistance(distToLeft, Direction.LEFT);
                totalHorDistanceMoved += distToLeft;
            }
            // Por qué while? No debería moverse el máximo con una sola
            // iteración? No, porque va intentar mover el objeto hacia abajo e
            // izquierda hasta que ya no pueda moverse más.
        } while (distToLeft > 0 || distToBott > 0);

        // Si la pieza no cupo dentro del Objeto, debemos regresarla a su lugar.
        if (!container.isWithinBounds(piece)) {
            piece.moveDistance(totalHorDistanceMoved, Direction.RIGHT);
            piece.moveDistance(totalVertDistanceMoved, Direction.UP);
            totalHorDistanceMoved = 0;
            totalVertDistanceMoved = 0;
        }

        /// Si no reacomoda la piece (si no cambió de posición)
        return totalHorDistanceMoved > 0 || totalVertDistanceMoved > 0;
    }

    /**
     * Implementa DJD. REFACTOR: Needs simplification and method extraction.
     */
    public void DJD(
            PieceList inputPieces,
            List<Objeto> containers,
            int xObjeto,
            int yObjeto,
            double initialCapacity) throws Exception {
        /// El desperdicio se incrementa en 1/20 del container.
        int increment = containers.get(0).getArea() / 20;
        /// De mayor a menor
        inputPieces.sort(Order.DESCENDING);

        /**
         * Revisa containers con menos de CapInicial para meter una sola piece.
         * En alguna HH podría ser necesario revisar varios containers.
         */
        for (int j = containers.size() - 1; j < containers.size(); j++) {
            Objeto container = containers.get(j);
            // initialCapacity = 1/4 o 1/3
            if (container.getUsedArea() < container.getArea() * initialCapacity) {
                /// Recorre de mayor a menor, dado que pieces está en orden DESC
                for (int i = 0; i < inputPieces.size(); i++) {
                    Pieza piece = inputPieces.get(i);
                    if (piece.getArea() <= container.getFreeArea()) {
                        /* *
                         * true o false, dependiendo si se puede acomodar 
                         * la piece.                        
                         * */
                        if (tryAllocateInBottomLeft(container, piece)) {
                            container.addPiece(piece);
                            inputPieces.remove(piece);
                            return;
                        }
                    }
                }
            }
        }

        /**
         * No hubo containers con menos de 1/3 de capacidad, o bien, ninguna
         * piece cupo en un container con menos de 1/3 de capacidad, lo que
         * podría ocurrir.
         */
        /// En alguna HH podría ser necesario revisar varios containers
        for (int j = containers.size() - 1; j < containers.size(); j++) {
            Objeto container = containers.get(j);

            if (inputPieces.areAllBiggerThan(container.getFreeArea())) {
                /**
                 * Si por area libre, ya no cabe ninguna piece, se pasa al otro
                 * objeto, en caso de haber.
                 */
                continue;
            }

            /// Desperdicio
            int waste = 0;
            /**
             * Pasa a otro objeto si no encuentra 1, 2 o 3 piezas que quepan en
             * el objeto actual.
             */
            while (waste <= container.getFreeArea()) {
                if (tryFitPieces(inputPieces, container, waste)) {
                    // ¿Se sale a la primera que encuentra piezas que puede
                    // meter en un objeto? No tiene sentido, ¿qué pasa si quedan
                    // más piezas?
                    return;
                }

                waste += increment;
            }
        }


        Objeto newObject = openNewObject(containers, xObjeto, yObjeto);
        Pieza biggest = inputPieces.getBiggest();
        /// Si el container es nuevo, siempre debería poder acomodar la piece.
        /// ¿A menos que la piece sea más ancha o alta que el objeto?
        if (tryAllocateInBottomLeft(newObject, biggest)) {
            newObject.addPiece(biggest);
            inputPieces.remove(biggest);
        } else {
            throw new Exception("A new Objeto should always be empty and every\n"
                    + " Pieza should always be smaller than an Objeto.");
        }
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
            Objeto container,
            int maxWaste) {
        /**
         * BAD: Should these three be in the same method, and return a single
         * boolean value, which then can be used to exit this method?
         */
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
     * Indica si puede o no poner una piece en el container, dejando un máximo
     * de desperdicio maxWaste. SE ASUME QUE LA LISTA ESTÁ DADA ORDENADA DE
     * MAYOR A MENOR
     */
    private static boolean tryFitOnePiece(
            PieceList descOrderPieces,
            Objeto container,
            int maxWaste) {
        assert (descOrderPieces.get(0).equals(descOrderPieces.getBiggest()));
        int freeArea = container.getFreeArea();

        for (int i = 0; i < descOrderPieces.size(); i++) {
            Pieza piece = descOrderPieces.get(i);
            if ((freeArea - piece.getArea()) > maxWaste) {
                /**
                 * Si con una piece deja más desperdicio que maxWaste, con las
                 * demás también lo hará (dado q están ordenadas).
                 */
                break;
            }

            if (tryAllocateInBottomLeft(container, piece)) {
                container.addPiece(piece);
                descOrderPieces.remove(piece);
                // Indica que ya acomodó piece.
                return true;
            }
        }

        return false;
    }

    /**
     * Indica si puede o no poner dos pieces en el container, dejando un máximo
     * de desperdicio maxWaste. SE ASUME QUE LA LISTA ESTÁ DADA ORDENADA DE
     * MAYOR A MENOR
     */
    private static boolean tryFitTwoPieces(
            PieceList descOrderPieces,
            Objeto container,
            int maxWaste) {
        /// Assert descOrderPieces are actually in descending order.
        assert (descOrderPieces.get(0).equals(descOrderPieces.getBiggest()));

        /// Guardará el área de las 2 pieces más grandes.
        int largestSz, secondLargeSz;
        /// Guardará el área de la piece más pequeña.
        int smallestSz;
        int freeArea;
        smallestSz = descOrderPieces.get(descOrderPieces.size() - 1)
                .getArea();
        freeArea = container.getFreeArea();

        largestSz = descOrderPieces.get(0).getArea();
        secondLargeSz = descOrderPieces.get(1).getArea();
        /**
         * Verificando si cabrían 2 pieces con ese desperdicio máximo permitido.
         * SE SUPONE QUE ESTÁN ORDENADAS DE MAYOR A MENOR (se revisan las 2
         * pieces + grandes).
         *
         * REFACTOR: Este método podría ir en Object, algo como: int
         * calculateWaste(int addedArea)
         */
        if ((freeArea - largestSz - secondLargeSz) > maxWaste) {
            return false;
        }

        for (int i = 0; i < descOrderPieces.size(); i++) {
            Pieza pieceI = descOrderPieces.get(i);

            if (freeArea - pieceI.getArea() - largestSz > maxWaste) {
                /**
                 * Con pieceI y la más grande dejan más w, ya no tiene caso
                 * probar + pieces1.
                 */
                break;
            }

            if (pieceI.getArea() + smallestSz > freeArea) {
                /**
                 * A la siguiente pieceI. pieceI + la mas chica se pasarían del
                 * área disponible.
                 */
                continue;
            }

            if (tryAllocateInBottomLeft(container, pieceI)) {
                /**
                 * Se añade piece como 'borrador' Se utiliza para poder estimar
                 * si cabrán dos piezas en el contenedor, sin solapar entre sí,
                 * ni con las demás ya dentro.
                 *
                 * La Dra. Eunice quería dejar claro que no agregaba
                 * definitivamente la pieza al contenedor, y por ende no cambia
                 * el área libre, sino que se trata de un acomodo hipotético
                 * dentro del contendor (un intento de permutación).
                 */
                container.addCandidate(pieceI);
                /// No altera el FreeArea de container.

                /**
                 * Si puede acomodar pieceI, prueba con cuál pieceJ entra
                 * simultáneamente.
                 */
                for (int j = 0; j < descOrderPieces.size(); j++) {
                    if (i == j) {
                        continue;
                    }

                    Pieza pieceJ = descOrderPieces.get(j);

                    if (freeArea
                            - pieceI.getArea()
                            - pieceJ.getArea() > maxWaste) {
                        /**
                         * If current pieceJ already wastes more spaces than
                         * maxWaste, then the next pieces will also waste even
                         * more space, so break the loop.
                         */
                        break;
                    }

                    if (pieceI.getArea()
                            + pieceJ.getArea() > freeArea) {
                        /// Try with next piece, this one is too big.
                        continue;
                    }

                    if (tryAllocateInBottomLeft(container, pieceJ)) {
                        /// Se borra el pegado preliminar.
                        container.removeCandidate(pieceI);
                        /// Se añade definitivamente.
                        container.addPiece(pieceI);
                        container.addPiece(pieceJ);
                        descOrderPieces.remove(pieceI);
                        descOrderPieces.remove(pieceJ);
                        // Indica que ya acomodó 2 pieces.
                        return true;
                    }
                } /// Termina de revisar posibles pieces 2.

                /// Ninguna pieceJ entró con la posible pieceI.  
                container.removeCandidate(pieceI);
                /// Se borra el preliminar de pieceI.
            }
        }  /// Termina de revisar posibles pieces 1.

        return false;
    }

    /**
     * Indica si puede o no poner tres pieces en el container, dejando un máximo
     * de desperdicio maxWaste. SE ASUME QUE LA LISTA ESTÁ DADA ORDENADA DE
     * MAYOR A MENOR
     */
    private static boolean tryFitThreePieces(
            PieceList descOrderPieces,
            Objeto container,
            int maxWaste) {
        /// Assert descOrderPieces are actually in descending order.
        assert (descOrderPieces.get(0).equals(descOrderPieces.getBiggest()));

        /// Guardará el área de las 3 pieces más grandes.
        int largestSz, secondLargeSz, thirdLargeSz;
        /// Guardará el área de las 2 pieces más pequeñas.
        int smallestSz, secondSmallSz;
        /**
        * Utilizó la variable freeArea, porque se sintió insegura de que
        * al agregar una pieza 'preliminar', se modificaría el freeArea
        * calculado del Objeto
        */ 
        int freeArea;
        freeArea = container.getFreeArea();

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
         * SE SUPONE QUE ESTÁN ORDENADAS DE MAYOR A MENOR (se revisan las pieces
         * más grandes).
         */
        if ((freeArea
                - largestSz
                - secondLargeSz
                - thirdLargeSz) > maxWaste) {
            return false;
        }

        for (int i = 0; i < descOrderPieces.size(); i++) {
            Pieza candidateI = descOrderPieces.get(i);
            if (freeArea
                    - candidateI.getArea()
                    - largestSz
                    - secondLargeSz > maxWaste) {
                /**
                 * Esa pieceI no es 'compatible' con ningun otro par de pieces
                 * sin pasarse del desperdicio máximo permitido.
                 */
                break;
            }

            if (candidateI.getArea()
                    + smallestSz
                    + secondSmallSz > freeArea) {
                /**
                 * A la siguiente pieceI. pieceI + las2 más chicas se pasarían
                 * del área libre.
                 */
                continue;
            }

            /**
             * Antes decía: acomodo1 = (nextObject1, pieceI, H_acomodo1);
             */
            if (tryAllocateInBottomLeft(container, candidateI)) {
                /// Se añade pieceI como 'borrador'
                /// No altera el FreeArea de container.
                /**
                * Tal vez sea mejor utilizar una copia del Objeto, y llamarlo
                * hypotheticContainer, y operar *directamente* sobre ese, y
                * si se encuentra que sí caben, entonces se modifica el
                * contenedor original (container).
                */ 
                container.addCandidate(candidateI);

                /**
                 * Si puede acomodar pieceI, prueba con cuál pieceJ entra
                 * simultáneamente.
                 */
                for (int j = 0; j < descOrderPieces.size(); j++) {
                    if (i == j) {
                        continue;
                    }

                    Pieza candidateJ = descOrderPieces.get(j);

                    if (freeArea
                            - candidateI.getArea()
                            - candidateJ.getArea()
                            - largestSz > maxWaste) {
                        /**
                         * Las pieces I y J no son 'compatibles' con ninguna
                         * otra piece sin pasarse del desperdicio máximo
                         * permitido.
                         */
                        break;
                    }

                    if (candidateI.getArea()
                            + candidateJ.getArea()
                            + smallestSz > freeArea) {
                        /**
                         * A la siguiente pieceJ: pieceI + pieceJ + MásChica se
                         * pasarían del área libre.
                         */
                        continue;
                    }

                    if (tryAllocateInBottomLeft(container, candidateJ)) {
                        container.addCandidate(candidateJ);
                        for (int k = 0; k < descOrderPieces.size(); k++) {
                            if (k == i || k == j) {
                                continue;
                            }
                            
                            Pieza candidateK = descOrderPieces.get(k);

                            if (freeArea
                                    - candidateI.getArea()
                                    - candidateJ.getArea()
                                    - candidateK.getArea() > maxWaste) {
                                /**
                                 * Si con pieceK elegida se deja más waste, con 
                                 * las siguiente piecesK (más chicas) también lo
                                 * haría. Deja de revisar piecesK y se pasa a la
                                 * siguiente pieceJ.
                                 */
                                break;
                            }

                            /// Misma advertencia que en función 'dospieces'
                            if (candidateI.getArea()
                                    + candidateJ.getArea()
                                    + candidateK.getArea() > freeArea) {
                                /// A la siguiente piece 3.					
                                continue;
                            }

                            if (tryAllocateInBottomLeft(container, candidateK)) {
                                /// Se borra el pegado preliminar.
                                container.removeCandidate(candidateI);
                                container.removeCandidate(candidateJ);
                                /// Se añaden definitivamente.
                                container.addPiece(candidateI);
                                container.addPiece(candidateJ);
                                container.addPiece(candidateK);
                                descOrderPieces.remove(candidateI);
                                descOrderPieces.remove(candidateJ);
                                descOrderPieces.remove(candidateK);
                                /// Indica que ya acomodó 3 pieces.
                                return true;
                            }
                        } /// Termina de revisar posibles pieces 3.

                        /// Ninguna pieceK entró con la posible pieceI y pieceJ.  
                        container.removeCandidate(candidateJ);
                    }

                } /// Termina de revisar posibles pieces 2.

                /// Ningun par de pieces 2 y 3 entró con la posible pieceI.  
                container.removeCandidate(candidateI);
                /// Se borra el preliminar de pieceI
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
    Objeto openNewObject(List<Objeto> containers, int xCoord, int yCoord) {
        int x = 1 / 0;
        return null;
    }
}