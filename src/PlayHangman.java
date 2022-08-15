import java.util.ArrayList;
import java.util.Scanner;

public class PlayHangman {
    //Error counter
    public static int errorCount = 0;
    //Error limit
    public static int errorMax;
    //Difficulty level
    public static int difficulty;
    //The word as a string
    private static String word;
    //The displayed char array of guessed letters and underscores
    private static char[] wordLetters;
    //Letters already guessed
    private static ArrayList<String> lettersGuessed = new ArrayList<>();
    //Scanner for user entries
    public static Scanner userInput = new Scanner(System.in);

    //Bool to check if word is guessed
    public static boolean wordFound() {
        return word.contentEquals(new String(wordLetters));
    }
    //String builder to output the status of the game
    private static String wordStatus() {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < wordLetters.length; i++){
            sb.append(wordLetters[i]);
            //spacing for clarity
            sb.append(" ");
        }

        return sb.toString();
    }

    public static void playHangman (int diff, String wordParam){
        difficulty = diff;
        word = wordParam.toUpperCase();
        errorCount = 0;
        lettersGuessed.clear();

        //Assign error limit based on difficulty chosen
        if(diff == 3){
            errorMax = 3;
        }
        else if(diff == 2){
            errorMax = 4;
        }
        else if(diff == 1){
            errorMax = 6;
        }

        //assign word characters to wordLetters char array
        wordLetters = new char[word.length()];
        for (int i = 0; i < wordLetters.length; i++){
            wordLetters[i] = '_';
        }

        while(errorCount<errorMax) {
            displayHangman();
            System.out.println("WORD:  " + wordStatus());
            System.out.println("CHANCES: " + errorCount + "/" + errorMax);
            System.out.println("Enter a letter: ");
            String userGuess = userInput.next().toUpperCase();
            //If greater than one character, only take leading character
            if (userGuess.length() > 1){
                userGuess = userGuess.substring(0,1);
            }
            newGuess(userGuess);
            lettersGuessed.add(userGuess);

            if(wordFound()) {
                System.out.println("WORD:  " + wordStatus() + " !");
                System.out.println("Congratulations, you won!");
                break;
            }
        }
        if (errorCount >= errorMax){
            displayHangman();
            System.out.println("You lose! The word was: " + word);
        }
    }

    public static void newGuess(String letter){
        if (!lettersGuessed.contains(letter)){
            if (word.contains(letter)){
                int index = word.indexOf(letter);

                while (index >= 0){
                    wordLetters[index] = letter.charAt(0);
                    index = word.indexOf(letter, index + 1);
                }
                System.out.println("Good guess!");
            }
            else{
                System.out.println("Sorry, there's no " + letter);
                errorCount++;
            }
        }
        else{
            System.out.println("You've already guessed that letter!");
        }
    }

    public static void displayHangman(){
        System.out.println("_______");
        System.out.println("|/    |");
        if(errorCount >= 1) {
            System.out.println("|     O");
        }
        else{
            System.out.println("|");
        }
        if(errorCount >= 2) {
            if(difficulty == 3 || difficulty == 2 && errorCount >= 3 || difficulty == 1 && errorCount >= 4){
                System.out.println("|    /|\\");
            }
            else if(difficulty == 2 && errorCount ==2 || difficulty == 1 && errorCount ==2){
                System.out.println("|     |");
            }
            else if(difficulty == 1 && errorCount == 3){
                System.out.println("|    /|");
            }
        }
        else{
            System.out.println("|");
        }
        if (difficulty == 3 && errorCount >= 3 || difficulty == 2 && errorCount >= 4 || difficulty == 1 && errorCount >= 6){
            System.out.println("|    / \\      YOU LOSE!");
        }
        else if (difficulty == 1 && errorCount == 5){
            System.out.println("|    /");
        }
        else{
            System.out.println("|");
        }
        System.out.println("|____   __");
        System.out.println();
    }
}
