/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core;

/**
 *
 * @author kwhiting
 */

import constants.Constants;
import constants.Constants.Suit;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import java.util.Scanner;

public class Game 
{
    // member variables
    private Suit trump = Suit.SPADES;
    private Player leadPlayer;
    private Player dealer;
    private Player wonTrick;
    private int round;
    private ArrayList<Team> teams;
    private Deck deck;
    private Scanner scan;
    private ArrayList<Player> table;
    private int dealerIdx;
    
    //custom constructor
    public Game()
    {
        createTeams();
//        outputTeams();
        createDeck();
        setTable();
        dealHand();
        displayHands();
        
        play();
    }
    
    private void createTeams()
    {
        // instantiate the teams ArrayList
        teams = new ArrayList();
        
        // instantiate Team One and add to ArrayList
        Team teamOne = new Team();  
        teamOne.setTeamName("Team One");
        teams.add(teamOne);
        
        
        // instantiate Team Two and add to ArrayList
        Team teamTwo = new Team();
        teamTwo.setTeamName("Team Two");
        teams.add(teamTwo);
        
        // adding Human Player to Team One        
        scan = new Scanner(System.in);
        System.out.println("Enter human player name");
        String name = scan.next();        
        
        HumanPlayer hp = new HumanPlayer();
        hp.setName(name);
        System.out.println("Human Player added to Team One");
        teamOne.getTeam().add(hp);
        
        // create the AI Players and add them to a team
        for(int p = 1; p <= Constants.NUM_AI_PLAYERS; p++)
        {
            AiPlayer aip = new AiPlayer();
            aip.setName("AI " + p);
                     
            // add AI Player to a team
            if(teamOne.getTeam().size() < 2)
                teamOne.getTeam().add(aip);
            else
                teamTwo.getTeam().add(aip);            
        }   
    }
    
    private void outputTeams()
    {        
        for(Team team : teams)
        {
            System.out.println("Team includes players:");
            
            for(Player player : team.getTeam())
            {
                System.out.println("Player: " + player.getName());
            }
        }        
    }
    
    private void setTable()
    {
        // players are set up so that team members sit across from each other
        // therefore the deal would be to TeamOne.PlayerOne, TeamTwo.PlayerTwo,
        // TeamOne.PlayerTwo, TeamTwo.PlayerTwo as an example
        table = new ArrayList();
        
        // get the teams in the game
        Team teamOne = teams.get(Constants.ONE);
        Team teamTwo = teams.get(Constants.TWO);
        
        // get the players from each team
        Player teamOnePlayerOne = teamOne.getTeam().get(Constants.ONE);
        Player teamOnePlayerTwo = teamOne.getTeam().get(Constants.TWO);
        Player teamTwoPlayerOne = teamTwo.getTeam().get(Constants.ONE);
        Player teamTwoPlayerTwo = teamTwo.getTeam().get(Constants.TWO);
        
        // we want to explicitly dictate which seat each player is in so we are 
        // using the add method that takes two arguments, one to set the position
        // in the ArrayList and the associated object at that position
        table.add(0, teamOnePlayerOne);
        table.add(1, teamTwoPlayerOne);
        table.add(2, teamOnePlayerTwo);
        table.add(3, teamTwoPlayerTwo);

        // select the first dealer
        Random random = new Random();
        dealerIdx = random.nextInt(Constants.NUM_PLAYERS);
        dealer = table.get(dealerIdx);    
        
        System.out.println("Players at the table are");
        for(Player player : table)
        {
            System.out.println(player.getName());
        }
    }
    
    private void dealHand()
    {
        System.out.println("Player " + dealer.getName() + " will deal the hand");
        
        // create an index to keep track of which player got the card; 
        // reset when get to 3
        // set the playerIdx based on which player was selected as the dealer and
        int playerIdx; 

        if(dealerIdx < 3)
            playerIdx = dealerIdx++;
        else
            playerIdx = 0;

        // loop through the shuffled deck and deal 13 cards to each player        
        for(Iterator<Card> currentCard = deck.getDeck().iterator(); currentCard.hasNext(); )
        {
            Card card = currentCard.next();

//            System.out.println("Dealing " + card.getFace() + " of " + card.getSuit());
            // add card to a player's hand
//            System.out.println("Adding to player " + table.get(playerIdx).getName() + " hand");
            table.get(playerIdx).getHand().add(card);
            
            // increment the player index until value of 3, then reset to 0
            if(playerIdx == 3)
                playerIdx = 0;
            else
                playerIdx++;
            
            // remove the card from the deck after it has been dealt
            currentCard.remove();
        }
    }
    
