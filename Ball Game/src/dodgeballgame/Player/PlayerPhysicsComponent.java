/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dodgeballgame.Player;

import dodgeballgame.Balls.Ball;
import dodgeballgame.BounceCalculator;
import dodgeballgame.GamePanel;
import dodgeballgame.HitBox;
import dodgeballgame.Items.Item;
import dodgeballgame.Tools;
import dodgeballgame.Vec2;
import java.util.ArrayList;

/**
 *
 * @author Sam
 */
public class PlayerPhysicsComponent implements PlayerComponent{
    
    Player p;
    
    // MOVEMENT
    public Vec2 prevPos;
    public Vec2 prevDelta;
    
    public double speed;
    public double frameSpeed;
    public double baseSpeed;
    public double maxSpeed;
                 
    public float d;                     // Time delta
    
    // HITBOXES
    public HitBox playerHitbox;
    public HitBox catchHitbox;
    private BounceCalculator bCalc;
    
    //THROWING & CATCHING
    public double throwSpeed;           //throw speed when standing still.
    private double relThrowSpeed;       //throw speed with movement taken into account
    
    public int numBallsInArea;
    public boolean inCatchArea;

    public PlayerPhysicsComponent(Player p) {
        this.p = p;
    }
     
    @Override
    public void init() {
        p.solid = true;
        p.catchOn = true;
        
        prevPos = new Vec2(p.pos.getX(),p.pos.getY());
        prevDelta = new Vec2(0,0);
        
        // Initialize hitboxes
        playerHitbox = new HitBox(p.pos.getX(),p.pos.getY());
        playerHitbox.makeCircle(p.H/2);
        catchHitbox = new HitBox(p.pos.getX(),p.pos.getY());
        catchHitbox.makeArc(p.catchAngle, p.angle, p.radius);
        
        baseSpeed = speed = 500.0;
        maxSpeed = 800;
        throwSpeed = 500.0;
        p.angle = 0;
        numBallsInArea = 0;
        inCatchArea = false;
    }
    
    // d is the time Delta
    @Override
    public void update(float d) {
       if (!p.solid) {
            double timeDelta = p.hbTimer.getDifference();
            if (timeDelta > p.invincibleTime) {
                p.solid = true;
            }
        }
        
        this.d = d;

        updateMovementValues(d);
        
        updatePosition(p.delta);
        resolveMove(p.delta); 
        prevPos.set(p.pos);
        
        updateThrowValues();
        
    }
    
    private void updateMovementValues(float d) {
        frameSpeed = d*speed;
        prevDelta.set(p.delta);
        p.delta = p.DELTA.multiply(frameSpeed);
    }
    
    private void updatePosition(Vec2 vec) { 
        p.pos = p.pos.add(vec);
        updateHitbox(); 
    }

    private void updateHitbox() {
        playerHitbox.moveTo(p.pos.getX(),p.pos.getY());
        catchHitbox.moveTo(p.pos.getX(),p.pos.getY());
        catchHitbox.setArcAngle(p.catchAngle);
        catchHitbox.setRadius(p.radius);
    }
    
    private void resolveMove(Vec2 vec) {
        resolveCollisions(vec, GamePanel.arena.arenaPlayerHitbox);
        if(p.team == 0)         resolveCollisions(vec, GamePanel.arena.arenaTeam1Hitbox);
        else if(p.team == 1)    resolveCollisions(vec, GamePanel.arena.arenaTeam2Hitbox);
        resolvePlayerCollisions(vec);
        p.distanceTravelled += prevPos.getMagnitude(p.pos);
    }
    
    public void resolveCollisions(Vec2 vec, ArrayList<HitBox> array) {
        Vec2 d2;
        
        for(HitBox hb : array) {
            if(hb.collision(playerHitbox)) {

                HitBox test1 = new HitBox((int)p.pos.getX(), (int)prevPos.getY());
                test1.makeCircle(p.H/2);
                HitBox test2 = new HitBox((int)prevPos.getX(), (int)p.pos.getY());
                test2.makeCircle(p.H/2);
                
                if (hb.collision(test1) && !hb.collision(test2)) {
                    d2 = new Vec2(-(vec.getX()),0);   
                } else if (hb.collision(test2) && !hb.collision(test1)) {
                    d2 = new Vec2(0,-(vec.getY()));
                } else {
                    d2 = new Vec2(-(vec.getX()),-(vec.getY()));
                }
                updatePosition(d2);
            }
        }
    }
    
