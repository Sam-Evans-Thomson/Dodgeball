/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dodgeballgame.Arenas;

import dodgeballgame.HitBoxes.Hitbox;
import dodgeballgame.HitBoxes.*;
import dodgeballgame.Vec2;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;

/**
 *
 * @author Sam
 */
public class TunnelArena extends HorizontalArena{
    
    public TunnelArena() {
        super();
        imgPath = "Images/Arenas/landscape.png";
        arenaName = "TUNNEL";
    }

    
    @Override
    protected void renderHitboxes(Graphics2D g) {
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
        g.setColor(Color.WHITE);
        for (Hitbox hb : renderHitboxes) hb.render(g);
    }
        
    @Override
    public void renderSpecific(Graphics2D g) {
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
        g.setColor(Color.WHITE);
        int[] xPoints = new int[]{0,0,HEIGHT/3};
        int[] yPoints = new int[]{0,HEIGHT/3,0};
        g.fillPolygon(xPoints, yPoints, 3);
        xPoints = new int[]{0,0,HEIGHT/3};
        yPoints = new int[]{HEIGHT,2*HEIGHT/3,HEIGHT};
        g.fillPolygon(xPoints, yPoints, 3);
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f));
        g.fillRect(0,0, HEIGHT/2, HEIGHT);
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
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
        renderHitboxes.add(hbBallMiddle);
        
        LineHitbox thb = new LineHitbox(0,HEIGHT/3,-Math.PI/4);
        arenaBallHitbox.add(thb);
        arenaTeam1Hitbox.add(thb);
        
        LineHitbox bhb = new LineHitbox(0,2*HEIGHT/3, 5*Math.PI/4);
        arenaBallHitbox.add(bhb);
        arenaTeam2Hitbox.add(bhb);
        
        LineHitbox playerVert = new LineHitbox(HEIGHT/2,0,Hitbox.DEG_270);
        arenaPlayerHitbox.add(playerVert);
        renderHitboxes.add(playerVert);
    }
    
    @Override
    public void initTeamAreas() {
        teamAreas = new Vec2[2][2];
        teamAreas[0] = new Vec2[]{new Vec2(HEIGHT/2,0), new Vec2(WIDTH,HEIGHT/2)};
        teamAreas[1] = new Vec2[]{new Vec2(HEIGHT/2,HEIGHT/2), new Vec2(WIDTH,HEIGHT)};
    }

    
    @Override
    protected void sideGoals(){
        RectHitbox goal1 = new RectHitbox(WIDTH,HEIGHT/4,15, HEIGHT/4);
        arenaTeam2Goal.add(goal1);
        
        RectHitbox goal2 = new RectHitbox(WIDTH,3*HEIGHT/4,15, HEIGHT/4);
        arenaTeam1Goal.add(goal2);
    }
    
    @Override
    protected void cornerGoals() {
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
