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
    protected GameState gameState;

    public GameController() {
        this.board = new Board();
        this.moveGenerator = new MoveGenerator();
        this.judge = new Judge();
        this.moveMemory = new MoveMemory();
        this.gameState = GameState.NOT_STARTED;
    }

    //TESTED
    public void startGame(){
        //System.out.println("START GAME CALL");
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
    public void applyMove(byte[] move) throws InvalidMoveException {
        applyMove(board.getBoardArr(), move);
    }

    public void applyMove(byte[] board, byte[] move) throws InvalidMoveException {
        if(isValidMove(move)){
            board = this.board.applyMove(board, move);
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
        return getAllValidMoves( board.getBoardArr(), playerType);
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

    protected Board getBoard() {
        return board;
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
}
