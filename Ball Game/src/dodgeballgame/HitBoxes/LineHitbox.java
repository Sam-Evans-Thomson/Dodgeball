/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dodgeballgame.HitBoxes;

import dodgeballgame.GamePanel;
import dodgeballgame.Vec2;
import java.awt.Graphics2D;

/**
 *
 * @author Sam
 */
public class LineHitbox extends Hitbox{
    
    Vec2 ORIGIN = new Vec2(0,0);
    HorizontalLineHitbox horiLine = new HorizontalLineHitbox(ORIGIN);
    
    public LineHitbox(double x, double y, double angle) {
        super(x,y);
        this.angle = angle;
        shape = LINE;
    }
    
    public LineHitbox(Vec2 pos, double angle) {
        super(pos);
        this.angle = angle;
        shape = LINE;
    }
    
    public LineHitbox(double x, double y) {
        super(x,y);
        shape = LINE;
    }
    
    public LineHitbox(Vec2 pos) {
        super(pos);
        shape = LINE;
    }
    
    @Override
    public boolean collision(Vec2 point) {
        Vec2 test = point.changeReference(angle, pos);
        
        return horiLine.collision(test);
    }
    
    @Override
    public boolean collision(CircleHitbox circle) {
        CircleHitbox test = new CircleHitbox(circle.pos.changeReference(angle, pos), circle.r);
        return (horiLine.collision(test));
    }
    
    // Not Implemented
    
    @Override
    public boolean collision(LineHitbox line) {
        return super.collision(line);
    }
    
    @Override
    public boolean collision(ArcHitbox arc) {
        return super.collision(arc);
    }
    
        
    @Override
    public void render(Graphics2D g) {
        int xStart = (int)(pos.getX() - GamePanel.arenaWIDTH*Math.cos(angle));
        int yStart = (int)(pos.getY() - GamePanel.arenaWIDTH*Math.sin(angle));
        int xEnd = (int)(pos.getX() + GamePanel.arenaWIDTH*Math.cos(angle));
        int yEnd = (int)(pos.getY() + GamePanel.arenaWIDTH*Math.sin(angle));
        g.drawLine(xStart, yStart, xEnd, yEnd);  
    }

    
    
    
    public class HorizontalLineHitbox extends Hitbox {
        
        public HorizontalLineHitbox(double x, double y) {
            super(0,y);
        }
        
        public HorizontalLineHitbox(Vec2 origin) {
            super(0, origin.getY());
        }
        
        @Override
        public boolean collision(Vec2 point) {
            return (point.getY() < pos.getY());
        }

        @Override
        public boolean collision(CircleHitbox circle) {
            return (circle.pos.getY() - circle.r < pos.getY());
        }
        
        // Not implemented
        @Override
        public boolean collision(LineHitbox line) {
            return super.collision(line);
        }

        @Override
        public boolean collision(ArcHitbox arc) {
            return super.collision(arc);
        }

        @Override
        public boolean collision(RectHitbox rect) {
            return super.collision(rect);
        }    
    }
}
