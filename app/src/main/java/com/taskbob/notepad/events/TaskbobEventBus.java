package com.taskbob.notepad.events;

import android.os.Handler;
import android.os.Looper;

import com.squareup.otto.Bus;
import com.squareup.otto.ThreadEnforcer;

/**
 * Created by sumanta on 21/7/16.
 */
public class TaskbobEventBus extends Bus {
    public TaskbobEventBus(ThreadEnforcer enforcer) {
        super(enforcer);
    }
    private final Handler mHandler = new Handler(Looper.getMainLooper());
    @Override
    public void post(final Object event) {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            super.post(event);
        } else {
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    TaskbobEventBus.super.post(event);
                }
            });
        }
    }
}
