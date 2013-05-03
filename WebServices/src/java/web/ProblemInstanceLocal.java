/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package web;

import javax.ejb.Local;
import hiper.Piece;
import hiper.PieceList;

/**
 *
 * @author marcel
 */
@Local
public interface ProblemInstanceLocal {
    void addPieces(Piece[] pieces);
    
    void setContainerDimensions(int width, int height);
    
    PieceList getBestFit() throws Exception;
}
