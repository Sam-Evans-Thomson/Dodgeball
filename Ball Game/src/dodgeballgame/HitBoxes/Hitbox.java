/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dodgeballgame.HitBoxes;

import dodgeballgame.Vec2;
import java.awt.Graphics2D;
import dodgeballgame.HitBoxes.*;

/**
 *
 * @author Sam
 */
public class Hitbox {
    
    public static double DEG_0 = 0d;
    public static double DEG_45 = Math.PI/4d;
    public static double DEG_90 = Math.PI/2d;
    public static double DEG_135 = 3d*Math.PI/4d;
    public static double DEG_180 = Math.PI;
    public static double DEG_225 = 5d*Math.PI/4d;
    public static double DEG_270 = 3d*Math.PI/2d;
    public static double DEG_315 = 7d*Math.PI/4d;
    
    
    public Vec2 pos;
    public double r;
    public double angle;
    public double arcAngle;
    public double h;
    public double w;

    public int shape;
    public static int CIRCLE = 0;
    public static int RECT = 1;
    public static int ARC = 2;
    public static int LINE = 3;
    
    public Hitbox(double x, double y) {
        pos = new Vec2(x,y);
    }
    
    public Hitbox(Vec2 pos) {
        this.pos = pos;
    }
        
    // move to these coordinates
    public void moveTo(double x, double y) {
        pos.set(x,y);
    }
    
    public void moveTo(Vec2 pos) {
        this.pos = pos;
    }
    // move by this much
    public void moveBy(double x, double y) {
        pos.add(new Vec2(x,y));
    }
    
    public void scale(double scale) {
        r*=r;
    }
    
    public void scale(double x, double y) {
        w*=x;
        h*=y;
    }
    
    public void setAngle(double angle) {
        this.angle = angle;
    }
    
    public void setRadius(double radius) {
        r = radius;
    }
    
    public void setArcAngle(double arcAngle) {
        this.arcAngle = arcAngle;
    }
    
    public void setPos(Vec2 pos) {
        this.pos = pos;
    }
    
    public boolean collision(Vec2 point) {
        System.out.println("Not yet implemented.");
        return false;
    }
    
    public boolean collision(LineHitbox line) {
        System.out.println("Not yet implemented.");
        return false;
    }
    
    public boolean collision(ArcHitbox arc) {
        System.out.println("Not yet implemented.");
        return false;
    }
    
    public boolean collision(CircleHitbox circle) {
        System.out.println("Not yet implemented.");
        return false;
    }
    
    public boolean collision(RectHitbox rect) {
        System.out.println("Not yet implemented.");
        return false;
    }

    
    public double bounceAngle(Vec2 prevPos, double angle, Hitbox hitbox) {
        if(hitbox instanceof LineHitbox) return bounceAngle(angle, (LineHitbox)hitbox);
        else if(hitbox instanceof CircleHitbox) return bounceAngle(prevPos, angle, (CircleHitbox)hitbox);
        else if(hitbox instanceof RectHitbox) return bounceAngle(prevPos, angle, (RectHitbox)hitbox);
        else if(hitbox instanceof ArcHitbox) return bounceAngle(angle, (ArcHitbox)hitbox);
        else return 0d;
    }
    
    public double bounceAngle(double angle, LineHitbox line) {
        return 0d;
    }
    
    public double bounceAngle(Vec2 prevPos, double angle, CircleHitbox circle) {
        return 0d;
    }
    
    public double bounceAngle(Vec2 prevPos, double angle, RectHitbox rect) {
        return 0d; 
    }

    
    public double bounceAngle(double angle, ArcHitbox rect) {
        return 0d; 
    }
    
    public void render(Graphics2D g) {

    }
}
