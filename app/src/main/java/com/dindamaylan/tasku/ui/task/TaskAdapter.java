package com.dindamaylan.tasku.ui.task;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.dindamaylan.tasku.R;
import com.dindamaylan.tasku.data.StatusTask;
import com.dindamaylan.tasku.data.TaskData;
import com.dindamaylan.tasku.databinding.ItemTaskBinding;
import com.dindamaylan.tasku.repo.remote.TaskRepo;
import com.dindamaylan.tasku.ui.home.OnGetTaskListener;
import com.dindamaylan.tasku.utils.Helpers;
import com.google.firebase.Timestamp;

import java.util.ArrayList;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.ViewHolder> {

    private ItemTaskBinding binding;
    private ArrayList<TaskData> listTaskData;
    private OnGetTaskListener listener;
    private Context context;

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView tvStatus, tvTittle, tvDesc, tvDeadline;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvStatus = itemView.findViewById(R.id.tvStatus);
            tvDeadline = itemView.findViewById(R.id.tvDeadline);
            tvTittle = itemView.findViewById(R.id.tvTittle);
            tvDesc = itemView.findViewById(R.id.tvDesc);
        }

        public TextView getTvStatus() {
            return tvStatus;
        }

        public TextView getTvTittle() {
            return tvTittle;
        }

        public TextView getTvDesc() {
            return tvDesc;
        }

        public TextView getTvDeadline() {
            return tvDeadline;
        }
    }

    public TaskAdapter(OnGetTaskListener listener, Context tContext, ArrayList<TaskData> data) {
        this.listener = listener;
        context = tContext;
        listTaskData = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = ItemTaskBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false
        );
        return new ViewHolder(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        TaskData data = listTaskData.get(position);
        binding.tvStatus.setBackgroundColor(ContextCompat.getColor(context, getStatusColor(data.status)));
        binding.tvDeadline.setText(new Helpers().formatTime(data.deadline));
        String inDeadline = "";
        if (data.deadline.toDate().getTime() < Timestamp.now().toDate().getTime()) inDeadline = "missing";
        if (!inDeadline.isEmpty() && !data.status.equals(StatusTask.missing.toString())){
            new TaskRepo().updateStatusTask(data.id, StatusTask.missing.toString(), (isSuccess, message) -> {});

        }
        binding.tvTittle.setText(data.title);
        binding.tvDesc.setText(data.desc);

        binding.cardTask.setOnClickListener(v -> {
            listener.onItemTaskListener(data);
        });
    }

    private int getStatusColor(String tvStatus) {
        switch(tvStatus){
            case "doing" : return R.color.doing;
            case "done" : return R.color.done;
            case "missing" : return R.color.missing;
            default : return R.color.todo;
        }
    }


    @Override
    public int getItemCount() {
        return listTaskData.size();
    }

}
