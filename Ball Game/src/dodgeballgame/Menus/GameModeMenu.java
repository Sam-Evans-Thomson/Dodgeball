/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dodgeballgame.Menus;

import dodgeballgame.Cursor;
import dodgeballgame.GameModes.GameMode;
import dodgeballgame.GamePanel;
import dodgeballgame.Tools;
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
    
    int x = (int)(INNER_X_START*5);
    int x2 = (int)(INNER_X_END*1.1);
    int y = (int)(INNER_Y_START * 1.3);
    int yOffset = INNER_MENU_HEIGHT/10;

    public GameModeMenu() {
        numGameModes = GamePanel.gameModeManager.gameModes.size();
        for (int i = 0; i < 4; i++) cursors[i] = new Cursor(numGameModes,1);
        fontSizeLarge = WIDTH/40;
        fontSizeSmall = WIDTH/50;
    }
    
    @Override
    public void renderMenu(Graphics2D g) {
        GamePanel.menuManager.startMatchSettingsMenu.renderMenu(g);
        glowRight(g);
        
        g.setPaint(Color.white);
        g.setFont(new Font("Sans Serif", Font.BOLD, fontSizeLarge));
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
        
        GameMode gameMode = GamePanel.gameModeManager.gameModes.get(cursors[0].x);
        cursors[0].moveCursor(-1,0);
        GameMode prevGameMode = GamePanel.gameModeManager.gameModes.get(cursors[0].x);
        cursors[0].moveCursor(1,0);
        cursors[0].moveCursor(1,0);
        GameMode nextGameMode = GamePanel.gameModeManager.gameModes.get(cursors[0].x);
        String modeName = gameMode.settings.type;
        cursors[0].moveCursor(-1,0);

        Tools.centreStringHor(modeName, g, x, y);
        
        g.setPaint(new Color(80,80,180));
        g.setFont(new Font("Sans Serif", Font.BOLD, fontSizeSmall-5));
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
        Tools.centreStringHor(prevGameMode.settings.type, g, (int)(x - INNER_MENU_WIDTH*0.2), (int)(0.96*y));
        Tools.centreStringHor(nextGameMode.settings.type, g, (int)(x + INNER_MENU_WIDTH*0.2), (int)(0.96*y));
        
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
        
        g.fillRect((int)(INNER_X_START*3.15), 
                (int)(1.1*y), 
                (int)(INNER_MENU_WIDTH - INNER_X_START*2.15), 
                1);
        
        yOffset = INNER_MENU_HEIGHT/15;
        g.setColor(new Color(80,80,180));
        g.setFont(new Font("Sans Serif", Font.BOLD, fontSizeSmall-12));
        
        // Render Descriptions
        for(int i = 0; i < gameMode.text.length; i++) {
            g.drawString(gameMode.text[i],
                    (int)(INNER_X_START*3.15),
                    y + (2+i)*yOffset);
        }
        
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
        renderLoad(g);
        if (accept) g.drawImage(acceptImage, WIDTH/2 - acceptImage.getWidth()/2, INNER_Y_START, null);
    }
        
    @Override    
    public void moveCursor(int playerNumber, int x, int y) {
        if(playerNumber==0) GamePanel.soundManager.menu(8);
        cursors[playerNumber].moveCursor(x,y);
    }
    
    @Override
    public void select() {
        GamePanel.soundManager.menu(6);
        GamePanel.gameModeManager.setMode(cursors[0].x);
        GamePanel.newGame();
        GamePanel.menuManager.changeMenu("START_MATCH_SETTINGS");
    }
       
    @Override
    public void back() {
        GamePanel.soundManager.menu(7);
        GamePanel.menuManager.changeMenu("START_MATCH_SETTINGS");
    }
} 
