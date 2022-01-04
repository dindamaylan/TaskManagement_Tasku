package com.dindamaylan.tasku.ui.home;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.dindamaylan.tasku.R;
import com.dindamaylan.tasku.data.MenuTaskData;
import com.dindamaylan.tasku.databinding.ItemTaskMenuBinding;
import com.dindamaylan.tasku.ui.HomeTodoAct;

import java.util.ArrayList;
import java.util.Locale;

public class TaskMenuAdapter extends RecyclerView.Adapter<TaskMenuAdapter.ViewHolder> {

    private ArrayList<MenuTaskData> menuTaskData;
    private Context context;
    private ItemTaskMenuBinding binding;
    private MenuTaskListener listener;

    public TaskMenuAdapter(Context mContext, MenuTaskListener listener,ArrayList<MenuTaskData> data) {
        this.listener = listener;
        this.context = mContext;
        this.menuTaskData = data;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvTaskName, tvTaskRemain;
        private final ImageView ivTask;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTaskName = itemView.findViewById(R.id.tvTaskName);
            tvTaskRemain = itemView.findViewById(R.id.tvTaskRemain);
            ivTask = itemView.findViewById(R.id.ivTask);
        }

        public TextView getTvTaskName() {
            return tvTaskName;
        }

        public TextView getTvTaskRemain() {
            return tvTaskRemain;
        }

        public ImageView getIvTask() {
            return ivTask;
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = ItemTaskMenuBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false
        );
        return new ViewHolder(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MenuTaskData taskData = menuTaskData.get(position);
        binding.tvTaskName.setText(taskData.titleTask);
        binding.tvTaskRemain.setText(new StringBuilder().append(taskData.totalTask).append(" Task Tersedia").toString());
        binding.ivTask.setImageResource(taskData.ic);
        binding.cardHomeTask.setCardBackgroundColor(ContextCompat.getColor(context, taskData.bgColor));
        binding.cardHomeTask.setOnClickListener(v -> {
            listener.onMenuTaskClick(taskData.titleTask.toLowerCase(Locale.ROOT));
        });
    }

    @Override
    public int getItemCount() {
        return menuTaskData.size();
    }

    public interface MenuTaskListener{
        void onMenuTaskClick(String status);
    }
}
