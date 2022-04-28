/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package PersonalBookstoreProject;

/**
 * Subclass of products, creates book
 *
 * @author Ethan Herring
 */
public class Book extends Products {

    private String name;
    private String author;
    private double price;
    private int stock;
    private int barcode;
    private int numPurcahsed;

    /**
     * Creates book data
     *
     * @param name Book Title
     * @param author Author Name
     * @param price Price
     * @param stock Amount in stock
     */
    public Book(String name, String author, double price, int stock) {
        super(name, author, price, stock, Products.productID);
        super.incrimentBarcode();
    }
    

}
