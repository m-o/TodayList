package com.example.marek.todaylist.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.marek.todaylist.R;
import com.example.marek.todaylist.models.Task;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;

/**
 * Created by marek on 01/11/15.
 */
public class FinishedTasksAdapter extends AbstractTaskAdapter<FinishedTasksAdapter.ViewHolder> {

    RealmResults<Task> realmResults;
    private RealmChangeListener realmListener;
    private Context context;

    public FinishedTasksAdapter(RealmResults<Task> realmResults, Context context) {
        this.realmResults = realmResults;
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
        public CheckBox mCheckBox;
        public ViewHolder(View v) {
            super(v);
            mTextView = (TextView) v.findViewById(R.id.text);
            mCheckBox = (CheckBox) v.findViewById(R.id.checkbox);
        }
    }

    public void addData(RealmResults<Task> tasks){
        this.realmResults = tasks;
        notifyDataSetChanged();
    }

    @Override
    public FinishedTasksAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.finished_task_layout, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.mTextView.setText(realmResults.get(position).getName());
//        holder.mCheckBox.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View arg0) {
//                CheckBox c = (CheckBox) arg0.findViewById(R.id.checkbox);
//                c.setChecked(false);
//                addTaskToBacklog(position);
//            }
//        });
    }

    @Override
    public int getItemCount() {
        if(realmResults == null) {
            return 0;
        }
        return realmResults.size();
    }

}
