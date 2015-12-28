 /* To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dodgeballgame.PowerUps;

import dodgeballgame.GamePanel;
import dodgeballgame.Player;
import dodgeballgame.Vec2;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
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
public class AddCatchAngle extends PowerUp {
    

    public AddCatchAngle(Vec2 pos) {
        
        super(pos);
        color = new Color(200,200,0);
        try {
            image = ImageIO.read(new File("Images/addCatchAngle.png"));
        } catch (IOException e) {
        }
    }
    
    @Override
    public void applyEffect(Player p) {
        GamePanel.soundManager.addCatchAngle();
        p.catchAngle += Math.PI/11.0;
        if (p.catchAngle > 2*Math.PI) p.catchAngle = 2*Math.PI;
    }

}