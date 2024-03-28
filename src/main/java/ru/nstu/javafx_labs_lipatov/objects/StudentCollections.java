package ru.nstu.javafx_labs_lipatov.objects;

import ru.nstu.javafx_labs_lipatov.Controller.Controller;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.TreeMap;
import java.util.UUID;

public class StudentCollections {
    private static StudentCollections instance;

    public LinkedList<Student> linkedStudentList;
    public HashSet<UUID> idHashSet;
    public TreeMap<UUID, Long> bornTreeMap;

    private StudentCollections() {
        linkedStudentList = new LinkedList<>();
        idHashSet = new HashSet<>();
        bornTreeMap = new TreeMap<>();
    }

    public static StudentCollections getInstance() {
        if (null == instance) {
            instance = new StudentCollections();
        }
        return instance;
    }
    //private Controller controller;
    public void updateCollections(Long time, Controller controller) throws IndexOutOfBoundsException{
        for (int i = 0; i < linkedStudentList.size(); i++) {
            Student current = linkedStudentList.get(i);
            Long curBornTime = bornTreeMap.get(current.getId());
            if (current instanceof MaleStudent) {
                if (time - curBornTime >= MaleStudent.liveTime){
                    controller.getVisualPane().getChildren().remove(current.getImageView());
                    bornTreeMap.remove(current.getId());
                    idHashSet.remove(current.getId());
                    linkedStudentList.remove(i);
                }
            } else {
                if (time - curBornTime >= FemaleStudent.liveTime){
                    controller.getVisualPane().getChildren().remove(current.getImageView());
                    bornTreeMap.remove(current.getId());
                    idHashSet.remove(current.getId());
                    linkedStudentList.remove(i);
                }
            }
        }
    }

    public void clearCollections(Controller controller) {
        linkedStudentList.forEach((tmp) -> controller.getVisualPane().getChildren().remove(tmp.getImageView()));
        linkedStudentList.clear();
        idHashSet.clear();
        bornTreeMap.clear();
    }
}
