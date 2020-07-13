package com.example.assignment.mvvm.view.fragment;

import android.Manifest;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.bumptech.glide.Glide;
import com.example.assignment.R;
import com.example.assignment.mvvm.model.StudentModel;
import com.example.assignment.mvvm.view.activity.MainActivity;
import com.example.assignment.mvvm.viewmodel.StudentViewModel;
import com.example.assignment.utils.AppUtility;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;


/*
 * TODO will manage through one screen (MainActivity), for now the code is redundant
 * */

public class UpdateStudentFragment extends Fragment implements AdapterView.OnItemSelectedListener {
    @BindView(R.id.view)
    View view;
    @BindView(R.id.iv_edit)
    ImageView ivEdit;
    @BindView(R.id.tv_name)
    TextInputEditText tvName;
    @BindView(R.id.tv_class)
    TextInputEditText tvClass;
    @BindView(R.id.tv_age)
    TextInputEditText tvAge;
    @BindView(R.id.tv_height)
    TextInputEditText tvHeight;
    @BindView(R.id.tv_bg)
    TextInputEditText tvBg;
    @BindView(R.id.spn_performance)
    Spinner spnPerformance;
    @BindView(R.id.btn_save)
    Button btnSave;
    @BindView(R.id.iv_profile)
    CircleImageView ivProfile;
    @BindView(R.id.iv_add)
    CircleImageView ivAdd;
    @BindView(R.id.text_input_name)
    TextInputLayout textInputName;
    @BindView(R.id.text_input_class)
    TextInputLayout textInputClass;
    @BindView(R.id.text_input_age)
    TextInputLayout textInputAge;
    @BindView(R.id.text_input_height)
    TextInputLayout textInputHeight;
    @BindView(R.id.text_input_bg)
    TextInputLayout textInputBg;
    private String[] performanceList = {"Average", "Poor", "Good", "Best"};
    private Activity activity;
    private StudentViewModel studentViewModel;
    private ArrayAdapter adapter;
    private String name;
    private String stClass;
    private String age;
    private String height;
    private String bloodGroup;
    private String performance;
    private StudentModel studentModel;
    private String image;

    private final int REQUEST = 112;
    private String[] permission = new String[]{
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA
    };

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        activity = getActivity();
        studentViewModel = ViewModelProviders.of(this).get(StudentViewModel.class);

        adapter = new ArrayAdapter(activity, R.layout.item_performance, performanceList);
        adapter.setDropDownViewResource(
                android.R.layout
                        .simple_spinner_dropdown_item);
        spnPerformance.setAdapter(adapter);
        spnPerformance.setOnItemSelectedListener(this);
        if (getArguments() != null) {
            studentViewModel.getStudentDetailById(getArguments().getString("userId"));
        }
        TextView textView = activity.findViewById(R.id.tv_title);
        textView.setText(R.string.st_update);
        Objects.requireNonNull(((MainActivity) activity).getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        enableDisableFocus(false);
        setUpObservers();
    }


    private void enableDisableFocus(boolean value) {
        tvName.setEnabled(value);
        tvClass.setEnabled(value);
        tvHeight.setEnabled(value);
        tvAge.setEnabled(value);
        tvBg.setEnabled(value);
        spnPerformance.setEnabled(value);
        tvName.requestFocus();
        btnSave.setVisibility(value ? View.VISIBLE : View.GONE);
        ivAdd.setVisibility(value ? View.VISIBLE : View.GONE);
    }

