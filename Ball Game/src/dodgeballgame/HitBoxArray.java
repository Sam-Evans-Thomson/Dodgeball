/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dodgeballgame;

import java.util.ArrayList;

/**
 *
 * HitBoxArray needs to calculate when two hitboxes cross over.
 * HitboxArray needs to be able to hold multiple hitboxes (like many circles)
 * these hitboxes will move together when rotated or translated.
 */
public class HitBoxArray {
    
    public ArrayList<HitBox> hitBoxArray;
    
    public HitBoxArray() {
        hitBoxArray = new ArrayList<>();
    }
    
    public void add(HitBox hb) {
        hitBoxArray.add(hb);
    }
    
    public void add(HitBoxArray hbArray) {
        for (HitBox hb : hbArray.hitBoxArray) {
            hitBoxArray.add(hb);
        }
    }
    
    public void remove(HitBox hb) {
        hitBoxArray.remove(hb);
    }
    
    public void remove(HitBoxArray hbArray) {
        for (HitBox hb : hbArray.hitBoxArray) {
            hitBoxArray.remove(hb);
        }
    }
    
    public void addCircle(int x, int y, int r) {
        HitBox hb = new HitBox(x,y);
        hb.makeCircle(r);
        hitBoxArray.add(hb);
    }
    
    public void addRect(int x, int y, int w, int h) {
        HitBox hb = new HitBox(x,y);
        hb.makeRect(w,h);
        hitBoxArray.add(hb);
    }
    
    // move to these coordinates
    public void moveTo(int x, int y) {
        for (HitBox hb : hitBoxArray) {
            hb.moveTo(x,y);
        }
    }
    // move by this much
    public void moveBy(int x, int y) {
        for (HitBox hb : hitBoxArray) {
            hb.moveBy(x,y);
        }
    }
    
    public void scaleCircle(double scale) {
        for (HitBox hb : hitBoxArray) {
            hb.scaleCircle(scale);
        }
    }
    
    public void scaleRect(double scalex, double scaley) {
        for (HitBox hb : hitBoxArray) {
            hb.scaleRect(scalex,scaley);
        }
    }
    
    public boolean collision(HitBox otherHB) {
        if (hitBoxArray.stream().anyMatch((hb) -> (hb.collision(otherHB)))) {
            return true;
        }
        return false;
    }
    
    public boolean collisionPoint(int x, int y) {
        for (HitBox hb : hitBoxArray) {
            return hb.collisionPoint(x,y);
        }
        return false;
    }
    
}
