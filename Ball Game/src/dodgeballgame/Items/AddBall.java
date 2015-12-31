 /* To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dodgeballgame.Items;

import dodgeballgame.GamePanel;
import dodgeballgame.ImageEditor;
import dodgeballgame.Player.Player;
import dodgeballgame.Vec2;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 *
 * @author Sam
 */
public class AddBall extends Item {

    public AddBall(Vec2 pos) {
        super(pos);
        color = new Color(255,255,200);
        try {
            image = ImageIO.read(new File("Images/Balls/ball.png"));
        } catch (IOException e) {
        }
    }
    
    @Override
    public void applyEffect(Player p) {
        p.numBalls++;
        GamePanel.soundManager.addBall();
        p.catchTimer.refresh();
    }
    
    @Override
    public void hitBall() {
        
    }
}