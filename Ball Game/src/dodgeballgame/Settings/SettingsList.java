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
    public int length;
    public String path;
    
    public SettingsList() {
        length = 0;
    }
    
    public SettingsList(String path) {
        this.path = path;
        read();
    }
    
    public SettingsList(SettingsList sList) {
        settingsList = sList.settingsList;
        if (path!= null) path = sList.path;
        length = sList.length;
    }
    
    public void set(SettingsList list) {
        settingsList = list.settingsList;
        if (path!= null) path = list.path;
        length = list.length;
    }
    
    public void setPath(String path) {
        this.path = path;
    }
    
    public void add(Setting setting) {
        settingsList.add(setting);
        length++;
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
    
    public int size() {
        return settingsList.size();
    }
    
    public void read() {
        if (path!=null) {
            FileManager fm = new FileManager(path);
            try {
                String[] strArray = fm.openFile();
                for (String s : strArray) {
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
                String[] settings = new String[settingsList.size()];
                int cnt = 0;
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
