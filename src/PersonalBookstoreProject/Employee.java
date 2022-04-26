/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package PersonalBookstoreProject;

/**
 *
 * @author Ethan Herring
 */
public class Employee {
    private int userKey;
    private boolean isManager;
    private String name;
    private double payRate;
    private boolean usesMagCard;
    private String magCard;
    private double totalSales;
    private int membersRegistered;
    public Employee(int userKey,boolean isManager,String name,double payRate,boolean usesMagCard,String magCard){
        this.userKey=userKey;
        this.isManager=isManager;
        this.name=name;
        this.payRate=payRate;
        this.usesMagCard=usesMagCard;
        this.magCard=magCard;
    }
    public int getUserKey(){
        return userKey;
    }public void setUserKey(int userKey){
        this.userKey=userKey;
    }public boolean isManager(){
        return isManager;
    }public void setAccess(boolean isManager){
        this.isManager=isManager;
    }public String getName(){
        return name;
    }public void setName(String name){
        this.name=name;
    }public double getPayRate(){
        return payRate;
    }public void setPayRate(double payRate){
        this.payRate=payRate;
    }public void assignMagCard(String card){
        usesMagCard=true;
        if(card.contains("|")){
        int start = 0;
        int end = card.indexOf("|");
        magCard=card.substring(start, end);
        }else{
            magCard=card;
        }
    }public boolean usesMagCard(){
        return usesMagCard;
    }
    public String getMagCard(){
        return magCard;
    }public void setTotalSales(double total){
        totalSales=total;
    }public double getTotalSales(){
        return totalSales;
    }public void setNumberMembersRegistered(int num){
        membersRegistered=num;
    }public int getNumberMembersRegistered(){
        return membersRegistered;
    }
    
  /*  public double calculateCheck(double hoursWorked){
        double paycheck = hoursWorked*payRate;
        return paycheck;
    }
    Possible idea with unknown implimentation
    */
    
}
