package readability;

import java.util.Scanner;

public class Main 
{
    private static final int HARD_LENGTH = 100;
    private static String text;
    public static void main(String[] args) 
    {
        userInput();
        System.out.println(checkHard() ? "HARD" : "EASY");
    }

    public static void userInput()
    {
        Scanner scanner = new Scanner(System.in);
        text = scanner.nextLine();
    }

    public static boolean checkHard()
    {
        return text.length() > HARD_LENGTH;
    }
}
