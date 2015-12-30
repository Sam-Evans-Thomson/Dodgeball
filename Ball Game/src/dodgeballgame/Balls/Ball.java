/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dodgeballgame.Balls;

import dodgeballgame.Arenas.Arena;
import dodgeballgame.BounceCalculator;
import dodgeballgame.GamePanel;
import dodgeballgame.HitBox;
import dodgeballgame.ImageEditor;
import dodgeballgame.Player;
import dodgeballgame.PowerUps.PowerUp;
import dodgeballgame.SoundManager;
import dodgeballgame.Vec2;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 *
 * @author Sam
 */
public class Ball {
    public ImageEditor imageEditor;
    public Vec2 pos, prevPos;
    public Vec2 delta, prevDelta;
    public double speed, angle;
    public double r;
    
    public int team;
    public int player;
    
    public float d;
    
    public boolean bounceActive;
    public double bounceFactor;
    public double softBounceFactor;
    public double maxSpeed = 1200;
    
    public boolean[] inCatchArea;
    
    public HitBox ballHitbox;
    public BufferedImage ball, ball0, ball1;
    
    double imageWidth;
    
    public Ball(double speed, double x, double y, double angle, int team, int player) {
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
    
    public void setBall(double speed, double x, double y, double angle, int team, int player) {
        this.team = team;
        this.speed = speed;
        pos = new Vec2(x,y);
        this.angle = angle;
        this.player = player;
        
        init();
    }
    
    public void init() {
        bounceFactor = GamePanel.arena.bounceFactor;
        softBounceFactor = GamePanel.arena.softBounceFactor;
        inCatchArea = new boolean[GamePanel.NUM_PLAYERS];
        bounceActive = true;
        r = 15;
        prevPos = new Vec2(pos.getX(),pos.getY());
        delta = new Vec2(0,0);
        prevDelta = new Vec2(0,0);
        
        ballHitbox = new HitBox((int)pos.getX(),(int)pos.getY());
        ballHitbox.makeCircle((int)r);
        
        try {
            ball = ImageIO.read(new File("Images/Balls/ball.png"));
            ball0 = ImageIO.read(new File("Images/Balls/ball0.png"));
            ball1 = ImageIO.read(new File("Images/Balls/ball1.png"));
        } catch (IOException e) {
        }
        
        imageWidth = ball.getWidth();
        imageEditor = new ImageEditor(ball);
        ImageEditor imageEditor0 = new ImageEditor(ball0);
        ImageEditor imageEditor1 = new ImageEditor(ball1);
        ball = imageEditor.scale(2*r/imageWidth);
        ball0 = imageEditor0.scale(2*r/imageWidth);
        ball1 = imageEditor1.scale(2*r/imageWidth);
        
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
            if(p.playerHitbox.collision(ballHitbox)) {
                
                if(!GamePanel.friendlyFire && p.team == team) {
                    p.numBalls++;
                    GamePanel.soundManager.catchBall();
                    p.pGraphics.setCatchGlow();
                    GamePanel.ballArray.remove(this);
                } else if (p.solid){
                    hitPlayer(p);
                    return; 
                }
                
            }
            if(p.catchHitbox.collisionPoint(pos)) {
                inCatchArea[p.pNumber] = true;
            } else inCatchArea[p.pNumber] = false;
        }
            if (Arena.goalsActive) {
                for(HitBox hb : GamePanel.arena.arenaTeam1Goal) {
                    if(hb.collision(ballHitbox)) {
                        hitGoal(player, 0);
                    }
                }
                for(HitBox hb : GamePanel.arena.arenaTeam2Goal) {
                    if(hb.collision(ballHitbox)) {
                        hitGoal(player, 1);
                    }
                }
            }
        
        for(HitBox hb : GamePanel.arena.arenaBallHitbox) {
            if(hb.collision(ballHitbox)) {

                hitWall(hb,GamePanel.arena.bounceFactor);
                return;
            }
        }
        
        for(HitBox hb : GamePanel.arena.arenaSoftBallHitbox) {
            if(hb.collision(ballHitbox)) {
                hitWall(hb,GamePanel.arena.softBounceFactor);
                return;
            }
        }
        
        for(PowerUp pu : GamePanel.powerUpArray) {
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
    
    public void hitPlayer(Player p) {
        p.hitPlayer();
        delete();
    }
    
    public void hitWall(HitBox hb, double bf) {
        if (bounceActive) {
            BounceCalculator bc = new BounceCalculator(ballHitbox, hb, angle, prevPos);
            angle = refreshAngle(bc.calcBounceAngle());
            speed = bf*speed;
            if(speed>maxSpeed) speed = maxSpeed;
            pos = prevPos;
        } else {
            delete();
        }
    }
    
    public void hitGoal(int player, int team) {
        
        if(GamePanel.playerArray.get(player).team == team) {// if you hit your goal
            GamePanel.playerArray.get(player).scoreGoal(team);
            GamePanel.soundManager.addHealth();
            delete();
        } else if(GamePanel.matchSettings.getDouble(5) != 0) {         //If friendly fire is off
            GamePanel.playerArray.get(player).scoreGoal(team);
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
