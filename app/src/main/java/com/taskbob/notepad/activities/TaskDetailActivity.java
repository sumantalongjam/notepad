package com.taskbob.notepad.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import com.squareup.otto.Subscribe;
import com.taskbob.notepad.Notepad;
import com.taskbob.notepad.R;
import com.taskbob.notepad.databases.TaskDataHandler;
import com.taskbob.notepad.entities.TaskEntity;
import com.taskbob.notepad.events.TaskEvent;

/**
 * Created by sumanta on 21/7/16.
 */
public class TaskDetailActivity extends TaskbobActivity {

    private TaskEntity entity;
    private TextView titleTV, contentTV;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Notepad.getInstance().getEventBus().register(this);
        setContentView(R.layout.activity_task_detail);
        setToolbar("Task Detail");
        long id = getIntent().getLongExtra("taskId", 0);
        entity = TaskDataHandler.getTaskById(this, id);
        titleTV = (TextView) findViewById(R.id.titleTV);
        contentTV = (TextView) findViewById(R.id.contentTV);
        System.out.println(entity.getTitle()+"      "+entity.getContent());
        if(entity!=null) {
            titleTV.setText(TextUtils.isEmpty(entity.getTitle()) ? "" : entity.getTitle());
            contentTV.setText(TextUtils.isEmpty(entity.getContent()) ? "" : entity.getContent());
        }
    }

    @Subscribe
    public void TaskEvent(TaskEvent event) {
        TaskEvent.Action action = event.getAction();
        TaskEntity entity = event.getEntity();
        if(action == TaskEvent.Action.EDIT_TASK) {
            this.entity = entity;
            titleTV.setText(TextUtils.isEmpty(entity.getTitle()) ? "" : entity.getTitle());
            contentTV.setText(TextUtils.isEmpty(entity.getContent()) ? "" : entity.getContent());
        }
    }

    @Override
    protected void setToolbar(String title) {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(title);
        toolbar.setTitleTextColor(getResources().getColor(R.color.color_white));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_edit, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
        } else if (id == R.id.editMenu) {
            Intent intent = new Intent(TaskDetailActivity.this, FragmentWrapperActivity.class);
            intent.putExtra("className", "com.taskbob.notepad.fragments.EditTaskFragment");
            intent.putExtra("entity", entity);
            startActivity(intent);
            overridePendingTransition(R.anim.flipin, R.anim.flipout);
        } else if (id == R.id.deleteMenu) {
            TaskDataHandler.deleteTask(TaskDetailActivity.this, entity.getId());
            Notepad.getInstance().getEventBus().post(new TaskEvent(TaskEvent.Action.DELETE_TASK, entity));
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Notepad.getInstance().getEventBus().unregister(this);
    }
}
