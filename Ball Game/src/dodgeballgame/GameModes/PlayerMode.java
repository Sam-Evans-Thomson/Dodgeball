/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dodgeballgame.GameModes;

import dodgeballgame.GamePanel;
import dodgeballgame.Player.Player;

/**
 *
 * @author Sam
 */
public class PlayerMode extends GameMode{
    
    public PlayerMode() {
        super();
    }
    
    public PlayerMode(String path) {
        super(path);
    }

    @Override
    public void apply() {
        
        Player.pointsPerHit = 1;
        Player.pointsPerGoal = 0;
        GamePanel.arena.goalsActive = false;

        numberOfPlayers();
        startingHealth();
        startingBalls();
        wallBounceFactor();
        softBackWall();
        friendlyFire();
        winningScore();
        killsPerPower();
        pointsPerPower();
    }
    
    // Number of players - [2 to numControllers]
    public void numberOfPlayers() {
        int val = (int)getDouble(0);
        GamePanel.numPlayers = val;
    }
    
    // Starting Health - [1 to 99]
    public void startingHealth() {
        int val = (int)getDouble(1);
        Player.startHealth = val;
    }
    
    // Starting Balls - [1 to 99]
    public void startingBalls() {
        int val = (int)getDouble(2);
        Player.startBalls = val;
    }
    
    // Wall Bounce Factor - [0.1 to 3.0]
    public void wallBounceFactor() {
        double val = getDouble(3);
        GamePanel.arena.bounceFactor = val;
    }
    
    // Soft Back Wall - [0 or 1]
    public void softBackWall() {
        double val = getDouble(4);
        GamePanel.arena.softBounceFactor = (1-0.5*val)*GamePanel.arena.bounceFactor;
    }
    
    // Friendly Fire - [0 or 1]
    public void friendlyFire() {
        boolean val = ((int)getDouble(5)==1);
        GamePanel.friendlyFire = val;
    }
    
    // Winning Score - [0 to 99]
    public void winningScore() {
        int val = (int)getDouble(6);
        GamePanel.winScore = val;
    }

    public void killsPerPower() {
        int val = (int)getDouble(7);
        Player.killsPerPower = val;
    }

    public void pointsPerPower() {
        int val = (int)getDouble(8);
        Player.pointsPerPower = val;
    }
}
