package ru.nstu.javafx_labs_lipatov_v2.AI;

public abstract class BaseAI extends Thread{
    public String threadName;
    public final Object obj = new Object();

    public BaseAI(String threadName){
        this.threadName = threadName;
    }

    public Boolean paused = Boolean.TRUE;

    public boolean flagEnd = false;
    public void end(){
        flagEnd = true;
    }

    @Override
    public void run(){
        while (!this.isInterrupted()){
            synchronized (obj){
                if (paused){
                    try{
                        System.out.println(threadName + " paused");
                        obj.wait();
                    }catch (InterruptedException e){
                        break;
                    }
                }
            }
            try{
                if (flagEnd){
                    this.interrupt();
                    break;
                }
                this.sleep(10);
            } catch (InterruptedException e){
                e.printStackTrace();
            }
            nextStep();
        }
    }

    abstract void nextStep();
}
