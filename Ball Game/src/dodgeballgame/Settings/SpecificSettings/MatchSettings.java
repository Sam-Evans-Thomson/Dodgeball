/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dodgeballgame.Settings.SpecificSettings;

import dodgeballgame.Arenas.Arena;
import dodgeballgame.GamePanel;
import dodgeballgame.Player;
import dodgeballgame.ItemManager;
import dodgeballgame.Settings.Setting;
import dodgeballgame.Settings.SettingsList;

/**
 *
 * @author Sam
 */
public class MatchSettings extends SettingsList {
    
    public MatchSettings(){
        super();
    }
    
    public MatchSettings(SettingsList list) {
        super(list);
    }
    
    public MatchSettings(MatchSettings list) {
        super(list);
    }
    
    public MatchSettings(String path) {
        super(path);

        // number of players
        get(0).setLimits(2, GamePanel.numControllers, 1);
        changeValue(0,0);
    }
    
    @Override
    public void setPath(String path) {
         this.path = path;

        // number of players
        get(0).setLimits(2, GamePanel.numControllers, 1);
        changeValue(0,0);
    }

    public void apply() {
        numberOfPlayers();
        startingHealth();
        startingBalls();
        wallBounceFactor();
        softBackWall();
        friendlyFire();
        winningScore();
        itemSpawnChance();
        pointsPerGoal();
        goalsOnOff();
    }
    
    // Number of players - [2 to numControllers]
    private void numberOfPlayers() {
        int val = (int)getDouble(0);
        GamePanel.NUM_PLAYERS = val;
    }
    
    // Starting Health - [1 to 99]
    private void startingHealth() {
        int val = (int)getDouble(1);
        Player.startHealth = val;
    }
    
    // Starting Balls - [1 to 99]
    private void startingBalls() {
        int val = (int)getDouble(2);
        Player.startBalls = val;
    }
    
    // Wall Bounce Factor - [0.1 to 3.0]
    private void wallBounceFactor() {
        double val = getDouble(3);
        GamePanel.arena.bounceFactor = val;
    }
    
    // Soft Back Wall - [0 or 1]
    private void softBackWall() {
        double val = getDouble(4);
        GamePanel.arena.softBounceFactor = (1-0.5*val)*GamePanel.arena.bounceFactor;
    }
    
    // Friendly Fire - [0 or 1]
    private void friendlyFire() {
        boolean val = ((int)getDouble(5)==1);
        GamePanel.friendlyFire = val;
    }
    
    // Winning Score - [0 to 99]
    private void winningScore() {
        int val = (int)getDouble(6);
        GamePanel.winScore = val;
    }
    
    // Item Spawn Chance - [0 to 1.0]
    private void itemSpawnChance() {
        double val = getDouble(7);
        ItemManager.spawnChance = val;
    }
    
    // Points Per Goal - [0 to 99]
    private void pointsPerGoal() {
        int val = (int)getDouble(8);
        Player.pointsPerGoal = val;
    }
    
    // Goals On/Off - [0 or 1]
    private void goalsOnOff() {
        boolean val = ((int)getDouble(9)==1);
        Arena.goalsActive = val;
    }
}
