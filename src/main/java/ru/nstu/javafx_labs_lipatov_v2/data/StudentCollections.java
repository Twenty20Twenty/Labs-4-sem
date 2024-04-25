package ru.nstu.javafx_labs_lipatov_v2.data;

import ru.nstu.javafx_labs_lipatov_v2.mvc.HabitatView;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.TreeMap;
import java.util.UUID;

public class StudentCollections {
    private static StudentCollections instance;

    public final LinkedList<Student> linkedStudentList;
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

    public synchronized void updateCollections(Long time, HabitatView view) throws IndexOutOfBoundsException{
        for (int i = 0; i < linkedStudentList.size(); i++) {
            Student current = linkedStudentList.get(i);
            Long curBornTime = bornTreeMap.get(current.getId());
            if (current instanceof MaleStudent) {
                if ((time - curBornTime) >= (MaleStudent.lifeTime * 1000)){
                    view.getVisualPane().getChildren().remove(current.getImageView());
                    bornTreeMap.remove(current.getId());
                    idHashSet.remove(current.getId());
                    linkedStudentList.remove(i);
                }
            } else {
                if ((time - curBornTime) >= (FemaleStudent.lifeTime * 1000)){
                    view.getVisualPane().getChildren().remove(current.getImageView());
                    bornTreeMap.remove(current.getId());
                    idHashSet.remove(current.getId());
                    linkedStudentList.remove(i);
                }
            }
        }
    }

    public StringBuilder getLiveObjString(){
        StringBuilder stringBuilder = new StringBuilder();
        for (int i =0; i < linkedStudentList.size(); i++){
            Student current =  linkedStudentList.get(i);
            if (current instanceof MaleStudent){
                stringBuilder.append("Male Student ");
            }else{
                stringBuilder.append("Female Student ");
            }
            stringBuilder.append(current.getId() + " ");
            stringBuilder.append(bornTreeMap.get(current.getId()));
            stringBuilder.append("\n");
        }
        return stringBuilder;
    }

    public void clearCollections(HabitatView view) {
        linkedStudentList.forEach((tmp) -> view.getVisualPane().getChildren().remove(tmp.getImageView()));
        linkedStudentList.clear();
        idHashSet.clear();
        bornTreeMap.clear();
    }
}
