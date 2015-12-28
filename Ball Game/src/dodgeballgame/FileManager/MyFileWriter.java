package dodgeballgame.FileManager;

import java.io.*;

/**
 * Write a description of class FileWriter here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class MyFileWriter
{
    // instance variables - replace the example below with your own
    private final String FILE_PATH;
    private final PrintWriter writer;

    /**
     * 
     * @param path
     * @param text
     * @throws IOException 
     */
    public MyFileWriter(String path, String text) throws IOException {
        FILE_PATH = path;
        writer = new PrintWriter(FILE_PATH, "UTF-8");
        writeLine(text);
    }
    
    public MyFileWriter(String path, String[] text) throws IOException {
        FILE_PATH = path;
        writer = new PrintWriter(FILE_PATH, "UTF-8");
        for (String text1 : text) {
            writeLine(text1);
        }
    }
    
    private void writeLine(String text) throws IOException {
        writer.println(text);
    }
    
    public void close() {
        writer.close();
    }

}
