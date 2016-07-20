package com.taskbob.notepad.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import com.taskbob.notepad.R;
import com.taskbob.notepad.Utilities.Util;
import com.taskbob.notepad.entities.TaskEntity;
import com.taskbob.notepad.fragments.AddTaskFragment;
import com.taskbob.notepad.fragments.EditTaskFragment;
import com.taskbob.notepad.fragments.TaskbobFragment;

/**
 * Created by sumanta on 21/7/16.
 */
public class FragmentWrapperActivity extends TaskbobActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_wrapper);
        String className = getIntent().getStringExtra("className");
        fragmentFactory(className);
    }

    private void fragmentFactory(String className) {
        try {
            TaskbobFragment fragment = (TaskbobFragment) Class.forName(className).newInstance();
            if (fragment instanceof AddTaskFragment) {
                setToolbar("Add Task");
            } else if (fragment instanceof EditTaskFragment) {
                TaskEntity entity = getIntent().getParcelableExtra("entity");
                Bundle bundle = new Bundle();
                bundle.putParcelable("entity", entity);
                fragment.setArguments(bundle);
                setToolbar("Edit Task");
            }
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fm.beginTransaction();
            fragmentTransaction.replace(R.id.fragmentContainer, fragment, "fragment");
            fragmentTransaction.commit();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
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
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Util.hideKeypad(FragmentWrapperActivity.this);
        overridePendingTransition(R.anim.flipin_reverse, R.anim.flipout_reverse);
    }
}
