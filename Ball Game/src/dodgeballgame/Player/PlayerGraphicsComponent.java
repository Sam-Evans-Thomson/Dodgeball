/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dodgeballgame.Player;

import dodgeballgame.GamePanel;
import dodgeballgame.ImageEditor;
import dodgeballgame.Timer;
import dodgeballgame.Tools;
import dodgeballgame.Vec2;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 *
 * @author Sam
 */
public class PlayerGraphicsComponent implements PlayerComponent{
    
    Player p;

    Color colors[] = new Color[2];

    public BufferedImage playerImageA;
    public BufferedImage playerImageB;
    public BufferedImage playerImage;
    
    private BufferedImage heart;
    private BufferedImage ball;
    
    public int playerScoreOffsetX;
    public int playerScoreOffsetY;
    
    public ImageEditor imageEditor;
    
    public PlayerGraphicsComponent(Player p) {
        this.p = p;
    }
    
    @Override
    public void init() {
        try {
            playerImageA = ImageIO.read(new File("Images/Players/player" + p.pNumber + "a.png"));
            playerImageB = ImageIO.read(new File("Images/Players/player" + p.pNumber + "b.png"));
            heart = ImageIO.read(new File("Images/heart.png"));
            ball = ImageIO.read(new File("Images/Balls/ball.png"));
        } catch (IOException e) {
        }
        playerImageA = Tools.sizeImage(playerImageA, 2*p.r);
        playerImageB = Tools.sizeImage(playerImageB, 2*p.r);
        heart = Tools.sizeImage(heart, 60);
        ball = Tools.sizeImage(ball, 60);
        
        playerImage = playerImageA;
        
        if (p.team == 0) {
            playerScoreOffsetX = p.pNumber*GamePanel.arenaWIDTH/4 + 20;
        } else {
            playerScoreOffsetX = 21*GamePanel.arenaWIDTH/40 + ((p.pNumber-1)/2)*GamePanel.arenaWIDTH/4 + 20;
        }
        
        playerScoreOffsetY = GamePanel.arenaHEIGHT + 60;
        
        // initialize colours
        if (p.team==0) {
            colors[0]=new Color(255,0,0);
            colors[1]=new Color(255,200,200);
        } else {
            colors[0]=new Color(0,0,255);
            colors[1]=new Color(200,200,255);
        }
        
        catchTime = 1.0;
        catchTimer.init();
        itemGlowTime = 1.0;
        itemGlowTimer.init();
        hitTimer.init();
        hitTime = 1;
        hitTimeMax = Player.invincibleTime;
    }
    
    public void changeImages(BufferedImage a, BufferedImage b) {
        playerImageA = Tools.sizeImage(a, 2*p.r);
        playerImageB = Tools.sizeImage(b, 2*p.r);
        playerImage = playerImageA;
    }
    
    @Override
    public void update(float d) {
        
    }
    
