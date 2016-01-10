/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dodgeballgame.Arenas;

import dodgeballgame.Arenas.*;
import dodgeballgame.GamePanel;
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
    public BarracksArena barracksArena;
    
    public boolean goalsActive;
    
    public static final int NUM_GOAL_POS = 2;
    public static final int NUM_ARENAS = 4;
    
    public static ArrayList<Arena> arenas;
    
    public ArenaManager() {

    }
    
    public void init() {
        arenas = new ArrayList();
        basicArena = new BasicArena();
        horArena = new HorizontalArena();
        tunnelArena = new TunnelArena();
        barracksArena = new BarracksArena();
        
        basicArena.init();
        horArena.init();
        tunnelArena.init();
        barracksArena.init();
        
        arenas.add(basicArena);
        arenas.add(horArena);
        arenas.add(tunnelArena);
        arenas.add(barracksArena);
    }
    
    public BufferedImage getArenaDisplay(int i, int goals) {
        BufferedImage image = new BufferedImage(GamePanel.arenaWIDTH, 
                GamePanel.arenaHEIGHT, 
                BufferedImage.TYPE_INT_RGB);
        Graphics2D g = image.createGraphics();
        
        Arena temp = arenas.get(i).copy();
        if(goalsActive) temp.setGoals(goals);
        temp.render(g);
        
        return image;
    }
    
    public String getName(int i) {
        return arenas.get(i).arenaName;
    }
        
    public void setGoals(int i) {
        for (Arena ar : arenas) ar.setGoals(i);
    }
    
    public void setArena(int i) {
        GamePanel.arena = arenas.get(i);
        GamePanel.newGame();
    }
}
