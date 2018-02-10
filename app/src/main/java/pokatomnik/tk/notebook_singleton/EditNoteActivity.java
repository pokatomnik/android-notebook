package pokatomnik.tk.notebook_singleton;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Date;

public class EditNoteActivity extends AppCompatActivity {

    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_note);

        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        Intent intent = getIntent();
        position = intent.getIntExtra("position", -1);

        if (position != -1) {
            EditText editTitle = findViewById(R.id.edit_title);
            EditText editText = findViewById(R.id.edit_text);

            try {
                NoteRecord noteRecord = DataStore.getInstance().getNotes().get(position);

                if (noteRecord == null) {
                    return;
                }

                editTitle.setText(noteRecord.getTitle());
                editText.setText(noteRecord.getText());
            } catch (Exception ignored) {}
        }
    }

    private void updateList() {
        String title = ((EditText) findViewById(R.id.edit_title)).getText().toString();
        String text = ((EditText) findViewById(R.id.edit_text)).getText().toString();

        if (position == -1) {
            if (text.equals("")) { return; }
            DataStore
                    .getInstance()
                    .getNotes()
                    .add(
                            new NoteRecord(
                                    title,
                                    text,
                                    new Date()
                            )
                    );
        } else {
            try {
                NoteRecord noteRecord = DataStore
                        .getInstance()
                        .getNotes()
                        .get(position);
                if (noteRecord == null) {
                    return;
                }

                noteRecord.setTitle(title);
                noteRecord.setText(text);
            } catch (Exception ignored) {}
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                updateList();
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
