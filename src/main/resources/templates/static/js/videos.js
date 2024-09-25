import links from "./links.js";

const prevBtn = document.getElementById("prevBtn");
const nextBtn = document.getElementById("nextBtn");
let currentQuestion = 1;
const totalQuestions = document.querySelectorAll(".question-card").length;

console.log(sessionStorage);

export function showQuestion(questionNumber) {
  const questions = document.querySelectorAll(".question-card");
  questions.forEach((question) => {
    question.classList.remove("active");
  });
  document.querySelector(`#question${questionNumber}`).classList.add("active");
}

export function nextQuestion() {
  if (!validateCurrentQuestion()) {
    Swal.fire({
      title: "Responda a pergunta antes de avançar!",
      text: "Verifique sua resposta",
      icon: "warning",
    });
    return;
  }
  if (currentQuestion < totalQuestions + 1) {
    guardarResposta();
    guardarRespostaTexto();
    currentQuestion++;
    console.log(currentQuestion)
    if (currentQuestion === 7) {
      goToFinal();
      currentQuestion = currentQuestion-1;
    } else {
      showQuestion(currentQuestion);
    }
    

  }
}

export function prevQuestion() {
  console.log(currentQuestion);
  currentQuestion--;
  if (currentQuestion > 0) {
    showQuestion(currentQuestion);
  }
  if (currentQuestion === 0 ) {
    //window.location.href = "../HTML/login.html";
    window.location.href = links.LoginPage;
    currentQuestion++;
    showQuestion(currentQuestion);
  }
  
}

export function validateCurrentQuestion() {
  const currentQuestionCard = document.querySelector(
    `#question${currentQuestion}`
  );
  const radioInputs = currentQuestionCard.querySelectorAll(
    'input[type="radio"]'
  );
  const checkboxInputs = currentQuestionCard.querySelectorAll(
    'input[type="checkbox"]'
  );
  const textareaInput = currentQuestionCard.querySelector("textarea");

  if (radioInputs.length > 0) {
    const checkedRadio = Array.from(radioInputs).some((input) => input.checked);
    if (!checkedRadio) return false;
  }

  if (checkboxInputs.length > 0) {
    const checkedCheckbox = Array.from(checkboxInputs).some(
      (input) => input.checked
    );
    if (!checkedCheckbox) return false;
  }

  if (textareaInput) {
    if (!textareaInput.value.trim()) return false;
  }

  return true;
}

export function guardarResposta() {
  const respostaSelecionada = document.querySelector(
    `input[name="${currentQuestion}"]:checked`
  );
  if (respostaSelecionada) {
    const resposta = respostaSelecionada.value;
    const idResposta = respostaSelecionada.getAttribute("name");
    user.anwsers.push({ id: "Pergunta" + idResposta, resposta: resposta });
    console.log(user.anwsers);
  }
}

export function guardarRespostaTexto() {
  const respostaTexto = document.querySelector(
    `textarea[name="${currentQuestion}"]`
  );
  if (respostaTexto) {
    const resposta = respostaTexto.value;
    const idResposta = respostaTexto.getAttribute("name");
    user.anwsers.push({ id: `questao${currentQuestion}`, resposta: resposta });
    console.log(user.anwsers.toString());
  }
}

export function goToFinal() {
  guardarResposta();

  Swal.fire({
    title: "Iniciar questionário?",
    text: "Você terá 15 minutos para realizar o questionário!",
    icon: "question",
    showCancelButton: true,
    confirmButtonColor: "#3085d6",
    cancelButtonColor: "#d33",
    confirmButtonText: "Sim, iniciar",
    cancelButtonText: "Não"
  }).then((result) => {
    if(result.isConfirmed){
      window.location.href=links.FormsPage;
      //window.location.href='../HTML/indexForms.html'
    }else if(result.isDenied){
      console.log("RECUSADO!")
    }
  });
  
}

showQuestion(currentQuestion);

prevBtn.addEventListener("click", prevQuestion);
nextBtn.addEventListener("click", nextQuestion);


const nav = document.querySelector('nav');
export function showSidebar(){
  nav.style.display="block";
}




const nr6Checkbox = document.querySelector('input[type="checkbox"][value="NR6"]');
const nr10Checkbox = document.querySelector('input[type="checkbox"][value="NR10"]');
const nr11Checkbox = document.querySelector('input[type="checkbox"][value="NR11"]');
const nr12Checkbox = document.querySelector('input[type="checkbox"][value="NR12"]');
const nr15Checkbox = document.querySelector('input[type="checkbox"][value="NR15"]');
const nr18Checkbox = document.querySelector('input[type="checkbox"][value="NR18"]');

const questionNR6Card = document.getElementById('question1');
const questionNR10Card = document.getElementById('question2');
const questionNR11Card = document.getElementById('question3');
const questionNR12Card = document.getElementById('question4');
const questionNR15Card = document.getElementById('question5');
const questionNR18Card = document.getElementById('question6');

function toggleCheckboxes(selectedCheckbox) {
  const checkboxes = [nr6Checkbox, nr10Checkbox, nr11Checkbox, nr12Checkbox, nr15Checkbox, nr18Checkbox];

  checkboxes.forEach(checkbox => {
    if (checkbox !== selectedCheckbox) {
      checkbox.disabled = selectedCheckbox.checked;
    }
  });
}

nr6Checkbox.addEventListener('change', function() {
  toggleCheckboxes(this);
  questionNR6Card.style.display = this.checked ? 'block' : 'none';
});

nr10Checkbox.addEventListener('change', function() {
  toggleCheckboxes(this);
  questionNR10Card.style.display = this.checked ? 'block' : 'none';
});

nr11Checkbox.addEventListener('change', function() {
  toggleCheckboxes(this);
  questionNR11Card.style.display = this.checked ? 'block' : 'none';
});

nr12Checkbox.addEventListener('change', function() {
  toggleCheckboxes(this);
  questionNR12Card.style.display = this.checked ? 'block' : 'none';
});

nr15Checkbox.addEventListener('change', function() {
  toggleCheckboxes(this);
  questionNR15Card.style.display = this.checked ? 'block' : 'none';
});

nr18Checkbox.addEventListener('change', function() {
  toggleCheckboxes(this);
  questionNR18Card.style.display = this.checked ? 'block' : 'none';
});

questionNR6Card.style.display = 'none';
questionNR10Card.style.display = 'none';
questionNR11Card.style.display = 'none';
questionNR12Card.style.display = 'none';
questionNR15Card.style.display = 'none';
questionNR18Card.style.display = 'none';
