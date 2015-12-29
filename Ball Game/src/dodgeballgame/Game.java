/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dodgeballgame;

import java.awt.event.WindowEvent;
import javax.swing.JFrame;

/**
 *
 * @author Sam
 */
public class Game {
    
    private static JFrame window;
    private static GamePanel gamePanel;
    
    public static void main(String[] args) {
        
    window = new JFrame("First Game");
    window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    
    gamePanel = new GamePanel();
    window.setContentPane(gamePanel);
    
    window.pack();
    window.setVisible(true);
    window.setExtendedState( window.getExtendedState()|JFrame.MAXIMIZED_BOTH );
    }   
    
    public static void close() {
        gamePanel.exit();
        window.dispatchEvent(new WindowEvent(window, WindowEvent.WINDOW_CLOSING));
    }
}
