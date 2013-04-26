package hiperheuristica;

import java.util.List;

public class HiperHeuristica {

    int vertices = 0;
    /**
     * Es el valor que devuelven los métodos de cercanía cuando una piece no
     * alcanza a la otra.
     */
    private static final int SIZE_LIMIT = 100000;
    /// TODO: Determinar qué es esto.
    int[] coordY = new int[100];
    /// TODO: Determinar qué es esto.
    int[] coordX = new int[100];

    public static void main(String[] args) {
    }

    /**
     * HeurísticaBL, trata de colocar piece en object
     *
     * @param object
     * @param piece
     * @return true if the piece fits, false otherwise.
     */
    private boolean BLHeuristic(Objeto object, Pieza piece) {
        /**
         * Coloca la piece en la parte superior derecha del object, justo afuera
         * del object.
         */
        int despX = object.getXmax() - piece.getXmax();
        // Qué está moviendo?
        moveDistance(despX, Direction.RIGHT);
        int despY = object.getYmax() - piece.getYmin();
        // Qué está moviendo?
        moveDistance(despY, Direction.UP);
        movePieceToObjectLowerLeft(object, piece);
        return object.isWithinBounds(piece);
    }

    /**
     * TODO: Determinar si este método es correcto aquí. Creo que sí, porque
     * aplica a la 'heurística', no al object.
     *
     * @param dist
     * @param dir
     */
    public void moveDistance(int dist, Direction dir) {
        /* Creo que así debe de ser la implementación para cada pieza. */
        for (int i = 0; i < vertices; i++) {
            switch (dir) {
                case UP:
                    coordY[i] += dist;
                    break;

                case DOWN:
                    coordY[i] -= dist;
                    break;

                case LEFT:
                    coordX[i] -= dist;
                    break;

                case RIGHT:
                    coordX[i] += dist;
                    break;
            }
        }
    }

    /**
     * Mueve la piece hasta una posicion estable lo más abajo y a la izquierda
     * posible. Devuelve TRUE si hubo movimiento y FALSE si no hubo. REFACTOR:
     * Mover este método a Objeto o Pieza.
     *
     * @param object whose bounds are used.
     * @param piece to move.
     * @return true if the piece was moved, false otherwise.
     */
    private boolean movePieceToObjectLowerLeft(Objeto object, Pieza piece) {
        int distVertical;
        int distHorizontal;
        int oldXmin = piece.getXmin();
        int oldYmin = piece.getYmin();

        do {
            /// Distancia hacia abajo que puede moverse la piece hasta topar.            
            distVertical = object.distanceToBottomBound(piece);
            if (distVertical > 0 && distVertical < SIZE_LIMIT) {
                piece.moveDistance(distVertical, Direction.DOWN);
            }
            /**
             * Distancia hacia la izquierda que puede moverse la piece hasta
             * topar.
             */
            distHorizontal = object.distanceToLeftBound(piece);
            if (distHorizontal > 0 && distHorizontal < SIZE_LIMIT) {
                piece.moveDistance(distHorizontal, Direction.LEFT);
            }
            // Por qué while? No debería moverse el máximo con una sola
            // iteración?
        } while ((distHorizontal > 0 && distHorizontal < SIZE_LIMIT)
                || (distVertical > 0 && distVertical < SIZE_LIMIT));

        /// Si no reacomoda la piece (si no cambió de posición)
        return oldXmin != piece.getXmin() || oldYmin != piece.getYmin();
    }

