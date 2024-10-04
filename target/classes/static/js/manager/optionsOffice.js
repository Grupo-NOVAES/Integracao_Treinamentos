let modalVisible = false;
let selectedOfficeId = null;


document.addEventListener('click', function(event) {
    const btnOptions = document.getElementById('options');
    const ModalAdd = document.getElementById('addModal');
    const target = event.target;

    if (modalVisible && !btnOptions.contains(target) && !target.closest('button')) {
        hideModalOptionsOffice();
    }

    if(modalVisible && !ModalAdd.contains(target) && !target.closest('button')){
        hideModalAddOffice()
    }

});

document.getElementById("btnOptionUser").addEventListener('click',showModalOptionsOffice)

function showModalOptionsOffice(button){
    const options = document.getElementById('options');
    const rect = button.getBoundingClientRect();
    selectedOfficeId = button.closest('tr').getAttribute('data-id');

    const marginLeft = 125;
    const marginTop = -40;

    let modalLeft = rect.left - marginLeft;
    let modalTop = rect.top + marginTop;

    options.style.top = `${modalTop}px`;
    options.style.left = `${modalLeft}px`;

    options.style.display = 'block';
    modalVisible = true;
}
function hideModalOptionsOffice(){
    const options = document.getElementById('options');
    options.style.display="none";
}


function redirectToOfficeInfo(){
    window.location.href="/office/"+selectedOfficeId;
}

function showModalAddOffice(){
    const AddModal = document.getElementById('addModal');
    AddModal.style.display = 'block';
    modalVisible = true;
}
function hideModalAddOffice(){
    const AddModal = document.getElementById('addModal');
    AddModal.style.display="none";
    modalVisible = false;
}



function showMenuOffice() {
    const dropdownMenu = document.getElementById('dropdownMenu');
    dropdownMenu.classList.toggle('active');
}
