/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
import hiperheuristica.PieceList;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
//import java.io.FileReader;

package parsing;

/**
 *
 * @author Priscila Angulo
 */
public class Parser {
    
    public Parser(){
        
    }
    
    public PieceList processFile(String file) throws FileNotFoundException{
        PieceList pieceList = new PieceList();
        FileReader fileReader = new FileReader(file);
        BufferedReader bufReader = new BufferedReader(fileReader);
        try {
            StringBuilder sb = new StringBuilder();
            String line = bufReader.readLine();
            
            while (line != null) {
                sb.append(line);
                sb.append("\n");
                line = br.readLine();
            }
            String everything = sb.toString();
            return pieceList;
        } finally {
            br.close();
        }
    }
   
    
}
