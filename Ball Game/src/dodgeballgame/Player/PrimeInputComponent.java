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
public class PrimeInputComponent extends InputComponent{

    public PrimeInputComponent(Player p) {
        super(p);
    }
    
    @Override
    public void pressA() {
        if (GamePanel.gameState == GamePanel.MENU) {
                GamePanel.menu.select();
        }
    }

    @Override
    public void pressB() {
        if (GamePanel.gameState == GamePanel.MENU) {
            GamePanel.menu.back();
        }
    }

    @Override
    public void pressX() {
        GamePanel.menu.xButton();
    }

    @Override
    public void pressY() {
        GamePanel.menu.yButton();
    }

    @Override
    public void pressLB() {

    }

    @Override
    public void pressRB() {

    }

    @Override
    public void pressSta() {
        GamePanel.changeGameState();
    }

    @Override
    public void pressLTh() {
        
    }

    @Override
    public void pressSel() {
        if (GamePanel.gameState == GamePanel.MENU) {
            GamePanel.menu.selectButton();
        }
    }

    @Override
    public void pressRTh() {
        
    }

    @Override
    public void pressU() {
        if (GamePanel.gameState == GamePanel.MENU) {
                GamePanel.menu.moveCursor(0, -1);
        }
    }

    @Override
    public void pressR() {
        if (GamePanel.gameState == GamePanel.MENU) {
                GamePanel.menu.moveCursor(1, 0);
                GamePanel.menu.right();
        }
    }

    @Override
    public void pressD() {
        if (GamePanel.gameState == GamePanel.MENU) {
                GamePanel.menu.moveCursor(0, 1);
        }
    }

    @Override
    public void pressL() {
        if (GamePanel.gameState == GamePanel.MENU) {
                GamePanel.menu.moveCursor(-1, 0);
                GamePanel.menu.left();
        }
    }

    @Override
    public void axesLJoy(float DX, float DY) {
        if (GamePanel.gameState == GamePanel.MENU) {
                
        } else if (GamePanel.gameState == GamePanel.PLAY) {
            p.DELTA.set(DX,DY);
        }
    }

    @Override
    public void axesRJoy(float DX, float DY) {
        if (GamePanel.gameState == GamePanel.MENU) {
                
        } else if (GamePanel.gameState == GamePanel.PLAY) {
            p.aimAngle = new Vec2(DX,DY);
            p.angle = Tools.refreshAngle(p.aimAngle.getAngle()); 
            p.physicsComp.catchHitbox.setAngle(p.angle);
        }
    }

    @Override
    public void leftTrigger(float axesS) {
        if (GamePanel.gameState == GamePanel.MENU) {
            if(axesS > 0.7 && p.leftTrig<0.7) {
                GamePanel.menu.leftTrigger();
            }
        } else if (GamePanel.gameState == GamePanel.PLAY) {
            if(axesS > 0.7 && p.leftTrig < 0.7) {
                p.catchBall();
            } 
        }   
        p.leftTrig = axesS;
    }

    @Override
    public void rightTrigger(float axesS) {
        if (GamePanel.gameState == GamePanel.MENU) {
            if(axesS > 0.7 && p.rightTrig<0.7) {
                GamePanel.menu.rightTrigger();
            }
        } else if (GamePanel.gameState == GamePanel.PLAY) {
            if(axesS > 0.7 && p.rightTrig<0.7) {
                p.throwBall();
            } 
        }  
        p.rightTrig = axesS;
    }
}
