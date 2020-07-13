package com.example.assignment;

import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;

import com.example.assignment.dao.StudentDao;
import com.example.assignment.db.StudentDatabase;

import org.junit.After;
import org.junit.Before;

public abstract class StudentDBTest {

    // system under test
    private StudentDatabase studentDatabase;

    public StudentDao getStudentDao() {
        return studentDatabase.studentDao();
    }

    @Before
    public void init() {
        studentDatabase = Room.inMemoryDatabaseBuilder(
                ApplicationProvider.getApplicationContext(),
                StudentDatabase.class
        ).allowMainThreadQueries().build();
    }

    @After
    public void finish() {
        studentDatabase.close();
    }
}
