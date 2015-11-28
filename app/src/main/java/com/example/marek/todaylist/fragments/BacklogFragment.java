package com.example.marek.todaylist.fragments;

import com.example.marek.todaylist.Utils;
import com.example.marek.todaylist.adapters.AbstractTaskAdapter;
import com.example.marek.todaylist.adapters.BacklogTasksAdapter;
import com.example.marek.todaylist.models.Task;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by marek on 22/11/15.
 */
public class BacklogFragment extends AbstractFragment{

    protected RealmResults<Task> getData(){
        Realm realm = Realm.getInstance(this.getContext());
        return realm.where(Task.class).equalTo("state",Utils.TASK_STATE_BACKLOG).findAll();
    }

    @Override
    protected AbstractTaskAdapter getAdapter() {
        return new BacklogTasksAdapter(data, getContext());
    }
}
