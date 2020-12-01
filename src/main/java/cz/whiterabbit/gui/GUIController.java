package cz.whiterabbit.gui;

import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import cz.whiterabbit.elements.GameController;
import cz.whiterabbit.gui.frames.elements.GameSettings;
import cz.whiterabbit.gui.frames.GameLoopFrame;
import cz.whiterabbit.gui.frames.MenuFrame;
import cz.whiterabbit.gui.frames.SettingsFrame;
import cz.whiterabbit.gui.listeners.FrameListener;

import java.io.IOException;

public class GUIController {
    //Frames
    MenuFrame menuFrame;
    GameLoopFrame gameLoopFrame;
    SettingsFrame settingsFrame;

    //Game Components
    GameController gameController;
    GameSettings gameSettings;

    //Lanterna
    Screen screen;

    public GUIController() throws IOException {
        initialize();
        initializeListeners();

        menuFrame.drawFrame();
    }

    private void initialize() throws IOException {
        //Lanterna

        screen = new DefaultTerminalFactory().createScreen();
        screen.startScreen();
        screen.setCursorPosition(null);

        //Game Elements
        gameController = new GameController();
        gameSettings = new GameSettings();
        //Frames
        menuFrame = new MenuFrame(screen);
        gameLoopFrame = new GameLoopFrame(); //game controller and game settings will be injected
        settingsFrame = new SettingsFrame(screen, gameSettings); //game settings will be injected
    }

    private void initializeListeners() {
        menuFrame.setFrameListener(new FrameListener() {
            @Override
            public void onSettings() {
                try {
                    screen.clear();
                    settingsFrame.drawFrame();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                //menuFrame.showResponseText("SETTINGS option selected");
            }

            @Override
            public void onNewGame() {
                menuFrame.showResponseText("NEW GAME option selected");
            }

            @Override
            public void onMenu() {

            }

            @Override
            public void onContinue() {
                menuFrame.showResponseText("CONTINUE option selected");
            }
        });

        settingsFrame.setFrameListener(new FrameListener() {
            @Override
            public void onSettings() {

            }

            @Override
            public void onNewGame() {

            }

            @Override
            public void onMenu() {
                try {
                    screen.clear();
                    menuFrame.drawFrame();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onContinue() {

            }
        });
    }

}
