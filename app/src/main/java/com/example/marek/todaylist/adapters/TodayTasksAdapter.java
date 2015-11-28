package com.example.marek.todaylist.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.marek.todaylist.R;
import com.example.marek.todaylist.Utils;
import com.example.marek.todaylist.models.Task;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;

/**
 * Created by marek on 01/11/15.
 */
public class TodayTasksAdapter extends AbstractTaskAdapter<TodayTasksAdapter.TodayViewHolder> {

    RealmResults<Task> realmResults;
    private RealmChangeListener realmListener;
    private Realm realm;
    private Context context;

    public TodayTasksAdapter(RealmResults<Task> realmResults, Context context) {
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

    public static class TodayViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public boolean expanded;
        public TextView mTextView;
        public TextView mDescriptionText;
        public CheckBox mCheckbox;
        public LinearLayout mTexts;
        public RelativeLayout mButtons;
        public TextView mBacklogButton;
        public TextView mDeleteButton;

        public TodayViewHolder(View v) {
            super(v);
            mTextView = (TextView) v.findViewById(R.id.text);
            mDescriptionText = (TextView) v.findViewById(R.id.description);
            mCheckbox = (CheckBox) v.findViewById(R.id.checkbox);
            mTexts = (LinearLayout) v.findViewById(R.id.texts);
            mButtons = (RelativeLayout) v.findViewById(R.id.buttons);
            mBacklogButton = (TextView) v.findViewById(R.id.backlogButton);
            mDeleteButton = (TextView) v.findViewById(R.id.deleteButton);
        }
    }

    public void addData(RealmResults<Task> tasks){
        this.realmResults = tasks;
        notifyDataSetChanged();
    }

    @Override
    public TodayViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.today_task_layout, parent, false);
        // set the view's size, margins, paddings and layout parameters
        TodayViewHolder vh = new TodayViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(final TodayViewHolder holder, final int position) {
        final Task task = realmResults.get(position);
        holder.mTextView.setText(task.getName());
        holder.mDescriptionText.setText(getShortText(task.getDescription()));
        holder.mCheckbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                CheckBox c = (CheckBox) arg0.findViewById(R.id.checkbox);
                c.setChecked(false);
                finishTask(position);
            }
        });
        holder.mTexts.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(holder.expanded){
                    holder.mButtons.setVisibility(View.GONE);
                    holder.mDescriptionText.setText(getShortText(task.getDescription()));
                    holder.expanded = false;
                }
                else{
                    holder.mButtons.setVisibility(View.VISIBLE);
                    holder.expanded = true;
                    holder.mDescriptionText.setText(task.getDescription());
                }
            }
        });
        holder.mBacklogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addTasksToBacklog(position);
            }
        });
        holder.mDeleteButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                deleteTask(position);
            }
        });
    }

    private String getShortText(String text){
        if(text.length() <= 30){
            return text;
        }
        else{
            return text.substring(0,29)+"...";
        }
    }

    @Override
    public int getItemCount() {
        if(realmResults == null) {
            return 0;
        }
        return realmResults.size();
    }

    public void finishTask(int item){
        Realm realm = Realm.getInstance(context);
        realm.beginTransaction();
        Task task = realmResults.get(item);
        task.setState(Utils.TASK_STATE_FINISHED);
        realm.commitTransaction();
        realm.close();
        notifyDataSetChanged();
    }

    private void addTasksToBacklog(int position){
        Realm realm = Realm.getInstance(context);
        realm.beginTransaction();
        Task task = realmResults.get(position);
        task.setState(Utils.TASK_STATE_BACKLOG);
        realm.commitTransaction();
        realm.close();
        notifyDataSetChanged();
    }

    public void deleteTask(int position){
        Realm realm = Realm.getInstance(context);
        realm.beginTransaction();
        realmResults.remove(position);
        realm.commitTransaction();
        realm.close();
        notifyDataSetChanged();
    }
}
