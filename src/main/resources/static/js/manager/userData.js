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


async function activeUser(){
    console.log("função chamada!")
    let response = await fetch(`http://localhost:8080/user/activeUser/${userId}`, { 
        method: "POST",
    });
    window.location.reload();
    console.log("res: "+JSON.stringify(response))
}

