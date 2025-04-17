let modalVisible = false;
let selectedQuestionId = null;
let NrNumber;
const path = window.location.pathname;
const regex = /\/Nr\/(\d+)(\/|$)/;
const match = regex.exec(path);


const btnNewQuestion = document.getElementById("btnNewQuestion");

document.addEventListener("DOMContentLoaded", function () {
    
    const deleteForms = document.querySelectorAll("form .questionIdIput");

    deleteForms.forEach((input) => {
      const parent = input.closest('.box-questions')
      const questionId = parent.getAttribute("data-id");
      input.value = questionId;
    });
    getNrNumber();
  });

document.addEventListener('click', function(event) {
    const btnOptions = document.getElementById('options');
    const ModalAdd = document.getElementById('addModal');
    const target = event.target;

    if (modalVisible && !btnOptions.contains(target) && !target.closest('button')) {
        hideModalOptionsQuestion();
    }

    if(modalVisible && !ModalAdd.contains(target) && !target.closest('button')){
        hideModalAddQuestion()
    }

});

function getNrNumber(){
    if (match) {
        NrNumber = match[1];
    } else {
        console.log("Número não encontrado na URL");
    }
}

function showModalOptionsQuestion(button){
    const options = document.getElementById('options');
    const rect = button.getBoundingClientRect();

    const marginLeft = 125;
    const marginTop = -40;

    let modalLeft = rect.left - marginLeft;
    let modalTop = rect.top + marginTop;

    options.style.top = `${modalTop}px`;
    options.style.left = `${modalLeft}px`;

    options.style.display = 'block';
    modalVisible = true;
}
function hideModalOptionsQuestion(){
    const options = document.getElementById('options');
    options.style.display="none";
}


function redirectToQuestionInfo(){
    window.location.href="/question/"+selectedQuestionId;
}

function showModalAddQuestion(){
    const AddModal = document.getElementById('addModal');
    AddModal.style.display = 'block';
    modalVisible = true;
}
function hideModalAddQuestion(){
    const AddModal = document.getElementById('addModal');
    AddModal.style.display="none";
    modalVisible = false;
}

function showMenuQuestion() {
    const dropdownMenu = document.getElementById('dropdownMenu');
    dropdownMenu.classList.toggle('active');
}

function leavePageAddNewQuestion(){
    window.location.href=`/question/newQuestion/${NrNumber}`;//newQuestion/{nrNumber}
}

btnNewQuestion.addEventListener('click',leavePageAddNewQuestion)