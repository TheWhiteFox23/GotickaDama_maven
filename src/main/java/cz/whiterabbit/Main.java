package cz.whiterabbit;

import cz.whiterabbit.gui.GUIController;

import java.io.IOException;


public class Main {

    public static void main(String[] args) {

        /*WritingThread writingThread = new WritingThread("Writing Thread 1");
        ListeningThread listeningThread = new ListeningThread("Listenig Thread 1", writingThread);
        writingThread.runWritingThread();

        try{
            writingThread.getThread().join();
            listeningThread.getThread().join();
        }catch (InterruptedException e){
            System.out.println(e.toString());
        }*/
        /*GameLoop gameLoop = new GameLoop();
        gameLoop.run();*/

        try {
            GUIController guiController = new GUIController();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }


}
