package com.example.project_guide.ui.note;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.project_guide.R;
import com.example.project_guide.databinding.FragmentHomeBinding;
import com.example.project_guide.ui.home.HomeViewModel;
import com.example.project_guide.ui.note.task.TaskAdapter;
import com.example.project_guide.ui.note.task.TaskContract;
import com.example.project_guide.ui.note.task.TaskDBHelper;

public class NoteFragment extends Fragment {

    private FragmentHomeBinding binding;
//
//    public View onCreateView(@NonNull LayoutInflater inflater,
//                             ViewGroup container, Bundle savedInstanceState) {
//        HomeViewModel homeViewModel =
//                new ViewModelProvider(this).get(HomeViewModel.class);
//
//        binding = FragmentHomeBinding.inflate(inflater, container, false);
//        View root = binding.getRoot();
//
//        final TextView textView = binding.textHome;
//        homeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
//        return root;
//    }
//
//    @Override
//    public void onDestroyView() {
//        super.onDestroyView();
//        binding = null;
//    }
    TaskAdapter mTaskAdapter;
    // These indices are tied to TASKS_COLUMNS.  If TASKS_COLUMNS changes, these must change.
    public static final int COL_TASK_ID = 0;
    public static final int COL_TASK_NAME = 1;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //Usual inflating of the fragment layout file
        View rootView = inflater.inflate(R.layout.fragment_note, container, false);

        //Find the listView
        ListView listView = (ListView) rootView.findViewById(R.id.listview_tasks);

        //Get DBHelper to create new database
        TaskDBHelper helper = new TaskDBHelper(getActivity());
        SQLiteDatabase sqlDB = helper.getReadableDatabase();

        //Query database to get any existing data
        Cursor cursor = sqlDB.query(TaskContract.TaskEntry.TABLE_NAME,
                new String[]{TaskContract.TaskEntry._ID, TaskContract.TaskEntry.COLUMN_TASK},
                null, null, null, null, null);

        //Create a new TaskAdapter and bind it to ListView
        mTaskAdapter = new TaskAdapter(getActivity(), cursor);
        listView.setAdapter(mTaskAdapter);

        return rootView;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.task_fragment, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.action_add_task:
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Add a task");
                builder.setMessage("What do you want to do?");
                final EditText inputField = new EditText(getActivity());
                builder.setView(inputField);
                builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //Get user input
                        String inputTask = inputField.getText().toString();

                        //Get DBHelper to write to database
                        TaskDBHelper helper = new TaskDBHelper(getActivity());
                        SQLiteDatabase db = helper.getWritableDatabase();

                        //Put in the values within a ContentValues.
                        ContentValues values = new ContentValues();
                        values.clear();
                        values.put(TaskContract.TaskEntry.COLUMN_TASK, inputTask);

                        //Insert the values into the Table for Tasks
                        db.insertWithOnConflict(
                                TaskContract.TaskEntry.TABLE_NAME,
                                null,
                                values,
                                SQLiteDatabase.CONFLICT_IGNORE);

                        //Query database again to get updated data
                        Cursor cursor = db.query(TaskContract.TaskEntry.TABLE_NAME,
                                new String[]{TaskContract.TaskEntry._ID, TaskContract.TaskEntry.COLUMN_TASK},
                                null, null, null, null, null);

                        //Swap old data with new data for display
                        mTaskAdapter.swapCursor(cursor);
                    }
                });
                builder.setNegativeButton("Cancel", null);
                builder.create().show();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
