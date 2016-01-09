/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dodgeballgame;


import dodgeballgame.Player.Player;
import dodgeballgame.Arenas.*;
import dodgeballgame.Menus.MenuManager;
import dodgeballgame.Balls.Ball;
import dodgeballgame.GameModes.GameModeManager;
import dodgeballgame.Items.Item;
import dodgeballgame.Powers.Power;
import dodgeballgame.Settings.SpecificSettings.GeneralSettings;
import javax.swing.JPanel;
import java.awt.*;
import java.awt.image.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import javax.imageio.ImageIO;

import org.lwjgl.glfw.*;
 
import static org.lwjgl.glfw.GLFW.*;
/**
 *
 * @author Sam
 */
public class GamePanel extends JPanel implements Runnable, KeyListener {
    
    
    
    private GLFWErrorCallback errorCallback;
    private GLFWKeyCallback   keyCallback;
    
    public static int numControllers;
    public static int maxNumPlayers = 8;
    public static int minNumPlayers = 2;
    public static int numPlayers;
    
    private static int[][] buttonStates;
    private static int[][] prevButtonStates;
    private static float[][] axesStates;
    private static float[][] prevAxesStates;
    
    public static int gameState;
    public static final int PLAY = 1;
    public static final int MENU = 0;
    public static final int COUNTIN = 2;
    
    Timer timer;
    public int FPS;
    private double averageFPS;
    public static final int FPSGoal = 60;
    
    static int[] controllers = new int[16];
    static int[] playerControllers;
    
    public static ArrayList<Player> playerArray;
    public static ArrayList<Ball> ballArray;
    public static ArrayList<Item> itemArray;
    public static ArrayList<Power> powerArray;
    public static Arena arena;
    
    public static ItemManager itemManager;
    public static PowerManager powerManager;
    public static ArenaManager arenaManager;
    public static MenuManager menuManager;
    public static SoundManager soundManager;
    public static GameModeManager gameModeManager;
    
    public static GeneralSettings generalSettings;
    
    public static final int screenWIDTH = 1920;
    public static final int screenHEIGHT = 980;
    
    public static int arenaWIDTH = screenWIDTH;
    public static int arenaHEIGHT = screenHEIGHT - 100;
    
    private boolean running;
    private final Color BACKGROUND = Color.GRAY;
    
    private Thread thread;
    
    private BufferedImage image;
    private BufferedImage image2;
    private Graphics2D g;
    private Graphics2D g2;
    
    public static boolean friendlyFire;
    public static boolean musicOn;
    
    public static int team1Score;
    public static int team2Score;
    public static int winScore;
    public static GameTimer gameTimer;
    
    private BufferedImage loading;
    
    public GamePanel() {
        super();
        setPreferredSize(new Dimension(screenWIDTH,screenHEIGHT));
        setFocusable(true);
        requestFocus();
    }
    
    @Override
    public void addNotify() {
        super.addNotify();    
        if(thread == null) {
            thread = new Thread(this);
            thread.start();
        }
        
        addKeyListener(this);
    }
    
    @Override
    public void run() {
        loop();    
    }
    
    private void init() {
        // Find screen dimensions.
        // Set screen dimensions
        
        //Initialising images
        image = new BufferedImage(screenWIDTH, screenHEIGHT, BufferedImage.TYPE_INT_RGB);
        image2 = new BufferedImage(screenWIDTH, screenHEIGHT, BufferedImage.TYPE_INT_RGB);
        g = (Graphics2D) image.getGraphics();
        g2 = (Graphics2D) image2.getGraphics();
        
        /****** draw Loading Screen ******/
        draw();
        try {
            loading = ImageIO.read(new File("Images/Loading2.png"));
        } catch(IOException e) {}
        renderLoading();
        draw();
        
        gameTimer = new GameTimer();
        GameTimer.pause();
        running = true;
        gameState = MENU;      

        // Setup Timer
        timer = new Timer();
        arena = new BasicArena();
        
        //Managers
        itemManager = new ItemManager();
        powerManager = new PowerManager();
        soundManager = new SoundManager();
        
        generalSettings = new GeneralSettings();
        generalSettings.apply();
        
        arenaManager = new ArenaManager();
        gameModeManager = new GameModeManager();
        menuManager = new MenuManager();
        
        
       // Setup an error callback. The default implementation
        // will print the error message in System.err.
        glfwSetErrorCallback(errorCallback = GLFWErrorCallback.createPrint(System.err));
 
        // Initialize GLFW. Most GLFW functions will not work before doing this.
        if ( glfwInit() != GLFW_TRUE )
            throw new IllegalStateException("Unable to initialize GLFW");
        
        initControllers();
        generalSettings.numberOfPlayers();
        gameModeManager.setMode(0);
        newGame();
        
        g.setColor(Color.black);
        g.fillRect(0,0,screenWIDTH, screenHEIGHT);
        draw();
        arenaManager.init();        
    }
    
