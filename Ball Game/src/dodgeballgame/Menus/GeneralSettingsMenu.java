/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dodgeballgame.Menus;

import dodgeballgame.Cursor;
import dodgeballgame.GamePanel;
import dodgeballgame.ImageEditor;
import dodgeballgame.Settings.SpecificSettings.GeneralSettings;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 *
 * @author Sam
 */
public class GeneralSettingsMenu extends Menu{
    
    public GeneralSettings settings;
    private int[] pos;
    final int NUM_SETTINGS;
    int fontSizeLarge, fontSizeSmall;
    
    public static boolean accept;
    
    BufferedImage selectImage, acceptImage;
    
    public Cursor[] cursors = new Cursor[4];
    
    public GeneralSettingsMenu() {
        
        accept = false;
        settings = new GeneralSettings();
        NUM_SETTINGS = settings.size();
        for (int i = 0; i < 4; i++) cursors[i] = new Cursor(1,NUM_SETTINGS);

        fontSizeLarge = WIDTH/40;
        fontSizeSmall = WIDTH/50;
        
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
    
        
    @Override    
    public void moveCursor(int playerNumber, int x, int y) {
        cursors[playerNumber].moveCursor(x,y);
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
            
            int cursorPlace = (cursors[0].y + i - 4) % NUM_SETTINGS;
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
                g.setColor(new Color(80,80,180));
                g.setFont(new Font("Sans Serif", Font.BOLD, fontSizeSmall));
                g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity));
            }
            g.drawString(settings.getName(cursorPlace), x, y + i*yOffset);
            centreString(settings.getValueString(cursorPlace), g, x2, y + i*yOffset);

        }   
        
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
        if (accept) g.drawImage(acceptImage, WIDTH/2 - acceptImage.getWidth()/3, INNER_Y_START, null);
    }

    private void centreString(String s, Graphics2D g, int x, int y) {
        int stringLen = (int)
        g.getFontMetrics().getStringBounds(s, g).getWidth();
        g.drawString(s, x - stringLen/2, y);
    }
    
    private void changeValue(int i) {
        settings.changeValue(cursors[0].y,i);
    }
    
    public void apply() {
        settings.apply();
    }
    
    public void save() {
        settings.save();
    }

    @Override
    public void select() {
        if (accept) {
            GamePanel.soundManager.menu(6);
        }
    }
    
    @Override
    public void selectButton() {
        GamePanel.soundManager.menu(6);
        GamePanel.menuManager.changeMenu("LOAD");
        GamePanel.menuManager.loadMenu.setBack(0);
    }
    
    @Override
    public void right(int playerNumber) {
        if(playerNumber == 0) {
            GamePanel.soundManager.menu(8);
            changeValue(1);
        }
    }
    
    @Override
    public void left(int playerNumber) {
        if(playerNumber == 0) {
            GamePanel.soundManager.menu(8);
            changeValue(-1);
        }
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
    
    
    @Override
    public void back() {
        GamePanel.soundManager.menu(7);
        GamePanel.menuManager.changeMenu("START");
    }
}

