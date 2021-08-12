package ui;

import model.*;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedList;


// CITING: https://www.javatpoint.com/java-get-current-date for finding today's date
// CITING: https://stackoverflow.com/questions/299495/how-to-add-an-image-to-a-jpanel for adding an image
// CITING: https://github.students.cs.ubc.ca/CPSC210/AlarmSystem.git for much of the GUI

// A Calendar Application
public class CalendarApp extends JFrame implements ActionListener {

    // FIELDS:

    private final JLabel label = new JLabel();
    private final JLabel dateLabel = new JLabel();
    private final JLabel taskLabel1 = new JLabel();
    private final JLabel taskLabel2 = new JLabel();
    private JTextField field;
    ImageIcon image;


    // These are fields related to the Calendar
    Date currentDate;
    Calendar myCalendar;
    String dayName;
    int day;
    int month;
    int year;
    String dateString;
    String todayString;
    String taskString1;
    String taskString2;


    //These are fields related to storing and loading data
    private static final String JSON_STORE = "./data/calendar.json";
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;


    // EFFECTS: Construct a CalendarApp (starts the program)
    public CalendarApp() {
        super("Calendar App");

        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        runCalendar();
    }


    // MODIFIES: this
    // EFFECTS: constructs the initial calendar and processes user input via the GUI
    private void runCalendar() {
        todayString = "";

        String today = java.time.LocalDate.now().toString();
        java.util.Date date = new java.util.Date();

        initializeFields(today, date);


        // Setup for the GUI window
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(700, 700));
        ((JPanel) getContentPane()).setBorder(new EmptyBorder(13, 13, 13, 13));
        setLayout(new FlowLayout());


        // Setup by initializing the buttons
        JButton buttonAdd = new JButton("Add a task");
        JButton buttonRemove = new JButton("Remove a task");
        JButton buttonYesterday = new JButton("Go to yesterday");
        JButton buttonTomorrow = new JButton("Go to tomorrow");
        JButton buttonSave = new JButton("Save calendar to file");
        JButton buttonLoad = new JButton("Load calendar from file");
        JButton buttonQuit = new JButton("Quit");

        // Assigns the buttons to their actions, allowing them to be processed
        initializeButtons(buttonAdd, buttonRemove, buttonYesterday, buttonTomorrow, buttonSave, buttonLoad, buttonQuit);


