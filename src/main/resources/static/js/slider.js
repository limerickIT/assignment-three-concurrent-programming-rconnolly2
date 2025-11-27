const slider = document.getElementById("slider");
let index = 0;
function nextSlide() {
    index = (index + 1) % 3;
    slider.style.transform = `translateX(-${index * 100}%)`;
}
setInterval(nextSlide, 4000);