    /**
     * Implementa DJD. REFACTOR: Needs simplification and method extraction.
     */
    public void DJD(
            List<Pieza> pieces,
            List<Objeto> objects,
            int xObjeto,
            int yObjeto,
            double initialCapacity) throws Exception {
        /// El desperdicio se incrementa en 1/20 del object.
        int increment = (objects.get(0)).getTotalSize() / 20;
        /// Desperdicio
        int waste;
        /// De mayor a menor
        pieces = orderPieces(pieces, Order.DESCENDING);

        /**
         * Revisa objects con menos de CapInicial para meter una sola piece. En
         * alguna HH podría ser necesario revisar varios objects.
         */
        for (int j = objects.size() - 1; j < objects.size(); j++) {
            Objeto object = objects.get(j);
            // initialCapacity = 1/4 o 1/3
            if (object.getUsedArea() < object.getTotalSize() * initialCapacity) {
                /// De mayor a menor
                for (int i = 0; i < pieces.size(); i++) {
                    Pieza piece = pieces.get(i);
                    if (piece.getTotalSize() <= object.getFreeArea()) {
                        /* *
                         * true o false, dependiendo si se puede acomodar 
                         * la piece.                        
                         * */
                        if (BLHeuristic(object, piece)) {
                            object.addPiece(piece);
                            pieces.remove(piece);
                            return;
                        }
                    }
                }
            }
        }

        /**
         * No hubo objects con menos de 1/3 de capacidad, o bien, ninguna piece
         * cupo en un object con menos de 1/3 de capacidad, lo que podría
         * ocurrir.
         */
        /// En alguna HH podría ser necesario revisar varios objects
        for (int j = objects.size() - 1; j < objects.size(); j++) {

            Objeto object = objects.get(j);
            waste = 0;
            /// Decide cuándo pasar a otro object.
            boolean finish;

            if (areAllBiggerThan(object.getFreeArea(), pieces)) {
                /**
                 * Si por area libre, ya no cabe ninguna piece, se pasa al otro
                 * object, en caso de haber.
                 */
                continue;
            }

            do {
                /**
                 * BAD: Should these three be in the same method, and return a
                 * single boolean value, which then can be used to exit this
                 * method?
                 */
                if (tryFitOnePiece(pieces, object, waste)) {
                    return;
                }

                if (pieces.size() > 1
                        && tryFitTwoPieces(pieces, object, waste)) {
                    return;
                }

                if (pieces.size() > 2
                        && tryFitThreePieces(pieces, object, waste)) {
                    return;
                }

                finish = waste > object.getFreeArea();
                waste += increment;
            } while (!finish);
        }


        Objeto newObject = openNewObject(objects, xObjeto, yObjeto);
        Pieza greatestPiece = getBiggestPiece(pieces);
        /// Si el object es nuevo, siempre debería poder acomodar la piece.
        if (BLHeuristic(newObject, greatestPiece)) {
            newObject.addPiece(greatestPiece);
            pieces.remove(greatestPiece);
        } else {
            throw new Exception("A new Objeto should always be empty.");
        }
    }

    // Refactor: Se requiere que lista de piezas sea una clase del dominio,
    //           no de la librería Java, porque hay muchos métodos específicos
    //           para trabajar sobre una lista de Piezas.
    private static boolean areAllBiggerThan(
            int sizeThreshold,
            List<Pieza> descOrderPieces) {
        /**
         * Como se entrega la lista ordenada de mayor a menor, si se empieza a
         * buscar desde el último (piece más chica).
         */
        for (int i = descOrderPieces.size() - 1; i >= 0; i--) {
            // Devuelve un 'false' con menos comparaciones.            
            if (descOrderPieces.get(i).getTotalSize() <= sizeThreshold) {
                return false;
            }
        }

        return true;
    }

    /**
     * Indica si puede o no poner una piece en el object, dejando un máximo de
     * desperdicio maxWaste. SE ASUME QUE LA LISTA ESTÁ DADA ORDENADA DE MAYOR A
     * MENOR
     */
    private boolean tryFitOnePiece(
            List<Pieza> descOrderPieces,
            Objeto object,
            int maxWaste) {
        int freeArea = object.getFreeArea();

        for (int i = 0; i < descOrderPieces.size(); i++) {
            Pieza piece = descOrderPieces.get(i);
            if ((freeArea - piece.getTotalSize()) > maxWaste) {
                /**
                 * Si con una piece deja más desperdicio que w, con las demás
                 * también lo hará (dado q están ordenadas).
                 */
                break;
            }

            if (BLHeuristic(object, piece)) {
                object.addPiece(piece);
                descOrderPieces.remove(piece);
                // Indica que ya acomodó piece.
                return true;
            }
        }

        return false;
    }