    private void setUpObservers() {
        studentViewModel.getStudentInfo().observe(getViewLifecycleOwner(), studentModels -> {
            if (studentModels != null) {
                studentModel = studentModels;
                tvName.setText(studentModels.getStudentName());
                tvClass.setText(studentModels.getStudentClass());
                tvHeight.setText(studentModels.getHeight());
                tvAge.setText(String.valueOf(studentModels.getAge()));
                tvBg.setText(studentModels.getBloodGroup());
                image = studentModels.getImage();
                if (studentModels.getImage() != null && !studentModels.getImage().isEmpty())
                    Glide.with(activity).load(studentModels.getImage()).into(ivProfile);
                spnPerformance.setSelection(adapter.getPosition(studentModels.getPerformance()));
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_update_student, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @OnClick({R.id.btn_save, R.id.iv_add, R.id.iv_edit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_save:
                if (isValid()) {
                    studentModel.setAge(Integer.parseInt(age));
                    studentModel.setBloodGroup(bloodGroup);
                    studentModel.setHeight(height);
                    studentModel.setStudentClass(stClass);
                    studentModel.setStudentName(name);
                    studentModel.setImage(image);
                    studentModel.setPerformance(performance);
                    studentViewModel.updateStudent(studentModel, AppUtility.isNetworkConnected(activity));
                    Toast.makeText(activity, "record has been updated", Toast.LENGTH_SHORT).show();
                    AppUtility.hideKeyboard(activity);
                    new Handler().postDelayed(() -> activity.onBackPressed(), 500);
                }
                break;

            case R.id.iv_edit:
                ivEdit.setVisibility(View.GONE);
                enableDisableFocus(true);
                break;

            case R.id.iv_add:
                if (checkPermission()) {
                    openGallery();
                } else {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        requestPermissions(permission, REQUEST);
                    }
                }
                break;
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        performance = performanceList[position];
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0 && resultCode == Activity.RESULT_OK) {
            try {
                Uri imageUri = data.getData();
                if (imageUri != null) {
                    image = imageUri.toString();
                }
                Glide.with(activity).load(imageUri).into(ivProfile);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }
//
//    @OnClick(R.id.iv_back)
//    public void onViewClicked() {
//        activity.onBackPressed();
//    }

    private boolean isValid() {
        name = String.valueOf(tvName.getText());
        stClass = String.valueOf(tvClass.getText());
        age = String.valueOf(tvAge.getText());
        height = String.valueOf(tvHeight.getText());
        bloodGroup = String.valueOf(tvBg.getText());
        if (TextUtils.isEmpty(name)) {
            textInputName.setError("Please enter student name");
            return false;
        } else if (TextUtils.isEmpty(stClass)) {
            textInputClass.setError("Please enter student class");
            return false;
        } else if (TextUtils.isEmpty(age)) {
            textInputAge.setError("Please enter student age");
            return false;
        } else if (TextUtils.isEmpty(height)) {
            textInputHeight.setError("Please enter student height");
            return false;
        } else if (TextUtils.isEmpty(bloodGroup)) {
            textInputBg.setError("Please enter student blood group");
            return false;
        }
        textInputName.setError("");
        textInputClass.setError("");
        textInputAge.setError("");
        textInputHeight.setError("");
        textInputBg.setError("");
        return true;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST) {
            boolean allSuccess = true;
            for (int i = 0; i < permissions.length; i++) {
                if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
                    allSuccess = false;
                    boolean requestAgain = shouldShowRequestPermissionRationale(permissions[i]);
                    if (requestAgain) {
                        Toast.makeText(activity, "Permission denied", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(activity, "Go to settings and enable the permission", Toast.LENGTH_SHORT).show();
                    }
                }
            }
            if (allSuccess) {
                openGallery();
            }
        }

    }


    /*
     * TODO will manage through one screen (MainActivity), for now the code is redundant
     * */
    private void openGallery() {
        Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        i.putExtra("crop", "true");
        i.putExtra("aspectX", 100);
        i.putExtra("aspectY", 100);
        i.putExtra("outputX", 256);
        i.putExtra("outputY", 356);
        try {
            i.putExtra("return-data", true);
            startActivityForResult(
                    Intent.createChooser(i, "Select Picture"), 0);
        } catch (ActivityNotFoundException ex) {
            ex.printStackTrace();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private boolean checkPermission() {
        boolean allSuccess = true;
        for (String permission : permission) {
            if (activity.checkCallingOrSelfPermission(permission) == PackageManager.PERMISSION_DENIED) {
                allSuccess = false;
            }
        }
        return allSuccess;
    }
}