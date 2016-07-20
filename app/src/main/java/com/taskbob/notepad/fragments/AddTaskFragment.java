package com.taskbob.notepad.fragments;

import android.os.Bundle;
import android.support.annotation.Keep;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;
import com.taskbob.notepad.Notepad;
import com.taskbob.notepad.R;
import com.taskbob.notepad.databases.TaskDataHandler;
import com.taskbob.notepad.entities.TaskEntity;
import com.taskbob.notepad.events.TaskEvent;

/**
 * Created by sumanta on 21/7/16.
 */
@Keep
public class AddTaskFragment extends TaskbobFragment implements View.OnClickListener{

    private EditText titleET, contentET;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_task, container, false);
        titleET = (EditText) view.findViewById(R.id.titleET);
        contentET = (EditText) view.findViewById(R.id.contentET);
        view.findViewById(R.id.doneBtn).setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.doneBtn) {
            String title = titleET.getText().toString().trim();
            String content = contentET.getText().toString().trim();
            if(TextUtils.isEmpty(title)) {
                Toast.makeText(getActivity(), "Title can't be empty", Toast.LENGTH_SHORT).show();
                return;
            }
            if(TextUtils.isEmpty(content)) {
                Toast.makeText(getActivity(), "Content can't be empty", Toast.LENGTH_SHORT).show();
                return;
            }
            TaskEntity entity = new TaskEntity(title, content);
            long id = TaskDataHandler.addTask(getActivity(), entity);
            entity.setId(id);
            Notepad.getInstance().getEventBus().post(new TaskEvent(TaskEvent.Action.ADD_TASK, entity));
            getActivity().onBackPressed();
        }
    }
}
