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
public class Vec2 {
    double x;
    double y;
    

    public Vec2(double x, double y) {
        this.x = x;
        this.y = y;
    }
    
    public Vec2(int x, int y) {
        this.x = (double)x;
        this.y = (double)y;
    }
    
    public Vec2(float x, float y) {
        this.x = (double)x;
        this.y = (double)y;
    }
    
    public Vec2(double angle, double mag, int i) {
        x = mag*Math.cos(angle);
        y = mag*Math.sin(angle);
    }
    
    /////////
    
    public void test() {
        Vec2 v1 = new Vec2(2.0,4.0);
        Vec2 v2 = new Vec2(-2.0,-1.0);
        
        System.out.print("Vector 1: ");
        print(v1);
        System.out.print("Vector 2: ");
        print(v2);
        System.out.println();
        
        double angle1 = v1.getAngle();
        double angle2 = v2.getAngle();
        double angleBetween = v1.getAngle(v2);
        
        System.out.println("Vector 1: Expected angle = 1.107 result: " + angle1);
        System.out.println("Vector 2: Expected angle = 3.605 result: " + angle2);
        System.out.println("Angle between: Expected angle = 4.037 result: " + angleBetween);
        
        
        double mag1 = v1.getMagnitude();
        double mag2 = v2.getMagnitude();
        double magBetween = v1.getMagnitude(v2);
        
        Vec2 v3 = v1.add(v2);
        Vec2 v4 = v1.take(v2);
        v1.scale(2.0);
    }
    
    public double getAngle() {        
        double angle = Math.atan2(y,x);
        if (angle < 0.0) angle+= 2*Math.PI;
        return angle;
    }
    
    public double getAngle(Vec2 vec) {
        Vec2 v = new Vec2(vec.getX() - x, vec.getY() - y);

        return v.getAngle();
    }
    
    public double getMagnitude() {
        return Math.sqrt(x*x + y*y);        
    }
    
    public double getMagnitude(Vec2 vec) {
        double x2 = vec.getX();
        double y2 = vec.getY();
        double dx = x2-x;
        double dy = y2-y;
        
        return Math.sqrt(dx*dx + dy*dy);        
    }
    
    public Vec2 add(Vec2 vec) {
        return new Vec2(this.x + vec.getX(),this.y + vec.getY());
    }
    
    public Vec2 take(Vec2 vec) {
        return new Vec2(this.x - vec.getX(),this.y - vec.getY());
    }
    
    public void scale(double s) {
        x*=s;
        y*=s;
    }
    
    public Vec2 multiply(double s) {
        x*=s;
        y*=s;
        return new Vec2(x,y);
    }
    
    public double getX() {return x;}
    public double getY() {return y;}
    
    public void set(Vec2 vec) {
        this.x = vec.getX();
        this.y = vec.getY();
    }
    
    public void set(double x, double y) {
        this.x = x;
        this.y = y;
    }
    
    public void set(double angle, double mag, int i) {
        x = mag*Math.cos(angle);
        y = mag*Math.sin(angle);
    }
    
    public void setX(double x) {this.x = x;}
    public void setY(double y) {this.y = y;}
    
    public void print() {
        System.out.println("x: " + x + " y: " + y);
    }
    
    public void print(Vec2 v) {
        System.out.println("x: " + v.getX() + " y: " + v.getY());
    }
}
