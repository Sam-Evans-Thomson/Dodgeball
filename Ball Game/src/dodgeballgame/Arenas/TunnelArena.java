/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dodgeballgame.Arenas;

import dodgeballgame.GamePanel;
import dodgeballgame.HitBox;
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
public class TunnelArena extends HorizontalArena{
    
    public TunnelArena() {
        super();
        imgPath = "Images/Arenas/landscape.png";
    }
    
    @Override
    public void initHitBoxes() {
        // Team 1 middle Edge
        HitBox hbC1 = new HitBox (0,HEIGHT/2);
        hbC1.makeHLine(true);
        arenaTeam1Hitbox.add(hbC1);
        
        // Team 2 middle Edge
        HitBox hbC2 = new HitBox (0,HEIGHT/2);
        hbC2.makeHLine(false);
        arenaTeam2Hitbox.add(hbC2);
        
        HitBox hbBallMiddle = new HitBox (WIDTH, HEIGHT/2);
        hbBallMiddle.makeRect(5*WIDTH/6,5);
        arenaBallHitbox.add(hbBallMiddle);
        
    }
    
    @Override
    protected void sideGoals(){
        HitBox goal1 = new HitBox(WIDTH,HEIGHT/4);
        goal1.makeRect(15, HEIGHT/4);
        arenaTeam2Goal.add(goal1);
        
        HitBox goal2 = new HitBox(WIDTH,3*HEIGHT/4);
        goal2.makeRect(15, HEIGHT/4);
        arenaTeam1Goal.add(goal2);
    }

}
