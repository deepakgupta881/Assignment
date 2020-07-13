package com.example.assignment.repository;

import android.content.Context;

import com.example.assignment.dao.DeleteStudentFromFirebaseDao;
import com.example.assignment.dao.StudentDao;
import com.example.assignment.db.StudentDatabase;
import com.example.assignment.mvvm.model.DeleteStudentFirebase;
import com.example.assignment.mvvm.model.StudentModel;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;

public class StudentRepository {
    private StudentDao studentDao;
    private DeleteStudentFromFirebaseDao deleteStudentFromFirebaseDao;
    private DatabaseReference dbRef;

    private final String TAG = StudentRepository.class.getName();

    public StudentRepository(Context context) {
        StudentDatabase studentDatabase = StudentDatabase.getDatabaseInstance(context);
        studentDao = studentDatabase.studentDao();
        deleteStudentFromFirebaseDao = studentDatabase.deleteStudentFromFirebaseDao();
        //Just comment firebase instance while writing test case
        dbRef = FirebaseDatabase.getInstance().getReference();
    }

    public Completable insertStudent(StudentModel studentModel, boolean isNetworkConnected) {
        return Completable.fromAction(() -> {
            if (!isNetworkConnected) {
                studentModel.setSync(0);
                studentDao.insert(studentModel);
            } else {
                saveToFirebaseDb(studentModel);
            }
        }).subscribeOn(Schedulers.io());
    }

    public Completable deleteStudent(StudentModel studentModel, boolean networkConnected) {
        return Completable.fromAction(() -> {
            if (networkConnected) {
                dbRef.child("students").child(studentModel.getUserId()).removeValue();
                studentDao.delete(studentModel);
            } else {
                deleteStudentFromFirebaseDao.insert(new DeleteStudentFirebase(studentModel.getUserId()));
                studentDao.delete(studentModel);
            }
        }).subscribeOn(Schedulers.io());
    }

    public Completable updateStudent(StudentModel studentModel, boolean networkConnected) {
        return Completable.fromAction(() -> {
            if (networkConnected) {
                updateToFirebaseDb(studentModel);
            } else studentDao.update(studentModel);

        }).subscribeOn(Schedulers.io());
    }

    public void updateToFirebaseDb(StudentModel studentModel) {
        studentModel.setIsStudentUpdate(1);
        dbRef.child("students").child(studentModel.getUserId()).setValue(studentModel);
        studentDao.update(studentModel);
    }

    public void saveToFirebaseDb(StudentModel studentModel) {
        studentModel.setSync(1);
        dbRef.child("students").child(studentModel.getUserId()).setValue(studentModel);
        studentDao.insert(studentModel);
    }

    public Single<List<StudentModel>> getAllStudents() {
        return studentDao.getAllStudents().subscribeOn(Schedulers.io());
    }

    public Single<StudentModel> getAllStudents(String id) {
        return studentDao.getStudentDetailById(id).subscribeOn(Schedulers.io());
    }

/*
    public Completable getStudentFromDb(boolean isNetworkConnected) {
        allStudents.clear();
        return Completable.fromAction(() -> {
            if (!isNetworkConnected) {
                allStudents.addAll(studentDao.getAllStudents());
            } else {
                dbRef.child("students").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                            StudentModel studentModel = (StudentModel) postSnapshot.getValue(StudentModel.class);
                            allStudents.add(studentModel);
                            Log.e(TAG, studentModel.toString());
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });

            }
        }).subscribeOn(Schedulers.io());
    }
*/

    public List<StudentModel> getSyncStudents() {
        return studentDao.getSyncStudents(0);
    }

    public List<StudentModel> getSyncUpdateStudents() {
        return studentDao.getSyncUpdateStudents(0);
    }

    public void deleteAllStudentFromTempTable() {
        Completable.fromAction(() -> {
            deleteStudentFromFirebaseDao.deleteAll();
        }).subscribeOn(Schedulers.io()).subscribe();
    }

    public void deleteStudentFromFirebase(String userId) {
        dbRef.child("students").child(userId).removeValue();
    }

    public List<DeleteStudentFirebase> getAllDeleteStudentData() {
        return deleteStudentFromFirebaseDao.getAllStudents();
    }

}
