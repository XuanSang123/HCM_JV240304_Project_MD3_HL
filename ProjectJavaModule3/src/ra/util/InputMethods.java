package ra.util;

import java.util.Scanner;

public class InputMethods {
    private static final String ERROR_MESSAGE = "Invalid input! Please enter again!";
    private static final String EMPTY_MESSAGE = "Input cannot be empty! Please enter again!";


    public static String getString(String s){
        while (true){
            String input = getInput();
            if (input.isEmpty()){
                System.out.println(EMPTY_MESSAGE);
            } else {
                return input;
            }
        }
    }
    public static int getInteger(){
        while (true){
            try {
                return Integer.parseInt(getString("Enter your name: "));
            } catch (NumberFormatException e){
                System.out.println(ERROR_MESSAGE);
            }
        }
    }
    public static long getLong(){
        while (true){
            try {
                return Long.parseLong(getString("Enter your name: "));
            } catch (NumberFormatException e){
                System.out.println(ERROR_MESSAGE);
            }
        }
    }
    public static float getFloat(){
        while (true){
            try {
                return Float.parseFloat(getString("Enter your name: "));
            } catch (NumberFormatException e){
                System.out.println(ERROR_MESSAGE);
            }
        }
    }
    public static double getDouble(){
        while (true){
            try {
                return Double.parseDouble(getString("Enter your name: "));
            } catch (NumberFormatException e){
                System.out.println(ERROR_MESSAGE);
            }
        }
    }
    public static boolean getBoolean(){
        while (true){
            String input = getString("Enter your name: ");
            if (input.equalsIgnoreCase("true")){
                return true;
            } else if (input.equalsIgnoreCase("false")){
                return false;
            } else {
                System.out.println(ERROR_MESSAGE);
            }
        }
    }
    public static byte getByte(){
        while (true){
            try {
                return Byte.parseByte(getString("Enter your name: "));
            } catch (NumberFormatException e){
                System.out.println(ERROR_MESSAGE);
            }
        }
    }
    private static String getInput(){
        Scanner input = new Scanner(System.in);
        return input.nextLine();
    }
}
