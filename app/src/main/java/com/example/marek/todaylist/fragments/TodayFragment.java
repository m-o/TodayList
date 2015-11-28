package com.example.marek.todaylist.fragments;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.Fragment;
import android.widget.CheckBox;
import android.widget.EditText;

import com.example.marek.todaylist.R;
import com.example.marek.todaylist.adapters.AbstractTaskAdapter;
import com.example.marek.todaylist.adapters.FinishedTasksAdapter;
import com.example.marek.todaylist.adapters.TodayTasksAdapter;
import com.example.marek.todaylist.Utils;
import com.example.marek.todaylist.VerticalSpaceItemDecoration;
import com.example.marek.todaylist.models.Task;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by marek on 31/10/15.
 */
public class TodayFragment extends AbstractFragment{

    protected RealmResults getData(){
        Realm realm = Realm.getInstance(this.getContext());
        return realm.where(Task.class).equalTo("state",Utils.TASK_STATE_TODAY).findAll();
    }

    @Override
    protected AbstractTaskAdapter getAdapter() {
        return new TodayTasksAdapter(data, getContext());
    }

}
