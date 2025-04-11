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
document.addEventListener("DOMContentLoaded", () => {
  const rows = document.querySelectorAll("tr.UserNrData");

  rows.forEach(row => {
    const status = row.getAttribute("data-status");
    const statusTd = row.querySelector("td:nth-child(3)");

    if (statusTd) {
      const statusClass = `status-${status.toLowerCase()}`;
      statusTd.classList.add(statusClass);
    }
  });
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

async function downloadCertificate() {
  try {
    const response = await fetch(`/user/downloadCertify/${selectedUserId}/${selectedNrId}`);

    if (!response.ok) {
      const errorData = await response.json();
      alert(errorData.error); // Aqui você pode usar um modal no lugar do alert, se quiser
      return;
    }

    const blob = await response.blob();
    const url = window.URL.createObjectURL(blob);
    const a = document.createElement("a");
    a.href = url;
    a.download = `Certificado_NR${selectedNrId}.pptx`;
    document.body.appendChild(a);
    a.click();
    document.body.removeChild(a);
    window.URL.revokeObjectURL(url);
  } catch (error) {
    alert("Erro ao tentar baixar o certificado. Tente novamente mais tarde.");
  }
}


function reassessmentUserNr(){
  window.location.href=`/user/reassessmentNr/${selectedUserId}/${selectedNrId}`
}