    public static void initControllers() {
        // Setup the controllers
        numControllers = 0;        
        for(int d=0; d<16; d++) {
            if(glfwJoystickPresent(d)==GLFW_TRUE) {
            controllers[numControllers] = d;
            numControllers++;
            }
        }
        
        playerControllers = new int[numControllers];
        
        buttonStates = new int[numControllers][14];
        prevButtonStates = new int[numControllers][14];
        axesStates = new float[numControllers][6];
        prevAxesStates = new float[numControllers][6];
        
        if (numControllers < minNumPlayers) System.out.println("Please Connect at least 2 Controllers"); 
        else System.out.println(numControllers + " controllers connected.");
        
    }
    
    public static void newGame() {
        
        team1Score = team2Score = 0;
        gameTimer.reset();
        playerArray = new ArrayList<>();
        ballArray = new ArrayList<>();
        itemArray = new ArrayList<>();
        powerArray = new ArrayList<>();
        
        arena.init();
        
        gameModeManager.gameMode.apply();
        
        System.arraycopy(controllers, 0, playerControllers, 0, numPlayers);
        for (int p = 0; p<numPlayers; p++) {
            int team =2*p/numPlayers;
            playerArray.add(new Player(team, p, arena.playerPos[p].getX(), arena.playerPos[p].getY()));
        }
        
        gameModeManager.gameMode.apply();
    }
    
    public void countIn() {
        soundManager.stopAll();
        gameTimer.resetCountIn();
        int lastTime = 10;
        int thisTime;
        
        while(gameTimer.countInTime<3){
            thisTime = (int)gameTimer.countInTime;
            if(thisTime != lastTime) soundManager.menu(6);
            
            playerArray.stream().forEach((p) -> {
                p.refreshCatchTimer();
            });
            render();
            g.setColor(Color.black);
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
            g.fillRect(0, 0,screenWIDTH , screenHEIGHT);

            gameTimer.renderCountIn(g);
            draw();
            lastTime = thisTime;
        }
        soundManager.addHealth();
        soundManager.music();
        gameTimer.resume();
    }
    
    public static void changeScore(int team, int amount) {
        if (team == 0) {
            team1Score+=amount;
        } else {
            team2Score+=amount;
        }

        if (gameModeManager.gameMode.settings.type.equals("KNOCKOUT")) {
            if (team1Score == 0 || team2Score == 0) win();
        }
        else if (team1Score >= winScore || team2Score >= winScore) win();
    }
    
    public static void win() {
        gameState = MENU;
        menuManager.changeMenu("WIN");
    }
    
