/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package parsing;

import hiperheuristica.PieceList;
import hiperheuristica.Container;

/**
 *
 * @author Priscila Angulo
 */
public class ProblemInstance {
  public Container container = null;
  public PieceList pieceList = null;
  
  public ProblemInstance(Container container, PieceList pieceList){
    this.container = container;
    this.pieceList = pieceList;
  }
  
  
}
