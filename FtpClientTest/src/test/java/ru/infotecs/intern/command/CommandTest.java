package ru.infotecs.intern.command;

import org.testng.annotations.AfterTest;
import org.testng.annotations.DataProvider;
import ru.infotecs.intern.model.Student;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;

public class CommandTest {
    private static final InputStream INPUT_STREAM = System.in;
    private static final OutputStream OUTPUT_STREAM = System.out;

    @DataProvider(name = "default_param")
    public static Object[] params() {
        HashMap<Long, Student> students = new HashMap<>();
        students.put(1L, new Student(1, "student1"));
        students.put(2L, new Student(2, "Andrey"));
        students.put(3L, new Student(3, "Katya"));
        return new Object[]{students};
    }

    public static void simulateInputFromKeyBoard(String input) {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(input.getBytes());
        System.setIn(byteArrayInputStream);
        System.setOut(new PrintStream(new ByteArrayOutputStream()));
    }

    @AfterTest
    public static void tearDown() {
        System.setIn(INPUT_STREAM);
        System.setOut(new PrintStream(OUTPUT_STREAM));
    }
}
