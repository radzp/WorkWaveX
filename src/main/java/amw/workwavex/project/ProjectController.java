package amw.workwavex.project;

import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

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

    @GetMapping("/{id}")
    public ProjectDTO getProjectById(@PathVariable Integer id) {
        return projectService.getProjectById(id);
    }


    @GetMapping("/user/{userId}")
    public List<ProjectDTO> getProjectsByUserId(@PathVariable Integer userId) {
        return projectService.getProjectsByUserId(userId);
    }

    @PostMapping("/create")
    public ProjectDTO createProject(@RequestBody ProjectDTO newProjectDTO) {
        return projectService.createProject(newProjectDTO);
    }

    @PutMapping("/{id}")
    public ProjectDTO updateProject(@PathVariable Integer id, @RequestBody Project updatedProject) {
        return projectService.updateProject(id, updatedProject);
    }

    @DeleteMapping("/{id}")
    public void deleteProject(@PathVariable Integer id) {
        projectService.deleteProject(id);
    }

    @GetMapping("/{id}/details")
    public ModelAndView getProjectDetails(@PathVariable Integer id) {
        ProjectDTO project = projectService.getProjectById(id);
        ModelAndView modelAndView = new ModelAndView();
        if (project != null) {
            modelAndView.addObject("project", project);
            modelAndView.setViewName("example_project");
        } else {
            // handle the case when the project is null
            // for example, redirect to an error page or a list of projects
        }
        return modelAndView;
    }
}