    private void displayHands()
    {
        for(Team team : teams)
        {
            System.out.println("*************************");
            System.out.println("        " + team.getTeamName().toUpperCase());
            System.out.println("*************************");      
            
            for(Player player : team.getTeam())
            {
                System.out.println("Sorting hand by suit and face");
                player.sortBySuit();
                
                if(player instanceof HumanPlayer)
                {
                    player.displayHand();                    
                }
            }
        }
    }
    private void createDeck()
    {
        deck = new Deck();
    }
   
    private void play()
    {
        System.out.println("**************");
        System.out.println("Play the game!");
        System.out.println("**************\n");
        System.out.println("***************");
        System.out.println("Get player bids");
        System.out.println("***************");       
        
        // starting with the player to the left of the dealer
        // each player plays the best card in their hand
        // players must follow suit if they can, if not they can trump if they
        // want to 
        getBids();
    }
    
    private void getBids()
    {
        // counter for bids placed
        int bidsPlaced = 0;
        
        // set the lead player
        int leadIdx;
        
        if(dealerIdx < 3)
            leadIdx = ++dealerIdx;
        else
            leadIdx = 0;
        
        leadPlayer = table.get(leadIdx);
        
        System.out.println("Lead player is player " + leadPlayer.getName());

        // start with the lead player then move on to the others
        if(leadPlayer instanceof HumanPlayer)
        {
            // prompt player to play a card
            HumanPlayer hp = (HumanPlayer)leadPlayer;
            hp.placeBid();              
        }
        else
        {
            AiPlayer ai = (AiPlayer)leadPlayer;
            ai.placeBid();
        }          
            
        bidsPlaced++;
              
        // now get the remaining three bids
        int playerIdx;
               
        if(leadIdx < 3)
            playerIdx = ++leadIdx;
        else
            playerIdx = 0;
             
        // player after lead player
        Player nextPlayer = table.get(playerIdx);
        
        while(bidsPlaced < Constants.NUM_PLAYERS)
        {
            System.out.println("Getting bid from player " + nextPlayer.getName());
            if(nextPlayer instanceof HumanPlayer)
            {
                // prompt player to play a card
                HumanPlayer hp = (HumanPlayer)nextPlayer;
                hp.placeBid();              
            }
            else
            {
                AiPlayer ai = (AiPlayer)nextPlayer;
                ai.placeBid();
            }
            
            // increment the counter
            bidsPlaced++;
            
            // move to the next player
            if(playerIdx < 3)
                ++playerIdx;
            else
                playerIdx = 0;
            
            nextPlayer = table.get(playerIdx);
        }
    }
    
    private void playHand()
    {
        
    }
    /**
     * @return the leads
     */
    public Player getLeads() 
    {
        return leadPlayer;
    }

    /**
     * @param leads the leads to set
     */
    public void setLeads(Player leads) 
    {
        this.leadPlayer = leads;
    }

    /**
     * @return the deals
     */
    public Player getDeals() 
    {
        return dealer;
    }

    /**
     * @param deals the deals to set
     */
    public void setDeals(Player deals) 
    {
        this.dealer = deals;
    }

    /**
     * @return the wonTrick
     */
    public Player getWonTrick() 
    {
        return wonTrick;
    }

    /**
     * @param wonTrick the wonTrick to set
     */
    public void setWonTrick(Player wonTrick) 
    {
        this.wonTrick = wonTrick;
    }

    /**
     * @return the round
     */
    public int getRound() 
    {
        return round;
    }

    /**
     * @param round the round to set
     */
    public void setRound(int round) 
    {
        this.round = round;
    }
}
