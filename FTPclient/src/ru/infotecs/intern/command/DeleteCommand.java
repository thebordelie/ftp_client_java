package ru.infotecs.intern.command;

import ru.infotecs.intern.client.FtpClient;
import ru.infotecs.intern.gui.FtpGUI;
import ru.infotecs.intern.model.Student;
import ru.infotecs.intern.util.JsonConverter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;

public class DeleteCommand extends FtpCommand {
    public DeleteCommand() {
        super("Удаление студента по id");
    }

    @Override
    public String execute(FtpClient client, HashMap<Long, Student> students) throws IOException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите id");
        try {
            long id = Long.parseLong(scanner.nextLine());
            if (!students.containsKey(id)) return "Проверьте id, студент не найден";
            else {
                students.remove(id);
                JsonConverter.convertUserToJson(students, FtpGUI.fileName);
                client.sendFile(FtpGUI.fileName);
                return "Успешно удалён";
            }
        } catch (NumberFormatException e) {
            return "Неверно введён id";
        }


    }
}
