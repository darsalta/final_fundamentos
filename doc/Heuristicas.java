
import java.util.List;

public class Heuristicas {

    int vertices = 0;
    int[] coordY = new int[100];
    int[] coordX = new int[100];

    /**
     * HeurísticaBL, trata de colocar pieza en objeto
     * Regresa:  true: si logra colocar pieza;  false: si no lo logra  
     */
    private boolean BLHeuristic(Objeto objeto, Pieza pieza) {
        /** 
         * Coloca la pieza en la parte superior derecha del objeto,
         * justo afuera del objeto.
         **/
        
        int despX = objeto.getXmax() - pieza.getXmax();
        moveDistance(despX, Direction.RIGHT);
        int despY = objeto.getYmax() - pieza.getYmin();
        moveDistance(despY, Direction.UP);

        runIntoBottomLeftPlacement(objeto, pieza);

        return posicionValida(objeto, pieza);
    }

    /**
     * TODO: Determinar si este método es correcto aquí. Creo que sí, porque 
     *       aplica a la 'heurística', no al objeto.
     * @param dist
     * @param dir 
     */
    public void moveDistance(int dist, Direction dir) {
        switch (dir) {
            case UP:
                for (int i = 0; i < vertices; i++) {
                    coordY[i] += dist;
                }
                break;

            case DOWN:
                for (int i = 0; i < vertices; i++) {
                    coordY[i] -= dist;
                }
                break;

            case LEFT:
                for (int i = 0; i < vertices; i++) {
                    coordX[i] -= dist;
                }
                break;

            case RIGHT:
                for (int i = 0; i < vertices; i++) {
                    coordX[i] += dist;
                }
                break;
        }
    }

    /**  
     * Mueve la pieza hasta una posicion estable lo más abajo y 
     * a la izquierda posible.
     * Devuelve TRUE si hubo movimiento y FALSE si no hubo.
     * REFACTOR: Mover este método a Objeto o Pieza.
     **/
    private boolean runIntoBottomLeftPlacement(Objeto objeto, Pieza pieza) {
        int distVertical;
        int distHorizontal;
        int xpos = pieza.getXmin();
        int ypos = pieza.getYmin();
        /** 
         * Es el valor que devuelven los métodos de cercanía 
         * cuando una pieza no alcanza a la otra.
         **/
        int sizeLimit = 100000;

        do {
            /// Distancia hacia abajo q puede moverse la pieza hasta topar.
            distVertical = cercaniaVerOP(objeto, pieza);
            if (distVertical > 0 && distVertical < sizeLimit) {
                pieza.moveDistance(distVertical, Direction.DOWN);
            }
            /**
             * Distancia hacia la izquierda que puede moverse la pieza 
             * hasta topar.
             **/
            distHorizontal = cercaniaHorOP(objeto, pieza);
            if (distHorizontal > 0 && distHorizontal < sizeLimit) {
                pieza.moveDistance(distHorizontal, Direction.LEFT);
            }

        } while ((distHorizontal > 0 && distHorizontal < sizeLimit)
                || (distVertical > 0 && distVertical < sizeLimit));

        if (xpos == pieza.getXmin() && ypos == pieza.getYmin()) {    /// Si no reacomoda la pieza
            return false;
        }

        return true;
    }

    /**
     * Dado un objeto, indica si las coordenadas de 
     *	la pieza son válidas para colocarse dentro de él.
     **/
    private boolean posicionValida(Objeto objeto, Pieza pieza) {
        // Si la pieza no se sale de los límites del objeto.
        // REFACTOR: Mover este método a Objeto
        // REFACTOR: Crear una interfaz común entre Objeto y Pieza
        if (pieza.getYmax() <= objeto.getYmax()
         && pieza.getXmax() <= objeto.getXmax()
         && pieza.getXmin() >= 0 
         && pieza.getYmin() >= 0) {
            // Si la pieza no tiene intersección con ninguna otra del objeto.
            return !interseccionOP(objeto, pieza);
        }

        return false;
    }

