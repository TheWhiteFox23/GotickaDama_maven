package cz.whiterabbit.elements;

import java.util.ArrayList;
import java.util.List;

public class MoveMemory {
    private boolean valid = false;
    private List<byte[]> currentPossibleMoves;
    private List<byte[]> movesHistory;

    public MoveMemory(){
        movesHistory = new ArrayList<>();
        currentPossibleMoves = new ArrayList<>();
    }

    public void  addMove(byte[] move){
        movesHistory.add(move);
    }

    public List<byte[]> getMovesHistory(){
        return movesHistory;
    }

    public List<byte[]> getCurrentPossibleMoves(){
        return currentPossibleMoves;
    }

    public void setCurrentPossibleMoves(List<byte[]> currentPossibleMoves){
        this.currentPossibleMoves = currentPossibleMoves;
        validateMemory();
    }

    public void resetMemory(){
        currentPossibleMoves = new ArrayList<>();
        movesHistory = new ArrayList<>();
    }

    private void validateMemory(){
        valid = true;
    }

    public void invalidateMemory(){
        valid = false;
    }

    public boolean isValid(){
        return valid;
    }
}
