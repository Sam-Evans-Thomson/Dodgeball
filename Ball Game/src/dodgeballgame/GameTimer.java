/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dodgeballgame;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

/**
 *
 * @author Sam
 */
public class GameTimer {
    
    static Timer timer;
    static boolean timing;
    
    static private double startTime;
    static private double pauseTime;
    static private double currentTime;
    
    public double countInTime;
    public double startCountInTime;
    
    public int xPos = GamePanel.arenaWIDTH/2;
    public int yPos = GamePanel.arenaHEIGHT + 60;
            
    
    public GameTimer() {
        timer = new Timer();
        timing = false;
        startTime = 0d;
        currentTime = 0d;
        countInTime = startCountInTime = 0d;
    }
    
    public static void start() {
        timing = true;
        startTime = currentTime = getTime();
    }
    
    public static void pause() {
        timing = false;
        pauseTime = getTime();
    }
    
    public static void resume() {
        timing = true;
        startTime += (getTime() - pauseTime);
    }
    
    public static void reset() {
        timing = false;
        pauseTime = startTime = getTime();
        currentTime = 0d;
    }

    private static double getTime() {
        return timer.getTime();
    }
    
    public static void update() {
        if (timing) {
            currentTime = getTime() - startTime;
        } 
    }
    
    public static double getRunTime() {
        return currentTime;
    }
    
    public void render(Graphics2D g) {
        int cTimeMinute = (int)(currentTime)/60;
        int cTimeSecond = (int)(currentTime)%60;
        int cTimeMSecond = (int)((currentTime-(int)(currentTime))*10);
        g.setColor(new Color(200,0,0));
        g.setFont(new Font("Sans Serif", Font.BOLD, 50));
        if(cTimeMinute == 0) {
            centreString(cTimeSecond + "." + cTimeMSecond, g, xPos,yPos);
        } else {
            centreString(cTimeMinute + ":" + cTimeSecond + "." + cTimeMSecond, g, xPos, yPos);
        }
    }
    
    public void renderCountIn(Graphics2D g) {
        countInTime = getTime() - startCountInTime;
        int CIT = 3-(int)countInTime;
        g.setColor(new Color(200,0,0));
        g.setFont(new Font("Sans Serif", Font.BOLD, 500));
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
        if(CIT != 0) centreString("" + CIT, g, GamePanel.screenWIDTH/2,2*GamePanel.screenHEIGHT/3);
    }
    
    public void resetCountIn() {
        countInTime = 0;
        startCountInTime = getTime();
    }
    
    private void centreString(String s, Graphics2D g, int x, int y) {
        int stringLen = (int)
            g.getFontMetrics().getStringBounds(s, g).getWidth();
        g.drawString(s, x - stringLen/2, y);
    }

}
