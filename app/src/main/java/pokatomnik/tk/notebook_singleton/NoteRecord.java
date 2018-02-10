package pokatomnik.tk.notebook_singleton;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class NoteRecord implements Serializable {
    private String title;
    private String text;
    private ArrayList<String> tags;
    private Date updated;

    public NoteRecord(String title, String text, Date updated, ArrayList<String> tags) {
        this.title = title;
        this.text = text;
        this.tags = new ArrayList<>(tags.size());
        this.updated = updated;
        setTags(tags);
    }

    public NoteRecord(String title, String text, Date updated) {
        this(title, text, updated, new ArrayList<String>());
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String newTitle) {
        title = newTitle;
    }

    public String getText() {
        return text;
    }

    public void setText(String newText) {
        text = newText;
    }

    public Date getUpdated() {
        return (Date) updated.clone();
    }

    public void setUpdated(Date date) {
        updated = (Date) date.clone();
    }

    public String[] getTags() {
        return tags.toArray(new String[tags.size()]);
    }

    public void clearTags() {
        tags.clear();
    }

    public void setTags(Iterable<String> newTags) {
        clearTags();
        addTags(newTags);
    }

    public void addTag(String tag) {
        tags.add(tag);
    }

    public boolean hasTag(String tag) {
        return tag.contains(tag);
    }

    public void addTags(Iterable<String> newTags) {
        for (String tag: newTags) {
            tags.add(tag);
        }
    }

    public void removeTagByIndex(int index) {
        tags.remove(index);
    }
}