/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package PersonalBookstoreProject;

import java.util.ArrayList;

/**
 * abstract parent class for members and premium members
 *
 * @author Ethan Herring
 */
public abstract class Customers {

    private ArrayList<Customers> customerData = new ArrayList<>();
    private String name;
    private String birthday;
    private String email;
    public boolean premium;
    private boolean feePaid;
    private int lastFourCardNumber;
    public double totalSpent;
    private int memberNumber = 801223098;

    /**
     * Constructor for base customer class, parent to PremiumMember, and member
     *
     * @param name customer name
     * @param birthday customer birthday
     * @param email customer email
     * @param totalSpent total spent overtime
     * @param memberNumber autogenerated member id
     */
    public Customers(String name, String birthday, String email,
            double totalSpent, int memberNumber) {
        this.name = name;
        this.birthday = birthday;
        this.email = email;
        this.totalSpent = totalSpent;
        this.memberNumber = memberNumber;
    }

    /**
     * gets name
     *
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * sets name
     *
     * @param a name
     * @return name
     */
    public String setName(String a) {
        name = a;
        return name;
    }

    /**
     * prints birthday
     *
     * @return birthday
     */
    public String getBirthday() {
        return birthday;
    }

    /**
     * sets birthday
     *
     * @param a birthday
     * @return birthday
     */
    public String setBirthday(String a) {
        birthday = a;
        return birthday;
    }

    /**
     * prints member number
     *
     * @return member number
     */
    public int getMemberNumber() {
        return memberNumber;
    }

    /**
     * incriments member number
     *
     * @return next member number
     */
    public int incrimentMemberNumber() {
        memberNumber++;
        return memberNumber;
    }

    /**
     * prints email
     *
     * @return email
     */
    public String getEmail() {
        return email;
    }

    /**
     * sets email
     *
     * @param a email
     * @return email
     */
    public String setEmail(String a) {
        email = a;
        return email;
    }

    /**
     * returns true if premium
     *
     * @return true or false depending on premium
     */
    public boolean isPremium() {
        if (premium) {
            return true;
        }
        return false;
    }

    /**
     * sets premium status
     *
     * @return true when status is set
     */
    public boolean setPremium() {
        premium = true;
        System.out.println("Customer is now Premium");
        return premium;
    }

    /**
     * prints if fee is paid or not
     *
     * @return true if paid or false otherwise
     */
    public boolean payFee() {
        if (feePaid == true) {
            return true;
        }
        return false;
    }

    /**
     * sets fee to paid
     *
     * @return true once paid
     */
    public boolean setFeePaid() {

        feePaid = true;
        return true;

    }

    /**
     * prints last four of card
     *
     * @return last four of card
     */
    public int getLastFourCardNumber() {
        return lastFourCardNumber;
    }

    /**
     * sets last 4 of card
     *
     * @param a last 4 of card
     * @return updated last 4 of card
     */
    public int setLastFourCardNumber(int a) {
        return lastFourCardNumber;
    }

    /**
     * Prints total spent
     *
     * @return total spent
     */
    public double getTotalSpent() {
        totalSpent = Math.round(totalSpent * 100.0) / 100.0;
        return totalSpent;
    }

    /**
     * updates total spent
     *
     * @param a total spent in current purchase
     * @return new total spent
     */
    public double addTotalSpent(double a) {
        totalSpent = totalSpent + a;
        return totalSpent;
    }

    /**
     * determines discount
     *
     * @return discount amount
     */
    public abstract double rewardsDiscount();

}
