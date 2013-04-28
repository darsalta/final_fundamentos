/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hiperheuristica;

import javax.ejb.Local;

/**
 *
 * @author marcel
 */
@Local
public interface ProblemInstanceLocal {
    void addFigures(Point[] vertices);
    
    void setContainerDimensions(int width, int height);
    
    PieceList getBestFit();
}
