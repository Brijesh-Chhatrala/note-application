package com.notenow.activities;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import com.afollestad.materialdialogs.MaterialDialog;
import com.notenow.R;
import com.notenow.db.DBManager;
import com.notenow.model.Note;
import com.notenow.recyclerview.RecyclerViewAdapter;
import java.util.ArrayList;
import java.util.List;
import github.nisrulz.recyclerviewhelper.RVHItemClickListener;

public class MainActivity extends AppCompatActivity
        implements View.OnClickListener {

    long waitTime = 2000;
    long touchTime = 0;
    private FloatingActionButton mFAB;
    private DBManager dm;
    private List<Note> noteDataList = new ArrayList<>();
    private RecyclerViewAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private TextView mEmptyList;
    private CoordinatorLayout mainRelativeLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //set Actionbar title
        setSupportActionBar((Toolbar) this.findViewById(R.id.toolbar_main));
        if (getSupportActionBar() != null)
            getSupportActionBar().setTitle(R.string.app_name);

        firstInit();
    }

    private void firstInit() {
        mainRelativeLayout = (CoordinatorLayout) findViewById(R.id.mainCoordinatorLayout);
        int value = getIntent().getIntExtra("isSaved", -1);
        if (value != -1)
            showSnackBar(R.string.note_saved, true);
        init();
        initFab();
    }

    private void init() {
        dm = new DBManager(this);
        mRecyclerView = (RecyclerView) this.findViewById(R.id.rv_list);
        dm.readFromDB(noteDataList);
        mAdapter = new RecyclerViewAdapter(this, noteDataList);
        mRecyclerView.setAdapter(mAdapter);
        mEmptyList = (TextView) this.findViewById(R.id.empty);
        mRecyclerView.hasFixedSize();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));


        // Set On Click
        mRecyclerView.addOnItemTouchListener(
                new RVHItemClickListener(this, new RVHItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        int noteId = noteDataList.get(position).getId();
                        Intent intent = new Intent(MainActivity.this, EditNoteActivity.class);
                        intent.putExtra("id", noteId);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        finish();
                    }
                }));

        mRecyclerView.setOnLongClickListener(
                new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View view) {
                        Toast.makeText(MainActivity.this, "Long", Toast.LENGTH_SHORT).show();
                        new MaterialDialog.Builder(MainActivity.this)
                                //TODO add dialog content to delete
                                .show();
                        return true;
                    }
                }
        );

        updateView();
    }

    private void initFab() {
        mFAB = (FloatingActionButton) this.findViewById(R.id.add);
        mFAB.setOnClickListener(this);
        mFAB.show();
    }

    public void updateView() {
        if (noteDataList.isEmpty()) {
            if (mRecyclerView != null)
                mRecyclerView.setVisibility(View.GONE);
            mEmptyList.setVisibility(View.VISIBLE);
        } else {
            mRecyclerView.setVisibility(View.VISIBLE);
            mEmptyList.setVisibility(View.GONE);
        }
    }


    @Override
    public void onClick(View view) {
        Intent i = new Intent(this, EditNoteActivity.class);
        switch (view.getId()) {
            case R.id.add:
                startActivity(i);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                finish();
        }
    }

    public void showSnackBar(int showSnack, boolean lengthLong) {
        if (lengthLong)
            Snackbar.make(mainRelativeLayout, showSnack, Snackbar.LENGTH_LONG)
                    .show();
        else
            Snackbar.make(mainRelativeLayout, showSnack, Snackbar.LENGTH_SHORT)
                    .show();
    }

    public void onBackPressed() {
        long currentTime = System.currentTimeMillis();
        if ((currentTime - touchTime) >= waitTime) {
            showSnackBar(R.string.exit, false);
            touchTime = currentTime;
        } else {
            finish();
        }
    }
}