    /**
     * Implementa DJD. 
     **/
    public void DJD(
            List<Pieza> listapiezas,
            List<Objeto> listaObjetos,
            int xObjeto,
            int yObjeto,
            double CapInicial) {
        boolean acomodopieza = false;
        /// El desperdicio se incrementa en 1/20 del objeto.
        int incremento = ((Objeto) listaObjetos.get(0)).getTotalSize() / 20;
        /// Desperdicio
        int waste = 0;
        /// De mayor a menor
        listapiezas = OrdenaPiezas(listapiezas, 1);
        /// Decide cuándo pasar a otro objeto.
        boolean terminar = false;

        /**
         * Revisa objetos con menos de CapInicial para meter una sola pieza.
         * En alguna HH podría ser necesario revisar varios objetos.
         **/
        for (int j = listaObjetos.size() - 1; j < listaObjetos.size(); j++) {
            Objeto nextObjeto = (Objeto) listaObjetos.get(j);
            if (nextObjeto.getUsedArea() < nextObjeto.getTotalSize() * CapInicial) // CapInicial = 1/4 o 1/3
            {
                /// De mayor a menor
                for (int i = 0; i < listapiezas.size(); i++) {
                    Pieza pieza = (Pieza) listapiezas.get(i);
                    if (pieza.getTotalSize() <= nextObjeto.getFreeArea()) {
                        /// true o false, dependiendo se se puede acomodar la pieza.
                        acomodopieza = BLHeuristic(nextObjeto, pieza);
                        if (acomodopieza) {
                            nextObjeto.addPieza(pieza);
                            listapiezas.remove(pieza);
                            return;
                        }
                    }
                }
            }
        }

        /** 
         * No hubo objetos con menos de 1/3 de capacidad, o bien, 
         * ninguna pieza cupo en un objeto con menos de 1/3 de capacidad,
         * lo que podría ocurrir.
         **/
        /// En alguna HH podría ser necesario revisar varios objetos
        for (int j = listaObjetos.size() - 1; j < listaObjetos.size(); j++) {

            Objeto nextObjeto = (Objeto) listaObjetos.get(j);
            waste = 0;
            terminar = false;

            if (verificador(listapiezas, nextObjeto.getFreeArea())) {
                /** 
                 * Si por area libre, ya no cabe ninguna pieza, se pasa 
                 * al otro objeto, en caso de haber.
                 **/
                continue;
            }

            do {
                if (canFitOnePiece(listapiezas, nextObjeto, waste)) {
                    return;
                }

                if (listapiezas.size() > 1 && canFitTwoPieces(listapiezas, nextObjeto, waste)) {
                    return;
                }

                if (listapiezas.size() > 2 && canFitThreePieces(listapiezas, nextObjeto, waste)) {
                    return;
                }

                if (waste > nextObjeto.getFreeArea()) {
                    terminar = true;
                }

                waste += incremento;

            } while (!terminar);
        }


        Objeto nextObjeto = abreNuevoObjeto(listaObjetos, xObjeto, yObjeto);
        Pieza pieza = SearchGreatest(listapiezas);
        acomodopieza = BLHeuristic(nextObjeto, pieza);
        /// Si el objeto es nuevo, siempre debería poder acomodar la pieza.
        if (acomodopieza) {
            nextObjeto.addPieza(pieza);
            listapiezas.remove(pieza);
        }
    }

    private static boolean verificador(List<Pieza> listaPiezas, int freeArea) {
        Pieza pieza;
        /**
         * Como se entrega la lista ordenada de mayor a menor,
         * si se empieza a buscar desde el último (pieza + chica).
         **/
        for (int i = listaPiezas.size() - 1; i >= 0; i--) {
            // Devuelve un 'false' con menos comparaciones.
            pieza = (Pieza) listaPiezas.get(i);
            if (pieza.getTotalSize() <= freeArea) {
                return false;
            }
        }
        
        return true;
    }

