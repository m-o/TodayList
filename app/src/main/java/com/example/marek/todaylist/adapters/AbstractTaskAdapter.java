package com.example.marek.todaylist.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.example.marek.todaylist.models.Task;

import io.realm.RealmResults;

/**
 * Created by marek on 28/11/15.
 */

public abstract class AbstractTaskAdapter<T extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<T>{

    public abstract void addData(RealmResults<Task> tasks);

}
