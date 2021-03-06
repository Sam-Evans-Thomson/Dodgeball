/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dodgeballgame.Balls;

import dodgeballgame.GamePanel;
import dodgeballgame.Loading;
import dodgeballgame.Player.Player;
import dodgeballgame.Tools;

/**
 *
 * @author Sam
 */
public class HomingBall extends Ball {
    
    double turningAngle = 2*Math.PI/3;
    static Player closestPlayer = Loading.closestPlayer;
    
    public HomingBall(double speed, double x, double y, double angle, int team, Player player) {
        super(speed,x,y,angle,team, player);
        
        init();
    }
    
    @Override
    public void updatePosition() {
        angle = findAngle();
        delta.set(angle, d*speed, 1);
        pos = pos.add(delta);
        
        updateHitbox();
        checkHitboxes();
    }
    
    public double findAngle() {
        for (Player p : GamePanel.playerArray) {
            if(p.team != team && p.pos.getMagnitude(pos) < closestPlayer.pos.getMagnitude(pos)) {
                closestPlayer = p;
            }
        }
        
        double playerAngle = pos.getAngle(closestPlayer.pos);

        double workingAngle = Tools.refreshAngle(playerAngle - angle);
        if(workingAngle > Math.PI) workingAngle -= 2*Math.PI;

        if (workingAngle > 0) return angle + turningAngle*d;
        else return angle-turningAngle*d;
    }
}
