/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dodgeballgame.Player;

import dodgeballgame.Arenas.ArenaManager;
import dodgeballgame.GamePanel;
import dodgeballgame.HitBoxes.ArcHitbox;
import dodgeballgame.HitBoxes.CircleHitbox;
import dodgeballgame.Powers.NoPower;
import dodgeballgame.Powers.Power;
import dodgeballgame.Powers.StealPowerPower;
import dodgeballgame.Timer;
import dodgeballgame.Vec2;
import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;

/**
 *
 * @author Sam
 */
public class Player {
    
    // Variables shared between Components
    public Vec2 startPos;
    public int r;
    public Vec2 pos;
    public final int pNumber,gPad,team;
    public Vec2 delta;                                      // Change in position
    private float d;                                        // time delta
    
    public double angle;                        // Catch Area + Aiming angle
    public Vec2 aimAngle;                          
    public double throwAngle;  

    // Timers
    public Timer hbTimer;
    
    //States
    public boolean solid;       // Does the player collide with balls?
    public boolean catchOn;
    public boolean isGhost;
    public boolean autoCatchOn;
    public boolean invincible;
    public boolean throwBoost;
    
    public String nextBall;

    // INPUTS
    public float leftTrig, rightTrig;
    public Vec2 DELTA;          // The values directly from left Thumb stick.
    
    // Settings
    public static int killsPerPower;
    public static int goalsPerPower;
    public static int hitsPerPower = 999;
    public static int pointsPerPower;
    public static double invincibleTime;    //The time length of invincibility
    public static int pointsPerGoal;
    public static int startHealth;
    public static int startBalls;
    public static int pointsPerHit;
    public static boolean knockoutOn;
    public static int pointsPerKill;
    public static int pointsPerDeath;
    public static int startingLives;
    
    // Current Values
    public int health;
    public int lives;
    public int numBalls;
    public int numItems;
    
    public int pointsCount;
    public int killsCount;
    public int goalsCount;
    public int hitsCount;
    
    public double catchAngle, radius;   //The radius and angle of the catch area
    public Power currentPower;
    public Power noPower = new NoPower(new Vec2(0,0));
    public ArrayList<Power> activePowers = new ArrayList();
    
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
        startPos = new Vec2(x,y);
        this.team = team;

        init();
    }
    
     /*************************************************************/
    // INITIALIZATION
    //
    
    private void init() {
        initStats();
        lives = startingLives;
        throwBoost = false;
        knockoutOn = false;
        isGhost = false;
        invincible = false;
        autoCatchOn = false;
        pointsPerKill = 0;
        pointsPerDeath = 0;
        nextBall = "NORMAL";
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
        
        kills = goals = ownKills = ownGoals = hits = ownHits = deaths = 0;
        gotHits = ownGotHits = points = 0;
        goalsCount = hitsCount = pointsCount = killsCount = 0;
        distanceTravelled = 0d;

    }
    
    // These are stats that get set on death.
    public void initStats() {
        currentPower = noPower;
        health = startHealth;
        numBalls = startBalls;
        
        numItems = 0;
        radius = 150;
        catchAngle = (1.2*Math.PI)/4.0;
        r = 50; 

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
    
    public ArcHitbox getCatchHitbox() {
        return physicsComp.catchHitbox;
    }
    
    public CircleHitbox getPlayerHitbox() {
        return physicsComp.playerHitbox;
    }
    
    public void setGetHit() {
        graphicsComp.hitPlayer();
    }
    
    public void throwBoost() {
        stateComp.throwBoost();
    }
    
    /*************************************************************/
    // PLAYER ACTIONS
    //
    
    public void becomeGhost() {
        catchOn = false;
        isGhost = true;
        solid = false;
        physicsComp.speed = 150;
        health = 0;
    }
    
    public void eatItem() {
        physicsComp.eatObject();
    }
    
    public void throwBall() {
        physicsComp.throwBall();
    }
    
    public void catchBall() {
        physicsComp.catchObject();
    }
    
    ///// EVENTS //////
    public void usePower() {
        powers++;
        
        if (currentPower instanceof StealPowerPower) {
            currentPower.applyEffect(this);
        } else {
            currentPower.applyEffect(this);
            currentPower = noPower;
        }
    }
    
    public void hitPlayer(Player p, int i) {
        
        if (p.team != team) gotHits++;
        else ownGotHits++;
        
        GamePanel.changeScore(1-team,pointsPerHit);
        GamePanel.itemManager.addItem(1-team);
        GamePanel.itemManager.addBall(1-team); 
        hbTimer.refresh();
        solid = false;
        if (health > 1) {
            health-= i;
            graphicsComp.hitPlayer();           
        }
        else death(p);
    }
    
    public void hitOtherPlayer(Player p, int i) {
        if (p.team != team) {
            hits++;
            hitsCount++;
            points+=pointsPerHit;
            pointsCount+=pointsPerHit;
        }
        else ownHits++;
        spawnPowerCheck();
    }
    
    public void death(Player p) {
        if (p!=null) p.killedPlayer(this);
        deaths++;
        lives--;
        if (knockoutOn && lives == 0) becomeGhost();
        else initStats();
        
        GamePanel.changeScore(team, pointsPerDeath);

        for (int i = 0; i < numBalls; i++) {
            GamePanel.itemManager.addBall(1-team);
        }
        for (int i = 0; i < numItems/2; i++) {
            GamePanel.itemManager.addItem(1-team);
        }
        DELTA = new Vec2(0,0);
        aimAngle = new Vec2(0,0);
        numBalls = 0;
        pos.set(startPos);
        hbTimer.refresh();
        graphicsComp.setDeathAnimation();
    }
    
    public void killedPlayer(Player p) {
        if (p.team != team) {
            kills++;
            killsCount++;
            GamePanel.changeScore(team, pointsPerKill);
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
        GamePanel.itemManager.addBall(1-team);
        spawnPowerCheck();
    }
    
    private void spawnPowerCheck() {
        System.out.println(killsCount);
        System.out.println(killsPerPower);
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
