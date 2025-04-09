const btnOptions = document.getElementById("btnOptionUser").addEventListener('click',showModalOptionsUser)
const userIdFormInput = document.getElementById("userIdFormInput");

let selectedUserId = null;
let modalVisible = false;

document.addEventListener("DOMContentLoaded", function () {
    const errorMessageContainer = document.getElementById("errorMessageContainer");

    if (errorMessageContainer) {
        const errorMessage = errorMessageContainer.dataset.errorMessage;

        if (errorMessage) {
            Swal.fire({
                icon: 'error',
                title: 'Oops...',
                text: errorMessage,
            });
        }
    }
  });

document.addEventListener('click', function(event) {
    const btnOptions = document.getElementById('options');
    const ModalAdd = document.getElementById('addModal');
    const cpfInput = document.getElementById("cpfInput");
    const rgInput = document.getElementById("rgInput");
    const phoneInput = document.getElementById("phoneInput");
    const target = event.target;

    if (modalVisible && !btnOptions.contains(target) && !target.closest('button')) {
        hideModalOptionsUser();
    }

    if(modalVisible && !ModalAdd.contains(target) && !target.closest('button')){
        hideModalAddUser()
    }

    cpfInput.addEventListener("input", function (e) {
        let value = cpfInput.value.replace(/\D/g, "");
        if (value.length > 11) value = value.slice(0, 11); 

        value = value.replace(/(\d{3})(\d)/, "$1.$2");
        value = value.replace(/(\d{3})(\d)/, "$1.$2");
        value = value.replace(/(\d{3})(\d{1,2})$/, "$1-$2");

        cpfInput.value = value;
    });

    rgInput.addEventListener("input", function (e) {
        let value = rgInput.value.replace(/\D/g, "");
        if (value.length > 9) value = value.slice(0, 9); 

        value = value.replace(/(\d{2})(\d)/, "$1.$2");
        value = value.replace(/(\d{3})(\d)/, "$1.$2");
        value = value.replace(/(\d{3})(\d{1})$/, "$1-$2");

        rgInput.value = value;
    });
    phoneInput.addEventListener("input", function () {
        let value = phoneInput.value.replace(/\D/g, ""); 
        if (value.length > 11) value = value.slice(0, 11); 

        value = value.replace(/(\d{2})(\d)/, "($1) $2");
        value = value.replace(/(\d{5})(\d)/, "$1-$2");

        phoneInput.value = value;
    });

    

});

function showModalOptionsUser(button){
    const options = document.getElementById('options');
    const rect = button.getBoundingClientRect();
    selectedUserId = button.closest('tr').getAttribute('data-id');
    userIdFormInput.value = selectedUserId;

    const marginLeft = 125;
    const marginTop = -40;

    let modalLeft = rect.left - marginLeft;
    let modalTop = rect.top + marginTop;

    options.style.top = `${modalTop}px`;
    options.style.left = `${modalLeft}px`;

    options.style.display = 'block';
    modalVisible = true;
}

function redirectToInfo(){
    window.location.href="/user/infoClient/"+selectedUserId;
}

function hideModalOptionsUser(){
    const options = document.getElementById('options');
    options.style.display="none";
}




function showModalAddUser(){
    const AddModal = document.getElementById('addModal');
    AddModal.style.display = 'block';
    modalVisible = true;
}
function hideModalAddUser(){
    const AddModal = document.getElementById('addModal');
    AddModal.style.display="none";
    modalVisible = false;
}



function showMenu() {
    const dropdownMenu = document.getElementById('dropdownMenu');
    dropdownMenu.classList.toggle('active');
}
