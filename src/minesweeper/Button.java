package minesweeper;

import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JButton;


/**
 *
 * @author joshuay.lee
 */

public class Button extends JButton {
    int value;
    int[] location = new int[2];
    int row;
    int column;
    boolean exposed;
    boolean flagged;
    Minesweeper ms;
    Highscore hs;
    
    public Button(Minesweeper ms){
        this.ms = ms;
        exposed = false;
        flagged = false;
        this.setPreferredSize(new Dimension(ms.tilesize, ms.tilesize));
    }
    
    void setValue(int value){
        this.value = value;
    }
    
    void setLoc(int row, int column){
        this.location[1] = row;
        this.location[0] = column;
    }
    
    //reveals the tile value if unflagged. flag restriction is overridden if game is over
    void reveal(){
        if (this.flagged == false || ms.inPlay == false){
            if (this.exposed == false){
                if (this.value == -1){
                    ms.inPlay = false;
                    ms.timerEnabled = false;
                    this.setIcon(ms.MINE);     
                }   
                else {
                    //revealing non-mine tiles
                    ms.tilecount--;
                    ms.timerEnabled = true;
                    this.setBackground(Color.LIGHT_GRAY);
                    if (this.value == 0){
                        this.setText(" ");
                    }
                    else {
                       this.setText(Integer.toString(value));
                    }
                    this.expose();
                    this.setIcon(null);
                    

                } //wins game if number of un-exposed tiles equals number of mines
                if(ms.tilecount == ms.NUM_MINES && ms.inPlay){
                    ms.inPlay = false;
                    ms.timerEnabled = false;
                    ms.winGame();
                    System.out.println("win");
                }
            }
        }
    }
    

    //toggles the flagging of tiles
    void flag(){
        if (!this.isExposed() && ms.inPlay){
            if (this.flagged == false){
                this.flagged = true;
                this.setBackground(Color.LIGHT_GRAY);
                this.setIcon(ms.FLAG);
            }
            else{
                this.flagged = false;
                this.setIcon(ms.STONE);
            }
        }
    }
    
    boolean isExposed(){
        return exposed;
    }
    
    void expose(){
        exposed = true;
    }
    

}
