package model;

import org.json.JSONObject;
import persistence.Writable;

// Represents a normal, non-urgent task that needs to be done
public class NormalTask extends Task {


    //EFFECTS: Constructs a task with a name, date, and time
    public NormalTask(String name, int time) {
        super(name, time);
        urgent = false;
    }


    //EFFECTS: constructs the string that appears in the task list on the GUI
    @Override
    public String constructTaskString() {

        taskString = ("\n" + this.getTaskTime() + ":00 -> " + this.getTaskName());
        return taskString;
    }

}
