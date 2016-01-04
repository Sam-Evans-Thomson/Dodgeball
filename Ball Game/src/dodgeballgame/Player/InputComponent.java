/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dodgeballgame.Player;

import dodgeballgame.GamePanel;
import dodgeballgame.Tools;
import dodgeballgame.Vec2;

/**
 *
 * @author Sam
 */
public class InputComponent {
    
    Player p;
    
    public InputComponent (Player p) {
        this.p = p;
    }
    
    public void pressA() {
        if(GamePanel.gameState == GamePanel.MENU && GamePanel.menu.equals(GamePanel.characterMenu)) {
            GamePanel.characterMenu.select(p);
        }
    }

    public void pressB() {

    }

    public void pressX() {

    }

    public void pressY() {

    }

    public void pressLB() {
        if (GamePanel.gameState == GamePanel.PLAY) {
            p.catchBall();
        }
    }

    public void pressRB() {
        p.usePower();
    }

    public void pressSta() {

    }

    public void pressLTh() {
        
    }

    public void pressSel() {

    }

    public void pressRTh() {
        
    }

    public void pressU() {
        if (GamePanel.gameState == GamePanel.MENU) {
                GamePanel.menu.moveCursor(p.pNumber,0, -1);
        }
    }

    public void pressR() {
        if (GamePanel.gameState == GamePanel.MENU) {
                GamePanel.menu.moveCursor(p.pNumber,1, 0);
                GamePanel.menu.right();
        }
    }

    public void pressD() {
        if (GamePanel.gameState == GamePanel.MENU) {
                GamePanel.menu.moveCursor(p.pNumber,0, 1);
        }
    }

    public void pressL() {
        if (GamePanel.gameState == GamePanel.MENU) {
                GamePanel.menu.moveCursor(p.pNumber,-1, 0);
                GamePanel.menu.left();
        }
    }

    public void axesLJoy(float DX, float DY) {
        if (GamePanel.gameState == GamePanel.PLAY) {
            p.DELTA.set(DX,DY);
        }
    }

    public void axesRJoy(float DX, float DY) {
        if (GamePanel.gameState == GamePanel.PLAY) {
            p.aimAngle = new Vec2(DX,DY);
            p.angle = Tools.refreshAngle(p.aimAngle.getAngle()); 
            p.physicsComp.catchHitbox.setAngle(p.angle);
        }
    }

    public void leftTrigger(float axesS) {
        if (GamePanel.gameState == GamePanel.PLAY) {
            if(axesS > 0.7 && p.leftTrig < 0.5) {
                p.catchBall();
            } 
        }   
        p.leftTrig = axesS;
    }

    public void rightTrigger(float axesS) {
        if (GamePanel.gameState == GamePanel.PLAY) {
            if(axesS > 0.7 && p.rightTrig<0.5) {
                p.throwBall();
            } 
        }  
        p.rightTrig = axesS;
    }
}
