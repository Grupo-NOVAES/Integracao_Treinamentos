document.querySelectorAll(".single-select").forEach((checkbox) => {
    checkbox.addEventListener("change", function() {

        const questionDiv = document.getElementById(`question-${this.id}`);
        
        if (this.checked) {
            const nrSelected = this.id;
            console.log('NR selecionada: ' + nrSelected);
            
            document.querySelectorAll(".single-select").forEach((otherCheckbox) => {
                if(otherCheckbox !== this){
                    otherCheckbox.disabled = true;
                }
            });

            questionDiv.style.display="block";
            
        }else {
            document.querySelectorAll('.single-select').forEach((otherCheckbox) => {
                otherCheckbox.disabled = false;
            })

            questionDiv.style.display="none";
        }

    });
});


const questionCards = document.querySelectorAll(".question-card");
const prevButton = document.getElementById("prevBtn");
const nextButton = document.getElementById("nextBtn");
const sendForm = document.getElementById("btnExam");
let currentIndex = 0;


function updateQuestions(){
    questionCards.forEach((questionCard, index) => {

        questionCard.style.display = (index === currentIndex) ? "block" : "none";

       });

       nextButton.style.display = currentIndex === questionCards.length - 1 ? "none" : "block";
       prevButton.style.display = currentIndex === 0 ? "none" : "block";
       sendForm.style.display = currentIndex === questionCards.length - 1 ? "block" : "none";
    }


    nextButton.addEventListener('click', function(){
        if(currentIndex < questionCards.length - 1){
            currentIndex++;
            console.log('current++:'+currentIndex);
            updateQuestions();
        }
    })

    prevButton.addEventListener('click', function(){
        if(currentIndex > 0){
            currentIndex--;
            console.log('current--: '+currentIndex);
            updateQuestions();
        }
    })


updateQuestions();





document.getElementById("btnExam").addEventListener('click', function() {
    const swalWithBootstrapButtons = Swal.mixin({
      customClass: {
        confirmButton: "btn btn-success",
        cancelButton: "btn btn-danger"
      },
      buttonsStyling: false
    });

    // Montar uma string para exibir as respostas selecionadas
    let respostasParaRevisao = "Suas respostas:\n\n";
    respostasSelecionadas.forEach((resposta) => {
      respostasParaRevisao += `Pergunta ${resposta.pergunta}: ${resposta.resposta}`;
    });

    swalWithBootstrapButtons.fire({
      title: "Enviar respostas?",
      text: respostasParaRevisao + "\n\nVocê pode voltar e alterar as respostas.",
      icon: "question",
      showCancelButton: true,
      confirmButtonText: "Sim, enviar",
      cancelButtonText: "Cancelar",
      reverseButtons: true
    }).then((result) => {
      if (result.isConfirmed) {
        swalWithBootstrapButtons.fire({
          title: "Respostas Enviadas!",
          text: "Até a próxima.",
          icon: "success"
        }).then(() => {
          window.location = "thanks.html";
        });
      }
    });
});

let respostasSelecionadas = [];

document.querySelectorAll(".inputRadio").forEach((radio) => {
  radio.addEventListener("change", function() {
    if (this.checked) {
      const perguntaId = this.name;
      const valorResposta = this.value;

      const indiceExistente = respostasSelecionadas.findIndex(
        (resposta) => resposta.pergunta === perguntaId
      );

      if (indiceExistente !== -1) {
        respostasSelecionadas[indiceExistente].resposta = valorResposta;
      } else {
        respostasSelecionadas.push({ pergunta: perguntaId, resposta: valorResposta });
      }

      console.log(JSON.stringify(respostasSelecionadas));
    }
  });
});
