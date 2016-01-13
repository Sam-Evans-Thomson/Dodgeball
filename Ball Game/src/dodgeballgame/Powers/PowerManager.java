/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dodgeballgame.Powers;

import dodgeballgame.Arenas.ArenaManager;
import dodgeballgame.GamePanel;
import dodgeballgame.Vec2;
import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author Sam
 */
public class PowerManager {
    
    private final int NUM_POWERS = 16;
    public int[] powers = new int[NUM_POWERS];
    
    Random rand = new Random();
    
    ArrayList<Power> activePowers = new ArrayList();
    
    public PowerManager() {
        all();
    }
    
    public void all() {
        for (int i=0 ;i < NUM_POWERS; i++) powers[i] = 1;
        loadActivePowers();
    }
    
    public void randomize() {
        for (int i=0 ;i < NUM_POWERS; i++) powers[i] = rand.nextInt(2);
        loadActivePowers();
    }
    
    public void none() {
        for (int i=0 ;i < NUM_POWERS; i++) powers[i] = 0;
        loadActivePowers();
    }
    
    public void change(int power) {
        powers[power] = 1 - powers[power];
        loadActivePowers();
    }
    
    public void loadActivePowers() {
        Vec2 vec = new Vec2(0,0);
        activePowers = new ArrayList();
        if (powers[0] == 1) activePowers.add(new SlowPower(vec));
        if (powers[1] == 1) activePowers.add(new LargeCatchPower(vec));
        if (powers[2] == 1) activePowers.add(new RandomPower(vec));
        if (powers[3] == 1) activePowers.add(new NoCatchPower(vec));
        if (powers[4]== 1) activePowers.add(new AutoCatchPower(vec));
        if (powers[5]== 1) activePowers.add(new StealBallsPower(vec));
        if (powers[6]== 1) activePowers.add(new StealPowerPower(vec));
        if (powers[7]== 1) activePowers.add(new InvinciblePower(vec));
        if (powers[8]== 1) activePowers.add(new AimBotPower(vec));
    }
    
    private void add(Power power) {
        GamePanel.powerArray.add(power);
    }
    
    public void addPower(int team) {
        double wBuffer = (ArenaManager.arena.teamAreas[team][1].getX() - 50) - (ArenaManager.arena.teamAreas[team][0].getX() + 50);
        double hBuffer = (ArenaManager.arena.teamAreas[team][1].getY() - 50) - (ArenaManager.arena.teamAreas[team][0].getY() + 50);
        
        double x = (ArenaManager.arena.teamAreas[team][0].getX() + 50 + rand.nextDouble()*wBuffer);
        double y = (ArenaManager.arena.teamAreas[team][0].getY() + 50 + rand.nextDouble()*hBuffer);
        Vec2 pos = new Vec2(x,y);
        
        add(makeRandom(pos));
    }
    
    public Power makeRandom(Vec2 pos) {
        if (activePowers.size() < 1) {
            return null;
        } else {
            int seed = rand.nextInt(activePowers.size());
            Power randomPower = activePowers.get(seed).copy();
            randomPower.setPos(pos);
            return randomPower;
        }
    }
}
