/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dodgeballgame;

/**
 *
 * @author Sam
 */
public class BounceCalculator {
    
    // Assuming the first one is moving.
    
    double angle, factor;
    
    Vec2 v1, prevV1, delta1, resultant;
    Vec2 v2, prevV2;
    
    //still shape properties
    double width,height,r;
    int shape;
    
    // relation properties
    double movementAngle;
    double angleBetween;
    
    HitBox movingHB;
    HitBox stillHB;
    
    public BounceCalculator(HitBox movingHB, HitBox stillHB, double movementAngle, Vec2 prevPos) {
        this.movingHB = movingHB;
        this.stillHB = stillHB;
        v1 = movingHB.pos;
        
        this.movementAngle= refreshAngle(movementAngle);
        
        v2 = stillHB.pos;
        width = stillHB.w;
        height = stillHB.h;
        r = stillHB.r;
        shape = stillHB.shape;
        
        prevV1 = new Vec2(prevPos.getX(), prevPos.getY());
        
        delta1 = v1.take(prevV1);
        
        angleBetween = refreshAngle(v1.getAngle(v2));        
    } 
    
    public double calcBounceAngle() {

        if (shape == HitBox.VLINE) {
            angle = Math.PI - movementAngle;
        } else if (shape == HitBox.HLINE) {
            angle = -movementAngle;
        } else if(shape == HitBox.CIRCLE) {
            
            angle=angleBetween;
            
        } else if (shape == HitBox.RECT) {
            
            HitBox test1 = new HitBox((int)v1.getX(), (int)prevV1.getY());
            test1.makeCircle(movingHB.r);
            HitBox test2 = new HitBox((int)prevV1.getX(), (int)v1.getY());
            test2.makeCircle(movingHB.r);
            
            if (stillHB.collision(test1) && !stillHB.collision(test2)) {
                angle = Math.PI - movementAngle; 
                
            } else if (stillHB.collision(test2) && !stillHB.collision(test1)) {
                angle = -movementAngle;
                
            } else {
                angle = 2*Math.PI + movementAngle;
            }     
        }
        return angle;
    }
    
    public double calcBounceFactor() {
        return factor;
    }
    
    public double refreshAngle(double a) {
        while (a < 0) {
            a+=2.0*Math.PI;
        }
        while (a > 2*Math.PI) {
            a-=2.0*Math.PI;
        }
        return a;
    }
    
}
