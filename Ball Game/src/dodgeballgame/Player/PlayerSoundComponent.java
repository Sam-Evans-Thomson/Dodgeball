/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dodgeballgame.Player;

/**
 *
 * @author Sam
 */
public class PlayerSoundComponent implements PlayerComponent{
    
    Player p;
    
    public PlayerSoundComponent(Player p) {
        this.p = p;
    }
    
    @Override
    public void init() {
        
    }

    @Override
    public void update(float d) {
    }
    
}
