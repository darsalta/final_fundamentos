package hiper;

import java.io.File;
import java.util.List;
import parsing.Parser;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Get problem instances
 *
 * @author Marcel, Priscila
 */
public class Program {

  public static void main(String[] args) throws Exception {
    Heuristica heuristica = new Heuristica();
    Parser parser = new Parser();
    
    //folder names for results
    String folderResults_HH = "results_HH";
    String folderResults_H33 = "results_H33";
    String folderResults_H25 = "results_H25";
    
    //delete existing results or create results folder if doesn't exists
    cleanCreateFolderResults(folderResults_HH);
    cleanCreateFolderResults(folderResults_H33);
    cleanCreateFolderResults(folderResults_H25);
   
    //folder input data
    String folderName = "./input_data";
    final File folder = new File(folderName);
    for (final File fileEntry : folder.listFiles()) {
      if (fileEntry.isFile()) {

        /**
         * NOTE: We will probably need a ProblemInstanceResult or
         * HeuristicResultsEvaluator class to process the results.
         */
        executeHeuristic(folderName, fileEntry.getName(), folderResults_HH, parser, heuristica, 0);
        executeHeuristic(folderName, fileEntry.getName(), folderResults_H33, parser, heuristica, (float) 0.33);
        executeHeuristic(folderName, fileEntry.getName(), folderResults_H25, parser, heuristica, (float) 0.25);
      }
    }
  }
  
  /**
   * If the results folder exists clean it, otherwise creates it
   *
   * @param folder_name folder to clean or create
   */
  private static void cleanCreateFolderResults(String folder_name){
    //delete current results if exists
    final File folderResults = new File("./"+folder_name);
    if (folderResults.exists()){
        File[] existingResults = folderResults.listFiles();
        for (File existingResult : existingResults){
          existingResult.delete();
        }
    }
    //or create results files
    else
      folderResults.mkdir();
  }
  
  /**
   * Executes the heuristic and print the results in the Console
   *
   * @param file_folder folder with input data
   * @param file_name file with input data
   * @param folder_results folder for storing results
   * @param parser instance of the Parser class
   * @param heuristica instance of the Heuristica class
   * @param initial_capacity initial_capacity to use, in case it be 0, will get the recommended one
   */
  private static void executeHeuristic(
          String file_folder,
          String file_name, 
          String folder_results,
          Parser parser, 
          Heuristica heuristica,
          float initial_capacity
      ) throws IOException, Exception{
    ProblemInstanceSpec problemInstance = parser.parseFile(file_folder + "/" + file_name);
    List<PieceContainer> pieceContainers = heuristica.DJD(
                problemInstance.getInputPieces(),
                problemInstance.getContainerWidth(),
                problemInstance.getContainerHeight(),
                (initial_capacity == 0) ? problemInstance.getRecommendedInitialCapacity() : initial_capacity);
    System.out.println("\n*********************************************\n");
    System.out.println(file_name);
    System.out.println("Number of pieces: "
            + problemInstance.getInputPieces().size());
    if (initial_capacity == 0)
      System.out.println("Initial capacity used: "
            + problemInstance.getRecommendedInitialCapacity());
    else
       System.out.println("Initial capacity used: "
            + initial_capacity);
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
    for (PieceContainer container : pieceContainers) {
      System.out.println("\nContenedor #" + ++i + ": " + container);
      for (Piece piece : container) {
        System.out.println("  " + piece);
      }
    }
    
    saveResults(
            folder_results, 
            file_name, 
            pieceContainers, 
            initial_capacity, 
            (initial_capacity == 0) ? (float)problemInstance.getRecommendedInitialCapacity() : initial_capacity
       );
  }
  
  /**
   * Save each result per instance and per heuristic in a txt file
   *
   * @param folder_results folder for storing results
   * @param file_name file with input data
   * @param pieceContainers data of containers and the pieces in them
   * @param initial_capacity initial_capacity used, in case it be 0, will use the recommended one
   * @param recommended_capacity capacity recommended by the HH
   */
  private static void saveResults(
          String folder_results,
          String file_name, 
          List<PieceContainer> pieceContainers,
          float initial_capacity,
          float recommended_capacity
      ) throws IOException
  {
    //output results in results folder
    //create file
    FileWriter fileWriter = new FileWriter("./"+folder_results+"/results_" + file_name);
    try (BufferedWriter bufWriter = new BufferedWriter(fileWriter)) {
      //Header: file name and containers quantity
      bufWriter.write(file_name + ":" + pieceContainers.size());
      bufWriter.write("\r\n");
      
      //Header: heuristic used
      if (initial_capacity == 0){
        if (recommended_capacity == (float).33)
          bufWriter.write("Hiperheurística seleccionó DJD 1/3");
        else
          bufWriter.write("Hiperheurística seleccionó DJD 1/4");
      }
      else if (initial_capacity == (float).33)
        bufWriter.write("Heurística: DJD 1/3");
      else
        bufWriter.write("Heurística: DJD 1/4");
      bufWriter.write("\r\n");
      
      //Contents: pieces per containers
      for (PieceContainer container : pieceContainers) {
        for (Piece piece : container) {
          bufWriter.write(piece.getBottBound() + "," 
                  + piece.getLeftBound() + "," 
                  + piece.getWidth() + ","
                  + piece.getHeight());
          bufWriter.write("\r\n");
        }
        bufWriter.write("EOC");
        bufWriter.write("\r\n");
      }
    }
  }
}
