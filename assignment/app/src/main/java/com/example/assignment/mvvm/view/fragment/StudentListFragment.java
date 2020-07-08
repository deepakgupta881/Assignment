package com.example.assignment.mvvm.view.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.assignment.R;
import com.example.assignment.mvvm.model.StudentModel;
import com.example.assignment.mvvm.view.adapter.StudentListAdapter;
import com.example.assignment.mvvm.viewmodel.StudentViewModel;
import com.example.assignment.utils.AppUtility;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class StudentListFragment extends Fragment implements StudentListAdapter.OnItemClickListener {
    @BindView(R.id.rv_students)
    RecyclerView rvStudents;
    @BindView(R.id.fb_add_student)
    FloatingActionButton fbAddStudent;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_no_record)
    TextView tvNoRecord;
    @BindView(R.id.container)
    CoordinatorLayout container;
    private StudentListAdapter studentListAdapter;
    private Activity activity;
    private StudentViewModel studentViewModel;
    private View view;

    public StudentListFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_student_list, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        activity = getActivity();
        studentViewModel = ViewModelProviders.of(this).get(StudentViewModel.class);
        setUpObservers();
        studentListAdapter = new StudentListAdapter();
        studentListAdapter.setStudentList(new ArrayList<>());
        studentListAdapter.setOnItemClickListener(this);
        rvStudents.setLayoutManager(new LinearLayoutManager(activity));
        rvStudents.setAdapter(studentListAdapter);
        rvStudents.setHasFixedSize(true);
        studentViewModel.getStudents();
        ivBack.setVisibility(View.GONE);
        tvTitle.setText(R.string.std_rec);
        setUpItemTouchListener();
    }

    private void setUpItemTouchListener() {
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
                // delete data
                studentViewModel.deleteStudent(studentListAdapter.getStudentAt(viewHolder.getAdapterPosition()), AppUtility.isNetworkConnected(activity));
            }
        }).attachToRecyclerView(rvStudents);
    }

    private void setUpObservers() {
        studentViewModel.getAllStudents().observe(getViewLifecycleOwner(), studentModels -> {
            if (studentModels != null && studentModels.size() > 0) {
                rvStudents.setVisibility(View.VISIBLE);
                tvNoRecord.setVisibility(View.GONE);
                studentListAdapter.setStudentList(studentModels);
            } else {
                rvStudents.setVisibility(View.GONE);
                tvNoRecord.setVisibility(View.VISIBLE);
            }
        });
    }

    @OnClick(R.id.fb_add_student)
    public void onViewClicked(View view) {
        Navigation.findNavController(view).navigate(R.id.action_studentListFragment_to_addFragment);
    }

    @Override
    public void onItemClick(StudentModel studentModel) {
        Bundle bundle = new Bundle();
        bundle.putString("userId", studentModel.getUserId());
        Navigation.findNavController(view).navigate(R.id.action_studentListFragment_to_updateFragment, bundle);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

}