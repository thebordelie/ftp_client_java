package ru.infotecs.intern.command;

import ru.infotecs.intern.client.FtpClient;
import ru.infotecs.intern.model.Student;

import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;

public class GetInfoCommand extends FtpCommand {
    public GetInfoCommand() {
        super("Получение информации о студенте по id ");
    }

    @Override
    public String execute(FtpClient client, HashMap<Long, Student> students) throws IOException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите id");
        try {
            long id = Long.parseLong(scanner.nextLine());
            if (!students.containsKey(id)) return "Проверьте id, студент не найден";
            return "name: " + students.get(id).getName();
        } catch (NumberFormatException e) {
            return "Неверно введён id";
        }
    }
}
