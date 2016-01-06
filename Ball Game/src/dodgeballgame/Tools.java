/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dodgeballgame;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;

/**
 *
 * @author Sam
 */
public class Tools {
    
    public Tools() {
        
    }
    
    // make sure an angle is between 0 and 2*Pi.
    public static double refreshAngle(double a) {
        while (a < 0) {
            a+=2d*Math.PI;
        }
        while (a > 2*Math.PI) {
            a-=2d*Math.PI;
        }
        return a;
    }
    
    // prints a string centred on the specified spot.
    public static void centreStringHor(String s, Graphics2D g, int x, int y) {
        int stringLen = (int)
            g.getFontMetrics().getStringBounds(s, g).getWidth();
        g.drawString(s, x - stringLen/2, y);
    }

    public static BufferedImage scaleImage(BufferedImage im, double scale) {
        ImageEditor imageEditor = new ImageEditor(im);
        return imageEditor.scale(scale);
    }
    
    public static BufferedImage sizeImage(BufferedImage im, double size) {
        ImageEditor imageEditor = new ImageEditor(im);
        return imageEditor.scale(size/im.getWidth());
    }
}
