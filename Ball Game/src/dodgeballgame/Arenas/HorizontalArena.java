/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dodgeballgame.Arenas;

import dodgeballgame.GamePanel;
import dodgeballgame.HitBoxes.Hitbox;
import dodgeballgame.HitBoxes.*;
import dodgeballgame.Tools;
import dodgeballgame.Vec2;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

/**
 *
 * @author Sam
 */
public class HorizontalArena extends Arena{
    
    public HorizontalArena() {
        super();
        imgPath = "Images/Arenas/landscape.png";
        arenaName = "HORIZON";
    }
    
    @Override
    public Vec2[] getPlayerPositions(int numPlayers) {
        Vec2[] positions = new Vec2[numPlayers];
        
        if(numPlayers == 2) {
            positions[0] = new Vec2(WIDTH/2,HEIGHT/8);
            positions[1] = new Vec2(WIDTH/2, 7*HEIGHT/8);
        } else if (numPlayers == 3) {
            positions[0] = new Vec2(WIDTH/20,HEIGHT/8);
            positions[1] = new Vec2(19*WIDTH/20,HEIGHT/8);
            positions[2] = new Vec2(WIDTH/2, 7*HEIGHT/8);
        } else if (numPlayers == 4) {
            positions[0] = new Vec2(WIDTH/20,HEIGHT/8);
            positions[1] = new Vec2(19*WIDTH/20,HEIGHT/8);
            positions[2] = new Vec2(WIDTH/20,7*HEIGHT/8);
            positions[3] = new Vec2(19*WIDTH/20, 7*HEIGHT/8);
        }
        return positions;
    }
    
    @Override    
    public void initTeamAreas() {
        teamAreas = new Vec2[2][2];
        teamAreas[0] = new Vec2[]{new Vec2(0,0), new Vec2(WIDTH,HEIGHT/2)};
        teamAreas[1] = new Vec2[]{new Vec2(0,HEIGHT/2), new Vec2(WIDTH,HEIGHT)};
    }

    
    @Override 
    protected void renderScore(Graphics2D g) {
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
    
    @Override
    public void initHitBoxes() {
        // Team 1 middle Edge
        LineHitbox hbC1 = new LineHitbox (0,HEIGHT/2,Hitbox.DEG_180);
        arenaTeam1Hitbox.add(hbC1);
        
        // Team 2 middle Edge
        LineHitbox hbC2 = new LineHitbox (0,HEIGHT/2,Hitbox.DEG_0);
        arenaTeam2Hitbox.add(hbC2);
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
    protected void sideGoals(){
        RectHitbox goal1 = new RectHitbox(WIDTH/2,0,WIDTH/12, 15);
        arenaTeam2Goal.add(goal1);
        
        RectHitbox goal2 = new RectHitbox(WIDTH/2,HEIGHT,WIDTH/12, 15);
        arenaTeam1Goal.add(goal2);
    }
    
        
    @Override
    public Arena copy() {
        HorizontalArena temp = new HorizontalArena();
                temp.init();
        return temp;
    }
}
