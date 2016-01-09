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
            if (GamePanel.menuManager.menu.equals(GamePanel.menuManager.characterMenu))GamePanel.menuManager.characterMenu.select(p);
            else GamePanel.menuManager.menu.select();
        }
    }

    @Override
    public void pressB() {
        if (GamePanel.gameState == GamePanel.MENU) {
            GamePanel.menuManager.menu.back();
        }
    }

    @Override
    public void pressX() {
        GamePanel.menuManager.menu.xButton();
    }

    @Override
    public void pressY() {
        GamePanel.menuManager.menu.yButton();
    }

    @Override
    public void pressSta() {
        if (GamePanel.menuManager.menu.equals(GamePanel.menuManager.winScreen)) {
            GamePanel.menuManager.menu.back();
        } else {
            GamePanel.changeGameState();
            GamePanel.menuManager.changeMenu("START");
        }
        
    }

    @Override
    public void pressSel() {
        if (GamePanel.gameState == GamePanel.MENU) {
            GamePanel.menuManager.menu.selectButton();
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
                GamePanel.menuManager.menu.leftTrigger();
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
                GamePanel.menuManager.menu.rightTrigger();
            }
        } else if (GamePanel.gameState == GamePanel.PLAY) {
            if(axesS > 0.7 && p.rightTrig<0.7) {
                if(p.isGhost) p.eatItem();
                else p.throwBall();
            } 
        }  
        p.rightTrig = axesS;
    }
}
