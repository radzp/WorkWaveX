document.addEventListener('DOMContentLoaded', function() {
    var calendarEl = document.getElementById('calendar');

    var calendar = new FullCalendar.Calendar(calendarEl, {
        events : "/api/v1/tasks/allEvents",
        headerToolbar: {
            start: 'dayGridMonth,listWeek',
            center: 'title',
            end: 'today prevYear,prev,next,nextYear'
        },
        footerToolbar: {
            start: '',
            center: '',
            end: 'prev,next'
        },
        eventClick: function(info) {
            alert('Title: ' + info.event.title +
                '\nDescription: ' + info.event.extendedProps.description);
        }
    });

    calendar.render();
});