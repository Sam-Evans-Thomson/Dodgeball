/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dodgeballgame;

import dodgeballgame.Items.AddCatchAngle;
import dodgeballgame.Items.AddBall;
import dodgeballgame.Items.AddCatchReach;
import dodgeballgame.Items.AddHealth;
import dodgeballgame.Items.AddRunSpeed;
import dodgeballgame.Items.AddThrowSpeed;
import dodgeballgame.Items.TakeHealth;
import java.util.Random;

/**
 *
 * @author Sam
 */
public class ItemManager {
    
    public static double spawnChance = 1.0;
    
    Random rand = new Random();
    static double totalFreq;
    
    /*  0               1               2                   3
    0   healthFreq      speedFreq       catchAngleFreq      catchReachFreq
    1   throwSpeedFreq  takeHealth               _                   _
    2   _               _               _                   _
    3   _               _               _                   _
    */

    public static int[][] powerUpFreqs = new int[4][4];
    public static double[][] powerUpSeeds = new double[4][4];
    
    public ItemManager() {
        powerUpFreqs[0][0] = 5;
        powerUpFreqs[0][1] = 5;
        powerUpFreqs[0][2] = 5;
        powerUpFreqs[0][3] = 5;
        powerUpFreqs[1][0] = 5;
        powerUpFreqs[1][1] = 5;
        powerUpFreqs[1][2] = 0;
        powerUpFreqs[1][3] = 0;
        powerUpFreqs[2][0] = 0;
        powerUpFreqs[2][1] = 0;
        powerUpFreqs[2][2] = 0;
        powerUpFreqs[2][3] = 0;
        powerUpFreqs[3][0] = 0;
        powerUpFreqs[3][1] = 0;
        powerUpFreqs[3][2] = 0;
        powerUpFreqs[3][3] = 0;
        
        totalFreq = totalFreq();    
    }
    
    private static int totalFreq() {
        int count = 0;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                count += powerUpFreqs[i][j];
            }
        }
        return count;
    }
    
    private int sumFreq(int max) {
        int count = 0;
        int cnt = 0;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                while ( cnt < max) {
                    count += powerUpFreqs[i][j];
                    cnt++;
                }
            }
        }
        return count;
    }
    
    public static void incFreq(int x, int y, int val) {
        if ( x>-1 && x < 4 && y>-1 && y < 4 ) {
            powerUpFreqs[x][y] +=  val;
            powerUpFreqs[x][y] %= 6;
            totalFreq = totalFreq();
        }
    }
    
    public static void decFreq(int x, int y, int val) {
        if ( x>-1 && x < 4 && y>-1 && y < 4 ) {
            powerUpFreqs[x][y] -=  val;
            powerUpFreqs[x][y] %= 6;
            if(powerUpFreqs[x][y] < 0) {
                powerUpFreqs[x][y] = 5;
            }
            totalFreq = totalFreq();
        }
    }
        
    public void setFreq(int x, int y, int val) {
        if ( x>-1 && x < 4 && y>-1 && y < 4 ) {
            powerUpFreqs[x][y]=  val;
            totalFreq = totalFreq();
        }
    }
    
    private void addHealth(Vec2 pos) {
        GamePanel.itemArray.add(new AddHealth(pos));
    }

    private void addRunSpeed(Vec2 pos) {
        GamePanel.itemArray.add(new AddRunSpeed(pos));
    }

    private void addCatchAngle(Vec2 pos) {
        GamePanel.itemArray.add(new AddCatchAngle(pos));
    }
    
    private void addCatchReach(Vec2 pos) {
        GamePanel.itemArray.add(new AddCatchReach(pos));
    }
    
    private void addThrowSpeed(Vec2 pos) {
        GamePanel.itemArray.add(new AddThrowSpeed(pos));
    }
    
    private void takeHealth(Vec2 pos) {
        GamePanel.itemArray.add(new TakeHealth(pos));
    }
    
    public void addBall(int team) {
        double wBuffer = (GamePanel.arena.teamAreas[team][1].getX() - 50) - (GamePanel.arena.teamAreas[team][0].getX() + 50);
        double hBuffer = (GamePanel.arena.teamAreas[team][1].getY() - 50) - (GamePanel.arena.teamAreas[team][0].getY() + 50);
        
        double x = (GamePanel.arena.teamAreas[team][0].getX() + 50 + rand.nextDouble()*wBuffer);
        double y = (GamePanel.arena.teamAreas[team][0].getY() + 50 + rand.nextDouble()*hBuffer);
        Vec2 pos = new Vec2(x,y);
        GamePanel.itemArray.add(new AddBall(pos));
    }

    private void hitBoxShrink(Vec2 pos) {
    }

    public void addItem(int team) {
        double wBuffer = (GamePanel.arena.teamAreas[team][1].getX() - 50) - (GamePanel.arena.teamAreas[team][0].getX() + 50);
        double hBuffer = (GamePanel.arena.teamAreas[team][1].getY() - 50) - (GamePanel.arena.teamAreas[team][0].getY() + 50);
        
        double x = (GamePanel.arena.teamAreas[team][0].getX() + 50 + rand.nextDouble()*wBuffer);
        double y = (GamePanel.arena.teamAreas[team][0].getY() + 50 + rand.nextDouble()*hBuffer);
        Vec2 pos = new Vec2(x,y);

        double max = 0;
        double numAvailable = 0;
        double numTotal = 0;
        
        int val = 0;
        for (int i = 0; i<4; i++) {
            for (int j = 0; j<4; j++) {
                
                powerUpSeeds[i][j] = rand.nextDouble()*(double)powerUpFreqs[i][j];
                if (powerUpSeeds[i][j] != 0) {
                    numAvailable += 5;
                    numTotal += powerUpFreqs[i][j];
                }
                if (powerUpSeeds[i][j] > max) {
                    max = powerUpSeeds[i][j];
                    val = i*4 + j;
                }
            }
        }
 
        double seed = rand.nextDouble();
        
        if (spawnChance*numTotal/numAvailable > seed) {
            switch (val) {
            case 0 : addHealth(pos);
                break;
            case 1 : addRunSpeed(pos);
                break;
            case 2 : addCatchAngle(pos);
                break;
            case 3 : addCatchReach(pos);
                break;
            case 4 : addThrowSpeed(pos);
                break;
            case 5 : takeHealth(pos);
                break;
            case 6 : //addHealth(pos);
                break;
            case 7 : //addHealth(pos);
                break; 
            case 8 : //addHealth(pos);
                break;
            case 9 : //addHealth(pos);
                break;
            case 10 : //addHealth(pos);
                break;
            case 11 : //addHealth(pos);
                break; 
            case 12 : //addHealth(pos);
                break;
            case 13 : //addHealth(pos);
                break;
            case 14 : //addHealth(pos);
                break;
            case 15 : //addHealth(pos);
                break;     
            }
        }
    }   
}
