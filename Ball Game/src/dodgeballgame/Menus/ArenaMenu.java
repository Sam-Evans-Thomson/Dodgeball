/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dodgeballgame.Menus;

import dodgeballgame.ArenaManager;
import dodgeballgame.GamePanel;
import dodgeballgame.Tools;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 *
 * @author Sam
 */
public class ArenaMenu extends Menu {
    
    public ArenaManager manager = GamePanel.arenaManager;
    
    public boolean accept;
    
    public BufferedImage arrowRight;
    public BufferedImage arrowLeft;
   
    
    public ArenaMenu() {
        accept = false;
        positions = new int[]{3,1};
        
        try {
            arrowRight = ImageIO.read(new File("Images/arrowRight.png"));
            arrowLeft = ImageIO.read(new File("Images/arrowLeft.png"));
        } catch (IOException e) { }
        
        arrowRight = Tools.sizeImage(arrowRight, 100);
        arrowLeft = Tools.sizeImage(arrowLeft, 100);
    }
    
    @Override
    public void renderMenu(Graphics2D g) {
        BufferedImage preview = manager.getArenaDisplay(cursors[0][0]);
        preview = Tools.sizeImage(preview, 1000);
        g.drawImage(preview, WIDTH/2 - 500, HEIGHT/4, null);
        g.drawImage(arrowLeft, WIDTH/6, HEIGHT/2 - arrowLeft.getHeight()/2, null);
        g.drawImage(arrowRight, 5*WIDTH/6, HEIGHT/2 - arrowRight.getHeight()/2, null);
    }
    
    @Override
    public void back() {
        GamePanel.soundManager.menu(7);
        if (accept) {
            accept = false;
        } else {
        GamePanel.menu = GamePanel.startMatchSettingsMenu;
        }
    }
    
    @Override
    public void select() {
        manager.setArena(cursors[0][0]);
    }
}
