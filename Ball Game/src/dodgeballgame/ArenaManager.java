/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dodgeballgame;

import dodgeballgame.Arenas.*;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import javax.swing.JPanel;

/**
 *
 * @author Sam
 */
public class ArenaManager extends JPanel{
    public BasicArena basicArena; 
    public HorizontalArena horArena;
    public TunnelArena tunnelArena;
    
    public static ArrayList<Arena> arenas;
    
    public ArenaManager() {

    }
    
    public void init() {
        arenas = new ArrayList();
        basicArena = new BasicArena();
        horArena = new HorizontalArena();
        tunnelArena = new TunnelArena();
        
        basicArena.init();
        horArena.init();
        tunnelArena.init();
        
        arenas.add(basicArena);
        arenas.add(horArena);
        arenas.add(tunnelArena);
    }
    
    public BufferedImage getArenaDisplay(int i) {
        BufferedImage image = new BufferedImage(GamePanel.arenaWIDTH, 
                GamePanel.arenaHEIGHT, 
                BufferedImage.TYPE_INT_RGB);
        Graphics2D g = image.createGraphics();
        arenas.get(i).render(g);
        return image;
    }
    
    public void setArena(int i) {
        GamePanel.arena = arenas.get(i);
        GamePanel.newGame();
    }
}
