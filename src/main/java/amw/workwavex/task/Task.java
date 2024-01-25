package amw.workwavex.task;

import amw.workwavex.project.Project;
import amw.workwavex.user.User;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name= "_task")
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
public class Task {
    @Id
    @GeneratedValue
    private Integer id;

    @NotBlank(message = "Task name is mandatory")
    private String taskName;

    @NotBlank(message = "Task description is mandatory")
    private String taskDescription;

    @NotNull(message = "Task status is mandatory")
    @Enumerated(EnumType.STRING)
    private TaskStatus taskStatus;

    @NotNull(message = "Task priority is mandatory")
    @Enumerated(EnumType.STRING)
    private TaskPriority taskPriority;

    @FutureOrPresent(message = "Start date should be in the present or future")
    private LocalDate startDate;

    @FutureOrPresent(message = "End date should be in the present or future")
    private LocalDate endDate;

    @EqualsAndHashCode.Exclude // Exclude from hashCode() method
    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}