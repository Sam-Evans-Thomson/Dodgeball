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
public class MatchSettingsMenu extends Menu{
    
    /**
     *
     */

    public final String PATH = "D:/Users/Sam/Documents/Ideas/Dodgeball/Settings/DefaultSaves/DefaultPlayer.txt";
    public GameMode gameMode, gameMode2;
    public int NUM_SETTINGS;
    
    private int[] pos;
    
    int fontSizeLarge, fontSizeSmall;
    
    public Cursor[] cursors = new Cursor[4];
    
    public MatchSettingsMenu() {
        
        accept = false;
        
        updateMode();
        
        
        fontSizeLarge = WIDTH/40;
        fontSizeSmall = WIDTH/50;
    }
    
    public final void updateMode() {
        gameMode = GamePanel.gameModeManager.gameMode.copy();
        gameMode2 = gameMode.copy();
        NUM_SETTINGS = gameMode.settings.size();
        for (int i = 0; i < 4; i++) cursors[i] = new Cursor(1,NUM_SETTINGS);
    }
    
        
    @Override    
    public void moveCursor(int playerNumber, int x, int y) {
        cursors[playerNumber].moveCursor(x,y);
    }

    @Override
    public void renderMenu(Graphics2D g) {
        glowRight(g);
        GamePanel.menuManager.startMatchSettingsMenu.renderMenu(g);
        
        g.setPaint(Color.white);
        
        int x = (int)(INNER_X_START*3.15);
        int x2 = (int)(INNER_X_END*0.9);
        int y = (int)(INNER_Y_START * 1.3);
        int yOffset = INNER_MENU_HEIGHT/10;
        
        for (int i = 0; i < 9; i++) {
            float opacity = (float)(5f-Math.sqrt((i-4)*(i-4)))/5f;
            
            int cursorPlace = (cursors[0].y + i - 4) % NUM_SETTINGS;
            if(cursorPlace < 0) cursorPlace += NUM_SETTINGS;      
            
            if(i == 4) {
                g.setPaint(Color.white);
                g.setFont(new Font("Sans Serif", Font.BOLD, fontSizeLarge));
                g.fillRect(x2 - 80,y + i*yOffset - fontSizeSmall, 6,fontSizeSmall + 2);  
                g.fillRect(x2 + 80,y + i*yOffset - fontSizeSmall, 6,fontSizeSmall + 2); 
                g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.2f));
                g.fillRect(x2 - 80,y + i*yOffset - fontSizeSmall, 160,fontSizeSmall + 2); 
                g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
            } else {
                g.setColor(new Color(80,80,180));
                g.setFont(new Font("Sans Serif", Font.BOLD, fontSizeSmall));
                g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity));
            }
            g.drawString(gameMode.settings.getName(cursorPlace), x, y + i*yOffset);
            Tools.centreStringHor(gameMode.settings.getValueString(cursorPlace), g, x2, y + i*yOffset);

        }   
        
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
        renderLoad(g);
        
    }
    
    private void changeValue(int i) {
        
        if (!accept) {
            gameMode2 = gameMode.copy();
            accept = true;
        }
        gameMode.settings.changeValue(cursors[0].y,i);
    }
    
    // Saving these generalSettings to the default file.
    public void save() {
        gameMode.settings.save();
    }

    
    @Override
    public void select() {
        if (accept) {
            GamePanel.soundManager.menu(6);
            GamePanel.gameModeManager.gameMode = gameMode.copy();
            GamePanel.newGame();
            accept = false;
        }
    }
    
    @Override
    public void selectButton() {
        GamePanel.soundManager.menu(6);
        GamePanel.menuManager.changeMenu("LOAD");
        GamePanel.menuManager.loadMenu.setBack(0);
    }
    
    @Override
    public void right(int playerNumber) {
        if(playerNumber == 0) {
            GamePanel.soundManager.menu(8);
            changeValue(1);
        }
    }
    
    @Override
    public void left(int playerNumber) {
        if(playerNumber == 0) {
            GamePanel.soundManager.menu(8);
            changeValue(-1);
        }
    }
       
    @Override
    public void up(int playerNumber) {
        if(playerNumber == 0) {
            GamePanel.soundManager.menu(8);
            cursors[0].moveCursor(0,-1);
        }
    }
    
    @Override
    public void down(int playerNumber) {
        if(playerNumber == 0) {
            GamePanel.soundManager.menu(8);
            cursors[0].moveCursor(0,1);
        }
    }
    
    @Override
    public void rightTrigger() {
        GamePanel.soundManager.menu(8);
        changeValue(10);
    }
    
    @Override
    public void leftTrigger() {
        GamePanel.soundManager.menu(8);
        changeValue(-10);
    }

    @Override
    public void back() {
        GamePanel.soundManager.menu(7);
        if (accept) {
            accept = false;
            gameMode = gameMode2.copy();
        } else
        GamePanel.menuManager.changeMenu("START_MATCH_SETTINGS");
    }
}
