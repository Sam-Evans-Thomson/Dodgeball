/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dodgeballgame.GameModes;

import dodgeballgame.Settings.SettingsList;
import java.util.ArrayList;

/**
 *
 * @author Sam
 */
public class GameModeManager {
 
    public GameMode gameMode;
    public String defaultGoal = "D:/Users/Sam/Documents/Ideas/Dodgeball/Settings/DefaultSaves/DefaultGoal.txt";
    public String defaultPlayer = "D:/Users/Sam/Documents/Ideas/Dodgeball/Settings/DefaultSaves/DefaultPlayer.txt";
    public GoalMode goalMode = new GoalMode(defaultGoal);
    public PlayerMode playerMode = new PlayerMode(defaultPlayer);
    
    public ArrayList<GameMode> gameModes = new ArrayList();
    
    public GameModeManager(){
        gameMode = playerMode;
        gameModes.add(goalMode);
        gameModes.add(playerMode);
    }
    
    public void setMode(int i) {
        gameMode = gameModes.get(i);
        gameMode.apply();
    }
    
    public String getName() {
        return gameMode.type;
    }
    
    public String returnModeType(String path) {
        SettingsList sl = new SettingsList(path);
        return sl.type;
    }
    
    public GameMode loadGameMode(String path) {
        String modeType = returnModeType(path);
        
        switch (modeType) {
            case "GOAL" : return new GoalMode(path);
            case "PLAYER" : return new PlayerMode(path);
        }
        
        return new GameMode();
    }
}
