document.addEventListener('DOMContentLoaded', function () {

    // Pobierz kontener, do którego będą dodawane projekty
    const projectsContainer = document.querySelector('.wrapper');

    fetch('fetch/projects.json')
        .then(response => response.json())
        .then(projects => {
            // Dla każdego projektu
            projects.forEach(project => {
                // Stwórz element HTML dla projektu
                const projectElement = document.createElement('div');
                projectElement.classList.add('block-wrapper');
                projectElement.innerHTML = `
        <div class="block-content">
            <div class="project-header">
                <h2>${project.name}</h2>
            </div>
            <div class="project-body">
                <div class="project-info">
                    <div class="project-info-item">
                        <div class="status-date">
                            <h3 class="project-status ${project.status.toLowerCase()}">${project.status}</h3>
                            <div class="project-due-date">
                                <h3>Due date</h3>
                                <p>${project.end}</p>
                            </div>
                        </div>
                    </div>
                    <div class="project-info-item">
                        <p class="project-description">${project.description}</p>
                    </div>
                    <div class="project-info-item">
                        <h3>MEMBERS</h3>
                        <div class="members-images">
                            <!-- Tutaj można dodać obrazy członków projektu -->
                        </div>
                    </div>
                    <div class="project-info-item">
                        <div class="project-info-item-footer">
                            <div class="tasks-counter">
                                <p>${project.tasksID.length}</p>
                                <h3>Tasks</h3>
                            </div>
                            <h3>
                                <a href="#">Go to the project</a>
                            </h3>
                        </div>
                    </div>
                </div>
            </div>
        </div>
      `;

                // Dodaj element HTML do kontenera na stronie
                projectsContainer.appendChild(projectElement);
            });

            // Pobierz wszystkie divy z klasą .members-images
            const membersImagesDivs = document.querySelectorAll('.members-images');

            // Przejdź przez każdy div
            membersImagesDivs.forEach(div => {
                // Pobierz wszystkie obrazy w divie
                const images = div.querySelectorAll('img');

                // Jeśli jest więcej niż 7 obrazów
                if (images.length > number) {
                    // Ukryj obrazy poza pierwszymi siedmioma
                    for (let i = number; i < images.length; i++) {
                        images[i].style.display = 'none';
                    }

                    // Dodaj element z tekstem "+liczba"
                    const extraCount = images.length - number;
                    const extraElement = document.createElement('span');
                    extraElement.classList.add('extra');
                    extraElement.textContent = '+' + extraCount;
                    div.appendChild(extraElement);
                }
            });

            // Wywołaj funkcję truncateDescription
            truncateDescription();
        })
        .catch(error => console.error('Error:', error));


    // Pobierz wszystkie divy z klasą .members-images
    // const membersImagesDivs = document.querySelectorAll('.members-images');
    const openModal = document.querySelector('#addProjectButton');
    const closeModal = document.querySelector('#closeModal');
    const modal = document.querySelector('.modal');
    const number = 5;


    // Select the start and end date input fields
    const startDateInput = document.querySelector('#projectStartDate');
    const endDateInput = document.querySelector('#projectEndDate');

    // Add event listener to the start date input field
    startDateInput.addEventListener('change', function () {
        // Set the min attribute of the end date input field to the selected start date
        endDateInput.min = startDateInput.value;
    });
    // Add event listener to the start date input field
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

        // Iterate over each description
        descriptions.forEach(description => {
            if (description.textContent.length > 97) {
                description.textContent = description.textContent.slice(0, 97) + '...';
            }
        });
    }


    const form = document.querySelector('.form');

    form.addEventListener('submit', function (event) {
        // Prevent form submission
        event.preventDefault();


        // Extract form data
        const projectName = document.querySelector('#projectName').value;
        const projectDescription = document.querySelector('#projectDescription').value;
        const projectMembers = Array.from(document.querySelector('#projectMembers').selectedOptions).map(option => option.value);
        const projectStatus = document.querySelector('#projectStatus').value;
        const projectStartDate = document.querySelector('#projectStartDate').value;
        const projectEndDate = document.querySelector('#projectEndDate').value;

        // Create new project
        const newProject = {
            name: projectName,
            description: projectDescription,
            members: projectMembers,
            status: projectStatus,
            startDate: projectStartDate,
            endDate: projectEndDate
        };

        // Append new project to the list (you might need to adjust this part based on your HTML structure)
        const projectsWrapper = document.querySelector('.wrapper');
        const newProjectElement = document.createElement('div');
        newProjectElement.classList.add('block-wrapper');
        newProjectElement.innerHTML = `
        <div class="block-content">
            <div class="project-header">
                <h2>${newProject.name}</h2>
            </div>
            <div class="project-body">
                <div class="project-info">
                    <div class="project-info-item">
                        <div class="status-date">
                            <h3 class="project-status ${newProject.status.toLowerCase()}">${newProject.status}</h3>
                            <div class="project-due-date">
                                <h3>Due date</h3>
                                <p>${newProject.endDate}</p>
                            </div>
                        </div>
                    </div>
                    <div class="project-info-item">
                        <p class="project-description">${newProject.description}</p>
                    </div>
                    <div class="project-info-item">
                        <h3>MEMBERS</h3>
                        <div class="members-images">
                            <!-- You might need to adjust this part based on how you want to display members -->
                            ${newProject.members.map(member => `<img src="images/${member}.png" alt="">`).join('')}
                        </div>
                    </div>
                    <div class="project-info-item">
                        <div class="project-info-item-footer">
                            <div class="tasks-counter">
                                <p>0</p>
                                <h3>Tasks</h3>
                            </div>
                            <h3>
                                <a href="#">Go to the project</a>
                            </h3>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    `;
        projectsWrapper.insertBefore(newProjectElement, projectsWrapper.children[1]);

        truncateDescription();

        modal.close();
    });

    truncateDescription();

})
;
$(document).ready(function () {

    function matchCustom(params, data) {
        // If there are no search terms, return all of the data
        if ($.trim(params.term) === '') {
            return data;
        }

        // Do not display the item if there is no 'text' property
        if (typeof data.text === 'undefined') {
            return null;
        }

        // Convert both search term and data.text to lower case
        var searchTermLowerCase = params.term.toLowerCase();
        var dataTextLowerCase = data.text.toLowerCase();

        // `params.term` should be the term that is used for searching
        // `data.text` is the text that is displayed for the data object
        if (dataTextLowerCase.indexOf(searchTermLowerCase) > -1) {
            var modifiedData = $.extend({}, data, true);
            modifiedData.text += ' (matched)';

            // You can return modified objects from here
            // This includes matching the `children` how you want in nested data sets
            return modifiedData;
        }

        // Return `null` if the term should not be displayed
        return null;
    }

    // Inicjalizacja Select2 na elemencie select
    $('#projectMembers').select2({
        dropdownParent: $('.form'),
        matcher: matchCustom,
        minimumInputLength: 1, // Ilość wpisanych znaków, po których zaczyna się wyszukiwanie
        placeholder: 'Select members', // Tekst wyświetlany, gdy nie wybrano żadnego użytkownika
        allowClear: true, // Pozwala na usunięcie wszystkich wybranych użytkowników
        ajax: {
            url: 'fetch/employeesList.json',
            dataType: 'json',
            processResults: function (data) {
                return {
                    results: data.map(employee => {
                        return {
                            id: employee.IDNumber,
                            text: employee.employeeName + ' (ID Number: ' + employee.IDNumber + ')'
                        }
                    })
                };
            }
        }
    });

});