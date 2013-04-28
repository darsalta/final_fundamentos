/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hiperheuristica;

import hiperheuristica.Piece;
import hiperheuristica.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.ejb.Stateful;

/**
 *
 * @author marcel
 */
@Stateful
public class ProblemInstance implements ProblemInstanceLocal {

    private final PieceList pieces = new PieceList();
    private static final HiperHeuristica algorithm = new HiperHeuristica();
    private int containerWidth = 0;
    private int containerHeight = 0;

    @Override
    public void addPieces(Piece[] pieces) {
        assert (pieces != null);

        this.pieces.clear();
        for (int i = 0; i < pieces.length; i++) {
            this.pieces.add(pieces[i]);
        }
    }

    public void setContainerDimensions(int width, int height) {
        this.containerWidth = width;
        this.containerHeight = height;
    }

    public PieceList getBestFit() {
        List<Container> containers = algorithm.DJD(
                this.pieces,
                this.containerWidth,
                this.containerHeight,
                0.25d);
        PieceList pieceArrangement = new PieceList();
        for (Container container : containers) {
            for (Piece piece : container) {
                pieceArrangement.add(piece);
            }
        }

        return pieceArrangement;
    }
}
