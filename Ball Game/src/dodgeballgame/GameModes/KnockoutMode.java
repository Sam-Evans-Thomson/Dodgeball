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
public class KnockoutMode extends GameMode{
    
    public KnockoutMode() {
        super();
        initDescription();
    }
    
    @Override
    public void initDescription() {
        text = new String[4];
        text[0] = "- Win by killing all members of the other team.";
        text[1] = "- Earn points by hitting the other team.";
        text[2] = "- Getting hit will make you intangible for a short time.";
        text[3] = "- Dying will make you become a ghost.";
    }
    
    public KnockoutMode(String path) {
        super(path);
    }
    
    public KnockoutMode(KnockoutMode pm) {
        settings = pm.settings.copy();
    }
    
    @Override
    public KnockoutMode copy(){
        return new KnockoutMode(this);
    }

    @Override
    public void apply() {
        startingHealth();
        startingBalls();
        wallBounceFactor();
        softBackWall();
        friendlyFire();
        killsPerPower();
        pointsPerPower();
        playerLives();
        
        refreshValues();

        Player.pointsPerDeath = -1;
        Player.knockoutOn = true;
        
        GamePanel.team1Score = 0;
        GamePanel.team2Score = 0;
        GamePanel.winScore = 0;
        for (Player p : GamePanel.playerArray) {
            if (p.team == 0) GamePanel.team1Score+=Player.startingLives;
            else GamePanel.team2Score+=Player.startingLives;
        }
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

    public void killsPerPower() {
        int val = (int)settings.getDouble(5);
        Player.killsPerPower = val;
    }

    public void pointsPerPower() {
        int val = (int)settings.getDouble(6);
        Player.pointsPerPower = val;
    }
    
    public void playerLives() {
        int val = (int)settings.getDouble(7);
        Player.startingLives = val;
        for (Player p : GamePanel.playerArray) p.lives = val;
    }
}
