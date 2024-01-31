package amw.workwavex.task;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class TaskServiceTest {

    @InjectMocks
    private TaskService taskService;

    @Mock
    private TaskRepository taskRepository;

    @Test
    void getAllTasks() {
        //given
        Task task1 = new Task();
        task1.setId(1);
        task1.setTaskName("Task 1");
        task1.setTaskDescription("Description 1");
        task1.setTaskStatus(TaskStatus.TODO);
        task1.setTaskPriority(TaskPriority.HIGH);
        task1.setStartDate(LocalDate.of(2024, 1, 1));
        task1.setEndDate(LocalDate.of(2024, 1, 2));


        Task task2 = new Task();
        task2.setId(2);
        task2.setTaskName("Task 2");
        task2.setTaskDescription("Description 2");
        task2.setTaskStatus(TaskStatus.IN_PROGRESS);
        task2.setTaskPriority(TaskPriority.MEDIUM);
        task2.setStartDate(LocalDate.of(2024, 1, 3));
        task2.setEndDate(LocalDate.of(2024, 1, 4));

        List<Task> tasks = List.of(task1, task2);
        when(taskRepository.findAll()).thenReturn(tasks);

        //when
        List<TaskDTO> result = taskService.getAllTasks();

        //then
        assertEquals(2, result.size());
        assertEquals("Task 1", result.get(0).getTaskName());
        assertEquals("Task 2", result.get(1).getTaskName());
    }

    @Test
    void getTasksByProjectId() {
        //given
        Task task1 = new Task();
        task1.setId(1);
        task1.setTaskName("Task 1");
        task1.setTaskDescription("Description 1");
        task1.setTaskStatus(TaskStatus.TODO);
        task1.setTaskPriority(TaskPriority.HIGH);
        task1.setStartDate(LocalDate.of(2024, 1, 1));
        task1.setEndDate(LocalDate.of(2024, 1, 2));

        when(taskRepository.findAllByProjectId(1)).thenReturn(List.of(task1));

        //when
        List<TaskDTO> result = taskService.getTasksByProjectId(1);

        //then
        assertEquals(1, result.size());
        assertEquals("Task 1", result.get(0).getTaskName());
    }

    @Test
    void createTask() {
        //given
        Task task1 = new Task();
        task1.setId(1);
        task1.setTaskName("Task 1");
        task1.setTaskDescription("Description 1");
        task1.setTaskStatus(TaskStatus.TODO);
        task1.setTaskPriority(TaskPriority.HIGH);
        task1.setStartDate(LocalDate.of(2024, 1, 1));
        task1.setEndDate(LocalDate.of(2024, 1, 2));

        when(taskRepository.save(task1)).thenReturn(task1);

        //when
        TaskDTO result = taskService.createTask(task1);

        //then
        assertEquals("Task 1", result.getTaskName());
    }
}