package cz.whiterabbit.elements;

import cz.whiterabbit.elements.movegenerator.MostCaptureEnemiesFilter;
import cz.whiterabbit.elements.movegenerator.MoveGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GameController {
    private Board board;
    private MoveGenerator moveGenerator;
    private Judge judge;
    private MoveMemory moveMemory;
    private boolean playerType;
    protected GameState gameState;

    public GameController() {
        this.board = new Board();
        this.moveGenerator = new MoveGenerator();
        this.moveMemory = new MoveMemory();
        this.judge = new Judge(moveMemory, board);
        this.gameState = GameState.NOT_STARTED;
    }

    //TESTED
    public void startGame(){
        resetGame();
        gameState = GameState.IN_PROGRESS;
    }

    //tested
    public boolean isValidMove(byte[] move){
        if (!moveMemory.isValid()) {
            moveMemory.setCurrentPossibleMoves(moveGenerator.getAllMovesForPlayer(playerType, board.getBoardArr(), new MostCaptureEnemiesFilter()));
        }
        return judge.isValidMove(move, moveMemory);
    }

    //tested
    public void applyMove(byte[] move) {
        applyMove(board.getBoardArr(), move);
    }

    public void applyMove(byte[] board, byte[] move) {
        board = this.board.applyMove(board, move);
        judge.validateBoard(board);
        moveMemory.addMove(move);
        moveMemory.invalidateMemory();
    }

    public void switchPlayerType(){
        playerType = !playerType;
        moveMemory.invalidateMemory();
    }

    //tested
    public List<byte[]> getAllValidMoves(){
        return getAllValidMoves( board.getBoardArr(), playerType);
    }

    public List<byte[]> getAllValidMovesFromPosition(int position){
        List<byte[]> validMoves = getAllValidMoves();
        List<byte[]> movesFromPosition = new ArrayList<>();
        validMoves.iterator().forEachRemaining(m -> {
            if(m[0] == position){
                movesFromPosition.add(m);
            }
        });
        return movesFromPosition;
    }

    public boolean canContinue(){
        return canContinue(board.getBoardArr());
    }

    private boolean validateGameState() {
        switch (gameState) {
            case DRAW:
            case POSITIVE_WIN:
            case NEGATIVE_WIN:
                return false;
            case IN_PROGRESS:
            case NOT_STARTED:
            default:
                return true;
        }
    }

    public GameState getGameState(){
        return gameState;
    }

    public Board getBoard() {
        return board;
    }

    public MoveMemory getMoveMemory() {
        return moveMemory;
    }

    public boolean isPlayerType() {
        return playerType;
    }

    public void setPlayerType(boolean playerType) {
        this.playerType = playerType;
    }

    public byte[] getBoardArr(){
        return board.getBoardArr();
    }

    public void resetGame(){
        board.resetBoard();
        moveMemory.resetMemory();
        playerType = true;
        gameState = GameState.NOT_STARTED;
    }
    public boolean canContinue(byte[] board){
        if(gameState != GameState.NOT_STARTED){ //so the NOT_STARTED game state is not overwritten
            gameState = judge.getCurrentGameState(board, moveMemory);
        }
        return validateGameState();
    }
    public List<byte[]> getAllValidMoves(byte[] board, boolean playerType){
        if(!moveMemory.isValid()){
            moveMemory.setCurrentPossibleMoves(moveGenerator.getAllMovesForPlayer(playerType, board, new MostCaptureEnemiesFilter()));
        }
        return moveMemory.getCurrentPossibleMoves();
    }

    public void setMoveMemory(MoveMemory moveMemory) {
        this.moveMemory = moveMemory;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public boolean undo(){
        byte[] move = moveMemory.undo();
        if(move != null){
            applyReverseMove(move);
            return true;
        }
        return false;
    }

    public boolean redo(){
        byte[] move = moveMemory.redo();
        if(move != null){
            board.applyMove(move);
            judge.validateBoard(board);
            moveMemory.invalidateMemory();
            return true;
        }
        return false;
    }

    private void applyReverseMove(byte[] move){
        byte[] reversedMove = reverseMove(move);
        board.applyMove(reversedMove);
        judge.validateBoard(board);
        moveMemory.invalidateMemory();
    }

    private byte[] applyReverseMove(byte[] board, byte[] move){
        byte[] reversedMove = reverseMove(move);
        board = applyMoveToTheBoard(board, reversedMove);
        judge.validateBoard(board);
        return board;
    }

    private byte[] reverseMove(byte[] move) {
        byte[] moveCopy = new byte[move.length];
        System.arraycopy(move, 0, moveCopy, 0, move.length);
        int index = 0;
        for(int i = move.length -3; i>=0; i-=3){
            moveCopy[index] = move[i];
            moveCopy[index+1] = move[i+2];
            moveCopy[index+2] = move[i+1];
            index+=3;
        }
        return moveCopy;
    }

    public byte[] getLastMove(){
        List<byte[]> moveList = moveMemory.getMovesHistory();
        if(moveList.size()>0) {
            return moveList.get(moveList.size()-1);
        }else{
            return new byte[0];
        }
    }

    public byte[] applyMoveToTheBoard(byte[] board, byte[] move){
        byte[] modifiedBoard = new byte[board.length];
        System.arraycopy(board, 0, modifiedBoard,0, modifiedBoard.length);
        for(int i = 0; i< move.length; i+=3){
            modifiedBoard[move[i]] = move[i+2];
        }
        return  modifiedBoard;
    }

    public byte[] getBoardFromHistory(int numberOfSteps){
        if(numberOfSteps <= moveMemory.getMovesHistory().size() && numberOfSteps>=0){
            byte[] boardToReverse = getBoardArr();
            for (int i = 0; i < numberOfSteps; i++) {
                byte[] moveToApply = moveMemory.getMovesHistory().get(moveMemory.getMovesHistory().size() - (1 + i));
                boardToReverse = applyReverseMove(boardToReverse, moveToApply);
            }
            return boardToReverse;
        }else if(numberOfSteps <0 && numberOfSteps >= -moveMemory.getRedoList().size()){
            System.out.println("Getting move from redo list");
            byte[] boardToForward = getBoardArr();
            for (int i = 0; i < Math.abs(numberOfSteps); i++) {
                byte[] moveToApply = moveMemory.getRedoList().get(moveMemory.getRedoList().size() - (1 + i));
                boardToForward = applyMoveToTheBoard(boardToForward, moveToApply);
            }
            return boardToForward;
        }else{
            return new byte[0];
        }
    }

    public void returnToHistory(int undoIndex) {
        if(undoIndex > 0){
            for(int i = 0; i< undoIndex; i++){
                undo();
            }
        }else if(undoIndex < 0){
            for(int i = 0; i< Math.abs(undoIndex); i++){
                redo();
            }

        }
    }

    public int getTotalRounds(){
        return moveMemory.getMovesHistory().size();
    }

    public int getRoundWithoutCapture(){
        //System.out.println("getRounds");
        return judge.getRoundsWithoutCapture();
    }

    public void setRedoList(List<byte[]> redoList){
        moveMemory.setRedoList(redoList);
        moveMemory.invalidateMemory();
    }
    public void setUndoList(List<byte[]> undoList){
        moveMemory.setUndoList(undoList);
    }

    public void setBoardArr(byte[] board){
        this.board.setBoard(board);
    }
}
