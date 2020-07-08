package com.example.assignment.mvvm.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.assignment.R;

public class AddStudentFragment extends Fragment {
    public AddStudentFragment() {
    }

    public static AddStudentFragment newInstance(int columnCount) {
        AddStudentFragment fragment = new AddStudentFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_student, container, false);
    }
}