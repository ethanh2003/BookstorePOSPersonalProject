/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package PersonalBookstoreProject;

/**
 * Validates memeber numbers and barcodes
 *
 * @author Ethan Herring
 */
public interface ValidationCheck {

    /**
     * Checks to see if barcode is in system
     *
     * @param barcode barcode
     * @return true if in system false otherwise
     */
    public boolean validateBarcode(int barcode);

    /**
     * checks to see if member is in system
     *
     * @param memNum memberID
     * @return true if in system false otherwise
     */
    public boolean validateMember(int memNum);
}
