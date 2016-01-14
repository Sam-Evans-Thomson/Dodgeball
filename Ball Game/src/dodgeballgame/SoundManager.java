/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dodgeballgame;

import java.util.ArrayList;
/**
 *
 * @author Sam
 */
public class SoundManager {
    
    ArrayList<AudioPlayer> sounds;
    ArrayList<AudioPlayer> music;
    
    float soundVolume;
    float musicVolume;
    float quietMusicVolume;
    
    boolean soundIsOn;
    boolean musicIsOn;
    
    public SoundManager() {
        init();
    }
    
    private void init() {
        soundIsOn = true;
        musicIsOn = true;
        soundVolume = 0f;
        musicVolume = -20f;
        quietMusicVolume = -35f;
        
        sounds = new ArrayList<>();
        music = new ArrayList<>();
        
        sounds.add(new AudioPlayer("Sounds/ding0.wav"));    // vibrant ding
        sounds.add(new AudioPlayer("Sounds/addCatchAngle.wav"));    // techy sound
        sounds.add(new AudioPlayer("Sounds/addBall.wav"));
        sounds.add(new AudioPlayer("Sounds/catchBall.wav"));
        sounds.add(new AudioPlayer("Sounds/addCatchAngle.wav"));
        sounds.add(new AudioPlayer("Sounds/addRunSpeed.wav"));
        sounds.add(new AudioPlayer("Sounds/takehealth.wav"));
        sounds.add(new AudioPlayer("Sounds/menu0.wav"));    // note 1 - LOWEST
        sounds.add(new AudioPlayer("Sounds/menu1.wav"));    // note 2
        sounds.add(new AudioPlayer("Sounds/menu2.wav"));    // note 3
        sounds.add(new AudioPlayer("Sounds/menu3.wav"));    // note 4
        sounds.add(new AudioPlayer("Sounds/menu4.wav"));    // note 5
        sounds.add(new AudioPlayer("Sounds/menu5.wav"));    // note 6 - highest
        sounds.add(new AudioPlayer("Sounds/menu6.wav"));    // echo click
        sounds.add(new AudioPlayer("Sounds/menu7.wav"));    // back/undo sound
        sounds.add(new AudioPlayer("Sounds/menu8.wav"));    // short Click
        sounds.add(new AudioPlayer("Sounds/power.wav"));    // scale up
        
        music.add(new AudioPlayer("Sounds/fracture.wav"));

    }
    
    public void addHealth() {
        if(soundIsOn) sounds.get(0).play();
    }
    
    public void addCatchAngle() {
        if(soundIsOn) sounds.get(1).play();
    }
    
    public void addBall()  {
        if(soundIsOn) sounds.get(2).play();
    }
    
    public void catchBall() {
        if(soundIsOn) sounds.get(3).play();
    }
    
    public void addCatchReach() {
        if(soundIsOn) sounds.get(4).play();
    }
    
    public void addRunSpeed() {
        if(soundIsOn) sounds.get(5).play();
    }
    
    public void addThrowSpeed() {
        if(soundIsOn) sounds.get(5).play();
    }
    
    public void takeHealth() {
        if(soundIsOn) sounds.get(6).play();
    }
    
    public void menu(int i) {
        if(soundIsOn) sounds.get(7+i).play();
    }
    
    public void power() { if(soundIsOn) sounds.get(16).play();}
    
    public void music() {
        if (musicIsOn) {
           music.get(0).loop(); 
        }
    }
    
    public void stopMusic() {
        music.get(0).stop();
        musicIsOn = false;
    }
    
    public void changeSoundVolume(int vol) {
        sounds.stream().forEach((ap) -> {
            ap.changeVolume(vol);
        });
    }
    
    public void changeMusicVolume(int vol) {
        music.stream().forEach((ap) -> {
            ap.changeVolume(vol);
        });
    }
    
    public void stopAll() {
        music.stream().forEach((ap) -> {
            ap.stop();
        });
        sounds.stream().forEach((ap) -> {
            ap.stop();
        });
    }
    
    public void setSound(boolean bool) {
        soundIsOn = bool;
    }
    
    public void setMusic(boolean bool) {
        musicIsOn = bool;
    }
    
}
