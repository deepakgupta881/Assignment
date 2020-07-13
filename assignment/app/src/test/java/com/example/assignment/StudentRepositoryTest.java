package com.example.assignment;

import android.content.Context;

import com.bumptech.glide.load.engine.Resource;
import com.example.assignment.dao.StudentDao;
import com.example.assignment.mvvm.model.StudentModel;
import com.example.assignment.repository.StudentRepository;
import com.example.assignment.util.StudentTestUtil;

import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import io.reactivex.Single;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@RunWith(JUnit4.class)
public class StudentRepositoryTest {


    private StudentRepository noteRepository;
    private static final StudentModel STUDENT = new StudentModel(StudentTestUtil.STUDENT_1);
    private StudentDao studentDao;

    Context context;

    @BeforeEach
    public void initEach() {
        context = mock(Context.class);
        studentDao = mock(StudentDao.class);
        noteRepository = new StudentRepository(context.getApplicationContext());
    }

    /*
        insert note
        verify the correct method is called
        confirm observer is triggered
        confirm new rows inserted
     */
    @Test
    public void insert_student_success() throws Exception {


    }


}
