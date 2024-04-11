package ru.nstu.javafx_labs_lipatov_v2.AI;

import ru.nstu.javafx_labs_lipatov_v2.data.StudentCollections;

public abstract class BaseAI implements Runnable{
    Boolean isGoing = true;
    public String threadName;

    public BaseAI(String threadName){
        this.threadName = threadName;
    }

    @Override
    public void run(){
        isGoing = true;
        while (isGoing){
            synchronized (StudentCollections.getInstance().linkedStudentList){
                nextStep();
            }
        }
    }

    abstract Boolean nextStep();
}
