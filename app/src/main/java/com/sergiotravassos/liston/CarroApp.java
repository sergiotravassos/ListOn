package com.sergiotravassos.liston;

import android.app.Application;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by sergiotravassos on 29/05/16.
 */
public class CarroApp extends Application {

    EventBus eventBus;

    @Override
    public void onCreate() {
        super.onCreate();
        eventBus = new EventBus();
    }

    public EventBus getEventBus() {
        return eventBus;
    }
}
