/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dodgeballgame.GameModes;

import dodgeballgame.GamePanel;
import dodgeballgame.Settings.SettingsList;

/**
 *
 * @author Sam
 */
public class GameMode extends SettingsList {
    
    public GameMode(){
        super();
    }
    
    public GameMode(SettingsList list) {
        super(list);
    }
    
    public GameMode(String path) {
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

    }
}
