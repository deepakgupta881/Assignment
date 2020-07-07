package com.example.assignment.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.assignment.dao.StudentDao;
import com.example.assignment.mvvm.model.StudentModel;

@Database(entities = {StudentModel.class}, version = 1)
public abstract class StudentDatabase extends RoomDatabase {

    public abstract StudentDao studentDao();

    private static volatile StudentDatabase INSTANCE;

    public static synchronized StudentDatabase getDatabaseInstance(final Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                    StudentDatabase.class, "student_database")
                    .build();
        }
        return INSTANCE;
    }
}