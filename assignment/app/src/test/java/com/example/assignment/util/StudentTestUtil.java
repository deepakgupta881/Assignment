package com.example.assignment.util;

import com.example.assignment.dao.StudentDao;
import com.example.assignment.mvvm.model.StudentModel;
import com.example.assignment.repository.StudentRepository;

public class StudentTestUtil {

    //  under test
    public static final StudentModel STUDENT_1 = new StudentModel("Deepak", "X", 29, "5ft", "O+", "Good", "12");
    //  under test
    public static final StudentModel STUDENT_2 = new StudentModel("Ravi", "XI", 30, "5ft", "AB+", "Poor", "13");

}
