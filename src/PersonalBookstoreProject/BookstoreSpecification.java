/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package PersonalBookstoreProject;

/**
 * Interface for restock and inventory
 *
 * @author Ethan Herring
 */
public interface BookstoreSpecification {

    /**
     * restocks product
     *
     * @param productId barcode
     * @param amount amount adding/subtracting to/from stock
     * @return total stock
     */
    public int restockProduct(int productId, int amount);

    /**
     * calcuates total inventory value
     *
     * @return total value
     */
    public double inventoryValue();
}
