package hiper;

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
    Heuristica heuristica = new Heuristica();
    Parser parser = new Parser();

    double initialCapacity = 0.25;

    String folderName = ".\\input_data";
    final File folder = new File(folderName);

    for (final File fileEntry : folder.listFiles()) {
      if (fileEntry.isFile()) {
        ProblemInstanceSpec problemInstance;
        problemInstance = parser.parseFile(folderName + "\\" + fileEntry.getName());
        /**
         * NOTE: We will probably need a ProblemInstanceResult or
         * HeuristicResultsEvaluator class to process the results.
         */
        List<PieceContainer> pieceContainers = heuristica.DJD(
                problemInstance.getInputPieces(),
                problemInstance.getContainerWidth(),
                problemInstance.getContainerHeight(),
                initialCapacity);
        System.out.println("\n*********************************************\n");
        System.out.println(fileEntry.getName());
        int i = 0;
        for (PieceContainer container : pieceContainers) {
          System.out.println("\nContenedor #" + ++i + ": " + container);
          for (Piece piece : container) {
            System.out.println("  " + piece);
          }
        }
      }
    }
  }
}
