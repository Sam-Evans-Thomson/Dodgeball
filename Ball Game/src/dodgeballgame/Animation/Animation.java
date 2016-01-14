/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dodgeballgame.Animation;

import dodgeballgame.Timer;
import dodgeballgame.Vec2;
import java.awt.Graphics2D;

/**
 *
 * @author Sam
 */
public class Animation {
    
    public Timer timer;
    public double time;
    public double timeMax;
    
    public Animation() {
        timer = new Timer();
        timeMax = 1;
    }
    
    public Animation(double maxTime) {
        timeMax = maxTime;
        timer = new Timer();
    }
    
    public void setMaxTime(double maxTime) {
        timeMax = maxTime;
    }
    
    public void refresh() {
        timer.refresh();
        time = timer.getDifference();
    }
    
    public double getTime() {
        time = timer.getDifference();
        return time;
    }
    
    public double getRemainingTime() {
        time = timer.getDifference();
        if (timeMax > time) return timeMax - time;
        else return 0;
    }
    
    public double getPercentage() {
        time = timer.getDifference();
        return time/timeMax;
    }
    
    public boolean isActive() {
        time = timer.getDifference();
        return (time < timeMax);
    }
    
    public void render(Graphics2D g) {
        
    }
    
    public void render(Graphics2D g, Vec2 pos) {
        
    }
}
