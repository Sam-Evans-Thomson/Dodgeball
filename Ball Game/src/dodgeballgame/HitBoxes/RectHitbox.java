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
public class RectHitbox extends Hitbox{
    
    
    
    public RectHitbox(double x, double y, double w, double h) {
        super(x,y);
        this.w = w;
        this.h = h;
        shape = RECT;
    }
    
    public RectHitbox(double x, double y, double x2, double y2, int i){
        super(x+(x2-x)/2d,y+(y2-y)/2d);
        w = (x2-x)/2d;
        h = (y2-y)/2d;
    }
    
    public RectHitbox(Vec2 pos, double w, double h) {
        super(pos);
        this.w = w;
        this.h = h;
        shape = RECT;
    }
    
    public RectHitbox(double x, double y) {
        super(x,y);
        shape = RECT;
    }
    
    public RectHitbox(Vec2 pos) {
        super(pos);
        shape = RECT;
    }
    
    public void setDimensions(double width, double height) {
        w = width;
        h = height;
    }
    
    public void setDimensions(int width, int height) {
        w = (double)width;
        h = (double)height;
    }
    
    @Override
    public boolean collision(Vec2 point) {
        boolean hori = (point.getX()>(pos.getX()-w) && point.getX()<(pos.getX()+ w));
        boolean vert = (point.getY()>(pos.getY()-h) && point.getY()<(pos.getY() + h));
        return (hori&&vert);
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
        boolean left = ((circle.pos.getX()+circle.r) > (pos.getX() - w)
                && (circle.pos.getX()+circle.r) < (pos.getX() + w));
        boolean right = ((circle.pos.getX()-circle.r) < (pos.getX() + w)
                && (circle.pos.getX()-circle.r) > (pos.getX() - w));
        boolean top = ((circle.pos.getY()+circle.r) > (pos.getY() - h)
                && (circle.pos.getY()+circle.r) < (pos.getY() + h));
        boolean bottom = ((circle.pos.getY()-circle.r) < (pos.getY() + h)
                && (circle.pos.getY()-circle.r) > (pos.getY() - h));
                
        if ((left||right)&&(top||bottom)) return true;
        else return false;
    }
    
    @Override
    public boolean collision(RectHitbox rect) {
        double diffx = (w + rect.w - Math.abs(pos.getX() - rect.pos.getY()));
        double diffy = (h + rect.h - Math.abs(pos.getY() - rect.pos.getY()));
        if (diffx <= 0 && diffy <= 0) return true;

        else return false;
    }
    
        
    @Override
    public void render(Graphics2D g) {
        g.fillRect((int)(pos.getX() - w),(int)(pos.getY()-h),(int)(2*w),(int)(2*h));
    }
}
