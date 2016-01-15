/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dodgeballgame;

import dodgeballgame.Arenas.Arena;
import dodgeballgame.Arenas.ArenaManager;
import dodgeballgame.Player.Player;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
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
    
    // Break Blocks
    
    public static ArrayList<BufferedImage> breaks = new ArrayList<>();
    
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
        
        
        // BreakBlocks
        BufferedImage break0,break1,break2;
        try {
            break0 = ImageIO.read(new File("Images/BreakBlocks/break0.png"));
            break1 = ImageIO.read(new File("Images/BreakBlocks/break1.png"));
            break2 = ImageIO.read(new File("Images/BreakBlocks/break2.png"));
            breaks.add(Tools.sizeImage(break0, GamePanel.arenaHEIGHT/10));
            breaks.add(Tools.sizeImage(break1, GamePanel.arenaHEIGHT/10));
            breaks.add(Tools.sizeImage(break2, GamePanel.arenaHEIGHT/10));
        } catch (IOException e) {
        }
    }
    
    public static ArrayList<BufferedImage[]> arenaDisplays = new ArrayList<>();
    public static void arenaDisplays(ArrayList<Arena> arenas, int numArenas, int goals) {
        
        for (int i = 0; i < numArenas; i++) {
            
            BufferedImage[] arena = new BufferedImage[goals+1];
            
            for (int j = 0; j < goals + 1; j++) {
                
                BufferedImage image = new BufferedImage(GamePanel.arenaWIDTH, 
                GamePanel.arenaHEIGHT, 
                BufferedImage.TYPE_INT_RGB);
                Graphics2D g = image.createGraphics();

                Arena temp = arenas.get(i).copy();
                if (j<goals) temp.setGoals(j);
                else ArenaManager.goalsActive = false;
                temp.render(g);
                
                arena[j] = image;
            }
            
            arenaDisplays.add(arena);
        }
        
    }
}
