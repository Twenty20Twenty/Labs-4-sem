package ru.nstu.javafx_labs_lipatov_v2.AI;

import ru.nstu.javafx_labs_lipatov_v2.data.MaleStudent;
import ru.nstu.javafx_labs_lipatov_v2.data.Student;
import ru.nstu.javafx_labs_lipatov_v2.data.StudentCollections;

public class MaleAI extends BaseAI {
    public MaleAI(){
        super("MaleThread");
    }

    @Override
    Boolean nextStep(){
        System.out.println(super.threadName);
            for (Student stud: StudentCollections.getInstance().linkedStudentList){
                if (stud instanceof MaleStudent){
                    stud.move(stud.getX() - 1, stud.getY());
                }
            }
            super.isGoing = false;
        return true;
    }
}
