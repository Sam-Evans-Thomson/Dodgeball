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
public class NoCatchPower extends Power{

    public NoCatchPower(Vec2 pos) {
        super(pos);
        color = new Color(100,255,100);
        try {
            image = ImageIO.read(new File("Images/Powers/noCatch.png"));
        } catch (IOException e) {
        }
        image = Tools.sizeImage(image, r);
    }
    
    @Override
    public void applyEffect(Player p) {
        for (Player player: GamePanel.playerArray) {
            if (player.team != p.team) {
                player.stateComp.noCatch();
            }
        }
    }
    
    @Override
    public Power copy() {
        return new NoCatchPower(pos);
    }
}
