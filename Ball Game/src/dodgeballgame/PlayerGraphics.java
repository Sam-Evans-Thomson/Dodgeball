/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dodgeballgame;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

/**
 *
 * @author Sam
 */
public class PlayerGraphics {
    public int H;
    public int W;
    
    private Timer catchTimer = new Timer();
    private double catchTime;
    private double catchTimeMax = 0.12;
    public int[][] catchGlow;
    public double catchGlowRadius = 160.0;
    private Color glowColor = new Color(255,255,200);
    
    private Timer powerUpTimer = new Timer();
    private double powerUpTime;
    private double powerUpTimeMax = 0.2;
    public int[][] powerUpGlow;
    public double powerUpGlowRadius = 200.0;
    private Color powerUpGlowColor;
    
    private Timer hitTimer = new Timer();
    private double hitTime;
    private double hitTimeMax;
    
    public PlayerGraphics(int W, int H) {
        this.W = W;
        this.H = H;
        catchTime = 1.0;
        catchTimer.init();
        powerUpTime = 1.0;
        powerUpTimer.init();
        hitTimer.init();
        hitTime = 1;
        hitTimeMax = Player.invincibleTime;
    }
    
    public Graphics2D catchGlow(Color c, Graphics2D g, Vec2 pos) {
        
        if (catchTime < catchTimeMax) {
            float opacity = (float)(catchTimeMax - catchTime)*0.8f/(float)catchTimeMax;
            g.setColor(glowColor);
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity));
            double size = 100.0 + (catchGlowRadius - 100)*catchTime/catchTimeMax;
            g.fillOval((int)(pos.getX()-size/2), (int)(pos.getY()-size/2),(int)size, (int)size);
            catchTime = catchTimer.getDifference();
        }
        
        return g;        
    }
    
    public Graphics2D powerUpGlow(Color c, Graphics2D g, Vec2 pos) {
        
        if (powerUpTime < powerUpTimeMax) {
            float opacity = (float)(powerUpTimeMax - powerUpTime)*0.8f/(float)powerUpTimeMax;
            g.setColor(powerUpGlowColor);
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity));
            double size = 100.0 + (powerUpGlowRadius - 100)*powerUpTime/powerUpTimeMax;
            g.fillOval((int)(pos.getX()-size/2), (int)(pos.getY()-size/2),(int)size, (int)size);
            powerUpTime = powerUpTimer.getDifference();
        }
        
        return g;        
    }
    
    public void setCatchGlow() {        
        catchTimer.refresh();
        catchTime = catchTimer.getDifference();
    }
    
    public void setPowerUpGlow(Color c) {
        powerUpGlowColor = c;
        powerUpTimer.refresh();
        powerUpTime = powerUpTimer.getDifference();
    }
    
    public boolean checkHit() {
        hitTime = hitTimer.getDifference();
        if(hitTime > hitTimeMax) {
            return true;
        } else return false;
    }
    
    public void setGetHit() {
        hitTimer.refresh();
        hitTime = hitTimer.getDifference();
    }
    
}
