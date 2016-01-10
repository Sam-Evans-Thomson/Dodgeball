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
import java.util.ArrayList;
import java.util.Random;
import javax.imageio.ImageIO;

/**
 *
 * @author Sam
 */
public class StealPowerPower extends Power{

    public StealPowerPower(Vec2 pos) {
        super(pos);
        color = new Color(100,255,100);
        try {
            image = ImageIO.read(new File("Images/Powers/stealPower.png"));
        } catch (IOException e) {
        }
        image = Tools.sizeImage(image, r);
    }
    
    @Override
    public void applyEffect(Player p) {
        ArrayList<Player> players = new ArrayList();
        for (Player plyr : GamePanel.playerArray) {
            if (plyr.team != p.team && !plyr.currentPower.equals(plyr.noPower)) {
                players.add(plyr);
            }
        }
        
        if (players.size() > 0) {
            Random rand = new Random();
            Player victim = players.get(rand.nextInt(players.size()));
            Power power = victim.currentPower.copy();
            p.currentPower = power;
            victim.currentPower = victim.noPower;
        }
    }
    
    @Override
    public Power copy() {
        return new StealPowerPower(pos);
    }
}
