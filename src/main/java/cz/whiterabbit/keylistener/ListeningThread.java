package cz.whiterabbit.keylistener;

import java.util.Scanner;

public class ListeningThread implements Runnable{
    WritingThread controlledThread;
    Thread thread;
    
    boolean pause;
    boolean stop;
    
    public ListeningThread(String name, WritingThread controlledThread){
        this.controlledThread = controlledThread;
        thread = new Thread(this, name);
        thread.start();
    }
    
    @Override
    public void run() {

            /*try{
                Thread.sleep(1000);
                System.out.println("pausing ControlledThread");
                controlledThread.pauseWritingThread();
                Thread.sleep(1000);
                System.out.println("Resuming ControlledThread");
                controlledThread.resumeWritingThread();
                Thread.sleep(1000);
                System.out.println("Stopping ControlledThread");
                controlledThread.stopWritingThread();
            }catch (InterruptedException e){
                System.out.println(e.toString());
            }*/


        Scanner keyboard = new Scanner(System.in);
        boolean exit = false;
        while (true) {
            System.out.println("Enter command (quit to exit):");
            String input = keyboard.nextLine();
            if(input != null) {
                if(input.equals("resume")){
                    controlledThread.resumeWritingThread();
                    System.out.println("Your input is : " + input);
                }else if (input.length() == 0){
                    controlledThread.pauseWritingThread();
                    //System.out.println("Your input is : " + input);
                }
            }
        }
        //keyboard.close();
        
    }

    public Thread getThread() {
        return thread;
    }
}
