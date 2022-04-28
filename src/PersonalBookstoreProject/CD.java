/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package PersonalBookstoreProject;

/**
 * Subclass of products, creates CD
 *
 * @author Ethan Herring
 */
public class CD extends Products {

    private String name;
    private String author;
    private double price;
    private int stock;
    private int barcode;
        private int numPurcahsed;


    /**
     * creates CD data
     *
     * @param name cd name
     * @param author artist
     * @param price price of item
     * @param stock amount in stock
     */
    public CD(String name, String author, double price, int stock) {
        super(name, author, price, stock, Products.productID);
        super.incrimentBarcode();

    }

}
