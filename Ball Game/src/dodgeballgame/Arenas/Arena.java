/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dodgeballgame.Arenas;

import dodgeballgame.GamePanel;
import dodgeballgame.HitBoxes.Hitbox;
import static dodgeballgame.GamePanel.arenaHEIGHT;
import static dodgeballgame.GamePanel.arenaWIDTH;
import dodgeballgame.HitBoxes.*;
import dodgeballgame.Tools;
import dodgeballgame.Vec2;
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
    
    public Vec2[][] teamAreas;
    
    public String arenaName;
    
    public ArrayList<Hitbox> arenaBallHitbox;
    public ArrayList<Hitbox> arenaSoftBallHitbox;
    public ArrayList<Hitbox> arenaPlayerHitbox;
    public ArrayList<Hitbox> arenaTeam1Hitbox;
    public ArrayList<Hitbox> arenaTeam2Hitbox;
    public ArrayList<Hitbox> arenaTeam1Goal;
    public ArrayList<Hitbox> arenaTeam2Goal;
    
    public ArrayList<Hitbox> renderHitboxes;
    
    public static int SIDE_GOALS = 0;
    public static int CORNER_GOALS = 1;
    
    public double softBounceFactor = 0.2;
    public double bounceFactor = 0.9;
    public int buffer = 1000;
    public boolean goalsActive;
    
    public int numPlayers;
    
    public BufferedImage backgroundImage;
    public BufferedImage scaledBackgroundImage;
    
    int WIDTH = (int)GamePanel.arenaWIDTH;
    int HEIGHT = (int)GamePanel.arenaHEIGHT;
    
    public String imgPath;

    public Vec2[] playerPos = new Vec2[GamePanel.maxNumPlayers];
        
    public Arena() {
        
    }
        
    public void init() {
        try {
            backgroundImage = ImageIO.read(new File(imgPath));
        } catch (IOException e) {
        }  
        scaledBackgroundImage = new BufferedImage(arenaWIDTH, 
                arenaHEIGHT, 
                BufferedImage.TYPE_INT_ARGB);
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
        renderHitboxes = new ArrayList();
        
        initPlayerPositions();
        initBorders();
        if (goalsActive) {initGoals();}
        initHitBoxes();
        initTeamAreas();
    }
    
    public void initPlayerPositions() {
        numPlayers = GamePanel.numPlayers;
        for (int p = 0; p < 4 ; p++) {
            int team =2*p/numPlayers;
            int posi = (p%2+1);
            double x,y;
            if (team==0) x = WIDTH/20;
            else x = 19*WIDTH/20;
            if (posi == 1) y = HEIGHT/8;
            else y = 7*HEIGHT/8;
            
            playerPos[p] = new Vec2(x,y);
        }
    }
    
    public void initTeamAreas() {
        teamAreas = new Vec2[2][2];
        teamAreas[0] = new Vec2[]{new Vec2(0,0), new Vec2(WIDTH/2,HEIGHT)};
        teamAreas[1] = new Vec2[]{new Vec2(WIDTH/2,0), new Vec2(WIDTH,HEIGHT)};
    }

    
    public void initBorders() {
        // Top Edge
        LineHitbox hbT = new LineHitbox (0,0,Hitbox.DEG_0);
        arenaBallHitbox.add(hbT);
        arenaPlayerHitbox.add(hbT);
        
        // Left Edge
        LineHitbox hbL = new LineHitbox (0,0,Hitbox.DEG_270);
        arenaSoftBallHitbox.add(hbL);
        arenaPlayerHitbox.add(hbL);
        
        // Right Edge
        LineHitbox hbR = new LineHitbox (WIDTH,0, Hitbox.DEG_90);
        arenaSoftBallHitbox.add(hbR);
        arenaPlayerHitbox.add(hbR);
        
        // Bottom Edge
        LineHitbox hbB = new LineHitbox (0,HEIGHT, Hitbox.DEG_180);
        arenaBallHitbox.add(hbB);
        arenaPlayerHitbox.add(hbB);
    }
    
    public void initGoals() {
        sideGoals();
    }

    public void initHitBoxes() {
        // Team 1 middle Edge
        LineHitbox hbC1 = new LineHitbox (WIDTH/2,0, Hitbox.DEG_90);
        arenaTeam1Hitbox.add(hbC1);
        
        // Team 2 middle Edge
        LineHitbox hbC2 = new LineHitbox (WIDTH/2,0, Hitbox.DEG_270);
        arenaTeam2Hitbox.add(hbC2);
    }
    
    public void render(Graphics2D g) {
        g.drawImage(scaledBackgroundImage,0,0,null);
        renderScore(g);
        if (goalsActive) renderGoals(g);
        renderHitboxes(g);
        renderSpecific(g);
    }
    
    protected void renderHitboxes(Graphics2D g) {
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
        g.setColor(new Color(0,0,0));
        for(Hitbox hb: arenaBallHitbox) { hb.render(g); }
        g.setColor(new Color(100,100,100));
        for(Hitbox hb: arenaSoftBallHitbox) { hb.render(g); }
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
    }
    
    protected void renderScore(Graphics2D g) {
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
    
    protected void renderGoals(Graphics2D g) {
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.6f));
        g.setColor(new Color(255,100,100));
        for(Hitbox hb: arenaTeam1Goal) { hb.render(g); }
        g.setColor(new Color(100,100,255));
        for(Hitbox hb: arenaTeam2Goal) { hb.render(g); }
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
    }
    
    public void renderSpecific(Graphics2D g) {
        
    }
    
    public Arena copy() {
        BasicArena temp = new BasicArena();
        temp.init();
        return temp;
    }
    
    public void setGoals(int i) {
        arenaTeam1Goal = new ArrayList<>();
        arenaTeam2Goal = new ArrayList<>();
        goalsActive = true;
        switch (i) {
            case 0 : goalsActive = false;
                break;
            case 1 : cornerGoals();
                break;
            case 2 : sideGoals();
                break;
        }
    }
    
    // GOALS
    protected void cornerGoals() {
        int goalSize = 50;
        CircleHitbox goal1 = new CircleHitbox(0,0, goalSize);
        arenaTeam2Goal.add(goal1);
        
        CircleHitbox goal2 = new CircleHitbox(0,HEIGHT, goalSize);
        arenaTeam2Goal.add(goal2);
        
        CircleHitbox goal3 = new CircleHitbox(WIDTH,0, goalSize);
        arenaTeam1Goal.add(goal3);
        
        CircleHitbox goal4 = new CircleHitbox(WIDTH,HEIGHT, goalSize);
        arenaTeam1Goal.add(goal4);
    }
    
    protected void sideGoals(){
        RectHitbox goal1 = new RectHitbox(0,HEIGHT/2, 15, HEIGHT/12);
        arenaTeam2Goal.add(goal1);
        
        RectHitbox goal2 = new RectHitbox(WIDTH,HEIGHT/2,15, HEIGHT/12);
        arenaTeam1Goal.add(goal2);
    }
}
