/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dodgeballgame.Balls;

import dodgeballgame.Arenas.ArenaManager;
import dodgeballgame.GamePanel;
import dodgeballgame.HitBoxes.*;
import dodgeballgame.HitBoxes.Hitbox;
import dodgeballgame.Player.Player;
import dodgeballgame.Items.Item;
import dodgeballgame.Loading;
import dodgeballgame.Vec2;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

/**
 *
 * @author Sam
 */
public class Ball {
    
    public Vec2 pos, prevPos;
    public Vec2 delta, prevDelta;
    public double speed, angle;
    public double r = Loading.r;
    
    public int team;
    public Player player;
    
    public float d;
    
    public boolean bounceActive;
    public double bounceFactor;
    public double softBounceFactor;
    public double maxSpeed = 1200;
    
    public boolean[] inCatchArea;
    
    public CircleHitbox ballHitbox;
    
    public BufferedImage ball = Loading.ball;
    public BufferedImage ball0 = Loading.ball0;
    public BufferedImage ball1 = Loading.ball1;
    
    
    
    public Ball(double speed, double x, double y, double angle, int team, Player player) {
        this.team = team;
        this.speed = speed;
        pos = new Vec2(x,y);
        this.angle = angle;
        this.player = player;
        
        init();
    }
    
    public Ball(Ball b) {
        this.team = b.team;
        this.speed = b.speed;
        this.pos = b.pos;
        this.angle = b.angle;
        this.player = b.player;
        
        init();
    }
    
    public void setBall(double speed, double x, double y, double angle, int team, Player player) {
        this.team = team;
        this.speed = speed;
        pos = new Vec2(x,y);
        this.angle = angle;
        this.player = player;
        
        init();
    }
    
    public void init() {
        bounceFactor = ArenaManager.arena.bounceFactor;
        softBounceFactor = ArenaManager.arena.softBounceFactor;
        inCatchArea = new boolean[GamePanel.numPlayers];
        bounceActive = true;
        prevPos = new Vec2(pos.getX(),pos.getY());
        delta = new Vec2(0,0);
        prevDelta = new Vec2(0,0);
        
        ballHitbox = new CircleHitbox(pos.getX(),pos.getY(),r);

        if (!GamePanel.friendlyFire) {
            if(team == 0) ball = ball0;
            else ball = ball1;
        }
    }
    
    public void update(float d) {
        this.d = d;
        updatePosition();
        
        prevPos.set(pos);
        prevDelta.set(delta);
        
        if(pos.getX() < -20 || pos.getX()>GamePanel.arenaWIDTH + 20
                || pos.getY() < -20 || pos.getY() > GamePanel.arenaWIDTH + 20) {
            GamePanel.ballArray.remove(this);
        }
    }
    
    public void updatePosition() {
        delta.set(angle, d*speed, 1);
        pos = pos.add(delta);
        
        updateHitbox();
        checkHitboxes();
    }
    
    public void checkHitboxes() {
        for(Player p : GamePanel.playerArray) {
            if(p.getPlayerHitbox().collision(ballHitbox)) {
                
                if(!GamePanel.friendlyFire && p.team == team) {
                    p.numBalls++;
                    GamePanel.soundManager.catchBall();
                    p.setCatchGlow();
                    GamePanel.ballArray.remove(this);
                } else if (p.invincible) {
                    bouncePlayer(p);
                } else if (p.solid){
                    hitPlayer(p);
                    return; 
                }
                
            }
            if(p.getCatchHitbox().collision(pos)) {
                inCatchArea[p.pNumber] = true;
                if(p.autoCatchOn) p.physicsComp.catchObject();
            } else inCatchArea[p.pNumber] = false;
        }
            if (GamePanel.arenaManager.goalsActive) {
                for(Hitbox hb : ArenaManager.arena.arenaTeam1Goal) {
                    if(hb.collision(ballHitbox)) {
                        hitGoal(player, 0, hb);
                    }
                }
                for(Hitbox hb : ArenaManager.arena.arenaTeam2Goal) {
                    if(hb.collision(ballHitbox)) {
                        hitGoal(player, 1, hb);
                    }
                }
            }
        
        for(Hitbox hb : ArenaManager.arena.arenaBallHitbox) {
            if(hb.collision(ballHitbox)) {
                hitWall(hb,ArenaManager.arena.bounceFactor);
                return;
            }
        }
        
        for(Hitbox hb : ArenaManager.arena.arenaSoftBallHitbox) {
            if(hb.collision(ballHitbox)) {
                hitWall(hb,ArenaManager.arena.softBounceFactor);
                return;
            }
        }
        
        for(Item pu : GamePanel.itemArray) {
            if(pu.hb.collision(ballHitbox)) {
                pu.hitBall();
                return;
            }
        }
    }
    
    public void updateHitbox() {
        ballHitbox.moveTo((int)pos.getX(),(int)pos.getY());
    }
    
    public void render(Graphics2D g) {
        g.drawImage(ball, (int)(pos.getX()-r), (int)(pos.getY()-r), null);
    }
    
    public void bouncePlayer(Player p) {
        angle = ballHitbox.bounceAngle(prevPos, angle, p.getPlayerHitbox());
        
        double playerAngle = p.physicsComp.prevPos.getAngle(p.pos);
        double xFactor = Math.cos(angle)*p.delta.getX();
        double yFactor = Math.sin(angle)*p.delta.getY();
        
        double moveFactor;
        if(p.delta.getMagnitude() != 0d) {
            moveFactor = (xFactor+yFactor)/p.delta.getMagnitude();
        } else moveFactor = 0d;
        System.out.println(moveFactor);
        
        speed += 0.3*moveFactor*p.physicsComp.speed;
        if(speed>maxSpeed) speed = maxSpeed;
        pos = prevPos;
        delta.set(angle, d*speed, 1);
        pos = pos.add(delta); 
    }
    
    public void hitPlayer(Player p) {
        p.hitPlayer(player, 1);
        player.hitOtherPlayer(p,1);
        delete();
    }
    
    public void hitWall(Hitbox hb, double bf) {
        if (bounceActive) {
            angle = ballHitbox.bounceAngle(prevPos, angle, hb);
            speed = bf*speed;
            if(speed>maxSpeed) speed = maxSpeed;
            pos = prevPos;
            delta.set(angle, d*speed, 1);
            pos = pos.add(delta);
        } else {
            delete();
        }
    }
    
    public void hitGoal(Player player, int team, Hitbox hb) {
        
        if(player.team == team) {// if you hit your goal
            player.scoreGoal(team);
            ArenaManager.arena.setGoalGlow(hb);
            GamePanel.soundManager.addHealth();
            delete();
        } else if(GamePanel.friendlyFire) {         //If friendly fire is off
            player.scoreGoal(team);
            ArenaManager.arena.setGoalGlow(hb);
            GamePanel.soundManager.addHealth();
            delete();
        }
    }
    
    public void delete() {
        GamePanel.ballArray.remove(this);
    }
    
    public double refreshAngle(double a) {
        while (a < 0) {
            a+=2.0*Math.PI;
        }
        while (a > 2*Math.PI) {
            a-=2.0*Math.PI;
        }
        return a;
    }
}
