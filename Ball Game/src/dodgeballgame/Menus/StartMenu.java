/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dodgeballgame.Menus;

import dodgeballgame.Cursor;
import dodgeballgame.Game;
import dodgeballgame.GamePanel;
import dodgeballgame.Tools;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

/**
 *
 * @author Sam
 */
public class StartMenu extends Menu {
    
    int fontSizeLarge, fontSizeSmall;
    
    int yOffset;
    
    public Cursor[] cursors = new Cursor[4];
    
    public StartMenu() {
        for (int i = 0; i < 4; i++) cursors[i] = new Cursor(1,4);
        fontSizeLarge = WIDTH/40;
        fontSizeSmall = WIDTH/50;
        
        yOffset = INNER_MENU_HEIGHT/5;
    }
    
    @Override
    public void renderMenu(Graphics2D g) {
        
        
        g.setPaint(Color.white);
        String[] menuNames = {"MATCH SETTINGS",
            "GENERAL SETTINGS",
            "CHOOSE CHARACTERS", 
            "EXIT"};
        
        int i = 0;
        while( i < cursors[0].y) {
            g.setFont(new Font("Sans Serif", Font.BOLD, fontSizeSmall));
            g.setColor(new Color(80,80,180));
            Tools.centreStringHor(menuNames[i],g,WIDTH/2, INNER_Y_START + yOffset*(i+1));
            i++;
        }
            g.setFont(new Font("Sans Serif", Font.BOLD, fontSizeLarge));
            g.setPaint(Color.white);
            Tools.centreStringHor(menuNames[i],g,WIDTH/2, INNER_Y_START + yOffset*(i+1));
            i++;
        while( i < menuNames.length) {
            g.setFont(new Font("Sans Serif", Font.BOLD, fontSizeSmall));
            g.setColor(new Color(80,80,180));
            Tools.centreStringHor(menuNames[i],g,WIDTH/2, INNER_Y_START + yOffset*(i+1));
            i++;
        } 
    }
    
        
    @Override    
    public void moveCursor(int playerNumber, int x, int y) {
        cursors[playerNumber].moveCursor(x,y);
    }
    
    @Override
    public void select() {
        if (cursors[0].y == 0) {
            GamePanel.menuManager.changeMenu("START_MATCH_SETTINGS");
        } else if (cursors[0].y == 1) {
            GamePanel.menuManager.changeMenu("GENERAL_SETTINGS");
        } else if (cursors[0].y == 2) {
            GamePanel.menuManager.changeMenu("CHARACTER");
        } else if (cursors[0].y == 3) {
            Game.close();
        }
        
    }
    
    @Override
    public void back() {
        GamePanel.changeGameState();
    }
}
