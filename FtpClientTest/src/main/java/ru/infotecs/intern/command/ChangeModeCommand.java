package ru.infotecs.intern.command;

import ru.infotecs.intern.client.FtpClient;
import ru.infotecs.intern.model.Student;

import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;

public class ChangeModeCommand extends FtpCommand{
    public ChangeModeCommand() {
        super("Поменять режим работы клиента (Активный или пассивный)");
    }

    @Override
    public String execute(FtpClient client, HashMap<Long, Student> students) throws IOException {
        System.out.println("Текущий режим работы клиента " + (client.isActive()? "Активный": "Пассивный"));
        Scanner scanner = new Scanner(System.in);
        System.out.println("Поменять режим?\n1. Да\n2. Нет");
        String ans = scanner.nextLine();
        if (ans.equals("1")) {
            client.setActive(!client.isActive());
            return "Успешно изменён режим на " + (client.isActive()? "Активный": "Пассивный");
        }
        else if (ans.equals("2")) return "Изменений не произошло";
        else return "Неверно выбранная опция";
    }
}
