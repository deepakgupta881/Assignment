package com.example.assignment.mvvm.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "tb_student")
public class StudentModel {


    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "student_name")
    private String studentName;

    @ColumnInfo(name = "student_class")
    private String studentClass;

    public StudentModel(String studentName, String studentClass, int age, String height, String bloodGroup, String performance, String image, String userId) {
        this.studentName = studentName;
        this.studentClass = studentClass;
        this.age = age;
        this.height = height;
        this.bloodGroup = bloodGroup;
        this.performance = performance;
        this.image = image;
        this.userId = userId;
    }

    @ColumnInfo(name = "age")
    private int age;

    @ColumnInfo(name = "height")
    private String height;

    @ColumnInfo(name = "blood_group")
    private String bloodGroup;

    @ColumnInfo(name = "performance")
    private String performance;

    @ColumnInfo(name = "image")
    private String image;

    @ColumnInfo(name = "sync")
    private int sync;

    public int getSync() {
        return sync;
    }

    public void setSync(int sync) {
        this.sync = sync;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getStudentClass() {
        return studentClass;
    }

    public void setStudentClass(String studentClass) {
        this.studentClass = studentClass;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getBloodGroup() {
        return bloodGroup;
    }

    public void setBloodGroup(String bloodGroup) {
        this.bloodGroup = bloodGroup;
    }

    public String getPerformance() {
        return performance;
    }

    public void setPerformance(String performance) {
        this.performance = performance;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    private int isDeletedFromLocalDb;

    public int getIsDeletedFromLocalDb() {
        return isDeletedFromLocalDb;
    }

    public void setIsDeletedFromLocalDb(int isDeletedFromLocalDb) {
        this.isDeletedFromLocalDb = isDeletedFromLocalDb;
    }

    private int isStudentUpdate;

    public StudentModel() {
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    private String userId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIsStudentUpdate() {
        return isStudentUpdate;
    }

    public void setIsStudentUpdate(int isStudentUpdate) {
        this.isStudentUpdate = isStudentUpdate;
    }

    @Override
    public String toString() {
        return "StudentModel{" +
                "userId='" + userId + '\'' +
                ", id=" + id +
                ", studentName='" + studentName + '\'' +
                ", studentClass='" + studentClass + '\'' +
                ", age=" + age +
                ", height='" + height + '\'' +
                ", bloodGroup='" + bloodGroup + '\'' +
                ", performance='" + performance + '\'' +
                ", image='" + image + '\'' +
                ", sync=" + sync +
                '}';
    }


}
