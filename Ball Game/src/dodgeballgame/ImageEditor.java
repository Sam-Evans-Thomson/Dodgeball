/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dodgeballgame;

import java.awt.Graphics2D;
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
        at.scale(xscale, yscale);
        AffineTransformOp scaleOp = 
           new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR);
        
        return scaleOp.filter(image, edittedImage);
    }
    
    public BufferedImage scale(double scale) {
        at.scale(scale, scale);
        AffineTransformOp scaleOp = 
           new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR);
        
        edittedImage = scaleOp.filter(image, edittedImage);
        
        return edittedImage;
    }
    
    public BufferedImage rotate(double angle) {
        at.translate(width/2, height/2);
        at.rotate(angle);
        at.translate(-width/2, -height/2);
        
        AffineTransformOp rotateOp = 
           new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR);
        
        return rotateOp.filter(image, edittedImage);
    }
}
