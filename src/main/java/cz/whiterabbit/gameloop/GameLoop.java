package cz.whiterabbit.gameloop;

import cz.whiterabbit.elements.GameController;
import cz.whiterabbit.gui.ConsoleManager;
import cz.whiterabbit.keylistener.ListeningThread;

import java.util.Scanner;

public class GameLoop implements Runnable {
    //private Thread thread;
    private GameController gameController;
    private ConsoleManager consoleManager;
    private ListeningThread listeningThread;

    //GameSettings
    private GameSettings gameSettings;


    //gameLoop control
    private boolean pause = false;
    private boolean stop = false;


    public GameLoop(){
        //thread = new Thread(this);
        listeningThread = new ListeningThread("listeningThead",this);
        //listeningThread.run();

        gameSettings = new GameSettings();
        gameController = new GameController();
        consoleManager = new ConsoleManager(new Scanner(System.in));
        setConsoleManagerListener(consoleManager);
        listeningThread.startListeningThread();
        listeningThread.stopListening();
    }

    private void setConsoleManagerListener(ConsoleManager consoleManager){
        consoleManager.setCommandListener(new CommandListener() {
            @Override
            public void onChangeSettings() {
                consoleManager.promptForInitialSettings(gameSettings);
            }

            @Override
            public void onPrintAllMoves() {
                System.out.println("Print all moves - not yet implemented needs parser");
            }

            @Override
            public void onPrintBestMove() {
                System.out.println("Print best move - not yet implemented");
            }

            @Override
            public void onGameResume() {
                pause = false;
            }

            @Override
            public void onGameRestart() {
                gameController.startGame();
                consoleManager.promptForInitialSettings(gameSettings);
            }
        });
    }

    @Override
    public void run() {
        while (true){
            listeningThread.stopListening();
            gameController.startGame();
            consoleManager.promptForInitialSettings(gameSettings);

            System.out.println("Prompt response :  \n"
            + "player_1 name: " + gameSettings.getWhitePlayerName() + "\n" +
                    "player_2 name: " + gameSettings.getBlackPlayerName() + "\n" +
                    "player_1 operator: " + gameSettings.getWhiteOperator() + "\n" +
                    "player_2 operator: " + gameSettings.getBlackOperator() + "\n" +
                    "player_1 level: " + gameSettings.getWhitePlayerLevel() + "\n" +
                    "player_2 level: " + gameSettings.getBlackPlayerLevel());

            listeningThread.resumeListening();
            //todo temporary, change after game loop implemented
            int i = 0;
            //game loop
            while(true){

                i++;
                consoleManager.writeSymbol(i);

                try {
                    synchronized (this){
                        Thread.sleep(100);
                        if (pause){
                            consoleManager.promptForCommand();
                            listeningThread.resumeListening();
                            //wait();
                        }
                        if(stop){
                            return;
                        }
                    }
                }catch (InterruptedException e){
                    e.printStackTrace();
                }


            }
        }
    }

    public synchronized void pauseWritingThread(){
        pause = true;
        notify();
    }

    public synchronized void resumeWritingThread(){
        pause = false;
        notify();
    }

    public synchronized void stopWritingThread(){
        stop = true;
        pause = false;
        notify();
    }

}
