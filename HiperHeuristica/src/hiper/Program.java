package hiper;

import java.io.File;
import java.util.List;
import parsing.Parser;
import java.io.BufferedWriter;
import java.io.FileWriter;

/**
 * Get problem instances
 *
 * @author Marcel, Priscila
 */
public class Program {

  public static void main(String[] args) throws Exception {
    Heuristica heuristica = new Heuristica();
    Parser parser = new Parser();
    String nameFolder_results_HH = "results_HH";
    String nameFolder_results_H33 = "results_H33";
    String nameFolder_results_H25 = "results_H25";
    
    /*
     *FOLDER RESULTS H .33
     */
    //delete current results if exists
    final File folderResults_H33 = new File("./"+nameFolder_results_H33);
    if (folderResults_H33.exists()){
        File[] existingResults = folderResults_H33.listFiles();
        for (File existingResult : existingResults){
          existingResult.delete();
        }
    }
    //or create results files
    else
      folderResults_H33.mkdir();
    
    /*
     *FOLDER RESULTS H .25
     */
    final File folderResults_H25 = new File("./"+nameFolder_results_H25);
    if (folderResults_H25.exists()){
        File[] existingResults = folderResults_H25.listFiles();
        for (File existingResult : existingResults){
          existingResult.delete();
        }
    }
    //or create results files
    else
      folderResults_H25.mkdir();
    
    /*
     *FOLDER RESULTS HH
     */
    final File folderResults_HH = new File("./"+nameFolder_results_HH);
    if (folderResults_HH.exists()){
        File[] existingResults = folderResults_HH.listFiles();
        for (File existingResult : existingResults){
          existingResult.delete();
        }
    }
    //or create results files
    else
      folderResults_HH.mkdir();

    /*
     *FOLDER INPUT DATA
     */
    String folderName = "./input_data";
    final File folder = new File(folderName);
    for (final File fileEntry : folder.listFiles()) {
      if (fileEntry.isFile()) {
        ProblemInstanceSpec problemInstanceHH, problemInstanceH33, problemInstanceH25;
        problemInstanceHH = parser.parseFile(folderName + "/" + fileEntry.getName());
        problemInstanceH33 = parser.parseFile(folderName + "/" + fileEntry.getName());
        problemInstanceH25 = parser.parseFile(folderName + "/" + fileEntry.getName());
        /**
         * NOTE: We will probably need a ProblemInstanceResult or
         * HeuristicResultsEvaluator class to process the results.
         */
        
////////SOLVE WITH HH
        List<PieceContainer> pieceContainersHH = heuristica.DJD(
                problemInstanceHH.getInputPieces(),
                problemInstanceHH.getContainerWidth(),
                problemInstanceHH.getContainerHeight(),
                problemInstanceHH.getRecommendedInitialCapacity());
        System.out.println("\n*********************************************\n");
        System.out.println(fileEntry.getName());
        System.out.println("Number of pieces: "
                + problemInstanceHH.getInputPieces().size());
        System.out.println("Initial capacity used: "
                + problemInstanceHH.getRecommendedInitialCapacity());
        System.out.println("Piece average of container area: "
                + problemInstanceHH.getAvgPercentOfContainerArea());
        System.out.println("Theoretic number of containers: "
                + problemInstanceHH.getPerfectNumberOfContainers());
        System.out.println("Number of wide pieces bigger than initial capacity: "
                + problemInstanceHH.countBaseWidePiecesBiggerThan(
                (int) (problemInstanceHH.getContainerHeight()
                * problemInstanceHH.getContainerWidth()
                * problemInstanceHH.getRecommendedInitialCapacity())));
        int i = 0;
        System.out.println("Number of containers: " + pieceContainersHH.size());
        for (PieceContainer container : pieceContainersHH) {
          System.out.println("\nContenedor #" + ++i + ": " + container);
          for (Piece piece : container) {
            System.out.println("  " + piece);
          }
        }
     
        //output results in results folder
        //create file
        FileWriter fileWriter = new FileWriter("./"+nameFolder_results_HH+"/results_" + fileEntry.getName());
        try (BufferedWriter bufWriter = new BufferedWriter(fileWriter)) {
          bufWriter.write(fileEntry.getName() + ":" + pieceContainersHH.size());
          bufWriter.write("\r\n");
          for (PieceContainer container : pieceContainersHH) {
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
////////SOLVE WITH HH
        
////////SOLVE WITH H33
        List<PieceContainer> pieceContainersH33 = heuristica.DJD(
                problemInstanceH33.getInputPieces(),
                problemInstanceH33.getContainerWidth(),
                problemInstanceH33.getContainerHeight(),
                0.33);
        System.out.println("\n*********************************************\n");
        System.out.println(fileEntry.getName());
        System.out.println("Number of pieces: "
                + problemInstanceH33.getInputPieces().size());
        System.out.println("Initial capacity used: "
                + .33);
        System.out.println("Piece average of container area: "
                + problemInstanceH33.getAvgPercentOfContainerArea());
        System.out.println("Theoretic number of containers: "
                + problemInstanceH33.getPerfectNumberOfContainers());
        System.out.println("Number of wide pieces bigger than initial capacity: "
                + problemInstanceH33.countBaseWidePiecesBiggerThan(
                (int) (problemInstanceH33.getContainerHeight()
                * problemInstanceH33.getContainerWidth()
                * problemInstanceH33.getRecommendedInitialCapacity())));
        i = 0;
        System.out.println("Number of containers: " + pieceContainersH33.size());
        for (PieceContainer container : pieceContainersH33) {
          System.out.println("\nContenedor #" + ++i + ": " + container);
          for (Piece piece : container) {
            System.out.println("  " + piece);
          }
        }
     
        //output results in results folder
        //create file
        fileWriter = new FileWriter("./"+nameFolder_results_H33+"/results_" + fileEntry.getName());
        try (BufferedWriter bufWriter = new BufferedWriter(fileWriter)) {
          bufWriter.write(fileEntry.getName() + ":" + pieceContainersH33.size());
          bufWriter.write("\r\n");
          for (PieceContainer container : pieceContainersH33) {
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
////////SOLVE WITH H33
        
////////SOLVE WITH H25
        List<PieceContainer> pieceContainersH25 = heuristica.DJD(
                problemInstanceH25.getInputPieces(),
                problemInstanceH25.getContainerWidth(),
                problemInstanceH25.getContainerHeight(),
                .25);
        System.out.println("\n*********************************************\n");
        System.out.println(fileEntry.getName());
        System.out.println("Number of pieces: "
                + problemInstanceH25.getInputPieces().size());
        System.out.println("Initial capacity used: "
                + .25);
        System.out.println("Piece average of container area: "
                + problemInstanceH25.getAvgPercentOfContainerArea());
        System.out.println("Theoretic number of containers: "
                + problemInstanceH25.getPerfectNumberOfContainers());
        System.out.println("Number of wide pieces bigger than initial capacity: "
                + problemInstanceH25.countBaseWidePiecesBiggerThan(
                (int) (problemInstanceH25.getContainerHeight()
                * problemInstanceH25.getContainerWidth()
                * problemInstanceH25.getRecommendedInitialCapacity())));
        i = 0;
        System.out.println("Number of containers: " + pieceContainersH25.size());
        for (PieceContainer container : pieceContainersH25) {
          System.out.println("\nContenedor #" + ++i + ": " + container);
          for (Piece piece : container) {
            System.out.println("  " + piece);
          }
        }
     
        //output results in results folder
        //create file
        fileWriter = new FileWriter("./"+nameFolder_results_H25+"/results_" + fileEntry.getName());
        try (BufferedWriter bufWriter = new BufferedWriter(fileWriter)) {
          bufWriter.write(fileEntry.getName() + ":" + pieceContainersH25.size());
          bufWriter.write("\r\n");
          for (PieceContainer container : pieceContainersH25) {
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
////////SOLVE WITH H25       
      }
    }
  }
}
