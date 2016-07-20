package com.taskbob.notepad.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import com.squareup.otto.Subscribe;
import com.taskbob.notepad.Adapters.TaskRecyclerAdapter;
import com.taskbob.notepad.Notepad;
import com.taskbob.notepad.R;
import com.taskbob.notepad.databases.TaskDataHandler;
import com.taskbob.notepad.entities.TaskEntity;
import com.taskbob.notepad.events.TaskEvent;

import java.util.ArrayList;

/**
 * Created by sumanta on 21/7/16.
 */
public class MainActivity extends TaskbobActivity {

    private ArrayList<TaskEntity> entityList;
    private TaskRecyclerAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Notepad.getInstance().getEventBus().register(this);
        setContentView(R.layout.activity_main);
        setToolbar("Task List");
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerview);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        entityList = TaskDataHandler.getAllTaskTitle(this);
        adapter = new TaskRecyclerAdapter(this, entityList);
        recyclerView.setAdapter(adapter);
    }

    @Subscribe
    public void TaskEvent(TaskEvent event) {
        TaskEvent.Action action = event.getAction();
        TaskEntity entity = event.getEntity();
        if(action == TaskEvent.Action.ADD_TASK) {
            entityList.add(entity);
            adapter.notifyDataSetChanged();
        } else if (action == TaskEvent.Action.EDIT_TASK) {
            for (TaskEntity entityInner : entityList) {
                if (entity.getId() == entityInner.getId()) {
                    entityList.add(entityList.indexOf(entityInner), entity);
                    entityList.remove(entityInner);
                    adapter.notifyDataSetChanged();
                    break;
                }
            }
        } else if (action == TaskEvent.Action.DELETE_TASK) {
            for (TaskEntity entityInner : entityList) {
                if (entity.getId() == entityInner.getId()) {
                    entityList.remove(entityInner);
                    adapter.notifyDataSetChanged();
                    break;
                }
            }
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
        getMenuInflater().inflate(R.menu.menu_add, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
        } else if (id == R.id.addMenu) {
            Intent intent = new Intent(MainActivity.this, FragmentWrapperActivity.class);
            intent.putExtra("className", "com.taskbob.notepad.fragments.AddTaskFragment");
            startActivity(intent);
            overridePendingTransition(R.anim.flipin, R.anim.flipout);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Notepad.getInstance().getEventBus().unregister(this);
    }
}
