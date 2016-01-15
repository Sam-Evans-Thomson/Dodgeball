/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dodgeballgame.Arenas;

import dodgeballgame.GamePanel;
import dodgeballgame.Loading;
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
    public TunnelArena tunnelArena;
    public BarracksArena barracksArena;
    public HorizontalArena horArena;
    public MovingPanelArena movingPanelArena;
    public BreakArena breakArena;
    public TeleArena teleArena;
    
    public static boolean goalsActive;
    
    public static final int NUM_GOAL_POS = 3;
    public static final int NUM_ARENAS = 7;
    
    public static ArrayList<Arena> arenas;
    public static Arena arena;
    
    public ArenaManager() {

    }
    
    public void init() {
        arenas = new ArrayList();
        horArena = new HorizontalArena();
        basicArena = new BasicArena();
        tunnelArena = new TunnelArena();
        barracksArena = new BarracksArena();
        movingPanelArena = new MovingPanelArena();
        breakArena = new BreakArena();
        teleArena = new TeleArena();
        
        basicArena.init();
        horArena.init();
        tunnelArena.init();
        barracksArena.init();
        movingPanelArena.init();
        breakArena.init();
        teleArena.init();
        
        arenas.add(basicArena);
        arenas.add(horArena);
        arenas.add(tunnelArena);
        arenas.add(barracksArena);
        arenas.add(movingPanelArena);
        arenas.add(breakArena);
        arenas.add(teleArena);
        
        Loading.arenaDisplays(arenas, NUM_ARENAS, NUM_GOAL_POS);
        goalsActive = true;
    }
    
    public void update(double d) {
        arena.update(d);
    }
    
    public void updateArenaDisplay() {
        
    }
    
    public BufferedImage getArenaDisplay(int i, int goals) {
        if(!goalsActive) return Loading.arenaDisplays.get(i)[NUM_GOAL_POS];
        else return Loading.arenaDisplays.get(i)[goals];
    }
    
    public String getName(int i) {
        return arenas.get(i).arenaName;
    }
        
    public void setGoals(int i) {
        for (Arena ar : arenas) ar.setGoals(i);
    }
    
    public void setArena(int i) {
        arena = arenas.get(i);
        GamePanel.newGame();
    }
}