    public void resolvePlayerCollisions(Vec2 vec) {

        //check if in player
        for(int i = 0; i < GamePanel.numPlayers; i++) {
            Player player = GamePanel.playerArray.get(i);

            if (!(p.equals(player)) && player.physicsComp.playerHitbox.collision(playerHitbox)) {
                pushPlayer(player, vec);
            }
        }
    }
    
    public void pushPlayer(Player player, Vec2 delta) {
        double angle = p.pos.getAngle(player.pos);  // angle away from this player.
        double mag = delta.getMagnitude();
        Vec2 pdelta = new Vec2(angle,mag,1);
        
        player.physicsComp.updatePosition(pdelta);
        player.physicsComp.resolveMove(pdelta);
        player.physicsComp.prevPos.set(player.physicsComp.p.pos);
        
    }
    
    ////////////// CATCHING AND THROWING //////////////////
    
    private void updateThrowValues() {
        double movVelFactor = 0.3;
        
        Vec2 throwDelta = new Vec2((movVelFactor*p.delta.getX()/d)+throwSpeed*Math.cos(p.angle),
                                    (movVelFactor*p.delta.getY()/d)+throwSpeed*Math.sin(p.angle));
        relThrowSpeed = throwDelta.getMagnitude();
        
        double catchFactor = 7-(int)p.catchTimer.getDifference();
        if (catchFactor > 5) catchFactor = 5;
        else if (catchFactor < 1) catchFactor = 1;
        relThrowSpeed*= catchFactor/5d;
        
        p.throwAngle = throwDelta.getAngle();
        p.throwAngle = Tools.refreshAngle(p.throwAngle);
    }
    
    public void throwBall() {
        double x = p.pos.getX()+ 1.15*p.radius*Math.cos(p.angle);
        double y = p.pos.getY()+ 1.15*p.radius*Math.sin(p.angle);
        Ball b = new Ball(relThrowSpeed, x, y, p.throwAngle, p.team, p);
        b.setBall(relThrowSpeed, x, y, p.throwAngle, p.team, p);
        
        int count = 0;
        for(HitBox hb : GamePanel.arena.arenaBallHitbox) {
            if(hb.collision(b.ballHitbox)) {
                count++;
            }
        }
        for(HitBox hb : GamePanel.arena.arenaSoftBallHitbox) {
            if(hb.collision(b.ballHitbox)) {
                count++;
            }
        }
        for(Player player : GamePanel.playerArray) {
            if(player.physicsComp.playerHitbox.collision(b.ballHitbox)) {
                count++;
            }
        }
        
        if(p.numBalls > 0 && count<1) {
            p.ballThrows++;
            GamePanel.ballArray.add(b);
            p.numBalls--;
            p.catchTimer.refresh();
        }
    }
    
    public void catchObject() {
        if (p.catchOn) {
            for (int i=0; i<GamePanel.ballArray.size(); i++) {
                Ball b = GamePanel.ballArray.get(i);
                if(b.inCatchArea[p.pNumber]) {
                    p.catches++;
                    p.numBalls++;
                    GamePanel.soundManager.catchBall();
                    p.graphicsComp.setCatchGlow();
                    GamePanel.ballArray.remove(b);
                    i--;
                    p.catchTimer.refresh();
                }
            }
            //Catch powerUp
            for (int i=0; i<GamePanel.itemArray.size(); i++) {
                Item pUp = GamePanel.itemArray.get(i);
                if(pUp.inCatchArea[p.pNumber]) {
                    p.items++;
                    p.graphicsComp.setItemGlow(pUp.color);
                    pUp.applyEffect(p);
                    pUp.incPowerUpCount(p);
                    GamePanel.itemArray.remove(pUp);
                    i--;
                    p.catchTimer.refresh();
                }
            }
        }
    }
    
    /////////////// TOOLS  ////////////////

    
    
    // is called when a ball is in the catch area.
    public void inCatchArea(Ball ball, boolean b) {
        inCatchArea = b;
    }
}
