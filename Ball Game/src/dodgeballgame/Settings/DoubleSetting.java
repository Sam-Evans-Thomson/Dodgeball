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
public class DoubleSetting extends Setting {
    
    double value;
    
    public DoubleSetting(double val, String name) {
        value = val;
        this.name = name;
    }
    
    public DoubleSetting(double val, String name,double start, double end, double inc) {
        this.start = start;
        this.end = end;
        this.inc = inc;
        value = val;
        this.name = name;
    }
    
    public DoubleSetting(DoubleSetting set) {
        this.start = set.start;
        this.end = set.end;
        this.inc = set.inc;
        value = set.value;
        this.name = set.name;
    }
    
    @Override
    public DoubleSetting copy() {
        return new DoubleSetting(this);
    }
    
    @Override
    public String getValueString() {
        return "" + Math.round(value*1000000)/1000000.0;
    }
    
    @Override
    public void changeValue(int i) {
        moveThrough(i*inc);
    }
    
    private double moveThrough(double dist) {
        if (dist > end-start) return end;
        if (dist < start-end) return start;
        value += dist;
        if (value > end) value = start;
        if (value < start) value = end;
        return Math.round(value*1000000)/1000000.0;
    }
    
    public double getValue() {
        return value; 
    }
    
    @Override
    public double getDouble() {
        return value;
    }
    
    @Override
    public String toString() {
        return "DOUBLE" + " - " + name + " - " + getValue() + " - " + start + " - " + end + " - " + inc;
    }
}
