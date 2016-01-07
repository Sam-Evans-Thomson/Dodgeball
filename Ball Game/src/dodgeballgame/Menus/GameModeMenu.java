/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dodgeballgame.Menus;

import dodgeballgame.Cursor;
import dodgeballgame.GamePanel;
import static dodgeballgame.Menus.MatchSettingsMenu.accept;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

/**
 *
 * @author Sam
 */
public class GameModeMenu extends Menu{
    
    public Cursor[] cursors = new Cursor[4];
    
    int fontSizeLarge, fontSizeSmall;
    
    public int numGameModes;

    public GameModeMenu() {
        numGameModes = GamePanel.gameModeManager.gameModes.size();
        for (int i = 0; i < 4; i++) cursors[i] = new Cursor(1,numGameModes);
        fontSizeLarge = WIDTH/40;
        fontSizeSmall = WIDTH/50;
    }
    
    @Override
    public void renderMenu(Graphics2D g) {
        GamePanel.menuManager.startMatchSettingsMenu.renderMenu(g);
        
        g.setPaint(Color.white);
        
        int x = (int)(INNER_X_START*3.15);
        int x2 = (int)(INNER_X_END*1.1);
        int y = (int)(INNER_Y_START * 1.3);
        int yOffset = INNER_MENU_HEIGHT/10;
        
        for (int i = 0; i < 9; i++) {
            float opacity = (float)(5f-Math.sqrt((i-4)*(i-4)))/5f;
            
            int cursorPlace = (cursors[0].y + i - 4) % numGameModes;
            if(cursorPlace < 0) cursorPlace += numGameModes;      
            
            if(i == 4) {
                g.setPaint(Color.white);
                g.setFont(new Font("Sans Serif", Font.BOLD, fontSizeLarge));
            } else {
                g.setColor(new Color(80,80,180));
                g.setFont(new Font("Sans Serif", Font.BOLD, fontSizeSmall));
                g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity));
            }
            String modeName = GamePanel.gameModeManager.gameModes.get(cursorPlace).type;
            g.drawString(modeName, x, y + i*yOffset);

        }   
        
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
        renderLoad(g);
        if (accept) g.drawImage(acceptImage, WIDTH/2 - acceptImage.getWidth()/2, INNER_Y_START, null);
    }
        
    @Override    
    public void moveCursor(int playerNumber, int x, int y) {
        cursors[playerNumber].moveCursor(x,y);
    }
    
    @Override
    public void select() {
        GamePanel.gameModeManager.setMode(cursors[0].y);
    }
       
    @Override
    public void back() {
        GamePanel.soundManager.menu(7);
        GamePanel.menuManager.changeMenu("START_MATCH_SETTINGS");
    }
} 
