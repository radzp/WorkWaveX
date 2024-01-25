package amw.workwavex.task;


import amw.workwavex.project.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;
    private final ProjectRepository projectRepository;

    public List<TaskDTO> getAllTasks() {
        return taskRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<TaskDTO> getTasksByProjectId(Integer projectId) {
        return taskRepository.findAllByProjectId(projectId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public TaskDTO createTask(Task newTask) {
        return convertToDTO(taskRepository.save(newTask));
    }

    public TaskDTO updateTask(Integer id, Task updatedTask) {
        return taskRepository.findById(id)
                .map(task -> {
                    task.setTaskName(updatedTask.getTaskName());
                    task.setTaskDescription(updatedTask.getTaskDescription());
                    task.setTaskStatus(updatedTask.getTaskStatus());
                    task.setTaskPriority(updatedTask.getTaskPriority());
                    task.setStartDate(updatedTask.getStartDate());
                    task.setEndDate(updatedTask.getEndDate());
                    return convertToDTO(taskRepository.save(task));
                })
                .orElse(null);
    }

    public void deleteTask(Integer id) {
        taskRepository.deleteById(id);
    }

    public TaskDTO convertToDTO(Task task) {
        TaskDTO dto = new TaskDTO();
        dto.setId(task.getId());
        dto.setTaskName(task.getTaskName());
        dto.setTaskDescription(task.getTaskDescription());
        dto.setTaskStatus(task.getTaskStatus());
        dto.setTaskPriority(task.getTaskPriority());
        dto.setStartDate(task.getStartDate());
        dto.setEndDate(task.getEndDate());
        return dto;
    }

    public Optional<Task> getTaskById(Integer integer) {
        return taskRepository.findById(integer);
    }

    public void saveTask(Task task) {
        taskRepository.save(task);
    }

    public List<TaskEvent> getAllEventTasks() {
        return getAllTasks().stream()
                .map(TaskEvent::new)
                .collect(Collectors.toList());
    }

    public TaskDTO addTaskToProject(Integer projectId, Task newTask) {
        return projectRepository.findById(projectId)
                .map(project -> {
                    newTask.setProject(project);
                    return convertToDTO(taskRepository.save(newTask));
                })
                .orElse(null);
    }

    public long getTotalTasksByProjectId(Integer id) {
        return taskRepository.findAllByProjectId(id).size();
    }

    public long getDoneTasksByProjectId(Integer id) {
        return taskRepository.findAllByProjectId(id).stream()
                .filter(task -> task.getTaskStatus().equals(TaskStatus.DONE))
                .count();
    }
}