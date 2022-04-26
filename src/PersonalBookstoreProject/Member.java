/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package PersonalBookstoreProject;

/**
 * base free member class,Subclass of customer
 *
 * @author Ethan Herring
 */
public class Member extends Customers {

    /**
     * creates regular member
     *
     * @param name customer name
     * @param birthday customer birthday
     * @param email customer email
     * @param totalSpent total spent over time
     * @param memberNumber autogenerated member number
     */
    public Member(String name, String birthday, String email,
            double totalSpent, int memberNumber) {
        super(name, birthday, email, totalSpent, memberNumber);
    }

    /**
     * returns discount only if premium
     *
     * @return discount
     */
    @Override
    public double rewardsDiscount() {
        if (premium) {
            System.out.println("Thanks for being a premium member!");

            if (totalSpent <= 100) {
                return .05;
            } else if (totalSpent >= 100 && totalSpent <= 200) {
                return .15;
            } else if (totalSpent > 200) {
                return .20;
            }
        }
        return 0;
    }
}
