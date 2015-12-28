/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dodgeballgame.PowerUps;

import dodgeballgame.GamePanel;
import dodgeballgame.Player;
import dodgeballgame.ImageEditor;
import dodgeballgame.Vec2;
import java.awt.Color;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 *
 * @author Sam
 */
public class AddRunSpeed extends PowerUp {
    
    public AddRunSpeed(Vec2 pos) {    
        super(pos);
        color = new Color(140,220,255);
        try {
            image = ImageIO.read(new File("Images/addRunSpeed.png"));
        } catch (IOException e) {
        }
    }
    
    @Override
    public void applyEffect(Player p) {
        GamePanel.soundManager.addRunSpeed();
        p.maxSpeed += 30;
    }

}
