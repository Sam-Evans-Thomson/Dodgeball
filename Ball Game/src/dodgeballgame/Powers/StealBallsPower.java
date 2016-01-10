/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dodgeballgame.Powers;

import dodgeballgame.GamePanel;
import dodgeballgame.Player.Player;
import dodgeballgame.Tools;
import dodgeballgame.Vec2;
import java.awt.Color;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 *
 * @author Sam
 */
public class StealBallsPower extends Power{

    public StealBallsPower(Vec2 pos) {
        super(pos);
        color = new Color(100,255,100);
        try {
            image = ImageIO.read(new File("Images/Powers/stealBalls.png"));
        } catch (IOException e) {
        }
        image = Tools.sizeImage(image, r);
    }
    
    @Override
    public void applyEffect(Player p) {
        Player victim = new Player(0,0,0,0);
        victim.numBalls = 0;
        for (Player plyr : GamePanel.playerArray) {
            if (plyr.team != p.team && plyr.numBalls >= victim.numBalls) victim = plyr;
        }
        p.numBalls += victim.numBalls;
        victim.numBalls = 0;
    }
    
    @Override
    public Power copy() {
        return new StealBallsPower(pos);
    }
}
