package com.example.assignment.repository;

import android.content.Context;

import com.example.assignment.dao.StudentDao;
import com.example.assignment.db.StudentDatabase;
import com.example.assignment.mvvm.model.StudentModel;

import java.util.List;

public class StudentRepository {
    private StudentDao studentDao;

    public StudentRepository(Context context) {
        StudentDatabase studentDatabase = StudentDatabase.getDatabaseInstance(context);
        studentDao = studentDatabase.studentDao();
    }

    public void insertStudent(StudentModel studentModel) {
        studentDao.insert(studentModel);
    }

    public List<StudentModel> getStudentFromDb() {
        return studentDao.getAllStudents();
    }

}
