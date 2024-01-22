package amw.workwavex.task;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;

    public List<TaskDTO> getAllTasks() {
        return taskRepository.findAll().stream()
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
        dto.setStartDate(task.getStartDate());
        dto.setEndDate(task.getEndDate());
        return dto;
    }
}