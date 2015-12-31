/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dodgeballgame.Items;

import dodgeballgame.GamePanel;
import dodgeballgame.Player.Player;
import dodgeballgame.Vec2;
import java.awt.Color;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 *
 * @author Sam
 */
public class AddHealth extends Item {

    
    
    public AddHealth(Vec2 pos) {
        super(pos);
        color = new Color(100,255,100);
        try {
            image = ImageIO.read(new File("Images/addHealth.png"));
        } catch (IOException e) {
        }
    }
    
    @Override
    public void applyEffect(Player p) {
        p.health++;
        GamePanel.soundManager.addHealth();
    }


}
