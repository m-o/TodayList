package com.example.marek.todaylist.fragments;

import com.example.marek.todaylist.adapters.AbstractTaskAdapter;
import com.example.marek.todaylist.Utils;
import com.example.marek.todaylist.adapters.FinishedTasksAdapter;
import com.example.marek.todaylist.models.Task;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by marek on 31/10/15.
 */
public class FinishedFragment extends AbstractFragment{

    protected RealmResults<Task> getData(){
        Realm realm = Realm.getInstance(this.getContext());
        return realm.where(Task.class).equalTo("state",Utils.TASK_STATE_FINISHED).findAll();
    }

    @Override
    protected AbstractTaskAdapter getAdapter() {
        return new FinishedTasksAdapter(data, getContext());
    }
}
