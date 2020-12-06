package cz.whiterabbit.gui;

import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import cz.whiterabbit.elements.GameController;
import cz.whiterabbit.elements.GameState;
import cz.whiterabbit.gui.frames.elements.GameSettings;
import cz.whiterabbit.gui.frames.GameLoopFrame;
import cz.whiterabbit.gui.frames.MenuFrame;
import cz.whiterabbit.gui.frames.SettingsFrame;
import cz.whiterabbit.gui.listeners.FrameListener;

import java.io.IOException;

public class GUIController {
    //Frames
    private MenuFrame menuFrame;
    private GameLoopFrame gameLoopFrame;
    private SettingsFrame settingsFrame;

    //Frame control
    private GameFrame previousFrame = GameFrame.MAIN_MENU_FRAME;
    private GameFrame activeFrame = GameFrame.MAIN_MENU_FRAME;

    //Game Components
    private GameController gameController;
    private GameSettings gameSettings;

    //Lanterna
    private Screen screen;

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
        menuFrame = new MenuFrame(screen, gameController);
        gameLoopFrame = new GameLoopFrame(screen, gameController, gameSettings); //game controller and game settings will be injected
        settingsFrame = new SettingsFrame(screen, gameSettings); //game settings will be injected
    }

    private void switchFrame(GameFrame gameFrame){
        previousFrame = activeFrame;
        activeFrame = gameFrame;
        screen.clear();
        try{
            switch (gameFrame){
                case GAME_FRAME -> gameLoopFrame.drawFrame();
                case SETTINGS_FRAME -> settingsFrame.drawFrame();
                case MAIN_MENU_FRAME -> menuFrame.drawFrame();
            }
        }catch (IOException e){
            System.out.println("Unable to draw the frame :" + e.toString());
        }


    }

    private void initializeListeners() {
        menuFrame.setFrameListener(new FrameListener() {
            @Override
            public void onSettings() {
                switchFrame(GameFrame.SETTINGS_FRAME);
            }

            @Override
            public void onNewGame() {
                gameLoopFrame.startNewGame();
                switchFrame(GameFrame.GAME_FRAME);
            }

            @Override
            public void onMenu() { }

            @Override
            public void onContinue() {
                switchFrame(GameFrame.GAME_FRAME);
            }

            @Override
            public void onBack() {

            }
        });

        settingsFrame.setFrameListener(new FrameListener() {
            @Override
            public void onSettings() { }

            @Override
            public void onNewGame() { }

            @Override
            public void onMenu() { }

            @Override
            public void onContinue() { }

            @Override
            public void onBack() {
                switchFrame(previousFrame);
            }
        });

        gameLoopFrame.setFrameListener(new FrameListener() {
            @Override
            public void onSettings() {
                switchFrame(GameFrame.SETTINGS_FRAME);
            }

            @Override
            public void onNewGame() { }

            @Override
            public void onMenu() {
                switchFrame(GameFrame.MAIN_MENU_FRAME);
            }

            @Override
            public void onContinue() { }

            @Override
            public void onBack() { }
        });

    }

    enum GameFrame{
        MAIN_MENU_FRAME,
        GAME_FRAME,
        SETTINGS_FRAME
    }
}
