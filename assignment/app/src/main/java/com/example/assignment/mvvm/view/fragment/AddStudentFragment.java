package com.example.assignment.mvvm.view.fragment;

import android.Manifest;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
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
import com.example.assignment.mvvm.viewmodel.StudentViewModel;
import com.example.assignment.utils.AppUtility;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class AddStudentFragment extends Fragment implements AdapterView.OnItemSelectedListener {
    @BindView(R.id.spn_performance)
    Spinner spnPerformance;
    @BindView(R.id.btn_save)
    Button btnSave;
    @BindView(R.id.iv_profile)
    CircleImageView ivProfile;
    @BindView(R.id.iv_add)
    CircleImageView ivAdd;
    @BindView(R.id.et_name)
    TextInputEditText etName;
    @BindView(R.id.text_input_name)
    TextInputLayout textInputName;
    @BindView(R.id.et_class)
    TextInputEditText etClass;
    @BindView(R.id.et_age)
    TextInputEditText etAge;
    @BindView(R.id.et_height)
    TextInputEditText etHeight;
    @BindView(R.id.et_bg)
    TextInputEditText etBg;
    @BindView(R.id.views)
    View views;
    @BindView(R.id.text_input_class)
    TextInputLayout textInputClass;
    @BindView(R.id.text_input_age)
    TextInputLayout textInputAge;
    @BindView(R.id.text_input_height)
    TextInputLayout textInputHeight;
    @BindView(R.id.text_input_bg)
    TextInputLayout textInputBg;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;

    private Activity activity;
    private StudentViewModel studentViewModel;
    private String name;
    private String stClass;
    private String age;
    private String height;
    private String bloodGroup;
    private String performance;
    private String image;

    private String[] performanceList = {"Average", "Poor",
            "Good", "Best"};

    public AddStudentFragment() {
    }

    private final int REQUEST = 112;
    private String[] permission = new String[]{
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_student, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        activity = getActivity();
        ivBack.setVisibility(View.VISIBLE);
        tvTitle.setText(R.string.add_st);
        studentViewModel = ViewModelProviders.of(this).get(StudentViewModel.class);
        spnPerformance.setOnItemSelectedListener(this);
        ArrayAdapter adapter = new ArrayAdapter(activity, R.layout.item_performance, performanceList);
        adapter.setDropDownViewResource(
                android.R.layout
                        .simple_spinner_dropdown_item);
        spnPerformance.setAdapter(adapter);
    }


    public boolean isValid() {
        name = String.valueOf(etName.getText());
        stClass = String.valueOf(etClass.getText());
        age = String.valueOf(etAge.getText());
        height = String.valueOf(etHeight.getText());
        bloodGroup = String.valueOf(etBg.getText());
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
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
// TODO will add listener to communicate with activity
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @OnClick({R.id.btn_save, R.id.iv_add})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_save:
                if (isValid()) {
                    studentViewModel.insertStudent(name, stClass, Integer.parseInt(age), height, bloodGroup, image, performance, AppUtility.isNetworkConnected(activity));
                    Toast.makeText(activity, "record has been saved", Toast.LENGTH_SHORT).show();
                    AppUtility.hideKeyboard(activity);
                    new Handler().postDelayed(() -> activity.onBackPressed(), 500);
                }
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

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
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

    @OnClick(R.id.iv_back)
    public void onViewClicked() {
        activity.onBackPressed();
    }

    public interface GetImageFromGallery {
        void pickImageListener();

    }

}