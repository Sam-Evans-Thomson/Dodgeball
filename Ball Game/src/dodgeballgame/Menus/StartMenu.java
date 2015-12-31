/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dodgeballgame.Menus;

import dodgeballgame.Game;
import dodgeballgame.GamePanel;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

/**
 *
 * @author Sam
 */
public class StartMenu extends Menu {
    
    private int[] pos = {1,4};
    
    int fontSizeLarge, fontSizeSmall;
    
    int yOffset;
    
    public StartMenu() {
        positions = pos;
        fontSizeLarge = WIDTH/40;
        fontSizeSmall = WIDTH/50;
        
        yOffset = INNER_MENU_HEIGHT/5;
    }
    
    @Override
    public void renderMenu(Graphics2D g) {
        
        
        g.setPaint(Color.white);
        
        if (cursor[1] == 0) {
            g.setFont(new Font("Sans Serif", Font.BOLD, fontSizeLarge));
            centreString("MATCH SETTINGS", g, WIDTH/2, INNER_Y_START + yOffset);
            g.setFont(new Font("Sans Serif", Font.BOLD, fontSizeSmall));
            g.setColor(new Color(80,80,180));
            centreString("GENERAL SETTINGS", g, WIDTH/2, INNER_Y_START + yOffset*2);
            centreString("CHOOSE CHARACTERS", g, WIDTH/2, INNER_Y_START + yOffset*3);
            centreString("EXIT", g, WIDTH/2, INNER_Y_START + yOffset*4);
        } else if(cursor[1] == 1) {
            g.setFont(new Font("Sans Serif", Font.BOLD, fontSizeLarge));
            centreString("GENERAL SETTINGS", g, WIDTH/2, INNER_Y_START + 2*yOffset);
            g.setColor(new Color(80,80,180));
            g.setFont(new Font("Sans Serif", Font.BOLD, fontSizeSmall));
            centreString("MATCH SETTINGS", g, WIDTH/2, INNER_Y_START + yOffset);            
            centreString("CHOOSE CHARACTERS", g, WIDTH/2, INNER_Y_START + 3*yOffset);
            centreString("EXIT", g, WIDTH/2, INNER_Y_START + 4*yOffset);
        } else if(cursor[1] == 2) {
            g.setFont(new Font("Sans Serif", Font.BOLD, fontSizeLarge));
            centreString("CHOOSE CHARACTERS", g, WIDTH/2, INNER_Y_START + 3*yOffset);
            g.setColor(new Color(80,80,180));
            g.setFont(new Font("Sans Serif", Font.BOLD, fontSizeSmall));
            centreString("MATCH SETTINGS", g, WIDTH/2, INNER_Y_START + yOffset);            
            centreString("GENERAL SETTINGS", g, WIDTH/2, INNER_Y_START + 2*yOffset);
            centreString("EXIT", g, WIDTH/2, INNER_Y_START + 4*yOffset);
        } else if(cursor[1] == 3) {
            g.setFont(new Font("Sans Serif", Font.BOLD, fontSizeLarge));            
            centreString("EXIT", g, WIDTH/2, INNER_Y_START + 4*yOffset);
            g.setColor(new Color(80,80,180));
            g.setFont(new Font("Sans Serif", Font.BOLD, fontSizeSmall));
            centreString("MATCH SETTINGS", g, WIDTH/2, INNER_Y_START + yOffset);            
            centreString("GENERAL SETTINGS", g, WIDTH/2, INNER_Y_START + 2*yOffset);
            centreString("CHOOSE CHARACTERS", g, WIDTH/2, INNER_Y_START + 3*yOffset);
        }
    }
    
    private void centreString(String s, Graphics2D g, int x, int y) {
        int stringLen = (int)
            g.getFontMetrics().getStringBounds(s, g).getWidth();
        g.drawString(s, x - stringLen/2, y);
    }
    
    @Override
    public void select() {
        if (cursor[1] == 0) {
            GamePanel.menu = GamePanel.startMatchSettingsMenu;
        } else if (cursor[1] == 1) {
            GamePanel.menu = GamePanel.generalSettingsMenu;
        } else if (cursor[1] == 2) {
            GamePanel.menu = GamePanel.characterMenu;
        } else if (cursor[1] == 3) {
            Game.close();
        }
        
    }
    
    @Override
    public void back() {
        GamePanel.changeGameState();
    }
}
