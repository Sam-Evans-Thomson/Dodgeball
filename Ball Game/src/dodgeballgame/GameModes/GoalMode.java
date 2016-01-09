/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dodgeballgame.GameModes;

import dodgeballgame.GamePanel;
import dodgeballgame.Player.Player;
import dodgeballgame.Settings.Setting;

/**
 *
 * @author Sam
 */
public class GoalMode extends GameMode {

    
    
    public GoalMode(){
        super();
        
    }

    public GoalMode(String path) {
        super(path);
    }

    public GoalMode(GoalMode pm) {
        settings = pm.settings.copy();
    }
    
    @Override
    public void initDescription() {
        text = new String[4];
        text[0] = "- Win by earning the set amount of points before the other team.";
        text[1] = "- Earn points by scoring Goals.";
        text[2] = "- Getting hit will remove your catch region for a short time.";
        text[3] = "- Dying will give your balls and powerups to the other team.";
    }
    
    @Override
    public GoalMode copy() {
        return new GoalMode(this);
    }

    @Override
    public void apply() {
        startingHealth();
        startingBalls();
        wallBounceFactor();
        softBackWall();
        friendlyFire();
        winningScore();
        pointsPerGoal();
        goalsPerPower();
        killsPerPower();
        pointsPerPower();

        refreshValues();

        Player.pointsPerGoal = 1;
        GamePanel.arenaManager.goalsActive = true; 
    } 
    
    // Starting Health - [1 to 99]
    public void startingHealth() {
        int val = (int)settings.getDouble(0);
        Player.startHealth = val;
    }
    
    // Starting Balls - [1 to 99]
    public void startingBalls() {
        int val = (int)settings.getDouble(1);
        Player.startBalls = val;
    }
    
    // Wall Bounce Factor - [0.1 to 3.0]
    public void wallBounceFactor() {
        double val = settings.getDouble(2);
        GamePanel.arena.bounceFactor = val;
    }
    
    // Soft Back Wall - [0 or 1]
    public void softBackWall() {
        double val = settings.getDouble(3);
        GamePanel.arena.softBounceFactor = (1-0.5*val)*GamePanel.arena.bounceFactor;
    }
    
    // Friendly Fire - [0 or 1]
    public void friendlyFire() {
        boolean val = ((int)settings.getDouble(4)==1);
        GamePanel.friendlyFire = val;
    }
    
    // Winning Score - [0 to 99]
    public void winningScore() {
        int val = (int)settings.getDouble(5);
        GamePanel.winScore = val;
    }
    
    // Points Per Goal - [0 to 99]
    public void pointsPerGoal() {
        int val = (int)settings.getDouble(6);
        Player.pointsPerGoal = val;
    }

    public void goalsPerPower() {
        int val = (int)settings.getDouble(7);
        Player.goalsPerPower = val;
    }

    public void killsPerPower() {
        int val = (int)settings.getDouble(8);
        Player.killsPerPower = val;
    }

    public void pointsPerPower() {
        int val = (int)settings.getDouble(9);
        Player.pointsPerPower = val;
    }
}
