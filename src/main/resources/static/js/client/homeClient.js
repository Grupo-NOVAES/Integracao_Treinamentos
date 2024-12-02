const btnPerformNR = document.getElementById("btnPerformNR");
let selectedNrNumber = null

function performNr(button) {
    selectedNrNumber = button.closest('.card-content').getAttribute('data-number');

    window.location.href = `/question/player/${selectedNrNumber}`;
}

btnPerformNR.addEventListener('click',performNr)