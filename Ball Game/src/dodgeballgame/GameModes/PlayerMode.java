/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dodgeballgame.GameModes;

import dodgeballgame.Arenas.ArenaManager;
import dodgeballgame.GamePanel;
import dodgeballgame.Player.Player;

/**
 *
 * @author Sam
 */
public class PlayerMode extends GameMode{
    
    public PlayerMode() {
        super();
        initDescription();
    }
    
    public PlayerMode(String path) {
        super(path);
    }
    
    public PlayerMode(PlayerMode pm) {
        settings = pm.settings.copy();
    }
    
    @Override
    public void initDescription() {
        text = new String[4];
        text[0] = "- Win by earning the set amount of points before the other team.";
        text[1] = "- Earn points by hitting the other team.";
        text[2] = "- Getting hit will make you intangible for a short time.";
        text[3] = "- Dying will give your balls and powerups to the other team.";
    }
    
    @Override
    public PlayerMode copy(){
        return new PlayerMode(this);
    }

    @Override
    public void apply() {
        startingHealth();
        startingBalls();
        wallBounceFactor();
        softBackWall();
        friendlyFire();
        winningScore();
        killsPerPower();
        pointsPerPower();
        
        refreshValues();
        
        Player.pointsPerHit = 1;
        GamePanel.arenaManager.goalsActive = false;
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
        ArenaManager.arena.bounceFactor = val;
    }
    
    // Soft Back Wall - [0 or 1]
    public void softBackWall() {
        double val = settings.getDouble(3);
        ArenaManager.arena.softBounceFactor = (1-0.5*val)*ArenaManager.arena.bounceFactor;
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

    public void killsPerPower() {
        int val = (int)settings.getDouble(6);
        Player.killsPerPower = val;
    }

    public void pointsPerPower() {
        int val = (int)settings.getDouble(7);
        Player.pointsPerPower = val;
    }
}
