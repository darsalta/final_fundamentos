package hiperheuristica;

import java.io.File;
import parsing.Parser;
import parsing.ProblemInstance;

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
            ProblemInstance problemInstance = null;
            problemInstance = parser.processFile(folderName + "\\" + fileEntry.getName());
            hiperHeuristica.DJD(
                    problemInstance.pieceList, 
                    problemInstance.containers, 
                    (problemInstance.containers.get(0)).getWidth(), 
                    (problemInstance.containers.get(0)).getHeight(), 
                    initialCapacity);
            for (int i=0; i<(problemInstance.containers).size(); i++)
            {
              System.out.println("Contenedor: " + i);
              for (Piece piece : (problemInstance.containers).get(i)){
                for()
              } 
            }
        } 
    }
  }
}
