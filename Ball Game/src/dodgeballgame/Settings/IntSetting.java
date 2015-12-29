/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dodgeballgame.Settings;

/**
 *
 * @author Sam
 */
public class IntSetting extends Setting {
    
    int value;
    
    public IntSetting(int val, String name) {
        value = val;
        this.name = name;
    }
    
    public IntSetting(int val, String name,double start, double end, double inc) {
        this.start = start;
        this.end = end;
        this.inc = inc;
        value = val;
        this.name = name;
    }
    
    @Override
    public String getValueString() {
        return "" + value;
    }
    
    @Override
    public void changeValue(int i) {
        moveThrough((int)(i*inc));
    }
    
    
    private double moveThrough(int dist) {
        int end2 = (int)end;
        int start2 = (int)start;
        if (dist > end2-start2) return end2;
        if (dist < start2-end2) return start2;
        value += dist;
        if (value > end2) value = start2;
        if (value < start2) value = end2;
        return value;
    }
    
    public int getValue() {
        return value; 
    }
    
    @Override
    public double getDouble() {
        return (double)value;
    }
    
    @Override
    public String toString() {
        return "INT" + " - " + name + " - " + getValue();
    }
}
