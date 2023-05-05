package edu.guilford;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Hello world!
 *
 */
public class MainFind {
    public static void main(String[] args) throws URISyntaxException, FileNotFoundException, UnsupportedEncodingException {
        Scanner scan = new Scanner(System.in);
        System.out.println("Enter the name of the file you want to search: ");
        String fileName = scan.next();
        File file = new File(MainFind.class.getResource("/" + fileName).toURI());
        //make scanner use UTF-8 encoding
        //the only weird thing was that the quotation marks didn't fit the format of the file when downloading the txt file, so I had to replace all the quotation marks with the correct ones
        //also had to replace all dashes with spaces
        Scanner scanFile = new Scanner(new InputStreamReader(new FileInputStream(file), "UTF-8"));

        //I used an ArrayList because I didn't know how many words there would be in the file and I need
        //to be able to get words quickly from the file, and ArrayList is the quickest
        //type of list to do that. Although I also have to 
        //add and remove words from the ArrayList, which is slower than a LinkedList, this is a 
        //small disadvantage compared to the advantage of being able to get words quickly from the file
        //Also, adding and removing words from the ArrayList is not as slow because we're dealing 
        //with a small amount of words (compared to millions of them). And users typically would have txt files
        //with at most a few thousand words.
        ArrayList<String> words = new ArrayList<String>();
        ArrayList<String> repeatingWords = new ArrayList<String>();
        ArrayList<SearchWord> searchWords = new ArrayList<SearchWord>();


        //I wanted to break my algorithm into steps rather combining it all
        //into one big algorithm. This way, I can test each step to make sure
        //it works and also make it easier to debug if something goes wrong.
        //it's also easier to read and understand

        //1) Get each word from the txt file, lowercase it and replace all ' and — with a comma or space'
        //This just makes it easier for the program to understand and prevent words from being
        //outputted like weird. E.g., "sara?s" would be outputted instead of "sara's"
        while (scanFile.hasNext()) {
            String word = scanFile.next();
            // make word lowercase and replace all ’ with ' and — with a comma and space
            word = word.toLowerCase();
            //word = word.replaceAll("’", "'");
            //word = word.replaceAll("—", ", ");
            // if word has punctuation marks, remove it. Also remove any empty strings
            // and words that have digits in them
            if (word.matches(".*\\p{Punct}.*") || word.equals("") || word.matches(".*\\d.*")) {
                word = word.replaceAll("\\p{Punct}", "");
                word = word.replaceAll("\\d", "");
                // else if word is empty, remove it
                if (word.equals("")) {
                    continue;
                }
            }
            // add word to ArrayList
            words.add(word);
        }

        // close scanFile
        scanFile.close();
        
        // print out the words ArrayList (just to double check on my end that 
        //everything is working)
        // for (int i = 0; i < words.size(); i++) {
        //     System.out.println(words.get(i));
        // }

        //2) get all repeating words from the ArrayList and insert them into a new
        // ArrayList. This is without the first occurrence of the word, so I need to keep that in mind.
        for (int i = 0; i < words.size(); i++) {
            String word = words.get(i);
            for (int j = i + 1; j < words.size(); j++) {
                if (word.equals(words.get(j))) {
                    repeatingWords.add(word);
                    break;
                }
            }
        }

        // 3) Remove repeating words from the ArrayList and count the number of times
        // something is removed. Instantiate a new object with the number of occurences and
        // the word and add it to the searchWords ArrayList
        for (int i = 0; i < repeatingWords.size(); i++) {
            String word = repeatingWords.get(i);
            // We start with one because when we transferred repeating words to the new
            // arraylist, we didn't count the first one
            int count = 1;
            for (int j = i + 1; j < repeatingWords.size(); j++) {
                if (word.equals(repeatingWords.get(j))) {
                    repeatingWords.remove(j);
                    count++;
                    j--;
                }
            }
            // we also add one here because when comparing if a word is repeating, we didn't
            // count the first one
            SearchWord searchWord = new SearchWord(word, count + 1);
            searchWords.add(searchWord);
        }
        // // print out the results to check if everything is working properly
        // for (int i = 0; i < repeatingWords.size(); i++) {
        // System.out.println(repeatingWords.get(i));
        // }

        // Print out searchWord arraylist
        // for (int i = 0; i < searchWords.size(); i++) {
        // System.out.println(searchWords.get(i));
        // }

        System.out.println("Enter the word you want to search for (no punctuation, just the word): ");
        // scan userWord as a string
        String userWord = scan.next();
        // i
        userWord = userWord.toLowerCase();

        //4) Search for word in searchWords and print out that object's toString.
        //If word is not found, print out "Not Found." If word is not found in searchWords, 
        //it is likely that the word is not a repeating word and can be found in the words arraylist.
        for (int i = 0; i < searchWords.size(); i++) {
            //boolean match is set to false, when word is found, it is set to true. If word is not found, it is still false and we print out "Not Found."
            boolean match = false;
            if (userWord.equals(searchWords.get(i).getWord())) {
                match = true;
                if (match) {
                    System.out.println(userWord + " found, with count of " + searchWords.get(i).getCount());
                    System.exit(0);
                }
                //if we cannot find the word in the searchWords arraylist, it is likely that the word is not a repeating word and can be found in the words arraylist
                //we then look through the words arraylist and see if the word is there. If it is, we print out that it is found with a count of 1 and also set match to true. 
            } else if (i == searchWords.size() - 1 && !match) {
                // look through words arraylist and see if the word is there
                for (int j = 0; j < words.size(); j++) {
                    if (userWord.equals(words.get(j))) {
                        match = true;
                        System.out.println(userWord + " found, with count of 1");
                        System.exit(0);
                    }
                }
            }

            if (match == false && i == searchWords.size() - 1) {
                System.out.println("Not Found.");
            }
        }

    }

}
