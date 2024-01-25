package amw.workwavex.project;

import amw.workwavex.task.Task;
import amw.workwavex.user.User;
import amw.workwavex.user.UserDTO;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name= "_project")
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
public class Project {
    @Id
    @GeneratedValue
    private Integer id;

    @NotBlank(message = "Project name is mandatory")
    private String projectName;

    @NotBlank(message = "Project description is mandatory")
    private String projectDescription;

    @NotNull(message = "Project status is mandatory")
    @Enumerated(EnumType.STRING)
    private ProjectStatus projectStatus;

    @FutureOrPresent(message = "Start date should be in the present or future")
    private LocalDate startDate;

    @FutureOrPresent(message = "End date should be in the present or future")
    private LocalDate endDate;

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Task> projectTasks = new HashSet<>();

    @EqualsAndHashCode.Exclude // wykluczenie z metody equals() i hashCode() pola projectMembers
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
            name = "_project_user",
            joinColumns = @JoinColumn(name = "project_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private Set<User> projectMembers = new HashSet<>();
}