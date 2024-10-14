const btnOptions = document.getElementById("btnOptionUser").addEventListener('click',showModalOptionsUser)
const userIdFormInput = document.getElementById("userIdFormInput");
let selectedUserId = null;
let modalVisible = false;

document.addEventListener('click', function(event) {
    const btnOptions = document.getElementById('options');
    const ModalAdd = document.getElementById('addModal');
    const target = event.target;

    if (modalVisible && !btnOptions.contains(target) && !target.closest('button')) {
        hideModalOptionsUser();
    }

    if(modalVisible && !ModalAdd.contains(target) && !target.closest('button')){
        hideModalAddUser()
    }

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
