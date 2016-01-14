/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dodgeballgame.Arenas.ArenaGraphicsComponents;

import dodgeballgame.Arenas.Arena;
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
public class HorizontalGC extends ArenaGC{

    public HorizontalGC(Arena arena) {
        super(arena);
    }
    
    @Override
    public void init() {
        super.init();
    }
        
    @Override 
    public void renderScore(Graphics2D g) {
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.1f));
        g.setColor(new Color(250,250,250));
        g.setFont(new Font("Sans Serif", Font.BOLD, 500));
        Tools.centreStringHor("" + GamePanel.team1Score, g, WIDTH/2, HEIGHT/2 - HEIGHT/16);
        Tools.centreStringHor("" + GamePanel.team2Score, g, WIDTH/2, HEIGHT - HEIGHT/30);
        g.setFont(new Font("Sans Serif", Font.BOLD, 200));
        Tools.centreStringHor("" + (int)GamePanel.winScore, g,WIDTH - 130,HEIGHT/2 - 10);
        Tools.centreStringHor("" + (int)GamePanel.winScore, g,WIDTH - 130,HEIGHT/2 + 150);
        g.fillRect(17*WIDTH/20, HEIGHT/2 - HEIGHT/6, WIDTH/60, HEIGHT/3);
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
    }
}
