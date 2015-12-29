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
    
    private ArrayList<Setting> settingsList = new ArrayList<>();
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
    
    public double getDouble(int i) {
        return settingsList.get(i).getDouble();
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
                for (Setting s : settingsList) {
                    fm.writeLine(s.toString());
                }
            } catch (IOException e) {}
        } else {
            System.out.println("[SAVING] Path for settings list was not appropriate.");
        }
    }
    
}
