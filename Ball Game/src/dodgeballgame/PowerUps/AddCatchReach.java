/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dodgeballgame.PowerUps;

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
public class AddCatchReach extends PowerUp {
    
    public AddCatchReach(Vec2 pos) {    
        super(pos);
        color = new Color(200,200,0);
        try {
            image = ImageIO.read(new File("Images/addCatchReach.png"));
        } catch (IOException e) {
        }
    }
    
    @Override
    public void applyEffect(Player p) {
        GamePanel.soundManager.addCatchReach();
        p.radius += 10;
    }

}
