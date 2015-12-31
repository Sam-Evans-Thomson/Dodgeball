/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dodgeballgame.Balls;

import dodgeballgame.GamePanel;
import dodgeballgame.Player.Player;
import dodgeballgame.Vec2;

/**
 *
 * @author Sam
 */
public class HomingBall extends Ball {
    
    public HomingBall(double speed, double x, double y, double angle, int team, int player) {
        super(speed,x,y,angle,team, player);
        
        init();
    }
    
    @Override
    public void updatePosition() {
        delta.set(angle, d*speed, 1);
        pos = pos.add(delta);
        pos = pos.add(findDirection());
        angle = prevPos.getAngle(pos);
        
        updateHitbox();
        checkHitboxes();
        
        
        
    }
    
    public Vec2 findDirection() {
        double dist = 100000.0;
        Vec2 vec = new Vec2(0,0);
        for (Player p : GamePanel.playerArray) {
            if(p.team != team) {
                if (p.pos.getMagnitude(pos) < dist) {
                dist = p.pos.getMagnitude(pos);
                vec = p.pos.take(pos);
                }
            }
        }
        double scalar = (2000-dist)/8000000;
        if (scalar < 0) scalar = 0;
        
        vec.scale(scalar);
        
        return vec;
    }
}
