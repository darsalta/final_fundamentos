/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package parsing;

import hiperheuristica.Container;
import hiperheuristica.Piece;
import hiperheuristica.PieceList;
import hiperheuristica.Point;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import parsing.ProblemInstance;

/**
 *
 * @author Priscila Angulo
 */
public class Parser {
    
    public Parser(){
        
    }
    
    public ProblemInstance processFile(String file) throws FileNotFoundException, IOException{
        FileReader fileReader = new FileReader(file);
        BufferedReader bufReader = new BufferedReader(fileReader);
        try {
            ProblemInstance problemInstance = null;
            Container container = null;
            PieceList pieceList = new PieceList();
            //Skip the first line
            String line = bufReader.readLine();
            line = bufReader.readLine();
            
            //Get container dimmensions
            container = getContainerDim(line);
            
            //Get pieces
            line = bufReader.readLine();
            while (line != null) {
              Piece piece = getPiece(line);
              pieceList.add(piece);
              line = bufReader.readLine();
            }
          
            problemInstance = new ProblemInstance(container, pieceList);
            return problemInstance;
            
        } finally {
            bufReader.close();
        }
    }
    
    public Container getContainerDim(String line){
      int indexSpace = line.indexOf(" ", 1);
      int width = Integer.parseInt((line.substring(1, indexSpace)).trim());
      int height = Integer.parseInt((line.substring(indexSpace+1)).trim());
      Container container = new Container(width, height);
      return container;
    }
    
    public Piece getPiece(String line){
      int lineIndexSpace, readedLength, x, y;
      Point[] vertices = new Point[4];
      readedLength = 3;
    
      for (int i=0; i<4; i++)
      {
        lineIndexSpace = line.indexOf(" ", readedLength);
        x = Integer.parseInt((line.substring(readedLength, lineIndexSpace)).trim());
        readedLength += lineIndexSpace - readedLength + 1;
       
        if (i==3){
          y = Integer.parseInt((line.substring(readedLength)).trim());
        }
        else{
          lineIndexSpace = line.indexOf(" ", readedLength);
          y = Integer.parseInt((line.substring(readedLength, lineIndexSpace)).trim());
          readedLength += lineIndexSpace - readedLength + 1;
        }
        
        Point point = new Point(x,y);
        vertices[i] = point;
      }
      Piece piece = new Piece(vertices);
      return piece;
    }
   
    
}
