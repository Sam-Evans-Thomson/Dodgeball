/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dodgeballgame.Arenas;

import dodgeballgame.HitBoxes.Hitbox;
import dodgeballgame.HitBoxes.*;

/**
 *
 * @author Sam
 */
public class TunnelArena extends HorizontalArena{
    
    public TunnelArena() {
        super();
        imgPath = "Images/Arenas/landscape.png";
    }
    
    @Override
    public void initHitBoxes() {
        // Team 1 middle Edge
        LineHitbox hbC1 = new LineHitbox (0,HEIGHT/2,Hitbox.DEG_180);
        arenaTeam1Hitbox.add(hbC1);
        
        // Team 2 middle Edge
        LineHitbox hbC2 = new LineHitbox (0,HEIGHT/2,Hitbox.DEG_0);
        arenaTeam2Hitbox.add(hbC2);
        
        RectHitbox hbBallMiddle = new RectHitbox (WIDTH, HEIGHT/2,5*WIDTH/6,10);
        arenaBallHitbox.add(hbBallMiddle);
        
        LineHitbox thb = new LineHitbox(0,HEIGHT/3,-Math.PI/4);
        arenaBallHitbox.add(thb);
        arenaTeam1Hitbox.add(thb);
        
        LineHitbox bhb = new LineHitbox(0,2*HEIGHT/3, 5*Math.PI/4);
        arenaBallHitbox.add(bhb);
        arenaTeam2Hitbox.add(bhb);
    }
    
    @Override
    protected void sideGoals(){
        RectHitbox goal1 = new RectHitbox(WIDTH,HEIGHT/4,15, HEIGHT/4);
        arenaTeam2Goal.add(goal1);
        
        RectHitbox goal2 = new RectHitbox(WIDTH,3*HEIGHT/4,15, HEIGHT/4);
        arenaTeam1Goal.add(goal2);
    }

}
