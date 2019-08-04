    package minesweeper;

import java.util.*;
import java.io.*;

public class Highscore{
    Minesweeper ms;
    File scores;
    BufferedWriter fw;
    BufferedReader fr;
    String currentScores;
    
   
    public Highscore(Minesweeper ms) {
        this.ms = ms;
        currentScores = new String();
        
        try
        {   //creating the buffered reader/writer for the scores text file
            scores = new File("scores.txt");
            fw = new BufferedWriter(new FileWriter(scores, true));
            fr = new BufferedReader(new FileReader(scores));
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }

    //returns the text file of scores as a string
    String readScore(){
        try
        {
            char[] allAtOnce = new char[(int)scores.length()];
            int charsRead = fr.read(allAtOnce);
            currentScores = String.valueOf(allAtOnce);
            fr.close();//close the file
            fr = new BufferedReader(new FileReader(scores));
        }
        
        catch(IOException e)
        {
            e.printStackTrace();
        }
        return (String.valueOf(currentScores));
    }

    //writes the difficulty and time of completed run into a text file and reads it to update recent runs
    public void recordScore(){
        try
        {
            char[] allAtOnce = new char[(int)scores.length()];
            int charsRead = fr.read(allAtOnce);
            fw.write(String.format("<html><body><br>%s | %d seconds<body/><html/>", ms.difficulty, ms.time)); 
            ms.scoreLabel.setText(String.format("<html>Recent scores:%s</html>" , String.valueOf(allAtOnce)));
            fr.close();
            fw.close();
            fw = new BufferedWriter(new FileWriter(scores, true));
            fr = new BufferedReader(new FileReader(scores));
            
        }
        
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }
}