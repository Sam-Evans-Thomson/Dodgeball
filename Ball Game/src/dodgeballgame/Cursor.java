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
public class Cursor {
    
    public int x, y;
    public int xMax, yMax;
    
    public Cursor(int xMax, int yMax) {
        x = y = 0;
        this.xMax = xMax;
        this.yMax = yMax;
    }
    
    public void moveCursor(int x, int y) {
        if (x>1) x = 1;
        else if (x<-1) x = -1;
        if (y>1) y = 1;
        else if (y<-1) y = -1;
        
        this.x += x;
        this.y += y;
        if (this.x < 0) this.x = xMax - 1;
        if (this.y < 0) this.y = yMax - 1;
        this.x %= xMax;
        this.y %= yMax;
    }
    
    public int getX() {
        return x;
    }
    
    public int getY() {
        return y;
    }
    
}
