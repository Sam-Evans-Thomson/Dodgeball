/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dodgeballgame.Arenas.ArenaGraphicsComponents;

import dodgeballgame.Arenas.Arena;
import dodgeballgame.HitBoxes.Hitbox;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;

/**
 *
 * @author Sam
 */
public class TunnelGC extends HorizontalGC{

    public TunnelGC(Arena arena) {
        super(arena);
    }
    
    @Override
    public void init() {
        super.init();
    }
    
    @Override
    public void renderHitboxes(Graphics2D g) {
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
        g.setColor(Color.WHITE);
        for (Hitbox hb : arena.renderHitbox) hb.render(g);
    }
        
    @Override
    public void renderSpecific(Graphics2D g) {
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
        g.setColor(Color.WHITE);
        int[] xPoints = new int[]{0,0,HEIGHT/3};
        int[] yPoints = new int[]{0,HEIGHT/3,0};
        g.fillPolygon(xPoints, yPoints, 3);
        xPoints = new int[]{0,0,HEIGHT/3};
        yPoints = new int[]{HEIGHT,2*HEIGHT/3,HEIGHT};
        g.fillPolygon(xPoints, yPoints, 3);
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f));
        g.fillRect(0,0, HEIGHT/2, HEIGHT);
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
    }

}
