/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dodgeballgame.Powers;

import dodgeballgame.GamePanel;
import dodgeballgame.Items.Item;
import dodgeballgame.Player.Player;
import dodgeballgame.SoundManager;
import dodgeballgame.Vec2;

/**
 *
 * @author Sam
 */
public class Power extends Item{

    
    public Power(Vec2 pos) {
        super(pos);
        r = 80;
    }
    
    public void setPos(Vec2 pos) {
        this.pos = pos;
        this.hb.setPos(pos);
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
        }
    }
    
    public void collected(Player p) {
        p.currentPower = this;
        GamePanel.soundManager.power();
    }
    
    @Override
    public void applyEffect(Player p) {
        
    }
    
    public Power copy() {
        return new Power(pos);
    }
}
