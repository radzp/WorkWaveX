package amw.workwavex.task;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class TaskDTO {
    private Integer id;

    @NotBlank(message = "Task name is mandatory")
    private String taskName;

    @NotBlank(message = "Task description is mandatory")
    private String taskDescription;

    @NotNull(message = "Task status is mandatory")
    private TaskStatus taskStatus;

    @NotNull(message = "Task priority is mandatory")
    private TaskPriority taskPriority;

    @FutureOrPresent(message = "Start date should be in the present or future")
    private LocalDate startDate;

    @FutureOrPresent(message = "End date should be in the present or future")
    private LocalDate endDate;
}