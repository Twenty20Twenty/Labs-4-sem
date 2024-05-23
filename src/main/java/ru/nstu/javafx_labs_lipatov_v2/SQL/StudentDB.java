package ru.nstu.javafx_labs_lipatov_v2.SQL;

import javafx.application.Platform;
import ru.nstu.javafx_labs_lipatov_v2.data.FemaleStudent;
import ru.nstu.javafx_labs_lipatov_v2.data.MaleStudent;
import ru.nstu.javafx_labs_lipatov_v2.data.Student;
import ru.nstu.javafx_labs_lipatov_v2.data.StudentCollections;
import ru.nstu.javafx_labs_lipatov_v2.mvc.HabitatModel;
import ru.nstu.javafx_labs_lipatov_v2.mvc.HabitatView;

import java.io.FileNotFoundException;
import java.sql.*;
import java.util.Objects;

public class StudentDB {
    private static final String URL = "jdbc:postgresql://localhost:5432/javaFX_lipatov";
    private static final String USER = "postgres";
    private static final String PASSWORD = "199ovohah";

    public static void addStudent(Student student) {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement statement = connection.prepareStatement("INSERT INTO students (objectType, posX, posY) VALUES (?, ?, ?)")) {

            if (student instanceof MaleStudent){
                statement.setString(1, "MaleStudent");
            }
            if (student instanceof FemaleStudent){
                statement.setString(1, "FemaleStudent");
            }
            statement.setInt(2, (int)student.getX());
            statement.setInt(3, (int)student.getY());

            statement.executeUpdate();
            System.out.println("Student added successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void getStudent(HabitatView view, int flag){
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM students")) {

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String type = resultSet.getString("objectType");
                int posX = resultSet.getInt("posX");
                int posY = resultSet.getInt("posY");
                System.out.println(type + " " + posX + " " + posY);
                if ((Objects.equals(type, "MaleStudent")) && (flag == 0 || flag == 1)){
                    System.out.println("add Male");
                    MaleStudent student = new MaleStudent(posX, posY);
                    view.getVisualPane().getChildren().add(student.getImageView());
                    StudentCollections.getInstance().linkedStudentList.add(student);
                    StudentCollections.getInstance().idHashSet.add(student.getId());
                    StudentCollections.getInstance().bornTreeMap.put(student.getId(), 0L);
                }
                if (Objects.equals(type, "FemaleStudent") && (flag == 0 || flag == 2)){
                    System.out.println("add Female");
                    FemaleStudent student = new FemaleStudent(posX, posY);
                    view.getVisualPane().getChildren().add(student.getImageView());
                    StudentCollections.getInstance().linkedStudentList.add(student);
                    StudentCollections.getInstance().idHashSet.add(student.getId());
                    StudentCollections.getInstance().bornTreeMap.put(student.getId(), 0L);
                }
            }
            System.out.println("load from DB +");

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

}
