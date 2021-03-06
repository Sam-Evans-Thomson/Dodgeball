/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dodgeballgame.Arenas.ArenaGraphicsComponents;

import dodgeballgame.Arenas.Arena;
import dodgeballgame.Environment.BallTeleporter;
import dodgeballgame.HitBoxes.Hitbox;
import java.awt.Color;
import java.awt.Graphics2D;

/**
 *
 * @author Sam
 */
public class TeleGC extends ArenaGC{

    public TeleGC(Arena arena) {
        super(arena);
    }
    
    @Override
    public void init() {
        super.init();
    }
    
    @Override
    public void renderSpecific(Graphics2D g) {
        g.setColor(Color.WHITE);
        for(Hitbox hb : arena.renderHitbox) hb.render(g);
        g.setColor(new Color(170,0,255));
        for(BallTeleporter bt : arena.teleporters) bt.render(g);
    }
}
