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
        int syls = 0;
        int polySyls = 0;

        try 
	{
            text = new String(Files.readAllBytes(Paths.get(String.valueOf(file))));
        } 
	catch (IOException e) 
	{
            e.printStackTrace();
        }
        sc.close();

        int[] age = {6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 24, 25};

        String[] sentences = text.split("[!.?]"); /** Separates input text into sentences by spliting with punctuation mark regexps **/
        String[] words = text.split("[\\pZ+\\s+]"); /** Separates input text into words by spliting with any whitespace regexps **/

        int[] syllPerWordCnt = new int[words.length];
        for (int i=0; i<words.length; i++)  /** Counts chars, syllables, and polysyllables **/
        {
            chars += words[i].length(); /**  Char counter increases by adding the length of each word in word array **/

            syllPerWordCnt[i] = 0; /** Sets syllable count of current word to zero **/

            /** Syllable counter only increases as number of vowels increases
             * and decreases if there are double vowels or 'e' is the last letter of the word **/
            char[] ch = words[i].toLowerCase().toCharArray();
            int len = ch.length;
            boolean isPreviousCharVowel = false;
            for (int j=0; j<len; j++)
            {
                // Do not count double-vowels
                if (isPreviousCharVowel)
                {
                    if (ch[j] != 'a' && ch[j] != 'e' && ch[j] != 'i' && ch[j] != 'o' && ch[j] != 'u' && ch[j] != 'y')
                    {
                        isPreviousCharVowel = false;
                    }
                    continue;
                }
                else if (ch[j] == 'a' || ch[j] == 'e' || ch[j] == 'i' || ch[j] == 'o' || ch[j] == 'u' || ch[j] == 'y')
                {
                    syllPerWordCnt[i]++;
                    isPreviousCharVowel = true;
                }
            }
            // If the last letter in the word is 'e' do not count it as a vowel
            if (ch[len - 1] == 'e')
            {
                syllPerWordCnt[i]--;
            }
            /** If there are now syllables in the word,
             * then it is automatically a one-syllable word. **/
            if (syllPerWordCnt[i] == 0)
            {
                syllPerWordCnt[i] = 1;
            }
            /** A word is a polysyllable if it has more than 2 syllables **/
            if(syllPerWordCnt[i] > 2)
            {
                polySyls++;
            }
            /** Syllable per word counter is added to total number of syllable counter **/
            syls += syllPerWordCnt[i];
        }

        System.out.println("The text is: " + text
                            + " \nWords: " + words.length
                            + "\nSentences: " + sentences.length
                            + "\nCharacters: " + chars
                            + "\nSyllables " + syls
                            + "\nPolysyllables " + polySyls
                            + "\nEnter the score you want to calculate (ARI, FK, SMOG, CL, all): ");

        Scanner s = new Scanner (System.in);
        String test = s.next();
        s.close();

        switch (test)
        {
            case "ARI":
                ariTest(words.length, sentences.length, chars, age);
                break;
            case "FK":
                fkTest(words.length, sentences.length, syls, age);
                break;
            case "SMOG":
                smogTest(polySyls, sentences.length, age);
                break;
            case "CL":
                clTest(chars, words.length, sentences.length, age);
                break;
            case "all":
                ariTest(words.length, sentences.length, chars, age);
                fkTest(words.length, sentences.length, syls, age);
                smogTest(polySyls, sentences.length, age);
                clTest(chars, words.length, sentences.length, age);
                 break;
            default:
                break;
        }
    }

    private static void ariTest (int words, int sentences, int chars, int[] age)
    {
        double rawScore;
        int roundedScore;

        rawScore = 4.71 * ((double) chars / words) + 0.5 * ((double) words / sentences) - 21.43;
        roundedScore = (int) Math.floor(rawScore);

        System.out.println("Automated Readability Index: " + rawScore + " (about " + age[roundedScore] + " year olds).");
    }

    private static void fkTest(int words, int sentences, int syllables, int[] age)
    {
        double rawScore;
        int roundedScore;

        rawScore = 0.39 * words/sentences + 11.8 * syllables/words - 15.59;
        roundedScore = (int) Math.floor(rawScore);

        System.out.println("Flesch–Kincaid readability tests: " + rawScore + " (about " + age[roundedScore] + " year olds).");
    }

    private static void smogTest (int polySyls, int sentences, int[] age)
    {
        double rawScore;
        int roundedScore;

        rawScore = 1.043 * Math.sqrt(polySyls * (30/sentences)) + 3.1291;
        roundedScore = (int) Math.ceil(rawScore);

        System.out.println("Simple Measure of Gobbledygook: " + rawScore + " (about " + age[roundedScore-1] + " year olds).");
    }

    private static void clTest (int chars, int words, int sentences, int[] age)
    {
        double rawScore;
        int roundedScore;

        double L =  ((double) chars / (double) words) * 100;
        double S = ((double) sentences / (double) words) * 100;

        rawScore = 0.0588 * L - 0.296 * S - 15.8;
        roundedScore = (int) Math.ceil(rawScore);

        System.out.println("Coleman–Liau index: " + rawScore + " (about " + age[roundedScore-1] + " year olds).");
    }
}
