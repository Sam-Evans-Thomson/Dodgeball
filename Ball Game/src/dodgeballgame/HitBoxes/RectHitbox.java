/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dodgeballgame.HitBoxes;

import dodgeballgame.Vec2;
import java.awt.Graphics2D;
import java.util.ArrayList;

/**
 *
 * @author Sam
 */
public class RectHitbox extends Hitbox{
    
    LineHitbox left,right,up,down;
    
    public RectHitbox(double x, double y, double w, double h) {
        super(x,y);
        this.w = w;
        this.h = h;
        shape = RECT;
        setLines();
    }
    
    public RectHitbox(double x, double y, double x2, double y2, int i){
        super(x+(x2-x)/2d,y+(y2-y)/2d);
        w = (x2-x)/2d;
        h = (y2-y)/2d;
        shape = RECT;
        setLines();
    }
    
    public RectHitbox(Vec2 pos, double w, double h) {
        super(pos);
        this.w = w;
        this.h = h;
        shape = RECT;
        setLines();
    }
    
    public RectHitbox(double x, double y) {
        super(x,y);
        shape = RECT;
        setLines();
    }
    
    public RectHitbox(Vec2 pos) {
        super(pos);
        shape = RECT;
        setLines();
    }
    
    @Override
    public void moveTo(Vec2 pos) {
        this.pos = pos;
        setLines();
    }
    
    private void setLines() {
        left = new LineHitbox(pos.getX() + w, 0, Hitbox.DEG_270);
        right = new LineHitbox(pos.getX() - w, 0, Hitbox.DEG_90);
        up = new LineHitbox(0, pos.getY() + h, Hitbox.DEG_0);
        down = new LineHitbox(0, pos.getY() - h, Hitbox.DEG_180);
    }
    
    public void setDimensions(double width, double height) {
        w = width;
        h = height;
        setLines();
    }
    
    public void setDimensions(int width, int height) {
        w = (double)width;
        h = (double)height;
        setLines();
    }
    
    @Override
    public boolean collision(Vec2 point) {
        return (
                left.collision(point) &&
                right.collision(point) &&
                up.collision(point) &&
                down.collision(point)
                );
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
        return (
                left.collision(circle) &&
                right.collision(circle) &&
                up.collision(circle) &&
                down.collision(circle)
                );
    }
    
    @Override
    public boolean collision(RectHitbox rect) {
        double diffx = (w + rect.w - Math.abs(pos.getX() - rect.pos.getY()));
        double diffy = (h + rect.h - Math.abs(pos.getY() - rect.pos.getY()));
        if (diffx <= 0 && diffy <= 0) return true;

        else return false;
    }
    
    public LineHitbox getLine(Vec2 prevPos, Vec2 pos) {
        
        double dx = pos.getX() - prevPos.getX();
        double dy = pos.getY() - prevPos.getY();
        
        double mag = prevPos.getMagnitude(pos);
        double angl = prevPos.getAngle(pos);
        
        if (dx == 0) dx = 0.001;
        double grad = dy/dx;
        
        Vec2 testPos = new Vec2(prevPos);
        
        ArrayList<LineHitbox> hits = new ArrayList();
        
        while (hits.size() != 1) {
            hits = new ArrayList();
            mag *= 0.5;
            
            // Hitboxes that register a hit at pos but not at testPos
            if (!left.collision(testPos) && left.collision(pos)) hits.add(left);
            if (!right.collision(testPos) && right.collision(pos)) hits.add(right);
            if (!up.collision(testPos) && up.collision(pos)) hits.add(up);
            if (!down.collision(testPos) && down.collision(pos)) hits.add(down);
            
            // needs to go further away from pos
            if(hits.size() < 1) {
                Vec2 temp = new Vec2(-angl,mag,1);
                testPos = testPos.add(temp);
            }
            // needs to go closer to pos
            else if (hits.size() > 1) {
                Vec2 temp = new Vec2(angl,mag,1);
                testPos = testPos.add(temp);
            }
        }

        return hits.get(0);

    }
    
    public LineHitbox getLine(Vec2 prevPos, CircleHitbox pos) {
        
        double mag = pos.r + prevPos.getMagnitude(pos.pos);
        double radius = pos.r + prevPos.getMagnitude(pos.pos);

        CircleHitbox testBox;
                
        ArrayList<LineHitbox> hits = new ArrayList();
        
        while (hits.size() != 1) {
            hits = new ArrayList();
            testBox = new CircleHitbox(prevPos,radius);
            mag *= 0.5;
           
            if (!left.collision(testBox) && left.collision(pos)) hits.add(left);
            if (!right.collision(testBox) && right.collision(pos)) hits.add(right);
            if (!up.collision(testBox) && up.collision(pos)) hits.add(up);
            if (!down.collision(testBox) && down.collision(pos)) hits.add(down);
            
            // needs to go further away from pos
            if(hits.size() < 1) radius -= mag;
            // needs to go closer to pos
            else if (hits.size() > 1) radius += mag;
        }
        
        return hits.get(0);
    }
    
        
    @Override
    public void render(Graphics2D g) {
        g.fillRect((int)(pos.getX() - w),(int)(pos.getY()-h),(int)(2*w),(int)(2*h));
    }
}
