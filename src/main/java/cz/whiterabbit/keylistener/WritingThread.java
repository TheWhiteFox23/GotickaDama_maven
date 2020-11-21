package cz.whiterabbit.keylistener;

import cz.whiterabbit.gui.ConsolePrinter;

public class WritingThread implements Runnable{

    ConsolePrinter consolePrinter;
    private Thread thread;
    private boolean pause = false;
    private boolean stop = false;

    public WritingThread(String name){
        consolePrinter = new ConsolePrinter();
        thread = new Thread(this, name);
    }

    @Override
    public void run() {
        System.out.println("Writing thread " + thread.getName() + " started");
        try{
            int count = 0;
            while(true){
                //System.out.println(count);
                consolePrinter.writeSymbol(count);
                count++;
                Thread.sleep(100);
                synchronized (this){
                    while(pause){
                        wait();
                    }
                    if(stop){
                        break;
                    }
                }
            }
        }catch (InterruptedException e){
            System.out.println(e.toString());
        }

    }

    synchronized void pauseWritingThread(){
        pause = true;
        notify();
    }

    synchronized void resumeWritingThread(){
        pause = false;
        notify();
    }

    synchronized void stopWritingThread(){
        stop = true;
        pause = false;
        notify();
    }

    public void runWritingThread(){
        thread.start();
    }

    public Thread getThread(){
        return thread;
    }
}
