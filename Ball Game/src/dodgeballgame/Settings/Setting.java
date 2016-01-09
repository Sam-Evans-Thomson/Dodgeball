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

        public String name;
        public double start;
        public double end;
        public double inc;
        public boolean active;
        
        public void setLimits(double start, double end, double inc) {
            this.start = start;
            this.end = end;
            this.inc = inc;
            active = true;
        }
        
        public Setting copy() {
            return new Setting();
        }
        
        public void changeValue(int i) {
            
        }
        
        public String getValueString() {
            return "";
        }
        
        public double getDouble() {
            return 0d;
        }
        
        public String getName() {
            return name;
        }
        
        @Override
        public String toString() {
            return "";
        }

    }
