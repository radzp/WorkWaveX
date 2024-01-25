document.addEventListener('DOMContentLoaded', function () {
        // Pobierz ID projektu, dla którego chcesz wyświetlić zadania
        const urlPath = window.location.pathname; // Zwróci "/api/v1/projects/1/details"
        const parts = urlPath.split('/'); // Podzieli ścieżkę na części: ["", "api", "v1", "projects", "1", "details"]
        const projectId = parts[parts.length - 2]; // Pobiera przedostatnią część, która jest ID projektu

// Wyślij żądanie do serwera, aby pobrać zadania dla danego projektu
        fetch(`/api/v1/tasks/project/${projectId}`)
            .then(response => response.json())
            .then(tasks => {
                // Teraz masz listę zadań dla danego projektu
                console.log(tasks);
                // Możesz je wyświetlić w dowolny sposób, na przykład dodając elementy HTML do strony
                tasks.forEach(task => {
                    // Tworzenie i dodawanie elementów HTML dla każdego zadania...
                    const taskContainer = document.querySelector(`#${mapTaskStatusToContainerId(task.taskStatus)}`); // Znajdź kontener dla zadań na podstawie statusu zadania
                    const taskContent = taskContainer.querySelector('.task-content'); // Znajdź kontener zadań wewnątrz kontenera
                    const taskBlock = document.createElement('div');
                    taskBlock.classList.add('task-block');
                    taskBlock.setAttribute('data-id', task.id);

                    let arrowDirection;
                    if (task.taskPriority === 'LOW') {
                        arrowDirection = 'arrow_downward';
                    } else if (task.taskPriority === 'MEDIUM') {
                        arrowDirection = 'arrow_forward';
                    } else {
                        arrowDirection = 'arrow_upward';
                    }

                    taskBlock.innerHTML = `
                <div class="task-row">
                    <div class="priority ${task.taskPriority.toLowerCase()}">
                        <div class="priority-icon">
                            <span class="material-icons-sharp">
                                ${arrowDirection}
                            </span>
                        </div>
                        <div class="priority-text">
                            <p>${task.taskPriority}</p>
                        </div>
                    </div>
                    <div class="task-options">
                        <span class="material-icons-sharp task-menu">
                            more_vert
                        </span>
                        <div class="context-menu">
                            <button class="delete-button">Delete</button>
                        </div>
                    </div>
                </div>
                <div class="task-row">
                    <div class="task-name">
                        <p>${task.taskName}</p>
                    </div>
                    <div class="task-description">
                        <p>${task.taskDescription}</p>
                        </div>
                </div>
                <div class="task-row">
                    <div class="project-date">
                        <div class="start-date">
                            <p>${task.startDate}</p>
                        </div>
                        <div class="end-date">
                            <p>${task.endDate}</p>
                        </div>
                    </div>
                </div>
                <div class="task-row">
                    <div class="assigned-to">
                        <div class="assigned-member">
                            <img src="../static/images/user_pink.png" th:src="@{/images/user_pink.png}">
                        </div>
                    </div>
                </div>
            
            `;
                    taskContent.appendChild(taskBlock); // Dodaj blok zadania do kontenera zadań, a nie do kontenera otaczającego
                });

                // Get all the tasks and the task containers
                const tasksItem = document.querySelectorAll('.task-block');
                const containers = document.querySelectorAll('.task-content');

                // Add drag events to tasks
                tasksItem.forEach(task => {
                    task.setAttribute('draggable', 'true');
                    task.addEventListener('dragstart', handleDragStart, false);
                    task.addEventListener('dragend', handleDragEnd, false);
                });

                // Add drag events to task containers
                containers.forEach(container => {
                    container.addEventListener('dragover', handleDragOver, false);
                    container.addEventListener('drop', handleDrop, false);
                });

                // Get all the more_vert icons and context menu links
                const moreVertIcons = document.querySelectorAll('.material-icons-sharp');
                const deleteButtons = document.querySelectorAll('.context-menu .delete-button');

                // Add click events to more_vert icons
                moreVertIcons.forEach(icon => {
                    icon.addEventListener('click', toggleMenu, false);
                });


                // Add click events to delete buttons
                deleteButtons.forEach(button => {
                    button.addEventListener('click', deleteTask, false);
                });
            })
            .catch(error => console.error('Error:', error));

        fetch(`/api/v1/projects/${projectId}`)
            .then(response => response.json())
            .then(project => {
                // Get the project's end date
                const projectEndDate = new Date(project.endDate);

                // Get the current date
                const currentDate = new Date();

                // Calculate the difference in milliseconds
                const differenceInMilliseconds = projectEndDate - currentDate;

                // Convert the difference from milliseconds to days
                const differenceInDays = Math.ceil(differenceInMilliseconds / (1000 * 60 * 60 * 24));

                // Display the number of days left for the project
                document.querySelector('.days-left .counting-number').textContent = differenceInDays;
            })
            .catch(error => console.error('Error:', error));


        let draggedTask = null;

        function handleDragStart(e) {
            // Store the task being dragged
            draggedTask = this;
            e.dataTransfer.effectAllowed = 'move';
        }

        function handleDragEnd(e) {
            // Clear the stored task when the dragging ends
            draggedTask = null;
        }

        function handleDragOver(e) {
            // Prevent default to allow drop
            e.preventDefault();
            e.dataTransfer.dropEffect = 'move';
        }

        function handleDrop(e) {
            // Prevent default action
            e.preventDefault();

            // Find the closest parent element with the class 'task-content'
            let dropTarget = e.target;
            while (!dropTarget.classList.contains('task-content')) {
                dropTarget = dropTarget.parentElement;
            }

            console.log(`Drop event triggered on element with id: ${dropTarget.id}`); // Debugging line

            // Check if the drop target is a valid task container
            if (dropTarget.classList.contains('task-content')) {
                console.log('Drop target is a valid task container'); // Debugging line

                // Move the dragged task to the dropped container
                if (draggedTask && dropTarget !== draggedTask) {
                    console.log('Moving the dragged task to the dropped container'); // Debugging line

                    dropTarget.appendChild(draggedTask);
                    updateTaskStatus(draggedTask, dropTarget.parentElement.id);
                    console.log("Wykonano updateTaskStatus")

                    // Debugging code
                    console.log(`Task ID: ${draggedTask.getAttribute('data-id')}`);
                    console.log(`New status: ${dropTarget.id}`);
                } else {
                    console.log('Dragged task is the same as the drop target'); // Debugging line
                }
            } else {
                console.log('Drop target is not a valid task container'); // Debugging line
            }
        }

        function updateTaskStatus(taskElement, newStatus) {
            const taskId = taskElement.getAttribute('data-id');
            const taskStatus = mapContainerIdToTaskStatus(newStatus);

            console.log(`Task ID 2: ${taskId}`);
            console.log(`New status 2: ${taskStatus}`);

            fetch(`/api/v1/tasks/${taskId}`, {
                method: 'PUT',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({
                    taskName: taskElement.querySelector('.task-name p').textContent,
                    taskDescription: taskElement.querySelector('.task-description p').textContent,
                    taskStatus: taskStatus,
                    taskPriority: taskElement.querySelector('.priority').classList[1].toUpperCase(),
                    startDate: taskElement.querySelector('.start-date p').textContent,
                    endDate: taskElement.querySelector('.end-date p').textContent,
                }),
            })
                .then(response => response.json())
                .then(data => {
                    console.log('Success:', data);
                })
                .catch((error) => {
                    console.error('Error:', error);
                });
        }

        function mapContainerIdToTaskStatus(containerId) {
            console.log(`Container ID: ${containerId}`); // Debugging line
            switch (containerId) {
                case 'to-do':
                    return 'TODO';
                case 'in-progress':
                    return 'IN_PROGRESS';
                case 'review':
                    return 'REVIEW';
                case 'done':
                    return 'DONE';
                default:
                    return null;
            }
        }

        function mapTaskStatusToContainerId(taskStatus) {
            switch (taskStatus) {
                case 'TODO':
                    return 'to-do';
                case 'IN_PROGRESS':
                    return 'in-progress';
                case 'REVIEW':
                    return 'review';
                case 'DONE':
                    return 'done';
                default:
                    return null;
            }
        }


        function toggleMenu(e) {
            // Check if the currentTarget is a more_vert icon
            if (!e.currentTarget.classList.contains('task-menu')) {
                return;
            }

            e.stopPropagation();
            const contextMenu = e.currentTarget.nextElementSibling;
            contextMenu.style.display = contextMenu.style.display === 'none' ? 'block' : 'none';
        }

        // Close the context menu when clicking outside of it
        window.onclick = function () {
            const contextMenus = document.querySelectorAll('.context-menu');
            contextMenus.forEach(contextMenu => {
                if (contextMenu.style.display === 'block') {
                    contextMenu.style.display = 'none';
                }
            });
        }


        function deleteTask(e) {
            e.stopPropagation();
            let taskElement = e.currentTarget.closest('.task-block');
            let taskId = taskElement.getAttribute('data-id');
            let taskName = taskElement.querySelector('.task-name p').textContent;

            let confirmDelete = confirm("Do you really want to delete this task?");
            if (confirmDelete) {
                fetch(`/api/v1/tasks/${taskId}`, {
                    method: 'DELETE',
                })
                    .then(response => {
                        if (response.ok) {
                            // Wyświetlenie alertu, że zadanie zostało pomyślnie usunięte
                            alert(`Task ${taskName} has been successfully deleted`);
                            // Usunięcie elementu zadania z DOM
                            taskElement.remove();
                        } else {
                            return response.json();
                        }
                    })
                    .then(data => {
                        if (data) {
                            console.log('Error:', data);
                        }
                    })
                    .catch((error) => {
                        console.error('Error:', error);
                    });
            }
        }
    }
);