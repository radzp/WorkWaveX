package amw.workwavex.task;


import lombok.Data;

import java.time.LocalDate;

@Data
public class TaskDTO {
    private Integer id;
    private String taskName;
    private String taskDescription;
    private LocalDate startDate;
    private LocalDate endDate;
}