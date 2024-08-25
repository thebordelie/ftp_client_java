package ru.infotecs.intern.command;

import org.mockito.Mockito;
import org.testng.Assert;
import org.testng.AssertJUnit;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ru.infotecs.intern.client.FtpClient;
import ru.infotecs.intern.gui.FtpGUI;
import ru.infotecs.intern.model.Student;

import java.io.IOException;
import java.util.HashMap;

import static org.testng.Assert.assertEquals;

public class AddCommandTest extends CommandTest {
    private AddCommand addCommand;


    @BeforeClass
    public void setUp() {
        addCommand = new AddCommand();
    }

    @Test(description = "checking the addition of a new student and sending it to the server", dataProvider = "default_param")
    public void checkIncorrectId(HashMap<Long, Student> students) throws IOException {
        FtpClient ftpClient = Mockito.mock(FtpClient.class);
        int prevSize = students.size();
        simulateInputFromKeyBoard("student\n");
        assertEquals(addCommand.execute(ftpClient, students), "Студент добавлен");
        assertEquals(prevSize + 1, students.size());
        Mockito.verify(ftpClient).sendFile(FtpGUI.fileName);
    }
}
