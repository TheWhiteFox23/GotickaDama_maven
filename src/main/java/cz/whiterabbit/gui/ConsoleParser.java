package cz.whiterabbit.gui;


/**
 * Create console output representing current game status
 * - singleton, don't really need more than one instance
 */
@Deprecated
public class ConsoleParser {
    public static ConsoleParser consoleParser = new ConsoleParser();

    /**
     * singletons private constructor
     */
    private ConsoleParser(){

    }

    /**
     * Draw ascii representation of the board
     * temporally method, will be replaced todo - rewrite after debugging
     * @param board
     */
    public void drawBoard(byte[] board){
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

}
