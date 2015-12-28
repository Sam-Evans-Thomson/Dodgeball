/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dodgeballgame.Balls;

import dodgeballgame.GamePanel;
import dodgeballgame.HitBox;
import dodgeballgame.ImageEditor;
import dodgeballgame.Player;
import dodgeballgame.PowerUps.PowerUp;
import dodgeballgame.Vec2;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 *
 * @author Sam
 */
public class Wrench extends Ball{
    
    ImageEditor imageEditor;
    
    double imageWidth;
    
    public Wrench(double speed, double x, double y, double angle, int team, int player) {
        super(speed, x, y, angle, team, player);
        
        init();
    }
    
    @Override
    public void init() {
        bounceFactor = GamePanel.arena.bounceFactor;
        softBounceFactor = GamePanel.arena.softBounceFactor;
        inCatchArea = new boolean[GamePanel.NUM_PLAYERS];
        bounceActive = true;
        r = 50;
        prevPos = new Vec2(pos.getX(),pos.getY());
        delta = new Vec2(0,0);
        prevDelta = new Vec2(0,0);
        speed += 100;
        
        ballHitbox = new HitBox((int)pos.getX(),(int)pos.getY());
        ballHitbox.makeCircle((int)r-3);
        
        try {
            ball = ImageIO.read(new File("Images/Balls/wrench.png"));
        } catch (IOException e) {
        }
        
        imageWidth = ball.getWidth();
        
        System.out.println(imageWidth);
        
        imageEditor = new ImageEditor(ball);
        ball = imageEditor.scale(2*r/imageWidth);
        
        if (!GamePanel.friendlyFire) {
            if(team == 0) ball = ball0;
            else ball = ball1;
        }
    }
    
    @Override
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
    
    @Override
    public void hitPlayer(Player p) {
        p.hitPlayer(2);
        delete();
    }
    
    @Override
    public void checkHitboxes() {
        for(Player p : GamePanel.playerArray) {
            if(p.playerHitbox.collision(ballHitbox)) {
                
                if(!GamePanel.friendlyFire && p.team == team) {

                } else {
                hitPlayer(p);
                   break; 
                }
            }
        }
        
        for(HitBox hb : GamePanel.arena.arenaBallHitbox) {
            if(hb.collision(ballHitbox)) {               
                GamePanel.ballArray.remove(this);
                break;
            }
        }
        
        for(HitBox hb : GamePanel.arena.arenaSoftBallHitbox) {
            if(hb.collision(ballHitbox)) {
                GamePanel.ballArray.remove(this);
                break;
            }
        }
        
        for(PowerUp pu : GamePanel.powerUpArray) {
            if(pu.hb.collision(ballHitbox)) {

                pu.hitBall();
                break;
            }
        }
    }
    
    @Override
    public void render(Graphics2D g) {
        ball = imageEditor.rotate(0.01*speed*d); 
        g.drawImage(ball, (int)(pos.getX()-r), (int)(pos.getY()-r), null);
    }
}
