package model;

import org.json.JSONObject;
import persistence.Writable;

// Represents a task that needs to be done
public abstract class Task implements Writable {

    protected String taskName;
    protected int taskTime;
    protected String taskString;
    protected boolean urgent;


    //EFFECTS: Constructs a task with a name, date, and time
    public Task(String name, int time) {
        taskName = name;
        taskTime = time;
        taskString = "";
    }

    //EFFECTS: Returns the task as a JSONObject
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("task name", taskName);
        json.put("task time", taskTime);
        json.put("urgent", urgent);
        return json;
    }

    //EFFECTS: returns the task's name
    public String getTaskName() {
        return this.taskName;
    }

    //EFFECTS: returns the task's time
    public int getTaskTime() {
        return this.taskTime;
    }

    //EFFECTS: constructs the string that appears in the task list on the GUI
    public String constructTaskString() {
        return taskString;
    }



}
