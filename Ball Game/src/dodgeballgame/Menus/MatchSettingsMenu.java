/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dodgeballgame.Menus;

import dodgeballgame.GamePanel;
import dodgeballgame.ImageEditor;
import dodgeballgame.Settings.SpecificSettings.MatchSettings;
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
public class MatchSettingsMenu extends Menu{
    
    /**
     *
     */
    
    private final String PATH = "D:/Users/Sam/Documents/Ideas/Dodgeball/Settings/DefaultSaves/DefaultMatchSettings.txt";
    public static MatchSettings settings;
    public MatchSettings settings2;
    private int[] pos;
    final int NUM_SETTINGS;
    int fontSizeLarge, fontSizeSmall;
    
    public static boolean accept;
    BufferedImage selectImage, acceptImage;
    
    public MatchSettingsMenu() {
        
        accept = false;
        settings = new MatchSettings(PATH);
        NUM_SETTINGS = settings.size();
        pos = new int[]{1,NUM_SETTINGS};
        
        positions = pos;
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
        
        applyLoad();
        
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
                g.setColor(new Color(80,80,180));
                g.setFont(new Font("Sans Serif", Font.BOLD, fontSizeSmall));
                g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity));
            }
            g.drawString(settings.getName(cursorPlace), x, y + i*yOffset);
            centreString(settings.getValueString(cursorPlace), g, x2, y + i*yOffset);

        }   
        
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
        g.drawImage(selectImage, WIDTH/2 - selectImage.getWidth()/3, INNER_Y_START + INNER_MENU_HEIGHT, null);
        if (accept) g.drawImage(acceptImage, WIDTH/2 - acceptImage.getWidth()/3, INNER_Y_START, null);
    }

    private void centreString(String s, Graphics2D g, int x, int y) {
        int stringLen = (int)
        g.getFontMetrics().getStringBounds(s, g).getWidth();
        g.drawString(s, x - stringLen/2, y);
    }
    
    private void changeValue(int i) {
        if (!accept) {
            settings2 = new MatchSettings(settings);
            accept = true;
        }
        settings.changeValue(cursor[1],i);
    }
    
    public void applyLoad() {
        GamePanel.matchSettings = settings;
        GamePanel.newGame();
    }
    
    // Saving these settings to the default file.
    public void save() {
        settings.save();
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

    @Override
    public void back() {
        GamePanel.soundManager.menu(7);
        if (accept) {
            accept = false;
            settings = new MatchSettings(settings2);
        } else {
        GamePanel.menu = GamePanel.startMatchSettingsMenu;
        }
    }
}
