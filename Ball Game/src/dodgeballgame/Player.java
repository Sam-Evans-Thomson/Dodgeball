/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dodgeballgame;

import dodgeballgame.Balls.Ball;
import dodgeballgame.Balls.HomingBall;
import dodgeballgame.Balls.Wrench;
import dodgeballgame.PowerUps.PowerUp;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
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
public class Player {
    
    public static int startHealth;
    public static int startBalls;
    
    public PlayerGraphics pGraphics;
    public ImageEditor imageEditor;
    public int playerScoreOffsetX;
    public int playerScoreOffsetY;
    
    private float leftTrig;
    private float rightTrig;
    
    public Vec2 DELTA;      // The values directly from left Thumb stick.
    private Vec2 aimAngle;  // the angle of throwing.
    
    public Vec2 pos, prevPos;       // current position
    public Vec2 delta, prevDelta;   //Change in position
    
    public double catchAngle, radius;   //The radius and angle of the catch area
    
    private double angle;               //The angle of movement of the player.
    
    public int health;
    
    public double baseSpeed;    //starting speed per second.
    
    /*This speed changes between frames to reflect 
    the acceleration applied to it */
    public double speed;
    
    public double frameSpeed;   //this frames speed per second.
    public double maxSpeed;     //the maximum speed per second.
    
    private float d;            // This frames change in time.
    
    public int numPowerUps;
    
    public static int pointsPerGoal;
    
    public Timer hbTimer;
    public Timer catchTimer;
    
    public boolean solid;
    public static double invincibleTime;
    
    public double throwSpeed;       //throw speed when standing still.
    private double relThrowSpeed;   //throw speed with movement taken into account
    private double throwAngle;      //angle of throwing the ball.
    
    public int numBallsInArea;
    public int numBalls;
    
    public Ball nextBall;
    
    BounceCalculator bCalc;
    
    Color colors[] = new Color[2];
    
    boolean inCatchArea;
    
    int leftLimit, rightLimit, upperLimit, lowerLimit;
    
    private int H, W;
    
    public int team;
    public final int pNumber,gPad;
    
    public BufferedImage playerImageA;
    public BufferedImage playerImageB;
    public BufferedImage playerImage;
    
    private BufferedImage heart;
    private BufferedImage ball;
    
    public HitBox playerHitbox;
    public HitBox catchHitbox;
    
    public Player(int team, int pNumber, double x, double y) {
        this.pNumber = gPad = pNumber;
        pos = new Vec2(x,y);
        this.team = team;

        init();
    }
    
    private void init() {
        
        try {
            playerImageA = ImageIO.read(new File("Images/Players/player" + pNumber + "a.png"));
            playerImageB = ImageIO.read(new File("Images/Players/player" + pNumber + "b.png"));
            heart = ImageIO.read(new File("Images/heart.png"));
            ball = ImageIO.read(new File("Images/Balls/ball.png"));
        } catch (IOException e) {
        }
        
        
        solid = true;
        hbTimer = new Timer();
        catchTimer = new Timer();
        playerImage = playerImageA;
        
        if (team == 0) {
            playerScoreOffsetX = pNumber*GamePanel.arenaWIDTH/6 + 20;
        } else {
            playerScoreOffsetX = GamePanel.arenaWIDTH/2 + ((pNumber+1)/2)*GamePanel.arenaWIDTH/6 + 20;
        }
        
        
        
        playerScoreOffsetY = GamePanel.arenaHEIGHT + 60;
        
        W = H = 100; 
        pGraphics = new PlayerGraphics(W,H);
        health = startHealth;
        DELTA = new Vec2(0,0);
        aimAngle = new Vec2(0,0);
        numPowerUps = 0;
               
        invincibleTime = 2d;
        baseSpeed = speed = 500.0;
        maxSpeed = 800;
        throwSpeed = 500.0;
        prevPos = new Vec2(pos.getX(),pos.getY());
        delta = new Vec2(0,0);
        prevDelta = new Vec2(0,0);
        angle = 0;
        radius = 150;
        catchAngle = (1.2*Math.PI)/4.0;
        inCatchArea = false;
        numBalls = startBalls;
        numBallsInArea = 0;
        
        nextBall = new Ball(0, 0, 0, 0, team, pNumber);
                
        // Player Boundaries
        leftLimit = team*GamePanel.arenaWIDTH/2 + W/2;
        rightLimit = (team+1)*GamePanel.arenaWIDTH/2 - W/2;
        upperLimit = H/2;
        lowerLimit = GamePanel.arenaHEIGHT - H/2;
        
        // Initialize hitboxes
        playerHitbox = new HitBox(pos.getX(),pos.getY());
        playerHitbox.makeCircle(H/2);
        catchHitbox = new HitBox(pos.getX(),pos.getY());
        catchHitbox.makeArc(catchAngle, angle, radius);
        
        // initialize colours
        if (team==0) {
            colors[0]=new Color(255,0,0);
            colors[1]=new Color(255,200,200);
        } else {
            colors[0]=new Color(0,0,255);
            colors[1]=new Color(200,200,255);
        }
    }
    
