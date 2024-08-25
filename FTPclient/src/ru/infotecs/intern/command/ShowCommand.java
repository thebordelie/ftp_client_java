package ru.infotecs.intern.command;

import com.sun.istack.internal.NotNull;
import ru.infotecs.intern.client.FtpClient;
import ru.infotecs.intern.model.Student;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

public class ShowCommand extends FtpCommand {
    public ShowCommand() {
        super("Получение списка студентов");
    }

    @Override
    public String execute(FtpClient client, @NotNull HashMap<Long, Student> students) throws IOException {
        if (students.isEmpty()) return "Студенты отсутствуют";
        StringBuilder builder = new StringBuilder("Список студентов: \n");
        List<Student> studentList = new ArrayList<>(students.values());
        studentList.sort(Comparator.comparing(Student::getName));
        for (Student student: studentList)  {
            builder.append(String.format("\nid: %d\nname: %s\n", student.getId(), student.getName()));
        }
        return builder.toString();
    }
}
