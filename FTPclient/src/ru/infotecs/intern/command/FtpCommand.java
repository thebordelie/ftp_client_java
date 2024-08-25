package ru.infotecs.intern.command;

import ru.infotecs.intern.client.FtpClient;
import ru.infotecs.intern.model.Student;

import java.io.IOException;
import java.util.HashMap;

public abstract class FtpCommand {
    private final String infoAboutCommand;

    public String getInfoAboutCommand() {
        return infoAboutCommand;
    }

    public FtpCommand(String infoAboutCommand) {
        this.infoAboutCommand = infoAboutCommand;
    }

    abstract public String execute(FtpClient client, HashMap<Long, Student> students) throws IOException;
}