    private void loop() {

        init();
        
        long lastTime = System.nanoTime();
        long startTime = 0;
        long URDTimeMillis;
        long waitTime;
        long totalTime = 0;
        
        int frameCount = 0;
        int maxFrameCount = 30;

        double frameTime = 0;
        
        long targetTime = 1000 / FPSGoal;
        
        // Game Loop
        while(running) {
            
            
            
            // Poll for window events. The key callback above will only be
            // invoked during this call.
            glfwPollEvents();
            
            // GAME LOOP
            
            if (gameState == PLAY) {
                frameTime = 0;
                while(frameTime < 1f/FPSGoal) {
                    startTime = System.nanoTime();
                    float delta = ((float)(startTime - lastTime)/1000000000f);
                    // Does this fix the player start mvoement issue?
                    if (delta > 1f/20f) delta = 1f/20f;
                    ///////
                    input();
                    update(delta);
                    frameTime += (double)delta;
                    lastTime = startTime;
                }
                render();
                       
            } else if (gameState == MENU) {
                
                input();
                startTime = System.nanoTime();
                float delta = ((float)(startTime - lastTime)/1000000000f);
                updateMenu(delta);
                renderMenu();
                lastTime = startTime;
                
            } else if (gameState == COUNTIN) {
                countIn();
                gameState = PLAY;
                startTime = System.nanoTime();
            }
            
            draw();
            
            // TIMING
            URDTimeMillis = (System.nanoTime() - startTime)/1000000;
            waitTime = targetTime - URDTimeMillis;
            
            try {
                Thread.sleep(waitTime);
            }
            catch(Exception e) {}
            
            totalTime += System.nanoTime() - startTime;
            frameCount++;
            if(frameCount == maxFrameCount) {
                averageFPS = 1000.0 / ((totalTime / frameCount) / 1000000);
                frameCount = 0;
                totalTime = 0;
            }
        }
    }
    
    public static void changeGameState() {
        if(gameState == MENU) {
            gameState = COUNTIN;
        } else if (gameState == PLAY) {
            soundManager.stopAll();
            gameState = MENU;
            gameTimer.pause();
        }
    }  
    
    public void exit() {
        menuManager.generalSettingsMenu.save();
    }
    /*************************************************************************/
    // GAME UPDATE//
    /*************************************************************************/
    
    // inputs
    private void input() {
        
        ByteBuffer buttons;   
        FloatBuffer axes;
        int[] axisCount = new int[maxNumPlayers];
        //Scan button States
        for (int i=0; i<numPlayers; i++) {

            buttons = glfwGetJoystickButtons(playerControllers[i]);
            axes = glfwGetJoystickAxes(playerControllers[i]);      
            int buttonID = 0;
            while (buttons.hasRemaining()) {
                buttonStates[i][buttonID] = buttons.get();
                buttonID++;
            }
            
            int axisID = 0;
            while (axes.hasRemaining()) {
                float state = axes.get();
                axesStates[i][axisID] = state;
                axisID++;
            }
            axisCount[i] = axisID;
            
            float a1 = axesStates[i][1];
            float a2 = axesStates[i][2];
            float a3 = axesStates[i][3];
            float a4 = axesStates[i][4];
            if(Math.sqrt(a1*a1 + a2*a2) < 0.5) {
                axesStates[i][1] = 0.0f;
                axesStates[i][2] = 0.0f;
            }
            if(Math.sqrt(a3*a3 + a4*a4) < 0.25) {
                axesStates[i][3] = 0.0f;
                axesStates[i][4] = 0.0f;
            }
        }

        for(int i = 0; i<numPlayers; i++) {
            Player p = playerArray.get(i);
            int[] buttonS = buttonStates[i];
            int[] prevButtons = prevButtonStates[i];
            float[] axesS = axesStates[i];
            
            //float[] prevAxesS = prevAxesStates[i];
            
            if((buttonS[0]==GLFW_PRESS)&&(buttonS[0]!=prevButtons[0])) p.inputComponent.pressA();
            if(buttonS[1]==GLFW_PRESS&&buttonS[1]!=prevButtons[1]) p.inputComponent.pressB();
            if(buttonS[2]==GLFW_PRESS&&buttonS[2]!=prevButtons[2]) p.inputComponent.pressX();
            if(buttonS[3]==GLFW_PRESS&&buttonS[3]!=prevButtons[3]) p.inputComponent.pressY();
            if(buttonS[4]==GLFW_PRESS&&buttonS[4]!=prevButtons[4]) p.inputComponent.pressLB();
            if(buttonS[5]==GLFW_PRESS&&buttonS[5]!=prevButtons[5]) p.inputComponent.pressRB();
            if(buttonS[6]==GLFW_PRESS&&buttonS[6]!=prevButtons[6]) p.inputComponent.pressSel();
            if(buttonS[7]==GLFW_PRESS&&buttonS[7]!=prevButtons[7]) p.inputComponent.pressSta();
            if(buttonS[8]==GLFW_PRESS&&buttonS[8]!=prevButtons[8]) p.inputComponent.pressLTh();
            if(buttonS[9]==GLFW_PRESS&&buttonS[9]!=prevButtons[9]) p.inputComponent.pressRTh();
            if(buttonS[10]==GLFW_PRESS&&buttonS[10]!=prevButtons[10]) p.inputComponent.pressU();
            if(buttonS[11]==GLFW_PRESS&&buttonS[11]!=prevButtons[11]) p.inputComponent.pressR();
            if(buttonS[12]==GLFW_PRESS&&buttonS[12]!=prevButtons[12]) p.inputComponent.pressD();
            if(buttonS[13]==GLFW_PRESS&&buttonS[13]!=prevButtons[13]) p.inputComponent.pressL();
            
            if(Math.sqrt(axesS[0]*axesS[0] + axesS[1]*axesS[1])< 0.25) {
                p.inputComponent.axesLJoy(0f,0f);
            } else {
                p.inputComponent.axesLJoy(axesS[0],axesS[1]);
            }
            
            // Xbone controller
            if (axisCount[i] == 6) {        
                if(Math.sqrt(axesS[5]*axesS[5] + axesS[4]*axesS[4])> 0.5) {
                    p.inputComponent.axesRJoy(axesS[5],axesS[4]);
                }
                p.inputComponent.leftTrigger(axesS[2]);
                p.inputComponent.rightTrigger(axesS[3]);
                
            // Xbox 360 Controller    
            } else if (axisCount[i] == 5) {
                if(Math.sqrt(axesS[4]*axesS[4] + axesS[3]*axesS[3])> 0.5) {
                    p.inputComponent.axesRJoy(axesS[4],axesS[3]);
                }
                if (axesS[2] > 0) p.inputComponent.leftTrigger(axesS[2]);
                else p.inputComponent.rightTrigger(-axesS[2]);
            }    
        }
        
        for(int i = 0; i<numPlayers; i++) {
            for(int j = 0; j<14; j++) {
                prevButtonStates[i][j] = buttonStates[i][j];
            }
        }
        //prevAxesStates = axesStates;
    }
    
