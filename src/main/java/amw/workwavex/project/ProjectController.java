package amw.workwavex.project;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/projects")
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;

    @GetMapping("/all")
    public List<ProjectDTO> getAllProjects() {
        return projectService.getAllProjects();
    }

    @GetMapping("/name/{name}")
    public List<ProjectDTO> getProjectsByName(@PathVariable String name) {
        return projectService.getProjectsByName(name);
    }

    @GetMapping("/user/{userId}")
    public List<ProjectDTO> getProjectsByUserId(@PathVariable Integer userId) {
        return projectService.getProjectsByUserId(userId);
    }

    @PostMapping("/create")
    public ProjectDTO createProject(@RequestBody Project newProject) {
        return projectService.createProject(newProject);
    }

    @PutMapping("/{id}")
    public ProjectDTO updateProject(@PathVariable Integer id, @RequestBody Project updatedProject) {
        return projectService.updateProject(id, updatedProject);
    }

    @DeleteMapping("/{id}")
    public void deleteProject(@PathVariable Integer id) {
        projectService.deleteProject(id);
    }
}