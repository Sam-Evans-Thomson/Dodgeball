/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dodgeballgame.Powers;

import dodgeballgame.Vec2;
import java.awt.Color;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 *
 * @author Sam
 */
public class NoPower extends Power{

    public NoPower(Vec2 pos) {
        super(pos);
        try {
            image = ImageIO.read(new File("Images/Powers/noPower.png"));
        } catch (IOException e) {
        }
    }
    
}
