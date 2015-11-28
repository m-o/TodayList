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
import com.example.marek.todaylist.TodayTasksAdapter;
import com.example.marek.todaylist.Utils;
import com.example.marek.todaylist.VerticalSpaceItemDecoration;
import com.example.marek.todaylist.models.Task;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by marek on 31/10/15.
 */
public class TodayFragment extends Fragment{
    private static final int VERTICAL_ITEM_SPACE = 10;
    private RecyclerView mRecyclerView;
    private TodayTasksAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    AlertDialog dialog;
    private View rootView;
    private RealmResults<Task> data;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_list, container, false);
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerView);

        mLayoutManager = new LinearLayoutManager(this.getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        getData();
        mAdapter = new TodayTasksAdapter(data, getContext());
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addItemDecoration(new VerticalSpaceItemDecoration(VERTICAL_ITEM_SPACE));

        createAddTaskDialog();

        //TODO Add to main activity and take the floating button too
        FloatingActionButton fab = (FloatingActionButton) rootView.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAddTaskDialog();
            }
        });
        return rootView;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (this.isVisible()) {
            mAdapter.notifyDataSetChanged();
        }
    }

    private void getData(){
        Realm realm = Realm.getInstance(this.getContext());
        data = realm.where(Task.class).equalTo("state",Utils.TASK_STATE_TODAY).findAll();
    }

    private void showAddTaskDialog() {
        dialog.show();
    }

    private void createAddTaskDialog(){
        //Preparing views
        LayoutInflater inflater = (LayoutInflater) this.getContext().getSystemService(this.getContext().LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.add_task_dialog, (ViewGroup) rootView.findViewById(R.id.addTaskDialog));
        //layout_root should be the name of the "top-level" layout node in the dialog_layout.xml file.
        final EditText nameText = (EditText) layout.findViewById(R.id.task_name);
        final EditText descriptionText = (EditText) layout.findViewById(R.id.task_description);
        final CheckBox checkBox = (CheckBox) layout.findViewById(R.id.dialog_checkbox);

        //Building dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this.getContext());
        builder.setView(layout);
        builder.setPositiveButton("Add task", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                addTask(nameText.getText().toString(), descriptionText.getText().toString(), checkBox.isChecked());
                //save info where you want it
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //Add some data to realm database
                dialog.dismiss();
            }
        });
        dialog = builder.create();
    }

    private void addTask(String name, String description, boolean checked){
        Realm realm = Realm.getInstance(this.getContext());
        realm.beginTransaction();
        int state = checked ? Utils.TASK_STATE_TODAY : Utils.TASK_STATE_BACKLOG;
        Task task = new Task(name,description, state);
        realm.copyToRealm(task);
        realm.commitTransaction();
        realm.close();
        getData();
        mAdapter.addData(data);
        dialog.dismiss();
    }
}
