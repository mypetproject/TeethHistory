package com.appsverse.teethhistory.repository;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.RealmClass;

@RealmClass(embedded = true)
public class ToothModel extends RealmObject {

    public static final int NO_BABY_TOOTH = -1;
    public static final int NO_TOOTH = 0;

    public static final int BABY_TOOTH = 2;
    public static final int PERMANENT_TOOTH = 3;

    public static final int IMPLANTED = 30;
    public static final int FILLED = 20;
    public static final int NORMAL = 10;

    private int id;
    private int position;
    private int defaultState;
    private int state;

    private RealmList<EventModel> eventModels;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public RealmList<EventModel> getEventModels() {
        return eventModels;
    }

    public void setEventModels(RealmList<EventModel> eventModels) {
        this.eventModels = eventModels;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getDefaultState() {
        return defaultState;
    }

    public void setDefaultState(int defaultState) {
        this.defaultState = defaultState;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
}