    /**
     * Indica si puede o no poner dos pieces en el object, dejando un máximo de
     * desperdicio maxWaste. SE ASUME QUE LA LISTA ESTÁ DADA ORDENADA DE MAYOR A
     * MENOR
     */
    private boolean tryFitTwoPieces(
            List<Pieza> descOrderPieces,
            Objeto object,
            int maxWaste) {
        /// Guardará el área de las 2 pieces más grandes.
        int largestSz, secondLargeSz;
        /// Guardará el área de la piece más pequeña.
        int smallestSz;
        int freeArea;
        smallestSz = descOrderPieces.get(descOrderPieces.size() - 1)
                .getTotalSize();
        freeArea = object.getFreeArea();

        largestSz = descOrderPieces.get(0).getTotalSize();
        secondLargeSz = descOrderPieces.get(1).getTotalSize();
        /**
         * Verificando si cabrían 2 pieces con ese desperdicio máximo permitido.
         * SE SUPONE QUE ESTÁN ORDENADAS DE MAYOR A MENOR (se revisan las 2
         * pieces + grandes).
         */
        if ((freeArea - largestSz - secondLargeSz) > maxWaste) {
            return false;
        }

        for (int i = 0; i < descOrderPieces.size(); i++) {
            Pieza pieceI = descOrderPieces.get(i);

            if (freeArea - pieceI.getTotalSize() - largestSz > maxWaste) {
                /**
                 * Con piece1 y la más grande dejan más w, ya no tiene caso
                 * probar + pieces1.
                 */
                break;
            }

            if (pieceI.getTotalSize() + smallestSz > freeArea) {
                /**
                 * A la siguiente piece 1. piece1 + la mas chica se pasarían del
                 * área disponible.
                 */
                continue;
            }

            if (BLHeuristic(object, pieceI)) {
                /// Se añade piece como 'borrador'
                object.addPreliminaryPiece(pieceI);
                /// No altera el FreeArea de object.

                /**
                 * Si puede acomodar piece1, prueba con cuál piece2 entra
                 * simultáneamente.
                 *
                 */
                for (int j = i + 1; j < descOrderPieces.size(); j++) {
                    Pieza pieceJ = descOrderPieces.get(j);

                    if (freeArea
                            - pieceI.getTotalSize()
                            - pieceJ.getTotalSize() > maxWaste) {
                        /**
                         * If current pieceJ already wastes more spaces than
                         * maxWaste, then the next pieces will also waste even
                         * more space, so break the loop.
                         */
                        break;
                    }

                    if (pieceI.getTotalSize()
                            + pieceJ.getTotalSize() > freeArea) {
                        /// Try with next piece, this one is too big.
                        continue;
                    }

                    if (BLHeuristic(object, pieceJ)) {
                        /// Se borra el pegado preliminar.
                        object.removePreliminaryPiece(pieceI);
                        /// Se añade definitivamente.
                        object.addPiece(pieceI);
                        object.addPiece(pieceJ);
                        descOrderPieces.remove(pieceI);
                        descOrderPieces.remove(pieceJ);
                        // Indica que ya acomodó 2 pieces.
                        return true;
                    }
                } /// Termina de revisar posibles pieces 2.

                /// Ninguna piece2 entró con la posible piece1.  
                object.removePreliminaryPiece(pieceI);
                /// Se borra el preliminar de piece1.
            }
        }  /// Termina de revisar posibles pieces 1.

        return false;
    }

