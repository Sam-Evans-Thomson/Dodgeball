/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dodgeballgame;

import java.io.File;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

/**
 *
 * @author Sam
 */
public class AudioPlayer {
    
    AudioInputStream ais;
    AudioInputStream dais;
    private Clip clip;
    
    public AudioPlayer(String s) {
        
        
        try {
            File file = new File(s);
            ais = AudioSystem.getAudioInputStream(
                    file);
        } catch (Exception e) {
            e.printStackTrace();
        }
        AudioFormat baseFormat = ais.getFormat();
        AudioFormat decodeFormat = new AudioFormat(
                AudioFormat.Encoding.PCM_SIGNED,
                baseFormat.getSampleRate(),
                16,
                baseFormat.getChannels(),
                baseFormat.getChannels() * 2,
                baseFormat.getSampleRate(),
                false);
        dais = AudioSystem.getAudioInputStream(
                decodeFormat, ais);
        try {
            clip = AudioSystem.getClip();
            clip.open(dais);
        } catch (Exception e) {
            
        }
    }
    
    public void changeVolume(int vol) {
        if (vol>6) vol = 6;
        FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        gainControl.setValue((float)vol);
    }
    
    public void play() {
        
        if(clip == null) {
            return;
        }
        stop(); 
        clip.setFramePosition(0);  
        clip.start();
    }
    
    public void loop() {
        if(clip == null) {
            return;
        }
        stop(); 
        clip.setFramePosition(0);  
        clip.setLoopPoints(0,-1);
        clip.loop(100);
    }
    
    public void pause() {
        
    }
    
    public void resume() {
        
    }
    
    public void stop() {
        clip.stop();
        clip.flush();
    }
    
    public void close() {
        stop();
        clip.close();
    }
}
