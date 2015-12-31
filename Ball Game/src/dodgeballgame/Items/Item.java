/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dodgeballgame.Items;


import dodgeballgame.GamePanel;
import dodgeballgame.HitBox;
import dodgeballgame.ImageEditor;
import dodgeballgame.Player.Player;
import dodgeballgame.Vec2;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import sun.audio.AudioStream;

/**
 *
 * @author Sam
 */
public class Item {
    
    public Vec2 pos;
    private boolean collected;
    double W, H;
    public double r;
    public HitBox hb;
    public Color color;
    public BufferedImage image;
    
    public ImageEditor imageEditor;
    
    public boolean[] inCatchArea;
    
    public Item(Vec2 pos) {
        this.pos = pos;
            
        init();
    }
    
    private void init() {
        collected = false;
        W = H = 30;
        r = 40;
        hb = new HitBox(pos);
        hb.makeCircle(r);
        inCatchArea = new boolean[GamePanel.NUM_PLAYERS];
        
    }
    
    public void applyEffect() {
        
    }
    
    public void applyEffect(Player p) {
        
    }
    
    public void incPowerUpCount(Player p) {
        p.numPowerUps++;
    }
    
    public void update() {
        for(Player p : GamePanel.playerArray) {
            if(p.getPlayerHitbox().collision(hb)) {
                applyEffect(p);
                incPowerUpCount(p);
                p.setItemGlow(color);
                GamePanel.itemArray.remove(this);
                return;
            }
            if(p.getCatchHitbox().collisionPoint(pos)) {
                inCatchArea[p.pNumber] = true;
            } else inCatchArea[p.pNumber] = false;
        }
    }
    
    public void render(Graphics2D g) {
        imageEditor = new ImageEditor(image);
        BufferedImage bi = imageEditor.scale(r/image.getHeight());
        g.drawImage(bi, (int)(pos.getX()-W/2), (int)(pos.getY()-H/2), null);
    }
    
    public void hitBall() {
        GamePanel.itemArray.remove(this);
    }
    
}
