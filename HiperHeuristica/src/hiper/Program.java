package hiper;

import java.io.File;
import java.util.List;
import parsing.Parser;
import hiper.ProblemInstanceSpec;

/**
 * Get problem instances
 *
 * @author Marcel, Priscila
 */
public class Program {

  public static void main(String[] args) throws Exception {
    Heuristica heuristica = new Heuristica();
    Parser parser = new Parser();

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
                problemInstance.getRecommendedInitialCapacity());        
        System.out.println("\n*********************************************\n");
        System.out.println(fileEntry.getName());
        System.out.println("Number of pieces: "
                + problemInstance.getInputPieces().size());
        System.out.println("Initial capacity used: "
                + problemInstance.getRecommendedInitialCapacity());
        System.out.println("Piece average of container area: "
                + problemInstance.getAvgPercentOfContainerArea());
        System.out.println("Theoretic number of containers: "
                + problemInstance.getPerfectNumberOfContainers());
        System.out.println("Number of wide pieces bigger than initial capacity: "
                + problemInstance.countBaseWidePiecesBiggerThan(
                (int) (problemInstance.getContainerHeight()
                * problemInstance.getContainerWidth()
                * problemInstance.getRecommendedInitialCapacity())));
        int i = 0;
        System.out.println("Number of containers: " + pieceContainers.size());
        /*for (PieceContainer container : pieceContainers) {
         System.out.println("\nContenedor #" + ++i + ": " + container);
         for (Piece piece : container) {
         System.out.println("  " + piece);
         }
         }*/
      }
    }
  }
}
