package amw.workwavex.project;

import amw.workwavex.task.Task;
import amw.workwavex.user.User;
import lombok.Data;

import java.time.LocalDate;
import java.util.Set;

@Data
public class ProjectDTO {
    private Integer id;
    private String projectName;
    private String projectDescription;
    private Set<User> projectMembers;
    private Set<Task> projectTasks;
    private ProjectStatus projectStatus;
    private LocalDate startDate;
    private LocalDate endDate;

}