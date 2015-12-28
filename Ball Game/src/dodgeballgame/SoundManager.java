/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dodgeballgame;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;
import java.util.ArrayList;
import javax.sound.sampled.FloatControl;
import sun.audio.AudioStream;
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
    
    public SoundManager() {
        init();
    }
    
    private void init() {
        soundVolume = 0f;
        musicVolume = -20f;
        quietMusicVolume = -35f;
        
        sounds = new ArrayList<>();
        music = new ArrayList<>();
        
        sounds.add(new AudioPlayer("Sounds/ding0.wav"));
        sounds.add(new AudioPlayer("Sounds/addCatchAngle.wav"));
        sounds.add(new AudioPlayer("Sounds/addBall.wav"));
        sounds.add(new AudioPlayer("Sounds/catchBall.wav"));
        sounds.add(new AudioPlayer("Sounds/addCatchAngle.wav"));
        sounds.add(new AudioPlayer("Sounds/addRunSpeed.wav"));
        sounds.add(new AudioPlayer("Sounds/takehealth.wav"));
        sounds.add(new AudioPlayer("Sounds/menu0.wav"));
        sounds.add(new AudioPlayer("Sounds/menu1.wav"));
        sounds.add(new AudioPlayer("Sounds/menu2.wav"));
        sounds.add(new AudioPlayer("Sounds/menu3.wav"));
        sounds.add(new AudioPlayer("Sounds/menu4.wav"));
        sounds.add(new AudioPlayer("Sounds/menu5.wav"));
        sounds.add(new AudioPlayer("Sounds/menu6.wav"));
        sounds.add(new AudioPlayer("Sounds/menu7.wav"));
        sounds.add(new AudioPlayer("Sounds/menu8.wav"));
        
        music.add(new AudioPlayer("Sounds/fracture.wav"));

    }
    
    public void addHealth() {
        sounds.get(0).play();
    }
    
    public void addCatchAngle() {
        sounds.get(1).play();
    }
    
    public void addBall()  {
        sounds.get(2).play();
    }
    
    public void catchBall() {
        sounds.get(3).play();
    }
    
    public void addCatchReach() {
        sounds.get(4).play();
    }
    
    public void addRunSpeed() {
        sounds.get(5).play();
    }
    
    public void addThrowSpeed() {
        sounds.get(5).play();
    }
    
    public void takeHealth() {
        sounds.get(6).play();
    }
    
    public void menu(int i) {
        sounds.get(7+i).play();
    }
    
    public void music() {
        music.get(0).changeVolume(-10);
        music.get(0).loop();
    }
    
    public void stopMusic() {
        music.get(0).pause();
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
    
}
