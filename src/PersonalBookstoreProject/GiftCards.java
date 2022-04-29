/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package PersonalBookstoreProject;

import java.util.*;

/**
 * Giftcard and related functions
 * @author Ethan Herring
 */
public class GiftCards {
    private double amount;
    private int cardNumber;
     /**
     * Giftcard constructor
     * @param amount inital amount
     * @param cardNumber card number
     */  
    public GiftCards(double amount, int cardNumber){
    this.amount=amount;
    this.cardNumber=cardNumber;
    }
    /**
     * gets amount on card
     * @return amount
     */
    public double getAmount(){
        return amount;
    }/**
     * sets amount on card
     * @param amount amount on card
     */
    public void setAmount(double amount){
        this.amount=amount;
    }
    /**
     * gets card number
     * @return card number
     */
    public int getCardNumber(){
        return cardNumber;
    }
    /**
     * sets card number
     * @param cardNumber card number
     */
    public void setCardNumber(int cardNumber){
        this.cardNumber=cardNumber;
    }/**
     * subtracts total from giftcard
     * @param total total of purchase
     * @return remaning balance if any or returns new total if card does not cover it
     */
    public double makePurchase(double total){
        if(total>=amount){
        amount-=total;
        return amount;
        }
        else{
            double newTotal=total-amount;
            amount=0;
            return newTotal;
        }
        
    }
    
}
