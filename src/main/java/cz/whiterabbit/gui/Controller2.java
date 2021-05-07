package cz.whiterabbit.gui;

import cz.whiterabbit.elements.GameController;
import cz.whiterabbit.elements.GameState;
import cz.whiterabbit.gui.swing.GameBoardMoveManager;
import cz.whiterabbit.gui.swing.GameFrame;
import cz.whiterabbit.gui.swing.listeners.FrameListener;
import cz.whiterabbit.gui.swing.listeners.GameBordMoveManagerListener;

public class Controller2 {
    private GameFrame gameFrame;
    private GameController gameController;
    private GameBoardMoveManager gameBoardMoveManager;

    private byte[] moveToConfirm;
    private byte[] moveToRedo;

    private int undoIndex;


    public Controller2(){
        gameFrame = new GameFrame();
        gameController = new GameController();
        gameBoardMoveManager = new GameBoardMoveManager(gameFrame.getPlayBoard(), gameController);

        moveToConfirm = new byte[0];
        moveToRedo = new byte[0];

        undoIndex = 0;

        gameFrame.setVisible(true);
        gameFrame.setContinueButtonEnabled(true);
        gameFrame.setConfirmButtonText("Start Game");

        initializeListeners();
    }

    private void initializeListeners() {
        gameBoardMoveManager.addListener(new GameBordMoveManagerListener() {
            @Override
            public void onMove(byte[] move) {
                onMoveResponse(move);
            }

            @Override
            public void onRepaintCall() {
                gameFrame.repaint();
            }
        });
        gameFrame.addFrameListener(new FrameListener() {
            @Override
            public void onConfirmClicked() {
                continueLoop();
            }

            @Override
            public void onRedoClicked() {
                redo();
            }

            @Override
            public void onUndoClicked() {
                undo();
            }

            @Override
            public void onHistoryClicked() {

            }

            @Override
            public void onInfoClicked() {

            }

            @Override
            public void onSettingsClicked() {

            }

            @Override
            public void onSaveClicked() {

            }

            @Override
            public void onLoadClicked() {

            }

            @Override
            public void onFieldClicked(int field) {

            }

            @Override
            public void onFieldSelected(int field) {

            }
        });
    }

    private void continueLoop() {
        //TODO reorganize if statement to reduce repetitions
        if(gameController.getGameState() == GameState.NOT_STARTED){
            gameController.startGame();
            gameFrame.setConfirmButtonText("Continue");
            continueLoop();
        }else if(playerIsComputer()){
            if(undoConfirmWaiting()){
                confirmUndo();
            }else if(moveMade()) {
                applyMove();
            }else if(!moveMade()){
                getMoveFromComputer();
            }
        }else if(!playerIsComputer()){
            if(undoConfirmWaiting()){
                confirmUndo();
            }else if(moveMade()){
                applyMove();
            }else if(!moveMade()){
                allowMoveSelection();
            }

        }
    }

    /**
     * Get move from computer player
     */
    private void getMoveFromComputer() {

    }

    /**
     * Allow player to select move from the playBoard
     */
    private void allowMoveSelection() {
        gameFrame.setContinueButtonEnabled(false);
        gameBoardMoveManager.setSelectionEnabled(true);
    }

    /**
     * Apply move made by computer or human player
     */
    private void applyMove() {
        gameController.applyMove(moveToConfirm);
        gameController.switchPlayerType();
        gameBoardMoveManager.updateBoard();
        moveToConfirm = new byte[0];
        gameFrame.repaint();
        continueLoop();
    }

    /**
     * return if move was made by ether computer or human player
     * @return
     */
    private boolean moveMade() {
        return moveToConfirm.length != 0;
    }

    /**
     * Confirm return to the state of the game from the history
     */
    private void confirmUndo() {
        gameController.returnToHistory(undoIndex);
        gameBoardMoveManager.updateBoard();
        undoIndex = 0;
        continueLoop();
    }

    /**
     * Return if player is computer player or human player
     * @return
     */
    private boolean playerIsComputer() {
        return false;
    }

    /**
     * Return if there is undo or redo action waiting to be confirmed
     * @return
     */
    private boolean undoConfirmWaiting(){
        return undoIndex != 0;
    }

    private void undo(){
        if(moveMade()){ //unconfirmed move
            moveToRedo = moveToConfirm;
            moveToConfirm = new byte[0];
            gameBoardMoveManager.updateBoard();
            gameFrame.setContinueButtonEnabled(false);
            gameBoardMoveManager.setSelectionEnabled(true);
        }else{
            byte[] boardFromHistory = gameController.getBoardFromHistory(undoIndex + 1);
            if(boardFromHistory.length != 0){
                undoIndex++;
                updatePlayBoardFromHistory(boardFromHistory);
            }
        }
    }

    private void updatePlayBoardFromHistory(byte[] boardFromHistory) {
        gameBoardMoveManager.setBoard(boardFromHistory);
        gameFrame.setContinueButtonEnabled(true);
        gameBoardMoveManager.setSelectionEnabled(false);
    }

    private void redo(){
        if(undoIndex == 0 && moveToRedo.length != 0){
            onMoveResponse(moveToRedo);
        }else {
            byte[] boardFromHistory = gameController.getBoardFromHistory(undoIndex - 1);
            if(boardFromHistory.length != 0){
                undoIndex--;
                updatePlayBoardFromHistory(boardFromHistory);
            }
        }
    }

    private void onMoveResponse(byte[] move){
        moveToConfirm = move;
        gameFrame.setContinueButtonEnabled(true);
        gameBoardMoveManager.previewMove(move);
        gameBoardMoveManager.setSelectionEnabled(false);
        moveToRedo = new byte[0];
    }


}
