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
public class BoolSetting extends Setting{
    
    boolean value;

    public BoolSetting(boolean val, String name) {
        value = val;
        this.name = name;
        setLimits(0d,1d,1d);
    }
    
    public BoolSetting(BoolSetting set) {
        this.value = set.value;
        this.name = set.name;
        setLimits(0d,1d,1d);
    }
      
    @Override
    public BoolSetting copy() {
        return new BoolSetting(this);
    }
    
    @Override
    public String getValueString() {
        return (value) ? "On" : "Off";
    }
    
    @Override
    public void changeValue(int i) {
        value = !value;
    }
    
    
    public boolean getValue() {
        return value;
    }
    
    @Override
    public double getDouble() {
        return (value) ? 1d : 0d;
    }
    
    @Override
    public String toString() {
        return "BOOL" + " - " + name + " - " + getValue() + " - " + start + " - " + end + " - " + inc;
    }
    

}
