package ru.infotecs.intern.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.testng.annotations.Test;
import ru.infotecs.intern.model.Student;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import static org.testng.Assert.assertEquals;

public class JsonConverterTest {
    private final String jsonFileName = "students_test.json";
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test(description = "checking for the conversion of a json file to students")
    public void checkConvertJsonToUser() throws JsonProcessingException {
        String jsonStudent = "{\"id\": 4, \n\"name\":\"student1\"\n}";
        jsonStudent = jsonStudent.trim().replaceAll("\\s+", "");
        Student expectedStudent = objectMapper.readValue(jsonStudent, Student.class);
        Student actualStudent = JsonConverter.parseData(jsonStudent).get(4L);
        assertEquals(actualStudent, expectedStudent);
    }

    @Test(description = "checking for the conversion of students to a json file")
    public void checkConvertUserToJson() throws IOException {
        HashMap<Long, Student> student = new HashMap<>();
        student.put(4L, new Student(4, "student"));
        JsonConverter.convertUserToJson(student, jsonFileName);
        try (BufferedReader reader = new BufferedReader(new FileReader(jsonFileName))) {
            StringBuilder fileData = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                fileData.append(line).append("\n");

            }
            List<Student> students = new ArrayList<>(student.values());
            assertEquals(fileData.toString().trim().replaceAll("\\s+", ""), "{\"students\":" + objectMapper.writeValueAsString(students) + "}");
        }
    }
}
