package cz.whiterabbit.gui.frames;

import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.Screen;
import cz.whiterabbit.gui.frames.elements.GameSettings;
import cz.whiterabbit.gui.frames.elements.PlayerLevel;
import cz.whiterabbit.gui.frames.elements.PlayerOperator;
import cz.whiterabbit.gui.frames.elements.LanternaMenuItem;
import cz.whiterabbit.gui.frames.elements.LanternaToggleMenuItem;
import cz.whiterabbit.gui.listeners.ToggleMenuListener;

import java.io.IOException;
import java.util.ArrayList;

public class SettingsFrame extends LanternaFrame implements GUIFrame {
    private String whitePlayerText = "Player X";
    private String blackPlayerText = "Player O";
    private GameSettings gameSettings;

    public SettingsFrame(Screen screen, GameSettings gameSettings) {
        super(screen);
        this.gameSettings = gameSettings;
        initMenu();
    }

    private void initMenu() {
        //White Operator
        LanternaMenuItem whiteOperator = new LanternaMenuItem("X player operator", 2, 5, 0);
        whiteOperator.setToggleMenuItem(new LanternaToggleMenuItem(30, 5, new ArrayList<String>() {{
            add("PLAYER");
            add("COMPUTER_MINIMAX");
            add("COMPUTER_RANDOM");
        }}, new ToggleMenuListener() {
            @Override
            public void onToggle() {
                gameSettings.setWhiteOperator(parseOperator(whiteOperator.getToggleMenuItem().getSelectedItem()));
            }
        }));
        addMenuItem(whiteOperator);

        LanternaMenuItem whiteDifficulty = new LanternaMenuItem("X player difficulty", 2, 6, 1);
        whiteDifficulty.setToggleMenuItem(new LanternaToggleMenuItem(30, 6, new ArrayList<String>() {{
            add("EASY");
            add("MEDIUM");
            add("HARD");
        }}, new ToggleMenuListener() {
            @Override
            public void onToggle() {
                gameSettings.setWhitePlayerLevel(parseLevel(whiteDifficulty.getToggleMenuItem().getSelectedItem()));
            }
        }));
        addMenuItem(whiteDifficulty);

        LanternaMenuItem blackOperator = new LanternaMenuItem("O player operator", 2, 10, 2);
        blackOperator.setToggleMenuItem(new LanternaToggleMenuItem(30, 10, new ArrayList<String>() {{
            add("PLAYER");
            add("COMPUTER_MINIMAX");
            add("COMPUTER_RANDOM");
        }}, new ToggleMenuListener() {
            @Override
            public void onToggle() {
                gameSettings.setBlackOperator(parseOperator(blackOperator.getToggleMenuItem().getSelectedItem()));
            }
        }));
        addMenuItem(blackOperator);

        LanternaMenuItem blackDifficulty = new LanternaMenuItem("O player difficulty", 2, 11, 3);
        blackDifficulty.setToggleMenuItem(new LanternaToggleMenuItem(30, 11, new ArrayList<String>() {{
            add("EASY");
            add("MEDIUM");
            add("HARD");
        }}, new ToggleMenuListener() {
            @Override
            public void onToggle() {
                gameSettings.setBlackPlayerLevel(parseLevel(blackDifficulty.getToggleMenuItem().getSelectedItem()));
            }
        }));
        addMenuItem(blackDifficulty);
    }

    private PlayerLevel parseLevel(String selectedItem) {
        switch (selectedItem){
            case "EASY":{
                return PlayerLevel.EASY;
            } case "MEDIUM":{
                return PlayerLevel.MEDIUM;
            } case "HARD":{
                return PlayerLevel.HARD;
            }
        }
        return null;
    }

    private PlayerOperator parseOperator(String selectedItem) {
        switch (selectedItem){
            case "PLAYER":{
                return PlayerOperator.HUMAN_PLAYER;
            } case "COMPUTER_MINIMAX":{
                return PlayerOperator.COMPUTER_MINIMAX;
            } case "COMPUTER_RANDOM":{
                return PlayerOperator.COMPUTER_RANDOM;
            }
        }
        return null;
    }


    @Override
    void onDraw() {
        drawInFrame("SETTINGS", 1, 1);
        paintBorder("X PLAYER", 1, 4, 50, 5, -1);
        paintBorder("O PLAYER", 1, 9, 50, 5, -1);
        drawSimpleText("Up/Down arrow - menu navigation",3, 13);
        drawSimpleText("Left/Right arrow - change setting",3, 14);
        drawSimpleText("Enter - save settings and return",3, 15);
        drawMenu();
    }

    @Override
    void onListen() {
        KeyStroke keyStroke = null;
        try {
            keyStroke = getScreen().pollInput();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (keyStroke != null) {
            menuOnListen(keyStroke);
        }
        if(keyStroke!= null && keyStroke.getKeyType() == KeyType.Enter){
            if(getFrameListener() != null){
                getFrameListener().onBack();
            }
        }
        //menuOnListen();

    }

}
