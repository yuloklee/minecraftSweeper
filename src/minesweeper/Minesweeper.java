package minesweeper;

import java.util.ArrayList;
import java.util.Random;
import java.awt.*;
import javax.swing.*;
import javax.swing.ImageIcon;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.Clip;
import javax.sound.sampled.AudioSystem;
import java.io.File;
import java.util.Timer;
import java.util.TimerTask;

/**
 * minesweeper 
 * @author joshua lee
 */

public class Minesweeper {
    ArrayList<Integer> minenums;
    int NUM_MINES = 10;
    int rows = 8;
    int columns = 8;
    int tilesize = 42;
    int tilecount;
    boolean inPlay;
    Random rand = new Random();
    int[][] grid;
    Button[][] buttons;
    JFrame mainFrame;
    JPanel panel;
    JPanel body;
    JPanel difficultyPanel;
    JPanel scorePanel;
    int time = 0;
    JLabel timerLabel;
    JLabel scoreLabel;
    Timer timer;
    TimerTask repeatedTask;
    boolean wonGame = false;
    boolean lostGame = false;
    boolean timerEnabled;
    String difficulty = "Easy";
    Highscore hs;

    ImageIcon FLAG = new ImageIcon("src\\minesweeper\\Assets\\FLAG.png");
    ImageIcon MINE = new ImageIcon("src\\minesweeper\\Assets\\MINE.png");
    ImageIcon TILE = new ImageIcon("src\\minesweeper\\Assets\\TILE.png");
    ImageIcon PICK = new ImageIcon("src\\minesweeper\\Assets\\PICK.png");
    ImageIcon SHOV = new ImageIcon("src\\minesweeper\\Assets\\SHOV.png");
    ImageIcon STONE = new ImageIcon("src\\minesweeper\\Assets\\STONE.png");
    ImageIcon CREEPER = new ImageIcon("src\\minesweeper\\Assets\\CREEPER.jpg");
    
    public static void main(String[] args) {
        Minesweeper game = new Minesweeper();
             
    }
    
    public Minesweeper() {
        hs = new Highscore(this);
        MINE = scaleImage(MINE);
        FLAG = scaleImage(FLAG);
        STONE = scaleImage(STONE);
        CREEPER = scaleImage(CREEPER);
        
        //creating timer
        long delay = 1000L;
        long period = 1000L;
        timer = new Timer();
        timerLabel = new JLabel("Time: " + Integer.toString(time));
        
        //task incrementing the time every second
        repeatedTask = new TimerTask() {
            public void run() {
                if(timerEnabled){
                    time++;
                    timerLabel.setText("Time: " + Integer.toString(time));
                }
                
            }
        };
        timer.scheduleAtFixedRate(repeatedTask,delay,period);

        inPlay = true;
        timerEnabled = false;
        createGrid();  
        createCursor();
    }
    
    //generates a custom cursor using a custom image
    public void createCursor(){
        try
        {
        Image customImage = PICK.getImage();
        Cursor customCursor = Toolkit.getDefaultToolkit().createCustomCursor(customImage, new Point(0, 0), "pickaxe");
        mainFrame.setCursor(customCursor);
        }catch(Exception e){}
        ;
    }
    