    /** 
     * Indica si puede o no poner una pieza en el objeto, dejando 
     * un máximo de desperdicio w.
     * SE ASUME QUE LA LISTA ESTÁ DADA ORDENADA DE MAYOR A MENOR 
     **/
    private boolean canFitOnePiece(List<Pieza> listaPiezas, Objeto nextObjeto, int w) {
        Pieza pieza1;
        boolean acomodo = false;
        int areaLibre = nextObjeto.getFreeArea();

        for (int i = 0; i < listaPiezas.size(); i++) {
            pieza1 = (Pieza) listaPiezas.get(i);
            if ((areaLibre - pieza1.getTotalSize()) > w) {
                /**
                 * Si con una pieza deja más desperdicio que w, con las demás
                 * también lo hará (dado q están ordenadas).
                 **/
                break;
            }

            acomodo = BLHeuristic(nextObjeto, pieza1);
            if (acomodo) {
                nextObjeto.addPieza(pieza1);
                listaPiezas.remove(pieza1);
                // Indica que ya acomodó pieza.
                return true;
            }
        }

        return false;
    }

    /** 
     * Indica si puede o no poner dos piezas en el objeto, dejando un 
     * máximo de desperdicio w.
     * SE ASUME QUE LA LISTA ESTÁ DADA ORDENADA DE MAYOR A MENOR 
     **/
    private boolean canFitTwoPieces(List<Pieza> listapiezas1, Objeto nextObjeto1, int w1) {
        Pieza pieza1, pieza2;
        boolean acomodo1 = false, acomodo2 = false;
        /// Guardará el área de las 2 piezas más grandes.
        int area0, area1;
        /// Guardará el área de la pieza más pequeña.
        int areaU;	  
        int arealibre;
        pieza1 = (Pieza) listapiezas1.get(listapiezas1.size() - 1);
        areaU = pieza1.getTotalSize();
        arealibre = nextObjeto1.getFreeArea();

        /**
         * Verificando si cabrían 2 piezas con ese desperdicio máximo 
         * permitido.
         * SE SUPONE QUE ESTÁN ORDENADAS DE MAYOR A MENOR  (se revisan las 2 piezas + grandes).
         **/
        pieza1 = (Pieza) listapiezas1.get(0);
        pieza2 = (Pieza) listapiezas1.get(1);
        area0 = pieza1.getTotalSize();
        area1 = pieza2.getTotalSize();
        if ((arealibre - area0 - area1) > w1) {
            return false;
        }

        for (int i = 0; i < listapiezas1.size(); i++) {
            acomodo1 = false;
            acomodo2 = false;
            pieza1 = (Pieza) listapiezas1.get(i);

            if (arealibre - pieza1.getTotalSize() - area0 > w1) {
                /** 
                 * Con pieza1 y la más grande dejan más w, ya no tiene caso
                 * probar + piezas1.
                 **/
                break;
            }

            if (pieza1.getTotalSize() + areaU > arealibre) {
                /** 
                 * A la siguiente pieza 1. pieza1 + la mas chica se pasarían del
                 * área disponible.
                 **/
                continue;
            }

            acomodo1 = BLHeuristic(nextObjeto1, pieza1);
            if (acomodo1) {
                /// Se añade pieza como 'borrador'
                nextObjeto1.addPreliminarPieza(pieza1);
                /// No altera el FreeArea de objeto.

                /**
                 * Si puede acomodar pieza1, prueba con cuál pieza2 entra
                 * simultáneamente.
                 **/
                for (int j = 0; j < listapiezas1.size(); j++) {
                    pieza2 = (Pieza) listapiezas1.get(j);

                    if ((arealibre - pieza1.getTotalSize() - pieza2.getTotalSize()) > w1) {
                        /**
                         * Si con pieza2 elegida se deja + w, con las siguiente piezas2 
                         * también lo haría.
                         **/
                        break;
                    }

                    if ((pieza1.getTotalSize() + pieza2.getTotalSize()) > arealibre || i == j) {
                        /// A la siguiente pieza 2.
                        continue;
                    }

                    acomodo2 = BLHeuristic(nextObjeto1, pieza2);
                    if (acomodo2) {
                        /// Se borra el pegado preliminar.
                        nextObjeto1.removePreliminarPieza(pieza1);
                        /// Se añade definitivamente.
                        nextObjeto1.addPieza(pieza1);
                        nextObjeto1.addPieza(pieza2);
                        listapiezas1.remove(pieza1);
                        listapiezas1.remove(pieza2);
                        // Indica que ya acomodó 2 piezas.
                        return true;
                    }
                } /// Termina de revisar posibles piezas 2.

                /// Ninguna pieza2 entró con la posible pieza1.  
                nextObjeto1.removePreliminarPieza(pieza1);
                /// Se borra el preliminar de pieza1.
            }
        }  /// Termina de revisar posibles piezas 1.

        return false;
    }

