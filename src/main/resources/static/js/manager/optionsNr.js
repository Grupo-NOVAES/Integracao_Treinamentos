let modalVisible = false;
let selectedNrId = null
let selectedNrNumber = null;

document.addEventListener('click', function(event) {
    const btnOptions = document.getElementById('options');
    const ModalAdd = document.getElementById('addModal');
    const target = event.target;
    document.getElementById("nrIdInput").value = selectedNrId;

    if (modalVisible && !btnOptions.contains(target) && !target.closest('button')) {
        hideModalOptionsNr();
    }

    if(modalVisible && !ModalAdd.contains(target) && !target.closest('button')){
        hideModalAddNr()
    }

});


function showModalOptionsNr(button){
    const options = document.getElementById('options');
    const rect = button.getBoundingClientRect();
    selectedNrId = button.closest('tr').getAttribute('data-id');
    selectedNrNumber = button.closest('tr').getAttribute('data-number');

    const marginLeft = 125;
    const marginTop = -40;

    let modalLeft = rect.left - marginLeft;
    let modalTop = rect.top + marginTop;

    options.style.top = `${modalTop}px`;
    options.style.left = `${modalLeft}px`;

    options.style.display = 'block';
    modalVisible = true;
}
function hideModalOptionsNr(){
    const options = document.getElementById('options');
    options.style.display="none";
}

function redirectToNrInfo(){
    window.location.href="/Nr/"+selectedNrId;
}

function redirectToQuestionBynR(){
    window.location.href="/Nr/"+selectedNrNumber+"/question";
}


function showModalAddNr(){
    const AddModal = document.getElementById('addModal');
    AddModal.style.display = 'block';
    modalVisible = true;
}
function hideModalAddNr(){
    const AddModal = document.getElementById('addModal');
    AddModal.style.display="none";
    modalVisible = false;
}



function showMenuNr() {
    const dropdownMenu = document.getElementById('dropdownMenu');
    dropdownMenu.classList.toggle('active');
}
