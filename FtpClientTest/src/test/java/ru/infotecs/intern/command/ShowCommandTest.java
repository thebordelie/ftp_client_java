package ru.infotecs.intern.command;

import org.testng.AssertJUnit;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ru.infotecs.intern.model.Student;

import java.io.IOException;
import java.util.HashMap;

import static org.testng.Assert.assertEquals;

public class ShowCommandTest extends CommandTest {
    private ShowCommand showCommand;

    @BeforeClass
    public void setUp() {
        showCommand = new ShowCommand();
    }

    @Test(description = "checking the result of the command execution", dataProvider = "default_param")
    public void checkOfCommandExecution(HashMap<Long, Student> students) throws IOException {
        assertEquals(
                showCommand.execute(null, students).trim().replaceAll("\\s+", " "),
                "Список студентов: id: 2 name: Andrey id: 3 name: Katya id: 1 name: student1");
    }

    @Test(description = "checking empty file")
    public void checkOfCommandExecutionOnEmptyFile() throws IOException {
        assertEquals(showCommand.execute(null, new HashMap<>()), "Студенты отсутствуют");
    }
}
