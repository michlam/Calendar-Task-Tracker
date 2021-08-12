package persistence;

import model.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;


import org.json.*;

// This class represents a reader that reads JSON representation of Calendar from file
// CITING: code is modeled on https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads calendar from file and returns it;
    // throws IOException if an error occurs reading data from file
    public Calendar read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseCalendar(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses calendar from JSON object and returns it
    private Calendar parseCalendar(JSONObject jsonObject) {
        Date myDate = new Date("Monday", 1, 1, 1000);
        Calendar cal = new Calendar(myDate);

        addDates(cal, jsonObject);
        addViewingDate1(cal, jsonObject);
        return cal;
    }


    // MODIFIES: cal
    // EFFECTS: parses viewing date from JSON object and adds it to calendar
    private void addViewingDate1(Calendar cal, JSONObject jsonObject) {
        JSONObject viewingDate = jsonObject.getJSONObject("viewing date");
        addViewingDate2(cal, viewingDate);
    }


    // MODIFIES: cal
    // EFFECTS: continues parsing viewing date from JSON object and adds it to calendar
    private void addViewingDate2(Calendar cal, JSONObject jsonObject) {
        String dayName = jsonObject.getString("viewing day name");
        int day = jsonObject.getInt("viewing day");
        int month = jsonObject.getInt("viewing month");
        int year = jsonObject.getInt("viewing year");

        Date date = new Date(dayName, day, month, year);

        addViewingTasks(date, jsonObject);
        cal.setViewingDate(date);
    }

    // MODIFIES: cal
    // EFFECTS: parses viewing tasks from JSON object and adds them to calendar
    private void addViewingTasks(Date date, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("viewing tasks");
        for (Object json : jsonArray) {
            JSONObject nextTask = (JSONObject) json;
            addTask(date, nextTask);
        }
    }

    // MODIFIES: cal
    // EFFECTS: parses dates from JSON object and adds them to calendar
    private void addDates(Calendar cal, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("date list");
        for (Object json : jsonArray) {
            JSONObject nextDate = (JSONObject) json;
            addDate(cal, nextDate);
        }
    }

    // MODIFIES: cal
    // EFFECTS: parses date from JSON object and adds it to calendar
    private void addDate(Calendar cal, JSONObject jsonObject) {
        String dayName = jsonObject.getString("day name");
        int day = jsonObject.getInt("day");
        int month = jsonObject.getInt("month");
        int year = jsonObject.getInt("year");

        Date date = new Date(dayName, day, month, year);

        addTasks(date, jsonObject);
        cal.addDate(date);
    }

    // MODIFIES: cal
    // EFFECTS: parses tasks from JSON object and adds it to calendar
    private void addTasks(Date date, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("tasks");
        for (Object json : jsonArray) {
            JSONObject nextTask = (JSONObject) json;
            addTask(date, nextTask);
        }

    }

    // MODIFIES: cal
    // EFFECTS: parses task from JSON object and adds it to calendar
    private void addTask(Date date, JSONObject jsonObject) {
        String taskName = jsonObject.getString("task name");
        int taskTime = jsonObject.getInt("task time");
        boolean urgent = jsonObject.getBoolean("urgent");
        Task myNewTask;
        if (urgent) {
            myNewTask  = new UrgentTask(taskName, taskTime);
        } else {
            myNewTask = new NormalTask(taskName, taskTime);
        }
        date.addTask(myNewTask);
    }

}
