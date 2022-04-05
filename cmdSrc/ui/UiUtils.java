package ui;

import restaurant.Database;
import org.json.JSONObject;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import java.io.Console;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

public final class UiUtils {
    private static final Scanner scanner = new Scanner(System.in);

    public static boolean inputB(String input) {
        if(input.equalsIgnoreCase("b")) {
            System.out.println("Returning to previous screen..");
            return true;
        }
        return false;
    }

    static void checkQ(String input) {
        if(input.equalsIgnoreCase("q")) {
            System.out.println("Shutting down system...");
            System.exit(0);
        }
    }

    static String getNewPassword() {
        String password = getPassword("Enter ");
        checkQ(password);
        if(inputB(password)) {
            return "false";
        }
        password = checkPasswordLength(password);
        String repeatPass = getPassword("Repeat ");
        while(!Objects.equals(repeatPass, password)) {
            password = getPassword("Please enter the same password twice.\n");
            checkQ(password);
            if(inputB(password)) {
                return "false";
            }
            password = checkPasswordLength(password);
            repeatPass = getPassword("Repeat ");
        }
        return password;
    }

    static String getPassword(String repeat) {
        String password;
        Console console = System.console();
        if(console != null) {
            password = new String(console.readPassword(repeat + "Password: "));
        } else {
            System.out.println(repeat + "Password: ");
            password = scanner.nextLine();
        }
        return password;
    }

    static String checkPasswordLength(String password) {
        while(password.length() < 8) {
            System.out.println("Password must be at least 8 characters, please try again");
            password = scanner.nextLine();
            if(inputB(password)) {
                return "false";
            }
        }
        return password;
    }

    static int getInput(int min, int max) {
        String choice = scanner.nextLine();
        if(UiUtils.inputB(choice)) return -1;
        while(!isValid(choice, min, max)) {
            System.out.println("Please enter a valid number.");
            choice = scanner.nextLine();
        }
        return Integer.parseInt(choice);
    }

    public static boolean isValid(String choice, int min, int max) {
        try {
            int numChoice = Integer.parseInt(choice);
            if(max != -1 && min != -1 && (numChoice > max || numChoice < min)) {
                return false;
            }
        } catch(NumberFormatException e) {
            return false;
        }
        return true;
    }

    public static String getEmail() {
        System.out.println("Enter the email address you want to register:");
        String email = scanner.nextLine();
        UiUtils.checkQ(email);
        if(UiUtils.inputB(email)) {
            return "false";
        }

        while(!isValidEmailAddress(email)) {
            System.out.println("Please enter a valid email address");
            email = scanner.nextLine();
            UiUtils.checkQ(email);
            if(UiUtils.inputB(email)) {
                return "false";
            }
        }

        JSONObject existingUser = Database.readFromUserTable(email, null);
        //check for duplicate - can't register if so
        if(existingUser.has("email") && Objects.equals(existingUser.getString("email"), email)) {
            System.out.println("Sorry, that email has already been used to register an account. Please use a different one or login if this is your account.");
            email = getEmail();
        }
        return email;
    }

    private static boolean isValidEmailAddress(String email) {
        boolean result = true;
        try {
            InternetAddress emailAddr = new InternetAddress(email);
            emailAddr.validate();
        } catch (AddressException ex) {
            result = false;
        }
        return result;
    }

    public static String getInputChoice(ArrayList<String> choices) {
        String input = scanner.nextLine();
        while(!choices.contains(input.toLowerCase())) {
            System.out.println("Please enter a valid option.");
            input = scanner.nextLine();
        }
        return input.toLowerCase();
    }

}