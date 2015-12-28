/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dodgeballgame.Arenas;

import dodgeballgame.GamePanel;
import dodgeballgame.HitBox;
import static dodgeballgame.GamePanel.arenaHEIGHT;
import static dodgeballgame.GamePanel.arenaWIDTH;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;

/**
 *
 * @author Sam
 */
public class Arena {
    
    public ArrayList<HitBox> arenaBallHitbox;
    public ArrayList<HitBox> arenaSoftBallHitbox;
    public ArrayList<HitBox> arenaPlayerHitbox;
    public ArrayList<HitBox> arenaTeam1Hitbox;
    public ArrayList<HitBox> arenaTeam2Hitbox;
    public ArrayList<HitBox> arenaTeam1Goal;
    public ArrayList<HitBox> arenaTeam2Goal;
    
    public double softBounceFactor = 0.2;
    public double bounceFactor = 0.9;
    public int buffer = 1000;
    public static boolean goalsActive;
    
    private static BufferedImage backgroundImage;
    private static BufferedImage scaledBackgroundImage;
    
    double WIDTH = GamePanel.arenaWIDTH;
    double HEIGHT = GamePanel.arenaHEIGHT;
        
    public Arena() {
    }
    
    public void render(Graphics2D g) {
        g.drawImage(scaledBackgroundImage,0,0,null);
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.1f));
        g.setColor(new Color(250,250,250));
        g.setFont(new Font("Sans Serif", Font.BOLD, 500));
        centreString("" + GamePanel.team1Score, g, GamePanel.arenaWIDTH/4,GamePanel.arenaHEIGHT/2+150);
        centreString("" + GamePanel.team2Score, g, 3*GamePanel.arenaWIDTH/4,GamePanel.arenaHEIGHT/2+150);
        
        if (goalsActive){
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.6f));
            g.setColor(new Color(255,100,100));
            for(HitBox hb: arenaTeam1Goal) { hb.render(g); }
            g.setColor(new Color(100,100,255));
            for(HitBox hb: arenaTeam2Goal) { hb.render(g); }
        }
        
        
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
    }
    
    private void centreString(String s, Graphics2D g, int x, int y) {
        int stringLen = (int)
            g.getFontMetrics().getStringBounds(s, g).getWidth();
        g.drawString(s, x - stringLen/2, y);
    }
    
    public void init() {
        try {
            backgroundImage = ImageIO.read(new File("Images/Court1.png"));
        } catch (IOException e) {
        }  
        scaledBackgroundImage = new BufferedImage(arenaWIDTH, arenaHEIGHT, BufferedImage.TYPE_INT_ARGB);
        AffineTransform at = new AffineTransform();
        at.scale((double)arenaWIDTH/1400.0, (double)arenaHEIGHT/800.0);
        AffineTransformOp scaleOp = 
           new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR);
        scaledBackgroundImage = scaleOp.filter(backgroundImage, scaledBackgroundImage);
        
        
        
        arenaBallHitbox = new ArrayList<>();
        arenaSoftBallHitbox = new ArrayList<>();
        arenaPlayerHitbox = new ArrayList<>();
        arenaTeam1Hitbox = new ArrayList<>();
        arenaTeam2Hitbox = new ArrayList<>();
        arenaTeam1Goal = new ArrayList<>();
        arenaTeam2Goal = new ArrayList<>();
        
        initBorders();
        if (goalsActive) {initGoals();}
        initHitBoxes();
    }
    
    public void initBorders() {
        // Top Edge
        HitBox hbT = new HitBox (0,0);
        hbT.makeHLine(false);
        arenaBallHitbox.add(hbT);
        arenaPlayerHitbox.add(hbT);
        
        // Left Edge
        HitBox hbL = new HitBox (0,0);
        hbL.makeVLine(false);
        arenaSoftBallHitbox.add(hbL);
        arenaPlayerHitbox.add(hbL);
        
        // Right Edge
        HitBox hbR = new HitBox (WIDTH,0);
        hbR.makeVLine(true);
        arenaSoftBallHitbox.add(hbR);
        arenaPlayerHitbox.add(hbR);
        
        // Bottom Edge
        HitBox hbB = new HitBox (0,HEIGHT);
        hbB.makeHLine(true);
        arenaBallHitbox.add(hbB);
        arenaPlayerHitbox.add(hbB);
    }
    
    public void initGoals() {
        cornerGoals();
    }

    public void initHitBoxes() {
        // Team 1 middle Edge
        HitBox hbC1 = new HitBox (WIDTH/2 - 10,0);
        hbC1.makeVLine(true);
        arenaTeam1Hitbox.add(hbC1);
        
        // Team 2 middle Edge
        HitBox hbC2 = new HitBox (WIDTH/2 + 10,0);
        hbC2.makeVLine(false);
        arenaTeam2Hitbox.add(hbC2);
    }
    
    // GOALS
    private void cornerGoals() {
        int goalSize = 50;
        HitBox goal1 = new HitBox(0,0);
        goal1.makeCircle(goalSize);
        arenaTeam2Goal.add(goal1);
        
        HitBox goal2 = new HitBox(0,HEIGHT);
        goal2.makeCircle(goalSize);
        arenaTeam2Goal.add(goal2);
        
        HitBox goal3 = new HitBox(WIDTH,0);
        goal3.makeCircle(goalSize);
        arenaTeam1Goal.add(goal3);
        
        HitBox goal4 = new HitBox(WIDTH,HEIGHT);
        goal4.makeCircle(goalSize);
        arenaTeam1Goal.add(goal4);
    }
    
    private void sideGoals(){
        HitBox goal1 = new HitBox(0,HEIGHT/2);
        goal1.makeRect(15, HEIGHT/6);
        arenaTeam2Goal.add(goal1);
        
        HitBox goal2 = new HitBox(WIDTH,HEIGHT/2);
        goal2.makeRect(15, HEIGHT/6);
        arenaTeam1Goal.add(goal2);
    }
}
