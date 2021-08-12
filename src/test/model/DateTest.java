package model;

import org.junit.jupiter.api.*;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.*;


// This class represents a collection of tests for the Date class
public class DateTest {
    private Date myDate;
    private NormalTask myNormalTask;

    @BeforeEach

    public void runBefore() {
        myDate = new Date("Friday", 16, 10, 2020);
    }

    @Test
    public void testConstructor() {
        assertEquals("Friday", myDate.getDayName());
        assertEquals(16, myDate.getDay());
        assertEquals(10, myDate.getMonth());
        assertEquals(2020, myDate.getYear());
        assertTrue(myDate.isLeap);
    }


    //All the tests for getYesterdayDate() -----------------------------------------------------------------------------

    //Case that tests a date in the middle of a month
    @Test
    public void testGetYesterdayDateMidCase() {
        Date yesterday = new Date("Thursday", 15, 10, 2020);
        myDate = myDate.createYesterdayDate();

        assertEquals(yesterday.dayName, myDate.dayName);
        assertEquals(yesterday.day, myDate.day);
        assertEquals(yesterday.month, myDate.month);
        assertEquals(yesterday.year, myDate.year);
    }


    //Case that tests when first day of a month, and the previous month had 31 days
    @Test
    public void testGetYesterdayDate31() {
        Date yesterday = new Date("Saturday", 31, 10, 2020);
        Date today = new Date("Sunday", 1, 11, 2020);
        today = today.createYesterdayDate();

        assertEquals(yesterday.dayName, today.dayName);
        assertEquals(yesterday.day, today.day);
        assertEquals(yesterday.month, today.month);
        assertEquals(yesterday.year, today.year);
    }

    //Case that tests when first day of a month, and the previous month had 30 days
    @Test
    public void testGetYesterdayDate30() {
        Date yesterday = new Date("Wednesday", 30, 9, 2020);
        Date today = new Date("Thursday", 1, 10, 2020);
        today = today.createYesterdayDate();

        assertEquals(yesterday.dayName, today.dayName);
        assertEquals(yesterday.day, today.day);
        assertEquals(yesterday.month, today.month);
        assertEquals(yesterday.year, today.year);
    }

    //Case that tests when first day of March on a non-leap year
    @Test
    public void testGetYesterdayDate28() {
        Date yesterday = new Date("Thursday", 28, 2, 2019);
        Date today = new Date("Friday", 1, 3, 2019);
        today = today.createYesterdayDate();

        assertEquals(yesterday.dayName, today.dayName);
        assertEquals(yesterday.day, today.day);
        assertEquals(yesterday.month, today.month);
        assertEquals(yesterday.year, today.year);
    }

    //Case that tests when first day of March on a leap year
    @Test
    public void testGetYesterdayDate29() {
        Date yesterday = new Date("Saturday", 29, 2, 2020);
        Date today = new Date("Sunday", 1, 3, 2020);
        today = today.createYesterdayDate();

        assertEquals(yesterday.dayName, today.dayName);
        assertEquals(yesterday.day, today.day);
        assertEquals(yesterday.month, today.month);
        assertEquals(yesterday.year, today.year);
    }

    //Case that tests when January 1
    @Test
    public void testGetYesterdayDateJan1() {
        Date yesterday = new Date("Tuesday", 31, 12, 2019);
        Date today = new Date("Wednesday", 1, 1, 2020);
        today = today.createYesterdayDate();

        assertEquals(yesterday.dayName, today.dayName);
        assertEquals(yesterday.day, today.day);
        assertEquals(yesterday.month, today.month);
        assertEquals(yesterday.year, today.year);
    }


    @Test
    public void testGetYesterdayDayNameSun() {
        Date today = new Date("Monday", 12, 10, 2020);
        Date yesterday = new Date("Sunday", 11, 10, 2020);
        today = today.createYesterdayDate();

        assertEquals(yesterday.dayName, today.dayName);
        assertEquals(yesterday.day, today.day);
        assertEquals(yesterday.month, today.month);
        assertEquals(yesterday.year, today.year);
    }

    @Test
    public void testGetYesterdayDayNameMon() {
        Date today = new Date("Tuesday", 13, 10, 2020);
        Date yesterday = new Date("Monday", 12, 10, 2020);
        today = today.createYesterdayDate();

        assertEquals(yesterday.dayName, today.dayName);
        assertEquals(yesterday.day, today.day);
        assertEquals(yesterday.month, today.month);
        assertEquals(yesterday.year, today.year);
    }

    @Test
    public void testGetYesterdayDayNameFri() {
        Date today = new Date("Saturday", 10, 10, 2020);
        Date yesterday = new Date("Friday", 9, 10, 2020);
        today = today.createYesterdayDate();

        assertTrue(today.isSameDate(yesterday));
    }


    //All the tests for getTomorrowDate() -----------------------------------------------------------------------------

    //Case that tests a date in the middle of a month
    @Test
    public void testGetTomorrowDateMidCase() {
        Date tomorrow = new Date("Saturday", 17, 10, 2020);
        myDate = myDate.createTomorrowDate();

        assertEquals(tomorrow.dayName, myDate.dayName);
        assertEquals(tomorrow.day, myDate.day);
        assertEquals(tomorrow.month, myDate.month);
        assertEquals(tomorrow.year, myDate.year);
    }


    //Case that tests when last day of a month with 31 days
    @Test
    public void testGetTomorrowDate31() {
        Date today = new Date("Saturday", 31, 10, 2020);
        Date tomorrow = new Date("Sunday", 1, 11, 2020);
        today = today.createTomorrowDate();

        assertEquals(today.dayName, tomorrow.dayName);
        assertEquals(today.day, tomorrow.day);
        assertEquals(today.month, tomorrow.month);
        assertEquals(today.year, tomorrow.year);
    }

