
const sideMenu = document.querySelector('aside');
const menuBtn = document.getElementById('menu-btn');
const closeBtn = document.getElementById('close-btn');

const lightMode = document.querySelector('.light-mode');

menuBtn.addEventListener('click', () => {
    sideMenu.style.display = 'block';
});

closeBtn.addEventListener('click', () => {
    sideMenu.style.display = 'none';
});

window.addEventListener('resize', () => {
    if (window.innerWidth > 768) {
        sideMenu.style.display = 'block';
    }
});

lightMode.addEventListener('click', () => {
    document.body.classList.toggle('light-mode-variables');
    lightMode.querySelector('span:nth-child(1)').classList.toggle('active');
    lightMode.querySelector('span:nth-child(2)').classList.toggle('active');
})


