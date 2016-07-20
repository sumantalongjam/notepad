package com.taskbob.notepad;

import android.app.Application;
import android.content.Context;
import com.squareup.otto.ThreadEnforcer;
import com.taskbob.notepad.events.TaskbobEventBus;

/**
 * Created by sumanta on 21/7/16.
 */
public class Notepad extends Application {

    private static Context context;
    private static TaskbobEventBus eventBus;
    private static final Notepad notepad = new Notepad();

    public Notepad() {
        super();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        eventBus = new TaskbobEventBus(ThreadEnforcer.MAIN);
    }
    public static Notepad getInstance() {
        return notepad;
    }
    public TaskbobEventBus getEventBus() { return eventBus; }
    public Context getContext() {
        return context;
    }

}