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
public class Setting {
        
        double val;
        String name;
        
        public Setting(double val, String name) {
            this.val = val;
            this.name = name;
        }
        
        public Setting(int val, String name) {
            this.val = (int)val;
            this.name = name;
        }
        
        public Setting(boolean val, String name) {
            this.val = (val) ? 1d : 0d;
            this.name = name;
        }
        
        public double getValue() {
            return val;
        }
        
        public String getName() {
            return name;
        }
        
        @Override
        public String toString() {
            return (name + " - " + val);
        }
    }
