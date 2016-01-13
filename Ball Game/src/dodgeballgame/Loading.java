/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dodgeballgame;

import dodgeballgame.Player.Player;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 *
 * @author Sam
 */
public class Loading {

    
    ///////////////////////////////////////////////////////////////////////////
    // Images
    
    // ball
    
    public static BufferedImage ball, ball0, ball1;
    public static ImageEditor imageEditor;
    public static double imageWidth;
    public static double r = 15;
    
    public static Player closestPlayer = new Player(0,0,10000,10000);
    
    public Loading() {
        
        // Ball
        try {
            ball = ImageIO.read(new File("Images/Balls/ball.png"));
            ball0 = ImageIO.read(new File("Images/Balls/ball0.png"));
            ball1 = ImageIO.read(new File("Images/Balls/ball1.png"));
        } catch (IOException e) {
        }
        
        imageWidth = ball.getWidth();
        imageEditor = new ImageEditor(ball);
        ImageEditor imageEditor0 = new ImageEditor(ball0);
        ImageEditor imageEditor1 = new ImageEditor(ball1);
        ball = imageEditor.scale(2*r/imageWidth);
        ball0 = imageEditor0.scale(2*r/imageWidth);
        ball1 = imageEditor1.scale(2*r/imageWidth);
        
    }
}
