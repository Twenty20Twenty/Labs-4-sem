package ru.nstu.javafx_labs_lipatov_v2.AI;

import ru.nstu.javafx_labs_lipatov_v2.data.FemaleStudent;
import ru.nstu.javafx_labs_lipatov_v2.data.Student;
import ru.nstu.javafx_labs_lipatov_v2.data.StudentCollections;

public class FemaleAI extends BaseAI {
    public FemaleAI(){
        super("FemaleThread");
    }

    @Override
    void nextStep(){
        //System.out.println(super.threadName);
        synchronized (StudentCollections.getInstance().linkedStudentList){
            for (Student stud: StudentCollections.getInstance().linkedStudentList){
                if (stud instanceof FemaleStudent){
                    stud.move();
                }
            }
        }
    }
}
