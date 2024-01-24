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
            var options = { weekday: 'long', year: 'numeric', month: 'long', day: 'numeric' };
            var start = new Date(info.event.start).toLocaleDateString('en-EN', options);
            var end = new Date(info.event.end).toLocaleDateString('en-EN', options);
            alert('Title: ' + info.event.title +
                '\nDescription: ' + info.event.extendedProps.description +
                '\nStart: ' + start +
                '\nEnd: ' + end);
        }
    });

    calendar.render();
});