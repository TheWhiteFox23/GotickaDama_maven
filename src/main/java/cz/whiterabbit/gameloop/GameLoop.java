package cz.whiterabbit.gameloop;

import cz.whiterabbit.elements.GameController;
import cz.whiterabbit.gui.ConsolePrinter;

public class GameLoop implements Runnable {
    GameController gameController;

    //GameSettings
    private GameSettings gameSettings;

    public GameLoop(){
        gameController = new GameController();
    }

    @Override
    public void run() {
        while (true){
            ConsolePrinter consolePrinter = new ConsolePrinter();
            gameController.startGame();
            gameSettings = consolePrinter.promptForInitialSettings();

            System.out.println("Prompt response :  \n"
            + "player_1 name: " + gameSettings.getWhitePlayerName() + "\n" +
                    "player_2 name: " + gameSettings.getBlackPlayerName() + "\n" +
                    "player_1 operator: " + gameSettings.getWhiteOperator() + "\n" +
                    "player_2 operator: " + gameSettings.getBlackOperator() + "\n" +
                    "player_1 level: " + gameSettings.getWhitePlayerLevel() + "\n" +
                    "player_2 level: " + gameSettings.getBlackPlayerLevel());
        }

    }

}
