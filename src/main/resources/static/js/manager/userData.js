// const userId = document.getElementById("userId").value;
// const linkResponseButtons = document.querySelectorAll(".linkResponseList");

// linkResponseButtons.forEach(button => {
//   button.addEventListener('click', function () {
//     const idUser = this.getAttribute("data-iduser");
//     const nrNumber = this.getAttribute("data-nrnumber");
    
//     goToListResponses(idUser, nrNumber);
//   });
// });

// function goToListResponses(idUser, nrNumber) {
//   window.location.href = `/user/infoClient/${idUser}/${nrNumber}`;
// }



const btnOptions = document.getElementById("btnOptionUser").addEventListener('click',showModalOptionsUser)

let selectedUserId = null;
let selectedNrId = null;
let modalVisible = false;



document.addEventListener('click', function(event) {
  const btnOptions = document.getElementById('options');
  const target = event.target;

  if (modalVisible && !btnOptions.contains(target) && !target.closest('button')) {
      hideModalOptionsUser();
  }

});


function showModalOptionsUser(button) {
  const options = document.getElementById('options');
  const rect = button.getBoundingClientRect();
  selectedUserId = button.closest('tr').getAttribute('data-iduser'); // Corrigido para data-iduser
  selectedNrId = button.closest('tr').getAttribute('data-nrnumber');

  const marginLeft = 125;
  const marginTop = -40;

  let modalLeft = rect.left - marginLeft;
  let modalTop = rect.top + marginTop;

  options.style.top = `${modalTop}px`;
  options.style.left = `${modalLeft}px`;

  options.style.display = 'block';
  modalVisible = true;
}


function hideModalOptionsUser(){
  const options = document.getElementById('options');
  options.style.display="none";
}




function redirectToInfo(){
  window.location.href="/user/infoClient/"+selectedUserId+"/"+selectedNrId;
}

function redirectToCertificados(){
  window.location.href="/user/infoClient/"+selectedUserId;
}

function downloadCertificate() {
  window.location.href=`/user/downloadCertify/${selectedUserId}/${selectedNrId}`
}