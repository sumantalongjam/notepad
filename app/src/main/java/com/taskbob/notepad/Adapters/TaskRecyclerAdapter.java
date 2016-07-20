package com.taskbob.notepad.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.taskbob.notepad.R;
import com.taskbob.notepad.activities.TaskDetailActivity;
import com.taskbob.notepad.entities.TaskEntity;
import java.util.ArrayList;

/**
 * Created by sumanta on 21/7/16.
 */
public class TaskRecyclerAdapter extends RecyclerView.Adapter implements View.OnClickListener {

    private Context context;
    private ArrayList<TaskEntity> entityList;

    public TaskRecyclerAdapter(Context context, ArrayList<TaskEntity> entityList) {
        this.context = context;
        this.entityList = entityList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_task, parent, false);
        RecyclerView.ViewHolder viewHolder = new TaskHolder(view);
        ((TaskHolder)viewHolder).container.setOnClickListener(this);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        TaskEntity entity = entityList.get(position);
        if (entity != null) {
            System.out.println("title  "+entity.getTitle());
            ((TaskHolder) holder).titleTV.setText(entity.getTitle());
            ((TaskHolder) holder).container.setTag(position);
        }
    }

    @Override
    public int getItemCount() {
        return entityList == null ? 0 : entityList.size();
    }

    @Override
    public void onClick(View view) {
        int position = Integer.parseInt(view.getTag().toString());
        TaskEntity entity = entityList.get(position);
        if(entity!=null) {
            Intent intent = new Intent(context, TaskDetailActivity.class);
            intent.putExtra("taskId", entity.getId());
            context.startActivity(intent);
            if(context instanceof Activity)
                ((Activity)context).overridePendingTransition(R.anim.flipin, R.anim.flipout);
        }
    }

    public static class TaskHolder extends RecyclerView.ViewHolder {
        TextView titleTV;
        LinearLayout container;
        public TaskHolder(View v) {
            super(v);
            titleTV = (TextView) v.findViewById(R.id.titleTV);
            container = (LinearLayout) v.findViewById(R.id.container);
        }
    }
}
