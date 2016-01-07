/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dodgeballgame.Menus;

import java.awt.Graphics2D;


/**
 *
 * @author Sam
 */
public class MenuManager {
    
    public StartMatchSettingsMenu startMatchSettingsMenu = new StartMatchSettingsMenu();
    public Menu menu;
    public StartMenu startMenu = new StartMenu();
    public LoadMenu loadMenu = new LoadMenu();
    public ItemMenu itemMenu = new ItemMenu();
    public WinScreen winScreen = new WinScreen();
    public ArenaMenu arenaMenu = new ArenaMenu();
    public CharacterMenu characterMenu = new CharacterMenu();
    public PowerMenu powerMenu = new PowerMenu();
    public MatchSettingsMenu matchSettingsMenu = new MatchSettingsMenu();
    public GeneralSettingsMenu generalSettingsMenu = new GeneralSettingsMenu();
    
    public GameModeMenu gameModeMenu = new GameModeMenu();

    public MenuManager() {
        menu = startMenu;
    }
    
    public void update() {
        menu.update();
    }
    
    public void render(Graphics2D g){
        menu.render(g);
    }
    
    public void changeMenu(String s) {
        switch (s) {
            case "ARENA" : menu = arenaMenu;
                break;
            case "START" : menu = startMenu;
                break;    
            case "LOAD" : menu = loadMenu;
                break;
            case "ITEM" : menu = itemMenu;
                break; 
            case "WIN" : menu = winScreen;
                break;
            case "CHARACTER" : menu = characterMenu;
                break; 
            case "POWER" : menu = powerMenu;
                break;
            case "MATCH_SETTINGS" : menu = matchSettingsMenu;
                break; 
            case "GENERAL_SETTINGS" : menu = generalSettingsMenu;
                break; 
            case "START_MATCH_SETTINGS" : menu = startMatchSettingsMenu;
                break;
            case "GAME_MODE" : menu = gameModeMenu;
                break;              
        }
    }
}
