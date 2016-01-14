/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dodgeballgame.Animation;

import dodgeballgame.HitBoxes.CircleHitbox;
import dodgeballgame.HitBoxes.Hitbox;
import dodgeballgame.HitBoxes.RectHitbox;
import dodgeballgame.Vec2;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;

/**
 *
 * @author Sam
 */
public class GoalGlowAnimation extends Animation{
    
    public Color color1, color2;
    public Hitbox hb;
    public double radius;
    
    public GoalGlowAnimation(double maxTime) {
        super(maxTime);
        color1 = new Color(255,255,255);
        color2 = new Color(255,255,255);
    }
    
    public GoalGlowAnimation(Color color, Hitbox hb, double maxTime, double radius) {
        super(maxTime);
        color1 = color;
        color2 = color;
        this.hb = hb;
        this.radius = radius;
    }
    
    public GoalGlowAnimation(int r, int g, int b, Hitbox hb, double maxTime) {
        super(maxTime);
        color1 = new Color(r,g,b);
        color2 = new Color(r,g,b);
        this.hb = hb;
    }
    
    public void setHitBox(Hitbox hb) {
        this.hb = hb;
    }
    
    public void setRadius(double r) {
        radius = r;
    }
    
    public void setColor(Color color) {
        color1 = color;
        color2 = color;
    }
    
    public void setColor(int i, Color color) {
        if(i == 0)color1 = color;
        else if (i == 1) color2 = color;
    }
    
    public void setColors(Color color1, Color color2) {
        this.color1 = color1;
        this.color2 = color2;
    }
    public void setColor(int r1, int g1, int b1) {
        this.color1 = new Color(r1,g1,b1);
        this.color2 = new Color(r1,g1,b1);
    }
    
    public void setColors(int r1, int g1, int b1, int r2, int g2, int b2) {
        this.color1 = new Color(r1,g1,b1);
        this.color2 = new Color(r2,g2,b2);
    }
    
    public void paintSet(Graphics2D g, Color color, float opacity) {
        g.setColor(color);
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity));
    }
    
    @Override
    public void render(Graphics2D g) {
        double factor = 1-(timeMax - time)/timeMax;
        Color color = new Color(
                (int)((1-factor)*color1.getRed() + factor*color2.getRed()),
                (int)((1-factor)*color1.getGreen() + factor*color2.getGreen()),
                (int)((1-factor)*color1.getBlue() + factor*color2.getBlue()));
        paintSet(g, color1, (float)(1-factor));
        double size = 150d + (radius-100d)*time/timeMax;
        factor *= 6d;
        if (hb instanceof RectHitbox) {
            g.fillRoundRect(
                (int)(hb.pos.getX() - factor*hb.w/2), 
                (int)(hb.pos.getY() - factor*hb.h/2), 
                (int)(factor*hb.w), 
                (int)(factor*hb.h), 
                (int)size, 
                (int)size);
        } else if (hb instanceof CircleHitbox) {
            g.fillOval(
                (int)(hb.pos.getX() - size/2), 
                (int)(hb.pos.getY() - size/2), 
                (int)size, 
                (int)size);
        }
        
        getTime();
    }
    
    @Override
    public void render(Graphics2D g, Vec2 pos) {
        double factor = (timeMax - time)*0.8d/timeMax;
        Color color = new Color(
                (int)((1-factor)*color1.getRed() + factor*color2.getRed()),
                (int)((1-factor)*color1.getGreen() + factor*color2.getGreen()),
                (int)((1-factor)*color1.getBlue() + factor*color2.getBlue()));
        paintSet(g, color1, (float)factor);
        double size = 100d + (radius-100d)*time/timeMax;
        g.fillOval((int)(pos.getX()-size/2), (int)(pos.getY()-size/2),(int)size, (int)size);
        getTime();
    }
}
