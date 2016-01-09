/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dodgeballgame.Settings;

import dodgeballgame.FileManager.FileManager;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author Sam
 */
public class SettingsList {
    
    public ArrayList<Setting> settingsList = new ArrayList<>();
    public String path;
    public String type;
    
    public SettingsList() {
    }
    
    public SettingsList(String path) {
        this.path = path;
        read();
    }
    
    public SettingsList(SettingsList sList) {
        for (Setting set : sList.settingsList) settingsList.add(set.copy());
        if (path!= null) path = sList.path;
        this.type = sList.type;
    }
    
    public SettingsList copy() {
        return new SettingsList(this);
    }
    
    public void set(SettingsList list) {
        for (Setting set : list.settingsList) settingsList.add(set.copy());
        if (path!= null) path = list.path;
    }
    
    public void setPath(String path) {
        this.path = path;
    }
    
    public void add(Setting setting) {
        settingsList.add(setting);
    }
    
    public boolean isActive(int i){
        return settingsList.get(i).active;
    }
    
    public void setActive(int i, boolean b) {
        settingsList.get(i).active = b;
    }
    
    public Setting get(int i) {
        return settingsList.get(i);
    }
    
    public String getString(int i) {
        return settingsList.get(i).toString();
    }
    
    public String getValueString(int i) {
        return settingsList.get(i).getValueString();
    }
        
    public void changeValue(int setting, int amount) {
        settingsList.get(setting).changeValue(amount);
    }
    
    public double getDouble(int i) {
        return settingsList.get(i).getDouble();
    }
    
    public String getName(int i) {
        return settingsList.get(i).getName();
    }
    
    public SettingsList getActiveList() {
        SettingsList sl = new SettingsList();
        for(int i = 0; i < settingsList.size(); i++) {
            if (isActive(i)) sl.add(settingsList.get(i));
        }
        return sl;
    }
    
    public int size() {
        return settingsList.size();
    }
    
    public void read() {
        if (path!=null) {
            FileManager fm = new FileManager(path);
            try {
                String[] strArray = fm.openFile();
                type = strArray[0];
                for (int i = 1; i < strArray.length; i++) {
                    String s = strArray[i];
                    String[] setting = s.split(" - ");

                    if (setting[0].equals("BOOL")) {
                        add(new BoolSetting(
                                Boolean.parseBoolean(setting[2]), 
                                setting[1]));
                    } else if (setting[0].equals("DOUBLE")) {
                        add(new DoubleSetting(Double.parseDouble(setting[2]), 
                                setting[1], 
                                Double.parseDouble(setting[3]), 
                                Double.parseDouble(setting[4]), 
                                Double.parseDouble(setting[5])));
                    } else if (setting[0].equals("INT")) {
                        add(new IntSetting(Integer.parseInt(setting[2]),
                                setting[1],  
                                Double.parseDouble(setting[3]), 
                                Double.parseDouble(setting[4]), 
                                Double.parseDouble(setting[5])));
                    }
                }
            } catch (IOException e) {}
        } else {
            System.out.println("[READING] Path for settings list was not appropriate.");
        }
    }
    
    public void save() {
        if (path!=null) {
            FileManager fm = new FileManager(path);
            try {
                String[] settings = new String[settingsList.size()+1];
                settings[0] = type;
                int cnt = 1;
                for (Setting s : settingsList) {
                    settings[cnt] = s.toString();
                    cnt++;
                }
                fm.writeLine(settings);
                fm.close();
            } catch (IOException e) {}
        } else {
            System.out.println("[SAVING] Path for settings list was not appropriate.");
        }
        
    }
}