    //Case that tests when last day of a month with 30 days
    @Test
    public void testGetTomorrowDate30() {
        Date today = new Date("Wednesday", 30, 9, 2020);
        Date tomorrow = new Date("Thursday", 1, 10, 2020);
        today = today.createTomorrowDate();

        assertEquals(today.dayName, tomorrow.dayName);
        assertEquals(today.day, tomorrow.day);
        assertEquals(today.month, tomorrow.month);
        assertEquals(today.year, tomorrow.year);
    }

    //Case that tests when last day of February on a non leap year
    @Test
    public void testGetTomorrowDate28() {
        Date today = new Date("Thursday", 28, 2, 2019);
        Date tomorrow = new Date("Friday", 1, 3, 2019);
        today = today.createTomorrowDate();

        assertEquals(today.dayName, tomorrow.dayName);
        assertEquals(today.day, tomorrow.day);
        assertEquals(today.month, tomorrow.month);
        assertEquals(today.year, tomorrow.year);
    }

    //Case that tests when last day of February on a leap year
    @Test
    public void testGetTomorrowDate29() {
        Date today = new Date("Saturday", 29, 2, 2020);
        Date tomorrow = new Date("Sunday", 1, 3, 2020);
        today = today.createTomorrowDate();

        assertEquals(today.dayName, tomorrow.dayName);
        assertEquals(today.day, tomorrow.day);
        assertEquals(today.month, tomorrow.month);
        assertEquals(today.year, tomorrow.year);
    }

    //Case that tests when last day of a year
    @Test
    public void testGetTomorrowDateEnd() {
        Date today = new Date("Thursday", 31, 12, 2020);
        Date tomorrow = new Date("Friday", 1, 1, 2021);
        today = today.createTomorrowDate();

        assertEquals(today.dayName, tomorrow.dayName);
        assertEquals(today.day, tomorrow.day);
        assertEquals(today.month, tomorrow.month);
        assertEquals(today.year, tomorrow.year);
    }



    @Test
    public void testGetTomorrowDayNameTue() {
        Date today = new Date("Monday", 12, 10, 2020);
        Date tomorrow = new Date("Tuesday", 13, 10, 2020);
        today = today.createTomorrowDate();

        assertTrue(today.isSameDate(tomorrow));
    }

    @Test
    public void testGetTomorrowDayNameWed() {
        Date today = new Date("Tuesday", 13, 10, 2020);
        Date tomorrow = new Date("Wednesday", 14, 10, 2020);
        today = today.createTomorrowDate();

        assertTrue(today.isSameDate(tomorrow));
    }

    @Test
    public void testGetTomorrowDayNameMon() {
        Date today = new Date("Sunday", 11, 10, 2020);
        Date tomorrow = new Date("Monday", 12, 10, 2020);
        today = today.createTomorrowDate();

        assertTrue(today.isSameDate(tomorrow));
    }


    @Test
    public void testAddTask() {
        myNormalTask = new NormalTask("Homework", 17);
        myDate.addTask(myNormalTask);

        assertEquals("Homework", myDate.tasks.getFirst().getTaskName());
        assertEquals(17, myDate.tasks.getFirst().getTaskTime());
    }


    @Test
    public void testRemoveTaskIsThere() {
        myNormalTask = new NormalTask("Homework", 17);
        myDate.addTask(myNormalTask);

        assertEquals("Homework", myDate.getTasks().getFirst().getTaskName());
        assertEquals(17, myDate.tasks.getFirst().getTaskTime());

        myDate.removeTask(myNormalTask);
        assertTrue(myDate.tasks.isEmpty());
    }

    @Test
    public void testRemoveTaskIsNotThere() {
        myNormalTask = new NormalTask("Homework", 17);
        NormalTask myNormalTask2 = new NormalTask("work", 15);
        myDate.addTask(myNormalTask);

        assertEquals("Homework", myDate.getTasks().getFirst().getTaskName());
        assertEquals(17, myDate.tasks.getFirst().getTaskTime());

        myDate.removeTask(myNormalTask2);
        assertFalse(myDate.tasks.isEmpty());
    }




    @Test
    public void testIsSameDateFalse() {
        Date newDate = myDate.createTomorrowDate();
        Date newDate2 = new Date("Friday", 29, 2, 2016);

        assertFalse(myDate.isSameDate(newDate));
        assertFalse(myDate.isSameDate(newDate2));
    }

    @Test
    public void testIsEqual() {
        Date newDate0 = new Date("Friday", 29, 2, 2016);
        Date newDate1 = new Date("Friday", 29, 2, 2016);
        Date newDate2 = new Date("Monday", 29, 2, 2016);
        Date newDate3 = new Date("Friday", 30, 2, 2016);
        Date newDate4 = new Date("Friday", 29, 3, 2016);
        Date newDate5 = new Date("Friday", 29, 2, 2017);

        assertTrue(newDate0.equals(newDate1));
        assertFalse(newDate0.equals(newDate2));
        assertFalse(newDate0.equals(newDate3));
        assertFalse(newDate0.equals(newDate4));
        assertFalse(newDate0.equals(newDate5));
    }

    @Test
    public void testHashCode() {
        Date newDate0 = new Date("Friday", 29, 2, 2016);
        Date newDate1 = new Date("Friday", 29, 2, 2016);
        assertEquals(newDate0.hashCode(), newDate1.hashCode());
    }

}






