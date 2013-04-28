package hiperheuristica;

import java.io.File;
import parsing.Parser;
import parsing.ProblemInstance;
import hiperheuristica.Point;

/**
 * Get problem instances
 * @author Marcel, Priscila
 */
public class Program {

  public static void main(String[] args) throws Exception {
    //get problem instances and solve them
    HiperHeuristica hiperHeuristica = new HiperHeuristica();
    Parser parser = new Parser();
    
    double initialCapacity = 0.25;
    
    String folderName = ".\\input_data";
    final File folder = new File(folderName);
    
    for (final File fileEntry : folder.listFiles()) {
        if (fileEntry.isFile()) {
            ProblemInstance problemInstance;
            problemInstance = parser.processFile(folderName + "\\" + fileEntry.getName());
            problemInstance.containers = hiperHeuristica.DJD(
                    problemInstance.pieceList,                     
                    (problemInstance.containers.get(0)).getWidth(), 
                    (problemInstance.containers.get(0)).getHeight(), 
                    initialCapacity);
            System.out.println(fileEntry.getName());
            for (int i=0; i<(problemInstance.containers).size(); i++)
            {
              System.out.println("Contenedor: " + i);
              for (Piece piece : (problemInstance.containers).get(i)){
                final Point[] pieceVertices = piece.getVertices();
                for(int j = 0; j < 4; j++){
                  System.out.print("(" + pieceVertices[j].getX() + "," + pieceVertices[j].getY() + ") ");
                }
                System.out.println();
              }
              System.out.println();
            }
        } 
    }
  }
}
