document.addEventListener('DOMContentLoaded', function() {
    var calendarEl = document.getElementById('calendar');

    var calendar = new FullCalendar.Calendar(calendarEl, {
        events : "fetch/tasks.json",
        headerToolbar: {
            start: 'dayGridMonth,timeGridWeek,timeGridDay,listWeek',
            center: 'title,addTaskForEmployeeButton,addTaskForWholeProjectTeamButton',
            end: 'today prevYear,prev,next,nextYear'
        },
        footerToolbar: {
            start: '',
            center: '',
            end: 'prev,next'
        },
        customButtons: {
            addTaskForEmployeeButton: {
                text: 'Add a new task for employee',
                click: function() {
                    var dateStr = prompt('Enter a date in YYYY-MM-DD format');
                    var date = new Date(dateStr + 'T00:00:00'); // will be in local time

                    if (!isNaN(date.valueOf())) { // valid?
                        calendar.addEvent({
                            title: 'dynamic event',
                            start: date,
                            allDay: true
                        });
                        alert('Great. Now, update your database...');
                    } else {
                        alert('Invalid date.');
                    }
                }
            },
            addTaskForWholeProjectTeamButton:{
                text: 'Add a new task for whole project team',
                click: function() {
                    var dateStr = prompt('Enter a date in YYYY-MM-DD format');
                    var date = new Date(dateStr + 'T00:00:00'); // will be in local time

                    if (!isNaN(date.valueOf())) { // valid?
                        calendar.addEvent({
                            title: 'dynamic event',
                            start: date,
                            allDay: true
                        });
                        alert('Great. Now, update your database...');
                    } else {
                        alert('Invalid date.');
                    }
                }
            }
        }
    });

    calendar.render();
});