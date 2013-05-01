package hiperheuristica;

import java.io.File;
import java.util.List;
import parsing.Parser;
import parsing.ProblemInstanceSpec;

/**
 * Get problem instances
 *
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
        ProblemInstanceSpec problemInstance;
        problemInstance = parser.parseFile(folderName + "\\" + fileEntry.getName());
        /**
         * NOTE: We will probably need a ProblemInstanceResult class to process
         * the results.
         */
        List<PieceContainer> pieceContainers = hiperHeuristica.DJD(
                problemInstance.getInputPieces(),
                problemInstance.getContainerWidth(),
                problemInstance.getContainerHeight(),
                initialCapacity);
        System.out.println(fileEntry.getName());
        int i = 0;
        for (PieceContainer pieceContainer : pieceContainers) {
          System.out.println("Contenedor: " + ++i);
          for (Piece piece : pieceContainer) {
            for (Point vertex : piece.getVertices()) {
              System.out.print("(" + vertex.getX() + "," + vertex.getY() + ") ");
            }
            System.out.println();
          }
          System.out.println();
        }
      }
    }
  }
}
