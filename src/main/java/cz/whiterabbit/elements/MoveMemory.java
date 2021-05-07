package cz.whiterabbit.elements;

import java.util.ArrayList;
import java.util.List;

public class MoveMemory {
    private boolean valid = false;
    private List<byte[]> currentPossibleMoves;
    private List<byte[]> movesHistory;
    private List<byte[]> redoList;

    public MoveMemory(){
        movesHistory = new ArrayList<>();
        currentPossibleMoves = new ArrayList<>();
        redoList = new ArrayList<>();
    }

    public void addMove(byte[] move) {
        movesHistory.add(move);
        redoList = new ArrayList<>();

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

    public byte[] undo(){
        if(movesHistory.size() != 0){
            byte[] lastMove = movesHistory.get(movesHistory.size()-1);
            movesHistory.remove(movesHistory.size()-1);
            redoList.add(lastMove);
            return lastMove;
        }
        return null;
    }

    public byte[] redo(){
        if(redoList.size() != 0){
            byte[] lastMove = redoList.get(redoList.size()-1);
            movesHistory.add(lastMove);
            redoList.remove(redoList.size()-1);
            return lastMove;
        }
        return null;
    }

    public List<byte[]> getRedoList() {
        return redoList;
    }

    public void setRedoList(List<byte[]> redoList) {
        this.redoList = redoList;
    }
}
