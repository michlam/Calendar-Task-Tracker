package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.LinkedList;


// This class represents the main Calendar functionality of the application. It holds a list of generated dates,
// and also knows the current date.
public class Calendar implements Writable {
    //FIELDS:
    LinkedList<Date> dateList;
    Date viewingDate;



    //EFFECTS: Constructs a calendar object whose dateList contains only the current date
    public Calendar(Date currentDate) {
        this.viewingDate = new Date(currentDate.getDayName(), currentDate.getDay(),
                currentDate.getMonth(), currentDate.getYear());
        dateList = new LinkedList<>();
        this.addDate(viewingDate);

    }

    //MODIFIES: this
    //EFFECTS: If yesterday has been created, go to it. Otherwise, create it, then go to it.
    public void goToYesterday() {
        Date yesterday = viewingDate.createYesterdayDate();

        if (!this.containsDate(yesterday)) {
            this.addDate(this.getIndex(viewingDate), yesterday);
        }

        viewingDate = this.getDate(this.getIndex(viewingDate) - 1);
    }



    //MODIFIES: this
    //EFFECTS: If tomorrow has been created, go to it. Otherwise, create it, then go to it.
    public void goToTomorrow() {
        Date tomorrow = viewingDate.createTomorrowDate();

        if (!this.containsDate(tomorrow)) {
            this.addDate(this.getIndex(viewingDate) + 1, tomorrow);
        }

        viewingDate = this.getDate(this.getIndex(viewingDate) + 1);
    }


    //EFFECTS: returns the calendar as a JSONObject
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("date list", dateListToJson());
        json.put("viewing date", viewingDateToJson());
        return json;
    }


    //EFFECTS: returns the viewing date as a JSONObject
    private JSONObject viewingDateToJson() {
        JSONObject json = new JSONObject();
        json.put("viewing day name", viewingDate.getDayName());
        json.put("viewing day", viewingDate.getDay());
        json.put("viewing month", viewingDate.getMonth());
        json.put("viewing year", viewingDate.getYear());
        json.put("viewing tasks", viewingDate.tasksToJson());

        return json;
    }


    // EFFECTS: returns date lists in this calendar as a JSON array
    private JSONArray dateListToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Date d : dateList) {
            jsonArray.put(d.toJson());
        }

        return jsonArray;
    }

    //REQUIRES: The date must be valid.
    //MODIFIES: this
    //EFFECTS: Adds a date to this date list.
    public void addDate(Date d) {
        dateList.add(d);
    }

    //REQUIRES: This date must be valid.
    //MODIFIES: this
    //EFFECTS: Adds a date to this date list at the specific index n given.
    public void addDate(int n, Date d) {
        dateList.add(n, d);
    }

    //EFFECTS: Returns the Date at the specified index n
    public Date getDate(int n) {
        return dateList.get(n);
    }

    //EFFECTS: Returns the index of the Date given
    public int getIndex(Date d) {
        return dateList.indexOf(d);
    }

    //EFFECTS: Returns true if the Date is in the list
    public boolean containsDate(Date date) {
        boolean contains = false;

        for (Date d: dateList) {
            if (d.equals(date)) {
                contains = true;
            }
        }

        return contains;
    }


    //SIMPLE GETTERS

    //EFFECTS: Returns the viewingDate
    public Date getViewingDate() {
        return this.viewingDate;
    }

    //MODIFIES: this
    //EFFECTS: Assigns viewing date to d
    public void setViewingDate(Date d) {
        this.viewingDate = d;
    }

}
