document.querySelectorAll(".single-select").forEach((checkbox) => {
    checkbox.addEventListener("change", function () {
      const descriptionDiv = document.getElementById(`description-${this.id}`);
      const intros = document.getElementById("intro");   
      const mains = document.getElementById("main");
  
      if (this.checked) {
        console.log('nr id:'+this.id);
        // Desmarca outras checkboxes, oculta suas descrições e desabilita
        document.querySelectorAll(".single-select").forEach((otherCheckbox) => {
            if (otherCheckbox !== this) {
              otherCheckbox.checked = false;
              otherCheckbox.disabled = true; // Desabilita os outros checkboxes
              document.getElementById(
                `description-${otherCheckbox.id}`
              ).style.display = "none";
            }
          });
  
        // Mostra a descrição correspondente
        descriptionDiv.style.display = "block";
        mains.style.justifyContent = "space-evenly";
        intros.style.display="none";
        
        // Atualiza os vídeos
        updateVideos();
      } else {
        // Oculta a descrição se desmarcada e reabilita todas as checkboxes
        descriptionDiv.style.display = "none";
        intros.style.display = "block";
        mains.style.justifyContent = "";
        document
          .querySelectorAll(".single-select")
          .forEach((otherCheckbox) => {
            otherCheckbox.disabled = false; // Reabilita os checkboxes
          });
      }
    });
  });
  
  const videos = document.querySelectorAll(".video-card");
  const nextButton = document.getElementById("nextBtn");
  const prevButton = document.getElementById("prevBtn");
  const btnExam = document.getElementById("btnExam");
  let currentIndex = 0;
  
  function updateVideos(){
    videos.forEach((video, index) => {
      video.style.display = (index === currentIndex) ? "block" : "none";
    });
  
    prevButton.style.display = currentIndex > 0 ? "block" : "none";
    nextButton.style.display = currentIndex === videos.length - 1 ? "none" : "block";
    btnExam.style.display = currentIndex === videos.length - 1 ? "block" : "none";
  }
  
  nextButton.addEventListener('click', function(){
    if(currentIndex < videos.length - 1){
      currentIndex++;
      updateVideos();
    }
  });
  
  prevButton.addEventListener('click', function(){
    if(currentIndex > 0){
      currentIndex--;
      updateVideos();
    }
  });
  
  updateVideos();




  
  document.getElementById("btnExam").addEventListener('click', function(){
    const swalWithBootstrapButtons = Swal.mixin({
      customClass: {
        confirmButton: "btn btn-success",
        cancelButton: "btn btn-danger"
      },
      buttonsStyling: false
    });
    
    swalWithBootstrapButtons.fire({
      title: "Começar exame?",
      text: "Você poderá voltar aos vídeos caso necessário.",
      icon: "question",
      showCancelButton: true,
      confirmButtonText: "Sim, aceito",
      cancelButtonText: "Cancelar",
      reverseButtons: true
    }).then((result) => {
      if (result.isConfirmed) {
        swalWithBootstrapButtons.fire({
          title: "Você tem um prazo de 10 minutos para fazer a prova!",
          text: "Boa sorte.",
          icon: "warning"
        }).then((result) => {
            window.location = "forms.html";
        });
      } 
    });
  });
  