/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dodgeballgame.Settings;

import dodgeballgame.GamePanel;

/**
 *
 * @author Sam
 */
public class GeneralSettings {
    
    String path = "D:/Users/Sam/Documents/Ideas/Dodgeball/Settings/GeneralSettings.txt";
    SettingsList settings;
    
    public GeneralSettings(){
        settings = new SettingsList(path);
    }
    
    public void apply() {
        musicOn();
        musicVolume();
    }
    
    // Music On - [0 or 1]
    private void musicOn() {
        double val = settings.getValue(0);
        if (val == 0d) GamePanel.soundManager.stopMusic();
        else GamePanel.soundManager.music();
    }
    
    // Music Volume - [0 to 100]
    private void musicVolume() {
        double val = settings.getValue(1);
        if (val<0) val = 0d;
        else if (val>100) val = 100d;
        val = val/3.0 - 100.0/3.0;
        GamePanel.soundManager.changeMusicVolume((int)val);
    }
    
}
