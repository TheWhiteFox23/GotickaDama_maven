package cz.whiterabbit.gui;

import cz.whiterabbit.gameloop.CommandListener;
import cz.whiterabbit.gameloop.PlayerLevel;
import cz.whiterabbit.gameloop.PlayerOperator;

import java.util.Scanner;

public class ConsoleParser {
    private Scanner scanner; //for testing - allow to change source
    private CommandListener commandListener;

    public ConsoleParser(Scanner scanner){
        this.scanner = scanner;
    }

    /**
     * Prompt for string and return it, no check
     * @param promptText - text to display when prompting
     * @return
     */

    public String promptForString(String promptText, String defaultOption){
        System.out.print(promptText);
        String userInput = scanner.nextLine();
        if(userInput.length() == 0)  return defaultOption;
        return userInput;
    }

    public PlayerOperator promptForPlayerOperator(String promptText, PlayerOperator operator){
        System.out.print(promptText);
        String userInput = scanner.nextLine();

        if(userInput.length() == 0) return  operator;

        switch(userInput.toLowerCase()){
            case "player" : {
                return PlayerOperator.HUMAN_PLAYER;
            } case "computer_minimax":{
                return PlayerOperator.COMPUTER_MINIMAX;
            } case "computer_random":{
                return PlayerOperator.COMPUTER_RANDOM;
            } default: {
                System.out.println("INVALID OPTION");
                return promptForPlayerOperator(promptText, operator);
            }
        }
    }

    public PlayerLevel promptForPlayerLevel(String promptText, PlayerLevel level){
        System.out.print(promptText);
        String userInput = scanner.nextLine();

        if(userInput.length() == 0) return  level;

        switch(userInput.toLowerCase()){
            case "easy" : {
                return PlayerLevel.EASY;
            } case "medium":{
                return PlayerLevel.MEDIUM;
            } case "hard":{
                return PlayerLevel.HARD;
            } default: {
                System.out.println("INVALID OPTION");
                return promptForPlayerLevel(promptText, level);
            }
        }
    }

    public void promptForCommand(String promptText){
        System.out.print(promptText);
        String inputText = scanner.nextLine();
        switch (inputText.toLowerCase()){
            case "help":{
                printCommandList();
                break;
            } case "resume":{
                commandListener.onGameResume();
                break;
            } case "settings":{
                commandListener.onChangeSettings();
                break;
            } case "restart" :{
                commandListener.onGameRestart();
                break;
            } case "moves" :{
                commandListener.onPrintAllMoves();
                break;
            } case "suggest_move" : {
                commandListener.onPrintBestMove();
                break;
            } default:{
                printCommandList();
            }
        }

    }

    private void printCommandList(){
        System.out.println(
                "help : show list of commands \n" +
                        "settings : invoke settings menu \n" +
                        "resume : continue the game loop \n" +
                        "restart : start a new game \n" +
                        "moves : print list of all the possible moves \n" +
                        "suggest_move : suggest best possible move");

    }

    public CommandListener getCommandListener() {
        return commandListener;
    }

    public void setCommandListener(CommandListener commandListener) {
        this.commandListener = commandListener;
    }
}
