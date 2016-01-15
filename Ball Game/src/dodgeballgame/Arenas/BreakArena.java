/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dodgeballgame.Arenas;

import dodgeballgame.Arenas.ArenaGraphicsComponents.BreakGC;
import dodgeballgame.Environment.BreakableBlock;
import dodgeballgame.HitBoxes.*;
import dodgeballgame.Vec2;
import java.util.ArrayList;

/**
 *
 * @author Sam
 */
public class BreakArena extends Arena{

        
    
    public BreakArena() {
        super();
        graphicsComp = new BreakGC(this);
        graphicsComp.imgPath = "Images/Arenas/vertical.png";
        arenaName = "BREAKOUT";
    }

    @Override
    public void initTeamAreas() {
        teamAreas = new Vec2[2][2];
        teamAreas[0] = new Vec2[]{new Vec2(WIDTH/6 + 40,0), new Vec2(WIDTH/2,HEIGHT)};
        teamAreas[1] = new Vec2[]{new Vec2(WIDTH/2,0), new Vec2(5*WIDTH/6 - 40,HEIGHT)};
    }
    
    @Override
    public void initHitBoxes() {
        // Team 1 middle Edge
        LineHitbox hbC1 = new LineHitbox (WIDTH/2,0, Hitbox.DEG_90);
        arenaTeam1Hitbox.add(hbC1);
        
        // Team 2 middle Edge
        LineHitbox hbC2 = new LineHitbox (WIDTH/2,0, Hitbox.DEG_270);
        arenaTeam2Hitbox.add(hbC2);
        
        initBreakableBlocks();
    }
    
    public void initBreakableBlocks() {
        breakBlocks = new ArrayList<>();
        double width = HEIGHT/20;
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 4; j++) {
                Vec2 vec = new Vec2(
                        WIDTH/2 - 7*width + 4*j*width + (i%2)*2*width, 
                        width + 2*i*width);
                RectHitbox2 hb = new RectHitbox2(vec,width, width);
                addBreakBlock(new BreakableBlock(hb));
            }
        };
        graphicsComp.setBreakBlocks(breakBlocks);
    }
    
        // GOALS
    @Override
    public void cornerGoals() {
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
    
    @Override
    public void specialGoals(){
        RectHitbox goal1 = new RectHitbox(WIDTH/6 + 25, HEIGHT/3 - 15, WIDTH/6 + 115, 2*HEIGHT/3 + 15,1);
        arenaTeam2Goal.add(goal1);
        
        RectHitbox goal2 = new RectHitbox(5*WIDTH/6 - 115, HEIGHT/3 - 15, 5*WIDTH/6 - 25, 2*HEIGHT/3 + 15,1);
        arenaTeam1Goal.add(goal2);
    }
    
    @Override
    public void sideGoals() {
        RectHitbox goal1 = new RectHitbox(WIDTH/6,HEIGHT/3+20, WIDTH/6 + 20, 2*HEIGHT/3 - 20,1);
        arenaTeam2Goal.add(goal1);
        
        RectHitbox goal2 = new RectHitbox(5*WIDTH/6 - 20,HEIGHT/3+20, 5*WIDTH/6, 2*HEIGHT/3 - 20,1);
        arenaTeam1Goal.add(goal2);
    }
    
    @Override
    public Arena copy() {
        BreakArena temp = new BreakArena();
                temp.init();
        return temp;
    }
    
}