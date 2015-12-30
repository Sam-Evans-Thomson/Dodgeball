/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dodgeballgame.Menus;

import dodgeballgame.Game;
import dodgeballgame.GamePanel;
import dodgeballgame.ImageEditor;
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
public class SettingsMenu extends Menu {
    
    public static int NUM_SETTINGS = 10;
    private int[] pos = {1,NUM_SETTINGS};
    
    public static String[] namesList = new String[NUM_SETTINGS];
    public static double[] valuesList = new double[NUM_SETTINGS];
    
    public static double[] valuesList2 = new double[NUM_SETTINGS];;
    public static boolean accept;
    
    int fontSizeLarge, fontSizeSmall;
    
    BufferedImage selectImage;
    BufferedImage acceptImage;
    
    public SettingsMenu() {
        positions = pos;
        fontSizeLarge = WIDTH/40;
        fontSizeSmall = WIDTH/50;
        initSettings();
        
        try {
            selectImage = ImageIO.read(new File("Images/select.png"));
            acceptImage = ImageIO.read(new File("Images/accept.png"));
        } catch (IOException e) {
            
        }
        
        ImageEditor im = new ImageEditor(selectImage);
        selectImage = im.scale((double)WIDTH/1920d);
        im.setImage(acceptImage);
        acceptImage = im.scale((double)WIDTH/1920d);
    }
    
    public void initSettings() {
        accept = false;
        
        namesList[0] = "Number of Players";
        namesList[1] = "Starting Health";
        namesList[2] = "Starting Balls";
        namesList[3] = "Wall Bounce Factor";
        namesList[4] = "Soft Back Wall";
        namesList[5] = "Friendly Fire";
        namesList[6] = "Winning Score";
        namesList[7] = "Powerup Spawn Chance";
        namesList[8] = "Points per Goal";
        namesList[9] = "Goals on/off";
        
        valuesList[0] = 3;
        valuesList[1] = 10;
        valuesList[2] = 3;
        valuesList[3] = 0.8;
        valuesList[4] = 1;
        valuesList[5] = 0;
        valuesList[6] = 20;
        valuesList[7] = 0.5;
        valuesList[8] = 2;
        valuesList[9] = 1;
    }
    
    @Override
    public void renderMenu(Graphics2D g) {

        g.setPaint(Color.white);
        
        int x = (int)(INNER_X_START*2);
        int x2 = (int)(INNER_X_END*0.9);
        int y = (int)(INNER_Y_START * 1.3);
        int yOffset = INNER_MENU_HEIGHT/10;
        
        for (int i = 0; i < 9; i++) {
            float opacity = (float)(5f-Math.sqrt((i-4)*(i-4)))/5f;
            int fontSize = (int)((float)fontSizeLarge*((5f-Math.sqrt((i-4)*(i-4)))/7.5f + 0.333f));
            
            int cursorPlace = (cursor[1] + i - 4) % NUM_SETTINGS;
            if(cursorPlace < 0) cursorPlace += NUM_SETTINGS;      
            
            if(i == 4) {
                g.setPaint(Color.white);
                g.setFont(new Font("Sans Serif", Font.BOLD, fontSizeLarge));
                g.fillRect(x2 - 80,y + i*yOffset - fontSizeSmall, 6,fontSizeSmall + 2);  
                g.fillRect(x2 + 80,y + i*yOffset - fontSizeSmall, 6,fontSizeSmall + 2); 
                g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.2f));
                g.fillRect(x2 - 80,y + i*yOffset - fontSizeSmall, 160,fontSizeSmall + 2); 
                g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
            } else {
                g.setColor(new Color(40,40,100));
                g.setFont(new Font("Sans Serif", Font.BOLD, fontSize));
                g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity));
            }
            g.drawString(namesList[cursorPlace], x, y + i*yOffset);
            centreString(getString(cursorPlace), g, x2, y + i*yOffset);

        }   
        
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
        g.drawImage(selectImage, WIDTH/2 - selectImage.getWidth()/3, INNER_Y_START + INNER_MENU_HEIGHT, null);
        if (accept) g.drawImage(acceptImage, WIDTH/2 - acceptImage.getWidth()/3, INNER_Y_START, null);
    }
    
    private String getString(int cursor) {
        double val;
        // Booleans
        if (cursor == 4 || cursor == 5 || cursor == 9) {
            val = valuesList[cursor];
            if (val > 0) return "On";
            else return "Off";
        }
        // integers
        if (cursor == 0 || cursor == 1 || cursor == 2 || cursor == 6 || cursor == 8) {
            val = valuesList[cursor];
            return new String("" + (int)val);
        }
        return new String(""+valuesList[cursor]);        
    }
    
    private void centreString(String s, Graphics2D g, int x, int y) {
        int stringLen = (int)
            g.getFontMetrics().getStringBounds(s, g).getWidth();
        g.drawString(s, x - stringLen/2, y);
    }
    
    public static void applyLoad() {
        GamePanel.newGame(valuesList);
        accept = false;
    }
    
    private void changeValue(int i) {
        if (!accept) {
            for (int j = 0; j<valuesList.length; j++) {
                valuesList2[j] = valuesList[j];
            }
            accept = true;
        }
        
        double value = valuesList[cursor[1]];
        
        switch (cursor[1]) {
            case 0 : value = moveThrough(GamePanel.minNumPlayers,GamePanel.numControllers,value,i);
                break;
            case 1 : value = moveThrough(1,100,value,i);
                break;
            case 2 : value = moveThrough(1,50,value,i);
                break;
            case 3 : value = moveThrough(0.1,3,value,0.1*i);
                break;
            case 4 : value = moveThrough(0,1,value,i);
                break;
            case 5 : value = moveThrough(0,1,value,i);
                break;
            case 6 : value = moveThrough(1,99,value,i);
                break;
            case 7 : value = moveThrough(0,1,value,0.1*i);
                break;
            case 8 : value = moveThrough(0,10,value,i);
                break;
            case 9 : value = moveThrough(0,1,value,i);
                break;    
        }
        
        valuesList[cursor[1]] = value;

    }
    
    private double moveThrough(double start, double end, double val, double dist) {
        if (dist > end-start) return end;
        if (dist < start-end) return start;
        val += dist;
        if (val > end) val = start;
        if (val < start) val = end;
        return Math.round(val*1000000)/1000000.0;
    }
    
    @Override
    public void select() {
        if (accept) {
            GamePanel.soundManager.menu(6);
            applyLoad();
        }
    }
    
    @Override
    public void selectButton() {
        GamePanel.soundManager.menu(6);
        GamePanel.menu = GamePanel.loadMenu;
        GamePanel.loadMenu.setBack(0);
    }
    
    @Override
    public void right() {
        GamePanel.soundManager.menu(8);
        changeValue(1);
    }
    
    @Override
    public void left() {
        GamePanel.soundManager.menu(8);
        changeValue(-1);
    }
    
    @Override
    public void rightTrigger() {
        GamePanel.soundManager.menu(8);
        changeValue(10);
    }
    
    @Override
    public void leftTrigger() {
        GamePanel.soundManager.menu(8);
        changeValue(-10);
    }
    
    public void load() {
        
    }
    
    
    @Override
    public void back() {
        GamePanel.soundManager.menu(7);
        if (accept) {
            accept = false;
            for (int j = 0; j<valuesList.length; j++) {
                valuesList[j] = valuesList2[j];
            }
        } else {
        GamePanel.menu = GamePanel.startMenu;
        }
    }
}
