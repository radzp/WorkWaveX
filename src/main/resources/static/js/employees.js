document.addEventListener('DOMContentLoaded', () => {
    fetch('/api/v1/user-controller/all')
        .then(response => response.json())
        .then(users => {
            users.forEach(user => {
                const tr = document.createElement('tr');
                tr.innerHTML = `
                <td>${user.firstName} ${user.lastName}</td>
                <td>${user.id}</td>
                <td>${user.position}</td>
                <td>${user.email}</td>
                <td>${user.fullPhoneNumber}</td>
                <td>${user.salary}</td>
                <td><button class="material-symbols-sharp edit-button">
                    edit
                    </button>
                </td>
                <td><button class="material-symbols-sharp delete-button">
                    delete
                    </button>
                </td>
            `;
                document.querySelector('table tbody').appendChild(tr);
            });

            const editButtons = document.querySelectorAll('.edit-button');
            const modal = document.querySelector('.modal');

            const submitButton = document.querySelector('.submit');
            const deleteButtons = document.querySelectorAll('.delete-button');
            deleteButtons.forEach((deleteButton, index) => {
                deleteButton.addEventListener('click', () => {
                    const confirmation = confirm('Do you really wanna delete this user?');
                    if (confirmation) {
                        const userId = users[index].id;
                        fetch(`/api/v1/user-controller/${userId}`, {
                            method: 'DELETE',
                        })
                            .then(response => {
                                if (response.ok) {
                                    alert(`User with id ${userId} has been deleted.`);
                                    // Usuń wiersz użytkownika z tabeli
                                    deleteButton.parentElement.parentElement.remove();
                                } else {
                                    console.error('Error:', response.statusText);
                                }
                            })
                            .catch(error => console.error('Error:', error));
                    }
                });
            });

            editButtons.forEach(editButton => {
                editButton.addEventListener('click', () => {
                    modal.showModal();
                });
                editButtons.forEach((button, index) => {
                    button.addEventListener('click', () => {
                        // Pobranie danych pracownika
                        let employee = users[index];

                        // Wypełnienie pól formularza danymi pracownika
                        document.getElementById('employeeName').value = employee.firstName + ' ' + employee.lastName;
                        document.getElementById('idNumber').value = parseInt(employee.id);
                        document.getElementById('position').value = employee.position;
                        document.getElementById('email').value = employee.email;
                        document.getElementById('phoneNumber').value = employee.fullPhoneNumber;
                        document.getElementById('salary').value = employee.salary;

                        // Wyświetlenie modala
                        modal.showModal();
                    });
                });
            });

            const closeButton = document.getElementById('closeModal');
            closeButton.addEventListener('click', () => {
                modal.close();
            });

            submitButton.addEventListener('click', () => {
                const employeeData = {
                    id: document.getElementById('idNumber').value,
                    firstName: document.getElementById('employeeName').value.split(' ')[0],
                    lastName: document.getElementById('employeeName').value.split(' ')[1],
                    position: document.getElementById('position').value,
                    email: document.getElementById('email').value,
                    fullPhoneNumber: document.getElementById('phoneNumber').value,
                    salary: document.getElementById('salary').value
                };

                // Walidacja danych
                if (!employeeData.firstName || !employeeData.lastName || !employeeData.position || !employeeData.email || !employeeData.fullPhoneNumber || !employeeData.salary) {
                    alert('All correct data should be provided');
                    return;
                }

                const nameRegex = /^[a-zA-Z]+$/;
                if (!nameRegex.test(employeeData.firstName) || !nameRegex.test(employeeData.lastName)) {
                    alert('First name and last name should contain only letters');
                    return;
                }

                const emailRegex = /^[\w-]+(\.[\w-]+)*@([\w-]+\.)+[a-zA-Z]{2,7}$/;
                if (!emailRegex.test(employeeData.email)) {
                    alert('Email is not valid');
                    return;
                }

                const phoneRegex = /^\+\d{1,3}\d{1,14}$/;
                if (!phoneRegex.test(employeeData.fullPhoneNumber)) {
                    alert('Phone number should start with + and contain only numbers');
                    return;
                }

                const salaryRegex = /^\d+$/;
                if (!salaryRegex.test(employeeData.salary)) {
                    alert('Salary should contain only numbers');
                    return;
                }

                fetch(`/api/v1/user-controller/${employeeData.id}`, {
                    method: 'PUT',
                    headers: {
                        'Content-Type': 'application/json',
                    },
                    body: JSON.stringify(employeeData),
                })
                    .then(response => response.json())
                    .then(data => {
                        console.log('Success:', data);
                        location.reload();
                    })
                    .catch((error) => {
                        console.error('Error:', error);
                    });

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