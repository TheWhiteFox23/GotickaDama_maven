package cz.whiterabbit.elements;

import java.util.List;

public class Judge {

    private MoveMemory moveMemory;
    private Board board;

    public Judge(MoveMemory moveMemory, Board board){
        this.moveMemory = moveMemory;
        this.board = board;
    }

    public Judge(){

    }

    public boolean isValidMove(byte[] move, MoveMemory moveMemory){
        List<byte[]> allCurrentValidMoves = moveMemory.getCurrentPossibleMoves();
        return listContains(allCurrentValidMoves, move);
        //return false;
    }

    protected boolean listContains(List<byte[]> list, byte[] array){
        for(byte[] ba: list){
            boolean matching = false;
            if(ba.length == array.length){
                matching = true;
                for(int i = 0; i< ba.length; i++){
                    if(ba[i] != array[i]){
                        matching = false;
                        break;
                    }
                }
            }
            if(matching){
                return true;
            }
        }
        return false;
    }

    /**
     * adjust board according to rules
     * @param board
     */
    //TESTED
    public void validateBoard(Board board) {
        for(int i = 0; i<board.getBoardArr().length; i++){
            if(board.getBoardArr()[i] == -1 && i<=7){
                board.getBoardArr()[i] = -2;
            }else if ((board.getBoardArr()[i] == 1 && i>=56 && i<=63)){
                board.getBoardArr()[i] = 2;
            }
        }
    }

    /**
     * Return actual game state
     * @param board
     * @param moveMemory
     * @return
     */
    //TESTED
    public GameState getCurrentGameState(Board board, MoveMemory moveMemory) {
        if(!isCaptureInLast30(moveMemory)){
            return getWiningPlayer(board);
        }else{
            int[] count = getPeaceCount(board);
            if(count[0] == 0){
                return GameState.POSITIVE_WIN;
            }else if(count[1] == 0){
                return GameState.NEGATIVE_WIN;
            }else{
                return GameState.IN_PROGRESS;
            }
        }
    }

    /**
     * Search memory if in last 30 moves was any enemy captured
     * @param moveMemory
     * @return
     */
    //TESTED
    protected boolean isCaptureInLast30(MoveMemory moveMemory) {
        if(moveMemory.getMovesHistory().size() >= 30){
            boolean capture = false;
            for(int i = 0; i< 30; i++){
                if(moveMemory.getMovesHistory().get(i).length != 6){
                    return true;
                }
            }
            return false;
        }
        return true;
    }

    /**
     * Get winning player in case tha there is 30 moves in memory with no capture
     * @param board
     * @return
     */
    //TESTED
    protected GameState getWiningPlayer(Board board){
        int[] count = getPeaceCount(board);
        if(count[0] > count[1]){
            return GameState.NEGATIVE_WIN;
        }else if( count[0] < count[1]){
            return GameState.POSITIVE_WIN;
        }else{
            return GameState.DRAW;
        }
    }

    /**
     * return array in form {negativeCount, positiveCount}
     * @param board
     * @return
     */
    //TESTED
    protected int[] getPeaceCount(Board board){
        int negativeCount = 0;
        int positiveCount = 0;
        for(int i = 0; i< board.getBoardArr().length; i++){
            if(board.getBoardArr()[i] > 0){
                positiveCount++;
            }else if(board.getBoardArr()[i]<0){
                negativeCount++;
            }
        }
        return new int[]{negativeCount, positiveCount};
    }
}
