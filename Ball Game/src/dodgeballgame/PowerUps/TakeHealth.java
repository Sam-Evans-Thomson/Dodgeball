/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dodgeballgame.PowerUps;

import dodgeballgame.GamePanel;
import dodgeballgame.Player;
import dodgeballgame.Vec2;
import java.awt.Color;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

/**
 *
 * @author Sam
 */
public class TakeHealth extends PowerUp {

    
    
    public TakeHealth(Vec2 pos) {
        super(pos);
        color = new Color(100,255,100);
        try {
            image = ImageIO.read(new File("Images/takeHealth.png"));
        } catch (IOException e) {
        }
    }
    
    @Override
    public void applyEffect(Player p) {
        if (p.health > 1) {
            p.health--;
            p.playerImage = p.playerImageB;
            p.pGraphics.setGetHit();
        }

        GamePanel.soundManager.takeHealth();
    }


}
