package cz.whiterabbit.elements.movegenerator;


//todo probably wrong use of the singleton pattern
public class MoveGeneratorUtilities {
    private static MoveGeneratorUtilities moveGeneratorUtilities = new MoveGeneratorUtilities();

    private MoveGeneratorUtilities(){

    }

    public static MoveGeneratorUtilities moveGeneratorUtilities(){
        return moveGeneratorUtilities;
    }

    /**
     * Return boolean representing player type true-positive/false-negative
     * @param position
     * @param board
     * @param initialMove
     * @return
     * @throws Exception
     */
    protected boolean getPlayerType(byte position, byte[] board, byte[] initialMove) throws Exception {
        boolean positive = false;
        if(initialMove == null){
            if(board[position]>0){
                positive = true;
            }else if (board[position] < 0){
                positive = false;
            }else{
                throw new Exception("Box don't contains any play peace");
            }
        }else{
            if(initialMove.length > 1){
                if(board[initialMove[0]]>0){
                    positive = true;
                }else if (board[initialMove[0]]<0){
                    positive = false;
                }else{
                    throw new Exception("Invalid initial move, initial move cant be initialized from empty space");
                }
            }
        }
        return positive;
    }
}
