package ru.infotecs.intern.util;

import ru.infotecs.intern.model.Student;

import java.io.*;
import java.util.HashMap;

public class JsonConverter {
    public static HashMap<Long, Student> convertJsonToUser(String fileName) throws IOException {
        String data = readFile(fileName);
        data = data.trim();
        data = data.substring(data.indexOf("[") + 1, data.indexOf("]")).replaceAll("\\s+", "");

        HashMap<Long, Student> students = new HashMap<>();
        String[] users = data.split("},");
        for (String user : users) {
            String[] keyValues = user.replace("{", "").replace("}", "").split(",");
            Student student = new Student();
            for (String keyValue : keyValues) {
                String[] key = keyValue.replace("\"", "").split(":");
                if (key[0].equals("id")) {
                    student.setId(Long.parseLong(key[1]));
                }
                if (key[0].equals("name")) {
                    student.setName(key[1]);
                }
            }
            students.put(student.getId(), student);
        }
        return students;
    }

    public static void convertUserToJson(HashMap<Long, Student> students, String fileName) throws IOException {
        StringBuilder stringBuilder = new StringBuilder("{\n\t\"students\": [");
        for (long id : students.keySet()) {
            stringBuilder.append(
                    String.format(
                            "\n\t\t{\n\t\t\t\"id\": %d, \n\t\t\t\"name\":\"%s\"\n\t\t},",
                            id,
                            students.get(id).getName()));
        }
        stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        stringBuilder.append("\n\t]\n}");
        writeJson(stringBuilder.toString(), fileName);
    }

    private static void writeJson(String json, String fileName) throws IOException {
        FileWriter writer = new FileWriter(fileName);
        writer.write(json);
        writer.close();
    }

    public static String readFile(String fileName) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        try (FileReader reader = new FileReader(fileName)) {
            int i;
            while ((i = reader.read()) != -1) {
                stringBuilder.append((char) i);
            }
            return stringBuilder.toString();

        }
    }
}
