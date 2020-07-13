package com.example.assignment;

import com.example.assignment.mvvm.model.StudentModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class StudentTestUtil {
    //  under test
    public static final StudentModel STUDENT_1 = new StudentModel("Deepak", "X", 29, "5ft", "O+", "Good", "12", 1);
    public static final StudentModel STUDENT_2 = new StudentModel("Ravi", "XI", 30, "5ft", "AB+", "Poor", "13", 0);
    public static final StudentModel STUDENT_3 = new StudentModel("Adi", "2", 10, "7ft", "B+", "Best", "14", 0);

    public static final List<StudentModel> TEST_STUDENT_LIST = getTestList();


    public static List<StudentModel> getTestList() {
        List<StudentModel> testList = new ArrayList<>();
        testList.add(new StudentModel("Deepak", "X", 29, "5ft", "O+", "Good", "12", 1));
        testList.add(new StudentModel("Ravi", "XI", 30, "5ft", "AB+", "Poor", "13", 0));
        testList.add(new StudentModel("Adi", "2", 10, "7ft", "B+", "Best", "14", 0));
        return testList;
    }

}
