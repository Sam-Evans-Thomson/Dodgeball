/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dodgeballgame.Environment;

import dodgeballgame.Arenas.ArenaManager;
import dodgeballgame.Balls.Ball;
import dodgeballgame.HitBoxes.Hitbox;
import dodgeballgame.Loading;
import dodgeballgame.Vec2;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 *
 * @author Sam
 */
public class BreakableBlock {
    
    public Hitbox hb;
    public Vec2 pos;
    
    public int health;
    public int startingHealth = 3;
    
    public boolean isActive = true;
    
    BufferedImage breakImage;
    
    public static ArrayList<BufferedImage> breaks;
    
    public BreakableBlock(Hitbox hb) {
        this.hb = hb;
        this.pos = hb.pos;
        health = startingHealth;

        breaks = Loading.breaks;

        breakImage = breaks.get(0);
    }
    
    public void render(Graphics2D g) {
        g.drawImage(breakImage,
                (int)(hb.pos.getX()-hb.w), 
                (int)(hb.pos.getY()-hb.h),
                null);
    }
    
    public void hit(Ball b) {
        health--;
        
        if(health < 1) death();
        else breakImage = Loading.breaks.get(startingHealth-health);
    }
    
    public void death() {
        isActive = false;
        ArenaManager.arena.removeBreakBlock(this);
    }
}
