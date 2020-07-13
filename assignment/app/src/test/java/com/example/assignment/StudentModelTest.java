package com.example.assignment;


import com.example.assignment.mvvm.model.StudentModel;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;


public class StudentModelTest {


    @Test
    public void isStudentEqualIdenticalPropertiesReturnTrue() {
        StudentModel student1 = new StudentModel("Deepak", "X", 29, "5ft", "O+", "Good", "12", 1);
        StudentModel student2 = new StudentModel("Deepak", "X", 29, "5ft", "O+", "Good", "12", 1);
        // Assert
        assertEquals(student1, student2);
        System.out.println("The students are equal!");
    }

    @Test
    void isStudentEqualDifferentIdsReturnFalse() throws Exception {
        // Arrange
        StudentModel student1 = new StudentModel("Deepak", "X", 29, "5ft", "O+", "Good", "12", 1);
        StudentModel student2 = new StudentModel("Deepak", "X", 29, "5ft", "O+", "Good", "12", 1);
        student2.setUserId("15");
        // Act
        // Assert
        assertNotEquals(student1, student2);
        System.out.println("The students are not equal!");
    }




}
















