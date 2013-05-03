/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package parsing;

import hiper.Point;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import parsing.Parser;
import hiper.ProblemInstanceSpec;
import java.io.IOException;
import java.io.StringReader;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Priscila Angulo
 */
public class ParserTest {

  public ParserTest() {
  }

  @Test
  public void testGetFileReader() throws IOException {
    System.out.println("parser.getFileReader");

    // Arrange
    String content = "test";
    File tempFile = File.createTempFile("temp", "txt");
    String filePath = tempFile.getAbsolutePath();
    tempFile.setWritable(true);
    try (FileWriter writer = new FileWriter(tempFile)) {
      writer.write(content, 0, content.length());
    }
    Parser parser = new Parser();

    // Assume
    assertTrue(new File(filePath).exists());

    // Act
    try (BufferedReader actual = parser.getFileReader(filePath);) {
      // Assert
      assertEquals(content, actual.readLine());
    } catch (Exception e) {
      Logger.getLogger(ParserTest.class.getName()).log(Level.SEVERE, null, e);
      /// Fail fast.
      throw e;
    }
  }

  @Test
  public void testProcessFile() throws IOException {
    System.out.println("parser.processFile");
    // Arrange
    ProblemInstanceSpec problemInstance;
    ParserStub parser = new ParserStub();
    parser.setProblemSpecTxt(
            " 8\n"
            + " 100 100 \n"
            + " 4 0 0 54 0 54 100 0 100\n"
            + " 4 0 0 46 0 46 100 0 100\n"
            + " 4 0 0 56 0 56 100 0 100\n"
            + " 4 0 0 44 0 44 100 0 100\n"
            + " 4 0 0 100 0 100 66 0 66\n"
            + " 4 0 0 42 0 42 100 0 100\n"
            + " 4 0 0 100 0 100 34 0 34\n"
            + " 4 0 0 58 0 58 100 0 100\n"
            + "");

    // Act
    try {
      problemInstance = parser.parseFile("");
      // Assert        
      assertNotNull(problemInstance);
      assertEquals(100, problemInstance.getContainerHeight());
      assertEquals(100, problemInstance.getContainerWidth());
      assertEquals(8, problemInstance.getInputPieces().size());
    } catch (IOException ex) {
      Logger.getLogger(ParserTest.class.getName()).log(Level.SEVERE, null, ex);
      /// Fail fast.
      throw ex;
    }
  }

  @Test
  public void testGetContainerHeight() {
    System.out.println("parser.testGetContainerHeight");
    // Arrange
    String line = "10 100";
    Parser parser = new Parser();

    // Act
    int height = parser.parseContainerHeight(line);

    // Assert        
    assertEquals(100, height);
  }

  @Test
  public void testGetContainerWidth() {
    System.out.println("parser.testGetContainerWidth");
    // Arrange
    String line = "10 100";
    Parser parser = new Parser();

    // Act
    int width = parser.parseContainerWidth(line);

    // Assert        
    assertEquals(10, width);
  }

  @Test
  public void testGetPieceVertices() {
    System.out.println("parser.testGetPieceVertices");
    // Arrange
    String line = " 4 0 0 54 0 54 100 0 100";
    Point[] result;
    Parser parser = new Parser();

    // Act
    result = parser.parsePieceVertices(line);

    // Assert        
    assertNotNull(result);
    assertEquals(4, result.length);
    assertEquals(0, result[0].getX());
    assertEquals(0, result[0].getY());
    assertEquals(54, result[1].getX());
    assertEquals(0, result[1].getY());
    assertEquals(54, result[2].getX());
    assertEquals(100, result[2].getY());
    assertEquals(0, result[3].getX());
    assertEquals(100, result[3].getY());
  }

  class ParserStub extends Parser {

    private String problemSpecTxt = "";

    @Override
    protected BufferedReader getFileReader(String file) {
      return new BufferedReader(new StringReader(this.problemSpecTxt));
    }

    /**
     * @param problemSpecTxt the problemSpecTxt to set
     */
    public void setProblemSpecTxt(String problemSpecTxt) {
      this.problemSpecTxt = problemSpecTxt;
    }
  }
}