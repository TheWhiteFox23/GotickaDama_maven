package cz.whiterabbit.gui;

import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.screen.Screen;
import cz.whiterabbit.gameloop.GameSettings;
import org.w3c.dom.Text;

import javax.swing.*;
import java.io.IOException;
import java.util.ArrayList;

public class SettingsFrame extends LanternaFrame implements GUIFrame {
    private String whitePlayerText = "White player";
    private String blackPlayerText = "Black player";
    private GameSettings gameSettings;

    public SettingsFrame(Screen screen, GameSettings gameSettings) {
        super(screen);
        this.gameSettings = gameSettings;
        initMenu();
    }

    private void initMenu() {
        //White Operator
        LanternaMenuItem whiteOperator = new LanternaMenuItem("White player operator", 2, 5, 0);
        whiteOperator.setMenuItemListener(new MenuItemListener() {
            @Override
            public void onSelect() {
                System.out.println("White player operator");
            }
        });
        whiteOperator.setToggleMenuItem(new LanternaToggleMenuItem(30, 5, new ArrayList<>() {{
            add("PLAYER");
            add("COMPUTER_MINIMAX");
            add("COMPUTER_RANDOM");
        }}));
        addMenuItem(whiteOperator);

        LanternaMenuItem whiteDifficulty = new LanternaMenuItem("White player difficulty", 2, 6, 1);
        whiteDifficulty.setMenuItemListener(new MenuItemListener() {
            @Override
            public void onSelect() {
                System.out.println("White player difficulty");
            }
        });
        whiteDifficulty.setToggleMenuItem(new LanternaToggleMenuItem(30, 6, new ArrayList<>() {{
            add("EASY");
            add("MEDIUM");
            add("HARD");
        }}));
        addMenuItem(whiteDifficulty);

        LanternaMenuItem blackOperator = new LanternaMenuItem("Black player operator", 2, 10, 2);
        blackOperator.setMenuItemListener(new MenuItemListener() {
            @Override
            public void onSelect() {
                System.out.println("Black player operator");
            }
        });
        blackOperator.setToggleMenuItem(new LanternaToggleMenuItem(30, 10, new ArrayList<>() {{
            add("PLAYER");
            add("COMPUTER_MINIMAX");
            add("COMPUTER_RANDOM");
        }}));
        addMenuItem(blackOperator);

        LanternaMenuItem blackDifficulty = new LanternaMenuItem("Black player difficulty", 2, 11, 3);
        blackDifficulty.setMenuItemListener(new MenuItemListener() {
            @Override
            public void onSelect() {
                System.out.println("Black player difficulty");
            }
        });
        blackDifficulty.setToggleMenuItem(new LanternaToggleMenuItem(30, 11, new ArrayList<>() {{
            add("EASY");
            add("MEDIUM");
            add("HARD");
        }}));
        addMenuItem(blackDifficulty);
    }


    @Override
    void onDraw() {
        drawInFrame("SETTINGS", 1, 1);
        paintBorder("WHITE", 1, 4, 50, 5, -1);
        paintBorder("BLACK", 1, 9, 50, 5, -1);
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
        //menuOnListen();

    }

    @Override
    public void setFrameListener(FrameListener frameListener) {

    }
}
