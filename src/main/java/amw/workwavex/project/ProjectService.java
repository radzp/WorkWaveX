package amw.workwavex.project;

import amw.workwavex.task.Task;
import amw.workwavex.task.TaskService;
import amw.workwavex.task.TaskDTO;
import amw.workwavex.user.User;
import amw.workwavex.user.UserDTO;
import amw.workwavex.user.UserRepository;
import amw.workwavex.user.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;
    private final UserService userService;
    private final TaskService taskService;

    public List<ProjectDTO> getAllProjects() {
        return projectRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<ProjectDTO> getProjectsByName(String name) {
        return projectRepository.findByProjectName(name).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<ProjectDTO> getProjectsByUserId(Integer userId) {
        return projectRepository.findByMemberId(userId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public ProjectDTO createProject(ProjectDTO newProjectDTO) {
        Project newProject = new Project();
        newProject.setProjectName(newProjectDTO.getProjectName());
        newProject.setProjectDescription(newProjectDTO.getProjectDescription());
        newProject.setProjectStatus(newProjectDTO.getProjectStatus());
        newProject.setStartDate(newProjectDTO.getStartDate());
        newProject.setEndDate(newProjectDTO.getEndDate());

        Set<User> members = newProjectDTO.getProjectMembers().stream()
                .map(UserDTO::getId)
                .map(userRepository::findById)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toSet());
        newProject.setProjectMembers(members);

        // Przypisz zadania do projektu
        Set<Task> tasks = newProjectDTO.getProjectTasks().stream()
                .map(TaskDTO::getId)
                .map(taskService::getTaskById)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toSet());
        newProject.setProjectTasks(tasks);

        Project savedProject = projectRepository.save(newProject);

        // Save the relationship in the users as well
        members.forEach(member -> {
            member.getProjects().add(savedProject);
            userRepository.save(member);
        });

        // Save the relationship in the tasks as well
        tasks.forEach(task -> {
            task.setProject(savedProject);
            taskService.saveTask(task);
        });

        return convertToDTO(savedProject);
    }

    public ProjectDTO updateProject(Integer id, Project updatedProject) {
        return projectRepository.findById(id)
                .map(project -> {
                    project.setProjectName(updatedProject.getProjectName());
                    project.setProjectDescription(updatedProject.getProjectDescription());
                    project.setProjectStatus(updatedProject.getProjectStatus());
                    project.setStartDate(updatedProject.getStartDate());
                    project.setEndDate(updatedProject.getEndDate());
                    return convertToDTO(projectRepository.save(project));
                })
                .orElse(null);
    }

    public void deleteProject(Integer id) {
        projectRepository.deleteById(id);
    }

    private ProjectDTO convertToDTO(Project project) {
        ProjectDTO dto = new ProjectDTO();
        dto.setId(project.getId());
        dto.setProjectName(project.getProjectName());
        dto.setProjectDescription(project.getProjectDescription());
        dto.setProjectStatus(project.getProjectStatus());
        dto.setStartDate(project.getStartDate());
        dto.setEndDate(project.getEndDate());
        dto.setProjectMembers(
                project.getProjectMembers().stream()
                        .map(userService::convertToDTO) // użyj metody convertToDTO z UserService
                        .collect(Collectors.toSet())
        );
        dto.setProjectTasks(
                project.getProjectTasks().stream()
                        .map(taskService::convertToDTO) // użyj metody convertToDTO z TaskService
                        .collect(Collectors.toSet()));
        return dto;
    }

    public ProjectDTO getProjectById(Integer id) {
        return projectRepository.findById(id)
                .map(this::convertToDTO)
                .orElse(null);
    }
}