/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dodgeballgame.FileManager;

import java.io.IOException;

/**
 *
 * @author Sam
 */
public class FileManager {
    
    MyFileReader fileReader;
    MyFileWriter fileWriter;
    String path;
    
    public FileManager(String path) {
        this.path = path;
        try {
            fileReader = new MyFileReader(path);
        } catch (IOException e) {
            
        }        
    }
    
    public void setPath(String path) {
        this.path = path;
        try {
            fileReader = new MyFileReader(path);
        } catch (IOException e) {
            
        }   
    }
    
    public String[] openFile() throws IOException {
            return fileReader.openFile();
    }
    
    public int countLines() throws IOException {
        return fileReader.readLines();
    }
    
    public void writeLine(String text) throws IOException {
        fileWriter = new MyFileWriter(path, text);
    }
    
    public void writeLine(String[] text) throws IOException {
        fileWriter = new MyFileWriter(path, text);
    }
    
    public void close() {
        fileWriter.close();
    }
}
