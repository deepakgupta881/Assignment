package com.example.assignment.mvvm.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import com.example.assignment.R;
import com.example.assignment.service.WorkerThread;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getName();
    @BindView(R.id.tv_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar)
    public Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        setSupportActionBar(toolbar);
        WorkManager workManager = WorkManager.getInstance(this);
        PeriodicWorkRequest.Builder perBuilder =
                new PeriodicWorkRequest.Builder(WorkerThread.class, 15, TimeUnit.MINUTES);
        PeriodicWorkRequest request = perBuilder.build();
        workManager.enqueueUniquePeriodicWork(TAG, ExistingPeriodicWorkPolicy.REPLACE, request);
        toolbar.setNavigationOnClickListener(v -> {
            onBackPressed();
        });
    }


    @Override
    public void onAttachFragment(@NonNull Fragment fragment) {
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.action_addFragment_to_studentListFragment);
        if (fragment != null) {
            fragment.onActivityResult(requestCode, resultCode, data);
        }
    }


}