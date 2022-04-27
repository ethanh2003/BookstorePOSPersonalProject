/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package PersonalBookstoreProject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Creates and totals sales and cash till; manages user access levels
 *
 * @author Ethan Herring
 */
public class CashRegister implements CashDrawer {
    ArrayList<Employee> employees = new ArrayList<>();
    ArrayList<Products> inventory= new ArrayList<>();
    private double startingTotal = 0;
    private double runningTotal = 0;
    private double finalTotal = 0;
    private double finalSalesTotal = 0;
    private double chargeTotal = 0;
    private double cashTotal = 0;
    private double paidInTotal = 0;
    private double paidOutTotal = 0;
    private double dropAmount = 0;
    private int newMembers=0;
    private ArrayList<String> paidIn = new ArrayList<>();
    private ArrayList<String> paidOut = new ArrayList<>();
    private ArrayList<String> managerApproval = new ArrayList<>();
    private ArrayList<String> purchases;

    /**
     * Generates inital employee numbers
     */
    public CashRegister() {
        Scanner fileScanner;
//                            employees.add(new Employee(3214, true, "Sarah", 16.00, true, ";:<>9772231704?"));
//            employees.add(new Employee(2123, true, "Jamie", 12.00, true, "%S3JIHLS2V0LI?;290510160?"));
//            employees.add(new Employee(1111, true, "Ethan", 20.00, false, "0"));
//            employees.add(new Employee(2222, false, "Alex", 10.00, false, "0"));
//            employees.add(new Employee(5324, false, "Jade", 10.00, false, "0"));
//            employees.add(new Employee(4241, false, "Carter", 11.00, false, "0"));
        try {
            fileScanner = new Scanner(new File("EmployeeData.csv"));
            fileScanner.nextLine();
            while (fileScanner.hasNext()) {
                String line = fileScanner.nextLine();
                String[] lineSpliter = line.split(",");

                String userKey = lineSpliter[0];
                String isManager = lineSpliter[1];
                String name = lineSpliter[2];
                String payRate = lineSpliter[3];
                String usesMagCard = lineSpliter[4];
                String magCard = null;
                if (usesMagCard.equals("true")) {
                    magCard = lineSpliter[5];
                } else {
                    magCard = "0";
                }
                employees.add(new Employee(Integer.parseInt(userKey), Boolean.parseBoolean(isManager), name, Double.parseDouble(payRate), Boolean.parseBoolean(usesMagCard), magCard));
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(CashRegister.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     * Sets total for inital till
     *
     * @param amount start amount for till
     */
    @Override
    public void startingDrawerTotal(double amount) {
        startingTotal = amount;
        runningTotal = amount;
        System.out.println("Starting Total is " + startingTotal);
    }

    /**
     * Runs end of day providing sales and drawer totals
     *
     * @param restart allows you to choose if you want to reset till totals
     */
    @Override
    public void endOfDayTotal(int restart,int userKey) {
        FileOutputStream fs = null;
        try {
            finalTotal = cashTotal + paidInTotal - paidOutTotal;
            finalSalesTotal = cashTotal + chargeTotal;
            double amountInDrawer = finalTotal + startingTotal;
            dropAmount = amountInDrawer - startingTotal;

            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM-dd-yyyy HH_mm_ss");
            DateTimeFormatter date = DateTimeFormatter.ofPattern("MM-dd-yyyy");
            DateTimeFormatter time = DateTimeFormatter.ofPattern("HH:mm:ss");

            LocalDateTime now = LocalDateTime.now();;
            String path = "./EODReports/CashDrawerTotal_" + dtf.format(now) + ".txt";
            fs = new FileOutputStream(path);
            PrintWriter outFS = new PrintWriter(fs);
            outFS.println("\t\tEND OF DAY REPORT\t\t");
            for(Employee e:employees){
                if(e.getUserKey()==userKey){
                                outFS.println("Ran by: "+e.getName());
                                outFS.println("Date: "+date.format(now)+" Time: "+time.format(now));
                }
            }
            outFS.println("***************************");
            outFS.println("Total Sales: " + finalSalesTotal);
            outFS.println("Credit/Debit Card Sales: " + chargeTotal);
            outFS.println("Cash Sales: " + cashTotal);
            outFS.println("Paid In Total: " + paidInTotal);
            outFS.println("Paid Out Total: " + paidOutTotal);
            outFS.println("****************************");
            outFS.println("Amount Expected in Drawer: " + Math.round(amountInDrawer * 100.0) / 100.0);
            outFS.println("Starting Balance: " + startingTotal);
            outFS.println("Drop Amount: " + Math.round(dropAmount * 100.0) / 100.0);
            outFS.println("****************************");
            outFS.println("Paid in's");
            for (String s : paidIn) {
                outFS.println(s);
            }
            outFS.println("****************************");
            outFS.println("Paid Out's");
            for (String s : paidOut) {
                outFS.println(s);
            }
            outFS.println("********************************");
            outFS.println("Sales Per User");
            outFS.println("User Name:\tTotal Sales: ");
            for (Employee e : employees) {
                if(e.getUserKey()!=977223){
                outFS.println(e.getName() + "\t\t" + e.getTotalSales());
                }//key 977223 is blocked because it should not be used for sign in and sales, only for keycard
            }
            outFS.println("********************************");
            outFS.println("New Members: "+newMembers);
            
            for(Employee e:employees){
                if(e.getUserKey()!=977223){
                outFS.println(e.getName()+"\t\t"+e.getNumberMembersRegistered());
            }}//key 977223 is blocked because it should not be used for sign in and sales, only for keycard
            outFS.println("********************************");
            outFS.println("Manager Overrides");
            for(String s:managerApproval){
                outFS.println(s);
            }
            outFS.println("********************************");
            outFS.println("Todays purcahses");
            for(String s:purchases){
            outFS.println(s);
            }
            outFS.println("***************************");

            
            if (restart == 1) {
                runningTotal = startingTotal;
                finalTotal = 0;
                finalSalesTotal = 0;
                chargeTotal = 0;
                cashTotal = 0;
                paidInTotal = 0;
                paidOutTotal = 0;
                dropAmount = 0;
                for (int i = 0; i < paidIn.size(); i++) {
                    paidIn.remove(i);
                }
                
                for (int i = 0; i < paidOut.size(); i++) {
                    paidOut.remove(i);
                }
                for (int i = 0; i < managerApproval.size(); i++) {
                    managerApproval.remove(i);
                }
                for (int i = 0; i < purchases.size(); i++) {
                    purchases.remove(i);
                }                
                outFS.println("Totals have been reset. Drawer Total is: " + startingTotal);
                outFS.close();
            } else {
                outFS.println("Total was not reset. Current Drawer Total is: " + runningTotal);
                outFS.close();
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(CashRegister.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                fs.close();

            } catch (IOException ex) {
                Logger.getLogger(CashRegister.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * Performs paidIn functions to add money to till
     *
     * @param amount amount added
     * @param reason reason it was added
     * @param managerKey validates and stores manager key
     */
    @Override
    public void paidIn(double amount, String reason, int managerKey) {
        for (int i = 0; i < employees.size(); i++) {
            if (managerKey == employees.get(i).getUserKey()) {
                runningTotal = runningTotal + amount;
                paidInTotal = amount + paidInTotal;
                reason = (reason + " amount:" + amount + " Key#:" + managerKey + " Name: " + employees.get(i).getName());
                paidIn.add(reason);
            }
        }
    }

    /**
     * Performs Paid out to subtract money from till
     *
     * @param amount amount subtracted
     * @param reason reason
     * @param managerKey validates and records manager key
     */
    @Override
    public void paidOut(double amount, String reason, int managerKey) {
        for (int i = 0; i < employees.size(); i++) {
            if (managerKey == employees.get(i).getUserKey()) {
                runningTotal = runningTotal - amount;
                paidOutTotal = amount + paidOutTotal;
                reason = (reason + " amount:" + amount + " Key#:" + managerKey + " Name: " + employees.get(i).getName());
                paidOut.add(reason);
            }
        }
    }

    /**
     * Creates manager key
     *
     * @param mngrKey key to be added
     */
    public void addEmployee(int userKey, boolean isManager, String name, double payRate, boolean usesMag, String mag) {
        if (!"0".equals(mag) && mag != null) {
            if (mag.contains("|")) {
                int start = 0;
                int end = mag.indexOf("|");
                mag = mag.substring(start, end);
            }
        }
        employees.add(new Employee(userKey, isManager, name, payRate, usesMag, mag));
    }

    /**
     * removes manager key
     *
     * @param mngrKey key to be removed
     */
    public void removeEmployee(int userKey) {
        for (int i = 0; i < employees.size(); i++) {
            if (userKey == employees.get(i).getUserKey()) {
                employees.remove(i);
            }
        }
    }

    public void changeToManager(int key) {
        for (int i = 0; i < employees.size(); i++) {
            if (key == employees.get(i).getUserKey()) {
                employees.get(i).setAccess(true);
            }
        }
    }

    /**
     * Validates key to see if it has manager access
     *key 977223 is blocked for validation because it is only useable for keycard
     * @param mngrKey key to be checked
     * @return true if it is a manager key, false otherwise
     */
    public boolean validateManagerKey(int mngrKey) {
        for (int i = 0; i < employees.size(); i++) {
            if (mngrKey == employees.get(i).getUserKey()) {
                if(mngrKey!=977223){
                if (employees.get(i).isManager() == true) {
                    return true;
                }}
            }
        }
        return false;
    }

    /**
     * confirms key belongs to cashier
     *key 977223 is blocked for validation because it is only useable for keycard
     * @param cashKey key to be checked
     * @return true if belongs to cashier. false otherwise
     */
    public boolean validateCashierKey(int cashKey) {
        for (int i = 0; i < employees.size(); i++) {
            if (cashKey == employees.get(i).getUserKey()) {
                if(cashKey!=977223){
                if (employees.get(i).isManager() == false) {
                    return true;
                }
                }
            }
        }
        return false;
    }

    /**
     * Checks key agaisnt manager and cashier keys
     *key 977223 is blocked for validation because it is only useable for keycard
     * @param key key to be checked
     * @return true if found, false otherwise
     */
    public boolean validateAllKeys(int key) {
        for (int i = 0; i < employees.size(); i++) {
            if(key!=977223){
            if (key == employees.get(i).getUserKey()) {
                return true;
            }
            }
        }
        return false;
    }

    /**
     * Adds cash sale to total and adds to till
     *
     * @param amount amount of total order
     */
    public void cashSale(double amount,int userKey) {
        cashTotal = amount + cashTotal;
        runningTotal = amount + runningTotal;
        for(Employee e:employees){
            if(userKey==e.getUserKey()){
                double userSales=e.getTotalSales()+amount;
                e.setTotalSales(userSales);
            }
        }
        
    }

    /**
     * Tracks credit card sales
     *
     * @param amount amount of credit card sale
     */
    public void chargeSale(double amount,int userKey) {
        chargeTotal = amount + chargeTotal;
        //runningTotal = amount + runningTotal;
                for(Employee e:employees){
            if(userKey==e.getUserKey()){
                double userSales=e.getTotalSales()+amount;
                e.setTotalSales(userSales);
            }
        }
    }

    public void saveEmployees() {
        FileOutputStream employeeFS = null;
        try {
            employeeFS = new FileOutputStream("./EmployeeData.csv");
            PrintWriter employeeOut = new PrintWriter(employeeFS);
            employeeOut.println("userKey,isManager,name,payRate,usesMagCard,MagCard");
            for (Employee e : employees) {
                String card = e.getMagCard();
                employeeOut.println(e.getUserKey() + "," + e.isManager() + "," + e.getName() + "," + e.getPayRate() + "," + e.usesMagCard() + "," + e.getMagCard());

            }
            employeeOut.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(CashRegister.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                employeeFS.close();
            } catch (IOException ex) {
                Logger.getLogger(CashRegister.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void getEmployeeData(int key) {
        for (int i = 0; i < employees.size(); i++) {
            if (key == employees.get(i).getUserKey()) {
                System.out.println(employees.get(i).getName() + " " + employees.get(i).getUserKey() + " " + employees.get(i).getMagCard() + " " + employees.get(i).getPayRate());
            }
        }
    }

    public void assignMag(int userKey, String mag) {

        for (Employee e : employees) {
            if (e.getUserKey() == userKey) {
                e.assignMagCard(mag);
                e.getMagCard();
            }
        }
    }

    public boolean usesMag(int userKey) {
        for (Employee e : employees) {
            if (e.getUserKey() == userKey) {
                if (e.usesMagCard() == true) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean validateMagCard(int userKey, String mag) {

        if (mag.contains("?") || mag.contains("|")) {
            int start = 0;
            int end = mag.indexOf("|");
            mag = mag.substring(start, end);
        }
        for (Employee e : employees) {
            if (e.getUserKey() == userKey) {
                if (e.usesMagCard() == true) {
                    if (mag.equals(e.getMagCard())) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public boolean validateMagCardSolo(String mag, String function, int cashierKey) {
        if (mag.contains("?") || mag.contains("|")) {
            int start = 0;
            int end = mag.indexOf("|");
            mag = mag.substring(start, end);
        }
        for (Employee e : employees) {
            if (e.usesMagCard() == true) {
                if (e.isManager() == true) {
                    if (mag.equals(e.getMagCard())) {
                        for (Employee c : employees) {
                            if (c.getUserKey() == cashierKey) {
                                managerApproval.add(e.getName() + " approved " + function + " for " + c.getName());
                                return true;
                            }
                        }
                    }
                }
            }
        }
            return false;
        }
    
    public ArrayList<Employee> importEmployeeData(){
        return employees;
    }
    public void importPurchaseLog(ArrayList<String> s){
        purchases = s;
    }
    public void countNewMembers(int userKey){
        newMembers++;
        for(Employee e:employees){
            if(e.getUserKey()==userKey){
                int num=e.getNumberMembersRegistered()+1;
                e.setNumberMembersRegistered(num);
            }
        }
    }

        /**
         * Problem with paidin/paidout reasons translating from pos to
         * cashregister class
         */
    


}
