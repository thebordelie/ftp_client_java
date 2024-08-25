package ru.infotecs.intern.command;

import ru.infotecs.intern.client.FtpClient;
import ru.infotecs.intern.model.Student;

import java.io.IOException;
import java.util.HashMap;

public class ExitCommand extends FtpCommand {

    public ExitCommand() {
        super("Завершение работы");
    }

    @Override
    public String execute(FtpClient client, HashMap<Long, Student> students) throws IOException {
        client.disconnect();
        return "GoodBye";
    }
}
