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
        if (p.pNumber == 0 && GamePanel.gameState == GamePanel.MENU) {
                GamePanel.menu.select();
        }
    }

    public void pressB() {
        if (p.pNumber == 0) {
            if (GamePanel.gameState == GamePanel.MENU) {
                GamePanel.menu.back();
            }
        }
    }

    public void pressX() {
        if(p.pNumber == 0) GamePanel.menu.xButton();
    }

    public void pressY() {
        if(p.pNumber == 0) GamePanel.menu.yButton();
    }

    public void pressLB() {

    }

    public void pressRB() {

    }

    public void pressSta() {
        if (p.pNumber == 0) {
            GamePanel.changeGameState();
        }
    }

    public void pressLTh() {
        
    }

    public void pressSel() {
        if (p.pNumber == 0 && GamePanel.gameState == GamePanel.MENU) {
            GamePanel.menu.selectButton();
        }
    }

    public void pressRTh() {
        
    }

    public void pressU() {
        if (p.pNumber == 0 && GamePanel.gameState == GamePanel.MENU) {
                GamePanel.menu.moveCursor(0, -1);
        }
    }

    public void pressR() {
        if (p.pNumber == 0 && GamePanel.gameState == GamePanel.MENU) {
                GamePanel.menu.moveCursor(1, 0);
                GamePanel.menu.right();
        }
    }

    public void pressD() {
        if (p.pNumber == 0 && GamePanel.gameState == GamePanel.MENU) {
                GamePanel.menu.moveCursor(0, 1);
        }
    }

    public void pressL() {

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
            if(axesS > 0.7 && p.leftTrig < 0.7) {
                p.catchBall();
            } 
        }   
        p.leftTrig = axesS;
    }

    public void rightTrigger(float axesS) {
        if (GamePanel.gameState == GamePanel.PLAY) {
            if(axesS > 0.7 && p.rightTrig<0.7) {
                p.throwBall();
            } 
        }  
        p.rightTrig = axesS;
    }
}
