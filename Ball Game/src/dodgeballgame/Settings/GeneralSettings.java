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
    
    public int size() {
        return settings.size();
    }
    
    public Setting get(int i) {
        return settings.get(i);
    }
    
    public double getDouble(int i) {
        return settings.getDouble(i);
    }
    
    public String getName(int i) {
        return settings.getName(i);
    }
    
    public String getString(int i) {
        return settings.get(i).toString();
    }
    
    public String getValueString(int i) {
        return settings.get(i).getValueString();
    }
    
    public void changeValue(int setting, int amount) {
        settings.get(setting).changeValue(amount);
        apply();
    }
    
    public void apply() {
        musicOn();
        musicVolume();
        soundOn();
        soundVolume();
    }
    
    // Music On - [0 or 1]
    private void musicOn() {
        double val = settings.getDouble(0);
        if (val < 1) GamePanel.soundManager.stopMusic();
        else GamePanel.soundManager.setMusic(true);
    }
    
    // Music Volume - [0 to 100]
    private void musicVolume() {
        double val = settings.getDouble(1);
        if (val<0) val = 0d;
        else if (val>100) val = 100d;
        val = val/3.0 - 100.0/3.0;
        GamePanel.soundManager.changeMusicVolume((int)val);
    }
    
    // Music On - [0 or 1]
    private void soundOn() {
        double val = settings.getDouble(2);
        GamePanel.soundManager.setSound(val != 0d);
    }
    
    // Music Volume - [0 to 100]
    private void soundVolume() {
        double val = settings.getDouble(3);
        if (val<0) val = 0d;
        else if (val>100) val = 100d;
        val = val/3.0 - 100.0/3.0;
        GamePanel.soundManager.changeSoundVolume((int)val);
    }
}
