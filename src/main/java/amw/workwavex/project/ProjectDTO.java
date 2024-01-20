package amw.workwavex.project;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ProjectDTO {
    private Integer id;
    private String projectName;
    private String projectDescription;
    private ProjectStatus projectStatus;
    private LocalDate startDate;
    private LocalDate endDate;
}