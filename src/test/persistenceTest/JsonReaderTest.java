package persistenceTest;

import model.*;
import org.junit.jupiter.api.Test;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;


// This class represents collection of tests for the JsonReader class.
public class JsonReaderTest {
    Date d1 = new Date("Monday", 26, 10, 2020);

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            Calendar cal = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderInitialCalendar() {

        try {
            Calendar cal = new Calendar(d1);
            JsonWriter writer = new JsonWriter("./data/testReaderInitialCalendar.json");
            writer.open();
            writer.write(cal);
            writer.close();


            JsonReader reader = new JsonReader("./data/testReaderInitialCalendar.json");

            Calendar myCal = reader.read();
            assertTrue(d1.equals(cal.getViewingDate()));
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }


    @Test
    void testReaderGeneralWorkRoom() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralWorkRoom.json");
        try {
            Calendar cal = new Calendar(d1);
            cal.getDate(0).addTask(new NormalTask("Homework", 3));
            cal.goToYesterday();
            cal.goToTomorrow();
            cal.goToTomorrow();
            assertEquals("Tuesday", cal.getViewingDate().getDayName());
            cal.getViewingDate().addTask(new NormalTask("Homework", 3));

            JsonWriter writer = new JsonWriter("./data/testReaderGeneralWorkRoom.json");
            writer.open();
            writer.write(cal);
            writer.close();



            Calendar myCal = reader.read();
            assertEquals("Tuesday", cal.getViewingDate().getDayName());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }


}














