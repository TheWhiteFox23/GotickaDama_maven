package cz.whiterabbit.gui.frames;

import com.googlecode.lanterna.SGR;
import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TextCharacter;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.Screen;
import cz.whiterabbit.elements.GameController;
import cz.whiterabbit.elements.GameState;
import cz.whiterabbit.elements.InvalidMoveException;
import cz.whiterabbit.gui.frames.elements.GameSettings;
import cz.whiterabbit.gui.frames.elements.PlayerOperator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameLoopFrame extends LanternaFrame implements GUIFrame{
    private GameController gameController;
    private GameSettings gameSettings;

    private String promptText = "Please enter your move(initial and final position): ";
    private String ErrorMessageString = "MOVE IS INVALID PLEASE TRY AGAIN";
    private String moveString = "";

    private boolean promptForMove = false;
    private boolean errorTextVisible = false;
    private boolean continueGame = true;
    private boolean insertMoveAvailable = true;

    FrameState frameState;
    public GameLoopFrame(Screen screen, GameController gameController, GameSettings gameSettings) {
        super(screen);
        this.gameController = gameController;
        this.gameSettings = gameSettings;
        this.frameState = getFrameState();
    }

    /**
     * Return FrameState based on currant player and its operator
     * @return
     */
    private FrameState getFrameState() {
        PlayerOperator operator;
        boolean positivePlayerOnMOve = gameController.isPlayerType();
        if(positivePlayerOnMOve){
            operator = gameSettings.getWhiteOperator();
        }else{
            operator = gameSettings.getBlackOperator();
        }

        if(!gameController.canContinue())return FrameState.GAME_FINISH;

        switch (operator){
            case HUMAN_PLAYER:{
                if(promptForMove){
                    return FrameState.INPUT_MENU;
                }else{
                    return FrameState.PLAYER_MENU;
                }
            }
            case COMPUTER_RANDOM:
            case COMPUTER_MINIMAX: {
                return FrameState.COMPUTER_MENU;
            }
        }
        return null;
    }

    //todo - overwrite drawFrame method
    //todo - prompt for move logic
    @Override
    void onDraw() {
        //System.out.println(frameState);
        if(gameController.canContinue()){
            frameState = getFrameState();
                    switch (frameState){
                        case INPUT_MENU -> drawInputMenu();
                        case PLAYER_MENU -> drawPlayerMenu();
                        case COMPUTER_MENU -> drawComputerMenu();
                    }
                    if(continueGame){
                        manageMoveInput(gameController.isPlayerType());

            }
        }else{
            frameState = getFrameState();
            switch (gameController.getGameState()){
                case DRAW -> drawFinishMenu("DRAW");
                case POSITIVE_WIN -> drawFinishMenu("PLAYER X WINS");
                case NEGATIVE_WIN -> drawFinishMenu("PLAYER O WINS");
            }
        }

    }

    private void drawInputMenu() {
        drawBoard(gameController.getBoardArr(), 2, 1);
        drawSimpleText(promptText, 3, 20);
        if(errorTextVisible)drawSimpleText(ErrorMessageString, 3, 19,SGR.BOLD);
        drawSimpleText("press ENTER to confirm", 3, 21);
        drawSimpleText(moveString, promptText.length() + 2, 20);
        drawSimpleText("Player on move : " + getPlayerOnMove(), 20, 3);
    }

    private String getPlayerOnMove() {
        if(gameController.isPlayerType()){
            return "X";
        }else {
            return "O";
        }
    }

    private void drawComputerMenu(){
        drawBoard(gameController.getBoardArr(), 2, 1);
        drawSimpleText("S - Change Settings", 3, 19);
        drawSimpleText("C - Continue", 3,18);
        drawSimpleText("Player on move : " + getPlayerOnMove(), 20, 3);
    }
    private void drawPlayerMenu(){
        drawBoard(gameController.getBoardArr(), 2, 1);
        drawSimpleText("S - Change Settings", 3, 20);
        drawSimpleText("I - Insert Move", 3, 19);
        drawSimpleText("C - Continue", 3,18);
        drawSimpleText("Player on move : " + getPlayerOnMove(), 20, 3);

    }

    private void drawFinishMenu(String text) {
        //System.out.println(text);
        getScreen().clear();
        drawBoard(gameController.getBoardArr(), 2, 1);
        drawSimpleText("GAME FINISH : " + text, 3, 20);
    }

    private void manageMoveInput(boolean positiveOnMove) {
        PlayerOperator operator;
        if(positiveOnMove){
            operator = gameSettings.getWhiteOperator();
        }else{
            operator = gameSettings.getBlackOperator();
        }

        switch (operator){
            case HUMAN_PLAYER:
            case COMPUTER_MINIMAX: {
                //todo applyMove(playerMinimax.chooseTheMove(moves))
                break;
            }
            case COMPUTER_RANDOM:{
                    try {
                        byte[] move = chooseRandomMove(gameController.getAllValidMoves());
                        gameController.applyMove(move);
                    } catch (InvalidMoveException e) {
                        e.printStackTrace();
                    }
                    continueGame = false;
                    invalidate();
            }
        }
    }

    private byte[] chooseRandomMove(List<byte[]> allValidMoves) {
        Random random = new Random();
        return allValidMoves.get(random.nextInt(allValidMoves.size()));
    }

    @Override
    void onListen() {
        KeyStroke keyStroke = null;
        try {
            keyStroke = getScreen().pollInput();
        } catch (IOException e) {
            e.printStackTrace();
        }
        switch (frameState){
            case INPUT_MENU -> listenToInputMenu(keyStroke);
            case PLAYER_MENU -> listenToPlayerMenu(keyStroke);
            case COMPUTER_MENU -> listenToComputerMenu(keyStroke);
            case GAME_FINISH -> listenToGameFinishMenu(keyStroke);
        }
    }

    private void listenToGameFinishMenu(KeyStroke keyStroke) {
    }

    private void listenToComputerMenu(KeyStroke keyStroke) {
        if(keyStroke != null && (keyStroke.getKeyType() != KeyType.EOF || keyStroke.getKeyType() != KeyType.Escape)){
            if(keyStroke.getKeyType() == KeyType.Character && keyStroke.getCharacter() == 's'){
                //todo implement change to settings menu
                System.out.println("settings menu");
            }else if(keyStroke.getKeyType() == KeyType.Character && keyStroke.getCharacter() == 'c'){
                //System.out.println("continue");
                continueGame = true;
                insertMoveAvailable = true;
                getScreen().clear();
                gameController.switchPlayerType();
                invalidate();
            }else if(keyStroke.getKeyType() == KeyType.Character && keyStroke.getCharacter() == 'm'){
                //todo implement change to menu
                System.out.println("Menu");
            }else if(keyStroke.getKeyType() == KeyType.Character && keyStroke.getCharacter() == 'r'){
                //todo implement change to menu
                invalidate();
            }
        }
    }

    private void listenToPlayerMenu(KeyStroke keyStroke) {
        if(keyStroke != null && (keyStroke.getKeyType() != KeyType.EOF || keyStroke.getKeyType() != KeyType.Escape)){
            if(keyStroke.getKeyType() == KeyType.Character && keyStroke.getCharacter() == 'i'){
                if(insertMoveAvailable){
                    promptForMove = true;
                    getScreen().clear();
                    invalidate();
                }
            }else if(keyStroke.getKeyType() == KeyType.Character && keyStroke.getCharacter() == 's'){
                //todo implement change to settings menu
                System.out.println("settings menu");
            }else if(keyStroke.getKeyType() == KeyType.Character && keyStroke.getCharacter() == 'c'){
                continueGame = true;
                insertMoveAvailable = true;
                getScreen().clear();
                gameController.switchPlayerType();
                invalidate();
            }else if(keyStroke.getKeyType() == KeyType.Character && keyStroke.getCharacter() == 'm'){
                //todo implement change to menu
                System.out.println("Menu");
            }else if(keyStroke.getKeyType() == KeyType.Character && keyStroke.getCharacter() == 'r'){
                //todo implement change to menu
                invalidate();
            }
        }
    }

    private void listenToInputMenu(KeyStroke keyStroke) {
        if(keyStroke != null && (keyStroke.getKeyType() != KeyType.EOF || keyStroke.getKeyType() != KeyType.Escape)){
            if(keyStroke.getKeyType() == KeyType.Character){
                moveString += keyStroke.getCharacter();
                invalidate();
            }else if(keyStroke.getKeyType() == KeyType.Backspace){
                if(moveString.length()>0) {
                    moveString = moveString.substring(0, moveString.length() -1);
                    getScreen().clear();
                    invalidate();
                }
            }else if(keyStroke.getKeyType() == KeyType.Enter){
                if(applyMove(moveString)){
                    moveString = "";
                    getScreen().clear();
                    promptForMove = false;
                    invalidate();
                    continueGame = false;
                    insertMoveAvailable = false;
                }else{
                    invalidate();
                    getScreen().clear();
                    moveString = "";
                }
            }
        }

    }

    private void drawBoard(byte[] board, int positionX, int positionY){
        //Draw board markings
        char[] verticalMarking = "12345678".toCharArray();
        char[] horizontalMarking = "ABCDEFGH".toCharArray();
        drawHorizontally(positionX+1, positionY, horizontalMarking);
        drawHorizontally(positionX+1, positionY+9, horizontalMarking);
        drawVertically(positionX, positionY+1, verticalMarking);
        drawVertically(positionX+9, positionY+1, verticalMarking);
        positionY+=1;
        positionX+=1;
        for(int i = 0; i< board.length; i++){
            TerminalPosition cellPosition = new TerminalPosition(
                    positionX+(i%8),
                    positionY+(i/8)
            );
            TextColor.ANSI textColor = TextColor.ANSI.WHITE;
            TextColor.ANSI backgroundColor = TextColor.ANSI.BLACK;
            if((i/8)%2 == 0 && i%2 == 0 || (i/8)%2 != 0 && i%2 != 0){
                textColor = TextColor.ANSI.BLACK;
                backgroundColor = TextColor.ANSI.WHITE;
            }
            TextCharacter charactersInBackBuffer = getScreen().getBackCharacter(cellPosition);
            charactersInBackBuffer = charactersInBackBuffer.withBackgroundColor(backgroundColor);
            charactersInBackBuffer = charactersInBackBuffer.withCharacter(parseCharFromBoard(board[i]));
            charactersInBackBuffer = charactersInBackBuffer.withForegroundColor(textColor);
            getScreen().setCharacter(cellPosition, charactersInBackBuffer);
        }
        invalidate();
    }

    private void drawHorizontally(int positionX, int positionY, char[] horizontalMarking) {
        for(int i = 0; i< horizontalMarking.length; i++){
            TerminalPosition cellPosition = new TerminalPosition(
                    positionX +i,
                    positionY
            );
            TextCharacter charactersInBackBuffer = getScreen().getBackCharacter(cellPosition);
            charactersInBackBuffer = charactersInBackBuffer.withBackgroundColor(TextColor.ANSI.BLACK);
            charactersInBackBuffer = charactersInBackBuffer.withCharacter(horizontalMarking[i]);
            getScreen().setCharacter(cellPosition, charactersInBackBuffer);
        }
    }

    private void drawVertically(int positionX, int positionY, char[] verticalMarking) {
        for(int i = 0; i< verticalMarking.length; i++){
            TerminalPosition cellPosition = new TerminalPosition(
                    positionX,
                    positionY+i
            );
            TextCharacter charactersInBackBuffer = getScreen().getBackCharacter(cellPosition);
            charactersInBackBuffer = charactersInBackBuffer.withBackgroundColor(TextColor.ANSI.BLACK);
            charactersInBackBuffer = charactersInBackBuffer.withCharacter(verticalMarking[i]);
            getScreen().setCharacter(cellPosition, charactersInBackBuffer);
        }
    }

    private char parseCharFromBoard(byte b) {
        switch (b){
            case 1:return 'x';
            case 2:return 'X';
            case -1:return 'o';
            case -2:return 'O';
        }
        return ' ';
    }

    private boolean applyMove(String move){
        byte[] validMove = validateMove(move);
        if(validMove != null){
            List<byte[]> moves = gameController.getAllValidMoves();
            List<byte[]> correspondingMoves = new ArrayList<>();
            for(byte[] by : moves){
                if(by[0] == validMove[0] && by[by.length-3] == validMove[validMove.length-3]){
                    correspondingMoves.add(by);
                }
            }
            for(byte[] by : correspondingMoves){
                for(byte b :by){
                    System.out.print(b + ", ");
                }
                System.out.println();
            }
            if(correspondingMoves.size()>=1){
                try {
                    gameController.applyMove(correspondingMoves.get(0));
                    errorTextVisible = false;
                } catch (InvalidMoveException e) {
                    e.printStackTrace();
                }
                return true;
            }else{
                errorTextVisible = true;
                return false;
            }
        }else{
            errorTextVisible = true;
            return false;
        }
    }

    private byte[] validateMove(String move){
        move = move.toLowerCase();
        char[] validDigits = "12345678".toCharArray();
        char[] validChars = "abcdefgh".toCharArray();
        String[] split = move.split("\s+");
        byte[] converted = new byte[split.length*3];
        int arrayPointer =  0;
        for(String s :split){
            if(s.length() != 2) return null;
            int  digit = -1;
            int character = -1;
            for(int i = 0; i< s.length(); i++){
                char c = s.charAt(i);
                for(int j = 0; j< 8; j++){
                    if(c == validDigits[j]){
                        digit =j;
                        break;
                    }
                    if(c == validChars[j]){
                        character = j;
                        break;
                    }
                }
            }
            //System.out.println("digit: " + digit + " character: " +character);
            if(digit != -1 && character != -1){
                byte placeOnBoar = (byte)((digit*8 + character));
                converted[arrayPointer] = placeOnBoar;
                arrayPointer++;
                converted[arrayPointer]=gameController.getBoardArr()[placeOnBoar];
                arrayPointer++;
                converted[arrayPointer]=0;
                arrayPointer++;
            }else{
                return null;
            }
        }
        return converted;
    }


    enum FrameState{
        PLAYER_MENU,
        INPUT_MENU,
        COMPUTER_MENU,
        GAME_FINISH;
    }

}
