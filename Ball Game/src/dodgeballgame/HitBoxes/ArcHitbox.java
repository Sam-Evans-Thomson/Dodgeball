/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dodgeballgame.HitBoxes;

import dodgeballgame.Vec2;
import java.awt.Graphics2D;

/**
 *
 * @author Sam
 */
public class ArcHitbox extends Hitbox{
    
    public ArcHitbox(double x, double y, double r, double angle, double arcAngle) {
        super(x,y);
        this.r = r;
        this.angle = angle;
        this.arcAngle = arcAngle;
        shape = ARC;
    }
    
    public ArcHitbox(Vec2 pos, double r, double angle, double arcAngle) {
        super(pos);
        this.r = r;
        this.angle = angle;
        this.arcAngle = arcAngle;
        shape = ARC;
    }

    public ArcHitbox(double x, double y) {
        super(x, y);
        shape = ARC;
    }
    
    public ArcHitbox(Vec2 pos) {
        super(pos);
        shape = ARC;
    }

    @Override
    public boolean collision(Vec2 point) {
        double diff = pos.getMagnitude(point);
        double angleHB = pos.getAngle(point);
        double angleMax = angle+arcAngle/2;
        double angleMin = angle-arcAngle/2;

        if (diff <= r &&
                angleHB > (angleMin) &&
                angleHB < (angleMax)) {
            return true;
        } else if (diff <= r &&
                angleHB+2*Math.PI > (angleMin) &&
                angleHB+2*Math.PI < (angleMax)) {
            return true;
        } else if (diff <= r &&
                angleHB-2*Math.PI > (angleMin) &&
                angleHB-2*Math.PI < (angleMax)) {
            return true;
        }
        else return false;
    }
    
        
    @Override
    public boolean collision(LineHitbox line) {
        return super.collision(line);
    }
    
    @Override
    public boolean collision(ArcHitbox arc) {
        return super.collision(arc);
    }
    
    @Override
    public boolean collision(CircleHitbox circle) {
        double diff = pos.getMagnitude(circle.pos);
        double angleHB = pos.getAngle(circle.pos);
        double angleMax = angle+arcAngle/2;
        double angleMin = angle-arcAngle/2;
        if (diff < r + circle.r &&
                angleHB > (angleMin - r) &&
                angleHB < (angleMax -r)) {
            return true;
        }
        else return false;
    }
    
    @Override
    public boolean collision(RectHitbox rect) {
        return super.collision(rect);
    }
    
    @Override
    public void render(Graphics2D g) {
        g.fillOval((int)(pos.getX() - r),(int)(pos.getY()-r),(int)(2*r),(int)(2*r));
    }
    
}
