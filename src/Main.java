//import javax.sound.sampled.AudioInputStream;
//import javax.sound.sampled.AudioSystem;
//import javax.sound.sampled.Clip;
//import java.net.URL;
//Above are unused from attempt at getting sounds working
import java.io.File;
import java.io.FileNotFoundException;

import java.util.*;

public class Main {

    public static final String WordsFile = "words.txt";
    public static Scanner userInput = new Scanner(System.in);
    //bool for looping or ending the game
    public static boolean playing = true;
    //Array for holding the list of words for further rounds of the game
    public static List<String> hangmanWords = new ArrayList<>();
    //Function to repopulate/reshuffle the hangmanWords array
    public static void populateWords () throws FileNotFoundException{
        hangmanWords = retrieveWords();
        //Randomize selection of words each time
        Collections.shuffle(hangmanWords);
    }

    public static void main(String[] args) {
        //Grab the words from the file
        try{
            populateWords();
        }
        catch (FileNotFoundException fileNotFound) {
            fileNotFound.printStackTrace(System.out);
        } catch (Exception e) {
            e.printStackTrace(System.out);
         }
        //playSound("bg_music.wav");    Couldn't get these working
        printTitle();
        while(playing) {

            System.out.println("");
            System.out.println("Welcome to Hangman!");
            //Select difficulty
            int diff = getDifficulty();

            if (hangmanWords.size() == 0)
                try{
                    populateWords();
                }
                catch (FileNotFoundException fileNotFound) {
                    fileNotFound.printStackTrace(System.out);
                } catch (Exception e) {
                    e.printStackTrace(System.out);
                }
            String nextWord = hangmanWords.get(0);
            hangmanWords.remove(nextWord);
            //Left this diagnostic piece here to demo how list of strings is shuffled, used, and repopulated in random order
            System.out.println(hangmanWords);
            PlayHangman.playHangman(diff, nextWord);

            System.out.println("Play again? Enter X to stop or anything else to continue.");
            String userChoice = userInput.next().toUpperCase();
            if (userChoice.equals("X")){
                System.out.println("Goodbye");
                System.exit(1);
            }
        }
    }

    public static int getDifficulty() throws InputMismatchException {
        System.out.println("Enter 1 for easy, 2 for normal, 3 for hard.");
        int difficulty = 0;
        int numChoice = 0;
        boolean validNum = false;
        try {
            while (validNum == false) {
                numChoice = userInput.nextInt();
                if (numChoice == 1) {
                    difficulty = 1;
                    System.out.println("You chose EASY MODE...");
                    validNum = true;
                } else if (numChoice == 2) {
                    difficulty = 2;
                    System.out.println("You chose NORMAL MODE.");
                    validNum = true;
                } else if (numChoice == 3) {
                    difficulty = 3;
                    System.out.println("You chose HARD MODE!");
                    validNum = true;
                } else {
                    System.out.println("Invalid entry. 1-3 only.");
                }
            }
        }
        catch (InputMismatchException ime){
            ime.printStackTrace(System.out);
            System.out.println("Unexpected error, proceeding in NORMAL MODE.");
            difficulty = 2;
        }
        return difficulty;
    }

    public static List<String> retrieveWords() throws FileNotFoundException {
            Scanner wordInput = new Scanner(new File(WordsFile));
            List<String> wordList = new ArrayList<String>();
            while (wordInput.hasNext()) {
                wordList.add(wordInput.next().toUpperCase());
            }
            return wordList;
    }

    /* Didn't get the sound working, here's my abandoned attempt

    public static synchronized void playSound(final String url) {
        new Thread(new Runnable() {
            public void run() {
                try {
                    Clip clip = AudioSystem.getClip();
                    URL url = this.getClass().getClassLoader().getResource("\\bg_music.wav");
                    AudioInputStream audioIn = AudioSystem.getAudioInputStream(url);
                    clip.open(audioIn);
                    clip.start();
                } catch (Exception e) {
                    System.err.println(e.getMessage());
                }
            }
        }).start();
    }
    */

    public static void printTitle(){
        System.out.println("   |/|           __ __   ____  ____    ____  ___ ___   ____  ____");
        System.out.println("   | |          |  |  | /    ||    \\  /    ||   |   | /    ||    \\");
        System.out.println("   |/|          |  |  ||  o  ||  _  ||   __|| _   _ ||  o  ||  _  |");
        System.out.println("   | |          |  _  ||     ||  |  ||  |  ||  \\_/  ||     ||  |  |");
        System.out.println("   |/|          |  |  ||  _  ||  |  ||  |_ ||   |   ||  _  ||  |  |");
        System.out.println("  (___)         |  |  ||  |  ||  |  ||     ||   |   ||  |  ||  |  |");
        System.out.println("  (___)         |__|__||__|__||__|__||___,_||___|___||__|__||__|__|");
        System.out.println("  (___)");
        System.out.println("  (___)");
        System.out.println("  (___)                 ____       ||||||");
        System.out.println("  // \\\\                /    \\     |o o  |");
        System.out.println(" //   \\\\               | !!  >    | <   |");
        System.out.println("||     ||              \\____/     | /\\  |");
        System.out.println("||     ||                          \\___/");
        System.out.println("||     ||                          __|_|__");
        System.out.println(" \\\\___//                          /       \\");
        System.out.println("  -----                          | |     | |");
    }
}