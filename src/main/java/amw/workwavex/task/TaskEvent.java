package amw.workwavex.task;

import lombok.Data;

import java.time.format.DateTimeFormatter;

@Data
// Nowa klasa TaskEvent
public class TaskEvent {
    private String title;
    private String start;
    private String end;
    private String description;


    public TaskEvent(TaskDTO task) {
        this.title = task.getTaskName();
        this.start = task.getStartDate().atStartOfDay().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        this.end = task.getEndDate().atStartOfDay().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        this.description = task.getTaskDescription();
    }
}
