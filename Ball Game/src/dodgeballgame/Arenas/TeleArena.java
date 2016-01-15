/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dodgeballgame.Arenas;

import dodgeballgame.Arenas.ArenaGraphicsComponents.TeleGC;
import dodgeballgame.Environment.BallTeleporter;
import dodgeballgame.HitBoxes.*;
import dodgeballgame.Vec2;

/**
 *
 * @author Sam
 */
public class TeleArena extends Arena{

        
    public TeleArena() {
        super();
        graphicsComp = new TeleGC(this);
        graphicsComp.imgPath = "Images/Arenas/vertical.png";
        arenaName = "TELEPORTER";
    }

    @Override
    public void initTeamAreas() {
        teamAreas = new Vec2[2][2];
        teamAreas[0] = new Vec2[]{new Vec2(WIDTH/6 + 40,0), new Vec2(WIDTH/2,HEIGHT)};
        teamAreas[1] = new Vec2[]{new Vec2(WIDTH/2,0), new Vec2(5*WIDTH/6 - 40,HEIGHT)};
    }
    
    @Override
    public Vec2[] getPlayerPositions(int numPlayers) {
        Vec2[] positions = new Vec2[numPlayers];
        
        if(numPlayers == 2) {
            positions[0] = new Vec2(WIDTH/4,HEIGHT/4);
            positions[1] = new Vec2(3*WIDTH/4, HEIGHT/4);
        } else if (numPlayers == 3) {
            positions[0] = new Vec2(WIDTH/4,HEIGHT/4);
            positions[1] = new Vec2(WIDTH/4,3*HEIGHT/4);
            positions[2] = new Vec2(3*WIDTH/4, HEIGHT/4);
        } else if (numPlayers == 4) {
            positions[0] = new Vec2(WIDTH/4,HEIGHT/4);
            positions[1] = new Vec2(WIDTH/4,3*HEIGHT/4);
            positions[2] = new Vec2(3*WIDTH/4, HEIGHT/4);
            positions[3] = new Vec2(3*WIDTH/4, 3*HEIGHT/4);
        }
        return positions;
    }
    
    @Override
    public void initHitBoxes() {
        // Team 1 middle Edge
        LineHitbox hbC1 = new LineHitbox (WIDTH/2,0, Hitbox.DEG_90);
        arenaTeam1Hitbox.add(hbC1);
        
        // Team 2 middle Edge
        LineHitbox hbC2 = new LineHitbox (WIDTH/2,0, Hitbox.DEG_270);
        arenaTeam2Hitbox.add(hbC2);
        
        // Team1 Goal protectors
        RectHitbox mainVert = new RectHitbox(WIDTH/2, HEIGHT/2, 20,HEIGHT/2);
        arenaBallHitbox.add(mainVert);
        renderHitbox.add(mainVert);
        arenaPlayerHitbox.add(mainVert);
        
        // Team2 Goal protectors
        RectHitbox mainHor = new RectHitbox(WIDTH/2, HEIGHT/2, WIDTH/2,20);
        arenaBallHitbox.add(mainHor);
        renderHitbox.add(mainHor);
        arenaPlayerHitbox.add(mainHor);
        
        initTeleporters();
    }
    
    public void initTeleporters() {
        RectHitbox tele1 = new RectHitbox(0,0,10,HEIGHT/2-20);
        RectHitbox tele2 = new RectHitbox(WIDTH, 0, 10, HEIGHT/2-20);
        addTeleporter(new BallTeleporter(tele1,tele2, 1));
        
        RectHitbox tele3 = new RectHitbox(0,HEIGHT,10,HEIGHT/2-20);
        RectHitbox tele4 = new RectHitbox(WIDTH, HEIGHT, 10, HEIGHT/2-20);
        addTeleporter(new BallTeleporter(tele3,tele4, 1));
        
        RectHitbox tele5 = new RectHitbox(0,0,WIDTH/2 - 20,10);
        RectHitbox tele6 = new RectHitbox(0, HEIGHT, WIDTH/2 - 20, 10);
        addTeleporter(new BallTeleporter(tele5,tele6, 0));
        
        RectHitbox tele7 = new RectHitbox(WIDTH,0,WIDTH/2 - 20,10);
        RectHitbox tele8 = new RectHitbox(WIDTH, HEIGHT, WIDTH/2 - 20, 10);
        addTeleporter(new BallTeleporter(tele7,tele8, 0));
    }
    
        // GOALS
    @Override
    public void cornerGoals() {
        int goalSize = 50;
        CircleHitbox goal1 = new CircleHitbox(WIDTH/2 - goalSize,HEIGHT/2 - goalSize, goalSize);
        arenaTeam2Goal.add(goal1);
        
        CircleHitbox goal2 = new CircleHitbox(WIDTH/2 - goalSize,HEIGHT/2 + goalSize, goalSize);
        arenaTeam2Goal.add(goal2);
        
        CircleHitbox goal3 = new CircleHitbox(WIDTH/2 + goalSize,HEIGHT/2 - goalSize, goalSize);
        arenaTeam1Goal.add(goal3);
        
        CircleHitbox goal4 = new CircleHitbox(WIDTH/2 + goalSize,HEIGHT/2 + goalSize, goalSize);
        arenaTeam1Goal.add(goal4);
    }
    
    @Override
    public void specialGoals(){
        RectHitbox goal1 = new RectHitbox(WIDTH/6 + 25, HEIGHT/3 - 15, WIDTH/6 + 115, 2*HEIGHT/3 + 15,1);
        arenaTeam2Goal.add(goal1);
        
        RectHitbox goal2 = new RectHitbox(5*WIDTH/6 - 115, HEIGHT/3 - 15, 5*WIDTH/6 - 25, 2*HEIGHT/3 + 15,1);
        arenaTeam1Goal.add(goal2);
    }
    
    @Override
    public void sideGoals() {
        RectHitbox goal1 = new RectHitbox(WIDTH/6,HEIGHT/3+20, WIDTH/6 + 20, 2*HEIGHT/3 - 20,1);
        arenaTeam2Goal.add(goal1);
        
        RectHitbox goal2 = new RectHitbox(5*WIDTH/6 - 20,HEIGHT/3+20, 5*WIDTH/6, 2*HEIGHT/3 - 20,1);
        arenaTeam1Goal.add(goal2);
    }
    
    @Override
    public Arena copy() {
        TeleArena temp = new TeleArena();
                temp.init();
        return temp;
    }
    
}