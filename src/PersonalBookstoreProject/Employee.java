/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package PersonalBookstoreProject;

/**
 * Cashiers/manager employee functions
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
    private int drawerNumber;
    
    /**
     * Employee constructor
     * @param userKey key user logs in with
     * @param isManager true if user has manager access
     * @param name user name
     * @param payRate user payrate
     * @param usesMagCard true if user has magcard
     * @param magCard magcard number if used, 0 otherwise
     */
    public Employee(int userKey,boolean isManager,String name,double payRate,boolean usesMagCard,String magCard){
        this.drawerNumber=drawerNumber;
        this.userKey=userKey;
        this.isManager=isManager;
        this.name=name;
        this.payRate=payRate;
        this.usesMagCard=usesMagCard;
        this.magCard=magCard;
    }
    /**
     * gets user key
     * @return user key
     */
    public int getUserKey(){
        return userKey;
    }/**
     * sets user key
     * @param userKey users key 
     */
    public void setUserKey(int userKey){
        this.userKey=userKey;
    }/**
     * gets access level
     * @return true if manager
     */
    public boolean isManager(){
        return isManager;
    }/**
     * sets access level
     * @param isManager true if user has manager access
     */
    public void setAccess(boolean isManager){
        this.isManager=isManager;
    }
    /**
     * gets user name
     * @return name
     */
    public String getName(){
        return name;
    }/**
     * sets name
     * @param name users name
     */
    public void setName(String name){
        this.name=name;
    }/**
     * gets payrate
     * @return payrate
     */
    public double getPayRate(){
        return payRate;
    }/**
     * sets payrate
     * @param payRate user payrate
     */
    public void setPayRate(double payRate){
        this.payRate=payRate;
    }/**
     * Assigns user mag card
     * @param card card to be assigned
     */
    public void assignMagCard(String card){
        usesMagCard=true;
        if(card.contains("|")){
        int start = 0;
        int end = card.indexOf("|");
        magCard=card.substring(start, end);//removes part of magstring due to hardware level issues
        }else{
            magCard=card;
        }
    }/**
     * tells if user uses magcard
     * @return true if uses magcard
     */
    public boolean usesMagCard(){
        return usesMagCard;
    }/**
     * gets magcard
     * @return magcard
     */
    public String getMagCard(){
        return magCard;
    }/**
     * sets sales per user
     * @param total new total for user sales
     */
    public void setTotalSales(double total){
        totalSales=total;
    }/**
     * gets total sales
     * @return total sales
     */
    public double getTotalSales(){
        return totalSales;
    }/**
     * sets number of memebers registered
     * @param num number of users registered
     */
    public void setNumberMembersRegistered(int num){
        membersRegistered=num;
    }/**
     * gets members registered
     * @return members registerd
     */
    public int getNumberMembersRegistered(){
        return membersRegistered;
    }

    
  /*  public double calculateCheck(double hoursWorked){
        double paycheck = hoursWorked*payRate;
        return paycheck;
    }
    Possible idea with unknown implimentation
    */
    
}
