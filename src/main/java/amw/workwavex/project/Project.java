package amw.workwavex.project;

import amw.workwavex.task.Task;
import amw.workwavex.user.User;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Data   // Lombok annotation to create all the getters, setters, equals, hash, and toString methods, based on the fields
@Builder // Lombok annotation to create builder API for the class
@NoArgsConstructor // Lombok annotation to create constructor without parameters
@AllArgsConstructor // Lombok annotation to create constructor with all of the parameters
@Entity // JPA annotation to make this object ready for storage in a JPA-based data store
@Table(name= "_project") // JPA annotation to specify the table name (if not specified, the class name will be used as the table name
public class Project {
    @Id
    @GeneratedValue
    private Integer id;
    private String projectName;
    private String projectDescription;
    @Enumerated(EnumType.STRING)
    private ProjectStatus projectStatus;
    private LocalDate startDate;
    private LocalDate endDate;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(
            name = "_project_user",
            joinColumns = @JoinColumn(name = "project_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    @JsonManagedReference
    private Set<User> projectMembers;
    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private Set<Task> projectTasks;
}
