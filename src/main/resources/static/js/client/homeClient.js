document.addEventListener("DOMContentLoaded", () => {
    const cards = document.querySelectorAll(".card-content");
  
    cards.forEach(card => {
      const status = card.getAttribute("data-status");
      const btn = card.querySelector(".btnPerformNR");
      const statusMessage = card.querySelector(".statusMessage");
      const statusSpan = card.querySelector(".statusNr");
  
      // Aplica a classe com base no status (p/ estilização opcional)
      if (statusSpan) {
        statusSpan.classList.add(status.toLowerCase());
      }
  
      // Define a lógica de exibição
      if (status === "Inconcluida" || status === "Reavaliacao") {
        btn.style.display = "inline-block";
        statusMessage.style.display = "none";
      } else {
        btn.style.display = "none";
        statusMessage.style.display = "block";
        statusMessage.textContent = "Esta NR já foi Concluída";
      }
  
      // Adiciona o evento de click no botão
      btn.addEventListener("click", () => {
        const selectedNrNumber = card.getAttribute("data-number");
        window.location.href = `/question/player/${selectedNrNumber}`;
      });
    });
  });
  