package com.example.assignment.service;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.example.assignment.mvvm.model.DeleteStudentFirebase;
import com.example.assignment.mvvm.model.StudentModel;
import com.example.assignment.repository.StudentRepository;
import com.example.assignment.utils.AppUtility;

import java.util.List;

public class WorkerThread extends Worker {
    private final String TAG = WorkerThread.class.getName();

    public WorkerThread(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        Log.e(TAG, "working");
        if (AppUtility.isNetworkConnected(getApplicationContext())) {
            StudentRepository studentRepository = new StudentRepository(getApplicationContext());
            List<StudentModel> studentList = studentRepository.getSyncStudents();
            Log.e(TAG, studentList.size() + "");
            for (StudentModel studentModel : studentList) {
                // only for new records which are not syn
                studentRepository.saveToFirebaseDb(studentModel);
            }
            // will do the same for update and delete, which are nt update in fb based on the flags saved in room db
            List<StudentModel> updateStudentList = studentRepository.getSyncUpdateStudents();
            Log.e(TAG, updateStudentList.size() + "");
            for (StudentModel studentModel : updateStudentList) {
                // only for new records which are not syn
                studentRepository.updateToFirebaseDb(studentModel);
            }
            List<DeleteStudentFirebase> deleteStudentFirebase = studentRepository.getAllDeleteStudentData();
            Log.e(TAG, deleteStudentFirebase.size() + "");
            for (DeleteStudentFirebase deleteStudent : deleteStudentFirebase) {
                // only for new records which are not syn
                studentRepository.deleteStudentFromFirebase(deleteStudent.getUserId());
            }
            studentRepository.deleteAllStudentFromTempTable();
        } else {
            return Result.failure();
        }
        return Result.success();
    }
}
