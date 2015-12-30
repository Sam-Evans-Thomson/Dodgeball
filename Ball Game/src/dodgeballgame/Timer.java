/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dodgeballgame;

import static org.lwjgl.glfw.GLFW.glfwGetTime;

/**
 *
 * @author Sam
 */
public class Timer { 

    private double lastLoopTime;

    private float timeCount;

    public void init() {
        lastLoopTime = getTime();
    }

    public double getTime() {
        return glfwGetTime();
    }

    public float getDelta() {
        double time = getTime();
        float delta = (float) (time - lastLoopTime);
        lastLoopTime = time;
        timeCount += delta;
        return delta;
    }
    
    public float getDifference() {
        double time = getTime();
        float diff = (float) (time - lastLoopTime);
        return diff;
    }
    
    public void refresh() {
        init(); 
    }

    public void update() {
        if (timeCount > 1f) {
            timeCount -= 1f;
        }
    }

    public double getLastLoopTime() {
        return lastLoopTime;
    }
}
