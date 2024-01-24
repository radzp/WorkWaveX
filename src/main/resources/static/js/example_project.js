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
            const taskContainer = document.querySelector('.task-content'); // Znajdź kontener dla zadań
            const taskBlock = document.createElement('div');
            taskBlock.classList.add('task-block');
            taskBlock.innerHTML = `
                <div class="task-row">
                    <div class="priority ${task.taskPriority.toLowerCase()}">
                        <div class="priority-icon">
                            <span class="material-icons-sharp">
                                arrow_upward
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
                            <button class="edit-button">Edit</button>
                            <button class="delete-button">Delete</button>
                        </div>
                    </div>
                </div>
                <div class="task-row">
                    <div class="task-name">
                        <p>${task.taskName}</p>
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
            taskContainer.appendChild(taskBlock);
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

            // Check if the drop target is a valid task container
            if (this.classList.contains('task-content')) {
                // Move the dragged task to the dropped container
                if (draggedTask && this !== draggedTask) {
                    this.appendChild(draggedTask);
                }
            }
        }

        // Get all the more_vert icons and context menu links
        const moreVertIcons = document.querySelectorAll('.material-icons-sharp');
        const editButtons = document.querySelectorAll('.context-menu .edit-button');
        const deleteButtons = document.querySelectorAll('.context-menu .delete-button');

        // Add click events to more_vert icons
        moreVertIcons.forEach(icon => {
            icon.addEventListener('click', toggleMenu, false);
        });

        // Add click events to edit buttons
        editButtons.forEach(button => {
            button.addEventListener('click', editTask, false);
        });

        // Add click events to delete buttons
        deleteButtons.forEach(button => {
            button.addEventListener('click', deleteTask, false);
        });

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

        function editTask(e) {
            e.stopPropagation();
            // Here you can add the code to edit the task
            console.log('Edit task');
        }

        function deleteTask(e) {
            e.stopPropagation();
            // Here you can add the code to delete the task
            console.log('Delete task');
        }
    }
);