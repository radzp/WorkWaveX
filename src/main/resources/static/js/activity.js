document.addEventListener('DOMContentLoaded', () => {

    fetch('fetch/employeesList.json')
        .then(response => response.json())
        .then(Employees => {
            const activeUsers = Employees.filter(employee => employee.status === 'active');
            const userList = document.querySelector('.user-list');

            activeUsers.forEach(user => {
                const userDiv = document.createElement('div');
                userDiv.classList.add('user');

                const img = document.createElement('img');
                img.src = user.photo;

                const h2 = document.createElement('h2');
                h2.textContent = user.employeeName;

                const statusInfo = document.createElement('div');
                statusInfo.classList.add('status-info');

                const p = document.createElement('p');
                p.textContent = 'Active';

                statusInfo.appendChild(p);
                userDiv.append(img, h2, statusInfo);
                userList.appendChild(userDiv);
            });
            
        }).then(() => {
        const usersStatus = document.querySelectorAll('.user p');
        if (usersStatus) {
            usersStatus.forEach(userStatus => {
                if (userStatus.textContent === 'Active') {
                    const span = document.createElement('span');
                    span.classList.add('green-blink');
                    span.innerHTML = '<i class="fas fa-circle"></i>';
                    userStatus.insertBefore(span, userStatus.firstChild);
                }
            });
        }  
        })
        .catch(error => console.error('Error:', error));


    


    var ctx = document.getElementById('myChart').getContext('2d');

    var myChart = new Chart(ctx, {
        type: 'doughnut',
        data: {
            labels: ['Active users', 'Inactive users'],
            datasets: [{
                data: [45, 55], // tutaj wprowadź rzeczywiste dane
                backgroundColor: [
                    'green',
                    'red'
                ],
                borderWidth: 0,
                borderColor: 'black'
            }]
        },
        options: {
            responsive: false,
            responsiveAnimationDuration: 500, // czas trwania animacji przy zmianie rozmiaru w milisekundach
            aspectRatio: 2,
            legend: {
                position: 'top',
            },
            title: {
                display: false,
                text: 'Stosunek aktywnych użytkowników do nieaktywnych'
            },
            animation: {
                animateScale: true,
                animateRotate: true
            }
        }
    });
});
