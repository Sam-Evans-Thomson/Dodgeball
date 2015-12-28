/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dodgeballgame.Menus;

import dodgeballgame.GamePanel;
import dodgeballgame.ImageEditor;
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
    
    public int[] positions;
    public int[] cursor;
    
    public int[][] menuArray;
    public int[] currentMenu;
    
    private int menuStage;
    public final int START = 0;
    public final int POWERUPS = 1;
       
    public BufferedImage menu;
    
    public Menu(){
        init();
    }   
    
    public void init() {
        menuArray = new int[2][];
        
        cursor = new int[2];
        cursor[0] = cursor[1] = 0;

        HEIGHT = GamePanel.screenHEIGHT;
        WIDTH = GamePanel.screenWIDTH;
        
        MENU_WIDTH = (int)(0.83*WIDTH);
        MENU_HEIGHT = (int)(0.74*HEIGHT);
        
        INNER_MENU_WIDTH = (int)(0.75*WIDTH);
        INNER_MENU_HEIGHT = (int)(0.55*HEIGHT);
        
        INNER_X_START = (WIDTH - INNER_MENU_WIDTH)/2;
        INNER_Y_START = (HEIGHT - INNER_MENU_HEIGHT)/2;
        INNER_X_END += INNER_MENU_WIDTH;
        INNER_Y_END += INNER_MENU_HEIGHT;                
        try {
            menu = ImageIO.read(new File("Images/menu.png"));
        } catch (IOException e) {
        }
        
        ImageEditor im = new ImageEditor(menu);
        double size = menu.getWidth();
        menu = im.scale(MENU_WIDTH/size);
    }
    
    public void update() {
        
    }
    
    public void moveCursor(int x, int y) {
        GamePanel.soundManager.menu(8);
        if (x>1) x = 1;
        else if (x<-1) x = -1;
        if (y>1) y = 1;
        else if (y<-1) y = -1;
        
        cursor[0] += x;
        cursor[1] += y;
        if (cursor[0] < 0) cursor[0] = positions[0] - 1;
        if (cursor[1] < 0) cursor[1] = positions[1] - 1;
        cursor[0]%=positions[0];
        cursor[1]%=positions[1];
    }
    
    public void render(Graphics2D g) {
        g.setPaint(Color.black);
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
        g.fillRect(0,0,WIDTH, HEIGHT - 80);
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
        g.drawImage(menu,(WIDTH - MENU_WIDTH)/2 , (HEIGHT - MENU_HEIGHT-70)/2, null);
        renderMenu(g);
    }
    
    public void renderMenu(Graphics2D g) {
        
    }
    
    public void changeMenu(Menu menu) {
        GamePanel.menu = menu;
    }
    
    public void select() {
        
    }
    
    public void selectButton() {

    }
    
    public void xButton() {
        
    }
    
    public void yButton() {
        
    }
    
    public void right() {
        
    }
    
    public void left() {
        
    } 
    
    public void back() {
        
    }
    
    public void rightTrigger() {
        
    }
    
    public void leftTrigger() {
        
    }
}