    public void update(float d) {
        
        if (!solid) {
            double timeDelta = hbTimer.getDifference();
            if (timeDelta > invincibleTime) {
                solid = true;
            }
        }
        
        this.d = d;
        
        updateMovementValues(d);
        updatePosition();
        updateThrowValues();
        
        prevPos.set(pos);
        prevDelta.set(delta);
    }
    
    private void updateMovementValues(float d) {   
        speed=maxSpeed;
        frameSpeed = d*speed;
        delta = DELTA.multiply(frameSpeed);
    }
    
    private void updateThrowValues() {
        double movVelFactor = 0.3;
        
        Vec2 throwDelta = new Vec2((movVelFactor*delta.getX()/d)+throwSpeed*Math.cos(angle),
                                    (movVelFactor*delta.getY()/d)+throwSpeed*Math.sin(angle));
        relThrowSpeed = throwDelta.getMagnitude();
        
        double catchFactor = 7-(int)catchTimer.getDifference();
        if (catchFactor > 5) catchFactor = 5;
        else if (catchFactor < 1) catchFactor = 1;
        relThrowSpeed*= catchFactor/5d;
        
        throwAngle = throwDelta.getAngle();
        throwAngle = refreshAngle(throwAngle);
    }

    private void updatePosition() {         
        moveBy(delta);
    }
        
    public void moveBy(Vec2 vec) {
        
        pos = pos.add(vec);
        updateHitbox();
        
        Vec2 d2 = new Vec2(0,0);
        Vec2 dir;
        Vec2 dir2;
       
        //check if in wall and update the position accordingly
        for(HitBox hb : GamePanel.arena.arenaPlayerHitbox) {
            if(hb.collision(playerHitbox)) {

                HitBox test1 = new HitBox((int)pos.getX(), (int)prevPos.getY());
                test1.makeCircle(H/2);
                HitBox test2 = new HitBox((int)prevPos.getX(), (int)pos.getY());
                test2.makeCircle(H/2);
                
                if (hb.collision(test1) && !hb.collision(test2)) {
                    d2 = new Vec2(-(vec.getX()),0);   
                } else if (hb.collision(test2) && !hb.collision(test1)) {
                    d2 = new Vec2(0,-(vec.getY()));
                } else {
                    d2 = new Vec2(-(vec.getX()),-(vec.getY()));
                }
                pos = pos.add(d2);
                updateHitbox();
            }
        }
        
        if (team == 0) {
            for(HitBox hb : GamePanel.arena.arenaTeam1Hitbox) {
                if(hb.collision(playerHitbox)) {

                    HitBox test1 = new HitBox((int)pos.getX(), (int)prevPos.getY());
                    test1.makeCircle(H/2);
                    HitBox test2 = new HitBox((int)prevPos.getX(), (int)pos.getY());
                    test2.makeCircle(H/2);

                    if (hb.collision(test1) && !hb.collision(test2)) {
                        d2 = new Vec2(-(vec.getX()),0);   
                    } else if (hb.collision(test2) && !hb.collision(test1)) {
                        d2 = new Vec2(0,-(vec.getY()));
                    } else {
                        d2 = new Vec2(-(vec.getX()),-(vec.getY()));
                    }
                    pos = pos.add(d2);
                    updateHitbox();
                }
            }
        } else if (team == 1) {
            for(HitBox hb : GamePanel.arena.arenaTeam2Hitbox) {
                if(hb.collision(playerHitbox)) {

                    HitBox test1 = new HitBox((int)pos.getX(), (int)prevPos.getY());
                    test1.makeCircle(H/2);
                    HitBox test2 = new HitBox((int)prevPos.getX(), (int)pos.getY());
                    test2.makeCircle(H/2);

                    if (hb.collision(test1) && !hb.collision(test2)) {
                        d2 = new Vec2(-(vec.getX()),0);   
                    } else if (hb.collision(test2) && !hb.collision(test1)) {
                        d2 = new Vec2(0,-(vec.getY()));
                    } else {
                        d2 = new Vec2(-(vec.getX()),-(vec.getY()));
                    }
                    pos = pos.add(d2);
                    updateHitbox();
                }
            }
        }

        //check if in player
        for(int i = 0; i < GamePanel.NUM_PLAYERS; i++) {
            Player p = GamePanel.playerArray.get(i);
            if (!(this.equals(p)) && p.playerHitbox.collision(playerHitbox)) {
                speed = baseSpeed;
                double mag = delta.getMagnitude();
                double angle = pos.getAngle(p.pos);
                
                dir = new Vec2(angle, mag, 1);
                dir2 = new Vec2(angle, -mag, 1);
                
                moveBy(dir2);
                p.moveBy(dir);
            }
        }
    }
    
