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
    ArrayList<Products> inventory = new ArrayList<>();
    private double startingTotal = 0;
    private double runningTotal = 0;
    private double finalTotal = 0;
    private double finalSalesTotal = 0;
    private double chargeTotal = 0;
    private double cashTotal = 0;
    private double paidInTotal = 0;
    private double paidOutTotal = 0;
    private double dropAmount = 0;
    private int newMembers = 0;
    private ArrayList<String> paidIn = new ArrayList<>();
    private ArrayList<String> paidOut = new ArrayList<>();
    private ArrayList<String> managerApproval = new ArrayList<>();
    private ArrayList<String> purchases;
    private ArrayList<String> managerComps = new ArrayList<>();
    private int compTotal = 0;

    /**
     * Reads saved employee data
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
     * @param userKey used to get name of user running report
     */
    @Override
    public void endOfDayTotal(int restart, int userKey) {
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
            for (Employee e : employees) {
                if (e.getUserKey() == userKey) {
                    outFS.println("Ran by: " + e.getName());
                    outFS.println("Date: " + date.format(now) + " Time: " + time.format(now));
                }
            }
            outFS.println("***************************");
            outFS.println("Total Sales: " + finalSalesTotal);
            outFS.println("Credit/Debit Card Sales: " + chargeTotal);
            outFS.println("Cash Sales: " + cashTotal);
            outFS.println("Paid In Total: " + paidInTotal);
            outFS.println("Paid Out Total: " + paidOutTotal);
            outFS.println("Total Comps: "+ compTotal);
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
                if (e.getUserKey() != 977223) {
                    outFS.println(e.getName() + "\t\t" + e.getTotalSales());
                }//key 977223 is blocked because it should not be used for sign in and sales, only for keycard
            }
            outFS.println("********************************");
            outFS.println("New Members: " + newMembers);

            for (Employee e : employees) {
                if (e.getUserKey() != 977223) {
                    outFS.println(e.getName() + "\t\t" + e.getNumberMembersRegistered());
                }
            }//key 977223 is blocked because it should not be used for sign in and sales, only for keycard
            outFS.println("********************************");
            outFS.println("Manager Comps");
            for(String s:managerComps){
                outFS.println(s);
            }
            outFS.println("********************************");
            outFS.println("Manager Overrides");
            for (String s : managerApproval) {
                outFS.println(s);
            }
            outFS.println("********************************");
            outFS.println("Todays purcahses");
            for (String s : purchases) {
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
                compTotal = 0;
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
                mag = mag.substring(start, end);//removes part of magstring due to hardware level issues
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

    /**
     * Updates user access to manager
     *
     * @param key
     */
    public void changeToManager(int key) {
        for (int i = 0; i < employees.size(); i++) {
            if (key == employees.get(i).getUserKey()) {
                employees.get(i).setAccess(true);
            }
        }
    }

    /**
     * Validates key to see if it has manager access key 977223 is blocked for
     * validation because it is only useable for keycard
     *
     * @param mngrKey key to be checked
     * @return true if it is a manager key, false otherwise
     */
    public boolean validateManagerKey(int mngrKey) {
        for (int i = 0; i < employees.size(); i++) {
            if (mngrKey == employees.get(i).getUserKey()) {
                if (mngrKey != 977223) {
                    if (employees.get(i).isManager() == true) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * confirms key belongs to cashier key 977223 is blocked for validation
     * because it is only useable for keycard
     *
     * @param cashKey key to be checked
     * @return true if belongs to cashier. false otherwise
     */
    public boolean validateCashierKey(int cashKey) {
        for (int i = 0; i < employees.size(); i++) {
            if (cashKey == employees.get(i).getUserKey()) {
                if (cashKey != 977223) {
                    if (employees.get(i).isManager() == false) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * Checks key agaisnt manager and cashier keys key 977223 is blocked for
     * validation because it is only useable for keycard
     *
     * @param key key to be checked
     * @return true if found, false otherwise
     */
    public boolean validateAllKeys(int key) {
        for (int i = 0; i < employees.size(); i++) {
            if (key != 977223) {
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
     * @param userKey takes user ley to track user sales
     */
    public void cashSale(double amount, int userKey) {
        cashTotal = amount + cashTotal;
        runningTotal = amount + runningTotal;
        for (Employee e : employees) {
            if (userKey == e.getUserKey()) {
                double userSales = e.getTotalSales() + amount;
                e.setTotalSales(userSales);
            }
        }

    }

    /**
     * Tracks credit card sales
     *
     * @param amount amount of credit card sale
     * @param userKey takes user ley to track user sales
     */
    public void chargeSale(double amount, int userKey) {
        chargeTotal = amount + chargeTotal;
        //runningTotal = amount + runningTotal;
        for (Employee e : employees) {
            if (userKey == e.getUserKey()) {
                double userSales = e.getTotalSales() + amount;
                e.setTotalSales(userSales);
            }
        }
    }

    /**
     * Writes and saves employee data to file
     */
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

    /**
     * returns employee data
     *
     * @param key key of user to return data
     */
    public void getEmployeeData(int key) {
        for (int i = 0; i < employees.size(); i++) {
            if (key == employees.get(i).getUserKey()) {
                System.out.println(employees.get(i).getName() + " " + employees.get(i).getUserKey() + " " + employees.get(i).getMagCard() + " " + employees.get(i).getPayRate());
            }
        }
    }

    /**
     * Assigns magCard to user
     *
     * @param userKey locates user to assign card to
     * @param mag magCard number
     */
    public void assignMag(int userKey, String mag) {

        for (Employee e : employees) {
            if (e.getUserKey() == userKey) {
                e.assignMagCard(mag);
                e.getMagCard();
            }
        }
    }

    /**
     * Validates of user uses magCard
     *
     * @param userKey used to locate if user uses mag
     * @return true if uses magCard
     */
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

    /**
     * validates if magCard matches user key
     *
     * @param userKey user being validates
     * @param mag magcard swiped
     * @return true if magcard matches one on file
     */
    public boolean validateMagCard(int userKey, String mag) {

        if (mag.contains("?") || mag.contains("|")) {
            int start = 0;
            int end = mag.indexOf("|");
            mag = mag.substring(start, end);
        }//removes part of magstring due to hardware level issues
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

    /**
     * Validates that magcard belongs to a manager regardless of user key
     *
     * @param mag card swiped being validated
     * @param function function user is wanting approval for
     * @param cashierKey key of user requestiong approval
     * @return true if key belongs to any manager
     */
    public boolean validateMagCardSolo(String mag, String function, int cashierKey) {
        if (mag.contains("?") || mag.contains("|")) {
            int start = 0;
            int end = mag.indexOf("|");
            mag = mag.substring(start, end);//removes part of magstring due to hardware level issues
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

    /**
     * Exports employee data to another file
     *
     * @return employee array
     */
    public ArrayList<Employee> importEmployeeData() {
        return employees;
    }

    /**
     * imports purchase log from another file
     *
     * @param s purcahse log
     */
    public void importPurchaseLog(ArrayList<String> s) {
        purchases = s;
    }

    /**
     * Counts new members that are registered
     *
     * @param userKey Key of user registering new members, used to track new
     * memebrs per user
     */
    public void countNewMembers(int userKey) {
        newMembers++;
        for (Employee e : employees) {
            if (e.getUserKey() == userKey) {
                int num = e.getNumberMembersRegistered() + 1;
                e.setNumberMembersRegistered(num);
            }
        }
    }

    /**
     * Applies manager discount
     *
     * @param discountSelection select discount based on purpose
     * @param userKey key of user performing action
     * @param subTotal total before discount
     * @param customDollar dollars to be taken off
     * @param customPercent percent to be taken off
     * @param reason reason for discount
     * @return total after discount
     */
    public double applyPresetDiscount(int discountSelection, int userKey, double subTotal, double customDollar, double customPercent, String reason) {
        double customPercentDecimal = 0.0;
        if (customPercent > 0) {
            customPercentDecimal = customPercent / 100.0;
        }
        double newTotal;
        for (Employee e : employees) {
            if (e.getUserKey() == userKey) {
                switch (discountSelection) {
                    case 1:
                        newTotal = subTotal - (subTotal * .40);
                        managerComps.add(e.getName() + "Applied Employee Discount\t Employee Name: " + reason);
                        compTotal += subTotal - newTotal;
                        return newTotal;
                    case 2:
                        newTotal = subTotal - (subTotal * .20);
                        managerComps.add(e.getName() + "Applied 20% Discount\t Reason: " + reason);
                        compTotal += subTotal - newTotal;

                        return newTotal;
                    case 3:
                        newTotal = subTotal - (subTotal * .10);
                        managerComps.add(e.getName() + " Applied 10% Discount\t Reason: " + reason);
                        compTotal += subTotal - newTotal;

                        return newTotal;
                    case 4:
                        newTotal = subTotal - (subTotal * .65);
                        managerComps.add(e.getName() + " Applied Manager Discount\t Employee Name: " + reason);
                        compTotal += subTotal - newTotal;

                        return newTotal;
                    case 5:
                        newTotal = 0;
                        managerComps.add(e.getName() + " Applied 100% Discount\t Reason: " + reason);
                        compTotal += subTotal - newTotal;

                        return newTotal;
                    case 6:
                        newTotal = subTotal - customDollar;
                        managerComps.add(e.getName() + " Applied $" + customDollar + " Discount\t Reason: " + reason);
                        compTotal += subTotal - newTotal;

                        return newTotal;
                    case 7:
                        newTotal = subTotal - (subTotal * customPercentDecimal);
                        managerComps.add(e.getName() + " Applied " + customPercent + "% Discount\t Reason: " + reason);
                        compTotal += subTotal - newTotal;

                        return newTotal;
                }
            }
        }
        return 0;
    }

    /**
     * Problem with paidin/paidout reasons translating from pos to cashregister
     * class
     */
}
