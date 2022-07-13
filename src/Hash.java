//Isaiah Herr (herrx080, 5333708) and See Long Thao (thao0478@umn.edu, 5360172) Project 3

import java.util.*;
import java.io.*;

public class Hash {
    LinkedList[] Mainarray;
    int size;
    String[] alltoken;

    public Hash() { //Constructor with default values
        this.Mainarray = new LinkedList[129];
        this.size = 129;
    }

    public Hash(int size) {
        this.Mainarray = new LinkedList[size];
        this.size = size;
    }

    public void createHash() {
        System.out.println("Please enter in the name of your file (example: that_bad.txt)");
        Scanner part1 = new Scanner(System.in);
        String filename = part1.nextLine();


        File file = new File(filename);

        Scanner readFile = null;
        Scanner readFile2 = null;
        int count = 0;
        int count2 = 0;


        try { //Our try and catch block to see if we receive an error or not.
            readFile = new Scanner(file);
            readFile2 = new Scanner(file);
        } catch (FileNotFoundException e) {
            System.out.println(file + " has not been found");
        }

        System.out.println();

        while (readFile.hasNext()) {
            String s = readFile.next();
            count++;
        }
        alltoken = new String[count]; //An array containing all of our tokens

        while (readFile2.hasNext()) { //Adds all tokens into an array
            String s = readFile2.next();
            alltoken[count2] = s;
            count2++;
        }

        for (int x = 0; x < alltoken.length; x++) {
            int index = Math.floorMod(alltoken[x].hashCode(), Mainarray.length);
            LinkedList<String> chain = new LinkedList<String>(); //Linked list containing tokens of the same index

            if (Mainarray[index] == null) { //These next lines of code will attempt to implement the chaining method.
                for (int y = 0; y < alltoken.length; y++) { //Iterates through each token on the text file
                    int index2 = Math.floorMod(alltoken[y].hashCode(), Mainarray.length);

                    if (index2 == index && !chain.contains(alltoken[y])) { //Checks to see if indexes of other tokens are the same and if the element array is empty. If they are, we add a token to the linkedlist element.
                        chain.add(alltoken[y]);                             //Also doesn't add the token if it already exists in the linked list.
                    }
                }
                Mainarray[index] = chain;
            }
        }

    }

    public void display() {
        for (int z = 0; z < Mainarray.length; z++) {
            if (Mainarray[z] == null) {
                System.out.println("Index " + (z + 1) + ": " + Mainarray[z] + ", Number of keys: " + 0);
            } else {
                System.out.println("Index " + (z + 1) + ": " + Mainarray[z] + ", Number of keys: " + Mainarray[z].size());
            }
        }
    }

    public int getKey(String token) { //Will return a numeric value representation of a word
        int charvalue = 0;
        for (int x = 0; x < token.length(); x++) {
            charvalue += token.charAt(x) * 10; //Goes through each character in a list, multiplies value by 10, and then adds it to charvalue.
        }
        return charvalue;
    }

    public int getKey2(String token) { //Will return a numeric value representation of a word
        int charvalue = 0;
        int count = 0;
        for (int x = 0; x < token.length(); x++) {
            charvalue += token.charAt(x) * 31 * x-count; //Uses this mathematical equation for each character in a token
            count++;
        }
        return charvalue;
    }

    public int hashfunc1(int key) { //This will be our first hash function in our double hash implementation.
        int hashval = key % Mainarray.length;
        if (hashval < 0) {
            hashval += Mainarray.length;
        }
        return hashval;
    }

    public int hashfunc2(int key) { //Will be our step function in our hash implementation. We want to use only prime numbers to create more "uniqueness" for each token.
        int hashval = key % Mainarray.length;
        if (hashval < 0) { //If hashval becomes negative, we continually add the length of the array until the number becomes positive
            hashval += Mainarray.length;
        }
        return 7 - hashval % 7; //This is what will differentiate our step function in our double hash.
    }


