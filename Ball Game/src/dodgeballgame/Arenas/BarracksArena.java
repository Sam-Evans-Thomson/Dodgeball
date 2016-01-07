/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dodgeballgame.Arenas;

import dodgeballgame.GamePanel;
import dodgeballgame.HitBoxes.*;
import dodgeballgame.Vec2;
import java.awt.Color;
import java.awt.Graphics2D;

/**
 *
 * @author Sam
 */
public class BarracksArena extends Arena{

        
    public BarracksArena() {
        super();
        imgPath = "Images/Arenas/vertical.png";
        arenaName = "BARRACKS";
    }
    
    @Override    
    public void renderSpecific(Graphics2D g) {
        g.setColor(Color.WHITE);
        for(Hitbox hb : renderHitboxes) hb.render(g);
    }
    
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
        
        // Team1 Goal protectors
        RectHitbox main1 = new RectHitbox(WIDTH/6 + 20, HEIGHT/3, WIDTH/6 + 120, 2*HEIGHT/3,1);
        arenaBallHitbox.add(main1);
        renderHitboxes.add(main1);
        arenaTeam1Hitbox.add(main1);
        
        // Team2 Goal protectors
        RectHitbox main2 = new RectHitbox(5*WIDTH/6 - 120, HEIGHT/3, 5*WIDTH/6 - 20, 2*HEIGHT/3,1);
        arenaBallHitbox.add(main2);
        renderHitboxes.add(main2);
        arenaTeam2Hitbox.add(main2);
        
    }
    
        // GOALS
    @Override
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
    
    @Override
    protected void sideGoals(){
        RectHitbox goal1 = new RectHitbox(WIDTH/6,HEIGHT/3+20, WIDTH/6 + 20, 2*HEIGHT/3 - 20,1);
        arenaTeam2Goal.add(goal1);
        
        RectHitbox goal2 = new RectHitbox(5*WIDTH/6 - 20,HEIGHT/3+20, 5*WIDTH/6, 2*HEIGHT/3 - 20,1);
        arenaTeam1Goal.add(goal2);
    }
    
    @Override
    public Arena copy() {
        BarracksArena temp = new BarracksArena();
                temp.init();
        return temp;
    }
    
}