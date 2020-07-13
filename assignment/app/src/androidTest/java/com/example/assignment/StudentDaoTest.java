package com.example.assignment;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;

import com.example.assignment.mvvm.model.StudentModel;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;

import java.util.List;

import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;


public class StudentDaoTest extends StudentDBTest {

    @Rule
    public InstantTaskExecutorRule rule = new InstantTaskExecutorRule();

    @Test
    public void insertAndReadValueTest() {

        //Given Two sets of data
        StudentModel studentModel = new StudentModel(StudentTestUtil.STUDENT_1);
        StudentModel studentModelTwo = new StudentModel(StudentTestUtil.STUDENT_2);

        // insert
        getStudentDao().insert(studentModel);
        getStudentDao().insert(studentModelTwo);

        // read all Student list
        Single<List<StudentModel>> allList = getStudentDao().getAllStudents();
        allList.subscribe(new SingleObserver<List<StudentModel>>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onSuccess(List<StudentModel> studentModels) {
                StudentModel studentModel1 = studentModels.get(0);
                // check student mock properties
                assertThat(studentModel1.getStudentName(), is("Deepak"));
                assertThat(studentModel1.getStudentClass(), is("X"));
                assertThat(studentModel1.getAge(), is(29));
                assertThat(studentModel1.getHeight(), is("5ft"));
                assertThat(studentModel1.getBloodGroup(), is("O+"));
                assertThat(studentModel1.getPerformance(), is("Good"));
            }

            @Override
            public void onError(Throwable e) {
                System.out.print(e.getMessage());
            }
        });


    }

    @Test
    public void insertDeleteAndReadValueTest() {

        //Given Two sets of data
        StudentModel studentModel = new StudentModel(StudentTestUtil.STUDENT_1);
        StudentModel studentModelTwo = new StudentModel(StudentTestUtil.STUDENT_2);

        // insert
        getStudentDao().insert(studentModel);
        getStudentDao().insert(studentModelTwo);


        // read all Student list
        Single<List<StudentModel>> allList = getStudentDao().getAllStudents();
        allList.subscribe(new SingleObserver<List<StudentModel>>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onSuccess(List<StudentModel> studentModels) {
                StudentModel studentModel1 = studentModels.get(0);
                // check student mock properties
                assertThat(studentModel1.getStudentName(), is("Deepak"));
                assertThat(studentModel1.getStudentClass(), is("X"));
                assertThat(studentModel1.getAge(), is(29));
                assertThat(studentModel1.getHeight(), is("5ft"));
                assertThat(studentModel1.getBloodGroup(), is("O+"));
                assertThat(studentModel1.getPerformance(), is("Good"));
            }

            @Override
            public void onError(Throwable e) {
                System.out.print(e.getMessage());
            }
        });

        // delete data of first user
        getStudentDao().deleteDataById("12");
//      getStudentDao().deleteDataById("13");

        // read all Student list
        allList.subscribe(new SingleObserver<List<StudentModel>>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onSuccess(List<StudentModel> studentModels) {
                StudentModel studentModel1 = studentModels.get(0);

                // check student mock properties
                Assert.assertThat(studentModel1.getStudentName(), is("Ravi"));

                //check list size after deletion
                assertThat(studentModels.size(), is(1));
            }

            @Override
            public void onError(Throwable e) {
                System.out.print(e.getMessage());
            }
        });

    }

    @Test
    public void insertOnlyTest() {
        //Given Two sets of data
        StudentModel studentModel = new StudentModel(StudentTestUtil.STUDENT_1);
        StudentModel studentModelTwo = new StudentModel(StudentTestUtil.STUDENT_2);
        getStudentDao().insert(studentModel);
        getStudentDao().insert(studentModelTwo);
    }

    @Test
    public void DeleteOnlyTest() {
        //Given
        StudentModel studentModel = new StudentModel(StudentTestUtil.STUDENT_1);
        getStudentDao().insert(studentModel);
        getStudentDao().delete(studentModel);
    }

    @Test
    public void getSyncAndNotSyncDataTest() {
        //Given Two sets of data
        StudentModel studentModel = new StudentModel(StudentTestUtil.STUDENT_1);
        StudentModel studentModelTwo = new StudentModel(StudentTestUtil.STUDENT_2);
        StudentModel studentModelThree = new StudentModel(StudentTestUtil.STUDENT_3);

        getStudentDao().insert(studentModel);
        getStudentDao().insert(studentModelTwo);
        getStudentDao().insert(studentModelThree);

        // check student mock properties
        List<StudentModel> allList = getStudentDao().getSyncStudents(0);
        // list of sync data
        assertThat(allList.size(), is(2));

        // check student mock properties
        List<StudentModel> allListSyncData = getStudentDao().getSyncStudents(1);
        // list of sync data
        assertThat(allListSyncData.size(), is(1));
    }

    @Test
    public void updateStudentTest() {
        StudentModel studentModel = new StudentModel(StudentTestUtil.STUDENT_1);
        StudentModel studentModelTwo = new StudentModel(StudentTestUtil.STUDENT_2);
        StudentModel studentModelThree = new StudentModel(StudentTestUtil.STUDENT_3);

        getStudentDao().insert(studentModel);
        getStudentDao().insert(studentModelTwo);
        getStudentDao().insert(studentModelThree);

        // change name deepak to sachin
        getStudentDao().update("12", "Sachin");

        // read all Student list
        Single<List<StudentModel>> allList = getStudentDao().getAllStudents();
        allList.subscribe(new SingleObserver<List<StudentModel>>() {
            @Override
            public void onSubscribe(Disposable d) {
            }

            @Override
            public void onSuccess(List<StudentModel> studentModels) {
                StudentModel studentModel1 = studentModels.get(2);
                // check student mock properties
                Assert.assertThat(studentModel1.getStudentName(), is("Sachin"));
            }

            @Override
            public void onError(Throwable e) {
                System.out.print(e.getMessage());
            }
        });

    }


}
















