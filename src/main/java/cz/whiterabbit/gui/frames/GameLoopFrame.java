package cz.whiterabbit.gui.frames;

import com.googlecode.lanterna.SGR;
import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TextCharacter;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.Screen;
import com.sun.source.tree.BreakTree;
import cz.whiterabbit.elements.GameController;
import cz.whiterabbit.elements.InvalidMoveException;
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


    public GameLoopFrame(Screen screen, GameController gameController, GameSettings gameSettings) {
        super(screen);
        this.gameController = gameController;
        this.gameSettings = gameSettings;
        this.frameState = getFrameState();
    }

    /**
     * Return FrameState based on currant player and its operator
     *
     * @return
     */
    private FrameState getFrameState() {
        PlayerOperator operator;
        boolean positivePlayerOnMOve = gameController.isPlayerType();
        if (positivePlayerOnMOve) {
            operator = gameSettings.getWhiteOperator();
        } else {
            operator = gameSettings.getBlackOperator();
        }

        if (!gameController.canContinue()) return FrameState.GAME_FINISH;

        switch (operator) {
            case HUMAN_PLAYER: {
                if (promptForMove) {
                    return FrameState.INPUT_MENU;
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
            }
            if (continueGame) {
                manageMoveInput(gameController.isPlayerType());
                //todo - move highlight

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
        drawBoard(gameController.getBoardArr(), 2, 1);
        drawSimpleText("S - Change Settings", 3, 20);
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
        drawBorder("Legend", 20, 7, 30,5);
        highLightCell(21,8,TextColor.ANSI.GREEN);
        drawSimpleText(" - Initial position" + getPlayerOnMove(), 22, 7);
        highLightCell(21,9,TextColor.ANSI.CYAN);
        drawSimpleText(" - Landing position/s" + getPlayerOnMove(), 22, 8);
        highLightCell(21,10,TextColor.ANSI.MAGENTA);
        drawSimpleText(" - Captured enemies" + getPlayerOnMove(), 22, 9);


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
        PlayerOperator operator;
        if (positiveOnMove) {
            operator = gameSettings.getWhiteOperator();
        } else {
            operator = gameSettings.getBlackOperator();
        }

        switch (operator) {
            case HUMAN_PLAYER:
            case COMPUTER_MINIMAX: {
                //todo applyMove(playerMinimax.chooseTheMove(moves))
                break;
            }
            case COMPUTER_RANDOM: {
                try {
                    byte[] move = chooseRandomMove(gameController.getAllValidMoves());
                    //drawBoard(gameController.getBoardArr(), 2,1 ,move);
                    lastMove = move;
                    highlightMove = true;
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
        switch (frameState) {
            case INPUT_MENU -> listenToInputMenu(keyStroke);
            case PLAYER_MENU -> listenToPlayerMenu(keyStroke);
            case COMPUTER_MENU -> listenToComputerMenu(keyStroke);
            case GAME_FINISH -> listenToGameFinishMenu(keyStroke);
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
            for (byte[] by : correspondingMoves) {
                for (byte b : by) {
                    System.out.print(b + ", ");
                }
                System.out.println();
            }
            if (correspondingMoves.size() >= 1) {
                try {
                    gameController.applyMove(correspondingMoves.get(0));
                    errorTextVisible = false;
                    lastMove = correspondingMoves.get(0);
                } catch (InvalidMoveException e) {
                    e.printStackTrace();
                }
                return true;
            } else {
                errorTextVisible = true;
                return false;
            }
        } else {
            errorTextVisible = true;
            return false;
        }
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
        GAME_FINISH;
    }

}
