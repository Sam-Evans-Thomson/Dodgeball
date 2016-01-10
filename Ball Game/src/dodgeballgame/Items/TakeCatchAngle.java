 /* To change this license header, choose License Headers in Project Properties.
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
public class TakeCatchAngle extends Item {
    

    public TakeCatchAngle(Vec2 pos) {
        
        super(pos);
        color = new Color(255,0,0);
        try {
            image = ImageIO.read(new File("Images/Items/catchAngleDown.png"));
        } catch (IOException e) {
        }
        image = Tools.sizeImage(image, r);
    }
    
    @Override
    public void applyEffect(Player p) {
        GamePanel.soundManager.addCatchAngle();
        p.catchAngle -= Math.PI/11.0;
        if (p.catchAngle < 0) p.catchAngle = 0;
    }

}