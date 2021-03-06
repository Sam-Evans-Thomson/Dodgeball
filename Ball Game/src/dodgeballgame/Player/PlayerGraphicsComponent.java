/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dodgeballgame.Player;

import dodgeballgame.Animation.GlowAnimation;
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
    public BufferedImage playerImage, playerImageSmall;
    public BufferedImage ghostImage;
    
    public GlowAnimation deathAnimation;
    public GlowAnimation catchAnimation;
    public GlowAnimation itemAnimation;
    
    private BufferedImage heart;
    private BufferedImage ball;
    
    public int playerScoreOffsetX;
    public int playerScoreOffsetY;
    
    public ImageEditor imageEditor;
    public ImageEditor ghostEditor;
    
    public PlayerGraphicsComponent(Player p) {
        this.p = p;
    }
    
    @Override
    public void init() {
        try {
            playerImageA = ImageIO.read(new File("Images/Players/player" + p.pNumber + "a.png"));
            playerImageB = ImageIO.read(new File("Images/Players/player" + p.pNumber + "b.png"));
            ghostImage = ImageIO.read(new File("Images/Players/player" + p.pNumber + "b.png"));
            heart = ImageIO.read(new File("Images/heart.png"));
            ball = ImageIO.read(new File("Images/Balls/ball.png"));
        } catch (IOException e) {
        }

        changeImages(playerImageA,playerImageB);
        
        heart = Tools.sizeImage(heart, 70);
        ball = Tools.sizeImage(ball, 70);
        
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

        hitTimer.init();
        hitTime = 1;
        hitTimeMax = Player.invincibleTime;
        
        /// initialize animations
        deathAnimation  = new GlowAnimation(1.2);
        deathAnimation.setColors(255,0,0,0,0,0);
        deathAnimation.setRadius(160);
        
        catchAnimation  = new GlowAnimation(0.12);
        catchAnimation.setColor(255,255,200);
        catchAnimation.setRadius(160);
        
        itemAnimation  = new GlowAnimation(0.2);
        itemAnimation.setRadius(160);
        
    }
    
    public void changeImages(BufferedImage a, BufferedImage b) {
        playerImageA = Tools.sizeImage(a, 2*p.r);
        playerImageB = Tools.sizeImage(b, 2*p.r);
        playerImage = playerImageA;
        ghostImage = Tools.sizeImage(ghostImage, 2*p.r);
        ghostEditor = new ImageEditor(ghostImage);
        playerImageSmall = Tools.scaleImage(playerImage, 0.7f);
    }

    public void render(Graphics2D g) {
        //Render Animation graphics
        if(catchAnimation.isActive()) catchAnimation.render(g, p.pos);
        if (itemAnimation.isActive()) itemAnimation.render(g,p.pos);
        if(deathAnimation.isActive()) deathAnimation.render(g, p.pos);
        
        hitTime = hitTimer.getDifference();
        if (hitTime > hitTimeMax && !playerImage.equals(playerImageA)){
            playerImage = playerImageA;
        }

        if(p.isGhost) {
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.4f));
            BufferedImage ghostImage2 = ghostEditor.rotate(p.aimAngle.getAngle());
            g.drawImage(ghostImage2, (int)(p.pos.getX()-p.r), (int)(p.pos.getY()-p.r), null);
        } else if (!p.solid){
            renderAim(g);
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.6f));
            g.drawImage(playerImage, (int)(p.pos.getX()-p.r), (int)(p.pos.getY()-p.r), null);
        } else {
            renderAim(g);
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
            g.drawImage(playerImage, (int)(p.pos.getX()-p.r), (int)(p.pos.getY()-p.r), null);
        }

        renderPower(g);
        renderScore(g);
    }
    
    private void renderAim(Graphics2D g) {
        
        double rad = 0.9*p.radius;
        double x = p.pos.getX();
        double y = p.pos.getY();
        
        g.setColor(colors[0]);   
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f));     
        int[] xPoints = {(int)(x + 1.15*rad*Math.cos(p.angle)),
                        (int)(x + rad*Math.cos(p.angle+0.1)),
                        (int)(x + rad*Math.cos(p.angle-0.1))};
        int[] yPoints = {(int)(y + 1.15*rad*Math.sin(p.angle)),
                        (int)(y + rad*Math.sin(p.angle+0.1)),
                        (int)(y + rad*Math.sin(p.angle-0.1))};
        g.fillPolygon(xPoints, yPoints, 3);
        
        if (p.catchOn) {
            g.setColor(colors[1]);        
            g.fillArc((int)(x-rad), (int)(y-rad), (int)(2*rad), (int)(2*rad),
                    (int)(Math.toDegrees(-p.angle-p.catchAngle/2+0.1)), (int)Math.toDegrees(p.catchAngle-0.2));
        }
    }
    
    private void renderScore(Graphics2D g) {
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
        if (Player.knockoutOn) {
            for(int i = p.lives; i > 1; i--) {
                g.drawImage(heart, playerScoreOffsetX + 100 - 7*(i-1), playerScoreOffsetY-50, null);
            }
        } 
        
        g.drawImage(heart, playerScoreOffsetX + 100, playerScoreOffsetY-50, null);
        
        g.drawImage(playerImageSmall, playerScoreOffsetX, playerScoreOffsetY-50, null);
        
        g.setColor(new Color(0,0,0));
        g.setFont(new Font("Sans Serif", Font.BOLD, 27));
        
        Tools.centreStringHor(("" + p.health), g, playerScoreOffsetX + 133, playerScoreOffsetY-9);

        g.drawImage(ball, playerScoreOffsetX + 200, playerScoreOffsetY-50, null);

        g.drawImage(Tools.sizeImage(p.currentPower.image,60), playerScoreOffsetX + 300, playerScoreOffsetY-50, null);

        Tools.centreStringHor(("" + p.numBalls), g, playerScoreOffsetX + 233, playerScoreOffsetY-7);
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
    
    //////////Dying /////////////////////
    
    
    public void setDeathAnimation() {deathAnimation.refresh();}
    public void setCatchGlow() {catchAnimation.refresh();}
    public void setItemGlow(Color color) {
        itemAnimation.refresh();
        itemAnimation.setColor(color);
    }
    


    
    /////// POWERS   ///////////////
    public void setPowerGlow(Color c) {
        
    }
    

    /////////// TOOLS //////////////////

    @Override
    public void update(float d) {
    }

}
