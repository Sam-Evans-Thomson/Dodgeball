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
public class StateComponent {
    
    public boolean active;
    public double[] values;
    public double[] origValues;
    
    public Timer timer;
    public double length;
    public double time;
    
    public StateComponent(double[] values, double length) {
        this.values = values;
        this.length = length;
        active = false;
        timer = new Timer();
    }
    
    public void apply() {
        active = true;
        timer = new Timer();
        timer.refresh();
        time = timer.getDifference();
    }
    
    public void setOrigValues(double[] origValues) {
        this.origValues = origValues;
    }
    
    public double getValue(int i) {
        return values[i];
    }
    
    public double getOrigValue(int i) {
        return origValues[i];
    }
    
    public boolean hasExpired() {
        time = timer.getDifference();
        return (active && time > length);
    }
    
}
