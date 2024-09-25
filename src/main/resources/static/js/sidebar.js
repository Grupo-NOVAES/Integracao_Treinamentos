let bodyElement = document.querySelector('body');

  BarraLateral = bodyElement.querySelector(".BarraLateral"),

BarraLateral.addEventListener("click", () => {
  BarraLateral.classList.toggle("close");
});