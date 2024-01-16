package amw.workwavex.task;

import amw.workwavex.project.Project;
import amw.workwavex.user.User;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Set;

@Data   // Lombok annotation to create all the getters, setters, equals, hash, and toString methods, based on the fields
@Builder // Lombok annotation to create builder API for the class
@NoArgsConstructor // Lombok annotation to create constructor without parameters
@AllArgsConstructor // Lombok annotation to create constructor with all of the parameters
@Entity // JPA annotation to make this object ready for storage in a JPA-based data store
@Table(name= "_task") // JPA annotation to specify the table name (if not specified, the class name will be used as the table name
public class Task {
    @Id
    @GeneratedValue
    private Integer id;
    private String taskName;
    private String taskDescription;
    @Enumerated(EnumType.STRING)
    private TaskStatus taskStatus;
    private LocalDate startDate;
    private LocalDate endDate;
    @ManyToOne
    @JoinColumn(name = "project_id")
    @JsonBackReference
    private Project project;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private User user;

}
