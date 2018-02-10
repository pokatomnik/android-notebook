package pokatomnik.tk.notebook_singleton;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class NoteRecordAdapter extends ArrayAdapter<NoteRecord> {
    private ArrayList<NoteRecord> baseItems;

    public NoteRecordAdapter(Context context, ArrayList<NoteRecord> list) {
        super(context, 0, list);
        baseItems = list;
    }

    @Override
    public void notifyDataSetChanged() {
        try {
            String path =  "data.dat";
            FileOutputStream fos = getContext().openFileOutput(path, Context.MODE_PRIVATE);
            ObjectOutputStream oos = new ObjectOutputStream(fos);

            oos.writeObject(this.baseItems);

            oos.close();
        } catch (Exception ignored) { }
        super.notifyDataSetChanged();
    }

    @NonNull
    @Override
    public View getView(final int position, View convertView, @NonNull ViewGroup parent) {
        NoteRecord item = getItem(position);

        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater
                    .inflate(R.layout.note_record_view, parent, false);
        }

        TextView titleTextView = convertView.findViewById(R.id.title_text);
        TextView textTextView = convertView.findViewById(R.id.text_text);
        Button removeNoteButton = convertView.findViewById(R.id.remove_item_button);

        titleTextView.setText(item != null ? item.getTitle() : "");
        textTextView.setText(item != null ? item.getText() : "");

        final NoteRecordAdapter self = this;

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(self.getContext(), EditNoteActivity.class);
                intent.putExtra("position", position);
                self.getContext().startActivity(intent);
            }
        });

        removeNoteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DataStore.getInstance().getNotes().remove(position);
                self.notifyDataSetChanged();
            }
        });

        return convertView;
    }
}