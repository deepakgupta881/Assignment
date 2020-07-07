package com.example.assignment.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.assignment.mvvm.model.StudentModel;

import java.util.List;

@Dao
public interface StudentDao {

    @Query("SELECT * FROM tb_student")
    List<StudentModel> getAllStudents();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(StudentModel student);

    @Delete
    void delete(StudentModel student);

    @Update
    void update(StudentModel student);
}
