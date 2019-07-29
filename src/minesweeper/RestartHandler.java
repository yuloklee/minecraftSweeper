package minesweeper;

import java.awt.event.*;

public class RestartHandler implements MouseListener{
    RestartButton rb;
    Minesweeper ms;

    public RestartHandler(Minesweeper ms){
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
        rb = (RestartButton) e.getSource();
        
        rb.restart();

    
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}