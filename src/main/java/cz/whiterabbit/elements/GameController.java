package cz.whiterabbit.elements;

import cz.whiterabbit.elements.movegenerator.MostCaptureEnemiesFilter;
import cz.whiterabbit.elements.movegenerator.MoveGenerator;

import java.util.List;

public class GameController {
    private Board board;
    private MoveGenerator moveGenerator;
    private Judge judge;
    private MoveMemory moveMemory;
    private boolean playerType;
    GameState gameState;

    public GameController() {
        this.board = new Board();
        this.moveGenerator = new MoveGenerator();
        this.judge = new Judge();
        this.moveMemory = new MoveMemory();
        gameState = GameState.NOT_STARTED;
    }

    //TESTED
    public void startGame(){
        board.resetBoard();
        moveMemory.resetMemory();
        playerType = true;
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
    public void applyMove(byte[] move) throws InvalidMoveException {
        if(isValidMove(move)){
            board.applyMove(move);
            judge.validateBoard(board);
            moveMemory.addMove(move);
            moveMemory.invalidateMemory();
        }else{
            throw new InvalidMoveException();
        }
    }

    public void switchPlayerType(){
        playerType = !playerType;
        moveMemory.invalidateMemory();
    }

    //tested
    public List<byte[]> getAllValidMoves(){
        if(!moveMemory.isValid()){
            moveMemory.setCurrentPossibleMoves(moveGenerator.getAllMovesForPlayer(playerType, board.getBoardArr(), new MostCaptureEnemiesFilter()));
        }
        return moveMemory.getCurrentPossibleMoves();
    }

    public boolean canContinue(){
        gameState = judge.getCurrentGameState(board, moveMemory);
        switch(gameState){
            case DRAW:
            case POSITIVE_WIN:
            case NEGATIVE_WIN:
                return false;
            case IN_PROGRESS:
            default:
                return true;
        }
    }

    public GameState getGameState(){
        return gameState;
    }

    protected Board getBoard() {
        return board;
    }

    protected MoveGenerator getMoveGenerator() {
        return moveGenerator;
    }

    protected Judge getJudge() {
        return judge;
    }

    protected MoveMemory getMoveMemory() {
        return moveMemory;
    }

    public boolean isPlayerType() {
        return playerType;
    }

    protected void setPlayerType(boolean playerType) {
        this.playerType = playerType;
    }

    public byte[] getBoardArr(){
        return board.getBoardArr();
    }
}
