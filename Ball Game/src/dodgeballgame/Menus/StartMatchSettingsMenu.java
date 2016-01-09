/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dodgeballgame.Menus;

import dodgeballgame.Cursor;
import dodgeballgame.GamePanel;
import dodgeballgame.Tools;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

/**
 *
 * @author Sam
 */
public class StartMatchSettingsMenu extends Menu{
    
    
    
    int fontSizeLarge, fontSizeSmall;
    
    int yOffset;
    
    public static boolean accept;
    
    public Cursor[] cursors = new Cursor[4];
    
    public StartMatchSettingsMenu() {
        accept = false;
        
        for (int i = 0; i < 4; i++) cursors[i] = new Cursor(1,5);
        fontSizeLarge = WIDTH/40;
        fontSizeSmall = WIDTH/50;
        
        yOffset = INNER_MENU_HEIGHT/8;
    }
    
        
    @Override    
    public void moveCursor(int playerNumber, int x, int y) {
        cursors[playerNumber].moveCursor(x,y);
    }
    
    @Override
    public void renderMenu(Graphics2D g) {

        g.setPaint(Color.white);
        g.setFont(new Font("Sans Serif", Font.BOLD, fontSizeSmall));
        Tools.centreStringHor("GAME MODE: " + GamePanel.gameModeManager.gameMode.settings.type,
                g, 
                (int)(1.9*INNER_X_START), 
                (int)(1.2*INNER_Y_START));
        
        String[] menuNames = {"ARENA","GAME MODE","ITEMS", "POWERS", "MATCH SETTINGS"};
        
        int yStart = (int)(1.4*INNER_Y_START);
        int i = 0;
        while( i < cursors[0].y) {
            g.setFont(new Font("Sans Serif", Font.BOLD, fontSizeSmall));
            g.setColor(new Color(80,80,180));
            g.drawString(menuNames[i],INNER_X_START, yStart + (1+i)*yOffset);
            i++;
        }
            g.setFont(new Font("Sans Serif", Font.BOLD, fontSizeLarge));
            g.setPaint(Color.white);
            g.drawString(menuNames[i],INNER_X_START, yStart + (1+i)*yOffset);
            i++;
        while( i < menuNames.length) {
            g.setFont(new Font("Sans Serif", Font.BOLD, fontSizeSmall));
            g.setColor(new Color(80,80,180));
            g.drawString(menuNames[i],INNER_X_START, yStart + (1+i)*yOffset);
            i++;
        }    

        if (accept) g.drawImage(acceptImage, WIDTH/2 - acceptImage.getWidth()/2, INNER_Y_START, null);
        g.setPaint(Color.WHITE);
        g.fillRect((int)(INNER_X_START*3), 
                INNER_Y_START, 
                3, 
                INNER_MENU_HEIGHT);
        renderLoad(g);
    }

    @Override
    public void renderGlow(Graphics2D g) {
        glowLeft(g);
    }
    
    public void applyLoad() {
        GamePanel.newGame();
        accept = false;
    }
    
    @Override
    public void select() {
        GamePanel.soundManager.menu(6);
        if (accept) {
            applyLoad();
        } else if (cursors[0].y == 0) {
            GamePanel.menuManager.changeMenu("ARENA");
        } else if (cursors[0].y == 1) {
            GamePanel.menuManager.changeMenu("GAME_MODE");
        } else if (cursors[0].y == 2) {
            GamePanel.menuManager.changeMenu("ITEM");
        } else if (cursors[0].y == 3) {
            //GamePanel.changeMenu("POWER");
        } else if (cursors[0].y == 4) {
            GamePanel.menuManager.changeMenu("MATCH_SETTINGS");
        } 
    }
    
    @Override
    public void selectButton() {
        GamePanel.soundManager.menu(6);
        GamePanel.menuManager.changeMenu("LOAD");
        GamePanel.menuManager.loadMenu.setBack(0);
    }
    
    @Override
    public void back() {
        GamePanel.soundManager.menu(7);
        GamePanel.menuManager.changeMenu("START");
    }

}
