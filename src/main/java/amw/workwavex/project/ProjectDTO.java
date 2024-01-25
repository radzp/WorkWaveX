package amw.workwavex.project;

import amw.workwavex.task.TaskDTO;
import amw.workwavex.user.UserDTO;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
public class ProjectDTO {
    private Integer id;

    @NotBlank(message = "Project name is mandatory")
    private String projectName;

    @NotBlank(message = "Project description is mandatory")
    private String projectDescription;

    private Set<UserDTO> projectMembers = new HashSet<>();

    private Set<TaskDTO> projectTasks = new HashSet<>();

    @NotNull(message = "Project status is mandatory")
    private ProjectStatus projectStatus;

    @FutureOrPresent(message = "Start date should be in the present or future")
    private LocalDate startDate;

    @FutureOrPresent(message = "End date should be in the present or future")
    private LocalDate endDate;
}