document.getElementById("loginForm").addEventListener("submit", function(event) {
    event.preventDefault();
    window.location.href = "pages/home.html";
});

document.querySelectorAll(".stars").forEach(starsContainer => {
    const stars = starsContainer.querySelectorAll(".star");
    stars.forEach((star, index) => {
        star.addEventListener("click", () => {
            starsContainer.setAttribute("data-rating", index + 1);
            stars.forEach((s, i) => {
                s.textContent = i <= index ? "★" : "☆";
            });
        });
    });
});
