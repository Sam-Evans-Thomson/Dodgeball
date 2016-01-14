/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dodgeballgame.Arenas.ArenaGraphicsComponents;

import dodgeballgame.Animation.GoalGlowAnimation;
import dodgeballgame.Arenas.Arena;
import dodgeballgame.GamePanel;
import static dodgeballgame.GamePanel.arenaWIDTH;
import dodgeballgame.HitBoxes.Hitbox;
import dodgeballgame.Tools;
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
public class ArenaGC {
    
    public Arena arena;
    
    public static final double GLOW_RADIUS = 160;
    public static final double GLOW_TIME = 0.3;
    public static final Color TEAM_1_COLOR = new Color(255,100,100);
    public static final Color TEAM_2_COLOR = new Color(100,100,255);
    
    public BufferedImage backgroundImage;
    public BufferedImage scaledBackgroundImage;
    
    public int WIDTH = (int)GamePanel.arenaWIDTH;
    public int HEIGHT = (int)GamePanel.arenaHEIGHT;
        
    public String imgPath;
    
    ArrayList<GoalGlowAnimation> goalGlows;
    
    public ArenaGC(Arena arena) {
        this.arena=arena;
    }
    
    public void init() {
        try {
            backgroundImage = ImageIO.read(new File(imgPath));
        } catch (IOException e) {
        }  
        scaledBackgroundImage = new BufferedImage(WIDTH, 
                HEIGHT, 
                BufferedImage.TYPE_INT_ARGB);
        AffineTransform at = new AffineTransform();
        at.scale((double)arenaWIDTH/1400.0, (double)HEIGHT/800.0);
        AffineTransformOp scaleOp = 
           new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR);
        scaledBackgroundImage = scaleOp.filter(backgroundImage, scaledBackgroundImage);
    }
    
    public void loadGoals() {
        goalGlows = new ArrayList<>();
        for (Hitbox hb : arena.arenaTeam1Goal) {
            goalGlows.add(new GoalGlowAnimation(TEAM_1_COLOR, hb, GLOW_TIME, GLOW_RADIUS));
        }
        for (Hitbox hb : arena.arenaTeam2Goal) {
            goalGlows.add(new GoalGlowAnimation(TEAM_2_COLOR, hb, GLOW_TIME, GLOW_RADIUS));
        }
    }
    
    public void render(Graphics2D g) {
        g.drawImage(scaledBackgroundImage,0,0,null);
        renderScore(g);
        if (GamePanel.arenaManager.goalsActive) renderGoals(g);
        renderHitboxes(g);
        renderSpecific(g);
    }    
    
    public void renderHitboxes(Graphics2D g) {
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
        g.setColor(new Color(255,255,255));
        for(Hitbox hb: arena.arenaBallHitbox) { hb.render(g); }
        g.setColor(new Color(255,255,255));
        for(Hitbox hb: arena.arenaSoftBallHitbox) { hb.render(g); }
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
    }

    
    public void renderScore(Graphics2D g) {
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.1f));
        g.setColor(new Color(250,250,250));
        g.setFont(new Font("Sans Serif", Font.BOLD, 500));
        Tools.centreStringHor("" + GamePanel.team1Score, g, WIDTH/2 - WIDTH/4, HEIGHT/2+150);
        Tools.centreStringHor("" + GamePanel.team2Score, g, WIDTH/2 + WIDTH/4, HEIGHT/2+150);
        g.setFont(new Font("Sans Serif", Font.BOLD, 200));
        Tools.centreStringHor("" + (int)GamePanel.winScore, g,WIDTH/2 - 130,HEIGHT);
        Tools.centreStringHor("" + (int)GamePanel.winScore, g,WIDTH/2 + 130,HEIGHT);
        g.fillRect(WIDTH/2 - WIDTH/8, 16*HEIGHT/20,WIDTH/4, HEIGHT/40);
    }
    
    public void renderGoals(Graphics2D g) {
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.6f));
        g.setColor(new Color(255,100,100));
        for(Hitbox hb: arena.arenaTeam1Goal) { hb.render(g); }
        g.setColor(new Color(100,100,255));
        for(Hitbox hb: arena.arenaTeam2Goal) { hb.render(g); }
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
        for(GoalGlowAnimation gga : goalGlows) {if(gga.isActive()) gga.render(g);} 
    } 
    
    public void renderSpecific(Graphics2D g) {
        
    }
    
    public void setGoalGlow(Hitbox hb) {
        for (GoalGlowAnimation gga : goalGlows) {if (gga.hb.equals(hb)) gga.refresh();}
    }
}
