const map = L.map('map');
map.setView([51.236316954731635, 22.54844492611861], 15);
L.tileLayer('https://tile.openstreetmap.org/{z}/{x}/{y}.png', {
    maxZoom: 19,
    attribution: '&copy; <a href="http://www.openstreetmap.org/copyright">OpenStreetMap</a>'
}).addTo(map);

const marker = L.marker([51.236316954731635, 22.54844492611861]).addTo(map);
marker.bindPopup("<strong style='font-weight: bolder'>Here we are!</strong><br>Visit our office.").openPopup();
