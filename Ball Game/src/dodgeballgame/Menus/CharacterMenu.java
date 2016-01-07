/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dodgeballgame.Menus;

import dodgeballgame.Cursor;
import dodgeballgame.GamePanel;
import dodgeballgame.Player.Player;
import dodgeballgame.Tools;
import java.awt.AlphaComposite;
import java.awt.Color;
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
    
    private final int NUM_CHARACTERS = 9;
    
    public int xPos = INNER_X_START;
    public int yPos = INNER_Y_START;
    public int width = INNER_MENU_WIDTH;
    public int height = INNER_MENU_HEIGHT - HEIGHT/10;
    
    public int buttonH = INNER_MENU_HEIGHT / 50;
    public int buttonW = INNER_MENU_WIDTH / 40;
    public int buttonXOffset = INNER_MENU_WIDTH/5;
    
    private BufferedImage[] playerWords;
    private Image[] greyFaces;
    private BufferedImage[] colourFaces;
    
    public Cursor[] cursors = new Cursor[4];
    
    public CharacterMenu() {
        for (int i = 0; i < 4; i++) cursors[i] = new Cursor(NUM_CHARACTERS, 1);
        loadImages();
    }
    
        
    @Override    
    public void moveCursor(int playerNumber, int x, int y) {
        cursors[playerNumber].moveCursor(x,y);
    }
    
    private void loadImages() {
        playerWords = new BufferedImage[5];
        greyFaces = new Image[NUM_CHARACTERS];
        colourFaces = new BufferedImage[NUM_CHARACTERS];
        ImageFilter filter = new GrayFilter(true, 50); 
        for(int i = 0; i<NUM_CHARACTERS; i++) {
            BufferedImage imageA, imageB;
            try{
                imageA = ImageIO.read(new File("Images/Players/player" + i + "a.png"));
                imageB = ImageIO.read(new File("Images/Players/player" + i + "b.png"));
                playerWords[0] = ImageIO.read(new File("Images/player1.png"));
                playerWords[1] = ImageIO.read(new File("Images/player2.png"));
                playerWords[2] = ImageIO.read(new File("Images/player3.png"));
                playerWords[3] = ImageIO.read(new File("Images/player4.png"));
                playerWords[4] = ImageIO.read(new File("Images/player2b.png"));
                playerImages.add(new PlayerImages(imageA, imageB));
            } catch (IOException e) {}

            BufferedImage image = Tools.sizeImage(playerImages.get(i).getA(),100);
            colourFaces[i] = image;
            ImageProducer producer = new FilteredImageSource(image.getSource(), filter); 
            greyFaces[i] = Toolkit.getDefaultToolkit().createImage(producer);
        }
        playerWords[0] = Tools.scaleImage(playerWords[0], 0.7);
        playerWords[1] = Tools.scaleImage(playerWords[1], 0.7);
        playerWords[2] = Tools.scaleImage(playerWords[2], 0.7);
        playerWords[3] = Tools.scaleImage(playerWords[3], 0.7);
        playerWords[4] = Tools.scaleImage(playerWords[4], 0.7);
        
        
    }
    
    @Override
    public void renderMenu(Graphics2D g) {
        
        int xOffset = 140;
        int xPos2 = width/3 + xPos + 140;
        
        for (int ctrl = 0; ctrl < GamePanel.numPlayers; ctrl++) {
            for(int chr = 0; chr < 5; chr++) {

                int yPos2 = ctrl*height/3 + yPos;

                int cursorPlace = (cursors[ctrl].x + chr) % NUM_CHARACTERS;
                if(cursorPlace < 0) cursorPlace += NUM_CHARACTERS; 

                if(chr != 0) {
                    g.drawImage(greyFaces[cursorPlace],xPos2 + chr*xOffset, yPos2, null);
                } else {
                    g.drawImage(colourFaces[cursorPlace],xPos2 + chr*xOffset, yPos2, null);
                }

            }
        }
        for (int i = 0; i < GamePanel.numPlayers; i++) {
            BufferedImage img = GamePanel.playerArray.get(i).graphicsComp.playerImage;
            
            if (GamePanel.numPlayers == 2 && i == 1) {
                g.drawImage(playerWords[4], xPos + 140, yPos + i*height/3, null);
            } else {
                g.drawImage(playerWords[i], xPos + 140, yPos + i*height/3, null);
            }
            g.setColor(new Color(200,200,200));
            g.drawImage(img, xPos, yPos + i*height/3, null);
            if (i != 0) g.fillRect(xPos, yPos + (i)*height/3 - 30, width, 3);
        }
    }
    
    public void chooseCharacter(Player p) {
        PlayerImages im = playerImages.get(cursors[p.pNumber].x);
        p.graphicsComp.changeImages(im.getA(),im.getB());
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
    
    @Override
    public void back() {
        GamePanel.soundManager.menu(7);
        GamePanel.menuManager.changeMenu("START");
    }
    
}
