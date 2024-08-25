package ru.infotecs.intern.gui;

import ru.infotecs.intern.client.FtpClient;
import ru.infotecs.intern.command.*;
import ru.infotecs.intern.model.Student;
import ru.infotecs.intern.util.JsonConverter;

import java.util.HashMap;
import java.util.Scanner;

public class FtpGUI {

    public static String fileName = "student.json";

    public static void main(String[] args) throws Exception {
        if (args.length != 3 && args.length != 4) throw new Exception("Incorrect number of arguments");

        String user = args[0];
        String password = args[1];
        String host = args[2];
        if (args.length == 4) fileName = args[3];

        FtpClient ftpClient = new FtpClient(host);
        ftpClient.connect(user, password);
        ftpClient.getFile(fileName);
        ftpClient.setActive(false);
        HashMap<Long, Student> students = JsonConverter.convertJsonToUser(fileName);

        HashMap<String, FtpCommand> commands = new HashMap<>();
        commands.put("1", new ShowCommand());
        commands.put("2", new GetInfoCommand());
        commands.put("3", new AddCommand());
        commands.put("4", new DeleteCommand());
        commands.put("5", new ExitCommand());
        commands.put("6", new ChangeModeCommand());


        Scanner in = new Scanner(System.in);
        while (true) {
            System.out.println("Введите номер команды");
            for (String key : commands.keySet()) {
                System.out.println(key + ". " + commands.get(key).getInfoAboutCommand());
            }

            String commandNumber = in.nextLine();
            if (!commands.containsKey(commandNumber)) {
                System.out.println("Неверный номер команды");
                continue;
            }
            System.out.println(commands.get(commandNumber).execute(ftpClient, students));
            if (commands.get(commandNumber).getClass() == ExitCommand.class) break;
        }
    }
}
