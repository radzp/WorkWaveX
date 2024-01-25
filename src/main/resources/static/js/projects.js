document.addEventListener('DOMContentLoaded', function () {
    const projectsContainer = document.querySelector('.wrapper');

    fetch('/api/v1/projects/all')
        .then(response => response.json())
        .then(projects => {
            projects.forEach(project => {
                const projectElement = document.createElement('div');
                projectElement.classList.add('block-wrapper');
                projectElement.innerHTML = `
        <div class="block-content">
            <div class="project-header">
                <h2>${project.projectName}</h2>
            </div>
            <div class="project-body">
                <div class="project-info">
                    <div class="project-info-item">
                        <div class="status-date">
                            <h3 class="project-status ${project.projectStatus.toLowerCase()}">${project.projectStatus}</h3>
                            <div class="project-due-date">
                                <h3>Due date</h3>
                                <p>${project.endDate}</p>
                            </div>
                        </div>
                    </div>
                    <div class="project-info-item">
                        <p class="project-description">${project.projectDescription}</p>
                    </div>
                    <div class="project-info-item">
                        <h3>MEMBERS</h3>
                        <div class="members-images">
                            ` + project.projectMembers.map(member => `<img src="images/user_aqua.png" alt="${member.firstName} ${member.lastName}">`).join('') + `
                        </div>
                    </div>
                    <div class="project-info-item">
                        <div class="project-info-item-footer">
                            <div class="tasks-counter">
                                <p>${project.projectTasks.length}</p>
                                <h3>Tasks</h3>
                            </div>
                            <h3>
                                <a href="/api/v1/projects/${project.id}/details">View Details</a>
                            </h3>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    `;
                projectsContainer.appendChild(projectElement);
            });

            const membersImagesDivs = document.querySelectorAll('.members-images');

            membersImagesDivs.forEach(div => {
                const images = div.querySelectorAll('img');

                if (images.length > number) {
                    for (let i = number; i < images.length; i++) {
                        images[i].style.display = 'none';
                    }

                    const extraCount = images.length - number;
                    const extraElement = document.createElement('span');
                    extraElement.classList.add('extra');
                    extraElement.textContent = '+' + extraCount;
                    div.appendChild(extraElement);
                }
            });

            truncateDescription();
        })
        .catch(error => console.error('Error:', error));


    const openModal = document.querySelector('#addProjectButton');
    const closeModal = document.querySelector('#closeModal');
    const modal = document.querySelector('.modal');
    const number = 5;


    const startDateInput = document.querySelector('#projectStartDate');
    const endDateInput = document.querySelector('#projectEndDate');

    startDateInput.addEventListener('change', function () {
        endDateInput.min = startDateInput.value;
    });
    endDateInput.addEventListener('change', function () {
        startDateInput.max = endDateInput.value;
    });

    openModal.addEventListener('click', function () {
        modal.showModal();
    });

    closeModal.addEventListener('click', function () {
        modal.close();
    });

    function truncateDescription() {
        const descriptions = document.querySelectorAll('.project-description');

        descriptions.forEach(description => {
            if (description.textContent.length > 97) {
                description.textContent = description.textContent.slice(0, 97) + '...';
            }
        });
    }


    const form = document.querySelector('.form');

    form.addEventListener('submit', function (event) {
        event.preventDefault();


        const projectName = document.querySelector('#projectName').value;
        const projectDescription = document.querySelector('#projectDescription').value;
        const projectMembers = $('#projectMembers').val().map(id => ({ id: Number(id) }));
        const projectStatus = document.querySelector('#projectStatus').value;
        const projectStartDate = document.querySelector('#projectStartDate').value;
        const projectEndDate = document.querySelector('#projectEndDate').value;

        const newProject = {
            projectName: projectName,
            projectDescription: projectDescription,
            projectMembers: projectMembers,
            projectStatus: projectStatus,
            startDate: projectStartDate,
            endDate: projectEndDate
        };

        if (!newProject.projectName.trim()) {
            alert('Project name is required');
            return;
        }

        if (!newProject.projectDescription.trim()) {
            alert('Project description is required');
            return;
        }

        if (!newProject.projectMembers.length) {
            alert('At least one project member is required');
            return;
        }

        if (!newProject.projectStatus.trim()) {
            alert('Project status is required');
            return;
        }

        if (!newProject.startDate.trim()) {
            alert('Start date is required');
            return;
        }

        if (!newProject.endDate.trim()) {
            alert('End date is required');
            return;
        }

        console.log(newProject);


        fetch('/api/v1/projects/create', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(newProject)
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
    });

    truncateDescription();

})
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
document.querySelectorAll('.members-images img').forEach(img => {
    img.title = img.alt;
});