        // Handles the initial creation of the GUI
        handleInterface();

    }


    // MODIFIES: this
    // EFFECTS: Helper method that initializes many fields related to the myCalendar object
    private void initializeFields(String today, java.util.Date date) {
        String dayNameInput;
        dayNameInput = date.toString().substring(0, 3);
        dayName = processDayName(dayNameInput);
        day = Integer.parseInt(today.substring(8, 10));
        month = Integer.parseInt(today.substring(5, 7));
        year = Integer.parseInt(today.substring(0, 4));
        currentDate = new Date(dayName, day, month, year);
        myCalendar = new Calendar(currentDate);
    }


    // MODIFIES: this
    // EFFECTS: Initializes and presents the first GUI a user will see when first running the app
    private void handleInterface() {

        // Shows a welcome image on startup
        image = new ImageIcon("resources/welcome.png");
        JOptionPane.showMessageDialog(null, "", "Welcome", JOptionPane.INFORMATION_MESSAGE,
                image);

        // Prints the viewing date
        printViewingDate();
        add(dateLabel);

        label.setText("What would you like to do?");
        add(label);

        // Creates the button panel
        addButtonPanel();

        // Prints the tasks
        printTasks1();
        add(taskLabel1);
        add(taskLabel2);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);
    }


    // MODIFIES: buttonPanel
    // EFFECTS: Creates the button panel, and assigns the buttons to their appropriate processing actions
    private void addButtonPanel() {

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(2, 3));
        buttonPanel.add(new JButton(new AddTaskAction()));
        buttonPanel.add(new JButton(new RemoveTaskAction()));
        buttonPanel.add(new JButton(new YesterdayAction()));
        buttonPanel.add(new JButton(new TomorrowAction()));
        buttonPanel.add(new JButton(new SaveAction()));
        buttonPanel.add(new JButton(new LoadAction()));

        add(buttonPanel, BorderLayout.WEST);
    }


    // MODIFIES: this
    // EFFECTS: Handles the process of adding a task after pressing the associated button
    private class AddTaskAction extends AbstractAction {

        AddTaskAction() {
            super("Add a task");
        }

        @Override
        public void actionPerformed(ActionEvent evt) {
            int sensorLoc0 = JOptionPane.showConfirmDialog(null,
                    "Is this urgent?",
                    "Add a task?",
                    JOptionPane.YES_NO_OPTION);

            String sensorLoc1 = JOptionPane.showInputDialog(null,
                    "Enter the name of your task",
                    "Add a task?",
                    JOptionPane.QUESTION_MESSAGE);

            String sensorLoc2 = JOptionPane.showInputDialog(null,
                    "Enter the time of your task.\nFor example, if you have a task at 5pm, enter 17",
                    "Add a task?",
                    JOptionPane.QUESTION_MESSAGE);

            Task myTaskSet;
            int myTaskTime;
            myTaskTime = Integer.parseInt(sensorLoc2);

            if (sensorLoc0 == 1) {
                myTaskSet = new NormalTask(sensorLoc1, myTaskTime);

            } else {
                myTaskSet = new UrgentTask(sensorLoc1, myTaskTime);
            }


            myCalendar.getViewingDate().addTask(myTaskSet);

            printTasks1();


        }
    }

    // MODIFIES: this
    // EFFECTS: Handles the process of removing a task after pressing the associated button
    private class RemoveTaskAction extends AbstractAction {

        RemoveTaskAction() {
            super("Remove a task");
        }

        @Override
        public void actionPerformed(ActionEvent evt) {
            String sensorLoc1 = JOptionPane.showInputDialog(null,
                    "Enter the name of the task you wish to remove",
                    "Remove a task?",
                    JOptionPane.QUESTION_MESSAGE);


            NormalTask myNormalTask;
            int myTaskTime = 0;

            myNormalTask = new NormalTask(sensorLoc1, myTaskTime);
            myCalendar.getViewingDate().removeTask(myNormalTask);

            printTasks1();

        }
    }


    // MODIFIES: this
    // EFFECTS: Handles the process of going to yesterday after pressing the associated button
    private class YesterdayAction extends AbstractAction {

        YesterdayAction() {
            super("Go to yesterday");
        }

        @Override
        public void actionPerformed(ActionEvent evt) {
            myCalendar.goToYesterday();

            printViewingDate();
            printTasks1();
        }
    }


    // MODIFIES: this
    // EFFECTS: Handles the process of going to tomorrow after pressing the associated button
    private class TomorrowAction extends AbstractAction {

        TomorrowAction() {
            super("Go to tomorrow");
        }

        @Override
        public void actionPerformed(ActionEvent evt) {
            myCalendar.goToTomorrow();

            printViewingDate();
            printTasks1();
        }
    }

    // MODIFIES: this
    // EFFECTS: Handles the process of saving to file after pressing the associated button
    private class SaveAction extends AbstractAction {

        SaveAction() {
            super("Save calendar to file");
        }

        @Override
        public void actionPerformed(ActionEvent evt) {
            try {
                jsonWriter.open();
                jsonWriter.write(myCalendar);
                jsonWriter.close();
                JOptionPane.showMessageDialog(null, "Saved this calendar to" + JSON_STORE);

            } catch (FileNotFoundException e) {
                JOptionPane.showMessageDialog(null, "Unable to write to file: " + JSON_STORE);
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: Handles the process of loading from file after pressing the associated button
    private class LoadAction extends AbstractAction {

        LoadAction() {
            super("Load calendar from file");
        }

        @Override
        public void actionPerformed(ActionEvent evt) {
            try {
                myCalendar = jsonReader.read();

                JOptionPane.showMessageDialog(null,
                        "Loaded the previously saved calendar from " + JSON_STORE);


                printViewingDate();
                printTasks1();

            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "Unable to read from file: " + JSON_STORE);

            }
        }
    }


    // EFFECTS: Handles the process of printing the viewing date
    private void printViewingDate() {
        if (myCalendar.getViewingDate().isSameDate(currentDate)) {
            todayString = "This is today's date.";
        } else {
            todayString = "";
        }

        dateString = myCalendar.getViewingDate().getDayName() + ", "
                + getCorrespondingMonthPart1(myCalendar.getViewingDate().getMonth()) + " "
                + myCalendar.getViewingDate().getDay() + ", " + myCalendar.getViewingDate().getYear();


        dateLabel.setText(dateString + "\r\n" + todayString);
    }


    // EFFECTS: Handles the first part of printing tasks onto the window
    private void printTasks1() {
        taskString1 = "";


        if (!(myCalendar.getViewingDate().getTasks().isEmpty())) {
            taskString1 = "Here are your tasks for the day:";
        } else {
            taskString1 = "You have no tasks for today.";
        }

        taskLabel1.setText(taskString1);

        printTasks2();

    }

    // EFFECTS: Handles the second part of printing tasks onto the window
    private void printTasks2() {

        taskString2 = "";
        // These are fields related to the GUI
        LinkedList<String> taskStrings = new LinkedList<>();
        String taskStrings2 = "";

        //Goes through every hour
        for (int i = 0; i < 24; i++) {

            //Goes through every task
            for (Task t : myCalendar.getViewingDate().getTasks()) {
                if (t.getTaskTime() == i) {
                    taskString2 = t.constructTaskString();
                    taskStrings.add(taskString2);


                }
            }

        }

        for (String s : taskStrings) {
            taskStrings2 += ("\r\n" + s);
        }

        taskLabel2.setText(taskStrings2);
        taskLabel2.repaint();
    }


    // MODIFIES: this
    // EFFECTS: Assigns the buttons to their actions, allowing them to be processed
    private void initializeButtons(JButton buttonAdd, JButton buttonRemove, JButton buttonYesterday,
                                   JButton buttonTomorrow, JButton buttonSave, JButton buttonLoad, JButton buttonQuit) {
        buttonAdd.setActionCommand("add");
        buttonAdd.addActionListener(this);

        buttonRemove.setActionCommand("remove");
        buttonRemove.addActionListener(this);

        buttonYesterday.setActionCommand("yesterday");
        buttonYesterday.addActionListener(this);

        buttonTomorrow.setActionCommand("tomorrow");
        buttonTomorrow.addActionListener(this);

        buttonSave.setActionCommand("save");
        buttonSave.addActionListener(this);

        buttonLoad.setActionCommand("load");
        buttonLoad.addActionListener(this);

        buttonQuit.setActionCommand("quit");
        buttonQuit.addActionListener(this);
    }


    //EFFECTS: Given the short form day name added, return the full day name
    private String processDayName(String c) {
        if (c.equals("Mon")) {
            return "Monday";
        } else if (c.equals("Tue")) {
            return "Tuesday";
        } else if (c.equals("Wed")) {
            return "Wednesday";
        } else if (c.equals("Thu")) {
            return "Thursday";
        } else if (c.equals("Fri")) {
            return "Friday";
        } else if (c.equals("Sat")) {
            return "Saturday";
        } else {
            return "Sunday";
        }
    }


    //REQUIRES: 1 <= i <= 12
    //EFFECTS: given the month denoted by a number, return the corresponding month's name
    private String getCorrespondingMonthPart1(int i) {
        switch (i) {
            case 1:
                return "January";
            case 2:
                return "February";
            case 3:
                return "March";
            case 4:
                return "April";
            case 5:
                return "May";
            case 6:
                return "June";
            default:
                return getCorrespondingMonthPart2(i);
        }
    }


    //REQUIRES: 7 <= i <= 12
    //EFFECTS: given the month denoted by a number, return the corresponding month's name
    private String getCorrespondingMonthPart2(int i) {
        switch (i) {
            case 7:
                return "July";
            case 8:
                return "August";
            case 9:
                return "September";
            case 10:
                return "October";
            case 11:
                return "November";
            default:
                return "December";
        }
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("myButton")) {
            label.setText(field.getText());
        }
    }


}
