package com.example.assignment;

import android.content.Context;

import com.example.assignment.dao.StudentDao;
import com.example.assignment.repository.StudentRepository;

import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.mock;


@RunWith(MockitoJUnitRunner.class)
public class StudentRepositoryTest {


    private StudentRepository studentRepository;
//    private static final StudentModel STUDENT = new StudentModel(StudentTestUtil.STUDENT_1);
    private static final boolean IS_NETWORK_CONNECTED = true;
    private StudentDao studentDao;

    Context context;

    @BeforeEach
    public void initEach() {
        context = mock(Context.class);
        studentDao = mock(StudentDao.class);
        studentRepository = new StudentRepository(context.getApplicationContext());
    }

    /*
        insert student
        verify the correct method is called
        confirm observer is triggered
        confirm new rows inserted
     */
    @Test
    public void insert_student_success() throws Exception {


    }


}
