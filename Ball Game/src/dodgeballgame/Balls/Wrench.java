/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dodgeballgame.Balls;

import dodgeballgame.Arenas.ArenaManager;
import dodgeballgame.GamePanel;
import dodgeballgame.HitBoxes.*;
import dodgeballgame.ImageEditor;
import dodgeballgame.Player.Player;
import dodgeballgame.Items.Item;
import dodgeballgame.Vec2;
import java.awt.Graphics2D;
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
    
    public Wrench(double speed, double x, double y, double angle, int team, Player player) {
        super(speed, x, y, angle, team, player);
        
        init();
    }
    
    @Override
    public void init() {
        bounceFactor = ArenaManager.arena.bounceFactor;
        softBounceFactor = ArenaManager.arena.softBounceFactor;
        inCatchArea = new boolean[GamePanel.numPlayers];
        bounceActive = true;
        r = 50;
        prevPos = new Vec2(pos.getX(),pos.getY());
        delta = new Vec2(0,0);
        prevDelta = new Vec2(0,0);
        speed += 100;
        
        ballHitbox = new CircleHitbox((int)pos.getX(),(int)pos.getY(),r-3);
        
        try {
            ball = ImageIO.read(new File("Images/Balls/wrench.png"));
        } catch (IOException e) {
        }
        
        imageWidth = ball.getWidth();
        
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
        p.hitPlayer(player,2);
        delete();
    }
    
    @Override
    public void checkHitboxes() {
        for(Player p : GamePanel.playerArray) {
            if(p.getPlayerHitbox().collision(ballHitbox)) {
                
                if(!GamePanel.friendlyFire && p.team == team) {

                } else {
                hitPlayer(p);
                   break; 
                }
            }
        }
        
        for(Hitbox hb : ArenaManager.arena.arenaBallHitbox) {
            if(hb.collision(ballHitbox)) {               
                GamePanel.ballArray.remove(this);
                break;
            }
        }
        
        for(Hitbox hb : ArenaManager.arena.arenaSoftBallHitbox) {
            if(hb.collision(ballHitbox)) {
                GamePanel.ballArray.remove(this);
                break;
            }
        }
        
        for(Item pu : GamePanel.itemArray) {
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
