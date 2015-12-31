/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dodgeballgame.Player;

import dodgeballgame.GamePanel;
import dodgeballgame.HitBox;
import dodgeballgame.Timer;
import dodgeballgame.Tools;
import dodgeballgame.Vec2;
import java.awt.Color;
import java.awt.Graphics2D;

/**
 *
 * @author Sam
 */
public class Player {
    
    // Variables shared between Components
    public int H, W;
    public Vec2 pos;
    public final int pNumber,gPad,team;
    public Vec2 delta;                                      // Change in position
    private float d;                                        // time delta
    
    // need to figure these out
    public double angle;                        // Catch Area + Aiming angle
    public Vec2 aimAngle;                          
    public double throwAngle;  

    // Timers
    public Timer hbTimer;
    public Timer catchTimer;
    
    //States
    public boolean solid;       // Does the player collide with balls?

    // INPUTS
    public float leftTrig, rightTrig;
    public Vec2 DELTA;          // The values directly from left Thumb stick.
    
    // Settings
    public static double invincibleTime;    //The time length of invincibility
    public static int pointsPerGoal;
    public static int startHealth;
    public static int startBalls;
    
    // Current Values
    public int health;
    public int numBalls;
    public int numPowerUps;
    public double catchAngle, radius;   //The radius and angle of the catch area
    
    // Components
    public PlayerGraphicsComponent graphicsComp;
    public PlayerPhysicsComponent physicsComp;
    public PlayerSoundComponent soundComp;
    public InputComponent inputComponent;
    

    public Player(int team, int pNumber, double x, double y) {
        this.pNumber = gPad = pNumber;
        pos = new Vec2(x,y);
        this.team = team;

        init();
    }
    
     /*************************************************************/
    // INITIALIZATION
    //
    
    private void init() {
        initStats();
        
        physicsComp = new PlayerPhysicsComponent(this);
        graphicsComp = new PlayerGraphicsComponent(this);
        soundComp = new PlayerSoundComponent(this);
        inputComponent = (pNumber == 0) ? new PrimeInputComponent(this) : new InputComponent(this);
        
        physicsComp.init();
        graphicsComp.init();
        soundComp.init();

        DELTA = new Vec2(0,0);
        aimAngle = new Vec2(0,0);
        delta = new Vec2(0,0);

        invincibleTime = 2d;
        
        hbTimer = new Timer();
        catchTimer = new Timer();

    }
    
    public void initStats() {
        health = startHealth;
        numBalls = startBalls;
        
        numPowerUps = 0;
        radius = 150;
        catchAngle = (1.2*Math.PI)/4.0;
        W = H = 100; 

    }
    
    /*************************************************************/
    // UPDATE
    //
    
    public void update(float d) {
        physicsComp.update(d);
    }

    /*************************************************************/
    // RENDERING
    //
    
    public void render(Graphics2D g) {
        graphicsComp.render(g);
    }
    
    /*************************************************************/
    // COMPONENT CALLS
    //
    
    public void setCatchGlow() {
        graphicsComp.setCatchGlow();
    }
    
    public void setPowerUpGlow(Color color) {
        graphicsComp.setPowerUpGlow(color);
    }
    
    public HitBox getCatchHitbox() {
        return physicsComp.catchHitbox;
    }
    
    public HitBox getPlayerHitbox() {
        return physicsComp.playerHitbox;
    }
    
    public void setGetHit() {
        graphicsComp.hitPlayer();
    }
    
    public void refreshCatchTimer() {
        catchTimer.refresh();
    }
    
    /*************************************************************/
    // PLAYER ACTIONS
    //
    
    public void throwBall() {
        physicsComp.throwBall();
    }
    
    public void catchBall() {
        physicsComp.catchBall();
    }
    
    ///// EVENTS //////
    
    public void hitPlayer() {
        hbTimer.refresh();
        solid = false;
        GamePanel.changeScore(1-team,1);
        GamePanel.powerUpManager.addPowerUp(1-team);
        GamePanel.powerUpManager.addBall(1-team); 
        if (health > 1) {
            health--;
            graphicsComp.hitPlayer();
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
            graphicsComp.hitPlayer();           
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
         
        initStats();

        DELTA = new Vec2(0,0);
        aimAngle = new Vec2(0,0);
        numBalls = 0;

    }

    public void scoreGoal(int team) {
        GamePanel.changeScore(team, pointsPerGoal);
        numBalls++;
        catchTimer.refresh();
    }

    
    /*************************************************************/
    // INPUTS
    //
    
    
}