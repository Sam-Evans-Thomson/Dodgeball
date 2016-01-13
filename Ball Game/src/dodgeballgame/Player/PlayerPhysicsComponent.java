/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dodgeballgame.Player;

import dodgeballgame.Arenas.ArenaManager;
import dodgeballgame.Balls.Ball;
import dodgeballgame.Balls.HomingBall;
import dodgeballgame.GamePanel;
import dodgeballgame.HitBoxes.*;
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
    public CircleHitbox playerHitbox;
    public ArcHitbox catchHitbox;
    
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
        playerHitbox = new CircleHitbox(p.pos.getX(),p.pos.getY(), p.r);
        catchHitbox = new ArcHitbox(p.pos.getX(),p.pos.getY(),p.catchAngle, p.angle, p.radius);
        
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
        
        if (!p.solid && !p.isGhost) {
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
        resolveCollisions(vec, ArenaManager.arena.arenaPlayerHitbox);
        if(!p.isGhost && p.team == 0)         resolveCollisions(vec, ArenaManager.arena.arenaTeam1Hitbox);
        else if(!p.isGhost && p.team == 1)    resolveCollisions(vec, ArenaManager.arena.arenaTeam2Hitbox);
        //resolvePlayerCollisions(vec);
        p.distanceTravelled += prevPos.getMagnitude(p.pos);
    }
    
    public void resolveCollisions(Vec2 vec, ArrayList<Hitbox> array) {
        for(Hitbox hb : array) {
            if(hb.collision(playerHitbox)) {
                double angle = playerHitbox.bounceAngle(prevPos, vec.getAngle(), hb);
                double magnitude = vec.getMagnitude();
                Vec2 d2 = new Vec2(angle, magnitude,1);
                
                Vec2 newPos = p.pos.add(d2);
                double newAngle = prevPos.getAngle(newPos);
                double newMag = 0.5*prevPos.getMagnitude(newPos);
                
                Vec2 newD = new Vec2(newAngle, newMag,1);
                p.pos.set(prevPos);
                updatePosition(newD);
                resolveMove(newD);
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
    
    public Ball nextBall() {
        switch (p.nextBall) {
            case "NORMAL" : return new Ball(relThrowSpeed, 0, 0, p.throwAngle, p.team, p);
            case "HOMING" : return new HomingBall(relThrowSpeed, 0, 0, p.throwAngle, p.team, p);
        }
        return new Ball(relThrowSpeed, 0, 0, p.throwAngle, p.team, p);
    }
    
    public void throwBall() {
        Ball b = nextBall();
        double x,y;
        if (p.autoCatchOn) {
            x = p.pos.getX()+ 1.02*(catchHitbox.r + b.r)*Math.cos(p.angle);
            y = p.pos.getY()+ 1.02*(catchHitbox.r + b.r)*Math.sin(p.angle);
        } else {
            x = p.pos.getX()+ 1.02*(p.r + b.r)*Math.cos(p.angle);
            y = p.pos.getY()+ 1.02*(p.r + b.r)*Math.sin(p.angle);
        }
        
        
        b.setBall(relThrowSpeed, x, y, p.throwAngle, p.team, p);
        
        int count = 0;
        for(Hitbox hb : ArenaManager.arena.arenaBallHitbox) {
            if(hb.collision(b.ballHitbox)) {
                count++;
            }
        }
        for(Hitbox hb : ArenaManager.arena.arenaSoftBallHitbox) {
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
            //Catch Item
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
    
    public void eatObject() {

        for (int i=0; i<GamePanel.itemArray.size(); i++) {
            Item item = GamePanel.itemArray.get(i);
            Item newItem = GamePanel.itemManager.getOpposite(item,p.team);
            if(item.inCatchArea[p.pNumber]) {
                p.graphicsComp.setItemGlow(item.color);
                GamePanel.itemArray.remove(item);
                GamePanel.itemArray.add(newItem);
                i--;
                p.catchTimer.refresh();
            }
        }
    }
    
    /////////////// TOOLS  ////////////////

    
    
    // is called when a ball is in the catch area.
    public void inCatchArea(Ball ball, boolean b) {
        inCatchArea = b;
    }
}
