/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dodgeballgame;

import java.awt.Graphics2D;

/**
 *
 * @author Sam
 */
public class HitBox {
    
    public Vec2 pos;
    public double r;
    public double angle;
    public double arcAngle;
    public double h;
    public double w;
    public boolean right;
    public boolean down;
    
    public int shape;
    public static int CIRCLE = 0;
    public static int RECT = 1;
    public static int ARC = 2;
    public static int HLINE = 3;
    public static int VLINE = 4;
    
    public HitBox(double x, double y) {
        pos = new Vec2(x,y);
    }
    
    public HitBox(Vec2 pos) {
        this.pos = pos;
    }
    
    public void makeCircle(double r) {
        shape = CIRCLE;
        this.r = r;
    }
    
    // makes a rectangular hitbox of width 2*w and height 2*h
    public void makeRect(double w, double h) {
        shape = RECT;
        this.w = w;
        this.h = h;
    }
    
    public void makeArc(double arcAngle, double angle, double r) {
        shape = ARC;
        this.arcAngle = arcAngle;
        this.angle = angle;
        this.r = r;
    }
    
    public void makeHLine(boolean down) {
        shape = HLINE;
        this.down = down;
    }
    
    public void makeVLine(boolean right) {
        shape = VLINE;
        this.right = right;
    }
    
