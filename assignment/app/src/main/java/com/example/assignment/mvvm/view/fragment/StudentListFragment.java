package com.example.assignment.mvvm.view.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.assignment.R;
import com.example.assignment.mvvm.model.StudentModel;
import com.example.assignment.mvvm.view.adapter.StudentListAdapter;
import com.example.assignment.mvvm.viewmodel.StudentViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class StudentListFragment extends Fragment implements StudentListAdapter.OnItemClickListener {
    @BindView(R.id.rv_students)
    RecyclerView rvStudents;
    @BindView(R.id.fb_add_student)
    FloatingActionButton fbAddStudent;
    private StudentListAdapter studentListAdapter;
    private Activity activity;
    private StudentViewModel studentViewModel;

    public StudentListFragment() {
    }

    public static StudentListFragment newInstance(int columnCount) {
        StudentListFragment fragment = new StudentListFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_student_list, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        activity = getActivity();
        studentViewModel = ViewModelProviders.of(this).get(StudentViewModel.class);
        rvStudents.setLayoutManager(new LinearLayoutManager(activity));
        studentListAdapter = new StudentListAdapter();
        studentListAdapter.setOnItemClickListener(this);
        rvStudents.setAdapter(studentListAdapter);
        setUpObservers();
    }

    private void setUpObservers() {
        studentViewModel.getAllStudents().observe(getViewLifecycleOwner(), studentModels -> studentListAdapter.setStudentList(studentModels));
    }

    @OnClick(R.id.fb_add_student)
    public void onViewClicked() {
    }

    @Override
    public void onItemClick(StudentModel note) {

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}