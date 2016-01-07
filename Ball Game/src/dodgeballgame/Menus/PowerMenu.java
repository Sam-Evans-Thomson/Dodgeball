/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dodgeballgame.Menus;

import dodgeballgame.Cursor;
import dodgeballgame.GamePanel;
import java.awt.Graphics2D;

/**
 *
 * @author Sam
 */
public class PowerMenu extends Menu{
    
    public Cursor[] cursors = new Cursor[4];
    
    public PowerMenu() {
        
    }
        
    @Override
    public void renderMenu(Graphics2D g) {
        GamePanel.menuManager.startMatchSettingsMenu.renderMenu(g);
    }
    
    @Override    
    public void moveCursor(int playerNumber, int x, int y) {
        cursors[playerNumber].moveCursor(x,y);
    }
   
    @Override
    public void back() {
        GamePanel.soundManager.menu(7);
        GamePanel.menuManager.changeMenu("START_MATCH_SETTINGS");
    }
}
