/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dodgeballgame.Arenas;

import dodgeballgame.Arenas.ArenaGraphicsComponents.BarracksGC;
import dodgeballgame.GamePanel;
import dodgeballgame.HitBoxes.*;
import dodgeballgame.Player.Player;
import dodgeballgame.Vec2;
import java.util.ArrayList;

/**
 *
 * @author Sam
 */
public class MovingPanelArena extends Arena{

    ArrayList<Hitbox> movingHitbox;
    ArrayList<Vec2> startPositions;
    ArrayList<Vec2> positions;
    ArrayList<Vec2> prevPositions;
    double distance;
    double delta;
        
    public MovingPanelArena() {
        super();
        graphicsComp = new BarracksGC(this);
        graphicsComp.imgPath = "Images/Arenas/vertical.png";
        arenaName = "SLIDER";
        distance = HEIGHT/5;
        delta = 0;
    }
    
    @Override
    public void update(double d) {
        delta+=d;
        
        Vec2 move = new Vec2(0,distance*Math.sin(delta));
        for (int i = 0; i < positions.size(); i++) {    
            Vec2 newPos = startPositions.get(i).add(move);
            positions.set(i, newPos);
            movingHitbox.get(i).pos = positions.get(i);
            
            // collision checking
            for(int j = 0; j < GamePanel.numPlayers; j++) {
                Player player = GamePanel.playerArray.get(j);
                Vec2 vec = positions.get(i).take(prevPositions.get(i));
                if (movingHitbox.get(i).collision(player.physicsComp.playerHitbox)) {
                    player.physicsComp.pushedBy(movingHitbox.get(i), vec);
                }
            }
        }
        
        for (int i = 0; i < positions.size(); i++) {  
            prevPositions.get(i).set(positions.get(i));
        }
    }
    
    @Override
    public void initHitBoxes() {
        movingHitbox = new ArrayList<>();
        positions = new ArrayList<>();
        startPositions = new ArrayList<>();
        prevPositions = new ArrayList<>();
        
        
        
        // Team 1 middle Edge
        LineHitbox hbC1 = new LineHitbox (WIDTH/2,0, Hitbox.DEG_90);
        arenaTeam1Hitbox.add(hbC1);
        
        // Team 2 middle Edge
        LineHitbox hbC2 = new LineHitbox (WIDTH/2,0, Hitbox.DEG_270);
        arenaTeam2Hitbox.add(hbC2);

        addVec(new Vec2(WIDTH/2,HEIGHT/2 - HEIGHT/6));
        addVec(new Vec2(WIDTH/2,HEIGHT/2 + HEIGHT/6));
        addVec(new Vec2(WIDTH/2,HEIGHT/2));
        
        RectHitbox movingPanel = new RectHitbox(WIDTH/2, HEIGHT/2,40,HEIGHT/6);
        CircleHitbox mp1 = new CircleHitbox(WIDTH/2,HEIGHT/2 - HEIGHT/6, 40);
        CircleHitbox mp2 = new CircleHitbox(WIDTH/2,HEIGHT/2 + HEIGHT/6, 40);
        movingHitbox.add(mp1);
        movingHitbox.add(mp2);
        movingHitbox.add(movingPanel);
        for(Hitbox h : movingHitbox) {
            arenaPlayerHitbox.add(h);
            arenaBallHitbox.add(h);
            renderHitbox.add(h);
        }
    }
    
    @Override
    public void specialGoals(){
        addVec(new Vec2(WIDTH/2 - 45,HEIGHT/2));
        addVec(new Vec2(WIDTH/2 + 45,HEIGHT/2));
        RectHitbox goal1 = new RectHitbox(WIDTH/2 - 45,HEIGHT/2, 5, HEIGHT/6);
        arenaTeam2Goal.add(goal1);
        movingHitbox.add(goal1);
        
        RectHitbox goal2 = new RectHitbox(WIDTH/2 + 45,HEIGHT/2, 5, HEIGHT/6);
        arenaTeam1Goal.add(goal2);
        movingHitbox.add(goal2);
    }
    
    public void addVec(Vec2 vec) {
        startPositions.add(new Vec2(vec));
        prevPositions.add(new Vec2(vec));
        positions.add(new Vec2(vec));
    }

    @Override
    public Arena copy() {
        MovingPanelArena temp = new MovingPanelArena();
        temp.init();
        return temp;
    }
    
}