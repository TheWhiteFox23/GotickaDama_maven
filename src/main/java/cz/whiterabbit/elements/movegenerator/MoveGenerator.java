package cz.whiterabbit.elements.movegenerator;

import java.util.ArrayList;
import java.util.List;


//todo: both methods can be refactored to be more simple, look at it after testing if there is still time

//todo: Separate into different classes (same interface)
public class MoveGenerator {

    public void getAllMovesForPlayer(boolean playerType, byte[] board, MoveFilter filter){

    }

    public void getAllMovesForPlayer(boolean playerType, byte[] board){
        List<byte[]> moves = new ArrayList<>();
        for(int i = 0; i< board.length; i++){
            if(isPositive(board[i]) == playerType){
                moves.addAll(getMovesFromPosition(i, board));
            }
        }
    }

    protected boolean isPositive(byte b){
        return b>0;
    }

    /**
     * Get all of the valid moves from given position (don't filter moves with most captures)
     * @param position
     * @param board
     * @return
     */
    public List<byte[]> getMovesFromPosition(int position, byte[] board){
        Finder finder;
        List<byte[]> moves = new ArrayList<>();
        switch (Math.abs(board[position])){
            case 1 : {
                finder = new RegularFinder();
                moves.addAll(finder.find(position, board));
                break;
            }
            case 2 :{
                finder = new RoyalFinder();
                moves.addAll(finder.find(position, board));
                break;
            }
        }
        return moves;
    }
}
