/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dodgeballgame.Environment;

import dodgeballgame.Balls.Ball;
import dodgeballgame.HitBoxes.Hitbox;
import dodgeballgame.Player.Player;
import dodgeballgame.Vec2;
import java.awt.Color;
import java.awt.Graphics2D;
import java.util.Random;

/**
 *
 * @author Sam
 */
public class BallTeleporter {
    
    public Hitbox hb1, hb2;
    public Hitbox exit, entry;
    
    public Vec2 diff;
    
    public int orientation;
    
    public final int HORIZONTAL = 0;
    public final int VERTICAL = 1;
    
    public int R,G,B, dR,dG,dB;
    
    
    public BallTeleporter(Hitbox hb1, Hitbox hb2, int ori) {
        this.hb1 = hb1;
        this.hb2 = hb2;
        diff = hb2.pos.take(hb1.pos);
        orientation = ori;
        
        Random rand = new Random();
        B = rand.nextInt(255);
        G = rand.nextInt(255);
        R = 155 + rand.nextInt(255);
        dB = rand.nextInt(3);
        dG = rand.nextInt(3);
        dR = rand.nextInt(3);
    }
    
    public void findDiff(Ball b, Hitbox exit) {
        if (orientation == HORIZONTAL) {
            double x = hb2.pos.take(hb1.pos).getX();
            double y = hb2.pos.take(hb1.pos).getY() - exit.h*2 - 2*b.hb.r;
            diff = new Vec2(x,y);
        } else if (orientation == VERTICAL) {
            double x = hb2.pos.take(hb1.pos).getX() - exit.w*2 - 2*b.hb.r;
            double y = hb2.pos.take(hb1.pos).getY();
            diff = new Vec2(x,y);
        }
    }
    
    public void collision(Ball b) {
        if (hb1.collision(b.hb)) {
            findDiff(b,hb2);
            exit = hb2;
            entry = hb1;
            
            b.pos.set(b.pos.add(diff));
            b.updateHitbox();
            
            b.pos.print();
        } else if (hb2.collision(b.hb)) {
            findDiff(b,hb1);
            exit = hb1;
            entry = hb2;
            b.pos.set(b.pos.take(diff));
            b.updateHitbox();
        }
    }
    
    public void collision(Player p) {
        
    }
    
    public void render(Graphics2D g) {
        updateColor(g);
        hb1.render(g);
        hb2.render(g);
    }
    
    public void updateColor(Graphics2D g) {
        Random rand = new Random();
        R += dR;
        if (R > 255) {
            R = 255;
            dR = -dR;
        } else if (R < 0) {
            R = 0;
            dR = -dR;
        }
        B += dB;
        if (B > 255) {
            B = 255;
            dB = -dB;
        } else if (B < 0) {
            B = 0;
            dB = -dB;
        }
        G += dG;
        if (G > 255) {
            G = 255;
            dG = -dG;
        } else if (G < 0) {
            G = 0;
            dG = -dG;
        }
        if(R+G+B == 300) {
            dB = rand.nextInt(3);
            dG = rand.nextInt(3);
            dR = rand.nextInt(3);
        }

        g.setColor(new Color(R,G,B));
    }

    
}
