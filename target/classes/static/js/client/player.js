const btnRealizeForm = document.getElementById("btnStartForm");
let nrNumber = null;

function RealizeForm(button) {
    nrNumber = document.getElementById("nrNumberValue").value
    window.location.href = `/question/${nrNumber}`;
}