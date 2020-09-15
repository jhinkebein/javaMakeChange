package makechange;

import java.text.MessageFormat;
import java.util.Scanner;
import java.util.Random;

/**
 *
 * @author Jacob Hinkebein
 */
public class MakeChange {

    static Scanner sc = new Scanner(System.in);
    
    public static void main(String[] args) {
        int cents;
        boolean halfDollarCheck = false;
        String choice, prompt = "What value would you like change for? ($0.01-$5.00, or 0=quit): ";
        
        
        System.out.println("Welcome to the Make Change Calculator!");
        System.out.println("");
        
        System.out.println("Do you want to recieve half-dollars? (Y/N): ");
        choice = sc.next();
        if (choice.toUpperCase().startsWith("Y")) {
            halfDollarCheck = true;
        }
        cents = getCents(prompt);
        while(cents != 0){
            Random rand = new Random();
            int coinMethod = rand.nextInt(2);
            if(coinMethod == 0){
                makeChangeAddMethod(cents, halfDollarCheck);
            }
            else if (coinMethod == 1) {
                makeChangeDivMethod(cents, halfDollarCheck);
            }
            cents = getCents(prompt);
        }
        System.out.println("Thanks for using the Make Change Calculator!");
    }
    
    public static int getCents(String prompt){
        int c;
        double change = 0;
        do {
            try{
                System.out.println(prompt);
                change = sc.nextDouble();
                if (change < 0) {
                    System.out.println("Please enter a non-negative value.");
                }
                if (change > 5.00) {
                    System.out.println("Please enter a value less than or equal to 5.00.");
                    change = -1;
                    sc.nextLine();
                }
            }
            catch (Exception e){
                System.out.println("Illegal input: non-numeric.");
                change = -1;
                sc.nextLine();
            }
        } while (change < 0);
        c = (int)(change * 100);
        return c;
    }
    public static void makeChangeAddMethod(int cents, boolean halfDollarCheck){
        int h = 0, q = 0, d = 0, n = 0, t = 0, p; //no need to assign a value to p
        int[] coins = {q, d, n}; 
        int[] coinValues = {25, 10, 5}; 
        String out = MessageFormat.format("By the additive method, for {0} cent(s), I give: ", cents);
        if (halfDollarCheck) {
            while((t+50) <= cents){
                h++; //h += 1;
                t += 50;
            }
        }
        for (int i = 0; i < 3; i++) { 
            while((coinValues[i] + t) <= cents){ // array instead of typing the same line a few times 
                coins[i]+= coins[i] + 1;
                t += coinValues[i];
            }
        }
        p = cents - t; 
        
        if (halfDollarCheck) {
            out = out.concat(MessageFormat.format("{0} half-dollars, ", h));
        }
        out = out.concat(MessageFormat.format("{0} quarters, {1} dimes, {2} nickels, {3} pennies", coins[0], coins[1], coins[2], p));
        System.out.println(out);
    }
         

    public static void makeChangeDivMethod (int cents, boolean halfDollarCheck){
        int h = 0, q = 0, d = 0 , n = 0, p, r = cents;
        int[] coins = {q, d, n};
        int[] coinValues = {25, 10, 5}; 
        String out = MessageFormat.format("By the division method method, for {0} cent(s), I give: ", cents);
        if (halfDollarCheck) {
            h = r / 50;
            r -= (h * 50);
        }
        for (int i = 0; i < coins.length; i++) {
            coins[i] = r / coinValues[i];
            r -= (coins[i] * coinValues [i]);
        }
        p = r;
        
         if (halfDollarCheck) {
            out = out.concat(MessageFormat.format("{0} half-dollars, ", h));
        }
        out = out.concat(MessageFormat.format("{0} quarters, {1} dimes, {2} nickels, {3} pennies", coins[0], coins[1], coins[2], p));
        System.out.println(out); 
    }
}