    public void distribute() { //This will iterate through the hash table and try to distribute tokens evenly across the hash table with each index containing a maximum of 3 tokens.
        System.out.println("Please enter in the filepath for your txt file");
        Scanner part1 = new Scanner(System.in);
        String filename = part1.nextLine();


        File file = new File(filename);

        this.Mainarray = new LinkedList[size];

        Scanner readFile = null;
        Scanner readFile2 = null;
        int count = 0;
        int count2 = 0;


        try { //Our try and catch block to see if we receieve an error or not.
            readFile = new Scanner(file);
            readFile2 = new Scanner(file);
        } catch (FileNotFoundException e) {
            System.out.println(file + " has not been found");
        }

        System.out.println();

        while (readFile.hasNext()) {
            String s = readFile.next();
            //System.out.println("Token found: " + s.hashCode());
            count++;
        }
        alltoken = new String[count]; //An array containing all of the tokens found in the txt file

        while (readFile2.hasNext()) { //Adds all tokens into an array
            String s = readFile2.next();
            alltoken[count2] = s;
            count2++;
        }

        for (int x = 0; x < alltoken.length; x++) {
            LinkedList<String> chain = new LinkedList<String>(); //Linked list that will chain tokens that consist of the index. Used for collisions.
            int hashval = hashfunc1(getKey(alltoken[x]));
            int step = hashfunc2(getKey(alltoken[x]));

            hashval += (step * 3)+9;
            hashval = hashval % Mainarray.length;

            if (Mainarray[hashval] == null) {
                for (int y = 0; y < alltoken.length; y++) { //Iterates through Mainarray a second time and compares hashvals to see if they are the same or not.
                    int hashval2 = hashfunc1(getKey(alltoken[y])); //This will chain the values into a linkedlist element if the hashvals are the same.
                    int step2 = hashfunc2(getKey(alltoken[y]));
                    hashval2 += (step2 * 3)+9;
                    hashval2 = hashval2 % Mainarray.length;

                    if (hashval2 == hashval && !chain.contains(alltoken[y])) { //Adds token to the linkedlist if not already in the linkedlist
                        chain.add(alltoken[y]); //Add the token into the chain
                    }
                }
                Mainarray[hashval] = chain; //Add the linked list into the index that hashval calculated.
            }
        }
    }

    public void specificHash() {

        System.out.println("Please enter in the filepath for your txt file");
        Scanner part1 = new Scanner(System.in);
        String filename = part1.nextLine();


        File file = new File(filename);

        Mainarray = new LinkedList[size]; //We create an array of empty linked lists here
        Scanner readFile = null;
        Scanner readFile2 = null;
        int count = 0;
        int count2 = 0;


        try { //Our try and catch block to see if we receieve an error or not.
            readFile = new Scanner(file);
            readFile2 = new Scanner(file);
        } catch (FileNotFoundException e) {
            System.out.println(file + " has not been found");
        }
        System.out.println();

        while (readFile.hasNext()) {
            String s = readFile.next();
            count++;
        }
        alltoken = new String[count]; //An array containing all tokens in the txt file

        while (readFile2.hasNext()) { //Adds all tokens into an array
            String s = readFile2.next();
            alltoken[count2] = s;
            count2++;
        }

        for (int x = 0; x < alltoken.length; x++) {
            int hashval = getKey2(alltoken[x]); //Receive the numeric value for tokens
            LinkedList<String> chain = new LinkedList<String>(); //Linked list that will chain tokens that consist of the index. Used for collisions.
            int step = hashfunc2(getKey2(alltoken[x]));
            hashval += step*31+233*x; //This is our specific hash function
            hashval = hashval % Mainarray.length;

            if (Mainarray[hashval] == null){
                for (int y = 0; y < alltoken.length; y++) { //Iterates through Mainarray a second time and compares hashvals to see if they are the same or not.
                    int hashval2 = getKey2(alltoken[y]);    ////This will chain the values into a linkedlist element if the hashvals are the same.
                    int step2 = hashfunc2(getKey2(alltoken[y]));
                    hashval2 += step2*31+233*y; //This is our specific hash function
                    hashval2 = hashval2 % Mainarray.length;

                    if (hashval2 == hashval && !chain.contains(alltoken[y])) { //Adds token to the linkedlist if not already in the linkedlist
                        chain.add(alltoken[y]);
                    }
                }
                Mainarray[hashval] = chain; //Add the linked list into the index that hashval calculated.
            }
        }
    }
}