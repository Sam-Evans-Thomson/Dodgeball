/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dodgeballgame;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

/**
 *
 * @author Sam
 */
public class ImageEditor {
    
    BufferedImage image, edittedImage;
    
    int width, height;
    Graphics2D g;
    AffineTransform at;
    
    public ImageEditor(BufferedImage image) {
        this.image = image;
        width = image.getWidth();
        height = image.getHeight();
        edittedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        at = new AffineTransform();
    }
    
    public void setImage(BufferedImage image) {
        this.image = image;
        width = image.getWidth();
        height = image.getHeight();
        edittedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        at = new AffineTransform();
    }
    
    public BufferedImage scale(double xscale, double yscale) {
        Image im = image.getScaledInstance((int)(xscale*image.getWidth()),
                (int)(yscale*image.getHeight()),
                Image.SCALE_SMOOTH);
        
        return toBufferedImage(im);
    }
    
    
    public BufferedImage scale(double scale) {
        Image im = image.getScaledInstance((int)(scale*image.getWidth()),
                (int)(scale*image.getHeight()),
                Image.SCALE_SMOOTH);
        
        return toBufferedImage(im);
    }
    
    public BufferedImage rotate(double angle) {
        at.translate(width/2, height/2);
        at.rotate(angle);
        at.translate(-width/2, -height/2);
        
        AffineTransformOp rotateOp = 
           new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR);
        
        return rotateOp.filter(image, edittedImage);
    }
    
    public BufferedImage toBufferedImage(Image img) {
        
        if (img instanceof BufferedImage) return (BufferedImage) img;

        BufferedImage bimage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);

        Graphics2D bGr = bimage.createGraphics();
        bGr.drawImage(img, 0, 0, null);
        bGr.dispose();

        return bimage;
    }
}
