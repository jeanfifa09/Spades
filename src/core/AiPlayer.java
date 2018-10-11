/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core;

import constants.Constants.Face;
import constants.Constants.Suit;

/**
 *
 * @author kwhiting
 */
public class AiPlayer extends Player
{
    
    @Override
    public Card playCard() 
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int placeBid() 
    {
        System.out.println(super.getName() + " place your bid");
        int bid = 0;
        
        // The AI will assume that if they have an Ace or King of any suit they will get the trick
        // The AI will assume that if they have the Jack or Queen of Spades they will get the trick 
        for(Card card : super.getHand())
        {
            // this condition covers the Ace and King of any suit
            if(card.getFace() == Face.ACE || card.getFace() == Face.KING)
            {
                bid++;
            }
            // this condition only covers the Jack and Queen of Spades
            // if the player had the King or Ace of Spades it was already counted
            else if(card.getSuit() == Suit.SPADES && 
                    card.getFace().getValue() > Face.TEN.getValue())
            {
                bid++;
            }
        }

        if(bid == 0)
            bid = 1;
        
        super.setBid(bid);
        
        System.out.println("Player " + this.getName() + " bid " + bid);
        return super.getBid();
    }
}
