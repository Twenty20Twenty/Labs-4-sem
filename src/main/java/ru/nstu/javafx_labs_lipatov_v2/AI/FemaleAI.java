package ru.nstu.javafx_labs_lipatov_v2.AI;

import ru.nstu.javafx_labs_lipatov_v2.data.FemaleStudent;
import ru.nstu.javafx_labs_lipatov_v2.data.Student;
import ru.nstu.javafx_labs_lipatov_v2.data.StudentCollections;

public class FemaleAI extends BaseAI {
    public FemaleAI(){
        super("FemaleThread");
    }

    @Override
    Boolean nextStep(){
        System.out.println(super.threadName);
            for (Student stud: StudentCollections.getInstance().linkedStudentList){
                if (stud instanceof FemaleStudent){
                    stud.move(stud.getX() + 1, stud.getY());
                }
            }
            super.isGoing = false;
        return true;
    }
}
