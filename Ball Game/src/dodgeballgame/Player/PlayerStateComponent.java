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
    public StateComponent largeCatch = new StateComponent(new double[]{200d,6.2d},5);
    public LargeCatchPower largeCatchPower = new LargeCatchPower(new Vec2(0,0));
    
    public StateComponent slowed = new StateComponent(new double[]{200d},5);
    public SlowPower slowPower = new SlowPower(new Vec2(0,0));
    
    public StateComponent noCatch = new StateComponent(new double[]{},1);
    public NoCatchPower noCatchPower = new NoCatchPower(new Vec2(0,0));
    
    public StateComponent autoCatch = new StateComponent(new double[]{},10);
    public AutoCatchPower autoCatchPower = new AutoCatchPower(new Vec2(0,0));
    
    public StateComponent invincible = new StateComponent(new double[]{},10);
    public InvinciblePower invinciblePower = new InvinciblePower(new Vec2(0,0));
    
    public StateComponent aimBot = new StateComponent(new double[]{},5);
    public AimBotPower aimBotPower = new AimBotPower(new Vec2(0,0));
    
    public StateComponent throwBoost = new StateComponent(new double[]{},0.2);
    
    
    public StateComponent noState = new StateComponent(new double[]{0},0);
    
    public ArrayList<StateComponent> activeStates = new ArrayList();
    
    public PlayerStateComponent(Player p) {
        this.p = p;
    }

    @Override
    public void update(float d) {
        if(slowed.hasExpired()) endSlowed(); 
        if(largeCatch.hasExpired()) endLargeCatchArea();
        if(noCatch.hasExpired()) endNoCatch();
        if(autoCatch.hasExpired()) endAutoCatch();
        if(invincible.hasExpired()) endInvincible();
        if(aimBot.hasExpired()) endAimBot();
        if(throwBoost.hasExpired()) endThrowBoost();
    }
    
    public void largeCatchArea() {
        add(largeCatchPower, largeCatch);
        
        if(!largeCatch.active) largeCatch.setOrigValues(new double[]{p.radius, p.catchAngle});
        largeCatch.apply();
        
        p.radius = largeCatch.getValue(0);
        p.catchAngle  = largeCatch.getValue(1);
    }
    
    public void endLargeCatchArea() {
        remove(largeCatchPower, largeCatch);
        
        p.radius = largeCatch.getOrigValue(0);
        p.catchAngle  = largeCatch.getOrigValue(1);
        
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
      
    public void autoCatch() {
        add(autoCatchPower, autoCatch);
        
        autoCatch.apply();
        p.autoCatchOn = true;
    }
    
    public void endAutoCatch() {
        remove(autoCatchPower, autoCatch);
        
        p.autoCatchOn = false;
    }
    
    public void invincible() {
        add(invinciblePower, invincible);
        
        invincible.apply();
        p.invincible = true;
    }
    
    public void endInvincible() {
        remove(invinciblePower, invincible);
        
        p.invincible = false;
    }
    
        
    public void aimBot() {
        add(aimBotPower, aimBot);
        
        aimBot.apply();
        p.nextBall = "HOMING";
    }
    
    public void endAimBot() {
        remove(aimBotPower, aimBot);
        
        p.nextBall = "NORMAL";
    }
    
    public void throwBoost() {
        throwBoost.apply();
        p.throwBoost = true;
    }
    
    public void endThrowBoost() {
        p.throwBoost = false;
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

