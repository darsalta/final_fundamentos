/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package web;

import java.util.List;
import javax.ejb.Stateful;
import hiperheuristica.PieceList;
import hiperheuristica.HiperHeuristica;
import hiperheuristica.Piece;
import hiperheuristica.Container;

/**
 *
 * @author marcel
 */
@Stateful
public class ProblemInstance implements ProblemInstanceLocal {

    private PieceList pieces;
    private static HiperHeuristica algorithm = new HiperHeuristica();
    private int containerWidth = 0;
    private int containerHeight = 0;

  public ProblemInstance() {
    this.pieces = new PieceList();
  }

    @Override
    public void addPieces(Piece[] pieces) {
        assert (pieces != null);

        this.pieces.clear();
        for (int i = 0; i < pieces.length; i++) {
            this.pieces.add(pieces[i]);
        }
    }

    @Override
    public void setContainerDimensions(int width, int height) {
        this.containerWidth = width;
        this.containerHeight = height;
    }

    @Override
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
