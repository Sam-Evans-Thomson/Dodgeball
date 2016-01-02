/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dodgeballgame.Player;

import dodgeballgame.Powers.*;
import dodgeballgame.StateComponent;
import dodgeballgame.Vec2;

/**
 *
 * @author Sam
 */
public class PlayerStateComponent implements PlayerComponent{
    
    Player p;
    public StateComponent largeCatchArea = new StateComponent(new double[]{200d,6.2d},5);
    public StateComponent slowed = new StateComponent(new double[]{200d},5);
    public StateComponent noState = new StateComponent(new double[]{0},0);
    
    public StateComponent activeState;
    
    public PlayerStateComponent(Player p) {
        this.p = p;
        activeState = noState;
    }

    @Override
    public void update(float d) {
        if(slowed.hasExpired()) endSlowed(); 
        if(largeCatchArea.hasExpired()) endLargeCatchArea();
    }
    
    public void largeCatchArea() {
        if(!largeCatchArea.active) largeCatchArea.setOrigValues(new double[]{p.radius, p.catchAngle});
        p.activePower = new LargeCatchPower(new Vec2(0,0));
        
        largeCatchArea.apply();
        activeState = largeCatchArea;
        p.radius = largeCatchArea.getValue(0);
        p.catchAngle  = largeCatchArea.getValue(1);
    }
    
    public void endLargeCatchArea() {
        p.radius = largeCatchArea.getOrigValue(0);
        p.catchAngle  = largeCatchArea.getOrigValue(1);
        
        p.activePower = p.noPower;
        activeState = noState;
    }

    public void slowed() {
        if(!slowed.active) slowed.setOrigValues(new double[]{p.physicsComp.speed});
        p.activePower = new SlowPower(new Vec2(0,0));
        
        slowed.apply();
        activeState = slowed;
        p.physicsComp.speed = slowed.getValue(0);
    }
    
    public void endSlowed() {
        p.physicsComp.speed = slowed.getOrigValue(0);
        
        p.activePower = p.noPower;
        activeState = noState;
    }

    @Override
    public void init() {
    }
    
}

