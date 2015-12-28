/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dodgeballgame.Menus;

import dodgeballgame.FileManager.FileManager;
import dodgeballgame.Game;
import dodgeballgame.GamePanel;
import dodgeballgame.ImageEditor;
import static dodgeballgame.Menus.SettingsMenu.NUM_SETTINGS;
import static dodgeballgame.Menus.SettingsMenu.namesList;
import dodgeballgame.PowerUpManager;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;

/**
 *
 * @author Sam
 */
public class LoadMenu extends Menu {
    
    public String SAVE_DIRECTORY = new String("D:/Users/Sam/Documents/Ideas/Dodgeball/GameSaves/save");
    public String SAVE_INFO = new String("D:/Users/Sam/Documents/Ideas/Dodgeball/GameSaves/info.txt");
    
    FileManager fileManager;
    
    private int numSaves;
    private String[] saveNames;
    private String[][] saves;
    
    int back;
    
    boolean savePopup;
    
    private int[] pos = new int[2]; 
    
    int fontSizeLarge, fontSizeSmall;
    
    BufferedImage popup;
    
    public LoadMenu() {
        fontSizeLarge = WIDTH/40;
        fontSizeSmall = WIDTH/50;
        back = 0;
        numSaves = 0;
        fileManager = new FileManager(SAVE_INFO);
        loadSaves();
        pos[0] = 1;
        pos[1] = numSaves;
        positions = pos;
        savePopup = false;
        
        try {
            popup = ImageIO.read(new File("Images/savePopup.png"));
        } catch (IOException e) {
            
        }
        
        ImageEditor im = new ImageEditor(popup);
        popup = im.scale((double)WIDTH/1920d);
    }
    
    @Override
    public void renderMenu(Graphics2D g) {

        g.setPaint(Color.white);
        
        int x = WIDTH/2;
        int y = (int)(INNER_Y_START * 1.5);
        int yOffset = INNER_MENU_HEIGHT/10;
        
        for (int i = 0; i < 9; i++) {
            float opacity = (float)(5f-Math.sqrt((i-4)*(i-4)))/5f;
            
            int cursorPlace = (cursor[1] + i - 4)%numSaves;
            if(cursorPlace < 0) cursorPlace += numSaves;      
            
            if(i == 4) {
                g.setPaint(Color.white);
                g.setFont(new Font("Sans Serif", Font.BOLD, fontSizeLarge));
                g.fillRect(x - 300,y + i*yOffset - fontSizeSmall, 6,fontSizeSmall + 2);  
                g.fillRect(x + 300,y + i*yOffset - fontSizeSmall, 6,fontSizeSmall + 2); 
                g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.2f));
                g.fillRect(x - 300,y + i*yOffset - fontSizeSmall, 600,fontSizeSmall + 2); 
                g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
            } else {
                g.setColor(new Color(80,80,180));
                g.setFont(new Font("Sans Serif", Font.BOLD, fontSizeSmall)); 
                g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity));
            }
            centreString(saveNames[cursorPlace], g, x, y + i*yOffset);
        } 
        
        if (savePopup) {
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.4f));
            g.setColor(Color.black);
            g.fillRect(0,0, WIDTH,HEIGHT - 80); 
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
            g.drawImage(popup, x - popup.getWidth()/3, HEIGHT/3, null);
        }
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
    }
    
    public void setBack(int i) {
        back = i;
    }
    
    private void centreString(String s, Graphics2D g, int x, int y) {
        int stringLen = (int)
            g.getFontMetrics().getStringBounds(s, g).getWidth();
        g.drawString(s, x - stringLen/2, y);
    }
    
    private void loadSaves() {
        try {
            numSaves = fileManager.countLines() - 1;
            String[] saveFile  = fileManager.openFile();
            saveNames = new String[numSaves];
            for(int i = 1; i<saveFile.length; i++) {
                saveNames[i-1] = saveFile[i];
            }
            
            saves = new String[numSaves][];
        } catch (IOException e) {}
        
        for (int i = 0; i<numSaves; i++) {
            String filePath = new String(SAVE_DIRECTORY + i + ".txt");
            try {
                FileManager fm = new FileManager(filePath);
                String[] saveData = fm.openFile();
                saves[i] = saveData;
            } catch (IOException e) {}   
        }  
           
    }
    
    private void applySave() {
        FileManager fm = new FileManager(SAVE_DIRECTORY + cursor[1] + ".txt");
        String[] entries = new String[SettingsMenu.valuesList.length + 16];
        for( int i = 0; i<SettingsMenu.valuesList.length; i++) {
            entries[i] = new String("" + SettingsMenu.valuesList[i]); 
        }
        int i = SettingsMenu.valuesList.length;
        for (int x = 0; x < 4; x++) {
            for (int y = 0; y < 4; y++) {
                entries[i] = new String("" + PowerUpManager.powerUpFreqs[x][y]);
                i++;
            }
        }

        try {
            
        fm.writeLine(entries);
        } catch (IOException e) {
            
        } 
        fm.close();
    }
    
    private void applyLoad() {
        loadSaves();
        String[] currentSave = saves[cursor[1]];
        
        for (int i = 0; i<SettingsMenu.NUM_SETTINGS; i++) {
            SettingsMenu.valuesList[i] = Double.parseDouble(currentSave[i]);
        }
        int i = SettingsMenu.NUM_SETTINGS;
        for (int x = 0; x < 4; x++) {
            for (int y = 0; y < 4; y++) {
                System.out.println(Integer.parseInt(currentSave[i]));
                PowerUpManager.powerUpFreqs[x][y] = Integer.parseInt(currentSave[i]);
                i++;
            }
        }

        SettingsMenu.applyLoad();
    }
    
    @Override
    public void yButton() {
        GamePanel.soundManager.menu(5);
        if (savePopup) {
            applyLoad();
            savePopup = false;
            GamePanel.menu = GamePanel.matchSettings;
        } else {

        }
    }
    
    @Override
    public void xButton() {
        GamePanel.soundManager.menu(5);
        if (savePopup) {
            applySave();
            savePopup = false;
            GamePanel.menu = GamePanel.matchSettings;
        } else {

        }
    }
    
    @Override
    public void select() {
        GamePanel.soundManager.menu(6);
        if (savePopup) {

        } else {
            savePopup = true;
        }
    }
    
    @Override
    public void back() {
        GamePanel.soundManager.menu(7);
        if (savePopup) {
            savePopup = false;
        } else {
            GamePanel.menu = GamePanel.matchSettings;
        }
    }
}
