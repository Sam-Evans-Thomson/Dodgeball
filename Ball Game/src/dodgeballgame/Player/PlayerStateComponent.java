/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dodgeballgame.Player;

import dodgeballgame.Powers.*;
import dodgeballgame.StateComponent;
import dodgeballgame.Vec2;
import java.util.ArrayList;

/**
 *
 * @author Sam
 */
public class PlayerStateComponent implements PlayerComponent{
    
    Player p;
    public StateComponent largeCatchArea = new StateComponent(new double[]{200d,6.2d},5);
    public LargeCatchPower largeCatchPower = new LargeCatchPower(new Vec2(0,0));
    
    public StateComponent slowed = new StateComponent(new double[]{200d},10);
    public SlowPower slowPower = new SlowPower(new Vec2(0,0));
    
    public StateComponent noCatch = new StateComponent(new double[]{},10);
    public NoCatchPower noCatchPower = new NoCatchPower(new Vec2(0,0));
    
    public StateComponent noState = new StateComponent(new double[]{0},0);
    
    public ArrayList<StateComponent> activeStates = new ArrayList();
    
    public PlayerStateComponent(Player p) {
        this.p = p;
    }

    @Override
    public void update(float d) {
        if(slowed.hasExpired()) endSlowed(); 
        if(largeCatchArea.hasExpired()) endLargeCatchArea();
        if(noCatch.hasExpired()) endNoCatch();
    }
    
    public void largeCatchArea() {
        add(largeCatchPower, largeCatchArea);
        
        if(!largeCatchArea.active) largeCatchArea.setOrigValues(new double[]{p.radius, p.catchAngle});
        largeCatchArea.apply();
        
        p.radius = largeCatchArea.getValue(0);
        p.catchAngle  = largeCatchArea.getValue(1);
    }
    
    public void endLargeCatchArea() {
        remove(largeCatchPower, largeCatchArea);
        
        p.radius = largeCatchArea.getOrigValue(0);
        p.catchAngle  = largeCatchArea.getOrigValue(1);
        
    }

    public void slowed() {
        add(slowPower, slowed);
        
        if(!slowed.active) slowed.setOrigValues(new double[]{p.physicsComp.speed});
        slowed.apply();
        
        p.physicsComp.speed = slowed.getValue(0);
    }
    
    public void endSlowed() {
        remove(slowPower, slowed);
        
        p.physicsComp.speed = slowed.getOrigValue(0);
    }
    
    public void noCatch() {
        add(noCatchPower, noCatch);
        
        noCatch.apply();
        p.catchOn = false;
    }
    
    public void endNoCatch() {
        remove(noCatchPower, noCatch);
        
        p.catchOn = true;
    }
    
    // Helpers

    private void add(Power pow, StateComponent s) {
        p.activePowers.remove(pow);
        p.activePowers.add(pow);
        activeStates.remove(s);
        activeStates.add(s);
    }
    
    private void remove(Power pow, StateComponent s) {
        p.activePowers.remove(pow);
        activeStates.remove(s);
    }
    
    @Override
    public void init() {
    }
    
}

