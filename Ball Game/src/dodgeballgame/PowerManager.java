/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dodgeballgame;

import dodgeballgame.Powers.*;
import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author Sam
 */
public class PowerManager {
    
    private final int NUM_POWERS = 4;
    public int[] powers = new int[NUM_POWERS];
    
    Random rand = new Random();
    
    ArrayList<Power> activePowers = new ArrayList();
    
    public PowerManager() {
        for (int i=0 ;i < NUM_POWERS; i++) powers[i] = 1;
        loadActivePowers();
    }
    
    public void loadActivePowers() {
        Vec2 vec = new Vec2(0,0);
        activePowers = new ArrayList();
        if (powers[0] == 1) activePowers.add(new SlowPower(vec));
        if (powers[1] == 1) activePowers.add(new LargeCatchPower(vec));
        if (powers[2] == 1) activePowers.add(new RandomPower(vec));
        if (powers[3] == 1) activePowers.add(new NoCatchPower(vec));
    }
    
    private void add(Power power) {
        GamePanel.powerArray.add(power);
    }
    
    public void addPower(int team) {
        double wBuffer = (GamePanel.arena.teamAreas[team][1].getX() - 50) - (GamePanel.arena.teamAreas[team][0].getX() + 50);
        double hBuffer = (GamePanel.arena.teamAreas[team][1].getY() - 50) - (GamePanel.arena.teamAreas[team][0].getY() + 50);
        
        double x = (GamePanel.arena.teamAreas[team][0].getX() + 50 + rand.nextDouble()*wBuffer);
        double y = (GamePanel.arena.teamAreas[team][0].getY() + 50 + rand.nextDouble()*hBuffer);
        Vec2 pos = new Vec2(x,y);
        
        add(makeRandom(pos));
    }
    
    public Power makeRandom(Vec2 pos) {
        int seed = rand.nextInt(powers.length);
        Power randomPower = activePowers.get(seed).copy();
        randomPower.setPos(pos);
        return randomPower;
    }
}
