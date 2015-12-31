/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dodgeballgame;

import dodgeballgame.Powers.*;
import java.util.Random;

/**
 *
 * @author Sam
 */
public class PowerManager {
    
    private final int NUM_POWERS = 2;
    
    public PowerManager() {

    }
    
    public void addSlowed(Vec2 pos) {
        GamePanel.powerArray.add(new SlowPower(pos));
    }
    
    public void addLargeCatch(Vec2 pos) {
        GamePanel.powerArray.add(new LargeCatchPower(pos));
    }
    
    public void addRandom(Vec2 pos) {
        Random rand = new Random();
        int seed = rand.nextInt(NUM_POWERS);
        
        switch (seed) {
            case 0 : addSlowed(pos);
                break;
            case 1 : addLargeCatch(pos);
        }
    }
}
