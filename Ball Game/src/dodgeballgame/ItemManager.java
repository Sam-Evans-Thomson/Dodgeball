/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dodgeballgame;

import dodgeballgame.Items.*;
import java.util.Random;

/**
 *
 * @author Sam
 */
public class ItemManager {
    
    public static double spawnChance = 1.0;
    
    public static int[] spawner;
    
    Random rand = new Random();
    static double totalFreq;

    public static int[][] powerUpFreqs = new int[4][4];
    public static double[][] powerUpSeeds = new double[4][4];
    
    public ItemManager() {
        powerUpFreqs[0][0] = 5;
        powerUpFreqs[0][1] = 5;
        powerUpFreqs[0][2] = 5;
        powerUpFreqs[0][3] = 5;
        powerUpFreqs[1][0] = 0;
        powerUpFreqs[1][1] = 0;
        powerUpFreqs[1][2] = 0;
        powerUpFreqs[1][3] = 0;
        powerUpFreqs[2][0] = 5;
        powerUpFreqs[2][1] = 5;
        powerUpFreqs[2][2] = 5;
        powerUpFreqs[2][3] = 5;
        powerUpFreqs[3][0] = 0;
        powerUpFreqs[3][1] = 0;
        powerUpFreqs[3][2] = 0;
        powerUpFreqs[3][3] = 0;

        refreshSpawner();
    }
    
    public static void randomize() {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                Random rand = new Random();
                powerUpFreqs[i][j] = rand.nextInt(5);
            }
        }
        refreshSpawner();
    }
    
    public static void none() {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                powerUpFreqs[i][j] = 0;
            }
        }
        refreshSpawner();
    }
    
    public static void all() {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                powerUpFreqs[i][j] = 5;
            }
        }
        refreshSpawner();
    }
    
    private static void refreshSpawner() {
        spawner = new int[totalFreq()];
        
        int cnt = 0;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                for (int k = 0; k < powerUpFreqs[i][j]; k ++) {
                    spawner[cnt] = j + 4*i;
                    cnt++;
                }
            }
        }
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
        refreshSpawner();
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
        refreshSpawner();
    }
        
    public void setFreq(int x, int y, int val) {
        if ( x>-1 && x < 4 && y>-1 && y < 4 ) {
            powerUpFreqs[x][y]=  val;
            totalFreq = totalFreq();
        }
        refreshSpawner();
    }

    public void addBall(int team) {
        GamePanel.itemArray.add(makeBall(team));
    }
    
    public AddBall makeBall(int team) {
        double wBuffer = (GamePanel.arena.teamAreas[team][1].getX() - 50) - (GamePanel.arena.teamAreas[team][0].getX() + 50);
        double hBuffer = (GamePanel.arena.teamAreas[team][1].getY() - 50) - (GamePanel.arena.teamAreas[team][0].getY() + 50);
        
        double x = (GamePanel.arena.teamAreas[team][0].getX() + 50 + rand.nextDouble()*wBuffer);
        double y = (GamePanel.arena.teamAreas[team][0].getY() + 50 + rand.nextDouble()*hBuffer);
        Vec2 pos = new Vec2(x,y);
        return new AddBall(pos);
    }
    
    public Item getOpposite(Item i, int team) {
        
        if (i instanceof AddCatchAngle) return new TakeCatchAngle(i.pos);
        else if (i instanceof TakeCatchAngle) return new AddCatchAngle(i.pos);
        else if (i instanceof AddCatchRadius) return new TakeCatchRadius(i.pos);
        else if (i instanceof TakeCatchRadius) return new AddCatchRadius(i.pos);
        else if (i instanceof AddHealth) return new TakeHealth(i.pos);
        else if (i instanceof TakeHealth) return new AddHealth(i.pos);
        else if (i instanceof AddPoint) return new TakePoint(i.pos);
        else if (i instanceof TakePoint) return new AddPoint(i.pos);
        else if (i instanceof AddRunSpeed) return new TakeRunSpeed(i.pos);
        else if (i instanceof TakeRunSpeed) return new AddRunSpeed(i.pos);
        else if (i instanceof AddThrowSpeed) return new TakeThrowSpeed(i.pos);
        else if (i instanceof TakeThrowSpeed) return new AddThrowSpeed(i.pos);
        else if (i instanceof MoreBalls) return new LessBalls(i.pos);
        else if (i instanceof LessBalls) return new MoreBalls(i.pos);
        else if (i instanceof RandomItem) return new Death(i.pos);
        else if (i instanceof Death) return new RandomItem(i.pos);
        else if (i instanceof AddBall) return makeBall(team);
        else return null;
    }

    public Item makeItem(int team) {
        
        int seed = rand.nextInt(spawner.length);
        Vec2 pos = new Vec2(0,0);

        switch (spawner[seed]) {
        case 0 : return new AddHealth(pos);
        case 1 : return new AddRunSpeed(pos);
        case 2 : return new AddCatchAngle(pos);
        case 3 : return new AddCatchRadius(pos);
        case 4 : return new TakeHealth(pos);
        case 5 : return new TakeRunSpeed(pos);
        case 6 : return new TakeCatchAngle(pos);
        case 7 : return new TakeCatchRadius(pos);
        case 8 : return new AddThrowSpeed(pos);
        case 9 : return new AddPoint(pos);
        case 10 : return new MoreBalls(pos);  
        case 11 : return new RandomItem(pos);
        case 12 : return new TakeThrowSpeed(pos);
        case 13 : return new TakePoint(pos);
        case 14 : return new LessBalls(pos);
        case 15 : return new Death(pos);   
        }

        return new AddHealth(pos);
    } 
    

    public void addItem(int team) {
        double wBuffer = (GamePanel.arena.teamAreas[team][1].getX() - 50) - (GamePanel.arena.teamAreas[team][0].getX() + 50);
        double hBuffer = (GamePanel.arena.teamAreas[team][1].getY() - 50) - (GamePanel.arena.teamAreas[team][0].getY() + 50);
        
        double x = (GamePanel.arena.teamAreas[team][0].getX() + 50 + rand.nextDouble()*wBuffer);
        double y = (GamePanel.arena.teamAreas[team][0].getY() + 50 + rand.nextDouble()*hBuffer);
        Vec2 pos = new Vec2(x,y);

        Item newItem = makeItem(team);
        newItem.pos = pos;
        newItem.hb.pos = pos;
        
        GamePanel.itemArray.add(newItem);
    }   
}
