/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package parsing;

import hiperheuristica.PieceList;
import hiperheuristica.Container;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Priscila Angulo
 */
public class ProblemInstance {
  public List<Container> containers = null;
  //public Container container = null;
  public PieceList pieceList = null;
  
  public ProblemInstance(Container container, PieceList pieceList){
    containers = new ArrayList<Container>();
    containers.add(container);
    //this.container = container;
    this.pieceList = pieceList;
  }
  
  
}
