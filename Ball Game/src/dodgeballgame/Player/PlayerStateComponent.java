/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dodgeballgame.Player;

import dodgeballgame.Timer;

/**
 *
 * @author Sam
 */
public class PlayerStateComponent implements PlayerComponent{
    
    Player p;
    
    
    
    public PlayerStateComponent(Player p) {
        this.p = p;
        slowed = false;
    }

    @Override
    public void init() {
    }

    @Override
    public void update(float d) {
        if(slowed)checkSlowed();
        if(largeCatch)checkLargeCatch();
    }
    
    //////Slowed//////////
    public boolean slowed;
    final double slowedLength = 5;
    final double slowSpeed = 200;
    public double origSpeed;
    Timer slowedTimer;
    private double slowedTime;
    
    public void slowed() {
        if(!slowed) origSpeed = p.physicsComp.speed;
        slowed = true;
        slowedTimer = new Timer();
        slowedTimer.refresh();
        slowedTime = slowedTimer.getDifference();
        p.physicsComp.speed=slowSpeed;
    }
    
    public void checkSlowed() {
        slowedTime = slowedTimer.getDifference();
        if (slowedTime>slowedLength) {
            slowed = !slowed;
            p.physicsComp.speed = origSpeed;
        }
    }
    
    ///// Large Catch ///////
    public boolean largeCatch;
    final double largeCatchLength = 5;
    final double largeRadius = 200;
    final double largeAngle = 6.0;
    public double origRadius;
    public double origAngle;
    Timer largeCatchTimer;
    private double largeCatchTime;
    
    public void largeCatch() {
        if(!largeCatch) {
            origRadius = p.radius;
            origAngle = p.catchAngle;
        }
        largeCatch = true;
        largeCatchTimer = new Timer();
        largeCatchTimer.refresh();
        largeCatchTime = largeCatchTimer.getDifference();
        p.radius = largeRadius;
        p.catchAngle  = largeAngle;
    }
    
    public void checkLargeCatch() {
        largeCatchTime = largeCatchTimer.getDifference();
        if (largeCatchTime>largeCatchLength) {
            largeCatch = !largeCatch;
            p.radius = origRadius;
            p.catchAngle  = origAngle;
        }
    }
}

