package pokatomnik.tk.notebook_singleton;

import java.util.ArrayList;

class DataStore {
    private static final DataStore dataStoreInstance = new DataStore();

    static DataStore getInstance() {
        return dataStoreInstance;
    }

    private ArrayList<NoteRecord> notes;

    private DataStore() {
        this.notes = new ArrayList<>();
    }

    public ArrayList<NoteRecord> getNotes() {
        return this.notes;
    }
}
