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