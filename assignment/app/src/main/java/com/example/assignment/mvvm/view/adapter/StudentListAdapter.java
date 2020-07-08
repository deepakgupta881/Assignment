package com.example.assignment.mvvm.view.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.assignment.R;
import com.example.assignment.mvvm.model.StudentModel;

import java.util.List;

public class StudentListAdapter extends RecyclerView.Adapter<StudentListAdapter.ViewHolder> {

    private List<StudentModel> studentList;
    public OnItemClickListener listener;

    public StudentListAdapter() {
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_student_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.studentData = studentList.get(position);
        holder.tvAge.setText(String.valueOf(holder.studentData.getAge()));
        holder.tvName.setText(holder.studentData.getStudentName());
        holder.tvBloodGroup.setText(holder.studentData.getBloodGroup());
        holder.tvHeight.setText(holder.studentData.getHeight());
        holder.tvPerformance.setText(holder.studentData.getPerformance());
        holder.tvStudentClass.setText(holder.studentData.getStudentClass());
        if (holder.studentData.getImage() != null && !holder.studentData.getImage().isEmpty())
            Glide.with(holder.ivProfile.getContext()).load(holder.studentData.getImage()).into(holder.ivProfile);
        else {
            Glide.with(holder.ivProfile.getContext()).load(R.drawable.ic_user).into(holder.ivProfile);
        }
    }

    public void setStudentList(List<StudentModel> studentList) {
        this.studentList = studentList;
        notifyDataSetChanged();
    }

    public StudentModel getStudentAt(int pos) {
        return studentList.get(pos);
    }


    @Override
    public int getItemCount() {
        return studentList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        private TextView tvName;
        private TextView tvStudentClass;
        private TextView tvAge;
        private TextView tvHeight;
        private TextView tvBloodGroup;
        private TextView tvPerformance;
        private ImageView ivProfile;
        private StudentModel studentData;

        public ViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
            tvName = itemView.findViewById(R.id.tv_name);
            tvStudentClass = itemView.findViewById(R.id.tv_class);
            tvAge = itemView.findViewById(R.id.tv_age);
            tvHeight = itemView.findViewById(R.id.tv_height);
            tvBloodGroup = itemView.findViewById(R.id.tv_bg);
            tvPerformance = itemView.findViewById(R.id.tv_performance);
            ivProfile = itemView.findViewById(R.id.iv_profile);
            RelativeLayout rlTap = itemView.findViewById(R.id.rl_tap);
            rlTap.setOnClickListener(v -> {
                if (listener != null) listener.onItemClick(studentList.get(getAdapterPosition()));
            });
        }

    }

    public interface OnItemClickListener {
        void onItemClick(StudentModel note);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}