    private void updateHitbox() {
        playerHitbox.moveTo(pos.getX(),pos.getY());
        catchHitbox.moveTo(pos.getX(),pos.getY());
        catchHitbox.setArcAngle(catchAngle);
        catchHitbox.setRadius(radius);
    }
    
    /*************************************************************/
    // RENDERING
    //
    
    public void render(Graphics2D g) {
        
        //Render Animation graphics
        pGraphics.catchGlow(colors[0],g,pos);
        pGraphics.powerUpGlow(colors[0],g,pos);
        
        if (pGraphics.checkHit()){
            playerImage = playerImageA;
        }
        
        renderAim(g);
        
        if (!solid) {
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.6f));
        } else {
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1));
        }
        
        g.drawImage(playerImage, (int)(pos.getX()-W/2), (int)(pos.getY()-H/2), null);
        
        renderScore(g);
       
    }
    
    private void renderAim(Graphics2D g) {
        double percentage = (7d-catchTimer.getDifference())/5d;
        if (percentage < 0) percentage = 0;
        else if (percentage > 1) percentage = 1;
        
        percentage = percentage*0.7 + 0.3;
        
        g.setColor(colors[0]);   
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f));     
        int[] xPoints = {(int)(pos.getX() + 1.15*0.9*radius*Math.cos(angle)),
                        (int)(pos.getX() + radius*0.9*Math.cos(angle+0.1)),
                        (int)(pos.getX() + radius*0.9*Math.cos(angle-0.1))};
        int[] yPoints = {(int)(pos.getY() + 1.15*0.9*radius*Math.sin(angle)),
                        (int)(pos.getY() + radius*0.9*Math.sin(angle+0.1)),
                        (int)(pos.getY() + radius*0.9*Math.sin(angle-0.1))};
        g.fillPolygon(xPoints, yPoints, 3);
        g.setColor(colors[1]);        
        g.fillArc((int)(pos.getX()-0.9*radius), (int)(pos.getY()-0.9*radius), (int)(2*0.9*radius), (int)(2*radius*0.9),
                (int)(Math.toDegrees(-angle-catchAngle/2+0.1)), (int)Math.toDegrees(catchAngle-0.2));
        g.setColor(colors[0]);  
        g.fillArc((int)(pos.getX()-0.9*radius*percentage), (int)(pos.getY()-0.9*radius*percentage), (int)(2*0.9*radius*percentage), (int)(2*radius*0.9*percentage),
                (int)(Math.toDegrees(-angle-catchAngle/2+0.1)), (int)Math.toDegrees(catchAngle-0.2));
    }
    
    private void renderScore(Graphics2D g) {
        imageEditor = new ImageEditor(playerImage);
        BufferedImage bi = imageEditor.scale(0.6);
        g.drawImage(bi, playerScoreOffsetX, playerScoreOffsetY-50, null);
        
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
        imageEditor.setImage(heart);
        BufferedImage biHeart = imageEditor.scale(0.6);
        g.drawImage(biHeart, playerScoreOffsetX + 100, playerScoreOffsetY-50, null);
        
        g.setColor(new Color(0,0,0));
        g.setFont(new Font("Sans Serif", Font.BOLD, 24));
        
        centreString(new String("" + health), g, playerScoreOffsetX + 129, playerScoreOffsetY-14);
        
        imageEditor.setImage(ball);
        BufferedImage biBall = imageEditor.scale(0.6);
        g.drawImage(biBall, playerScoreOffsetX + 200, playerScoreOffsetY-50, null);

        centreString(new String("" + numBalls), g, playerScoreOffsetX + 229, playerScoreOffsetY-14);
    }
    
    
    /*************************************************************/
    // PLAYER ACTIONS
    //
    
    public void hitPlayer() {
        hbTimer.refresh();
        solid = false;
        GamePanel.changeScore(1-team,1);
        GamePanel.powerUpManager.addPowerUp(1-team);
        GamePanel.powerUpManager.addBall(1-team); 
        if (health > 1) {
            health--;
            playerImage = playerImageB;
            pGraphics.setGetHit();
        }
        else death();
    }
    
    public void hitPlayer(int i) {
        GamePanel.changeScore(1-team,1);
        GamePanel.powerUpManager.addPowerUp(1-team);
        GamePanel.powerUpManager.addBall(1-team); 
        hbTimer.refresh();
        solid = false;
        if (health > 1) {
            health-= i;
            playerImage = playerImageB;
            pGraphics.setGetHit();            
        }
        else death();
    }
    
    public void death() {

        for (int i = 0; i < numBalls; i++) {
            GamePanel.powerUpManager.addBall(1-team);
        }
        for (int i = 0; i < numPowerUps/2; i++) {
            GamePanel.powerUpManager.addPowerUp(1-team);
        }
         
        
        W = H = 100; 
        health = startHealth;
        DELTA = new Vec2(0,0);
        aimAngle = new Vec2(0,0);
        numPowerUps = 0;
        
        baseSpeed = speed = 100.0;
        maxSpeed = 500;
        throwSpeed = 600.0;
        angle = 0;
        radius = 150;
        catchAngle = (1.2*Math.PI)/4.0;
        inCatchArea = false;
        numBalls = 0;
        numBallsInArea = 0;
        
        catchHitbox = new HitBox(pos.getX(),pos.getY());
        catchHitbox.makeArc(catchAngle, angle, radius);
    }
    
    private void catchBall() {
        for (int i=0; i<GamePanel.ballArray.size(); i++) {
            Ball b = GamePanel.ballArray.get(i);
            if(b.inCatchArea[pNumber]) {
                numBalls++;
                GamePanel.soundManager.catchBall();
                pGraphics.setCatchGlow();
                GamePanel.ballArray.remove(b);
                i--;
                catchTimer.refresh();
            }
        }
        //Catch powerUp
        for (int i=0; i<GamePanel.powerUpArray.size(); i++) {
            PowerUp p = GamePanel.powerUpArray.get(i);
            if(p.inCatchArea[pNumber]) {
                pGraphics.setPowerUpGlow(p.color);
                p.applyEffect(this);
                p.incPowerUpCount(this);
                GamePanel.powerUpArray.remove(p);
                i--;
                catchTimer.refresh();
            }
        }
        
        
    }
    
    private void throwBall() {
        double x = pos.getX()+ 1.15*radius*Math.cos(angle);
        double y = pos.getY()+ 1.15*radius*Math.sin(angle);
        Ball b = new Ball(relThrowSpeed, x, y, throwAngle, team, pNumber);
        b.setBall(relThrowSpeed, x, y, throwAngle, team, pNumber);
        
        int count = 0;
        for(HitBox hb : GamePanel.arena.arenaBallHitbox) {
            if(hb.collision(b.ballHitbox)) {
                count++;
            }
        }
        for(HitBox hb : GamePanel.arena.arenaSoftBallHitbox) {
            if(hb.collision(b.ballHitbox)) {
                count++;
            }
        }
        for(Player p : GamePanel.playerArray) {
            if(p.playerHitbox.collision(b.ballHitbox)) {
                count++;
            }
        }
        
        
        if(numBalls > 0 && count<1) {
            GamePanel.ballArray.add(b);
            numBalls--;
            catchTimer.refresh();
        }
        
        loadNextBall();
    }
    
    public void scoreGoal(int team) {
        GamePanel.changeScore(team, pointsPerGoal);
        numBalls++;
        catchTimer.refresh();
    }

    
    /*************************************************************/
    // HELPER METHODS
    // 
    
    // Chooses and loads the next ball.
    public void loadNextBall() {
        nextBall = new Ball(0d,0d,0d,0d,team, pNumber);
    }
    
    
    // is called when a ball is in the catch area.
    public void inCatchArea(Ball ball, boolean b) {
        inCatchArea = b;
    }
    
    // make sure an angle is between 0 and 2*Pi.
    public double refreshAngle(double a) {
        while (a < 0) {
            a+=2.0*Math.PI;
        }
        while (a > 2*Math.PI) {
            a-=2.0*Math.PI;
        }
        return a;
    }
    
    // prints a string centred on the specified spot.
    private void centreString(String s, Graphics2D g, int x, int y) {
        int stringLen = (int)
            g.getFontMetrics().getStringBounds(s, g).getWidth();
        g.drawString(s, x - stringLen/2, y);
    }
    
    /*************************************************************/
    // INPUTS
    //
    
    void pressA() {
        if (pNumber == 0 && GamePanel.gameState == GamePanel.MENU) {
                GamePanel.menu.select();
        }
    }

    void pressB() {
        if (pNumber == 0) {
            if (GamePanel.gameState == GamePanel.MENU) {
                GamePanel.menu.back();
            }
        }
    }

    void pressX() {
        if(pNumber == 0) GamePanel.menu.xButton();
    }

    void pressY() {
        if(pNumber == 0) GamePanel.menu.yButton();
    }

    void pressLB() {

    }

    void pressRB() {

    }

    void pressSta() {
        if (pNumber == 0) {
            GamePanel.changeGameState();
        }
    }

    void pressLTh() {
        
    }

    void pressSel() {
        if (pNumber == 0 && GamePanel.gameState == GamePanel.MENU) {
            GamePanel.menu.selectButton();
        }
    }

    void pressRTh() {
        
    }

    void pressU() {
        if (pNumber == 0 && GamePanel.gameState == GamePanel.MENU) {
                GamePanel.menu.moveCursor(0, -1);
        }
    }

    void pressR() {
        if (pNumber == 0 && GamePanel.gameState == GamePanel.MENU) {
                GamePanel.menu.moveCursor(1, 0);
                GamePanel.menu.right();
        }
    }

    void pressD() {
        if (pNumber == 0 && GamePanel.gameState == GamePanel.MENU) {
                GamePanel.menu.moveCursor(0, 1);
        }
    }

    void pressL() {
        if (pNumber == 0 && GamePanel.gameState == GamePanel.MENU) {
                GamePanel.menu.moveCursor(-1, 0);
                GamePanel.menu.left();
        }
    }

    void axesLJoy(float DX, float DY) {
        if (pNumber == 0 && GamePanel.gameState == GamePanel.MENU) {
                
        } else if (GamePanel.gameState == GamePanel.PLAY) {
            DELTA.set(DX,DY);
        }
    }

    void axesRJoy(float DX, float DY) {
        if (pNumber == 0 && GamePanel.gameState == GamePanel.MENU) {
                
        } else if (GamePanel.gameState == GamePanel.PLAY) {
            aimAngle = new Vec2(DX,DY);
            angle = refreshAngle(aimAngle.getAngle()); 
            catchHitbox.setAngle(angle);
        }
    }

    void leftTrigger(float axesS) {
        if (pNumber == 0 && GamePanel.gameState == GamePanel.MENU) {
            if(axesS > 0.7 && leftTrig<0.7) {
                GamePanel.menu.leftTrigger();
            }
        } else if (GamePanel.gameState == GamePanel.PLAY) {
            if(axesS > 0.7 && leftTrig < 0.7) {
                catchBall();
            } 
        }   
        leftTrig = axesS;
    }

    void rightTrigger(float axesS) {
        if (pNumber == 0 && GamePanel.gameState == GamePanel.MENU) {
            if(axesS > 0.7 && rightTrig<0.7) {
                GamePanel.menu.rightTrigger();
            }
        } else if (GamePanel.gameState == GamePanel.PLAY) {
            if(axesS > 0.7 && rightTrig<0.7) {
                throwBall();
            } 
        }  
        rightTrig = axesS;
    }
}
