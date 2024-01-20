package amw.workwavex.project;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProjectService {

    private final ProjectRepository projectRepository;

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

    public ProjectDTO createProject(Project newProject) {
        return convertToDTO(projectRepository.save(newProject));
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
        return dto;
    }
}