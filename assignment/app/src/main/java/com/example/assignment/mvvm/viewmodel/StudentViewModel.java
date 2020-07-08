package com.example.assignment.mvvm.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.assignment.mvvm.model.StudentModel;
import com.example.assignment.repository.StudentRepository;

import java.util.List;
import java.util.Random;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;

public class StudentViewModel extends AndroidViewModel {

    private MutableLiveData<List<StudentModel>> allStudents = new MutableLiveData<>();
    private MutableLiveData<StudentModel> studentInfo = new MutableLiveData<>();

    private StudentRepository studentRepository;
    private CompositeDisposable compositeDisposable;

    public StudentViewModel(@NonNull Application application) {
        super(application);
        studentRepository = new StudentRepository(application);
        compositeDisposable = new CompositeDisposable();
    }

    public void insertStudent(String name, String stClass, int age, String height, String bloodGroup, String image, String performance, boolean isNetworkConnected) {
        compositeDisposable.add(studentRepository.insertStudent(new StudentModel(name, stClass, age, height, bloodGroup, performance, image, name + new Random().nextInt() + ""), isNetworkConnected).observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> {
                }, Throwable::printStackTrace));
    }


    public void getStudents() {
        compositeDisposable.add(studentRepository.getAllStudents().observeOn(AndroidSchedulers.mainThread()).subscribe(studentModels ->
                allStudents.setValue(studentModels), Throwable::printStackTrace));
    }

    public void updateStudent(StudentModel studentModel, boolean networkConnected) {
        compositeDisposable.add(studentRepository.updateStudent(studentModel, networkConnected).observeOn(AndroidSchedulers.mainThread()).subscribe());
    }

    public void deleteStudent(StudentModel studentAt, boolean networkConnected) {
        compositeDisposable.add(studentRepository.deleteStudent(studentAt, networkConnected).observeOn(AndroidSchedulers.mainThread()).subscribe(this::getStudents));
    }

    public void getStudentDetailById(String userId) {
        compositeDisposable.add(studentRepository.getAllStudents(userId).observeOn(AndroidSchedulers.mainThread()).subscribe(studentModels ->
                studentInfo.setValue(studentModels), Throwable::printStackTrace));

    }

    public LiveData<StudentModel> getStudentInfo() {
        return studentInfo;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.dispose();
    }

    public MutableLiveData<List<StudentModel>> getAllStudents() {
        return allStudents;
    }


}
