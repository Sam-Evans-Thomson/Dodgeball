/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dodgeballgame.Settings.SpecificSettings;

import dodgeballgame.Settings.SettingsList;

/**
 *
 * @author Sam
 */
public class ItemSettings extends SettingsList{
    
    public ItemSettings(){
        super();
    }
    
    public ItemSettings(SettingsList sList) {
        super(sList);
    }
    
    public ItemSettings(ItemSettings list) {
        super(list);
    }
    
    public ItemSettings(String path) {
        super(path);
    }
    
    public void set(ItemSettings mSet) {
        settingsList = mSet.settingsList;
        if (path!= null) path = mSet.path;
        length = mSet.length;
    }

    public void apply() {
        
    }
    
}
