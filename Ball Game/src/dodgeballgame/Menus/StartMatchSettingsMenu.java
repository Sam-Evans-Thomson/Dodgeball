/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dodgeballgame.Menus;

import dodgeballgame.GamePanel;
import dodgeballgame.ImageEditor;
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
public class StartMatchSettingsMenu extends Menu{
    
    private int[] pos = {1,4};
    
    int fontSizeLarge, fontSizeSmall;
    
    int yOffset;
    
    public static boolean accept;
    
    BufferedImage selectImage;
    BufferedImage acceptImage;
    
    public StartMatchSettingsMenu() {
        
        accept = false;
        positions = pos;
        fontSizeLarge = WIDTH/40;
        fontSizeSmall = WIDTH/50;
        
        yOffset = INNER_MENU_HEIGHT/5;
        
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
    public void renderMenu(Graphics2D g) {

        g.setPaint(Color.white);
        
        if (cursor0[1] == 0) {
            g.setFont(new Font("Sans Serif", Font.BOLD, fontSizeLarge));
            centreString("ARENA", g, WIDTH/2, INNER_Y_START + yOffset);
            g.setFont(new Font("Sans Serif", Font.BOLD, fontSizeSmall));
            g.setColor(new Color(80,80,180));
            centreString("ITEMS", g, WIDTH/2, INNER_Y_START + yOffset*2);
            centreString("POWERS", g, WIDTH/2, INNER_Y_START + yOffset*3);
            centreString("MATCH SETTINGS", g, WIDTH/2, INNER_Y_START + yOffset*4);
        } else if(cursor0[1] == 1) {
            g.setFont(new Font("Sans Serif", Font.BOLD, fontSizeLarge));
            centreString("ITEMS", g, WIDTH/2, INNER_Y_START + 2*yOffset);
            g.setColor(new Color(80,80,180));
            g.setFont(new Font("Sans Serif", Font.BOLD, fontSizeSmall));
            centreString("ARENA", g, WIDTH/2, INNER_Y_START + yOffset);            
            centreString("POWERS", g, WIDTH/2, INNER_Y_START + 3*yOffset);
            centreString("MATCH SETTINGS", g, WIDTH/2, INNER_Y_START + 4*yOffset);
        } else if(cursor0[1] == 2) {
            g.setFont(new Font("Sans Serif", Font.BOLD, fontSizeLarge));
            centreString("POWERS", g, WIDTH/2, INNER_Y_START + 3*yOffset);
            g.setColor(new Color(80,80,180));
            g.setFont(new Font("Sans Serif", Font.BOLD, fontSizeSmall));
            centreString("ARENA", g, WIDTH/2, INNER_Y_START + yOffset);            
            centreString("ITEMS", g, WIDTH/2, INNER_Y_START + 2*yOffset);
            centreString("MATCH SETTINGS", g, WIDTH/2, INNER_Y_START + 4*yOffset);
        } else if(cursor0[1] == 3) {
            g.setFont(new Font("Sans Serif", Font.BOLD, fontSizeLarge));            
            centreString("MATCH SETTINGS", g, WIDTH/2, INNER_Y_START + 4*yOffset);
            g.setColor(new Color(80,80,180));
            g.setFont(new Font("Sans Serif", Font.BOLD, fontSizeSmall));
            centreString("ARENA", g, WIDTH/2, INNER_Y_START + yOffset);            
            centreString("ITEMS", g, WIDTH/2, INNER_Y_START + 2*yOffset);
            centreString("POWERS", g, WIDTH/2, INNER_Y_START + 3*yOffset);
        }
        
        g.drawImage(selectImage, WIDTH/2 - selectImage.getWidth()/3, INNER_Y_START + INNER_MENU_HEIGHT, null);
        if (accept) g.drawImage(acceptImage, WIDTH/2 - acceptImage.getWidth()/3, INNER_Y_START, null);
    }
    
    private void centreString(String s, Graphics2D g, int x, int y) {
        int stringLen = (int)
            g.getFontMetrics().getStringBounds(s, g).getWidth();
        g.drawString(s, x - stringLen/2, y);
    }
    
    public static void applyLoad() {
        GamePanel.newGame();
        accept = false;
    }
    
    @Override
    public void select() {
        if (accept) {
            GamePanel.soundManager.menu(6);
            applyLoad();
        } else if (cursor0[1] == 0) {
            GamePanel.menu = GamePanel.arenaMenu;
        } else if (cursor0[1] == 1) {
            GamePanel.menu = GamePanel.powerUpMenu;
        } else if (cursor0[1] == 2) {
            //GamePanel.menu = GamePanel.powerMenu;
        } else if (cursor0[1] == 3) {
            GamePanel.menu = GamePanel.matchSettingsMenu;
        }
    }
    
    @Override
    public void selectButton() {
        GamePanel.soundManager.menu(6);
        GamePanel.menu = GamePanel.loadMenu;
        GamePanel.loadMenu.setBack(0);
    }
    
    @Override
    public void back() {
        GamePanel.menu = GamePanel.startMenu;
    }

}
