/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dodgeballgame.Menus;

import dodgeballgame.ArenaManager;
import dodgeballgame.Cursor;
import dodgeballgame.GamePanel;
import dodgeballgame.Tools;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 *
 * @author Sam
 */
public class ArenaMenu extends Menu {
    
    public ArenaManager manager = GamePanel.arenaManager;
    
    public boolean accept;
    
    public BufferedImage arrowRight;
    public BufferedImage arrowLeft;
    
    public Cursor[] cursors = new Cursor[4];
   
    public int xPos = (int)(INNER_X_START*3.1);
    public int yPos = INNER_Y_START;
    public int width = (int)((INNER_X_END - xPos)*1.35);
    public int height = INNER_MENU_HEIGHT;
    
    public int buttonH = INNER_MENU_HEIGHT / 50;
    public int buttonW = INNER_MENU_WIDTH / 40;
    public int buttonXOffset = width/5;
    
    public ArenaMenu() {
        accept = false;
        for (int i = 0; i < 4; i++) {
            cursors[i] = new Cursor(ArenaManager.NUM_ARENAS,
                    ArenaManager.NUM_GOAL_POS);
        }
        try {
            arrowRight = ImageIO.read(new File("Images/arrowRight.png"));
            arrowLeft = ImageIO.read(new File("Images/arrowLeft.png"));
        } catch (IOException e) { }
        
        arrowRight = Tools.sizeImage(arrowRight, 60);
        arrowLeft = Tools.sizeImage(arrowLeft, 60);
    }
    
        
    @Override    
    public void moveCursor(int playerNumber, int x, int y) {
        cursors[playerNumber].moveCursor(x,y);
    }
    
    @Override
    public void renderMenu(Graphics2D g) {
        GamePanel.menuManager.startMatchSettingsMenu.renderMenu(g);
        
        BufferedImage preview = manager.getArenaDisplay(cursors[0].x, cursors[0].y);
        preview = Tools.sizeImage(preview, 700);
        g.drawImage(preview, (int)(xPos*1.17) , HEIGHT/4, null);
        g.drawImage(arrowLeft, 
                (int)(xPos*1.17 - 1.3*arrowLeft.getWidth()), 
                HEIGHT/4 + preview.getHeight()/2 - arrowLeft.getHeight()/2, 
                null);
        g.drawImage(arrowRight, 
                (int)(xPos*1.17 + preview.getWidth() + 0.3*arrowRight.getWidth()), 
                HEIGHT/4 + preview.getHeight()/2 - arrowLeft.getHeight()/2, 
                null);
        
        g.setColor(new Color(255,255,255));
        g.setFont(new Font("Sans Serif", Font.BOLD, 80));
        Tools.centreStringHor(manager.getName(cursors[0].x),
                g,
                (int)(xPos*1.17 + preview.getWidth()/2),
                (int)(0.7*HEIGHT));
    }
    
    @Override
    public void back() {
        GamePanel.soundManager.menu(7);
        if (accept) {
            accept = false;
        } else {
        GamePanel.menuManager.changeMenu("START_MATCH_SETTINGS");
        }
    }
    
    @Override
    public void up(int playerNumber) {
        moveCursor(playerNumber,0, -1);
    }
    
    @Override    
    public void down(int playerNumber) {
        moveCursor(playerNumber,0, 1);
    }
    
    @Override
    public void select() {
        manager.setArena(cursors[0].x);
        manager.setGoals(cursors[0].y);
    }
}
