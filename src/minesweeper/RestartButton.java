package minesweeper;

import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JButton;

public class RestartButton extends JButton {
    Minesweeper ms;

    public RestartButton(Minesweeper ms) {
        this.ms = ms;
        this.setPreferredSize(new Dimension(ms.tilesize, ms.tilesize));
        this.setIcon(ms.CREEPER);

    }

    void restart(){
        ms.tilecount = ms.columns * ms.rows;
        ms.mainFrame.dispose();
        ms.createGrid();
        ms.createCursor();
        ms.inPlay = true;
        ms.time = 0;
        ms.timerLabel.setText("Time: 0");
        ms.timerEnabled = false;
    }
}