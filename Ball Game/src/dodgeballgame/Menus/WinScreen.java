/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dodgeballgame.Menus;

import dodgeballgame.GamePanel;
import dodgeballgame.Menus.Menu;
import dodgeballgame.Player.Player;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

/**
 *
 * @author Sam
 */
public class WinScreen extends Menu{
    
    public int winTeam;
    
    public int fontSizeLarge, fontSizeSmall, fontSizeTiny;
    
    public WinScreen () {
        fontSizeLarge = WIDTH/6;
        fontSizeSmall = fontSizeLarge*3/5;
        fontSizeTiny = fontSizeSmall/4;
    }
    
    public void findWinner() {
        if(GamePanel.team1Score > GamePanel.team2Score ) {
            winTeam = 0;
        } else {
            winTeam = 1;
        }
    }
    
    @Override
    public void renderMenu(Graphics2D g) {
        findWinner();
        
        g.setColor(new Color(250,250,250));
        g.setFont(new Font("Sans Serif", Font.BOLD, fontSizeLarge));
        
        if (winTeam == 0) {
            centreString("" + GamePanel.winScore, g, WIDTH/4+INNER_X_START,HEIGHT/3+INNER_Y_START);
            g.setColor(new Color(200,0,0));
            g.setFont(new Font("Sans Serif", Font.BOLD, fontSizeSmall));
            centreString("" + GamePanel.team2Score, 
                    g, 
                    3*GamePanel.arenaWIDTH/4-50,
                    GamePanel.arenaHEIGHT/2+100);
            g.setFont(new Font("Sans Serif", Font.BOLD, fontSizeTiny));
            g.setColor(Color.white);
            centreString("WINNERS", g, GamePanel.arenaWIDTH/4 +50,260);
            centreString("LOSERS", g, 3*GamePanel.arenaWIDTH/4 - 50,260);
        } else {
            centreString("" + GamePanel.winScore, 
                    g, 
                    3*GamePanel.arenaWIDTH/4-50,
                    GamePanel.arenaHEIGHT/2+190);
            g.setColor(new Color(200,0,0));
            g.setFont(new Font("Sans Serif", Font.BOLD, fontSizeSmall));
            centreString("" + GamePanel.team1Score, g, GamePanel.arenaWIDTH/4+50,GamePanel.arenaHEIGHT/2+100);
            g.setFont(new Font("Sans Serif", Font.BOLD, fontSizeTiny));
            g.setColor(Color.white);
            centreString("WINNERS", g, 3*GamePanel.arenaWIDTH/4 - 50,260);
            centreString("LOSERS", g, GamePanel.arenaWIDTH/4 + 50,260);
        }
        
        
        
        for (int i = 0; i<GamePanel.playerArray.size(); i++) {
            Player p = GamePanel.playerArray.get(i);
            BufferedImage image;
            if(p.team == winTeam) {
                image = p.graphicsComp.playerImageA;  
            } else {
                image = p.graphicsComp.playerImageB;
            }
            g.drawImage(image, (int)GamePanel.arenaWIDTH/4 - 100 + 200*(p.pNumber) + 400*p.team, 700, null);
            
        }
    }
    
    private void centreString(String s, Graphics2D g, int x, int y) {
        int stringLen = (int)
            g.getFontMetrics().getStringBounds(s, g).getWidth();
        g.drawString(s, x - stringLen/2, y);
    }
    
    @Override
    public void select() {
        back();
    }
    
    @Override
    public void back() {
        GamePanel.newGame();
        GamePanel.menu = GamePanel.startMenu;
    }
}
