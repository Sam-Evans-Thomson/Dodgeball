/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dodgeballgame.Menus;

import dodgeballgame.Cursor;
import dodgeballgame.GamePanel;
import dodgeballgame.ImageEditor;
import dodgeballgame.ItemManager;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 *
 * @author Sam
 */
public class ItemMenu extends Menu {

    BufferedImage[][] images;   
    
    public int xPos = (int)(INNER_X_START*3.1);
    public int yPos = INNER_Y_START;
    public int width = (int)((INNER_X_END - xPos)*1.35);
    public int height = INNER_MENU_HEIGHT;
    
    public int buttonH = INNER_MENU_HEIGHT / 50;
    public int buttonW = INNER_MENU_WIDTH / 40;
    public int buttonXOffset = width/5;
    
    public Cursor[] cursors = new Cursor[4];
    
    BufferedImage selectImage;
    
    public ItemMenu() {
        for (int i = 0; i < 4; i++) cursors[i] = new Cursor(4,4);
        images = new BufferedImage[4][4];
        
        try {
            selectImage = ImageIO.read(new File("Images/select.png"));
        } catch (IOException e) {
            
        }
        ImageEditor im = new ImageEditor(selectImage);
        selectImage = im.scale((double)WIDTH/1920d);
        loadImages();
    }
    
        
    @Override    
    public void moveCursor(int playerNumber, int x, int y) {
        cursors[playerNumber].moveCursor(x,y);
    }
    
    @Override 
    public void renderMenu(Graphics2D g) {
        
        GamePanel.menuManager.startMatchSettingsMenu.renderMenu(g);

        for (int i = 0; i<4; i++) {
            for (int j = 0; j<4; j++) {
                
                // RE_DO ALL THIS;
                
                int freq = ItemManager.powerUpFreqs[i][j];
                int xPos2 = i*width/4 + xPos;
                int yPos2 = j*height/4 + yPos;
                g.drawImage(images[i][j],(int)xPos2 + 15,(int)yPos2 +  + images[i][j].getHeight()/2,null);
                g.setColor(new Color(50,50,50));
                g.drawRect(xPos2, yPos2, width/4,height/4);
                g.drawRect(xPos2+1, yPos2+1, width/4-2,height/4-2);
                // black out
                if(GamePanel.itemManager.powerUpFreqs[i][j]==0) {
                    g.setColor(Color.black);
                    g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.8f));
                    g.fillRect(xPos2, yPos2, width/4,height/4);
                }
                
                g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
                
                //Frequency buttons
                for (int d = 0; d<freq; d++) {
                    g.setColor(Color.WHITE);
                    g.fillRect((int)(xPos2 + buttonXOffset-2), (int)yPos2 + height/5 - d*(2*buttonH) - 2, buttonW+4,buttonH+4);
                    g.setColor(new Color(60*d,0,255 - 60*d));
                    g.fillRect((int)(xPos2 + buttonXOffset), (int)yPos2 + height/5 - d*(2*buttonH), buttonW,buttonH);
                }               
            }
        }  
        g.setColor(new Color(255,255,255));
        g.drawRect(cursors[0].x*width/4 + xPos,
                cursors[0].y*height/4 + yPos, 
                width/4,
                height/4);
        g.setColor(new Color(200,100,255));
        g.drawRect(cursors[0].x*width/4 + xPos + 1,
                cursors[0].y*height/4 + yPos + 1, 
                width/4 - 2,
                height/4 - 2);
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.1f));
        g.setColor(Color.white);
        g.fillRect(cursors[0].x*width/4 + xPos + 1,
                cursors[0].y*height/4 + yPos + 1, 
                width/4 - 2,
                height/4 - 2);
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
        renderLoad(g);
    }
    
    private void loadImages() {
        
        try {
            images[0][0] = ImageIO.read(new File("Images/addHealth.png"));  
            images[0][1] = ImageIO.read(new File("Images/addRunSpeed.png"));  
            images[0][2] = ImageIO.read(new File("Images/addCatchAngle.png"));
            images[0][3] = ImageIO.read(new File("Images/addCatchReach.png"));
            images[1][0] = ImageIO.read(new File("Images/addThrowSpeed.png"));
            images[1][1] = ImageIO.read(new File("Images/takeHealth.png"));
            images[1][2] = ImageIO.read(new File("Images/ph.png"));
            images[1][3] = ImageIO.read(new File("Images/ph.png"));
            images[2][0] = ImageIO.read(new File("Images/ph.png"));
            images[2][1] = ImageIO.read(new File("Images/ph.png"));
            images[2][2] = ImageIO.read(new File("Images/ph.png"));
            images[2][3] = ImageIO.read(new File("Images/ph.png"));
            images[3][0] = ImageIO.read(new File("Images/ph.png"));
            images[3][1] = ImageIO.read(new File("Images/ph.png"));
            images[3][2] = ImageIO.read(new File("Images/ph.png"));
            images[3][3] = ImageIO.read(new File("Images/ph.png"));
     
        } catch (IOException e) {
        }
        
        
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                ImageEditor im = new ImageEditor(images[i][j]);
                double size = images[i][j].getHeight();
                images[i][j] = im.scale((0.125*(double)INNER_MENU_HEIGHT/size));
            }
        }
    }

    @Override
    public void select() {
        GamePanel.soundManager.menu(6);
        ItemManager.incFreq(cursors[0].x,cursors[0].y,1);
    }
    
    @Override
    public void selectButton() {
        GamePanel.soundManager.menu(6);
        GamePanel.menuManager.changeMenu("LOAD");
        GamePanel.menuManager.loadMenu.setBack(1);
    }
    
    @Override
    public void back() {
        GamePanel.soundManager.menu(7);
        GamePanel.menuManager.changeMenu("START_MATCH_SETTINGS");
    }
    
    @Override
    public void rightTrigger() {
        
        ItemManager.incFreq(cursors[0].x,cursors[0].y,1);
        GamePanel.soundManager.menu((int)ItemManager.powerUpFreqs[cursors[0].x][cursors[0].y]);
    }
    
    @Override
    public void leftTrigger() {
        ItemManager.decFreq(cursors[0].x,cursors[0].y,1);
        GamePanel.soundManager.menu((int)ItemManager.powerUpFreqs[cursors[0].x][cursors[0].y]);
    }
}

