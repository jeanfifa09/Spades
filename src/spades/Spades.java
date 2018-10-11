/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spades;

import core.Deck;
import core.Game;
import javax.swing.JOptionPane;


/**
 *
 * @author kwhiting
 */
public class Spades 
{

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) 
    {
        // outputting to the console
        System.out.println("Welcome to Spades!");

        // this is a static method call ClassName.methodName()
        // do not need to create an instance of the class to use it
        JOptionPane.showMessageDialog(null, "Let's Play Spades!"); 
        
        Game game = new Game();
    }    
}
