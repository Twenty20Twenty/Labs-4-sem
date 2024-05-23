package ru.nstu.javafx_labs_lipatov_v2.SQL;

import ru.nstu.javafx_labs_lipatov_v2.data.FemaleStudent;
import ru.nstu.javafx_labs_lipatov_v2.data.MaleStudent;
import ru.nstu.javafx_labs_lipatov_v2.data.Student;

import java.sql.*;

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


}
