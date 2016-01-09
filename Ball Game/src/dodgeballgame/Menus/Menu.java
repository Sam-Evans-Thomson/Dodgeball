/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dodgeballgame.Menus;

import dodgeballgame.Cursor;
import dodgeballgame.GamePanel;
import dodgeballgame.ImageEditor;
import dodgeballgame.Tools;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 *
 * @author Sam
 */
public class Menu {

    public int INNER_MENU_WIDTH;
    public int INNER_MENU_HEIGHT;
    public int MENU_WIDTH;
    public int MENU_HEIGHT;
    public int WIDTH;
    public int HEIGHT;
    public int INNER_X_START;
    public int INNER_X_END;
    public int INNER_Y_START;
    public int INNER_Y_END;
    
    public int[][] menuArray;
    public int[] currentMenu;
    
    private int menuStage;
    public final int START = 0;
    public final int POWERUPS = 1;
       
    public BufferedImage menu;
    
    public boolean accept;
        
    public BufferedImage selectImage;
    public BufferedImage acceptImage;
    
    
    
    public Menu(){
        
        menuArray = new int[2][];
        HEIGHT = GamePanel.screenHEIGHT;
        WIDTH = GamePanel.screenWIDTH;
        
        MENU_WIDTH = (int)(0.83*WIDTH);
        MENU_HEIGHT = (int)(0.81*HEIGHT);
        
        INNER_MENU_WIDTH = (int)(0.75*WIDTH);
        INNER_MENU_HEIGHT = (int)(0.6*HEIGHT);
        
        INNER_X_START = (WIDTH - INNER_MENU_WIDTH)/2;
        INNER_Y_START = (int)((HEIGHT - INNER_MENU_HEIGHT)*0.48);
        INNER_X_END = INNER_X_START + INNER_MENU_WIDTH;
        INNER_Y_END = INNER_Y_START + INNER_MENU_HEIGHT;                
        try {
            menu = ImageIO.read(new File("Images/menu.png"));
            selectImage = ImageIO.read(new File("Images/select.png"));
            acceptImage = ImageIO.read(new File("Images/accept.png"));
        } catch (IOException e) {}
        
        selectImage = Tools.scaleImage(selectImage,(double)WIDTH/1920d);
        acceptImage = Tools.scaleImage(acceptImage,(double)0.8*WIDTH/1920d);
        menu = Tools.sizeImage(menu,MENU_WIDTH);
    }   
    
    public void update() {
        
    }
    
    public void moveCursor(int playerNumber, int x, int y) {

    }

    
    public void render(Graphics2D g) {
        g.setPaint(Color.black);
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.6f));
        g.fillRect(0,0,WIDTH, HEIGHT);
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
        g.drawImage(menu,(WIDTH - MENU_WIDTH)/2 , (HEIGHT - MENU_HEIGHT)/2 - MENU_HEIGHT/16, null);
        renderGlow(g);
        renderMenu(g);
        
    }
    
    public void renderLoad(Graphics2D g) {
        g.drawImage(selectImage, INNER_X_START, INNER_Y_END - selectImage.getHeight(), null);
        if (accept) g.drawImage(acceptImage, INNER_X_END - acceptImage.getWidth(), (int)((HEIGHT - MENU_HEIGHT)/2 - 0.35*acceptImage.getHeight()), null);
    }
    
    public void glowLeft(Graphics2D g) {
        g.setColor(Color.white);
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.03f));
        g.fillRect((int)(0.80*INNER_X_START), 
                (int)(0.80*INNER_Y_START), 
                (int)(2.2*INNER_X_START), 
                (int)(INNER_MENU_HEIGHT + 0.39*INNER_Y_START));
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
    }
     
    public void glowRight(Graphics2D g) {
        g.setColor(Color.white);
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.03f));
        g.fillRect((int)(3*INNER_X_START), 
                (int)(0.80*INNER_Y_START),
                (int)(INNER_X_END - 2.8*INNER_X_START), 
                (int)(INNER_MENU_HEIGHT + 0.39*INNER_Y_START));
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
    }
    
    public void renderGlow(Graphics2D g) {
        
    }

    
    public void renderMenu(Graphics2D g) {
        
    }
    
    public void select() {
        
    }
    
    public void back() {
        
    }    
    public void selectButton() {

    }
    
    public void xButton() {
        
    }
    
    public void yButton() {
        
    }
    
    public void right(int playerNumber) {
        GamePanel.soundManager.menu(8);
        moveCursor(playerNumber, 1, 0); 
    }
    
    public void left(int playerNumber) {
        GamePanel.soundManager.menu(8);
        moveCursor(playerNumber, -1, 0); 
    } 
    
    public void up(int playerNumber) {
        GamePanel.soundManager.menu(8);
        moveCursor(playerNumber, 0, -1); 
    }
    
    public void down(int playerNumber) {
        GamePanel.soundManager.menu(8);
        moveCursor(playerNumber, 0, 1); 
    }
    
    public void rightTrigger() {
        
    }
    
    public void leftTrigger() {
        
    }
}
