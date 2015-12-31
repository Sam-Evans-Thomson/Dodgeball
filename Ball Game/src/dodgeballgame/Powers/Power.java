/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dodgeballgame.Powers;

import dodgeballgame.GamePanel;
import dodgeballgame.HitBox;
import dodgeballgame.Items.Item;
import dodgeballgame.Player.Player;
import dodgeballgame.Vec2;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

/**
 *
 * @author Sam
 */
public class Power extends Item{

    
    public Power(Vec2 pos) {
        super(pos);
        r = 60;
    }
    
    @Override
    public void incPowerUpCount(Player p) {
        
    }
    
    @Override
    public void update() {
        for(Player p : GamePanel.playerArray) {
            if(p.getPlayerHitbox().collision(hb)) {
                collected(p);
                p.setPowerGlow(color);
                GamePanel.powerArray.remove(this);
                return;
            }
            if(p.getCatchHitbox().collisionPoint(pos)) {
                inCatchArea[p.pNumber] = true;
            } else inCatchArea[p.pNumber] = false;
        }
    }
    
    public void collected(Player p) {
        p.currentPower = this;
    }
    
    @Override
    public void applyEffect(Player p) {
        
    }
    
}
