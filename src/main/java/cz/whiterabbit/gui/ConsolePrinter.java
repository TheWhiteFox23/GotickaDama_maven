package cz.whiterabbit.gui;

import cz.whiterabbit.elements.Board;
import cz.whiterabbit.gameloop.GameSettings;

import java.util.Scanner;



public class ConsolePrinter {
    Board board;
    ConsoleParser consoleParser;

    /**
     * Meant for testing purpose only, will be removed
     */
    public ConsolePrinter(){
        consoleParser = new ConsoleParser(new Scanner(System.in));
    }


    public ConsolePrinter(Board board){

        this.board = board;
    }


    public void repaint(){
        clearScreen();
        drawBoard(board.getBoardArr());

    }

    private void drawBoard(byte[] board){
        //int count = 0;
        printHorizontalLine();
        for(int i = 0; i<board.length; i++){
            if((i+1)%8 != 0){
                if(String.valueOf(board[i]).length()==1){
                    System.out.print("|" + board[i] + " ");
                }else{
                    System.out.print("|" + board[i]);
                }
            }else{
                if(String.valueOf(board[i]).length()==1) {
                    System.out.print("|" + board[i] + " |" + "\n");
                }else{
                    System.out.print("|" + board[i] + "|" + "\n");
                }
            }
        }
        printHorizontalLine();
    }

    private void printHorizontalLine(){
        for(int i = 0; i< 8; i++){
            System.out.print("---");
        }
        System.out.println();
    }

    public static void clearScreen() {
        //System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    /**
     * test method will be removed
     */
    public void writeSymbol(int symbol){
        clearScreen();
        System.out.println(symbol);
    }

    //todo test method
    public GameSettings promptForInitialSettings(){
        //Scanner scanner = new Scanner(System.in);
        GameSettings gameSettings = new GameSettings();
        System.out.println("Initializing new game, please enter settings");

        //player_1 name
        gameSettings.setWhitePlayerName(consoleParser.promptForString(
                "White player name("+
                        gameSettings.getWhitePlayerName()+
                        "):",
                gameSettings.getWhitePlayerName()));


        //player_2 name
        gameSettings.setBlackPlayerName(consoleParser.promptForString(
                "White player name("+
                        gameSettings.getBlackPlayerName()+
                        "):",
                gameSettings.getBlackPlayerName()));

        //player_1 operator
        gameSettings.setWhiteOperator(consoleParser.promptForPlayerOperator(
                gameSettings.getWhitePlayerName() +
                        " operator(" + gameSettings.getWhiteOperator()+ ")" +
                        "PLAYER / COMPUTER_MINIMAX / COMPUTER_RANDOM " +
                        ":",
                gameSettings.getWhiteOperator()));


        //player_2 operator
        gameSettings.setBlackOperator(consoleParser.promptForPlayerOperator(
                gameSettings.getBlackPlayerName() +
                        " operator(" + gameSettings.getBlackOperator()+ ")" +
                        "PLAYER / COMPUTER_MINIMAX / COMPUTER_RANDOM " +
                        ":",
                gameSettings.getBlackOperator()));

        //player_1 level
        gameSettings.setWhitePlayerLevel(consoleParser.promptForPlayerLevel(
                gameSettings.getWhitePlayerName() +
                        " level(" + gameSettings.getWhitePlayerLevel()+ ")" +
                        "EASY / MEDIUM / HARD " +
                        ":",
                gameSettings.getWhitePlayerLevel()));

        //player_2 level
        gameSettings.setBlackPlayerLevel(consoleParser.promptForPlayerLevel(
                gameSettings.getBlackPlayerName() +
                        " level(" + gameSettings.getBlackPlayerLevel()+ ")" +
                        "EASY / MEDIUM / HARD " +
                        ":",
                gameSettings.getBlackPlayerLevel()));

        return  gameSettings;
    }
}
