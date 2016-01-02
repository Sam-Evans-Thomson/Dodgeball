/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dodgeballgame.Player;

import dodgeballgame.GamePanel;
import dodgeballgame.HitBox;
import dodgeballgame.Powers.NoPower;
import dodgeballgame.Powers.Power;
import dodgeballgame.Timer;
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
    public static int killsPerPower;
    public static int goalsPerPower;
    public static int hitsPerPower;
    public static int pointsPerPower;
    public static double invincibleTime;    //The time length of invincibility
    public static int pointsPerGoal;
    public static int startHealth;
    public static int startBalls;
    
    // Current Values
    public int health;
    public int numBalls;
    public int numItems;
    
    public int pointsCount;
    public int killsCount;
    public int goalsCount;
    public int hitsCount;
    
    public double catchAngle, radius;   //The radius and angle of the catch area
    public Power currentPower;
    public Power noPower = new NoPower(new Vec2(0,0));
    public Power activePower;
    
    // stats
    public int points;
    public int kills;
    public int ownKills;
    public int goals;
    public int ownGoals;
    public int hits;
    public int ownHits;
    public int gotHits;
    public int ownGotHits;
    public int catches;
    public int ballThrows;
    public int items;
    public int powers;
    public int deaths;
    public double distanceTravelled;
    
    // Components
    public PlayerGraphicsComponent graphicsComp;
    public PlayerPhysicsComponent physicsComp;
    public PlayerSoundComponent soundComp;
    public InputComponent inputComponent;
    public PlayerStateComponent stateComp;
    

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
        stateComp = new PlayerStateComponent(this);
        
        physicsComp.init();
        graphicsComp.init();
        soundComp.init();

        DELTA = new Vec2(0,0);
        aimAngle = new Vec2(0,0);
        delta = new Vec2(0,0);

        invincibleTime = 2d;
        
        hbTimer = new Timer();
        catchTimer = new Timer();
        
        kills = goals = ownKills = ownGoals = hits = ownHits = deaths = 0;
        gotHits = ownGotHits = points = 0;
        goalsCount = hitsCount = pointsCount = killsCount = 0;
        distanceTravelled = 0d;

    }
    
    // These are stats that get set on death.
    public void initStats() {
        currentPower = noPower;
        activePower = noPower;
        health = startHealth;
        numBalls = startBalls;
        
        numItems = 0;
        radius = 150;
        catchAngle = (1.2*Math.PI)/4.0;
        W = H = 100; 

    }
    
    // ***********************************************************/
    // UPDATE
    //
    
    /**
     * @param d is the time delta for this frame.
     * 
     **/
    public void update(float d) {
        stateComp.update(d);
        physicsComp.update(d);
    }

    // *************************************************************/
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
    
    public void setItemGlow(Color color) {
        graphicsComp.setItemGlow(color);
    }
    
    public void setPowerGlow(Color color) {
        graphicsComp.setPowerGlow(color);
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
    public void usePower() {
        powers++;
        currentPower.applyEffect(this);
        currentPower = noPower;
    }
    
    public void hitPlayer(Player p, int i) {
        if (p.team != team) gotHits++;
        else ownGotHits++;
        
        GamePanel.changeScore(1-team,1);
        GamePanel.itemManager.addItem(1-team);
        GamePanel.itemManager.addBall(1-team); 
        hbTimer.refresh();
        solid = false;
        if (health > 1) {
            health-= i;
            graphicsComp.hitPlayer();           
        }
        else death();
    }
    
    public void hitOtherPlayer(Player p, int i) {
        if (p.team != team) {
            hits++;
            hitsCount++;
            points+=i;
            pointsCount+=i;
        }
        else ownHits++;
        spawnPowerCheck();
    }
    
    public void death() {
        deaths++;
        for (int i = 0; i < numBalls; i++) {
            GamePanel.itemManager.addBall(1-team);
        }
        for (int i = 0; i < numItems/2; i++) {
            GamePanel.itemManager.addItem(1-team);
        }
         
        initStats();

        DELTA = new Vec2(0,0);
        aimAngle = new Vec2(0,0);
        numBalls = 0;

    }
    
    public void killedPlayer(Player p) {
        if (p.team != team) {
            kills++;
            killsCount++;
        }
        else ownKills++;
        spawnPowerCheck();
    }

    public void scoreGoal(int team) {
        if (this.team == team) {
            goals++;
            goalsCount++;
            points += pointsPerGoal;
        }
        else ownGoals++;
        GamePanel.changeScore(team, pointsPerGoal);
        numBalls++;
        catchTimer.refresh();
        spawnPowerCheck();
    }
    
    private void spawnPowerCheck() {
        System.out.println("spawnPowerCHeck");
        if(pointsCount>=pointsPerPower) {
            GamePanel.powerManager.addPower(team);
            pointsCount = 0;
        }
        if(goalsCount>=goalsPerPower) {
            GamePanel.powerManager.addPower(team);
            goalsCount = 0;
        }
        if(killsCount>=killsPerPower) {
            GamePanel.powerManager.addPower(team);
            killsCount = 0;
        }
        if(hitsCount>=hitsPerPower) {
            GamePanel.powerManager.addPower(team);
            hitsCount = 0;
        }
    }

    
    /*************************************************************/
    // INPUTS
    //
    
    
}
