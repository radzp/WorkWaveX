document.addEventListener('DOMContentLoaded', function () {

    const urlPath = window.location.pathname;
    const parts = urlPath.split('/');
    const projectId = parts[parts.length - 2];

    fetch(`/api/v1/tasks/project/${projectId}`)
        .then(response => response.json())
        .then(tasks => {
            console.log(tasks);
            tasks.forEach(task => {
                const taskContainer = document.querySelector(`#${mapTaskStatusToContainerId(task.taskStatus)}`);
                const taskContent = taskContainer.querySelector('.task-content');
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
                taskContent.appendChild(taskBlock);
            });

            const tasksItem = document.querySelectorAll('.task-block');
            const containers = document.querySelectorAll('.task-content');

            tasksItem.forEach(task => {
                task.setAttribute('draggable', 'true');
                task.addEventListener('dragstart', handleDragStart, false);
                task.addEventListener('dragend', handleDragEnd, false);
            });

            containers.forEach(container => {
                container.addEventListener('dragover', handleDragOver, false);
                container.addEventListener('drop', handleDrop, false);
            });

            const moreVertIcons = document.querySelectorAll('.material-icons-sharp');
            const deleteButtons = document.querySelectorAll('.context-menu .delete-button');

            moreVertIcons.forEach(icon => {
                icon.addEventListener('click', toggleMenu, false);
            });


            deleteButtons.forEach(button => {
                button.addEventListener('click', deleteTask, false);
            });
        })
        .catch(error => console.error('Error:', error));

    fetch(`/api/v1/projects/${projectId}`)
        .then(response => response.json())
        .then(project => {

            const projectEndDate = new Date(project.endDate);
            const currentDate = new Date();
            const differenceInMilliseconds = projectEndDate - currentDate;
            const differenceInDays = Math.ceil(differenceInMilliseconds / (1000 * 60 * 60 * 24));

            document.querySelector('.days-left .counting-number').textContent = differenceInDays;
        })
        .catch(error => console.error('Error:', error));


    let draggedTask = null;

    function handleDragStart(e) {
        draggedTask = this;
        e.dataTransfer.effectAllowed = 'move';
    }

    function handleDragEnd(e) {
        draggedTask = null;
    }

    function handleDragOver(e) {
        e.preventDefault();
        e.dataTransfer.dropEffect = 'move';
    }

    function handleDrop(e) {
        e.preventDefault();

        let dropTarget = e.target;
        while (!dropTarget.classList.contains('task-content')) {
            dropTarget = dropTarget.parentElement;
        }

        console.log(`Drop event triggered on element with id: ${dropTarget.id}`);


        if (dropTarget.classList.contains('task-content')) {
            console.log('Drop target is a valid task container');

            if (draggedTask && dropTarget !== draggedTask) {
                console.log('Moving the dragged task to the dropped container');

                dropTarget.appendChild(draggedTask);
                updateTaskStatus(draggedTask, dropTarget.parentElement.id);
                console.log("Made updateTaskStatus")

                console.log(`Task ID: ${draggedTask.getAttribute('data-id')}`);
                console.log(`New status: ${dropTarget.id}`);
            } else {
                console.log('Dragged task is the same as the drop target');
            }
        } else {
            console.log('Drop target is not a valid task container');
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
        console.log(`Container ID: ${containerId}`);
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
        if (!e.currentTarget.classList.contains('task-menu')) {
            return;
        }

        e.stopPropagation();
        const contextMenu = e.currentTarget.nextElementSibling;
        contextMenu.style.display = contextMenu.style.display === 'none' ? 'block' : 'none';
    }

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
                        alert(`Task ${taskName} has been successfully deleted`);
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

    const addTaskButton = document.getElementById('addTask');
    const modal = document.getElementById('modal');
    const closeButton = document.getElementById('closeModal');


    addTaskButton.addEventListener('click', () => {
        modal.showModal();
    });
    closeButton.addEventListener('click', () => {
        modal.close();
    });

    const startDateInput = document.querySelector('#startDate');
    const endDateInput = document.querySelector('#endDate');

    startDateInput.addEventListener('change', function () {
        endDateInput.min = startDateInput.value;
    });

    endDateInput.addEventListener('change', function () {
        startDateInput.max = endDateInput.value;
    });

    const form = document.querySelector('.form');

    form.addEventListener('submit', function (event) {
            event.preventDefault();


            const taskName = document.getElementById('taskName')
            const taskDescription = document.getElementById('taskDescription')
            const taskStatus = document.getElementById('taskStatus')

            const taskPriority = document.getElementById('taskPriority')
            const startDate = document.getElementById('startDate')
            const endDate = document.getElementById('endDate')

            const newTask = {
                taskName: taskName.value,
                taskDescription: taskDescription.value,
                taskStatus: taskStatus.value,
                taskPriority: taskPriority.value,
                startDate: startDate.value,
                endDate: endDate.value
            };

            if (!newTask.taskName || !newTask.taskDescription || !newTask.taskStatus || !newTask.taskPriority || !newTask.startDate || !newTask.endDate) {
                alert('All correct data should be provided');
                return;
            }

            fetch(`/api/v1/tasks/addTask/project/${projectId}`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(newTask)
            })
                .then(response => response.json())
                .then(data => {
                    console.log('Success:', data);
                    location.reload()
                })
                .catch((error) => {
                    console.error('Error:', error);
                });
            modal.close();

        }
    );
});

$(document).ready(function () {

    function matchCustom(params, data) {
        if ($.trim(params.term) === '') {
            return data;
        }

        if (typeof data.text === 'undefined') {
            return null;
        }

        var searchTermLowerCase = params.term.toLowerCase();
        var dataTextLowerCase = data.text.toLowerCase();

        if (dataTextLowerCase.indexOf(searchTermLowerCase) > -1) {
            var modifiedData = $.extend({}, data, true);
            modifiedData.text += ' (matched)';

            return modifiedData;
        }

        return null;
    }

    $('#projectMembers').select2({
        dropdownParent: $('.form'),
        matcher: matchCustom,
        minimumInputLength: 1,
        placeholder: 'Select members',
        allowClear: true,
        ajax: {
            url: '/api/v1/user-controller/all',
            dataType: 'json',
            processResults: function (data) {
                console.log(data)
                return {
                    results: data.map(user => {
                        return {
                            id: user.id,
                            text: user.firstName + ' ' + user.lastName
                        }
                    })
                };
            }
        }
    });
});