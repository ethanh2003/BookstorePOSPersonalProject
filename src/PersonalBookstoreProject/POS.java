/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package PersonalBookstoreProject;

import java.util.*;
import java.math.*;
import javax.swing.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Driver Class
 *
 * @author Ethan Herring
 */
public class POS {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        System.out.println("*************************");
        System.out.println("Manager Key:1111");
        System.out.println("Cashier Key:2222");
        System.out.println("Regular Member Number:801223094");
        System.out.println("Premium Member Number:801223095");
        System.out.println("Manager Override MagCard: 977223");
        System.out.println("*************************");
        Purchase purchase = new Purchase();
        CashRegister cashDrawer = new CashRegister();
        purchase.createPastInventory();

        boolean exit = false;
        double price = 0;
        double subTotal = 0;
        double discount = 0;
        int paymentType = 0;
        int customerID = 0;
        boolean EODRan = false;
        boolean tillStarted = false;
        boolean validKey = false;
        int cardNumber = 0;
        int userKey = 0;
        double amount = 0;
        int userType = 0;
        String reason = null;
        double startTotal = 0;

        while (!exit) {
            try {
                //Loops code until user wants to exit
                Scanner sc = new Scanner(System.in);
                while (!validKey) {
                    //Loops until login is valid

                    System.out.println("Please enter Login Key:");
                    userKey = sc.nextInt();
                    if (cashDrawer.validateAllKeys(userKey) == true || userKey == 68403) {
                        //68403 is being used as an override key during testing. can only create a new employee.

                        if (cashDrawer.usesMag(userKey)) {
                            System.out.println("Please swipe User Mag Card");
                            String mag = sc.next();
                            if (cashDrawer.validateMagCard(userKey, mag)) {
                                validKey = true;
                            } else {
                                System.out.println("Invalid Mag Card");
                            }
                        } else {
                            validKey = true;
                        }
                        //checks key to see if valid
                    } else {
                        System.out.println("Invalid Key! Try again");
                    }
                }
                while (!tillStarted) {//loops until manager opens till
                    if (cashDrawer.validateManagerKey(userKey) == true || userKey == 68403) {  //checks if user is manager
                        boolean validTotal = false;
                        while (!validTotal) {   //loops until till amount is over 0
                            System.out.println("Enter Starting Till Amount:");
                            startTotal = sc.nextDouble();
                            if (startTotal <= 0) {
                                System.out.println("Please enter a number above 0");
                            } else {
                                validTotal = true;
                            }
                        }
                        cashDrawer.startingDrawerTotal(startTotal);
                        //creates drawer
                        tillStarted = true;
                    } else {
                        System.out.println("Manager Required to Initalize Till");
                        System.out.println("Enter 1 to have manager swipe and 2 to log in again");

                        int managerSwipe = sc.nextInt();//Allows user to swipe manager card to give temporary access to manager function//Allows user to swipe manager card to give temporary access to manager function//Allows user to swipe manager card to give temporary access to manager function
                        if (managerSwipe == 2) {
                            System.out.println("Please enter Login Key:");
                            userKey = sc.nextInt();
                        } else {
                            System.out.println("Please swipe User Mag Card");
                            String mag = sc.next();
                            if (cashDrawer.validateMagCardSolo(mag, "Initalizing Till", userKey)) {
                                boolean validTotal = false;
                                while (!validTotal) {   //loops until till amount is over 0
                                    System.out.println("Enter Starting Till Amount:");
                                    startTotal = sc.nextDouble();
                                    if (startTotal <= 0) {
                                        System.out.println("Please enter a number above 0");
                                    } else {
                                        validTotal = true;
                                    }
                                }
                                cashDrawer.startingDrawerTotal(startTotal);
                                //creates drawer
                                tillStarted = true;
                            } else {
                                System.out.println("invalid mag card");
                            }
                        }

                    }
                }
                // see what the user wants to do
                System.out.println("Please select from the following menu of options, by typing a number:");
                System.out.println("\t 1. Purchase Book");
                System.out.println("\t 2. Add Customer");
                System.out.println("\t 3. Pay fee");
                System.out.println("\t 4. Upgrade Member");
                System.out.println("\t 5. get Inventory List");
                System.out.println("\t 6. Compare Prices");
                System.out.println("\t 7. Switch Users");
                System.out.println("\t 8. Get List Of Customers");
                System.out.println("\t 9. Manager Functions");
                System.out.println("\t 10. Exit");

                int num = sc.nextInt();
                switch (num) {
                    case 1:
                        EODRan = false;
                        purchase.getInventory();
                        //prints inventory for customer to choose from
                        double orderTotal = 0.0;
                        boolean exit1 = false;
                        int xit = 2;
                        while (xit != 1) {
                            //loops until user is done choosing items
                            boolean valid = false;
                            while (!valid) {
                                try {
                                    //loops if barcode is invalid
                                    System.out.println("Enter Item Barcode:");
                                    int barcode = sc.nextInt();
                                    price = purchase.purchaseBook(barcode);
                                    if (price == -12.0) {
                                        //determins if barcode is valid
                                        System.out.println("please enter a valid number");
                                        valid = false;
                                    } else {
                                        valid = true;
                                    }
                                } catch (InterruptedException ex) {
                                    Logger.getLogger(POS.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            }
                            orderTotal = orderTotal + price;
                            //calculates running total
                            System.out.println("is that all? enter 1 for yes and 2 for no");
                            xit = sc.nextInt();
                            //checks if user is done shopping
                            if (xit != 2 && xit != 1) {
                                //Validates answer
                                System.out.println("Invalid! Please Enter Either 1 or 2");
                            }
                            while (xit != 2 && xit != 1) {      //loops until user enters valid number
                                System.out.println("is that all? enter 1 for yes and 2 for no");
                                xit = sc.nextInt();

                                System.out.println("Enter either 1 or 2");
                            }
                        }

                        boolean valid = false;
                        while (!valid) {
                            //loops until userID is valid
                            System.out.println("Enter Member ID:");
                            customerID = sc.nextInt();
                            discount = purchase.finalizePurchase(orderTotal, customerID);
                            if (purchase.validateMember(customerID) == false) {
                                System.out.println("Not valid Member");
                                valid = false;
                                //validates user ID
                            } else {
                                valid = true;
                            }
                        }
                        subTotal = orderTotal;
                        if (discount > 0) {

                            orderTotal = orderTotal - (orderTotal * discount);
                        }
                        //applies discount if valid
                        if (discount > 0) {
                            System.out.println("you have received a rewards discount of " + discount * 90 + "%");
                        }
                        orderTotal = Math.round(orderTotal * 100.0) / 100.0;
                        System.out.println("your total is " + orderTotal);
                        boolean validNum = false;
                        double newTotal = 0;
                        System.out.println("Enter 1 to apply manual discount");
                        int apply = sc.nextInt();
                        if (apply == 1) {
                            boolean manager = false;
                            if (cashDrawer.validateManagerKey(userKey)) {
                                manager = true;
                            } else {
                                System.out.println("Manager is required for this function");
                                System.out.println("Enter 1 for manager swipe and 2 to abort");
                                int managerSwipe = sc.nextInt();//Allows user to swipe manager card to give temporary access to manager function
                                if (managerSwipe == 1) {
                                    System.out.println("Please swipe User Mag Card");
                                    String mag = sc.next();
                                    if (cashDrawer.validateMagCardSolo(mag, "Apply Discount", userKey)) {
                                        manager = true;
                                    }
                                }
                            }
                            if (manager) {

                                System.out.println("\t1. Employee Discount");
                                System.out.println("\t2. 20% Discount");
                                System.out.println("\t3. 10% Discount");
                                System.out.println("\t4. Manager Discount");
                                System.out.println("\t5. 100% Discount");
                                System.out.println("\t6. Custom Dollar");
                                System.out.println("\t7. Custom Percent");
                                int choice = sc.nextInt();
                                if (choice == 1 || choice == 4) {
                                    System.out.println("Enter Employee Name: ");
                                    String employeeName = sc.next();
                                    newTotal = cashDrawer.applyPresetDiscount(choice, userKey, orderTotal, 0, 0, employeeName);
                                } else if (choice == 2 || choice == 3 || choice == 5) {
                                    System.out.println("Enter Reason");
                                    String discountReason = sc.next();
                                    newTotal = cashDrawer.applyPresetDiscount(choice, userKey, orderTotal, 0, 0, discountReason);
                                } else if (choice == 6) {
                                    System.out.println("Enter Reason");
                                    String discountReason = sc.next();
                                    System.out.println("Enter Dollar Amount");
                                    Double dollarDiscount = sc.nextDouble();
                                    newTotal = cashDrawer.applyPresetDiscount(choice, userKey, orderTotal, dollarDiscount, 0, discountReason);

                                } else if (choice == 7) {
                                    System.out.println("Enter Reason");
                                    String discountReason = sc.next();
                                    System.out.println("Enter Percent");
                                    Double percentDiscount = sc.nextDouble();
                                    newTotal = cashDrawer.applyPresetDiscount(choice, userKey, orderTotal, 0, percentDiscount, discountReason);

                                }
                            }
                        }
                        while (!validNum) {
                            //loops until user chooses valid number
                            System.out.println("Select 1 for cash and 2 for card");
                            paymentType = sc.nextInt();
                            if (paymentType != 1 && paymentType != 2) {
                                //loops until user chooses valid number                            
                                System.out.println("Enter a valid Number");
                            } else {
                                validNum = true;
                            }
                        }
                        String method = null;
                        double cashGiven = 0;
                        if (paymentType == 1) {
                            method = "cash";
                            boolean validCash = false;
                            while (!validCash) {
                                //loops until cash paid is greater than or equal to toal
                                System.out.println("Enter Cash given:");
                                cashGiven = sc.nextDouble();
                                double changeOwed = cashGiven - newTotal;
                                changeOwed = Math.round(changeOwed * 100.0) / 100.0;
                                if (changeOwed >= 0) {
                                    //calcualtes change owed
                                    System.out.println("You owe them " + changeOwed);
                                    cashDrawer.cashSale(newTotal, userKey);
                                    validCash = true;
                                } else {
                                    System.out.println("Amount does not cover total bill. \nPlease enter valid amount");
                                }
                            }
                        } else if (paymentType == 2) {
                            //adds sales to charge total
                            cashDrawer.chargeSale(newTotal, userKey);
                            method = "card";
                        }
                        double customDiscount = orderTotal - newTotal;
                        purchase.reciept(subTotal, newTotal, customerID, userKey, discount, method, cashGiven, customDiscount);
                        //creates user reciept
                        break;

                    case 2:
                        cashDrawer.countNewMembers(userKey);

                        System.out.println("Enter Name");
                        String name = sc.next();
                        System.out.println("Enter Birthday");
                        String birthday = sc.next();
                        System.out.println("Enter Email");
                        String email = sc.next();
                        System.out.println("Enter true if premium member and false otherwise");
                        boolean premium = sc.nextBoolean();

                        System.out.println("enter true if fee is paid and false otherwise");
                        boolean feePaid = sc.nextBoolean();
                        valid = false;
                        while (!valid) {
                            //loops until card number is valid
                            System.out.println("Enter Last Four of Card Number");
                            cardNumber = sc.nextInt();
                            if (cardNumber < 10000 && cardNumber > 999) {
                                valid = true;
                            } else {
                                System.out.println("Please Enter only 4 digits");
                            }
                        }

                        double totalSpent = 0;
                        purchase.newMember(name, birthday, email,
                                premium, feePaid, cardNumber, totalSpent, userKey);
                        //creates new member
                        break;
                    case 3:
                        customerID = 0;
                        while (purchase.validateMember(customerID) == false) {
                            //loops until valid member is added
                            System.out.println("Enter Member ID:");
                            customerID = sc.nextInt();
                            if (purchase.validateMember(customerID) == false) {
                                System.out.println("Enter valid Member ID");
                            }
                        }
                        System.out.println(purchase.payFee(customerID));
                        //sets member fee to paid
                        break;
                    case 4:
                        customerID = 0;
                        while (purchase.validateMember(customerID) == false) {
                            //loops until valid member is added
                            System.out.println("Enter Member ID:");
                            customerID = sc.nextInt();
                            if (purchase.validateMember(customerID) == false) {
                                System.out.println("Enter valid Member ID");
                            }
                        }

                        purchase.upgradeMember(customerID);
                        //upgrades member to premium
                        break;
                    case 5:
                        purchase.getInventory();
                        //prints inventory
                        break;

                    case 6:
                        boolean validBrcde = false;
                        int prdct1 = 0;
                        int prdct2 = 0;
                        while (!validBrcde) {
                            //loops until barcode is valid
                            System.out.println("Please enter first Product Barcode");
                            prdct1 = sc.nextInt();
                            if (purchase.validateBarcode(prdct1) == true) {
                                validBrcde = true;
                            } else {
                                System.out.println("Please enter valid barcode");
                            }
                        }
                        validBrcde = false;
                        while (!validBrcde) {        //loops until barcode is valid            
                            System.out.println("Please enter second Product Barcode");
                            prdct2 = sc.nextInt();
                            if (purchase.validateBarcode(prdct2) == true) {
                                validBrcde = true;
                            } else {
                                System.out.println("Please enter valid barcode");
                            }
                        }
                        purchase.compare(prdct1, prdct2);
                        //compares price of two products
                        break;

                    case 7:
                        valid = false;
                        while (!valid) {
                            //loops until valid login key
                            System.out.println("Please enter Login Key:");
                            userKey = sc.nextInt();
                            if (cashDrawer.validateAllKeys(userKey) == true) {
                                //validates login
                                if (cashDrawer.usesMag(userKey) == true) {
                                    System.out.println("Please swipe User Mag Card");
                                    String mag = sc.next();
                                    if (cashDrawer.validateMagCard(userKey, mag)) {
                                        valid = true;
                                    } else {
                                        System.out.println("invalid mag card");
                                    }
                                } else {
                                    valid = true;
                                }

                            } else {
                                System.out.println("Please enter Valid cashier number");
                            }
                        }
                        break;
                    //swithes logged in user
                    case 8:
                        purchase.getListOfCustomers();
                        break;
                    //gets cusomer list
                    case 9:
                        int managerSwipe=0;
                        String mag = null;
                                    System.out.println("\t 1. Add Book");
                                    System.out.println("\t 2. Add CD");
                                    System.out.println("\t 3. Add DVD");
                                    System.out.println("\t 4. Restock Product");
                                    System.out.println("\t 5. Total Inventory Value");
                                    System.out.println("\t 6. Run Drawer");
                                    System.out.println("\t 7. Paid In");
                                    System.out.println("\t 8. Paid Out");
                                    System.out.println("\t 9. New Employee");
                                    System.out.println("\t 10. Remove Employee");
                                    System.out.println("\t 11. Set Employee Access");
                                    System.out.println("\t 12. Assign MagCard");
                                    System.out.println("\t 13. Get Employee Data");
                                    int manMenu = sc.nextInt();
                                    switch (manMenu) {
                                        case 1:
                                            if (cashDrawer.validateManagerKey(userKey)) {
                                                //validates access
                                                price = 0.0;
                                                System.out.println("Enter Title");
                                                String title = sc.next();
                                                System.out.println("Enter Author");
                                                String author = sc.next();
                                                while (price <= 0) {
                                                    //validates price
                                                    System.out.println("Enter Price");
                                                    price = sc.nextDouble();
                                                    if (price <= 0) {
                                                        System.out.println("Enter Price above 0");
                                                    }
                                                }
                                                System.out.println("Enter Stock");
                                                int stock = sc.nextInt();
                                                purchase.newBook(title, author, price, stock);
                                                //creates new book
                                            } else {
                                                System.out.println("Manager is required for this function");
                                                System.out.println("Enter 1 for manager swipe and 2 to abort");
                                                 managerSwipe = sc.nextInt();//Allows user to swipe manager card to give temporary access to manager function
                                                if (managerSwipe == 1) {
                                                    System.out.println("Please swipe User Mag Card");
                                                     mag = sc.next();
                                                    if (cashDrawer.validateMagCardSolo(mag, "new book", userKey)) {                                                    //validates access
                                                        price = 0.0;
                                                        System.out.println("Enter Title");
                                                        String title = sc.next();
                                                        System.out.println("Enter Author");
                                                        String author = sc.next();
                                                        while (price <= 0) {
                                                            //validates price
                                                            System.out.println("Enter Price");
                                                            price = sc.nextDouble();
                                                            if (price <= 0) {
                                                                System.out.println("Enter Price above 0");
                                                            }
                                                        }
                                                        System.out.println("Enter Stock");
                                                        int stock = sc.nextInt();
                                                        purchase.newBook(title, author, price, stock);
                                                    } else {
                                                        System.out.println("invalid mag card");
                                                    }
                                                }
                                            }
                                            break;
                                        case 2:
                                            if (cashDrawer.validateManagerKey(userKey)) {
                                                //validates access
                                                price = 0.0;
                                                System.out.println("Enter Title");
                                                String title = sc.next();
                                                System.out.println("Enter Author");
                                                String author = sc.next();
                                                while (price <= 0) {
                                                    //validates price
                                                    System.out.println("Enter Price");
                                                    price = sc.nextDouble();
                                                    if (price <= 0) {
                                                        System.out.println("Enter Price above 0");
                                                    }
                                                }
                                                System.out.println("Enter Stock");
                                                int stock = sc.nextInt();
                                                purchase.newCD(title, author, price, stock);
                                                //creates new CD
                                            } else {
                                                System.out.println("Manager is required for this function");
                                                System.out.println("Enter 1 for manager swipe and 2 to abort");
                                                 managerSwipe = sc.nextInt();//Allows user to swipe manager card to give temporary access to manager function
                                                if (managerSwipe == 1) {
                                                    System.out.println("Please swipe User Mag Card");
                                                     mag = sc.next();
                                                    if (cashDrawer.validateMagCardSolo(mag, "new CD", userKey)) {                                                    //validates access
                                                        price = 0.0;
                                                        System.out.println("Enter Title");
                                                        String title = sc.next();
                                                        System.out.println("Enter Author");
                                                        String author = sc.next();
                                                        while (price <= 0) {
                                                            //validates price
                                                            System.out.println("Enter Price");
                                                            price = sc.nextDouble();
                                                            if (price <= 0) {
                                                                System.out.println("Enter Price above 0");
                                                            }
                                                        }
                                                        System.out.println("Enter Stock");
                                                        int stock = sc.nextInt();
                                                        purchase.newCD(title, author, price, stock);
                                                        //creates new CD
                                                    } else {
                                                        System.out.println("invalid mag card");
                                                    }
                                                }
                                            }
                                            break;
                                        case 3:
                                            if (cashDrawer.validateManagerKey(userKey)) {
                                                //validates access
                                                price = 0.0;
                                                System.out.println("Enter Title");
                                                String title = sc.next();
                                                System.out.println("Enter Author");
                                                String author = sc.next();
                                                while (price <= 0) {
                                                    //validates price
                                                    System.out.println("Enter Price");
                                                    price = sc.nextDouble();
                                                    if (price <= 0) {
                                                        System.out.println("Enter Price above 0");
                                                    }
                                                }
                                                System.out.println("Enter Stock");
                                                int stock = sc.nextInt();
                                                purchase.newDVD(title, author, price, stock);
                                                //creates new DVD
                                            } else {
                                                System.out.println("Manager is required for this function");
                                                System.out.println("Enter 1 for manager swipe and 2 to abort");
                                                 managerSwipe = sc.nextInt();//Allows user to swipe manager card to give temporary access to manager function
                                                if (managerSwipe == 1) {
                                                    System.out.println("Please swipe User Mag Card");
                                                     mag = sc.next();
                                                    if (cashDrawer.validateMagCardSolo(mag, "new dvd", userKey)) {                        //validates access
                                                        price = 0.0;
                                                        System.out.println("Enter Title");
                                                        String title = sc.next();
                                                        System.out.println("Enter Author");
                                                        String author = sc.next();
                                                        while (price <= 0) {
                                                            //validates price
                                                            System.out.println("Enter Price");
                                                            price = sc.nextDouble();
                                                            if (price <= 0) {
                                                                System.out.println("Enter Price above 0");
                                                            }
                                                        }
                                                        System.out.println("Enter Stock");
                                                        int stock = sc.nextInt();
                                                        purchase.newDVD(title, author, price, stock);
                                                        //creates new DVD                            
                                                    } else {
                                                        System.out.println("invalid mag card");
                                                    }
                                                }
                                            }
                                            break;
                                        case 4:
                                            valid = false;
                                            int barcode = 0;
                                            int restockAmount = 0;
                                            if (cashDrawer.validateManagerKey(userKey) == true) {
                                                //validates access
                                                while (!valid) {
                                                    //loops until valid barcode
                                                    System.out.println("Enter Barcode:");
                                                    barcode = sc.nextInt();
                                                    if (purchase.validateBarcode(barcode) == true) {
                                                        valid = true;
                                                    }
                                                }
                                                System.out.println("To subtract from inventory, enter negitive number");
                                                System.out.println("Enter Restock Amount:");
                                                restockAmount = sc.nextInt();
                                                System.out.println("Total Updated Stock: " + purchase.restockProduct(barcode, restockAmount));
                                                //restocks product or subtracts from inventory depending on value
                                            } else {
                                                System.out.println("Manager Access is required for this function");
                                                System.out.println("Enter 1 to have manager swipe or 2 to abort");
                                                 managerSwipe = sc.nextInt();//Allows user to swipe manager card to give temporary access to manager function
                                                if (managerSwipe == 1) {
                                                    System.out.println("Please swipe User Mag Card");
                                                     mag = sc.next();
                                                    if (cashDrawer.validateMagCardSolo(mag, "update stock", userKey)) {
                                                        while (!valid) {
                                                            //loops until valid barcode
                                                            System.out.println("Enter Barcode:");
                                                            barcode = sc.nextInt();
                                                            if (purchase.validateBarcode(barcode) == true) {
                                                                valid = true;
                                                            }
                                                        }
                                                        System.out.println("To subtract from inventory, enter negitive number");
                                                        System.out.println("Enter Restock Amount:");
                                                        restockAmount = sc.nextInt();
                                                        System.out.println("Total Updated Stock: " + purchase.restockProduct(barcode, restockAmount));
                                                    } else {
                                                        System.out.println("invalid mag card");
                                                    }
                                                }
                                            }
                                            break;
                                        case 5:
                                            System.out.println("Total Inventory Value:" + purchase.inventoryValue());
                                            //prints value of all items in stock
                                            break;
                                        case 6:
                                            cashDrawer.importPurchaseLog(purchase.condensePurchaseLog());
                                            if (cashDrawer.validateManagerKey(userKey)) {
                                                //checks access level
                                                System.out.println("Enter 1 to reset Till when report is ran");
                                                int reset = sc.nextInt();
                                                cashDrawer.endOfDayTotal(reset, userKey);
                                                EODRan = true;
                                            } else {
                                                System.out.println("Manager Access is required for this function");
                                                System.out.println("Enter 1 to have manager swipe or 2 to abort");
                                                 managerSwipe = sc.nextInt();//Allows user to swipe manager card to give temporary access to manager function
                                                if (managerSwipe == 1) {
                                                    System.out.println("Please swipe User Mag Card");
                                                     mag = sc.next();
                                                    if (cashDrawer.validateMagCardSolo(mag, "run EOD", userKey)) {
                                                        System.out.println("Enter 1 to reset Till when report is ran");
                                                        int reset = sc.nextInt();
                                                        cashDrawer.endOfDayTotal(reset, userKey);
                                                        EODRan = true;
                                                    } else {
                                                        System.out.println("invalid mag card");
                                                    }
                                                }
                                            }
                                            break;
                                        //runs EOD routine
                                        case 7:

                                            if (cashDrawer.validateManagerKey(userKey)) {
                                                //verifys access
                                                EODRan = false;
                                                valid = false;
                                                while (!valid) {
                                                    //loops until amount is valid
                                                    System.out.println("Enter Amount:");
                                                    amount = sc.nextDouble();
                                                    if (amount <= 0) {
                                                        System.out.println("Enter amount above 0. \nIf money needs to be taken out please perform paid out");
                                                    } else {
                                                        valid = true;
                                                    }
                                                }
                                                System.out.println("Enter Reason:");
                                                reason = sc.next();
                                                cashDrawer.paidIn(amount, reason, userKey);
                                            } else {
                                                System.out.println("Manager Access is required for this function");
                                                System.out.println("Enter 1 to have manager swipe or 2 to abort");
                                                 managerSwipe = sc.nextInt();//Allows user to swipe manager card to give temporary access to manager function
                                                if (managerSwipe == 1) {
                                                    System.out.println("Please swipe User Mag Card");
                                                     mag = sc.next();
                                                    if (cashDrawer.validateMagCardSolo(mag, "paid in", userKey)) {
                                                        valid = false;
                                                        while (!valid) {
                                                            //loops until amount is valid
                                                            System.out.println("Enter Amount:");
                                                            amount = sc.nextDouble();
                                                            if (amount <= 0) {
                                                                System.out.println("Enter amount above 0. \nIf money needs to be taken out please perform paid out");
                                                            } else {
                                                                valid = true;
                                                            }
                                                        }
                                                        System.out.println("Enter Reason:");
                                                        reason = sc.next();
                                                        cashDrawer.paidIn(amount, reason, userKey);
                                                    } else {
                                                        System.out.println("invalid mag card");
                                                    }
                                                }
                                            }
                                            break;
                                        //performs paidIN
                                        case 8:

                                            if (cashDrawer.validateManagerKey(userKey)) {
                                                //validates access
                                                EODRan = false;
                                                valid = false;
                                                while (!valid) {
                                                    //loops until amount is valid
                                                    System.out.println("Enter Amount:");
                                                    amount = sc.nextDouble();
                                                    if (amount <= 0) {
                                                        System.out.println("Enter amount above 0. \nIf money needs to be added please perform paid in");
                                                    } else {
                                                        valid = true;
                                                    }
                                                }
                                                System.out.println("Enter Reason:");
                                                reason = sc.next();
                                                cashDrawer.paidOut(amount, reason, userKey);
                                            } else {
                                                valid = false;
                                                System.out.println("Manager Access is required for this function");
                                                System.out.println("Enter 1 to have manager swipe or 2 to abort");
                                                 managerSwipe = sc.nextInt();//Allows user to swipe manager card to give temporary access to manager function
                                                if (managerSwipe == 1) {
                                                    System.out.println("Please swipe User Mag Card");
                                                     mag = sc.next();
                                                    if (cashDrawer.validateMagCardSolo(mag, "paid out", userKey)) {
                                                        while (!valid) {
                                                            //loops until amount is valid
                                                            System.out.println("Enter Amount:");
                                                            amount = sc.nextDouble();
                                                            if (amount <= 0) {
                                                                System.out.println("Enter amount above 0. \nIf money needs to be added please perform paid in");
                                                            } else {
                                                                valid = true;
                                                            }
                                                        }
                                                        System.out.println("Enter Reason:");
                                                        reason = sc.next();
                                                        cashDrawer.paidOut(amount, reason, userKey);

                                                    } else {
                                                        System.out.println("invalid mag card");
                                                    }
                                                }
                                            }
                                            break;
                                        //performs paid out
                                        case 9:
                                            int newUserKey = 0;
                                            if (cashDrawer.validateManagerKey(userKey) || userKey == 68403) {
                                                //validates user access
                                                boolean validCode = false;
                                                while (!validCode) {
                                                    //Loops until valid user code is entered
                                                    System.out.println("Enter new User Code");
                                                    newUserKey = sc.nextInt();
                                                    if (cashDrawer.validateAllKeys(newUserKey) == false) {
                                                        validCode = true;
                                                    } else {
                                                        System.out.println("User code is already in use");
                                                    }
                                                }
                                                System.out.println("Enter Name");
                                                String newName = sc.next();
                                                System.out.println("Enter true if employee is manager and false otherwise");
                                                boolean isManager = sc.nextBoolean();
                                                System.out.println("Enter Payrate");
                                                double payRate = sc.nextDouble();
                                                System.out.println("Enter true if uses MagCard and false otherwise");
                                                boolean usesMag = sc.nextBoolean();
                                                String cardNum = null;
                                                if (usesMag) {
                                                    System.out.println("Swipe mag card");
                                                    cardNum = sc.next();
                                                } else {
                                                    cardNum = "0";
                                                }
                                                cashDrawer.addEmployee(newUserKey, isManager, newName, payRate, usesMag, cardNum);

                                            } else {
                                                System.out.println("Manager Access is required for this function");
                                                System.out.println("Enter 1 to have manager swipe or 2 to abort");
                                                 managerSwipe = sc.nextInt();//Allows user to swipe manager card to give temporary access to manager function
                                                if (managerSwipe == 1) {
                                                    System.out.println("Please swipe User Mag Card");
                                                     mag = sc.next();
                                                    if (cashDrawer.validateMagCardSolo(mag, "new user", userKey)) {
                                                        boolean validCode = false;
                                                        while (!validCode) {
                                                            //Loops until valid user code is entered
                                                            System.out.println("Enter new User Code");
                                                            newUserKey = sc.nextInt();
                                                            if (cashDrawer.validateAllKeys(newUserKey) == false) {
                                                                validCode = true;
                                                            } else {
                                                                System.out.println("User code is already in use");
                                                            }
                                                        }
                                                        System.out.println("Enter Name");
                                                        String newName = sc.next();
                                                        System.out.println("Enter true if employee is manager and false otherwise");
                                                        boolean isManager = sc.nextBoolean();
                                                        System.out.println("Enter Payrate");
                                                        double payRate = sc.nextDouble();
                                                        System.out.println("Enter true if uses MagCard and false otherwise");
                                                        boolean usesMag = sc.nextBoolean();
                                                        String cardNum = null;
                                                        if (usesMag) {
                                                            System.out.println("Swipe mag card");
                                                            cardNum = sc.next();
                                                        } else {
                                                            cardNum = "0";
                                                        }
                                                        cashDrawer.addEmployee(newUserKey, isManager, newName, payRate, usesMag, cardNum);

                                                    } else {
                                                        System.out.println("invalid mag card");
                                                    }
                                                }
                                            }
                                            break;
                                        //creates new user
                                        case 10:
                                            if (cashDrawer.validateManagerKey(userKey)) {
                                                //validates user access
                                                validNum = false;
                                                System.out.println("Enter User Code to remove");
                                                int oldUserKey = sc.nextInt();
                                                if (cashDrawer.validateAllKeys(oldUserKey) == true) {
                                                    cashDrawer.removeEmployee(oldUserKey);
                                                } else {
                                                    System.out.println("User number invalid");
                                                }
                                            } else {
                                                System.out.println("Manager Access is required for this function");
                                                System.out.println("Enter 1 to have manager swipe or 2 to abort");
                                                 managerSwipe = sc.nextInt();//Allows user to swipe manager card to give temporary access to manager function
                                                if (managerSwipe == 1) {
                                                    System.out.println("Please swipe User Mag Card");
                                                     mag = sc.next();
                                                    if (cashDrawer.validateMagCardSolo(mag, "remove user", userKey)) {
                                                        validNum = false;
                                                        System.out.println("Enter User Code to remove");
                                                        int oldUserKey = sc.nextInt();
                                                        if (cashDrawer.validateAllKeys(oldUserKey) == true) {
                                                            cashDrawer.removeEmployee(oldUserKey);
                                                        } else {
                                                            System.out.println("User number invalid");
                                                        }
                                                    } else {
                                                        System.out.println("invalid mag card");
                                                    }
                                                }
                                            }
                                            break;
                                        //removes user
                                        case 11:
                                            if (cashDrawer.validateManagerKey(userKey)) {
                                                //validates access
                                                System.out.println("Enter User Key to switch:");
                                                int switchKey = sc.nextInt();
                                                if (cashDrawer.validateManagerKey(switchKey) == true) {
                                                    System.out.println("User already has manager access");
                                                } else {

                                                    System.out.println("Switch Complete");
                                                }
                                            } else {
                                                System.out.println("Manager Access is required for this function");
                                                System.out.println("Enter 1 to have manager swipe or 2 to abort");
                                                 managerSwipe = sc.nextInt();//Allows user to swipe manager card to give temporary access to manager function
                                                if (managerSwipe == 1) {
                                                    System.out.println("Please swipe User Mag Card");
                                                     mag = sc.next();
                                                    if (cashDrawer.validateMagCardSolo(mag, "upgrade access", userKey)) {
                                                        System.out.println("Enter User Key to switch:");
                                                        int switchKey = sc.nextInt();
                                                        if (cashDrawer.validateManagerKey(switchKey) == true) {
                                                            System.out.println("User already has manager access");
                                                        } else {

                                                            System.out.println("Switch Complete");
                                                        }
                                                    } else {
                                                        System.out.println("invalid mag card");
                                                    }
                                                }
                                            }
                                            break;
                                        //gives user manager access
                                        case 12:
                                            if (cashDrawer.validateManagerKey(userKey)) {

                                                System.out.println("Enter User Key");
                                                int user = sc.nextInt();
                                                System.out.println("Swipe Card");
                                                String card = sc.next();
                                                cashDrawer.assignMag(user, card);
                                            } else {
                                                System.out.println("Manager Access is required for this function");
                                                System.out.println("Enter 1 to have manager swipe or 2 to abort");
                                                 managerSwipe = sc.nextInt();//Allows user to swipe manager card to give temporary access to manager function
                                                if (managerSwipe == 1) {
                                                    System.out.println("Please swipe User Mag Card");
                                                     mag = sc.next();
                                                    if (cashDrawer.validateMagCardSolo(mag, "assign mag card", userKey)) {
                                                        System.out.println("Enter User Key");
                                                        int user = sc.nextInt();
                                                        System.out.println("Swipe Card");
                                                        String card = sc.next();
                                                        cashDrawer.assignMag(user, card);
                                                    } else {
                                                        System.out.println("invalid mag card");
                                                    }
                                                }
                                            }
                                            break;
                                        //Assigns mag card
                                        case 13:
                                            if (cashDrawer.validateManagerKey(userKey)) {
                                                System.out.println("Enter Key");
                                                int userDataKey = sc.nextInt();
                                                cashDrawer.getEmployeeData(userDataKey);
                                            } else {
                                                System.out.println("Manager Access is required for this function");
                                                System.out.println("Enter 1 to have manager swipe or 2 to abort");
                                                 managerSwipe = sc.nextInt();//Allows user to swipe manager card to give temporary access to manager function
                                                if (managerSwipe == 1) {
                                                    System.out.println("Please swipe User Mag Card");
                                                     mag = sc.next();
                                                    if (cashDrawer.validateMagCardSolo(mag, "return employee data", userKey)) {
                                                        System.out.println("Enter Key");
                                                        int userDataKey = sc.nextInt();
                                                        cashDrawer.getEmployeeData(userDataKey);
                                                    } else {
                                                        System.out.println("invalid mag card");
                                                    }
                                                }
                                            }
                                    
                                            break;
                                        //gets employee data
                                        default:
                                            System.out.println("You have entered an invalid number");
                                    }
                        break;
                    case 10:
                        //Requires entering manager key even if logged in as one. 
                        //makes user realize they are exiting the program
                        //68403 is used as a universal override to exit program without running EOD procedures
                        System.out.println("Manager Key Required for exit.");
                        System.out.println("Please enter Manager Key:");
                        int tempXitKey = sc.nextInt();
                        boolean override = false;
                        if (userKey == 68403 || tempXitKey == 68403) {
                            override = true;
                        }
                        if (cashDrawer.validateManagerKey(tempXitKey) || override) {
                            if (cashDrawer.usesMag(tempXitKey)) {
                                System.out.println("Please swipe User Mag Card");
                                 mag = sc.next();
                                if (cashDrawer.validateMagCard(tempXitKey, mag)) {
                                    if (EODRan == true) {
                                        //Validates access level to exit program
                                        exit = true;
                                        purchase.saveData();
                                        cashDrawer.saveEmployees();

                                    } else {
                                        System.out.println("Please Run End of Day Procedures before exiting");
                                    }
                                } else {
                                    System.out.println("invalid mag card");
                                }
                            } else {
                                if (EODRan == true || override) {
                                    //Validates access level to exit program
                                    exit = true;
                                    purchase.saveData();

                                    cashDrawer.saveEmployees();
                                } else {
                                    System.out.println("Please Run End of Day Procedures before exiting");
                                }
                            }
                        } else {
                            System.out.println("Invalid Access; Please Enter Manager Code");
                        }

                        //exits program
                        break;
                    default:
                        System.out.println("Invalid Entry. \nPlease enter the number listed next to the function");
                    //outputs if entered case is invalid
                }

            } catch (InputMismatchException e) {
                System.out.println("you have entered an invalid character. please try again");
            }

        }
    }

}
