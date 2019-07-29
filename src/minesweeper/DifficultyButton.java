package minesweeper;

import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JButton;

public class DifficultyButton extends JButton{
    Minesweeper ms;
    String difficulty;

    public DifficultyButton(Minesweeper ms, String diff){
        this.ms = ms;
        this.setPreferredSize(new Dimension(80,25));
        this.difficulty = diff;
        this.setText(difficulty);
    }

    //creates a new game based on the difficulty chosen
    void changeDifficulty(){
        if (difficulty == "easy"){
            ms.columns = 8;
            ms.rows = 8;
            ms.NUM_MINES = 10;
            ms.time = 0;
            ms.timerLabel.setText("Iime: 0");
            ms.timerEnabled = false;
            ms.difficulty = "Easy";
            ms.tilecount = ms.columns * ms.rows;
            ms.mainFrame.dispose();
            ms.createGrid();
            ms.createCursor();
            ms.inPlay = true;
        }

        if (difficulty == "medium"){
            ms.columns = 16;
            ms.rows = 16;
            ms.NUM_MINES = 40;
            ms.time = 0;
            ms.timerLabel.setText("Time: 0");
            ms.timerEnabled = false;
            ms.difficulty = "Medium";
            ms.tilecount = ms.columns * ms.rows;
            ms.mainFrame.dispose();
            ms.createGrid();
            ms.createCursor();
            ms.inPlay = true;
        }

        if (difficulty == "hard"){
            ms.columns = 30;
            ms.rows = 16;
            ms.NUM_MINES = 50;
            ms.time = 0;
            ms.timerLabel.setText("Time: 0");
            ms.timerEnabled = false;
            ms.difficulty = "Hard";
            ms.tilecount = ms.columns * ms.rows;
            ms.mainFrame.dispose();
            ms.createGrid();
            ms.createCursor();
            ms.inPlay = true;
        }

    }
}