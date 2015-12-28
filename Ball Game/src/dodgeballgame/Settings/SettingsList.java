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
    
    private ArrayList<Setting> settingsList;
    private int length;
    private String path;
    
    public SettingsList() {
        length = 0;
    }
    
    public SettingsList(String path) {
        this.path = path;
        read();
    }
    
    public void setPath(String path) {
        this.path = path;
        read();
    }
    
    public void add(Setting setting) {
        settingsList.add(setting);
        length++;
    }
    
    public Setting get(int i) {
        return settingsList.get(i);
    }
    
    public double getValue(int i) {
        return settingsList.get(i).getValue();
    }
    
    public String getName(int i) {
        return settingsList.get(i).getName();
    }
    
    public int size() {
        return settingsList.size();
    }
    
    private void read() {
        if (path!=null) {
            FileManager fm = new FileManager(path);
            try {
                String[] strArray = fm.openFile();
                for (String s : strArray) {
                    String[] setting = s.split(" - ");
                    add(new Setting(Double.parseDouble(setting[1]),setting[0]));
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
                for (Setting s : settingsList) {
                    fm.writeLine(s.toString());
                }
            } catch (IOException e) {}
        } else {
            System.out.println("[SAVING] Path for settings list was not appropriate.");
        }
    }
    
}
