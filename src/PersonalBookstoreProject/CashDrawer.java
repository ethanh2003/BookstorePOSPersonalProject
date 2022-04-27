/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package PersonalBookstoreProject;

/**
 * Interface for cashRegisterClass
 *
 * @author Ethan Herring
 */
public interface CashDrawer {

    /**
     * starting till total
     *
     * @param amount starting total
     */
    public void startingDrawerTotal(double amount);

    /**
     * runs end of day procedures
     *
     * @param restart allows you to reset counts
     */
    public void endOfDayTotal(int restart,int userKey);

    /**
     * Performs paid in procedure
     *
     * @param amount amount to put in till
     * @param reason reason for instert
     * @param managerKey manager key to track who did it
     */
    public void paidIn(double amount, String reason, int managerKey);

    /**
     * performs paid out
     *
     * @param amount amount coming out of till
     * @param reason reason for removal
     * @param managerKey key to track who did it
     */
    public void paidOut(double amount, String reason, int managerKey);
}
