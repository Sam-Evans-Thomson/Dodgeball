package dodgeballgame.FileManager;

import java.io.*;

/**
 * Write a description of class FileReader here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class MyFileReader
{
    // instance variables - replace the example below with your own
    final private String FILE_PATH;

    /**
     * 
     * @param path
     * @throws IOException 
     */
    public MyFileReader(String path) throws IOException {   
        FILE_PATH = path;
    }

    public String[] openFile() throws IOException {
        File f = new File(FILE_PATH);
        if( f.isFile() != true) {
            throw new IOException(); 
        }
        
        FileReader fr = new FileReader(FILE_PATH);
        String[] textData;
        try (BufferedReader textReader = new BufferedReader(fr)) {
            textData = new String[readLines()];
            int i;
            int num = readLines();
            for (i=0; i < num; i++) {
                textData[i] = textReader.readLine();
            }
        }
        return textData;
    }
    
    public int readLines() throws IOException {
        FileReader fileToRead = new FileReader(FILE_PATH);
        int numberOfLines;
        try (BufferedReader bf = new BufferedReader(fileToRead)) {
            String aLine;
            numberOfLines = 0;
            while (( aLine = bf.readLine()) != null) {
                numberOfLines++;
            }
        }
        
        return numberOfLines;
    }
}
