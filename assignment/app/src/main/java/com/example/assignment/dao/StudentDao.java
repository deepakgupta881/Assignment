package com.example.assignment.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.assignment.mvvm.model.StudentModel;

import java.util.List;

import io.reactivex.Single;


@Dao
public interface StudentDao {

    @Query("SELECT * FROM tb_student ORDER BY student_name ASC")
    Single<List<StudentModel>> getAllStudents();

    @Query("SELECT * FROM tb_student WHERE sync=:sync")
    List<StudentModel> getSyncStudents(int sync);

    @Query("SELECT * FROM tb_student WHERE isStudentUpdate=:sync")
    List<StudentModel> getSyncUpdateStudents(int sync);

    @Query("SELECT * FROM tb_student Where userId=:id")
    Single<StudentModel> getStudentDetailById(String id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(StudentModel student);

    @Delete
    void delete(StudentModel student);

    @Update
    void update(StudentModel student);
}
