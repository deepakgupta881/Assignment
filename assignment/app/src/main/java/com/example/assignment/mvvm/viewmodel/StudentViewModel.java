package com.example.assignment.mvvm.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.assignment.mvvm.model.StudentModel;
import com.example.assignment.repository.StudentRepository;

import java.util.List;

public class StudentViewModel extends AndroidViewModel {

    private MutableLiveData<List<StudentModel>> allStudents = new MutableLiveData<>();
    private StudentRepository studentRepository;

    public StudentViewModel(@NonNull Application application) {
        super(application);
        studentRepository = new StudentRepository(application.getApplicationContext());
    }

    public void insertStudent(StudentModel studentModel) {
        studentRepository.insertStudent(studentModel);
    }

    public void deleteStudent() {
    }

    public void getStudentsFromDb() {
        allStudents.setValue(studentRepository.getStudentFromDb());
    }

    public void updateStudent() {

    }


    @Override
    protected void onCleared() {
        super.onCleared();
    }

    public MutableLiveData<List<StudentModel>> getAllStudents() {
        return allStudents;
    }

}
