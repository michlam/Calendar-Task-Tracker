package model;

import persistence.Writable;


// Represents an urgent task that needs to be done and requires extra attention
public class UrgentTask extends Task implements Writable {

    //EFFECTS: Constructs a task with a name, date, and time
    public UrgentTask(String name, int time) {
        super(name, time);
        urgent = true;
    }


    //EFFECTS: constructs the string that appears in the task list on the GUI
    @Override
    public String constructTaskString() {

        taskString = ("\nURGENT! -> " + this.getTaskTime() + ":00 -> " + this.getTaskName());
        return taskString;

    }

}
