/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dodgeballgame.GameModes;

import dodgeballgame.GamePanel;
import dodgeballgame.Player.Player;
import dodgeballgame.Settings.SettingsList;

/**
 *
 * @author Sam
 */
public class GameMode {
    
    public SettingsList settings;
    public String[] text;
    
    public GameMode(){
        initDescription();
    }
    
    public GameMode(String path) {
        settings = new SettingsList(path);
        initDescription();
    }
    
    public GameMode(GameMode gm) {
        settings = gm.settings.copy();
        initDescription();
    }
    
    public void initDescription() {
        
    }
        
    public void refreshValues() {
        Player.pointsPerHit = 0;
        Player.pointsPerKill = 0;
        Player.pointsPerGoal = 0;
        Player.pointsPerDeath = 0;
        Player.knockoutOn = false;
        GamePanel.arenaManager.goalsActive = false;
    }
    
    public GameMode copy() {
        return new GameMode(this);
    }

    public void setPath(String path) {
        settings.setPath(path);
    }

    public void apply() {

    }
}
