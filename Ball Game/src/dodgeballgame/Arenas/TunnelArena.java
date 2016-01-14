/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dodgeballgame.Arenas;

import dodgeballgame.Arenas.ArenaGraphicsComponents.TunnelGC;
import dodgeballgame.HitBoxes.Hitbox;
import dodgeballgame.HitBoxes.*;
import dodgeballgame.Vec2;

/**
 *
 * @author Sam
 */
public class TunnelArena extends HorizontalArena{
    
    public TunnelArena() {
        super();
        graphicsComp = new TunnelGC(this);
        graphicsComp.imgPath = "Images/Arenas/landscape.png";
        arenaName = "TUNNEL";
    }
    
    @Override
    public void initHitBoxes() {
        // Team 1 middle Edge
        LineHitbox hbC1 = new LineHitbox (0,HEIGHT/2,Hitbox.DEG_180);
        arenaTeam1Hitbox.add(hbC1);
        
        // Team 2 middle Edge
        LineHitbox hbC2 = new LineHitbox (0,HEIGHT/2,Hitbox.DEG_0);
        arenaTeam2Hitbox.add(hbC2);
        
        RectHitbox hbBallMiddle = new RectHitbox (WIDTH, HEIGHT/2,5*WIDTH/6,40);
        arenaBallHitbox.add(hbBallMiddle);
        renderHitbox.add(hbBallMiddle);
        arenaPlayerHitbox.add(hbBallMiddle);
        
        CircleHitbox roundEnd = new CircleHitbox(WIDTH/6, HEIGHT/2, 40);
        arenaBallHitbox.add(roundEnd);
        renderHitbox.add(roundEnd);
        
        LineHitbox thb = new LineHitbox(0,HEIGHT/3,-Math.PI/4);
        arenaBallHitbox.add(thb);
        arenaTeam1Hitbox.add(thb);
        
        LineHitbox bhb = new LineHitbox(0,2*HEIGHT/3, 5*Math.PI/4);
        arenaBallHitbox.add(bhb);
        arenaTeam2Hitbox.add(bhb);
        
        LineHitbox playerVert = new LineHitbox(HEIGHT/2,0,Hitbox.DEG_270);
        arenaPlayerHitbox.add(playerVert);
        renderHitbox.add(playerVert);
    }
    
        
    @Override
    public Vec2[] getPlayerPositions(int numPlayers) {
        Vec2[] positions = new Vec2[numPlayers];
        
        if(numPlayers == 2) {
            positions[0] = new Vec2(WIDTH/2,HEIGHT/8);
            positions[1] = new Vec2(WIDTH/2, 7*HEIGHT/8);
        } else if (numPlayers == 3) {
            positions[0] = new Vec2(8*WIDTH/20,HEIGHT/8);
            positions[1] = new Vec2(19*WIDTH/20,HEIGHT/8);
            positions[2] = new Vec2(WIDTH/2, 7*HEIGHT/8);
        } else if (numPlayers == 4) {
            positions[0] = new Vec2(5*WIDTH/20,HEIGHT/8);
            positions[1] = new Vec2(19*WIDTH/20,HEIGHT/8);
            positions[2] = new Vec2(5*WIDTH/20,7*HEIGHT/8);
            positions[3] = new Vec2(19*WIDTH/20, 7*HEIGHT/8);
        }
        return positions;
    }
    
    @Override
    public void initTeamAreas() {
        teamAreas = new Vec2[2][2];
        teamAreas[0] = new Vec2[]{new Vec2(HEIGHT/2,0), new Vec2(WIDTH,HEIGHT/2)};
        teamAreas[1] = new Vec2[]{new Vec2(HEIGHT/2,HEIGHT/2), new Vec2(WIDTH,HEIGHT)};
    }

    
    @Override
    public void sideGoals(){
        RectHitbox goal1 = new RectHitbox(WIDTH,HEIGHT/4,15, HEIGHT/4);
        arenaTeam2Goal.add(goal1);
        
        RectHitbox goal2 = new RectHitbox(WIDTH,3*HEIGHT/4,15, HEIGHT/4);
        arenaTeam1Goal.add(goal2);
    }
    
    @Override
    public void cornerGoals() {
        int goalSize = 50;
        CircleHitbox goal1 = new CircleHitbox(0,0, goalSize);
        arenaTeam2Goal.add(goal1);
        
        CircleHitbox goal2 = new CircleHitbox(0,HEIGHT, goalSize);
        arenaTeam1Goal.add(goal2);
        
        CircleHitbox goal3 = new CircleHitbox(WIDTH,0, goalSize);
        arenaTeam2Goal.add(goal3);
        
        CircleHitbox goal4 = new CircleHitbox(WIDTH,HEIGHT, goalSize);
        arenaTeam1Goal.add(goal4);
    }
    
        
    @Override
    public Arena copy() {
        TunnelArena temp = new TunnelArena();
                temp.init();
        return temp;
    }

}
