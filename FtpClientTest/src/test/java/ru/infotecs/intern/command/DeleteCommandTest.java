package ru.infotecs.intern.command;

import org.mockito.Mock;
import org.mockito.Mockito;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import ru.infotecs.intern.client.FtpClient;
import ru.infotecs.intern.gui.FtpGUI;
import ru.infotecs.intern.model.Student;

import java.io.IOException;
import java.util.HashMap;

import static org.testng.Assert.assertEquals;

public class DeleteCommandTest extends CommandTest {
    private DeleteCommand deleteCommand;
    private FtpClient ftpClient;

    @BeforeMethod
    public void createMock() {
        ftpClient = Mockito.mock(FtpClient.class);
    }

    @BeforeClass
    public void setUp() {
        deleteCommand = new DeleteCommand();
    }

    @Test(description = "check incorrect id", dataProvider = "default_param")
    public void checkIncorrectId(HashMap<Long, Student> students) throws IOException {
        int prevSize = students.size();
        simulateInputFromKeyBoard("10\n");
        assertEquals(deleteCommand.execute(ftpClient, students), "Проверьте id, студент не найден");
        assertEquals(prevSize, students.size());
        Mockito.verify(ftpClient, Mockito.never()).sendFile(FtpGUI.fileName);
    }

    @Test(description = "check correct id", dataProvider = "default_param")
    public void checkCorrectId(HashMap<Long, Student> students) throws IOException {
        int prevSize = students.size();
        simulateInputFromKeyBoard("1\n");
        assertEquals(deleteCommand.execute(ftpClient, students), "Успешно удалён");
        assertEquals(prevSize - 1, students.size());
        Mockito.verify(ftpClient).sendFile(FtpGUI.fileName);
    }

    @Test(description = "check incorrect format id", dataProvider = "default_param")
    public void checkIncorrectFormatId(HashMap<Long, Student> students) throws IOException {
        simulateInputFromKeyBoard("asdjhasjklf\n");
        assertEquals(deleteCommand.execute(ftpClient, students), "Неверно введён id");
    }


}
