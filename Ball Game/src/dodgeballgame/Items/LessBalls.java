/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dodgeballgame.Items;

import dodgeballgame.GamePanel;
import dodgeballgame.Player.Player;
import dodgeballgame.Tools;
import dodgeballgame.Vec2;
import java.awt.Color;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 *
 * @author Sam
 */
public class LessBalls extends Item {
    
    public LessBalls(Vec2 pos) {    
        super(pos);
        color = new Color(140,220,255);
        try {
            image = ImageIO.read(new File("Images/Items/ballsDown.png"));
        } catch (IOException e) {
        }
        image = Tools.sizeImage(image, r);
    }
    
    @Override
    public void applyEffect(Player p) {
        GamePanel.soundManager.addThrowSpeed();
        p.numBalls--;
        if (p.numBalls < 0) p.numBalls = 0;
    }

}
