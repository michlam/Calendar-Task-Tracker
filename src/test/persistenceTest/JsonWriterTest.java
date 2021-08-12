package persistenceTest;

import model.*;
import org.junit.jupiter.api.Test;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.IOException;


import static org.junit.jupiter.api.Assertions.*;


// This class represents collection of tests for the JsonWriter class.
public class JsonWriterTest {
    Date d1 = new Date("Monday", 26, 10, 2020);


    // Test case where the write writes to a invalid file
    @Test
    void testWriterInvalidFile() {
        try {
            Calendar c = new Calendar(d1);
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }


    @Test
    void testWriterInitialCalendar() {
        try {
            Calendar cal = new Calendar(d1);
            JsonWriter writer = new JsonWriter("./data/testWriterInitialCalendar.json");
            writer.open();
            writer.write(cal);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterInitialCalendar.json");
            cal = reader.read();
            assertTrue(d1.equals(cal.getViewingDate()));

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralCalendar() {
        try {
            Calendar cal = new Calendar(d1);
            cal.getDate(0).addTask(new NormalTask("Homework", 3));
            cal.goToYesterday();
            cal.goToTomorrow();
            cal.goToTomorrow();
            assertEquals("Tuesday", cal.getViewingDate().getDayName());

            JsonWriter writer = new JsonWriter("./data/testWriterGeneralCalendar.json");
            writer.open();
            writer.write(cal);
            writer.close();
            assertEquals("Tuesday", cal.getViewingDate().getDayName());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

}
