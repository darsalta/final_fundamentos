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

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    public void addFigures(Point[] vertices) {
        this.pieces.clear();
        assert (vertices.length > 0);
        assert (vertices.length % 4 == 0);
        for (int i = 0; i < vertices.length; i += 4) {
            this.pieces.add(
                    new Piece(
                    vertices[i],
                    vertices[i + 1],
                    vertices[i + 2],
                    vertices[i + 3]));
        }
    }

    public void setContainerDimensions(int width, int height) {
        this.containerHeight = height;
        this.containerWidth = width;
    }

    public PieceList getBestFit() {
        List<Container> containers = algorithm.DJD(
                this.pieces, 
                this.containerWidth, 
                this.containerHeight, 
                0.25d);
        PieceList pieceArrangement = new PieceList();
        for(Container container : containers) {
            for(Piece piece : container) {
                pieceArrangement.add(piece);
            }
        }
                
        return pieceArrangement;
    }
}
