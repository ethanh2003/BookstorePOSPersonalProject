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
 * Contains classes to purcahse products and add members
 *
 * @author Ethan Herring
 */
public class Purchase implements BookstoreSpecification, ValidationCheck {
    CashRegister cashDrawer=new CashRegister();
    private ArrayList<Products> inventory = new ArrayList<Products>();
    private ArrayList<Customers> customerData = new ArrayList<Customers>();
    private ArrayList<Employee> employees = cashDrawer.importEmployeeData();
    private ArrayList<String> reciept = new ArrayList<>();
    private ArrayList<Products> dailyPurchaseLog = new ArrayList<>();
    

    public Purchase() {
        try {
            //productInventory.csv
            Scanner fileScanner = new Scanner(new File("productInventory.csv"));
            while (fileScanner.hasNext()) {
                String line = fileScanner.nextLine();
                String[] lineSplit = line.split(",");

                if ("book".equals(lineSplit[1])) {
                    String title = lineSplit[2];
                    String author = lineSplit[3];
                    String inStock = lineSplit[4];
                    String price = lineSplit[5];
                    inventory.add(new Book(title, author, Double.parseDouble(price), Integer.parseInt(inStock)));

                } else if ("cd".equals(lineSplit[1])) {
                    String title = lineSplit[2];
                    String author = lineSplit[3];
                    String inStock = lineSplit[4];
                    String price = lineSplit[5];
                    inventory.add(new CD(title, author, Double.parseDouble(price), Integer.parseInt(inStock)));
                } else if ("dvd".equals(lineSplit[1])) {
                    String title = lineSplit[2];
                    String author = lineSplit[3];
                    String inStock = lineSplit[4];
                    String price = lineSplit[5];
                    inventory.add(new DVD(title, author, Double.parseDouble(price), Integer.parseInt(inStock)));
                }
            }
            try {
                //3 is premium index
                fileScanner = new Scanner(new File("customerData.csv"));
                fileScanner.nextLine();
                while (fileScanner.hasNext()) {
                    String line = fileScanner.nextLine();
                    String[] lineSpliter = line.split(",");
                    if ("true".equals(lineSpliter[3])) {
                        String name = lineSpliter[0];
                        String birthday = lineSpliter[1];
                        String email = lineSpliter[2];
                        String premium = lineSpliter[3];
                        String feePaid = lineSpliter[4];
                        String memNum = lineSpliter[5];
                        String lastFourCardNumber = lineSpliter[6];
                        String totalSpent = lineSpliter[7];
                        customerData.add(new PremiumMember(name, birthday, email, Double.parseDouble(totalSpent), Integer.parseInt(memNum), Boolean.parseBoolean(feePaid), Integer.parseInt(lastFourCardNumber)));
                    } else if ("false".equals(lineSpliter[3])) {
                        String name = lineSpliter[0];
                        String birthday = lineSpliter[1];
                        String email = lineSpliter[2];
                        String memNum = lineSpliter[5];
                        String totalSpent = lineSpliter[7];
                        customerData.add(new Member(name, birthday, email, Double.parseDouble(totalSpent), Integer.parseInt(memNum)));
                    }
                }

            } catch (FileNotFoundException ex) {
                Logger.getLogger(Purchase.class.getName()).log(Level.SEVERE, null, ex);
            }

            /**
             * Creates test data
             */
//                inventory.add(new DVD("Indiana Jones", "No idea", 12.99, 3));
//                inventory.add(new Book("Lucid", "No Idea", 10.99, 5));
//                inventory.add(new CD("Forever 21", "Taylor Swift", 15.99, 3));
//
//                customerData.add(new Member("Test1", "september 6th 2003", "eherrin6@uncc.edu",
//                        230.45, 801223094));
//                customerData.add(new PremiumMember("Test2", "December 6th 2003", "eherrin7@uncc.edu",
//                        90.45, 801223095, true, 2432));
//                customerData.add(new Member("Test3", "October 6th 2003", "eherrin3@uncc.edu",
//                        150.45, 801223096));
//                customerData.add(new PremiumMember("Test17", "september 6th 2000", "eherrin@uncc.edu",
//                        455.45, 801223097, true, 3456));
//        
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Purchase.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     * Checks inventory, lowers stock, and returns price and book information
     *
     * @param barcode barcode of product
     * @return Price of book
     */
    public double purchaseBook(int barcode) throws InterruptedException {
        double orderPrice = 0;
        for (int i = 0; i < inventory.size(); i++) {
            if (inventory.get(i).getBarcode() == barcode) {

                orderPrice = inventory.get(i).getPrice();
                int stock = inventory.get(i).getStock();
                inventory.get(i).setStock(stock - 1);

                System.out.println("***************************");
                System.out.println("You have selected: Title: " + inventory.get(i).getName() + "\nAuthor: " + inventory.get(i).getAuthor()
                        + "\nBarcode: " + inventory.get(i).getBarcode() + "\nPrice: " + inventory.get(i).getPrice() + "\nStock Left: " + inventory.get(i).getStock());
                System.out.println("***************************");
                reciept.add("Title: " + inventory.get(i).getName() + "\nAuthor: " + inventory.get(i).getAuthor()
                        + "\nBarcode: " + inventory.get(i).getBarcode() + "\nPrice: " + inventory.get(i).getPrice() + "\nStock Left: " + inventory.get(i).getStock());
               int num=inventory.get(i).getNumberPurchased()+1;
                inventory.get(i).setNumberPurchased(num); 
                dailyPurchaseLog.add(inventory.get(i));

                if (inventory.get(i).getStock() == 0) {
                    System.out.println("Last of item stock has been purchased. " + inventory.get(i).getName() + " has been removed from inventory");
                    inventory.remove(i);
                }

                return orderPrice;
            }
        }
        return -12.0;

    }

    /**
     * updates member information with new totalSpent and determines discount if
     * applicable
     *
     * @param orderTotal toatl of current order
     * @param memberID member ID
     * @return Discount amount if applicable
     */
    public double finalizePurchase(double orderTotal, int memberID) {
        for (int i = 0; i < customerData.size(); i++) {
            if (memberID == customerData.get(i).getMemberNumber()) {
                customerData.get(i).addTotalSpent(orderTotal);
                if (customerData.get(i) instanceof PremiumMember) {
                    if (customerData.get(i).getTotalSpent() <= 100) {
                        return .05;
                    } else if (customerData.get(i).getTotalSpent() >= 100 && customerData.get(i).getTotalSpent() <= 200) {
                        return .15;
                    } else if (customerData.get(i).getTotalSpent() > 200) {
                        return .20;
                    }
                }
            }
        }
        return -5;

    }

    /**
     * Gets total spent for that member
     *
     * @param memberID memberID
     * @return total spent
     */
    public double getTotalSpent(int memberID) {
        for (int i = 0; i < customerData.size(); i++) {
            if (memberID == customerData.get(i).getMemberNumber()) {
                return customerData.get(i).getTotalSpent();
            }
        }
        return 0;
    }

    /**
     * upgrades member from regular to premium
     *
     * @param memberID memberID
     */
    public void upgradeMember(int memberID) {
        for (int i = 0; i < customerData.size(); i++) {
            if (memberID == customerData.get(i).getMemberNumber()) {

                String name = customerData.get(i).getName();
                String birthday = customerData.get(i).getBirthday();
                String email = customerData.get(i).getEmail();
                double totalSpent = customerData.get(i).getTotalSpent();
                int memberNumber = customerData.get(i).getMemberNumber();
                boolean feePaid = customerData.get(i).payFee();
                int lastFourCardNumber = customerData.get(i).getLastFourCardNumber();
                //Copying information from regular to premium
                customerData.remove(i);
                //removes regular member data and creates premium 
                customerData.add(new PremiumMember(name, birthday, email,
                        totalSpent, memberNumber, feePaid, lastFourCardNumber));
                System.out.println("Member has been upgraded");
            }
        }

    }

    /**
     * Returns all items in inventory
     *
     */
    public void getInventory() {
        for (int i = 0; i < inventory.size(); i++) {
            System.out.println("Title: " + inventory.get(i).getName() + "\nAuthor: " + inventory.get(i).getAuthor()
                    + "\nBarcode: " + inventory.get(i).getBarcode() + "\nPrice: " + inventory.get(i).getPrice() + "\nStock: " + inventory.get(i).getStock());
            if (inventory.get(i) instanceof Book) {
                System.out.println("Product Type: Book");
            } else if (inventory.get(i) instanceof CD) {
                System.out.println("Product Type: CD");
            } else if (inventory.get(i) instanceof DVD) {
                System.out.println("Product Type: DVD");
            }
            System.out.println("***************************");
        }
    }

    /**
     * Creates new Book
     *
     * @param name name
     * @param author author
     * @param price price
     * @param stock amount in stock
     */
    public void newBook(String name, String author, double price, int stock) {
        inventory.add(new Book(name, author,
                price, stock));

    }

    /**
     * Creates new CD
     *
     * @param name name
     * @param author author
     * @param price price
     * @param stock amount in stock
     */
    public void newCD(String name, String author, double price, int stock) {
        inventory.add(new CD(name, author,
                price, stock));
    }

    /**
     * Creates new DVD
     *
     * @param name name
     * @param author author
     * @param price price
     * @param stock amount in stock
     */
    public void newDVD(String name, String author, double price, int stock) {
        inventory.add(new DVD(name, author,
                price, stock));
    }

    /**
     * Creates new Member
     *
     * @param name name
     * @param birthday birthday
     * @param email email
     * @param premium is premium?
     * @param feePaid is fee paid?
     * @param lastFourCardNumber last 4 of card
     * @param totalSpent total spent overtime
     */
    public void newMember(String name, String birthday, String email,
            boolean premium, boolean feePaid, int lastFourCardNumber, double totalSpent, int userKey) {
        int memberNumber = customerData.get(customerData.size() - 1).incrimentMemberNumber();
        if (premium) {
            customerData.add(new PremiumMember(name, birthday, email,
                    totalSpent, memberNumber, feePaid, lastFourCardNumber));
        } else {
            customerData.add(new Member(name, birthday, email, totalSpent, memberNumber));
        }

        System.out.println("Their member Number is:" + memberNumber);

    }

    /**
     * sets member fee to paid
     *
     * @param memberID memberID
     * @return fee paid if member is found
     */
    public String payFee(int memberID) {
        for (int i = 0; i < customerData.size(); i++) {
            if (memberID == customerData.get(i).getMemberNumber()) {
                if (customerData.get(i).payFee() == true) {
                    System.out.println("Fee is already Paid");
                } else {
                    customerData.get(i).setFeePaid();
                    return "Fee has been paid";
                }
            }
        }
        return null;
    }

    /**
     * Restocks product or subtracts from inventory
     *
     * @param productId barcode
     * @param amount amount to add to/take away from inventory
     * @return current stock
     */
    @Override
    public int restockProduct(int productId, int amount) {
        int stock = 0;
        for (int i = 0; i < inventory.size(); i++) {
            if (productId == inventory.get(i).getBarcode()) {

                stock = inventory.get(i).getStock();
                inventory.get(i).setStock(amount + stock);
                stock = inventory.get(i).getStock();
                if (inventory.get(i).getStock() == 0 || inventory.get(i).getStock() < 0) {
                    System.out.println("Last of item stock has been purchased. " + inventory.get(i).getName() + " has been removed from inventory");
                    inventory.remove(i);
                }
                if (stock > 0) {
                    return inventory.get(i).getStock();
                }
            }
        }
        return 0;
    }

    /**
     * Prints current inventory value
     *
     * @return inventory value
     */
    @Override
    public double inventoryValue() {
        double total = 0;
        for (int i = 0; i < inventory.size(); i++) {
            total = (inventory.get(i).getPrice() * inventory.get(i).getStock()) + total;
        }
        return total;
    }

    /**
     * validates if barcode is in system
     *
     * @param barcode barcode
     * @return true if in system
     */
    @Override
    public boolean validateBarcode(int barcode) {
        for (int i = 0; i < inventory.size(); i++) {
            if (barcode == inventory.get(i).getBarcode()) {
                return true;
            }
        }
        System.out.println("Please enter valid barcode");
        return false;
    }

    /**
     * validates if member is in system
     *
     * @param memNum memberID
     * @return true if member is found
     */
    @Override
    public boolean validateMember(int memNum) {
        for (int i = 0; i < customerData.size(); i++) {
            if (memNum == customerData.get(i).getMemberNumber()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Compares price of two products and prints which cost more/less
     *
     * @param o product 1
     * @param s product 2
     */
    public void compare(int o, int s) {

        Products a1 = inventory.get(0);
        Products a2 = inventory.get(0);
        for (int i = 0; i < inventory.size(); i++) {
            if (o == inventory.get(i).getBarcode()) {
                a1 = inventory.get(i);
            }
        }
        for (int i = 0; i < inventory.size(); i++) {
            if (s == inventory.get(i).getBarcode()) {
                a2 = inventory.get(i);
            }
        }
        int compare = a1.compareTo(a2);
        if (compare == 1) {
            System.out.println(a1.getName() + " is Cheaper than " + a2.getName());
        } else if (compare == -1) {
            System.out.println(a2.getName() + " is Cheaper than " + a1.getName());
        } else if (compare == 0) {
            System.out.println("They are the same Price");
        }
    }

    public void reciept(double subTotal, double total, int memberID, int cashierNum, double discount, String paymentMethod, double totalGiven) {
        FileOutputStream recieptWriter = null;
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM-dd-yyyy HH_mm_ss");
        DateTimeFormatter date = DateTimeFormatter.ofPattern("MM-dd-yyyy HH_mm_ss");
        LocalDateTime now = LocalDateTime.now();;
        if (discount == -5.0) {
            discount = 0;
        }

        String name = null;
        Double totalSpent = 0.0;
        String email = null;
        String cashierName = null;

        for (int i = 0; i < customerData.size(); i++) {
            if (customerData.get(i).getMemberNumber() == memberID) {
                name = customerData.get(i).getName();
                totalSpent = customerData.get(i).getTotalSpent();
                email = customerData.get(i).getEmail();
            }
        }
        for (int i = 0; i < employees.size(); i++) {
            if (employees.get(i).getUserKey() == cashierNum) {
                cashierName = employees.get(i).getName();
                double salesAmount = subTotal + employees.get(i).getTotalSales();
                employees.get(i).setTotalSales(salesAmount);
            }
        }
        try {
            recieptWriter = new FileOutputStream("./reciepts/" + dtf.format(now) + ".txt");
            PrintWriter recieptOut = new PrintWriter(recieptWriter);
            System.out.println("**********");

            System.out.println("****************");
            System.out.println(dtf.format(now));
            for (String s : reciept) {
                System.out.println("***************");

                System.out.println(s);
            }
            System.out.println("****************");
            System.out.println("Subtotal: " + (Math.round(subTotal * 100.0) / 100.0));
            System.out.println("Discount recieved: " + discount * 90 + "%");
            System.out.println("Order Total: " + total);
            System.out.println("Payment Method: " + paymentMethod);
            if (paymentMethod.equals("cash")) {
                System.out.println("Cash given: " + totalGiven);
                System.out.println("Change Owed: " + (totalGiven - total));
            }
            System.out.println("****************");

            System.out.println("Cashier Number: " + cashierNum);
            System.out.println("Cashier Name: " + cashierName);

            System.out.println("****************");

            System.out.println("Member ID: " + memberID);
            System.out.println("Member Name: " + name);
            System.out.println("Total Spent: " + totalSpent);
            System.out.println("Email: " + email);

            System.out.println("****************");

            recieptOut.println("****************");
                        recieptOut.println(dtf.format(now));
            for (String s : reciept) {
                recieptOut.println("***************");
                recieptOut.println(s);
            }
            recieptOut.println("****************");
            recieptOut.println("Subtotal: " + (Math.round(subTotal * 100.0) / 100.0));
            recieptOut.println("Discount recieved: " + discount * 90 + "%");
            recieptOut.println("Order Total: " + total);
            recieptOut.println("Payment Method: " + paymentMethod);
            if (paymentMethod.equals("cash")) {
                recieptOut.println("Cash given: " + totalGiven);
                recieptOut.println("Change Owed: " + (totalGiven - total));
            }
            recieptOut.println("****************");

            recieptOut.println("Cashier Number: " + cashierNum);
            recieptOut.println("Cashier Name: " + cashierName);

            recieptOut.println("****************");

            recieptOut.println("Member ID: " + memberID);
            recieptOut.println("Member Name: " + name);
            recieptOut.println("Total Spent: " + totalSpent);
            recieptOut.println("Email: " + email);

            recieptOut.println("****************");

            recieptOut.close();

        } catch (FileNotFoundException ex) {
            Logger.getLogger(Purchase.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                recieptWriter.close();
                for(int i = 0;i<reciept.size();i++){
                    reciept.remove(i);
                }

            } catch (IOException ex) {
                Logger.getLogger(Purchase.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void saveData() {
        FileOutputStream inventoryFS = null;
        FileOutputStream customerFS = null;
        try {
            String type = null;
            inventoryFS = new FileOutputStream("./productInventory.csv");
            PrintWriter inventoryOut = new PrintWriter(inventoryFS);
            inventoryOut.println("productID,type,title,author/artist,numInStock,price");
            for (Products s : inventory) {
                if (s instanceof Book) {
                    type = "book";
                } else if (s instanceof DVD) {
                    type = "dvd";
                } else if (s instanceof CD) {
                    type = "cd";
                }
                inventoryOut.println(s.getBarcode() + "," + type + "," + s.getName() + "," + s.getAuthor() + "," + s.getStock() + "," + s.getPrice());
            }

            customerFS = new FileOutputStream("./CustomerData.csv");
            PrintWriter customerOut = new PrintWriter(customerFS);
            customerOut.println("name,birthday,email,premium,feePaid,Member Number,lastFourCardNumber,totalSpent");
            for (Customers c : customerData) {
                customerOut.println(c.getName() + "," + c.getBirthday() + "," + c.getEmail()
                        + "," + (c instanceof PremiumMember) + "," + c.payFee() + "," + c.getMemberNumber() + "," + c.getLastFourCardNumber() + "," + c.getTotalSpent());
            }
            inventoryOut.close();
            customerOut.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Purchase.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                inventoryFS.close();
                customerFS.close();

            } catch (IOException ex) {
                Logger.getLogger(Purchase.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void getListOfCustomers() {
        for (Customers c : customerData) {
            System.out.println("Name: " + c.getName() + " Birthday: " + c.getBirthday() + " Member Number: " + c.getMemberNumber()
                    + " Is Premium? " + c.isPremium() + " Email: " + c.getEmail() + " Total Spent: " + c.getTotalSpent());
        }
    }
    
    public ArrayList<String> condensePurchaseLog(){
        ArrayList<String> purchases = new ArrayList<>();
        ArrayList<String> duplicateRemover=new ArrayList<>();
        int count = 0;
       for(Products p: dailyPurchaseLog){
           for(int i =0;i<dailyPurchaseLog.size();i++){
               if(p.getBarcode()==dailyPurchaseLog.get(i).getBarcode()){
                  count++;
           }
       }
           purchases.add(p.getName()+" was purcahsed "+count+" times today");
           count=0;
    }for(String s:purchases){
        if(!duplicateRemover.contains(s)){
            duplicateRemover.add(s);
        }
    }
       return duplicateRemover;
    }



}
