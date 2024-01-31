package amw.workwavex.project;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
public class ProjectServiceTest {

    @InjectMocks
    private ProjectService projectService;

    @Mock
    private ProjectRepository projectRepository;

    @Test
    public void testGetAllProjects() {
        // Arrange
        Project project1 = new Project();
        project1.setId(1);
        project1.setProjectName("Project 1");

        Project project2 = new Project();
        project2.setId(2);
        project2.setProjectName("Project 2");

        List<Project> projects = Arrays.asList(project1, project2);

        when(projectRepository.findAll()).thenReturn(projects);

        // Act
        List<ProjectDTO> result = projectService.getAllProjects();

        // Assert
        assertEquals(2, result.size());
        assertEquals("Project 1", result.get(0).getProjectName());
        assertEquals("Project 2", result.get(1).getProjectName());
    }
}