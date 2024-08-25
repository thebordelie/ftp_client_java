package ru.infotecs.intern.command;

import org.mockito.Mock;
import org.mockito.Mockito;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ru.infotecs.intern.client.FtpClient;
import ru.infotecs.intern.model.Student;

import java.io.IOException;
import java.util.HashMap;

import static org.testng.Assert.assertEquals;


public class GetInfoCommandTest extends CommandTest {
    private GetInfoCommand getInfoCommand;
    private final FtpClient ftpClient = Mockito.mock(FtpClient.class);

    @BeforeClass
    public void setUp() {
        getInfoCommand = new GetInfoCommand();
    }

    @Test(description = "check incorrect id", dataProvider = "default_param")
    public void checkIncorrectId(HashMap<Long, Student> students) throws IOException {
        simulateInputFromKeyBoard("10\n");
        assertEquals(getInfoCommand.execute(ftpClient, students), "Проверьте id, студент не найден");
    }

    @Test(description = "check incorrect format id", dataProvider = "default_param")
    public void checkIncorrectFormatId(HashMap<Long, Student> students) throws IOException {
        simulateInputFromKeyBoard("asdjhasjklf\n");
        assertEquals(getInfoCommand.execute(ftpClient, students), "Неверно введён id");
    }

    @Test(description = "check correct id", dataProvider = "default_param")
    public void checkCorrectId(HashMap<Long, Student> students) throws IOException {
        simulateInputFromKeyBoard("1\n");
        assertEquals(getInfoCommand.execute(ftpClient, students), "name: " + students.get(1L).getName());
    }
}
