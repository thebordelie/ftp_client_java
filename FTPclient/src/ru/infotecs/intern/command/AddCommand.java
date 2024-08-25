package ru.infotecs.intern.command;

import ru.infotecs.intern.client.FtpClient;
import ru.infotecs.intern.gui.FtpGUI;
import ru.infotecs.intern.model.Student;
import ru.infotecs.intern.util.JsonConverter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;

public class AddCommand extends FtpCommand {
    public AddCommand() {
        super("Добавление студента");
    }

    @Override
    public String execute(FtpClient client, HashMap<Long, Student> students) throws IOException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите имя");
        String name = scanner.nextLine();
        long nextId = -1;
        for (long id : students.keySet()) {
            nextId = Math.max(nextId, id);
        }
        students.put(nextId + 1, new Student(nextId + 1, name));
        JsonConverter.convertUserToJson(students, FtpGUI.fileName);
        client.sendFile(FtpGUI.fileName);
        return "Студент добавлен";
    }
}
