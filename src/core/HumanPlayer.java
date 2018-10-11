/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core;

import java.util.Scanner;

/**
 *
 * @author kwhiting
 */
public class HumanPlayer extends Player
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
        
        Scanner scanner = new Scanner(System.in);
        int bid = scanner.nextInt();

        if(bid < 1)
        {
            System.out.println("You must bid at least one trick");
            bid = 1;
        }
        
        super.setBid(bid);
        return super.getBid();
    }
}
