document.addEventListener('DOMContentLoaded', () => {
    fetch('fetch/employeesList.json')
        .then(response => response.json())
        .then(Employees => {
            Employees.forEach(employee => {
                const tr = document.createElement('tr');
                tr.innerHTML = `
                <td id="employeeName">${employee.employeeName}</td>
                <td id="employeeIdNumber">${employee.IDNumber}</td>
                <td id="employeePhoto">${employee.photo}</td>
                <td id="employeePosition">${employee.position}</td>
                <td id="employeeEmail">${employee.email}</td>
                <td id="employeePhoneNumber">${employee.phoneNumber}</td>
                <td id="employeeHours">${employee.hours}</td>
                <td id="employeeSalary">${employee.salary}</td>
                <td><button class="material-symbols-sharp edit-button">
                    edit
                    </button>
                </td>
            `;
                document.querySelector('table tbody').appendChild(tr);
            });

            const editButtons = document.querySelectorAll('.edit-button');
            const modal = document.querySelector('.modal');

            const submitButton = document.querySelector('.submit');

            editButtons.forEach(editButton => {
                editButton.addEventListener('click', () => {
                    modal.showModal();
                });
                editButtons.forEach((button, index) => {
                    button.addEventListener('click', () => {
                        // Pobranie danych pracownika
                        let employee = Employees[index];

                        // Wypełnienie pól formularza danymi pracownika
                        document.getElementById('employeeName').value = employee.employeeName;
                        document.getElementById('idNumber').value = parseInt(employee.IDNumber);
                        document.getElementById('photo').value = employee.photo;
                        document.getElementById('position').value = employee.position;
                        document.getElementById('email').value = employee.email;
                        document.getElementById('phoneNumber').value = employee.phoneNumber;
                        document.getElementById('hours').value = employee.hours;
                        document.getElementById('salary').value = employee.salary;

                        // Wyświetlenie modala
                        modal.showModal();
                    });
                });
            });

            submitButton.addEventListener('click', () => {
                modal.close();
            });

            const thElements = document.querySelectorAll('.employees-list table thead th');
            const tdElements = document.querySelectorAll('.employees-list table tbody td');

            thElements.forEach((th, index) => {
                const tdWidth = tdElements[index].offsetWidth;
                const thWidth = th.offsetWidth;
                const diff = (tdWidth - thWidth) / 2;
                th.style.width = `${tdWidth}px`;
                th.style.paddingLeft = `${diff}px`;
                th.style.paddingRight = `${diff}px`;
                th.style.textAlign = 'center'; // Centrowanie tekstu
            });
        })
        .catch(error => console.error('Error:', error));
});