    public void playSound(String soundName){
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(soundName).getAbsoluteFile( ));
            Clip clip = AudioSystem.getClip( );
            clip.open(audioInputStream);
            clip.start();
        }
        catch(Exception ex){
            System.out.println("Error with playing sound.");
            ex.printStackTrace( );
        }
    } 

    //scales images to specified size
    ImageIcon scaleImage(ImageIcon img){
        Image image = img.getImage();
        Image newimg = image.getScaledInstance(tilesize+1, tilesize+1,  java.awt.Image.SCALE_SMOOTH);
        return new ImageIcon(newimg);
    }

    //initializes a new game with the specified rows, columns, and number of mines
    void createGrid(){
        tilecount = rows*columns;
        buttons = new Button[rows][columns];
        
        //initializing grid
        grid = new int[rows][columns];
        
        //creating array list for mine positions
        ArrayList<Integer> minepos = new ArrayList<>();
        while (minepos.size() < NUM_MINES){
            //generating random position in range of grid
            int randNum = rand.nextInt(rows*columns);
            //if list does not contain position already, add it to list
            if (!minepos.contains(randNum)){
                minepos.add(randNum);
            }
        }
        
        //setting mine positions to -1
        for(int num: minepos){
            grid[num/columns][num%columns] = -1;
        }
        
        //setting grid locations to show surrounding mines
        //iterating through every position in list
        for (int row = 0; row < grid.length; row++) { 
            for (int column = 0; column < grid[row].length; column++) {
                //iterating through 3 by 3 sub-grid around mine positions
                for (int x = -1; x <= 1; x++){
                    for (int y = -1; y <= 1; y++){
                        //checking for mine position
                        if(grid[row][column] == -1){
                            //checking if 3 by 3 grid around mine position is in range of grid
                            if(inGrid((row - x),(column - y))){
                                //if surrounding position is not already a mine, add 1 to position
                                if(!(grid[row - x][column - y] == -1)){
                                    grid[row - x][column - y]++;
                                }
                            }
                        }
                    }
                }
            }
        } 
        
        
        //creating the body where the game panel and buttons will be added
        body = new JPanel();
        body.setLayout(new GridBagLayout());

        //creating and formatting the panel where the mines will be added
        panel = new JPanel(); 
        panel.setLayout(new GridLayout(rows, columns));
        
        //creating panel for difficulty buttons
        difficultyPanel = new JPanel();
        difficultyPanel.setLayout(new BoxLayout(difficultyPanel,BoxLayout.LINE_AXIS));

        //panel for highscores and timer
        scorePanel = new JPanel();
        scorePanel.setLayout(new GridLayout(2,1));

        scoreLabel = new JLabel("<html>Recent scores:<html/>" + hs.readScore());

        //creating buttons and adding them to the panel and a list
        for(int row = 0; row < rows; row++){
            for(int column = 0; column < columns; column++){
                Button b = new Button(this);
                b.setValue(grid[row][column]);
                b.setLoc(row, column);
                b.addMouseListener(new MouseHandler(this));
                b.setIcon(STONE);
                panel.add(b);
                buttons[row][column] = b;
            }
        }

        //creating layout restraints for buttons
        GridBagConstraints restart = new GridBagConstraints();
        restart.anchor = GridBagConstraints.PAGE_START;
        restart.gridy = 1;
        restart.gridx = 0;
        GridBagConstraints gridConstraints = new GridBagConstraints();
        gridConstraints.gridy = 2;
        gridConstraints.gridx = 0;
        GridBagConstraints difficultyConstraints = new GridBagConstraints();
        difficultyConstraints.anchor = GridBagConstraints.NORTH;
        difficultyConstraints.gridy = 0;
        difficultyConstraints.gridx = 0;
        GridBagConstraints scoreConstraints = new GridBagConstraints();
        scoreConstraints.anchor = GridBagConstraints.NORTH;
        scoreConstraints.gridy = 2;
        scoreConstraints.gridx = 1;

        difficultyPanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        difficultyPanel.add(Box.createHorizontalGlue());

        //creating the extra buttons
        RestartButton restartB = new RestartButton(this);
        restartB.addMouseListener(new RestartHandler(this));

        //difficulty buttons
        DifficultyButton easy = new DifficultyButton(this, "easy");
        DifficultyButton medium = new DifficultyButton(this, "medium");
        DifficultyButton hard = new DifficultyButton(this, "hard");
        easy.addMouseListener(new DifficultyHandler(this));
        medium.addMouseListener(new DifficultyHandler(this));
        hard.addMouseListener(new DifficultyHandler(this));
        
        //adding everything to the main panel
        difficultyPanel.add(easy);
        difficultyPanel.add(Box.createRigidArea(new Dimension(10, 0)));
        difficultyPanel.add(medium);
        difficultyPanel.add(Box.createRigidArea(new Dimension(10, 0)));
        difficultyPanel.add(hard);
        scorePanel.add(timerLabel);
        scorePanel.add(scoreLabel);
        scorePanel.setBorder(BorderFactory.createEmptyBorder(0,10,0,0));
        body.add(scorePanel, scoreConstraints);
        body.add(difficultyPanel, difficultyConstraints);
        body.add(restartB, restart);
        body.add(panel, gridConstraints);
        body.setBorder(BorderFactory.createEmptyBorder(20,35,35,40));

        
        //creating the frame and adding the body 
        mainFrame = new JFrame("minesweeper");    
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.add(body);
        mainFrame.pack();
        mainFrame.setVisible(true);

    }
    
    //checks if position is in range of grid
    private boolean inGrid(int a, int b){
        if(a<0 || b< 0){
            return false;
        }
        return !(a >= rows || b >= columns);
    }
    
    //method to print the grid  
    private void printGrid(int[][] list) {
        for(int row = 0; row < rows; row++){
            for(int column = 0; column < columns; column++){
                System.out.print(list[row][column] + " | ");
            }
            System.out.println();
        }
    }
    
    //reveals the entire board
    void loseGame(){
        inPlay = false;
        for(int row = 0; row < rows; row++){
            for(int column = 0; column < columns; column++){
                buttons[row][column].reveal();
            }
        }
        timerEnabled = false;
    }
    
    //recursively reveals surrounding empty tiles around an empty location
    void revealAround(Button b){
        int bx = b.location[0];
        int by = b.location[1];
        if (buttons[by][bx].value == 0 && buttons[by][bx].flagged == false){
            for (int x = -1; x <= 1; x++){
                for (int y = -1; y <= 1; y++){
                    if(inGrid((by - y),(bx - x))){
                        if (buttons[by-y][bx-x].value >= 0 && buttons[by-y][bx-x].isExposed() == false){
                            buttons[by-y][bx-x].reveal();
                            revealAround(buttons[by-y][bx-x]);
                        }
                    }
                }   
            }
        }
    }
    
    //sets all remaining mines as flags and records the score   
    void winGame(){
        hs.recordScore();
        scoreLabel.setText("<html>Recent scores:<html/>" + hs.readScore());
        mainFrame.pack();
        for(int row = 0; row < rows; row++){
            for(int column = 0; column < columns; column++){
                if (buttons[row][column].isExposed() == false){
                    buttons[row][column].flagged = true;
                    buttons[row][column].setIcon(FLAG);
                    buttons[row][column].setBackground(Color.LIGHT_GRAY);
                }
            }
        }
    }
}