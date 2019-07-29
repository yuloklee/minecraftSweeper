package minesweeper;

import java.awt.event.*;

public class DifficultyHandler implements MouseListener{
    DifficultyButton db;
    Minesweeper ms;

    public DifficultyHandler(Minesweeper ms){
        this.ms = ms;

    }
    @Override
    public void mouseClicked(MouseEvent e) {
        
    }

    @Override
    public void mousePressed(MouseEvent e) {
        
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        db = (DifficultyButton) e.getSource();
        
        db.changeDifficulty();

    
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}