package model;


import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.LinkedList;


// This class represents a single date, each with a day name, day number, month, and year. Every date also has
// its own list of tasks.
public class Date implements Writable {
    String dayName;
    int day;
    int month;
    int year;
    LinkedList<Task> tasks;
    boolean isLeap;


    //EFFECTS: Constructs a date with a day name (Monday, Tuesday, etc), day, month, and year. The task list is empty.
    public Date(String dayName, int day, int month, int year) {
        this.dayName = dayName;
        this.day = day;
        this.month = month;
        this.year = year;
        this.tasks = new LinkedList<>();

        isLeap = (this.year % 4) == 0;
    }


    // EFFECTS: returns true if the fields of the two Dates in comparison are the same
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Date date = (Date) o;

        if (day != date.day) {
            return false;
        }
        if (month != date.month) {
            return false;
        }
        if (year != date.year) {
            return false;
        }
        return dayName != null ? dayName.equals(date.dayName) : date.dayName == null;
    }

    @Override
    public int hashCode() {
        int result = dayName != null ? dayName.hashCode() : 0;
        result = 31 * result + day;
        result = 31 * result + month;
        result = 31 * result + year;
        return result;
    }


    //MODIFIES: this
    //EFFECTS: Changes the date given into the previous date
    public Date createYesterdayDate() {
        String lastDayName = this.getYesterdayDayName();
        int lastDay = this.getYesterdayDay();
        int lastMonth = this.month;
        int lastYear = this.year;


        if (this.day == 1) {
            lastMonth = this.getYesterdayMonth();
        }

        if (this.month == 1 && this.day == 1) {
            lastYear = this.getYesterdayYear();
        }

        return new Date(lastDayName, lastDay, lastMonth, lastYear);
    }


    //REQUIRES: The day name must be a valid day name
    //EFFECTS: Given the day name returns the previous day name
    public String getYesterdayDayName() {
        if (this.dayName.equals("Monday")) {
            return "Sunday";
        } else if (this.dayName.equals("Tuesday")) {
            return "Monday";
        } else if (this.dayName.equals("Wednesday")) {
            return "Tuesday";
        } else if (this.dayName.equals("Thursday")) {
            return "Wednesday";
        } else if (this.dayName.equals("Friday")) {
            return "Thursday";
        } else if (this.dayName.equals("Saturday")) {
            return "Friday";
        } else {
            return "Saturday";
        }
    }


    //REQUIRES: the day has to be valid
    //MODIFIES: this
    //EFFECTS: gets the previous day number. If it is 1, finds the max day number of the previous month.
    // Checks for leap year as well.
    public int getYesterdayDay() {
        int lastMonth = this.getYesterdayMonth();

        //First day of the month
        if (this.day == 1) {
            //Check days with 31 days
            if ((lastMonth == 1) || (lastMonth == 3) || (lastMonth == 5) || (lastMonth == 7) || (lastMonth == 8)
                    || (lastMonth == 10) || (lastMonth == 12)) {
                return 31;

                //Check days with 30 days
            } else if ((lastMonth == 4) || (lastMonth == 6) || (lastMonth == 9) || (lastMonth == 11)) {
                return 30;
            } else {
                if (isLeap) {
                    return 29;
                } else {
                    return 28;
                }
            }
        } else { //Not first day of month
            return (this.day - 1);
        }
    }

    //REQUIRES: the month has to be valid
    //MODIFIES: this
    //EFFECTS: gets the previous month. If it is January, becomes December.
    public int getYesterdayMonth() {

        if (this.month == 1) {
            return 12;
        } else {
            return (this.month - 1);
        }
    }

    //REQUIRES: Only takes years in Common Era(C.E.). Does not take years Before Common Era(B.C.E.)
    //MODIFIES: this
    //EFFECTS: subtract the year
    public int getYesterdayYear() {
        return (this.year - 1);

    }


    //MODIFIES: this
    //EFFECTS: Changes the date given into the next date
    public Date createTomorrowDate() {
        String nextDayName = this.getTomorrowDayName();
        int nextDay = this.getTomorrowDay();
        int nextMonth = this.month;
        int nextYear = this.year;

        if (this.day == this.getNumberOfDays(this.month)) {
            nextMonth = this.getTomorrowMonth();
        }

        if (this.month == 12 && this.day == 31) {
            nextYear = this.getTomorrowYear();
        }


        return new Date(nextDayName, nextDay, nextMonth, nextYear);
    }


    //REQUIRES: The day name must be a valid day name
    //EFFECTS: Given the day name returns the next day name
    public String getTomorrowDayName() {
        if (this.dayName.equals("Monday")) {
            return "Tuesday";
        } else if (this.dayName.equals("Tuesday")) {
            return "Wednesday";
        } else if (this.dayName.equals("Wednesday")) {
            return "Thursday";
        } else if (this.dayName.equals("Thursday")) {
            return "Friday";
        } else if (this.dayName.equals("Friday")) {
            return "Saturday";
        } else if (this.dayName.equals("Saturday")) {
            return "Sunday";
        } else {
            return "Monday";
        }
    }


    //REQUIRES: the day has to be valid
    //MODIFIES: this
    //EFFECTS: gets the previous day number. If it the last day of month, returns 1. Checks for leap year as well.
    public int getTomorrowDay() {
        //Months with 31 days
        if (((this.month == 1) || (this.month == 3) || (this.month == 5) || (this.month == 7) || (this.month == 8)
                || (this.month == 10) || (this.month == 12)) && this.day == 31) {
            return 1;
            //Months with 30 days
        } else if (((this.month == 4) || (this.month == 6) || (this.month == 9) || (this.month == 11))
                && (this.day == 30)) {
            return 1;
            //Handle February
        } else if ((this.month == 2) && ((isLeap && this.day == 29) || (!isLeap && this.day == 28))) {
            return 1;
        } else {
            return (this.day + 1);
        }
    }


    //REQUIRES: the month has to be valid
    //MODIFIES: this
    //EFFECTS: gets the next month. If it is December, becomes January.
    public int getTomorrowMonth() {
        if (this.month == 12) {
            return 1;
        } else {
            return (this.month + 1);
        }
    }


    //REQUIRES: Only takes years in Common Era(C.E.). Does not take years Before Common Era(B.C.E.)
    //MODIFIES: this
    //EFFECTS: step the year
    public int getTomorrowYear() {
        return (this.year + 1);
    }


    //________________________________________________________________________________________________________


    //EFFECTS: Returns the number of days in the month, given the month and year
    public int getNumberOfDays(int viewingMonth) {
        if ((viewingMonth == 1) || (viewingMonth == 3) || (viewingMonth == 5) || (viewingMonth == 7)
                || (viewingMonth == 8) || (viewingMonth == 10) || (viewingMonth == 12)) {
            return 31;
        } else if ((viewingMonth == 4) || (viewingMonth == 6) || (viewingMonth == 9) || (viewingMonth == 11)) {
            return 30;
        } else {
            {
                if (isLeap) {
                    return 29;
                } else {
                    return 28;
                }
            }
        }
    }


    //EFFECTS: Returns true if the dates are the same
    public boolean isSameDate(Date d) {
        boolean b1 = (this.dayName.equals(d.dayName));
        boolean b2 = (this.day == d.day);
        boolean b3 = (this.month == d.month);
        boolean b4 = (this.year == d.year);

        return (b1 && b2 && b3 && b4);
    }


    //REQUIRES: The task time must be valid.
    //MODIFIES: this
    //EFFECTS: Adds a task to this task list.
    public void addTask(Task t) {
        tasks.add(t);
    }


    //MODIFIES: this
    //EFFECTS: If the task is in the list, removes every instance of it
    public void removeTask(Task t) {
        for (Task normalTask : tasks) {
            if (normalTask.getTaskName().equals(t.getTaskName())) {
                tasks.remove(normalTask);
            }
        }
    }


    // EFFECTS: returns the date as a JSONObject
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("day name", dayName);
        json.put("day", day);
        json.put("month", month);
        json.put("year", year);
        json.put("tasks", tasksToJson());
        return json;
    }

    // EFFECTS: returns tasks in this Date as a JSON array
    protected JSONArray tasksToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Task t : tasks) {
            jsonArray.put(t.toJson());
        }

        return jsonArray;
    }


    //GETTERS

    //EFFECTS: Returns the date's day name
    public String getDayName() {
        return this.dayName;
    }

    //EFFECTS: Returns the date's day
    public int getDay() {
        return this.day;
    }

    //EFFECTS: Returns the date's month
    public int getMonth() {
        return this.month;
    }

    //EFFECTS: Returns the date's year
    public int getYear() {
        return this.year;
    }

    //EFFECTS: Returns the date's task list
    public LinkedList<Task> getTasks() {
        return this.tasks;
    }




}
