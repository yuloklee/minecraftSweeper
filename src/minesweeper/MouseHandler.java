package minesweeper;

import java.awt.event.*;

/**
 *
 * @author joshuay.lee
 */
public class MouseHandler implements MouseListener{
    Button b;
    Minesweeper ms;
    
    public MouseHandler(Minesweeper ms){
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
        b = (Button) e.getSource();


        //flags tile on right-click
        if (e.isMetaDown()){
            b.flag();
        }
        
        //on regular click, reveals and checks for mine which results in lost game, or checks for 0 to reveal around
        else if (b.flagged == false){
           b.reveal();
           if (b.value == -1){
               ms.loseGame();
               ms.lostGame = true;   
           }
           
           //reveals surrounding tiles of empty tiles
           if (b.value == 0 && b.flagged == false){
               ms.revealAround(b);
           }
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
    
}