    /** 
     * Indica si puede o no poner tres piezas en el objeto, dejando un 
     * máximo de desperdicio w.
     * SE ASUME QUE LA LISTA ESTÁ DADA ORDENADA DE MAYOR A MENOR 
     **/
    private boolean canFitThreePieces(
            List<Pieza> listaPiezas,
            Objeto nextObjeto,
            int maxWaste) {
        Pieza pieza1, pieza2, pieza3;
        boolean acomodo1 = false, acomodo2 = false, acomodo3 = false;
        /// Guardará el área de las 3 piezas más grandes.
        int area0, area1, area2;
        /// Guardará el área de las 2 piezas más pequeñas.
        int areaU1, areaU2;
        int arealibre;
        
        pieza1 = (Pieza) listaPiezas.get(listaPiezas.size() - 1);
        pieza2 = (Pieza) listaPiezas.get(listaPiezas.size() - 2);
        areaU1 = pieza1.getTotalSize();
        areaU2 = pieza2.getTotalSize();
        arealibre = nextObjeto.getFreeArea();

        /**
         * Verificando si cabrían 3 piezas con ese desperdicio máximo 
         * permitido.
         *
         * SE SUPONE QUE ESTÁN ORDENADAS DE MAYOR A MENOR (se revisan 
         * las piezas más grandes).
         **/
        pieza1 = (Pieza) listaPiezas.get(0);
        pieza2 = (Pieza) listaPiezas.get(1);
        pieza3 = (Pieza) listaPiezas.get(2);
        area0 = pieza1.getTotalSize();
        area1 = pieza2.getTotalSize();
        area2 = pieza3.getTotalSize();
        if ((arealibre - area0 - area1 - area2) > maxWaste) {
            return false;
        }

        for (int i = 0; i < listaPiezas.size(); i++) {
            acomodo1 = false;
            acomodo2 = false;
            acomodo3 = false;

            pieza1 = (Pieza) listaPiezas.get(i);
            if (arealibre - pieza1.getTotalSize() - area0 - area1 > maxWaste) {
                /**
                 * Esa pieza 1 no es 'compatible' con ningun otro par de piezas
                 * sin pasarse del desperdicio máximo permitido.
                 **/
                break;
            }
            
            if (pieza1.getTotalSize() + areaU1 + areaU2 > arealibre) {
                /**
                 * A la siguiente pieza 1.  pieza1 + las2+chicas se pasarían del
                 * área libre.
                 **/
                continue;
            }

            /**
             * Anteriormente decía:
             * acomodo1 = (nextObjeto1, pieza1, H_acomodo1);
             **/
            acomodo1 = MetodoMisterio(nextObjeto, pieza1, H_acomodo1);

            if (acomodo1) {
                /// Se añade pieza1 como 'borrador'
                /// No altera el FreeArea de objeto.
                nextObjeto.addPreliminarPieza(pieza1);

                /** 
                 * Si puede acomodar pieza1, prueba con cuál pieza2 
                 * entra simultáneamente.
                 **/
                for (int j = 0; j < listaPiezas.size(); j++) {
                    pieza2 = (Pieza) listaPiezas.get(j);

                    if (arealibre - pieza1.getTotalSize() - pieza2.getTotalSize() - area0 > maxWaste) {
                        /**
                         * Las piezas 1-2 no son 'compatibles' con ninguna otra pieza
                         * sin pasarse del desperdicio máximo permitido.  
                         **/
                        break;
                    }

                    if ((pieza1.getTotalSize() + pieza2.getTotalSize() + areaU1) > arealibre || i == j) {
                        /**
                         *  A la siguiente pieza2 
                         *  pieza1 + pieza2 + MásChica se pasarían el área libre.
                         **/
                        continue;
                    }

                    acomodo2 = BLHeuristic(nextObjeto, pieza2); // decía pieza21 :s
                    if (acomodo2) {
                        nextObjeto.addPreliminarPieza(pieza2);

                        for (int k = 0; k < listaPiezas.size(); k++) {
                            pieza3 = (Pieza) listaPiezas.get(k);

                            if ((arealibre - pieza1.getTotalSize() - pieza2.getTotalSize() - pieza3.getTotalSize()) > maxWaste) {
                                /** 
                                 * Si con pieza3 elegida se deja + w, con las siguiente piezas3 
                                 * (más chicas) también lo haría.
                                 * Deja de revisar piezas3 y se pasa a la siguiente pieza2.
                                 **/
                                break;
                            }

                            /// Misma advertencia que en función 'dospiezas'
                            if ((pieza1.getTotalSize() + pieza2.getTotalSize() + pieza3.getTotalSize()) > arealibre
                                    || i == k || j == k) {
                                /// A la siguiente pieza 3.					
                                continue;
                            }

                            acomodo3 = BLHeuristic(nextObjeto, pieza3);
                            if (acomodo3) {
                                /// Se borra el pegado preliminar.
                                nextObjeto.removePreliminarPieza(pieza1);
                                nextObjeto.removePreliminarPieza(pieza2);
                                /// Se añaden definitivamente.
                                nextObjeto.addPieza(pieza1);
                                nextObjeto.addPieza(pieza2);
                                nextObjeto.addPieza(pieza3);
                                listaPiezas.remove(pieza1);
                                listaPiezas.remove(pieza2);
                                listaPiezas.remove(pieza3);
                                /// Indica que ya acomodó 3 piezas.
                                return true;
                            }

                        } /// Termina de revisar posibles piezas 3.

                        /// Ninguna pieza3 entró con la posible pieza1y2.  
                        nextObjeto.removePreliminarPieza(pieza2);
                    }

                } /// Termina de revisar posibles piezas 2.

                /// Ningun par de piezas 2y3 entró con la posible pieza1.  
                nextObjeto.removePreliminarPieza(pieza1);
                /// Se borra el preliminar de pieza1
            }
        }  /// termina de revisar posibles piezas 1.

        return false;
    }

