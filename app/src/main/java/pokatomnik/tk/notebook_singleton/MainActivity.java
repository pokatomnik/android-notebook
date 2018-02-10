package pokatomnik.tk.notebook_singleton;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    NoteRecordAdapter noteRecordAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final MainActivity self = this;
        FloatingActionButton addNewNoteButton = findViewById(R.id.fab);
        addNewNoteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(self, EditNoteActivity.class);
                intent.putExtra("position", -1);
                startActivity(intent);
            }
        });

        noteRecordAdapter = new NoteRecordAdapter(this, DataStore.getInstance().getNotes());

        try {
            String path = "data.dat";
            FileInputStream fis = this.openFileInput(path);
            ObjectInputStream ois = new ObjectInputStream(fis);

            ArrayList<NoteRecord> noteRecords = (ArrayList<NoteRecord>) ois.readObject();

            noteRecordAdapter.addAll(noteRecords);

            ois.close();
        } catch (Exception ignored) {}

        ListView listView = findViewById(R.id.note_list);
        listView.setAdapter(noteRecordAdapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        noteRecordAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