    private void updateMenu(float delta) {
        menuManager.update();
    }

    // Do necessary calculations
    private static void update(float delta) {
        gameTimer.update();
        
        for (int i = 0; i<ballArray.size(); i++) {
            ballArray.get(i).update(delta);
        }
        for (int i = 0; i<playerArray.size(); i++) {
            playerArray.get(i).update(delta);
        }
        for (int i = 0; i<itemArray.size(); i++) {
            itemArray.get(i).update();
        }
        for (int i = 0; i<powerArray.size(); i++) {
            powerArray.get(i).update();
        }
        
    }
    
    //render to screen
    private void render() {
        
        arena.render(g);
        g.setColor(Color.white);
        g.fillRect(0, arenaHEIGHT,screenWIDTH , screenHEIGHT-arenaHEIGHT);
        

        for (int i = 0; i<ballArray.size(); i++) {
            ballArray.get(i).render(g);
        }
        for (int i = 0; i<itemArray.size(); i++) {
            itemArray.get(i).render(g);
        }
        for (int i = 0; i<powerArray.size(); i++) {
            powerArray.get(i).render(g);
        }
        for (int i = 0; i<playerArray.size(); i++) {
            playerArray.get(i).render(g);
        }
        gameTimer.render(g);
        
        // Display FPS
        g.setPaint(Color.red);
        g.setFont(new Font("Sans Serif", Font.BOLD, 20));
        g.drawString(("FPS: " +averageFPS), 20, 20);
        
    }

    private void renderMenu() {
        render();
        menuManager.render(g);
    }
    
    private void renderLoading() {
        g.drawImage(loading, 0,0, null);
    }
    
    private void draw() {
        Graphics g2 = this.getGraphics();
        g2.drawImage(image, 0 ,0, null);
        g2.dispose();
    }

    
    @Override
    public void keyTyped(KeyEvent key) {

    }
    
    @Override
    public void keyPressed(KeyEvent key) {
        
    }
    
    @Override
    public void keyReleased(KeyEvent key) {

    }
    
}
