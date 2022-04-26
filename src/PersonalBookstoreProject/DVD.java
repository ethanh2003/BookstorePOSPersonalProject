/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package PersonalBookstoreProject;

/**
 * Subclass of products, creates DVD
 *
 * @author Ethan Herring
 */
public class DVD extends Products {

    private String name;
    private String author;
    private double price;
    private int stock;
    private String catagory;
    private int barcode;
        private int numPurcahsed;


    /**
     * Creates DVD data
     *
     * @param name - DVD Title
     * @param author - creator
     * @param price - item price
     * @param stock - amount in stock
     * @param catagory - Genre
     */
    public DVD(String name, String author, double price, int stock) {
        super(name, author, price, stock, Products.productID);
        this.catagory = catagory;
        super.incrimentBarcode();
    }

    public String getCatagory() {
        return catagory;
    }

    public void setCatagory(String catagory) {
        this.catagory = catagory;
    }
    public void setNumberPurchased(int i){
        numPurcahsed=i;
    }public int getNumberPurchased(){
        return numPurcahsed;
    }

}