    public void render(Graphics2D g) {
        //Render Animation graphics
        catchGlow(colors[0],g,p.pos);
        powerUpGlow(colors[0],g,p.pos);
        
        if (checkHit()){
            playerImage = playerImageA;
        }
        
        renderAim(g);
        
        if (!p.solid) {
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.6f));
        } else {
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1));
        }
        
        g.drawImage(playerImage, (int)(p.pos.getX()-p.r), (int)(p.pos.getY()-p.r), null);
        
        renderPower(g);
        renderScore(g);
    }
    
    private void renderAim(Graphics2D g) {
        double percentage = (7d-p.catchTimer.getDifference())/5d;
        if (percentage < 0) percentage = 0;
        else if (percentage > 1) percentage = 1;
        
        percentage = percentage*0.7 + 0.3;
        
        g.setColor(colors[0]);   
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f));     
        int[] xPoints = {(int)(p.pos.getX() + 1.15*0.9*p.radius*Math.cos(p.angle)),
                        (int)(p.pos.getX() + p.radius*0.9*Math.cos(p.angle+0.1)),
                        (int)(p.pos.getX() + p.radius*0.9*Math.cos(p.angle-0.1))};
        int[] yPoints = {(int)(p.pos.getY() + 1.15*0.9*p.radius*Math.sin(p.angle)),
                        (int)(p.pos.getY() + p.radius*0.9*Math.sin(p.angle+0.1)),
                        (int)(p.pos.getY() + p.radius*0.9*Math.sin(p.angle-0.1))};
        g.fillPolygon(xPoints, yPoints, 3);
        
        if (p.catchOn) {
            g.setColor(colors[1]);        
            g.fillArc((int)(p.pos.getX()-0.9*p.radius), (int)(p.pos.getY()-0.9*p.radius), (int)(2*0.9*p.radius), (int)(2*p.radius*0.9),
                    (int)(Math.toDegrees(-p.angle-p.catchAngle/2+0.1)), (int)Math.toDegrees(p.catchAngle-0.2));
            g.setColor(colors[0]);  
            g.fillArc((int)(p.pos.getX()-0.9*p.radius*percentage), (int)(p.pos.getY()-0.9*p.radius*percentage), (int)(2*0.9*p.radius*percentage), (int)(2*p.radius*0.9*percentage),
                    (int)(Math.toDegrees(-p.angle-p.catchAngle/2+0.1)), (int)Math.toDegrees(p.catchAngle-0.2));
        }
    }
    
    private void renderScore(Graphics2D g) {
        
        g.drawImage(Tools.scaleImage(playerImage,0.6), playerScoreOffsetX, playerScoreOffsetY-50, null);
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
        g.drawImage(heart, playerScoreOffsetX + 100, playerScoreOffsetY-50, null);
        
        g.setColor(new Color(0,0,0));
        g.setFont(new Font("Sans Serif", Font.BOLD, 24));
        
        Tools.centreStringHor(("" + p.health), g, playerScoreOffsetX + 129, playerScoreOffsetY-14);

        g.drawImage(ball, playerScoreOffsetX + 200, playerScoreOffsetY-50, null);

        g.drawImage(Tools.sizeImage(p.currentPower.image,60), playerScoreOffsetX + 300, playerScoreOffsetY-50, null);

        Tools.centreStringHor(("" + p.numBalls), g, playerScoreOffsetX + 229, playerScoreOffsetY-14);
    }
    
    private void renderPower(Graphics2D g) {
        for (int i = 0; i < p.activePowers.size(); i++) {
            /// Display all powers over head.
            BufferedImage img = p.activePowers.get(i).image;
            double[] times = p.stateComp.activeStates.get(i).getTimes();
            float opacity = (float)(1d - times[0]/times[1]);
            if(opacity < 0) opacity = 0f;
            BufferedImage im = Tools.sizeImage(img,60);
            int w = (int)(im.getWidth());
            int x = (int)p.pos.getX() - w*p.activePowers.size()/2 + i*w;
            int y = (int)p.pos.getY() - (int)(1.2*2*p.r);
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity));
            g.drawImage(im , x, y, null);
        }
    }
    
    /////// GETTING HIT /////////////
    
    public final Timer hitTimer = new Timer();
    private double hitTime;
    private double hitTimeMax;
    
    public void hitPlayer() {
        playerImage = playerImageB;
        hitTimer.refresh();
        hitTime = hitTimer.getDifference();
    }
    
    public boolean checkHit() {
        hitTime = hitTimer.getDifference();
        return hitTime > hitTimeMax;
    }
    
    /////// CATCHING ///////////////
    
    public final Timer catchTimer = new Timer();
    private double catchTime;
    private final double catchTimeMax = 0.12;
    public int[][] catchGlow;
    public double catchGlowRadius = 160.0;
    private final Color glowColor = new Color(255,255,200);
    
    public Graphics2D catchGlow(Color c, Graphics2D g, Vec2 pos) {
        
        if (catchTime < catchTimeMax) {
            float opacity = (float)(catchTimeMax - catchTime)*0.8f/(float)catchTimeMax;
            g.setColor(glowColor);
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity));
            double size = 100.0 + (catchGlowRadius - 100)*catchTime/catchTimeMax;
            g.fillOval((int)(pos.getX()-size/2), (int)(pos.getY()-size/2),(int)size, (int)size);
            catchTime = catchTimer.getDifference();
        }
        
        return g;        
    }
    
    public void setCatchGlow() {        
        catchTimer.refresh();
        catchTime = catchTimer.getDifference();
    }
    
    ////// ITEMS ////////////
    
    public final Timer itemGlowTimer = new Timer();
    private double itemGlowTime;
    private final double powerUpTimeMax = 0.2;
    public int[][] powerUpGlow;
    public double powerUpGlowRadius = 200.0;
    private Color itemGlowColor;
    
    public Graphics2D powerUpGlow(Color c, Graphics2D g, Vec2 pos) {
        
        if (itemGlowTime < powerUpTimeMax) {
            float opacity = (float)(powerUpTimeMax - itemGlowTime)*0.8f/(float)powerUpTimeMax;
            g.setColor(itemGlowColor);
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity));
            double size = 100.0 + (powerUpGlowRadius - 100)*itemGlowTime/powerUpTimeMax;
            g.fillOval((int)(pos.getX()-size/2), (int)(pos.getY()-size/2),(int)size, (int)size);
            itemGlowTime = itemGlowTimer.getDifference();
        }
        
        return g;        
    }

    public void setItemGlow(Color c) {
        itemGlowColor = c;
        itemGlowTimer.refresh();
        itemGlowTime = itemGlowTimer.getDifference();
    }
    
    /////// POWERS   ///////////////
    public void setPowerGlow(Color c) {
        
    }
    

    /////////// TOOLS //////////////////
    
    
}
