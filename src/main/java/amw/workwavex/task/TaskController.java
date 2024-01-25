package amw.workwavex.task;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @GetMapping("/all")
    public List<TaskDTO> getAllTasks() {
        return taskService.getAllTasks();
    }
    @GetMapping("/allEvents")
    public List<TaskEvent> getAllEventTasks() {
        return taskService.getAllEventTasks();
    }

    @PostMapping("/create")
    public TaskDTO createTask(@RequestBody Task newTask) {
        return taskService.createTask(newTask);
    }

    @PutMapping("/{id}")
    public TaskDTO updateTask(@PathVariable Integer id, @RequestBody Task updatedTask) {
        return taskService.updateTask(id, updatedTask);
    }

    @GetMapping("/project/{projectId}")
    public List<TaskDTO> getTasksByProjectId(@PathVariable Integer projectId) {
        return taskService.getTasksByProjectId(projectId);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Integer id) {
        taskService.deleteTask(id);
        return ResponseEntity.ok().build();
    }
    @PostMapping("addTask/project/{projectId}")
    public TaskDTO addTaskToProject(@PathVariable Integer projectId, @RequestBody Task newTask) {
        return taskService.addTaskToProject(projectId, newTask);
    }
}