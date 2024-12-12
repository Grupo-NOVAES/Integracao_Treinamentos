const userId = document.getElementById("userId").value;
const linkResponseButtons = document.querySelectorAll(".linkResponseList");

linkResponseButtons.forEach(button => {
  button.addEventListener('click', function () {
    const idUser = this.getAttribute("data-iduser");
    const nrNumber = this.getAttribute("data-nrnumber");
    
    goToListResponses(idUser, nrNumber);
  });
});

function goToListResponses(idUser, nrNumber) {
  window.location.href = `/user/infoClient/${idUser}/${nrNumber}`;
}

