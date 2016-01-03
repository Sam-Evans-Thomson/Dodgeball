/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dodgeballgame.Menus;

import dodgeballgame.GamePanel;
import dodgeballgame.Player.Player;
import dodgeballgame.Tools;
import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageFilter;
import java.awt.image.ImageProducer;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.GrayFilter;

/**
 *
 * @author Sam
 */
public class CharacterMenu extends Menu{
    
    ArrayList<PlayerImages> playerImages = new ArrayList();
    private final int NUM_CHARACTERS = 5;
    
    private int[] pos = {NUM_CHARACTERS,1}; 
    
    public int xPos = INNER_X_START;
    public int yPos = INNER_Y_START;
    public int width = INNER_MENU_WIDTH;
    public int height = INNER_MENU_HEIGHT - HEIGHT/10;
    
    public int buttonH = INNER_MENU_HEIGHT / 50;
    public int buttonW = INNER_MENU_WIDTH / 40;
    public int buttonXOffset = INNER_MENU_WIDTH/5;
    
    public CharacterMenu() {
        positions = pos;
        loadImages();
    }
    
    private void loadImages() {
        for(int i = 0; i<NUM_CHARACTERS; i++) {
            BufferedImage imageA, imageB;
            try{
                imageA = ImageIO.read(new File("Images/Players/player" + i + "a.png"));
                imageB = ImageIO.read(new File("Images/Players/player" + i + "b.png"));
                
                playerImages.add(new PlayerImages(imageA, imageB));
            } catch (IOException e) {}
        }
    }
    
    @Override
    public void renderMenu(Graphics2D g) {
        
        int xOffset = 140;
        
        for (int ctrl = 0; ctrl < GamePanel.numControllers; ctrl++) {
            for(int chr = 0; chr < 5; chr++) {
                
                int xPos2 = width/4 + xPos;
                int yPos2 = ctrl*height/3 + yPos;

                int cursorPlace = (cursors[ctrl][0] + chr) % NUM_CHARACTERS;
                if(cursorPlace < 0) cursorPlace += NUM_CHARACTERS; 
                
                BufferedImage image = Tools.sizeImage(playerImages.get(cursorPlace).getA(),100);
                
                if(chr != 0) {
                    ImageFilter filter = new GrayFilter(true, 30);  
                    ImageProducer producer = new FilteredImageSource(image.getSource(), filter);  
                    Image greyImage = Toolkit.getDefaultToolkit().createImage(producer); 
                    g.drawImage(greyImage,xPos2 + chr*xOffset, yPos2, null);
                } else {
                    g.drawImage(image,xPos2 + chr*xOffset, yPos2, null);
                }         
            }
        }
    }
    
    public void chooseCharacter(Player p) {
        PlayerImages im = playerImages.get(cursors[p.pNumber][0]);
        p.graphicsComp.changeImages(im.getA(),im.getB());
        System.out.println("is this happening");
    }
    
    public void select(Player p) {
        chooseCharacter(p);
    }
    
    public class PlayerImages {
        
        public BufferedImage imageA;
        public BufferedImage imageB;
        
        public PlayerImages(BufferedImage a, BufferedImage b) {
            imageA = a;
            imageB = b;
        }
        
        public BufferedImage getA() {
            return imageA;
        }
        
        public BufferedImage getB() {
            return imageB;
        }
    }
    
}
