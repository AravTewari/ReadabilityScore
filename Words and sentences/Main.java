package readability;

import java.io.*;
import java.util.Scanner;

public class Main
{
    public static void main(String[] args) throws FileNotFoundException
    {
        //File file = new File(args[0]);
        Scanner sc = new Scanner(System.in);
        String text = sc.nextLine();

        char[] words = text.toCharArray();

        String[] sentences = text.split("[!.?]");
        String[] wordss = text.split("[\\pZ+\\s+]");
        
        double avg = (double) wordss.length / sentences.length;

        System.out.println(avg <= 10 ? "EASY" : "HARD");


    }
}