    /**
     * Métodos sin implementación
     **/
    /**
     * TODO: Pendiente determinar e implementar qué hace este método.
     * @param objeto
     * @param pieza
     * @return 
     */
    int cercaniaVerOP(Objeto objeto, Pieza pieza) {
        return 1 / 0;
    }

    int cercaniaHorOP(Objeto objeto, Pieza pieza) {
        return 1 / 0;
    }

    boolean interseccionOP(Objeto objeto, Pieza pieza) {
        int x = 1 / 0;
        return false;
    }

    Pieza SearchGreatest(List<Pieza> listapiezas) {
        int x = 1 / 0;
        return null;
    }

    /**
     * Ordena las piezas en la lista de piezas.
     * @param listapiezas
     * @param num TODO: Determinar qué hace este parámetro (ASC y DESC?)
     * @return 
     */
    List<Pieza> OrdenaPiezas(List<Pieza> listapiezas, int num) {
        int x = 1 / 0;
        return null;
    }
    
    Objeto abreNuevoObjeto(List<Objeto> listaObjetos, int xObjeto, int yObjeto) {
        int x = 1 / 0;
        return null;
    }   
}

enum Direction {

    UP, DOWN, LEFT, RIGHT
}

class Objeto {

    public int getXmax() {
        return 1 / 0;
    }

    public int getXmin() {
        return 1 / 0;
    }

    public int getYmax() {
        return 1 / 0;
    }

    public int getYmin() {
        return 1 / 0;
    }

    int getTotalSize() {
        return 1 / 0;
    }

    void removePreliminarPieza(Pieza pieza) {
        int x = 1 / 0;
    }

    void addPieza(Pieza pieza) {
        int x = 1 / 0;
    }

    void addPreliminarPieza(Pieza pieza) {
        int x = 1 / 0;
    }

    int getFreeArea() {
        return 1 / 0;
    }

    int getUsedArea() {
        return 1 / 0;
    }
}

class Pieza {

    public int getXmax() {
        return 1 / 0;
    }

    public int getXmin() {
        return 1 / 0;
    }

    public int getYmax() {
        return 1 / 0;
    }

    public int getYmin() {
        return 1 / 0;
    }

    void moveDistance(int distVertical, Direction dir) {
        int x = 1 / 0;
    }

    public int getTotalSize() {
        return 1 / 0;
    }
}