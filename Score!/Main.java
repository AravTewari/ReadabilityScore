package readability;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class Main
{
    public static void main(String[] args) throws IOException
    {
        File file = new File(args[0]);
        Scanner sc = new Scanner(file);

        String text = "";
        int chars = 0;
        double rawScore;
        int roundedScore;
        String age = null;

        try
        {
            text = new String (Files.readAllBytes(Paths.get(String.valueOf(file))));
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        String[] sentences = text.split("[!.?]");
        String[] words = text.split("[\\pZ+\\s+]");
        for (int i = 0; i < words.length; i++)
        {
            chars += words[i].length();
        }


        rawScore = 4.71 * ((double)chars/words.length) + 0.5 * ((double)words.length/sentences.length) - 21.43;
        roundedScore = (int) Math.ceil(rawScore);
        switch (roundedScore)
        {
            case 1:
                age = "5-6";
                break;
            case 2:
                age = "6-7";
                break;
            case 3:
                age = "7-9";
                break;
            case 4:
                age = "9-10";
                break;
            case 5:
                age = "10-11";
                break;
            case 6:
                age = "11-12";
                break;
            case 7:
                age = "12-13";
                break;
            case 8:
                age = "13-14";
                break;
            case 9:
                age = "14-15";
                break;
            case 10:
                age = "15-16";
                break;
            case 11:
                age = "16-17";
                break;
            case 12:
                age = "17-18";
                break;
            case 13:
                age = "18-24";
                break;
            case 14:
                age = "24+";
                break;
        }

        System.out.println("The text is: " + text);
        System.out.println("Words: " + words.length);
        System.out.println("Sentences: " + sentences.length);
        System.out.println("Characters: " + chars);
        System.out.println("The score is: " + rawScore);
        System.out.println("This text should be understood by " + age + " year olds.");

        sc.close();

    }
}
