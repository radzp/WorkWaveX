package amw.workwavex.project;

import amw.workwavex.task.Task;
import amw.workwavex.task.TaskDTO;
import amw.workwavex.user.User;
import amw.workwavex.user.UserDTO;
import lombok.Data;

import java.time.LocalDate;
import java.util.Set;

@Data
public class ProjectDTO {
    private Integer id;
    private String projectName;
    private String projectDescription;
    private Set<UserDTO> projectMembers;
    private Set<TaskDTO> projectTasks;
    private ProjectStatus projectStatus;
    private LocalDate startDate;
    private LocalDate endDate;

}