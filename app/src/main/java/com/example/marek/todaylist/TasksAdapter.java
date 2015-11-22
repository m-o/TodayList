package com.example.marek.todaylist;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.marek.todaylist.models.Task;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;

/**
 * Created by marek on 01/11/15.
 */
public class TasksAdapter extends RecyclerView.Adapter<TasksAdapter.ViewHolder> {

    RealmResults<Task> realmResults;
    private RealmChangeListener realmListener;
    private Realm realm;
    private Context context;

    public TasksAdapter(RealmResults<Task> realmResults, Realm realm, Context context) {
        this.realmResults = realmResults;
        this.realm = realm;
        this.context = context;

        this.realmListener = new RealmChangeListener() {
            @Override
            public void onChange() {
                notifyDataSetChanged();
            }
        };

        if(realmResults != null) {
            realmResults.addChangeListener(realmListener);
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView mTextView;
        public CheckBox mCheckbox;
        public ViewHolder(View v) {
            super(v);
            mTextView = (TextView) v.findViewById(R.id.text);
            mCheckbox = (CheckBox) v.findViewById(R.id.checkbox);
        }
    }

    public void addData(RealmResults<Task> tasks){
        this.realmResults = tasks;
        notifyDataSetChanged();
    }

    @Override
    public TasksAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.task_layout, parent, false);
        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.mTextView.setText(realmResults.get(position).getName());
        holder.mCheckbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                CheckBox c = (CheckBox) arg0.findViewById(R.id.checkbox);
                c.setChecked(false);
                removeTask(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        if(realmResults == null) {
            return 0;
        }
        return realmResults.size();
    }

    public void removeTask(int item){
        Realm realm = Realm.getInstance(context);
        realm.beginTransaction();
        Task task = realmResults.get(item);
        task.setToday(false);
        task.setFinished(true);
        realm.commitTransaction();
        realm.close();
        notifyDataSetChanged();
    }
}