    // move to these coordinates
    public void moveTo(double x, double y) {
        pos.set(x,y);
    }
    // move by this much
    public void moveBy(double x, double y) {
        pos.add(new Vec2(x,y));
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
        
    public void scaleCircle(double scale) {
        r = (int)((double)r*scale);
    }
    
    public void setPos(Vec2 pos) {
        this.pos = pos;
    }
        
    public void scaleRect(double scalex, double scaley) {
        pos.setX(scalex*pos.getX());
        pos.setY(scaley*pos.getY());
    }
    
    public boolean collision(HitBox otherHB) {
        if (shape == VLINE) {
            if (otherHB.shape == CIRCLE) {
                if (right) return (otherHB.pos.getX() + otherHB.r > pos.getX());
                else if (!right) return (otherHB.pos.getX() - otherHB.r < pos.getX());
            }
            else if (otherHB.shape == RECT) {
                if (right) return(otherHB.pos.getX() + otherHB.w > pos.getX());
                else if (!right) return (otherHB.pos.getX() - otherHB.w < pos.getX());
            }
            
        } else if (shape == HLINE) {
            if (otherHB.shape == CIRCLE) {
                if (down) return (otherHB.pos.getY() + otherHB.r > pos.getY());
                else if (!down) return (otherHB.pos.getY() - otherHB.r < pos.getY());
            }
            else if (otherHB.shape == RECT) {
                if (down) return(otherHB.pos.getY() + otherHB.h > pos.getY());
                else if (!down) return (otherHB.pos.getY() - otherHB.h < pos.getY());
            }
            
        } else if (shape == ARC) {
            
            double angleMax = angle+arcAngle/2;
            double angleMin = angle-arcAngle/2;
            
            double diff = pos.getMagnitude(otherHB.pos);
            double angleHB = pos.getAngle(otherHB.pos);

            if(otherHB.shape == CIRCLE) {
                if (diff < r + otherHB.r &&
                        angleHB > (angleMin - r) &&
                        angleHB < (angleMax -r)) {
                    return true;
                }
                else return false;
            }
        } else if (shape == CIRCLE) {
            
            if (otherHB.shape == HLINE) {
                if (otherHB.down && pos.getY() + r > otherHB.pos.getY()) {
                    
                    return true;
                }
                else if (!otherHB.down && pos.getY() - r < otherHB.pos.getY()) return true;
                else return false;
            }
            if (otherHB.shape == VLINE) {
                if (otherHB.right && pos.getX() + r > otherHB.pos.getX()) return true;
                else if (!otherHB.right && pos.getX() - r < otherHB.pos.getX()) return true;
                else return false;
            }
            
            if (otherHB.shape == CIRCLE) {
                
                double diff = pos.getMagnitude(otherHB.pos);
                if (diff < r + otherHB.r) return true;
                else return false;
                
            } else if (otherHB.shape == RECT) {

                boolean left = ((pos.getX()+r) > (otherHB.pos.getX() - otherHB.w)
                        && (pos.getX()+r) < (otherHB.pos.getX() + otherHB.w));
                boolean right = ((pos.getX()-r) < (otherHB.pos.getX() + otherHB.w)
                        && (pos.getX()-r) > (otherHB.pos.getX() - otherHB.w));
                boolean top = ((pos.getY()+r) > (otherHB.pos.getY() - otherHB.h)
                        && (pos.getY()+r) < (otherHB.pos.getY() + otherHB.h));
                boolean bottom = ((pos.getY()-r) < (otherHB.pos.getY() + otherHB.h)
                        && (pos.getY()-r) > (otherHB.pos.getY() - otherHB.h));

                
                if ((left||right)&&(top||bottom)) {
                    return true;
                }
                else return false;
                
            }
        } else if (shape == RECT) {
            
            if (otherHB.shape == HLINE) {
                if (otherHB.down && pos.getY() + h > otherHB.pos.getY()) return true;
                else if (!otherHB.down && pos.getY() - r < otherHB.pos.getY()) return true;
                else return false;
            }
            if (otherHB.shape == VLINE) {
                if (otherHB.right && pos.getX() + w > otherHB.pos.getX()) return true;
                else if (!otherHB.right && pos.getX() - r < otherHB.pos.getX()) return true;
                else return false;
            }
            
            if (otherHB.shape == CIRCLE) {
                
                boolean left = ((otherHB.pos.getX()+otherHB.r) > (pos.getX() - w)
                        && (otherHB.pos.getX()+otherHB.r) < (pos.getX() + w));
                boolean right = ((otherHB.pos.getX()-otherHB.r) < (pos.getX() + w)
                        && (otherHB.pos.getX()-otherHB.r) > (pos.getX() - w));
                boolean top = ((otherHB.pos.getY()+otherHB.r) > (pos.getY() - h)
                        && (otherHB.pos.getY()+otherHB.r) < (pos.getY() + h));
                boolean bottom = ((otherHB.pos.getY()-otherHB.r) < (pos.getY() + h)
                        && (otherHB.pos.getY()-otherHB.r) > (pos.getY() - h));
                
                if ((left||right)&&(top||bottom)) return true;
                else return false;
                
            } else if (otherHB.shape == RECT) {
                
                double diffx = (w + otherHB.w - (int)Math.abs(pos.getX() - otherHB.pos.getY()));
                double diffy = (h + otherHB.h - (int)Math.abs(pos.getY() - otherHB.pos.getY()));
                if (diffx <= 0 && diffy <= 0) return true;
                
                else return false; 
            }
        }
        
        return false;
    }
    
    public boolean collisionPoint(double x, double y) {
        return collisionPoint(new Vec2(x,y));
    }
    
    public boolean collisionPoint(int x, int y) {
        return collisionPoint(new Vec2(x,y));
    }
    
    public boolean collisionPoint(float x, float y) {
        return collisionPoint(new Vec2(x,y));
    }
    
    public boolean collisionPoint(Vec2 v) {
        
        double diff = pos.getMagnitude(v);
        double angleHB = pos.getAngle(v);
        if (shape == HLINE) {
            if (down) return (v.getY() > pos.getY());
            else return (v.getY() < pos.getY());
        } else if(shape == VLINE) {
            if (right) return (v.getX() > pos.getX());
            else return (v.getX() < pos.getX());
        } else if (shape == ARC) {
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
        } else if (shape == CIRCLE) {            
            return (diff<=r);
        } else if (shape == RECT) {
            boolean hori = (v.getX()>(pos.getX()-w) && v.getX()<(pos.getX()+ w));
            boolean vert = (v.getY()>(pos.getY()-h) && v.getY()<(pos.getY() + h));
            return (hori&&vert);
        }
        return false;
    }
    
    public void render(Graphics2D g) {
        switch (shape) {
            case 0 : fillCircle(g);
                break;
            case 1 : fillRect(g);
        }
    }
    
    public void fillRect(Graphics2D g) {
        g.fillRect((int)(pos.getX() - w),(int)(pos.getY()-h),(int)(2*w),(int)(2*h));
    }
    
    public void fillCircle(Graphics2D g) {
        g.fillOval((int)(pos.getX() - r),(int)(pos.getY()-r),(int)(2*r),(int)(2*r));
    }
}
