/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dodgeballgame.HitBoxes;

import dodgeballgame.HitBoxes.LineHitbox.HorizontalLineHitbox;
import dodgeballgame.Tools;
import dodgeballgame.Vec2;
import java.awt.Graphics2D;

/**
 *
 * @author Sam
 */
public class CircleHitbox extends Hitbox{
    
    public CircleHitbox(double x, double y, double r) {
        super(x,y);
        this.r = r;
        shape = CIRCLE;
    }
    
    public CircleHitbox(Vec2 pos, double r) {
        super(pos);
        this.r = r;
        shape = CIRCLE;
    }

    public CircleHitbox(double x, double y) {
        super(x, y);
        shape = CIRCLE;
    }
    
    public CircleHitbox(Vec2 pos) {
        super(pos);
        shape = CIRCLE;
    }

    @Override
    public boolean collision(Vec2 point) {
        double diff = pos.getMagnitude(point);
        return (diff<=r);
    }
        
    @Override
    public boolean collision(LineHitbox line) {
        return line.collision(this);
    }
    
    @Override
    public boolean collision(ArcHitbox arc) {
        return super.collision(arc);
    }
    
    @Override
    public boolean collision(CircleHitbox circle) {
        double diff = pos.getMagnitude(circle.pos);
        if (diff < r + circle.r) return true;
        else return false;
    }
    
    @Override
    public boolean collision(RectHitbox rect) {
        boolean left = ((pos.getX()+r) > (rect.pos.getX() - rect.w)
                && (pos.getX()+r) < (rect.pos.getX() + rect.w));
        boolean right = ((pos.getX()-r) < (rect.pos.getX() + rect.w)
                && (pos.getX()-r) > (rect.pos.getX() - rect.w));
        boolean top = ((pos.getY()+r) > (rect.pos.getY() - rect.h)
                && (pos.getY()+r) < (rect.pos.getY() + rect.h));
        boolean bottom = ((pos.getY()-r) < (rect.pos.getY() + rect.h)
                && (pos.getY()-r) > (rect.pos.getY() - rect.h));

        return (left||right)&&(top||bottom);
    }
    
    @Override
    public double bounceAngle(double angle, LineHitbox line) {
        return 2d*line.angle - angle;
    }
    
    @Override
    public double bounceAngle(Vec2 prevPos, double angle, CircleHitbox circle) {
        double tangentAngle = Tools.refreshAngle(circle.pos.getAngle(prevPos) - DEG_90);
        return 2d*tangentAngle - angle;
    }
    
    @Override
    public double bounceAngle(Vec2 prevPos, double angle, RectHitbox rect) {
        
        CircleHitbox test1 = new CircleHitbox(pos.getX(), pos.getY() - 20*Math.sin(angle), r);
        CircleHitbox test2 = new CircleHitbox(pos.getX() - 20*Math.cos(angle), pos.getY(), r);

        if (rect.collision(test1) && !rect.collision(test2)) {
            return Tools.refreshAngle(Math.PI - angle); 
        } else if (rect.collision(test2) && !rect.collision(test1)) {
            return Tools.refreshAngle(-angle);
        } else {
            return Tools.refreshAngle(Math.PI + angle);
            
        } 
    }
    
     @Override
    public double bounceAngle(Vec2 prevPos, double angle, RectHitbox2 rect) {
        LineHitbox line = rect.getLine(prevPos, this);
        return bounceAngle(angle, line);
    }
    
    @Override
    public void render(Graphics2D g) {
        g.fillOval((int)(pos.getX() - r),(int)(pos.getY()-r),(int)(2*r),(int)(2*r));
    }
    
}
