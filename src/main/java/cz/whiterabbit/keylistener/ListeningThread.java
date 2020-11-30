package cz.whiterabbit.keylistener;

import cz.whiterabbit.gameloop.GameLoop;

import java.util.Scanner;


//todo need to be able to start and stop listening
public class ListeningThread implements Runnable{
    GameLoop controlledThread;
    Thread thread;

    //Control
    private boolean pause;
    
    public ListeningThread(String name, GameLoop controlledThread){
        this.controlledThread = controlledThread;
        thread = new Thread(this, name);
        //thread.start();
    }
    
    @Override
    public void run() {
        Scanner keyboard = new Scanner(System.in);

        while (true) {
            String input = keyboard.nextLine();
            if(input.length() == 0){
                System.out.println("PAUSING");
                controlledThread.pauseWritingThread();
                stopListening();
            }
            synchronized (this){
                try{
                    while(pause){
                        wait();
                    }
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
        }
        
    }

    public synchronized void stopListening(){
        pause = true;
        notify();
    }

    public synchronized void resumeListening(){
        pause = false;
        notify();
    }

    public void startListeningThread(){
        thread.start();
    }
}
