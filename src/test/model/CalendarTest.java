package model;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


// This class represents collection of tests for the Calendar class.
// Also implicitly tests for most of the DateList class.
public class CalendarTest {
    Date myDate;
    Calendar myCalendar;



    //Case where myDate is a date in the middle of a month and year
    @Test
    public void testGoToTomorrowMid() {
        myDate = new Date("Monday", 12, 10, 2020);
        myCalendar = new Calendar(myDate);

        myCalendar.goToTomorrow();
        assertEquals(myCalendar.viewingDate.getDayName(), "Tuesday");
        assertEquals(myCalendar.viewingDate.getDay(), 13);
        assertEquals(myCalendar.viewingDate.getMonth(), 10);
        assertEquals(myCalendar.viewingDate.getYear(), 2020);
    }


    //Case where myDate is a date at the end of a month
    @Test
    public void testGoToTomorrowEndMonth() {
        myDate = new Date("Saturday", 31, 10, 2020);
        myCalendar = new Calendar(myDate);

        myCalendar.goToTomorrow();
        assertEquals(myCalendar.viewingDate.getDayName(), "Sunday");
        assertEquals(myCalendar.viewingDate.getDay(), 1);
        assertEquals(myCalendar.viewingDate.getMonth(), 11);
        assertEquals(myCalendar.viewingDate.getYear(), 2020);
    }


    //Case where myDate is a date at the end of a year
    @Test
    public void testGoToTomorrowEndYear() {
        myDate = new Date("Thursday", 31, 12, 2020);
        myCalendar = new Calendar(myDate);

        myCalendar.goToTomorrow();
        assertEquals(myCalendar.viewingDate.getDayName(), "Friday");
        assertEquals(myCalendar.viewingDate.getDay(), 1);
        assertEquals(myCalendar.viewingDate.getMonth(), 1);
        assertEquals(myCalendar.viewingDate.getYear(), 2021);
    }

    //Case where myDate is a date in the middle of a month and year
    @Test
    public void testGoToYesterdayMid() {
        myDate = new Date("Monday", 12, 10, 2020);
        myCalendar = new Calendar(myDate);

        myCalendar.goToYesterday();
        assertEquals(myCalendar.viewingDate.getDayName(), "Sunday");
        assertEquals(myCalendar.viewingDate.getDay(), 11);
        assertEquals(myCalendar.viewingDate.getMonth(), 10);
        assertEquals(myCalendar.viewingDate.getYear(), 2020);
    }


    //Case where myDate is a date at the beginning of a month
    @Test
    public void testGoToYesterdayBegMonth() {
        myDate = new Date("Thursday", 1, 10, 2020);
        myCalendar = new Calendar(myDate);

        myCalendar.goToYesterday();
        assertEquals(myCalendar.viewingDate.getDayName(), "Wednesday");
        assertEquals(myCalendar.viewingDate.getDay(), 30);
        assertEquals(myCalendar.viewingDate.getMonth(), 9);
        assertEquals(myCalendar.viewingDate.getYear(), 2020);
    }


    //Case where myDate is a date at the beginning of a year
    @Test
    public void testGoToYesterdayBegYear() {
        myDate = new Date("Wednesday", 1, 1, 2020);
        myCalendar = new Calendar(myDate);

        myCalendar.goToYesterday();
        assertEquals(myCalendar.viewingDate.getDayName(), "Tuesday");
        assertEquals(myCalendar.viewingDate.getDay(), 31);
        assertEquals(myCalendar.viewingDate.getMonth(), 12);
        assertEquals(myCalendar.viewingDate.getYear(), 2019);
    }

    @Test
    public void testGetViewingDate() {
        myDate = new Date("Monday", 12, 10, 2020);
        myCalendar = new Calendar(myDate);

        assertEquals(myDate.dayName, myCalendar.getViewingDate().dayName);
        assertEquals(myDate.day, myCalendar.getViewingDate().day);
        assertEquals(myDate.month, myCalendar.getViewingDate().month);
        assertEquals(myDate.year, myCalendar.getViewingDate().year);
    }




}
