package cz.whiterabbit.gui;

import cz.whiterabbit.elements.GameController;
import cz.whiterabbit.elements.GameState;
import cz.whiterabbit.elements.computerplayer.MinimaxComputerPlayer;
import cz.whiterabbit.gui.swing.ElementList.ListCrate;
import cz.whiterabbit.gui.swing.GameBoardMoveManager;
import cz.whiterabbit.gui.swing.GameFrame;
import cz.whiterabbit.gui.swing.dialogs.ConfirmDialog;
import cz.whiterabbit.gui.swing.dialogs.InfoDialog;
import cz.whiterabbit.gui.swing.dialogs.RulesDialog;
import cz.whiterabbit.gui.swing.sidePanel.Panels.SettingsPanel;
import cz.whiterabbit.gui.swing.listeners.FrameListener;
import cz.whiterabbit.gui.swing.listeners.GameBordMoveManagerListener;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.util.ArrayList;
import java.util.List;

public class Controller {
    private GameFrame gameFrame;
    private GameController gameController;
    private GameBoardMoveManager gameBoardMoveManager;
    private MinimaxComputerPlayer computerPlayer;

    private byte[] moveToConfirm;
    private byte[] moveToRedo;

    private int undoIndex;


    public Controller(){
        gameFrame = new GameFrame();
        gameController = new GameController();
        gameBoardMoveManager = new GameBoardMoveManager(gameFrame.getPlayBoard(), gameController);
        computerPlayer = new MinimaxComputerPlayer();

        moveToConfirm = new byte[0];
        moveToRedo = new byte[0];

        undoIndex = 0;

        gameFrame.setVisible(true);
        gameFrame.setContinueButtonEnabled(true);
        gameFrame.setConfirmButtonText("Start Game");

        gameBoardMoveManager.updateBoard();
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
            public void onSaveFileClicked(String filename) {
                try{
                    saveFile(filename);;
                }catch (Exception e){
                    InfoDialog infoDialog = new InfoDialog(gameFrame, "Unable to save the file");
                    infoDialog.showDialog();
                }
            }

            @Override
            public void onLoadFileClicked(String filename) {
                try{
                    loadFile(filename);
                }catch (Exception e){
                    InfoDialog infoDialog = new InfoDialog(gameFrame, "Unable to load the save file");
                    infoDialog.showDialog();
                }

            }

            @Override
            public void onSettingsConfirm() {
                settingsConfirm();
            }

            @Override
            public void onNewGameClicked() {
                onNewGame();
            }

            @Override
            public void onSuggestMoveClicked() {
                onSuggestMove();
            }

            @Override
            public void onRulesClicked() {
                onRules();
            }

            @Override
            public void onFieldClicked(int field) {

            }

            @Override
            public void onFieldSelected(int field) {

            }


            @Override
            public void onPreviewPressed(int previewIndex) {
                byte[] boardFromHistory = gameController.getBoardFromHistory(previewIndex);
                if(boardFromHistory.length != 0){
                    gameBoardMoveManager.setBoard(boardFromHistory);
                }
            }

            @Override
            public void onPreviewReleased(int previewIndex) {
                if(moveToConfirm.length != 0){
                    gameBoardMoveManager.previewMove(moveToConfirm);
                }else{
                    gameBoardMoveManager.updateBoard();
                }

            }

            @Override
            public void onReverseClicked(int reverseIndex) {
                if(reverseIndex != 0){
                    confirmUndo(reverseIndex);
                }
            }

        });
    }

    private void onRules() {
        RulesDialog rulesDialog = new RulesDialog(gameFrame);
        rulesDialog.showDialog();
    }

    private void onSuggestMove() {
        getMoveFromComputer();
        gameBoardMoveManager.updateBoard();
        gameBoardMoveManager.setSelectionEnabled(false);

    }

    private void onNewGame() {
        ConfirmDialog confirmDialog = new ConfirmDialog(gameFrame,"<html><h3>Do you want to start new game?</h3></html>");
        if(confirmDialog.showDialog()){
            gameController.startGame();
            gameBoardMoveManager.updateBoard();
            continueLoop();
        }

    }

    private void settingsConfirm() {
        if(playerIsComputer() && !moveMade()){
            gameBoardMoveManager.setSelectionEnabled(false);
            getMoveFromComputer();
        }
    }

    private void loadFile(String filename) throws SaveFileException {
        SaveFile saveFile = new SaveFile();
        try {
            saveFile = FileManager.getInstance().parseXmlToSaveFile(filename);
        } catch (SaveFileException e) {
            throw new SaveFileException("Invalid file");
        }
        gameController.setBoardArr(saveFile.getBoardState());
        gameController.setRedoList(saveFile.getRedoList());
        gameController.setUndoList(saveFile.getUndoList());
        gameController.setPlayerType(getPlayerOnMove());

        gameFrame.setBlackPlayerDifficulty(saveFile.getBlackPlayerDifficulty());
        gameFrame.setWhitePlayerDifficulty(saveFile.getWhitePlayerDifficulty());
        gameFrame.setBlackPlayerType(saveFile.getBlackPlayerType());
        gameFrame.setWhitePlayerType(saveFile.getWhitePlayerType());

        moveToConfirm = new byte[0];
        undoIndex = 0;


        gameBoardMoveManager.updateBoard();
        gameFrame.repaint();

        settingsConfirm();
        continueLoop();
    }

    private void continueLoop() {
        //TODO reorganize if statement to reduce repetitions
        if(gameController.getGameState() == GameState.NOT_STARTED){
            gameController.startGame();
            gameBoardMoveManager.updateBoard();
            gameFrame.setConfirmButtonText("Continue");
            continueLoop();
        }else if(gameController.canContinue()) {
            if(playerIsComputer()){
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
        }else{
            switch (gameController.getGameState()){
                case NEGATIVE_WIN:{
                    InfoDialog infoDialog = new InfoDialog(gameFrame, "Black Player Win");
                    infoDialog.showDialog();
                    break;
                }
                case POSITIVE_WIN:{
                    InfoDialog infoDialog = new InfoDialog(gameFrame, "White Player Win");
                    infoDialog.showDialog();
                    break;
                }
                case DRAW:{
                    InfoDialog infoDialog = new InfoDialog(gameFrame, "It's a Draw");
                    infoDialog.showDialog();
                    break;
                }
            }
        }


        updateGameInfo();
        gameFrame.setHistoryList(getHistoryList());
    }

    /**
     * Get move from computer player
     */
    private void getMoveFromComputer() {
        gameFrame.setConfirmButtonText("Calculating move");
        gameFrame.setContinueButtonEnabled(false);
        int difficultyFromSettings;
        if(gameController.isPlayerType()){
            difficultyFromSettings = gameFrame.getWhitePlayerDifficulty();
        }else{
            difficultyFromSettings = gameFrame.getBlackPlayerDifficulty();
        }
        setComputerPlayerDifficulty(difficultyFromSettings);
        getMoveInSeparateThread();
    }

    private void setComputerPlayerDifficulty(int difficultyFromSettings) {
        switch (difficultyFromSettings){
            case SettingsPanel.DIFFICULTY_EASY:{
                computerPlayer.setDifficulty(4);
                break;
            }
            case SettingsPanel.DIFFICULTY_MEDIUM:{
                computerPlayer.setDifficulty(5);
                break;
            }
            case SettingsPanel.DIFFICULTY_HARD:{
                computerPlayer.setDifficulty(6);
                break;
            }
        }
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
        gameController.setPlayerType(getPlayerOnMove());
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
        confirmUndo(undoIndex);
    }

    private void confirmUndo(int index) {
        gameController.returnToHistory(index);
        gameBoardMoveManager.updateBoard();
        undoIndex = 0;
        gameController.setPlayerType(getPlayerOnMove());
        continueLoop();
    }

    /**
     * Return if player is computer player or human player
     * @return
     */
    private boolean playerIsComputer() {
        boolean playerType = gameController.isPlayerType();
        if(playerType){
            return gameFrame.getWhitePlayerType() == SettingsPanel.PLAYER_COMPUTER;
        }else{
            return gameFrame.getBlackPlayerType() == SettingsPanel.PLAYER_COMPUTER;
        }
    }

    /**
     * Return if there is undo or redo action waiting to be confirmed
     * @return
     */
    private boolean undoConfirmWaiting(){
        return undoIndex != 0;
    }

    private void undo(){
        if(moveMade()){
            moveToRedo = moveToConfirm;
            moveToConfirm = new byte[0];
            gameBoardMoveManager.updateBoard();
            gameFrame.setContinueButtonEnabled(false);
            gameBoardMoveManager.setSelectionEnabled(true);
            if(playerIsComputer()){
                getMoveFromComputer();
            }
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

    private void updateGameInfo(){
        boolean playerType = gameController.isPlayerType();
        int figuresBlack = getFigures(false);
        int figuresWhite = getFigures(true);
        int totalRounds = gameController.getTotalRounds();
        int roundsNoCapture = gameController.getRoundWithoutCapture();

        gameFrame.updateInfoTable(playerType, figuresBlack, figuresWhite, totalRounds, roundsNoCapture);
    }

    private int getFigures(boolean playerType){
        int count = 0;
        byte[] board = gameController.getBoardArr();
        for(byte b : board){
            if(playerType && b>0){
                count++;
            }else if(!playerType && b<0){
                count++;
            }
        }
        return count;
    }

    private boolean getPlayerOnMove(){
        byte[] lastMove = gameController.getLastMove();
        if(lastMove.length != 0){
            return lastMove[1] < 0;
        }else return true;
    }

    private List<ListCrate> getHistoryList(){
        List<byte[]> redoList = gameController.getMoveMemory().getRedoList();
        List<byte[]> undoList = gameController.getMoveMemory().getMovesHistory();
        List<ListCrate> historyList = new ArrayList<>();
        for(int i = 0; i< redoList.size(); i++){
            historyList.add(new ListCrate(redoList.get(i), -redoList.size() + (i)));
        }
        for(int i = 0; i<undoList.size(); i++){
            historyList.add(new ListCrate(undoList.get(undoList.size() - (i+1)), i+1));
        }


        return historyList;
    }

    private void getMoveInSeparateThread(){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                byte[] move = computerPlayer.chooseMove(gameController.getBoardArr(),gameController.isPlayerType());
                moveToConfirm = move;
                gameBoardMoveManager.previewMove(move);
                gameFrame.setConfirmButtonText("Confirm");
                gameFrame.setContinueButtonEnabled(true);
                //continueLoop();
            }
        });
        thread.start();

    }

    private void saveFile(String filename){
        SaveFile saveFile = new SaveFile();
        saveFile.setUndoList(gameController.getMoveMemory().getMovesHistory());
        saveFile.setRedoList(gameController.getMoveMemory().getRedoList());
        saveFile.setBoardState(gameController.getBoardArr());
        saveFile.setBlackPlayerDifficulty(gameFrame.getBlackPlayerDifficulty());
        saveFile.setWhitePlayerDifficulty(gameFrame.getWhitePlayerDifficulty());
        saveFile.setBlackPlayerType(gameFrame.getBlackPlayerType());
        saveFile.setWhitePlayerType(gameFrame.getWhitePlayerType());
        try {
            FileManager.getInstance().createSaveGame(saveFile, filename+".xml");
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (TransformerException e) {
            e.printStackTrace();
        }
    }


}
