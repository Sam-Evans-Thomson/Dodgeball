/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dodgeballgame.Arenas;

import dodgeballgame.Arenas.ArenaGraphicsComponents.ArenaGC;
import dodgeballgame.GamePanel;
import dodgeballgame.HitBoxes.Hitbox;
import dodgeballgame.HitBoxes.*;
import dodgeballgame.Vec2;
import java.awt.Graphics2D;
import java.util.ArrayList;

/**
 *
 * @author Sam
 */
public class Arena {
    
    public Vec2[][] teamAreas;
    
    public String arenaName;
    
    public ArrayList<Hitbox> arenaBallHitbox;
    public ArrayList<Hitbox> arenaSoftBallHitbox;
    public ArrayList<Hitbox> arenaPlayerHitbox;
    public ArrayList<Hitbox> arenaTeam1Hitbox;
    public ArrayList<Hitbox> arenaTeam2Hitbox;
    public ArrayList<Hitbox> arenaTeam1Goal;
    public ArrayList<Hitbox> arenaTeam2Goal;
    
    public ArrayList<Hitbox> renderHitboxes;
    
    public ArenaGC graphicsComp;
    
    public static int SIDE_GOALS = 0;
    public static int CORNER_GOALS = 1;
    
    public double softBounceFactor = 0.2;
    public double bounceFactor = 0.9;
    public int buffer = 1000;

    int WIDTH = (int)GamePanel.arenaWIDTH;
    int HEIGHT = (int)GamePanel.arenaHEIGHT;

        
    public Arena() {

    }
        
    public void init() {
        graphicsComp.init();
        arenaBallHitbox = new ArrayList<>();
        arenaSoftBallHitbox = new ArrayList<>();
        arenaPlayerHitbox = new ArrayList<>();
        arenaTeam1Hitbox = new ArrayList<>();
        arenaTeam2Hitbox = new ArrayList<>();
        arenaTeam1Goal = new ArrayList<>();
        arenaTeam2Goal = new ArrayList<>();
        renderHitboxes = new ArrayList();

        initBorders();
        if (GamePanel.arenaManager.goalsActive) {initGoals();}
        initHitBoxes();
        initTeamAreas();
    }
    
    public Vec2[] getPlayerPositions(int numPlayers) {
        Vec2[] positions = new Vec2[numPlayers];
        
        if(numPlayers == 2) {
            positions[0] = new Vec2(WIDTH/20,HEIGHT/2);
            positions[1] = new Vec2(19*WIDTH/20, HEIGHT/2);
        } else if (numPlayers == 3) {
            positions[0] = new Vec2(WIDTH/20,HEIGHT/8);
            positions[1] = new Vec2(WIDTH/20,7*HEIGHT/8);
            positions[2] = new Vec2(19*WIDTH/20, HEIGHT/2);
        } else if (numPlayers == 4) {
            positions[0] = new Vec2(WIDTH/20,HEIGHT/8);
            positions[1] = new Vec2(WIDTH/20,7*HEIGHT/8);
            positions[2] = new Vec2(19*WIDTH/20, HEIGHT/8);
            positions[3] = new Vec2(19*WIDTH/20, 7*HEIGHT/8);
        }
        return positions;
    }
    
    public void initTeamAreas() {
        teamAreas = new Vec2[2][2];
        teamAreas[0] = new Vec2[]{new Vec2(0,0), new Vec2(WIDTH/2,HEIGHT)};
        teamAreas[1] = new Vec2[]{new Vec2(WIDTH/2,0), new Vec2(WIDTH,HEIGHT)};
    }

    
    public void initBorders() {
        // Top Edge
        LineHitbox hbT = new LineHitbox (0,0,Hitbox.DEG_0);
        arenaBallHitbox.add(hbT);
        arenaPlayerHitbox.add(hbT);
        
        // Left Edge
        LineHitbox hbL = new LineHitbox (0,0,Hitbox.DEG_270);
        arenaSoftBallHitbox.add(hbL);
        arenaPlayerHitbox.add(hbL);
        
        // Right Edge
        LineHitbox hbR = new LineHitbox (WIDTH,0, Hitbox.DEG_90);
        arenaSoftBallHitbox.add(hbR);
        arenaPlayerHitbox.add(hbR);
        
        // Bottom Edge
        LineHitbox hbB = new LineHitbox (0,HEIGHT, Hitbox.DEG_180);
        arenaBallHitbox.add(hbB);
        arenaPlayerHitbox.add(hbB);
    }
    
    public void initGoals() {
        sideGoals();
    }

    public void initHitBoxes() {
        // Team 1 middle Edge
        LineHitbox hbC1 = new LineHitbox (WIDTH/2,0, Hitbox.DEG_90);
        arenaTeam1Hitbox.add(hbC1);
        
        // Team 2 middle Edge
        LineHitbox hbC2 = new LineHitbox (WIDTH/2,0, Hitbox.DEG_270);
        arenaTeam2Hitbox.add(hbC2);
    }
    
    public void render(Graphics2D g) {
        graphicsComp.render(g);
    }


    
    public Arena copy() {
        BasicArena temp = new BasicArena();
        temp.init();
        return temp;
    }
    
    public void setGoals(int i) {
        arenaTeam1Goal = new ArrayList<>();
        arenaTeam2Goal = new ArrayList<>();
        GamePanel.arenaManager.goalsActive = true;
        switch (i) {
            case 0 : cornerGoals();
                break;
            case 1 : sideGoals();
                break;
        }
    }
    
    // GOALS
    protected void cornerGoals() {
        int goalSize = 50;
        CircleHitbox goal1 = new CircleHitbox(0,0, goalSize);
        arenaTeam2Goal.add(goal1);
        
        CircleHitbox goal2 = new CircleHitbox(0,HEIGHT, goalSize);
        arenaTeam2Goal.add(goal2);
        
        CircleHitbox goal3 = new CircleHitbox(WIDTH,0, goalSize);
        arenaTeam1Goal.add(goal3);
        
        CircleHitbox goal4 = new CircleHitbox(WIDTH,HEIGHT, goalSize);
        arenaTeam1Goal.add(goal4);
    }
    
    protected void sideGoals(){
        RectHitbox goal1 = new RectHitbox(0,HEIGHT/2, 15, HEIGHT/12);
        arenaTeam2Goal.add(goal1);
        
        RectHitbox goal2 = new RectHitbox(WIDTH,HEIGHT/2,15, HEIGHT/12);
        arenaTeam1Goal.add(goal2);
    }
}
