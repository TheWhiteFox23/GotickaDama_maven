package cz.whiterabbit.gui.frames;

import com.googlecode.lanterna.SGR;
import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TextCharacter;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.Screen;
import cz.whiterabbit.elements.ComputerPlayer;
import cz.whiterabbit.elements.GameController;
import cz.whiterabbit.elements.InvalidMoveException;
import cz.whiterabbit.elements.MinimaxComputerPlayer;
import cz.whiterabbit.elements.RandomComputerPlayer;
import cz.whiterabbit.gui.frames.elements.GameSettings;
import cz.whiterabbit.gui.frames.elements.PlayerOperator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameLoopFrame extends LanternaFrame implements GUIFrame {
    private GameController gameController;
    private GameSettings gameSettings;

    private String promptText = "Please enter your move(initial and final position): ";
    private String ErrorMessageString = "MOVE IS INVALID PLEASE TRY AGAIN";
    private String moveString = "";

    private boolean promptForMove = false;
    private boolean errorTextVisible = false;
    private boolean continueGame = true;
    private boolean insertMoveAvailable = true;

    private FrameState frameState;

    //move highlight
    private byte[] lastMove = null;
    private boolean highlightMove = false;

    //move selection menu
    private List<byte[]> movesList = null;
    private boolean multipleMoveSelection = false;
    private int selectedIndex;

    //Computer Players
    private ComputerPlayer randomComputerPlayer;
    private ComputerPlayer minimaxComputerPlayer;


    public GameLoopFrame(Screen screen, GameController gameController, GameSettings gameSettings) {
        super(screen);
        this.gameController = gameController;
        this.gameSettings = gameSettings;
        this.frameState = getFrameState();
        this.randomComputerPlayer = new RandomComputerPlayer();
        this.minimaxComputerPlayer = new MinimaxComputerPlayer();
    }

    /**
     * Return FrameState based on currant player and its operator
     *
     * @return
     */
    private FrameState getFrameState() {
        //PlayerOperator operator;
        boolean positivePlayerOnMOve = gameController.isPlayerType();
        PlayerOperator operator = getPlayerOperator(positivePlayerOnMOve);
        if (!gameController.canContinue()) return FrameState.GAME_FINISH;

        switch (operator) {
            case HUMAN_PLAYER: {
                if (promptForMove) {
                    return FrameState.INPUT_MENU;
                }else if(multipleMoveSelection){
                    return FrameState.MOVE_SELECTION_MENU;
                } else {
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

    @Override
    void onDraw() {
        if (gameController.canContinue()) {
            frameState = getFrameState();
            switch (frameState) {
                case INPUT_MENU -> drawInputMenu();
                case PLAYER_MENU -> drawPlayerMenu();
                case COMPUTER_MENU -> drawComputerMenu();
                case MOVE_SELECTION_MENU -> drawMoveSelectionMenu();
            }
            if (continueGame) {
                manageMoveInput(gameController.isPlayerType());
            }

        } else {
            frameState = getFrameState();
            switch (gameController.getGameState()) {
                case DRAW -> drawFinishMenu("DRAW");
                case POSITIVE_WIN -> drawFinishMenu("PLAYER X WINS");
                case NEGATIVE_WIN -> drawFinishMenu("PLAYER O WINS");
            }
        }
        if (lastMove!= null) drawBoard(2, 1, lastMove);


    }

    private void drawMoveSelectionMenu() {
        drawBoard(gameController.getBoardArr(), 2, 1);
        drawStatistics();
        drawSimpleText("E - navigate back", 3,18);
        drawSimpleText("up/down arrow - navigate in moves", 3,19);
        drawSimpleText("Enter - confirm move selection", 3,20);
        drawBorder("Available moves", 3, 11, 47, 8);
        drawMovesList(3,11, 6);
    }

    private void drawMovesList(int positionX, int positionY, int height) {
        if(movesList.size() <= height){
            for(int i = 0; i< movesList.size(); i++){
                if(i == selectedIndex){
                    drawColoredText(parseMove(movesList.get(i)), positionX, positionY + i,
                            TextColor.ANSI.WHITE, TextColor.ANSI.GREEN);
                    lastMove = movesList.get(i);
                }else{
                    drawSimpleText(parseMove(movesList.get(i)), positionX, positionY + i);
                }
            }
        }else if(movesList.size()>=height && (selectedIndex + height) >= movesList.size()){
            for(int i = movesList.size()-height; i< movesList.size(); i++){
                if(i == selectedIndex){
                    drawColoredText(parseMove(movesList.get(i)), positionX, positionY + (i-movesList.size())+height,
                            TextColor.ANSI.WHITE, TextColor.ANSI.GREEN);
                    lastMove = movesList.get(i);
                }else{
                    drawSimpleText(parseMove(movesList.get(i)), positionX, positionY + ((i-movesList.size())+height));
                }
            }
        }else if(movesList.size() > height && (selectedIndex + height)< movesList.size()){
            for(int i = selectedIndex; i< selectedIndex+height; i++){
                if(i == selectedIndex){
                    drawColoredText(parseMove(movesList.get(i)), positionX, positionY + (i-selectedIndex),
                            TextColor.ANSI.WHITE, TextColor.ANSI.GREEN);
                    lastMove = movesList.get(i);
                }else{
                    drawSimpleText(parseMove(movesList.get(i)), positionX, positionY + (i-selectedIndex));
                }
            }
        }
    }

    private void drawInputMenu() {
        drawBoard(gameController.getBoardArr(), 2, 1);
        drawSimpleText(promptText, 3, 20);
        if (errorTextVisible) drawSimpleText(ErrorMessageString, 3, 19, SGR.BOLD);
        drawSimpleText("press ENTER to confirm", 3, 21);
        drawSimpleText(moveString, promptText.length() + 2, 20);
        //drawSimpleText("Player on move : " + getPlayerOnMove(), 20, 3);
        drawStatistics();
    }

    private String getPlayerOnMove() {
        if (gameController.isPlayerType()) {
            return "X";
        } else {
            return "O";
        }
    }

    private void drawComputerMenu() {
        drawBoard(gameController.getBoardArr(), 2, 1);
        drawSimpleText("S - Change Settings", 3, 20);
        drawSimpleText("C - Continue", 3, 18);
        drawSimpleText("M - Main Menu", 3, 21);
        //drawSimpleText("Player on move : " + getPlayerOnMove(), 20, 3);
        drawStatistics();
    }

    private void drawPlayerMenu() {
        if(gameController.getAllValidMoves().size() == 0)insertMoveAvailable = false;
        drawBoard(gameController.getBoardArr(), 2, 1);
        drawSimpleText("S - Change Settings", 3, 20);
        if (insertMoveAvailable) drawSimpleText("L - Select moves from list of valid moves", 3, 17);
        if (insertMoveAvailable) drawSimpleText("I - Insert Move", 3, 19);
        if (!insertMoveAvailable) drawSimpleText("C - Continue", 3, 18);
        drawSimpleText("M - Main Menu", 3, 21);
        //drawSimpleText("Player on move : " + getPlayerOnMove(), 20, 3);
        drawStatistics();

    }

    //todo adjust this method
    private void drawFinishMenu(String text) {
        getScreen().clear();
        drawBoard(gameController.getBoardArr(), 2, 1);
        //drawSimpleText("GAME FINISH : " + text, 3, 20);
        drawBorder("", 20, 5, text.length() + 4, 3);
        drawSimpleText(text, 21, 5);
        drawSimpleText("M - Main Menu", 3, 21);
        lastMove =  null;
    }

    private void drawStatistics(){
        //statistics
        drawBorder("Statistics", 20, 1, 30,5);
        drawSimpleText("Player on move : " + getPlayerOnMove(), 20, 1);
        if(lastMove != null)drawSimpleText("Applied move : " + parseMove(lastMove), 20,2);
        drawSimpleText("Available moves: " + gameController.getAllValidMoves().size(), 20,3);

        //legend
        drawBorder("Legend", 20, 6, 30,5);
        highLightCell(21,7,TextColor.ANSI.GREEN);
        drawSimpleText(" - Initial position", 22, 6);
        highLightCell(21,8,TextColor.ANSI.CYAN);
        drawSimpleText(" - Landing position/s", 22, 7);
        highLightCell(21,9,TextColor.ANSI.MAGENTA);
        drawSimpleText(" - Captured enemies", 22, 8);
    }



    private String parseMove(byte[] move){
        String toReturn = "";
        toReturn += parsePosition(move[0]) + " ";
        for (int i = 0; i < move.length; i += 3) {
            if (move[i + 2] == move[1]) {
                toReturn += parsePosition(move[i]) + " ";
            }
        }

        return toReturn;
    }

    private String parsePosition(byte position){
        int character = position%8;
        int digit = position/8;
        char[] verticalMarking = "12345678".toCharArray();
        char[] horizontalMarking = "ABCDEFGH".toCharArray();
        return "" + verticalMarking[digit] + horizontalMarking[character];
    }

    private void manageMoveInput(boolean positiveOnMove) {
        switch (getPlayerOperator(positiveOnMove)) {
            case HUMAN_PLAYER:{
                break;
            }
            case COMPUTER_MINIMAX: {
                applyPlayerMove(minimaxComputerPlayer);
                break;
            }
            case COMPUTER_RANDOM: {
                applyPlayerMove(randomComputerPlayer);
            }
        }
    }

    private PlayerOperator getPlayerOperator(boolean positiveOnMove) {
        PlayerOperator operator;
        if (positiveOnMove) {
            operator = gameSettings.getWhiteOperator();
        } else {
            operator = gameSettings.getBlackOperator();
        }
        return operator;
    }

    private void applyPlayerMove(ComputerPlayer computerPlayer) {
        try {
            byte[] move = computerPlayer.chooseMove(gameController.getBoardArr(), gameController.isPlayerType());
            lastMove = move;
            highlightMove = true;
            gameController.applyMove(move);
        } catch (InvalidMoveException e) {
            e.printStackTrace();
        }
        continueGame = false;
        invalidate();
    }


    @Override
    void onListen() {
        KeyStroke keyStroke = null;
        try {
            keyStroke = getScreen().pollInput();
        } catch (IOException e) {
            e.printStackTrace();
        }
        switch (frameState) {
            case INPUT_MENU -> listenToInputMenu(keyStroke);
            case PLAYER_MENU -> listenToPlayerMenu(keyStroke);
            case COMPUTER_MENU -> listenToComputerMenu(keyStroke);
            case GAME_FINISH -> listenToGameFinishMenu(keyStroke);
            case MOVE_SELECTION_MENU -> listenToMoveSelectionMenu(keyStroke);
        }
    }

    private void listenToMoveSelectionMenu(KeyStroke keyStroke) {
        if (keyStroke != null && (keyStroke.getKeyType() != KeyType.EOF || keyStroke.getKeyType() != KeyType.Escape)) {
            if (keyStroke.getKeyType() == KeyType.Character && keyStroke.getCharacter() == 'e') {
                lastMove = null;
                multipleMoveSelection = false;
                getScreen().clear();
                invalidate();
            }else if(keyStroke.getKeyType() == KeyType.ArrowUp){
                if(selectedIndex == 0){
                    selectedIndex = movesList.size()-1;
                }else{
                    selectedIndex--;
                }
                invalidate();
                //System.out.println("selected index: " + selectedIndex + " object: " +movesList.get(selectedIndex));
            }else if(keyStroke.getKeyType() == KeyType.ArrowDown){
                selectedIndex = (selectedIndex+1)%movesList.size();
                invalidate();
                //System.out.println("selected index: " + selectedIndex + " object: " +movesList.get(selectedIndex));
            }else if(keyStroke.getKeyType() == KeyType.Enter){
                try {
                    gameController.applyMove(movesList.get(selectedIndex));
                    insertMoveAvailable = false;
                    multipleMoveSelection = false;
                    getScreen().clear();
                    invalidate();
                } catch (InvalidMoveException e) {
                    e.printStackTrace();
                }
                //todo add confirm move logic
            }
        }
    }

    private void listenToGameFinishMenu(KeyStroke keyStroke) {
        if (keyStroke != null && (keyStroke.getKeyType() != KeyType.EOF || keyStroke.getKeyType() != KeyType.Escape)) {
            if (keyStroke.getKeyType() == KeyType.Character && keyStroke.getCharacter() == 'm') {
                if (getFrameListener() != null) getFrameListener().onMenu();
                gameController.resetGame();
                invalidate();
            }
        }
    }

    private void listenToComputerMenu(KeyStroke keyStroke) {
        if (keyStroke != null && (keyStroke.getKeyType() != KeyType.EOF || keyStroke.getKeyType() != KeyType.Escape)) {
            if (keyStroke.getKeyType() == KeyType.Character && keyStroke.getCharacter() == 's') {
                if (getFrameListener() != null) getFrameListener().onMenu();
            } else if (keyStroke.getKeyType() == KeyType.Character && keyStroke.getCharacter() == 'c') {
                continueLoop();
            } else if (keyStroke.getKeyType() == KeyType.Character && keyStroke.getCharacter() == 'm') {
                if (getFrameListener() != null) getFrameListener().onMenu();
            }
        }
    }

    private void listenToPlayerMenu(KeyStroke keyStroke) {
        if (keyStroke != null && (keyStroke.getKeyType() != KeyType.EOF || keyStroke.getKeyType() != KeyType.Escape)) {
            if (keyStroke.getKeyType() == KeyType.Character && keyStroke.getCharacter() == 'i' && insertMoveAvailable) {
                promptForMove = true;
                getScreen().clear();
                invalidate();
            } else if (keyStroke.getKeyType() == KeyType.Character && keyStroke.getCharacter() == 's') {
                if (getFrameListener() != null) getFrameListener().onSettings();
            } else if (keyStroke.getKeyType() == KeyType.Character && keyStroke.getCharacter() == 'c' && !insertMoveAvailable) {
                continueLoop();
            } else if (keyStroke.getKeyType() == KeyType.Character && keyStroke.getCharacter() == 'm') {
                if (getFrameListener() != null) getFrameListener().onMenu();
            }else if(keyStroke.getKeyType() == KeyType.Character && keyStroke.getCharacter() == 'l' && insertMoveAvailable){
                multipleMoveSelection = true;
                movesList = gameController.getAllValidMoves();
                selectedIndex = 0;
                getScreen().clear();
                invalidate();
            }
        }
    }

    private void listenToInputMenu(KeyStroke keyStroke) {
        if (keyStroke != null && (keyStroke.getKeyType() != KeyType.EOF || keyStroke.getKeyType() != KeyType.Escape)) {
            if (keyStroke.getKeyType() == KeyType.Character) {
                moveString += keyStroke.getCharacter();
                invalidate();
            } else if (keyStroke.getKeyType() == KeyType.Backspace) {
                if (moveString.length() > 0) {
                    moveString = moveString.substring(0, moveString.length() - 1);
                    getScreen().clear();
                    invalidate();
                }
            } else if (keyStroke.getKeyType() == KeyType.Enter) {
                if (applyMove(moveString)) {
                    moveString = "";
                    getScreen().clear();
                    promptForMove = false;
                    invalidate();
                    continueGame = false;
                    insertMoveAvailable = false;
                } else {
                    invalidate();
                    getScreen().clear();
                    moveString = "";
                }
            }
        }

    }

    private void continueLoop() {
        //game control
        gameController.switchPlayerType();
        //GUI
        continueGame = true;
        insertMoveAvailable = true;
        lastMove = null;
        getScreen().clear();
        invalidate();
    }

    private void drawBoard(byte[] board, int positionX, int positionY) {
        //Draw board markings
        char[] verticalMarking = "12345678".toCharArray();
        char[] horizontalMarking = "ABCDEFGH".toCharArray();
        drawHorizontally(positionX + 1, positionY, horizontalMarking);
        drawHorizontally(positionX + 1, positionY + 9, horizontalMarking);
        drawVertically(positionX, positionY + 1, verticalMarking);
        drawVertically(positionX + 9, positionY + 1, verticalMarking);
        positionY += 1;
        positionX += 1;
        for (int i = 0; i < board.length; i++) {
            TerminalPosition cellPosition = new TerminalPosition(
                    positionX + (i % 8),
                    positionY + (i / 8)
            );
            TextColor.ANSI textColor = TextColor.ANSI.WHITE;
            TextColor.ANSI backgroundColor = TextColor.ANSI.BLACK;
            if ((i / 8) % 2 == 0 && i % 2 == 0 || (i / 8) % 2 != 0 && i % 2 != 0) {
                textColor = TextColor.ANSI.BLACK;
                backgroundColor = TextColor.ANSI.WHITE;
            }
            TextCharacter charactersInBackBuffer = getScreen().getBackCharacter(cellPosition);
            charactersInBackBuffer = charactersInBackBuffer.withBackgroundColor(backgroundColor);
            charactersInBackBuffer = charactersInBackBuffer.withCharacter(parseCharFromBoard(board[i]));
            charactersInBackBuffer = charactersInBackBuffer.withForegroundColor(textColor);
            getScreen().setCharacter(cellPosition, charactersInBackBuffer);
        }
        //invalidate();
    }

    private void drawBoard(int positionX, int positionY, byte[] moveToHighlight) {
        boolean playerPositive = moveToHighlight[1] > 0;
        //drawBoard(board, positionX, positionY);
        positionX++;
        positionY++;
        //Initial Position
        highLightCell(
                positionX + (moveToHighlight[0] % 8),
                positionY + (moveToHighlight[0] / 8),
                TextColor.ANSI.GREEN);
        for (int i = 0; i < moveToHighlight.length; i += 3) {
            //System.out.print(moveToHighlight[i] + ", ");
            //initial position
            if (moveToHighlight[i + 2] == moveToHighlight[1]) {
                highLightCell(
                        positionX + (moveToHighlight[i] % 8),
                        positionY + (moveToHighlight[i] / 8),
                         TextColor.ANSI.CYAN);
            } else if ((playerPositive && moveToHighlight[i + 1] < 0) ||
                    (!playerPositive && moveToHighlight[i + 1] > 0)) {
                highLightCell(
                        positionX + (moveToHighlight[i] % 8),
                        positionY + (moveToHighlight[i] / 8),
                        TextColor.ANSI.MAGENTA);
            }
        }
    }

    private void highLightCell(int positionX, int positionY, TextColor.ANSI backgroundColor) {
        TerminalPosition cellPosition = new TerminalPosition(positionX, positionY);
        TextColor.ANSI textColor = TextColor.ANSI.BLACK;

        TextCharacter charactersInBackBuffer = getScreen().getBackCharacter(cellPosition);
        charactersInBackBuffer = charactersInBackBuffer.withBackgroundColor(backgroundColor);
        charactersInBackBuffer = charactersInBackBuffer.withForegroundColor(textColor);
        getScreen().setCharacter(cellPosition, charactersInBackBuffer);
    }

    private void drawHorizontally(int positionX, int positionY, char[] horizontalMarking) {
        for (int i = 0; i < horizontalMarking.length; i++) {
            TerminalPosition cellPosition = new TerminalPosition(
                    positionX + i,
                    positionY
            );
            TextCharacter charactersInBackBuffer = getScreen().getBackCharacter(cellPosition);
            charactersInBackBuffer = charactersInBackBuffer.withBackgroundColor(TextColor.ANSI.BLACK);
            charactersInBackBuffer = charactersInBackBuffer.withCharacter(horizontalMarking[i]);
            getScreen().setCharacter(cellPosition, charactersInBackBuffer);
        }
    }

    private void drawVertically(int positionX, int positionY, char[] verticalMarking) {
        for (int i = 0; i < verticalMarking.length; i++) {
            TerminalPosition cellPosition = new TerminalPosition(
                    positionX,
                    positionY + i
            );
            TextCharacter charactersInBackBuffer = getScreen().getBackCharacter(cellPosition);
            charactersInBackBuffer = charactersInBackBuffer.withBackgroundColor(TextColor.ANSI.BLACK);
            charactersInBackBuffer = charactersInBackBuffer.withCharacter(verticalMarking[i]);
            getScreen().setCharacter(cellPosition, charactersInBackBuffer);
        }
    }

    private char parseCharFromBoard(byte b) {
        switch (b) {
            case 1:
                return 'x';
            case 2:
                return 'X';
            case -1:
                return 'o';
            case -2:
                return 'O';
        }
        return ' ';
    }

    private boolean applyMove(String move) {
        byte[] validMove = validateMove(move);
        if (validMove != null) {
            List<byte[]> moves = gameController.getAllValidMoves();
            List<byte[]> correspondingMoves = new ArrayList<>();
            for (byte[] by : moves) {
                if (by[0] == validMove[0] && by[by.length - 3] == validMove[validMove.length - 3]) {
                    correspondingMoves.add(by);
                }
            }
            if (correspondingMoves.size() == 1) {
                try {
                    gameController.applyMove(correspondingMoves.get(0));
                    errorTextVisible = false;
                    lastMove = correspondingMoves.get(0);
                } catch (InvalidMoveException e) {
                    e.printStackTrace();
                }
                return true;
            } else if (correspondingMoves.size() == 0) {
                errorTextVisible = true;
                return false;
            }else if(correspondingMoves.size() >1){
                movesList=correspondingMoves;
                multipleMoveSelection = true;
                selectedIndex = 0;
                getScreen().clear();
                invalidate();
                return true;
            }
        } else {
            errorTextVisible = true;
            return false;
        }
        return false;
    }

    private byte[] validateMove(String move) {
        move = move.toLowerCase();
        char[] validDigits = "12345678".toCharArray();
        char[] validChars = "abcdefgh".toCharArray();
        String[] split = move.split("\s+");
        byte[] converted = new byte[split.length * 3];
        int arrayPointer = 0;
        for (String s : split) {
            if (s.length() != 2) return null;
            int digit = -1;
            int character = -1;
            for (int i = 0; i < s.length(); i++) {
                char c = s.charAt(i);
                for (int j = 0; j < 8; j++) {
                    if (c == validDigits[j]) {
                        digit = j;
                        break;
                    }
                    if (c == validChars[j]) {
                        character = j;
                        break;
                    }
                }
            }
            //System.out.println("digit: " + digit + " character: " +character);
            if (digit != -1 && character != -1) {
                byte placeOnBoar = (byte) ((digit * 8 + character));
                converted[arrayPointer] = placeOnBoar;
                arrayPointer++;
                converted[arrayPointer] = gameController.getBoardArr()[placeOnBoar];
                arrayPointer++;
                converted[arrayPointer] = 0;
                arrayPointer++;
            } else {
                return null;
            }
        }
        return converted;
    }

    public void startNewGame() {
        gameController.startGame();
    }


    enum FrameState {
        PLAYER_MENU,
        INPUT_MENU,
        COMPUTER_MENU,
        GAME_FINISH,
        MOVE_SELECTION_MENU,
    }

}
