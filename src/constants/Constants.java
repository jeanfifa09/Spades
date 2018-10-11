/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package constants;

/**
 *
 * @author kwhiting
 */
public class Constants 
{
    public static final int NUM_AI_PLAYERS = 3;
    public static final int NUM_CARDS = 52;
    public static final int CARDS_EACH = 13;
    public static final int MIN_BID = 1;
    public static final int WINNING_SCORE = 200;
    public static final int ONE = 0;
    public static final int TWO = 1;
    public static final int NUM_PLAYERS = 4;

    public enum Color
    {
        RED,
        BLACK
    }
    
    public enum Suit 
    {
        CLUBS (0), 
        DIAMONDS (1),
        HEARTS (2), 
        SPADES (3);
        
        private int rank;
        
        public int getRank()
        {
            return rank;
        }
        
        private Suit(int rank)
        {
            this.rank = rank;
        }
    }
    
    public enum Face
    {
       TWO (2),
       THREE (3),
       FOUR (4),
       FIVE (5),
       SIX (6),
       SEVEN (7),
       EIGHT (8),
       NINE (9),
       TEN (10),
       JACK (11),
       QUEEN (12),
       KING (13),
       ACE (14);
       
       private int value;
       
       public int getValue()
       {
           return value;
       }
       
       private Face(int value)
       {
           this.value = value;
       }
    }
}
