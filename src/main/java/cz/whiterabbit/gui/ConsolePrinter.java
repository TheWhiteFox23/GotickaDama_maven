package cz.whiterabbit.gui;

import cz.whiterabbit.elements.Board;


public class ConsolePrinter {
    Board board;

    /**
     * Meant for testing purpose only, will be removed
     */
    public ConsolePrinter(){

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
}
