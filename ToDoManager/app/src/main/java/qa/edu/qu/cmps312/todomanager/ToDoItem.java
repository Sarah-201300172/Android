package qa.edu.qu.cmps312.todomanager;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Time;

/**
 * Created by sarahalhussaini on 10/26/16.
 */

public class ToDoItem implements Serializable {

    private String title;
    private boolean isDone = false;
    private String priority;
    private String time;
    private String date;

    public ToDoItem(String title, boolean isDone, String priority, String time, String date) {
        this.title = title;
        this.isDone = isDone;
        this.priority = priority;
        this.time = time;
        this.date = date;
    }

    public ToDoItem() {

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isDone() {
        return isDone;
    }

    public void setDone(boolean done) {
        isDone = done;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
