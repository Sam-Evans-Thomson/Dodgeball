/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dodgeballgame.Player;

import dodgeballgame.StateComponent;
import dodgeballgame.Timer;

/**
 *
 * @author Sam
 */
public class PlayerStateComponent implements PlayerComponent{
    
    Player p;

    public PlayerStateComponent(Player p) {
        this.p = p;
        init();
    }

    @Override
    public void init() {
        largeCatchArea = new StateComponent(new double[]{radius,angle},lgcTime);
        slowed = new StateComponent(new double[]{slowSpeed}, slowTime);
    }

    @Override
    public void update(float d) {
        if(slowed.hasExpired()) endSlowed(); 
        if(largeCatchArea.hasExpired()) endLargeCatchArea();
    }
    
    
    public StateComponent largeCatchArea;
    public double radius = 200d;
    public double angle = 6.2d;
    public double lgcTime = 5;
    
    public void largeCatchArea() {
        if(!largeCatchArea.active) largeCatchArea.setOrigValues(new double[]{p.radius, p.catchAngle});
        largeCatchArea.apply();
        p.radius = largeCatchArea.getValue(0);
        p.catchAngle  = largeCatchArea.getValue(1);
    }
    
    public void endLargeCatchArea() {
        p.radius = largeCatchArea.getOrigValue(0);
        p.catchAngle  = largeCatchArea.getOrigValue(1);
    }
    
    
    public StateComponent slowed;
    public double slowSpeed = 200;
    public double slowTime = 5;
    
    public void slowed() {
        if(!slowed.active) slowed.setOrigValues(new double[]{p.physicsComp.speed});
        slowed.apply();
        p.physicsComp.speed = slowed.getValue(0);
    }
    
    public void endSlowed() {
        p.physicsComp.speed = slowed.getOrigValue(0);
    }
    
}

