package com.example.assignment.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.assignment.mvvm.model.DeleteStudentFirebase;
import com.example.assignment.mvvm.model.StudentModel;

import java.util.List;

import io.reactivex.Single;


@Dao
public interface DeleteStudentFromFirebaseDao {

    @Query("SELECT * FROM tb_deleted_students")
    List<DeleteStudentFirebase> getAllStudents();

    @Query("DELETE FROM tb_deleted_students")
    void deleteAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(DeleteStudentFirebase deleteStudentFirebase);

}