    /**
     * Indica si puede o no poner tres pieces en el object, dejando un máximo de
     * desperdicio w. SE ASUME QUE LA LISTA ESTÁ DADA ORDENADA DE MAYOR A MENOR
     */
    private boolean tryFitThreePieces(
            List<Pieza> descOrderPieces,
            Objeto object,
            int maxWaste) {
        /// Guardará el área de las 3 pieces más grandes.
        int largestSz, secondLargeSz, thirdLargeSz;
        /// Guardará el área de las 2 pieces más pequeñas.
        int smallestSz, secondSmallSz;
        int freeArea;

        freeArea = object.getFreeArea();

        smallestSz = descOrderPieces.get(descOrderPieces.size() - 1)
                .getTotalSize();
        secondSmallSz = descOrderPieces.get(descOrderPieces.size() - 2)
                .getTotalSize();
        largestSz = descOrderPieces.get(0).getTotalSize();
        secondLargeSz = descOrderPieces.get(1).getTotalSize();
        thirdLargeSz = descOrderPieces.get(2).getTotalSize();
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
            Pieza pieceI = descOrderPieces.get(i);
            if (freeArea
                    - pieceI.getTotalSize()
                    - largestSz
                    - secondLargeSz > maxWaste) {
                /**
                 * Esa piece 1 no es 'compatible' con ningun otro par de pieces
                 * sin pasarse del desperdicio máximo permitido.
                 */
                break;
            }

            if (pieceI.getTotalSize()
                    + smallestSz
                    + secondSmallSz > freeArea) {
                /**
                 * A la siguiente piece 1. piece1 + las2 más chicas se pasarían
                 * del área libre.
                 */
                continue;
            }

            /**
             * Antes decía: acomodo1 = (nextObject1, piece1, H_acomodo1);
             */
            if (BLHeuristic(object, pieceI)) {
                /// Se añade piece1 como 'borrador'
                /// No altera el FreeArea de object.
                object.addPreliminaryPiece(pieceI);

                /**
                 * Si puede acomodar piece1, prueba con cuál piece2 entra
                 * simultáneamente.
                 */
                for (int j = i + 1; j < descOrderPieces.size(); j++) {
                    Pieza pieceJ = descOrderPieces.get(j);

                    if (freeArea
                            - pieceI.getTotalSize()
                            - pieceJ.getTotalSize()
                            - largestSz > maxWaste) {
                        /**
                         * Las pieces I y J no son 'compatibles' con ninguna
                         * otra piece sin pasarse del desperdicio máximo
                         * permitido.
                         */
                        break;
                    }

                    if (pieceI.getTotalSize()
                            + pieceJ.getTotalSize()
                            + smallestSz > freeArea) {
                        /**
                         * A la siguiente piece2 piece1 + piece2 + MásChica se
                         * pasarían el área libre.
                         */
                        continue;
                    }

                    if (BLHeuristic(object, pieceJ)) {
                        object.addPreliminaryPiece(pieceJ);
                        for (int k = j + 1; k < descOrderPieces.size(); k++) {
                            Pieza pieceK = descOrderPieces.get(k);

                            if (freeArea
                                    - pieceI.getTotalSize()
                                    - pieceJ.getTotalSize()
                                    - pieceK.getTotalSize() > maxWaste) {
                                /**
                                 * Si con piece3 elegida se deja + w, con las
                                 * siguiente pieces3 (más chicas) también lo
                                 * haría. Deja de revisar pieces3 y se pasa a la
                                 * siguiente piece2.
                                 */
                                break;
                            }

                            /// Misma advertencia que en función 'dospieces'
                            if (pieceI.getTotalSize()
                                    + pieceJ.getTotalSize()
                                    + pieceK.getTotalSize() > freeArea) {
                                /// A la siguiente piece 3.					
                                continue;
                            }

                            if (BLHeuristic(object, pieceK)) {
                                /// Se borra el pegado preliminar.
                                object.removePreliminaryPiece(pieceI);
                                object.removePreliminaryPiece(pieceJ);
                                /// Se añaden definitivamente.
                                object.addPiece(pieceI);
                                object.addPiece(pieceJ);
                                object.addPiece(pieceK);
                                descOrderPieces.remove(pieceI);
                                descOrderPieces.remove(pieceJ);
                                descOrderPieces.remove(pieceK);
                                /// Indica que ya acomodó 3 pieces.
                                return true;
                            }
                        } /// Termina de revisar posibles pieces 3.

                        /// Ninguna piece3 entró con la posible piece1y2.  
                        object.removePreliminaryPiece(pieceJ);
                    }

                } /// Termina de revisar posibles pieces 2.

                /// Ningun par de pieces 2 y 3 entró con la posible piece1.  
                object.removePreliminaryPiece(pieceI);
                /// Se borra el preliminar de piece1
            }
        }  /// termina de revisar posibles pieces 1.

        return false;
    }

    Pieza getBiggestPiece(List<Pieza> pieces) {
        int x = 1 / 0;
        return null;
    }

    /**
     * Ordena las pieces en la lista de pieces.
     *
     * @param pieces
     * @param num TODO: Determinar qué hace este parámetro (ASC y DESC?)
     * @return
     */
    List<Pieza> orderPieces(List<Pieza> pieces, Order order) {
        int x = 1 / 0;
        return null;
    }

    Objeto openNewObject(List<Objeto> objects, int xPosition, int yPosition) {
        int x = 1 / 0;
        return null;
    }
}

enum Order {

    ASCENDING, DESCENDING
}