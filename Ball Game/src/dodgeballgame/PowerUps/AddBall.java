 /* To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dodgeballgame.PowerUps;

import dodgeballgame.GamePanel;
import dodgeballgame.ImageEditor;
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
public class AddBall extends PowerUp {
    
    BufferedImage ball;
    ImageEditor imageEditor;

    public AddBall(Vec2 pos) {
        super(pos);
        color = new Color(255,255,200);
        try {
            ball = ImageIO.read(new File("Images/Balls/ball.png"));
        } catch (IOException e) {
        }
    }
    
    @Override
    public void applyEffect(Player p) {
        p.numBalls++;
        GamePanel.soundManager.addBall();
    }

    
    @Override
    public void render(Graphics2D g) {
        imageEditor = new ImageEditor(ball);
        BufferedImage bi = imageEditor.scale(r/50);
        g.drawImage(bi, (int)(pos.getX()-r), (int)(pos.getY()-r), null);
    }
    
    @Override
    public void hitBall() {
        
    }
}